package com.hdbc;

import com.hdbc.mapper.AssignmentMapper;
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
    @Autowired
    private AssignmentMapper assignmentMapper;

    @Test
    public void testInsert() {
        System.out.println(assignmentMapper.check((long)4,26));
    }


}
