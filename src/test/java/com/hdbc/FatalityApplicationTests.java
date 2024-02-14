package com.hdbc;

import com.hdbc.mapper.UserMapper;
import com.hdbc.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class FatalityApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void testInsert() {
        User user = new User();
        user.setNickname("test");
        user.setOpenid("0000000");
        userMapper.insert(user);
        System.out.println(user);
    }


}
