package com.hdbc.handler;

import com.alibaba.fastjson.JSON;
import com.hdbc.common.RedisKey;
import com.hdbc.common.Result;
import com.hdbc.pojo.dto.UserDto;
import com.hdbc.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


//拦截器的使用 要放入mvc配置当中
@Component
public class LoginHandler implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //方法执行之前 进行拦截
        /*
         * 1. 判断请求是否是请求 controller 方法
         * 2. 有些接口是不需要登录拦截的，需要开发自定义的注解 @NoAuth 此注解标识的不需要登录
         * 3. 拿到token
         * 4. token认证 redis认证 -> user信息
         * 5. 如果token认证通过 就放行 认证不通过 就返回未登录
         * 6. 得到了用户信息 放入ThreadLocal当中
         *
         */
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.hasMethodAnnotation(NoAuth.class)){
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)){
            return noLoginResponse(response);
        }
        token = token.replace("Bearer ","");
        boolean verify = JWTUtils.verify(token);
        if(!verify){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.FAIL("未登录")));
            return false;
        }
        String userJson = redisTemplate.opsForValue().get(RedisKey.TOKEN + token);
        if(StringUtils.isBlank(userJson)){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.FAIL("未登录")));
            return false;
        }
        UserDto userDto = JSON.parseObject(userJson,UserDto.class);
        // 得到了用户信息 放入ThreadLocal当中
        UserThreadLocal.put(userDto);
        return true;
    }

    private boolean noLoginResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.FAIL("未登录")));
        return false;
    }
}
