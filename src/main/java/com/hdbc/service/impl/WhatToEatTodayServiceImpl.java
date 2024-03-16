package com.hdbc.service.impl;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
import com.hdbc.mapper.DefaultPoolMapper;
import com.hdbc.mapper.UserFoodPoolMapper;
import com.hdbc.pojo.Pool;
import com.hdbc.service.WhatToEatTodayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class WhatToEatTodayServiceImpl implements WhatToEatTodayService{
    @Autowired
    private DefaultPoolMapper defaultPoolMapper;

    @Autowired
    private UserFoodPoolMapper userFoodPoolMapper;

    @Override
    public List<Pool> getPool(long userID) {
        List<String> poolNameList = userFoodPoolMapper.getPoolNameByUserID(userID);
        if(poolNameList.isEmpty()){
            return null;
        }

        List<Pool> pools = new LinkedList<>();

        for(String poolName : poolNameList){
            Pool tmp = new Pool(userID,poolName,userFoodPoolMapper.getPoolByName(userID, poolName));
            pools.add(tmp);
        }
        return pools;
    }

    @Override
    public List<String> getDefaultPool(String aspect, String defaultPoolName) {
        return defaultPoolMapper.getPool(aspect, defaultPoolName);
    }

    @Override
    public Result setPool(Pool pool) {
        if(!userFoodPoolMapper.getPoolByName(pool.getUserID(),pool.getPoolName()).isEmpty()){
            return Result.FAIL(ResultCode.POOL_NAME_EXISTED);
        }
        for(String foodName : pool.getList()){
            userFoodPoolMapper.setPool(pool.getUserID(), foodName, pool.getPoolName());
        }

        return Result.SUCCESS();
    }

    @Override
    public Result deletePool(long userID, String poolName) {

        if(userFoodPoolMapper.deletePool(userID,poolName) == 0)
        {
            return Result.FAIL(ResultCode.POOL_NOT_EXIST);
        }

        return Result.SUCCESS();
    }


}
