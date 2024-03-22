package com.hdbc;

import com.hdbc.mapper.AssignmentMapper;
import com.hdbc.mapper.UserFoodPoolMapper;
import com.hdbc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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
        Long userID = 114L;
        String poolName = "空池子";

        userFoodPoolMapper.deleteNull(userID,poolName);
    }


}
