package com.hdbc;

import com.hdbc.mapper.AssignmentMapper;
import com.hdbc.mapper.UserFoodPoolMapper;
import com.hdbc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FatalityApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private UserFoodPoolMapper userFoodPoolMapper;

    @Test
    public void testInsert() {
        userFoodPoolMapper.deleteItem((long)114,"测试池子名","7777");
    }


}
