package com.hdbc.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hdbc.common.RedisKey;
import com.hdbc.common.Result;
import com.hdbc.handler.UserThreadLocal;
import com.hdbc.mapper.UserMapper;
import com.hdbc.model.WXAuth;
import com.hdbc.pojo.Group;
import com.hdbc.pojo.User;
import com.hdbc.pojo.WxUserInfo;
import com.hdbc.pojo.dto.UserDto;
import com.hdbc.service.UserService;
import com.hdbc.service.WxService;
import com.hdbc.util.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    @Value("${wxmini.appid}")
    private String appid;

    @Value("${wxmini.secret}")
    private String secret;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;


    @Override
    public String getSessionID(String code) {
        /*
            1.拼接一个url，微信登录凭证校验接口
            2.发起一个http调用，获取微信的返回结果
            3.存到redis
            4.生成一个sessionID,返回给前端作为当前需要用户的标识
            5.生成一个sessionID,用户在点击微信登录的时候，我们可以标识是谁点击微信登录
         */
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        //替换掉url中的占位符
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        String res = HttpUtil.get(replaceUrl);
        //随机生成一个uuid对应一个用户
        String uuid = UUID.randomUUID().toString();
        JSONObject jsonObject = JSONObject.parseObject(res);
        // 获取到key为shoppingCartItemList的值
        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");

        redisTemplate.opsForValue().set(RedisKey.WX_SESSION_ID + uuid, res, 30, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public List<Group> getGroups(Integer userID) {
        return userMapper.getGroups(userID);
    }

    @Autowired
    private WxService wxService;

    @Override
    public Result authLogin(WXAuth wxAuth) {
        /*
        * 1.通过wxauth中的值，要对它进行解密
        * 2.解密完成之后，会获取到微信用户信息 其中包含 openid 性别 昵称 头像等信息
        * 3.openid 是唯一的 需要去user表中查询openid是否存在，存在，则此用户的身份登录成功
        * 4.不存在，新用户，注册流程，登录成功
        * 5.使用jwt技术，生成一个token，提供给前端token令牌，用户在下次访问的时候，携带token来访问
        * 6.后端通过对token的验证，知道此用户是否处于登录状态，以及是哪个用户登录的
         */
        try {
            String json = wxService.wxDecrypt(wxAuth.getEncryptedData(), wxAuth.getSessionId(), wxAuth.getIv());
            System.out.println(json);
            WxUserInfo wxUserInfo = JSON.parseObject(json,WxUserInfo.class);
            String openid = wxUserInfo.getOpenId();
            User user = userMapper.selectByOpenId(openid);
            UserDto userDto = new UserDto();
            userDto.from(wxUserInfo);
            //判断用户是否存在
            if(user == null){
                // 注册
                return this.register(userDto);
            }else {
                userDto.setId(user.getUserID());
                userDto.setNickname(user.getNickname());
                //登录
                return this.login(userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.FAIL();
    }

    @Override
    public Result userinfo(Boolean refresh) {
        /*
         * 1. 根据token，来验证此token 是否有效
         * 2. refresh 如果为true 代表刷新 重新生成token和redis里面重新存储 续期
         * 3. false 直接返回用户信息 -> redis中 查询出来 直接返回
         */
        UserDto userDto = UserThreadLocal.get();
        if(refresh){
            String token = JWTUtils.sign(userDto.getId());
            userDto.setToken(token);
            redisTemplate.opsForValue().set(RedisKey.TOKEN+token,JSON.toJSONString(userDto),7,TimeUnit.DAYS);
        }
        return Result.SUCCESS(userDto);
    }

    @Override
    public Result storeAvatarURL(String openid, String avatarURL) {
        userMapper.storeAvatarURL(openid, avatarURL);

        return Result.SUCCESS();
    }

    private Result login(UserDto userDto) {
        String token = JWTUtils.sign(userDto.getId());
        userDto.setToken(token);
        userDto.setOpenid(null);
        userDto.setWxUnionId(null);
        redisTemplate.opsForValue().set(RedisKey.TOKEN+token,JSON.toJSONString(userDto),7, TimeUnit.DAYS);
        return Result.SUCCESS(userDto);
    }

    private Result register(UserDto userDto) {
        User user = new User();
        userDto.setNickname(wxService.getStringRandom(10));
        BeanUtils.copyProperties(userDto,user);
        this.userMapper.insert(user);
        userDto.setId(user.getUserID());
        return this.login(userDto);
    }


}
