package com.hdbc.service.impl;

import com.hdbc.mapper.FoodPoolMapper;
import com.hdbc.mapper.UserFoodPoolMapper;
import com.hdbc.service.WhatToEatTodayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatToEatTodayServiceImpl implements WhatToEatTodayService{
    @Autowired
    private FoodPoolMapper foodPoolMapper;

    @Autowired
    private UserFoodPoolMapper userFoodPoolMapper;

    @Override
    public List<String> getPool(Integer userID) {
        List<String> list = userFoodPoolMapper.getPool(userID);

        //如果不空就返回
        if(!list.isEmpty())
            return list;
        else
            return foodPoolMapper.getPool();
    }

    @Override
    public List<String> getDefaultPool() {
        return foodPoolMapper.getPool();
    }


}
