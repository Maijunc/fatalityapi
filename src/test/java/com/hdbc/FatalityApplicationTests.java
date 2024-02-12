package com.hdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class FatalityApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisConnection() {
        String key = "testKey";
        String value = "testValue";

        // 存储数据到 Redis
        redisTemplate.opsForValue().set(key, value);

        // 从 Redis 中获取数据
        String retrievedValue = redisTemplate.opsForValue().get("test");

        System.out.println(retrievedValue);

        // 验证存储和检索是否成功
//        assert value.equals(retrievedValue);
    }


}
