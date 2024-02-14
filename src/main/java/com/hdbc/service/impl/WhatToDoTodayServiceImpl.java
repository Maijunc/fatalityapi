package com.hdbc.service.impl;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
import com.hdbc.mapper.DefaultPoolMapper;
import com.hdbc.mapper.UserTaskPoolMapper;
import com.hdbc.pojo.Pool;
import com.hdbc.service.WhatToDoTodayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class WhatToDoTodayServiceImpl implements WhatToDoTodayService {
    @Autowired
    private DefaultPoolMapper defaultPoolMapper;

    @Autowired
    private UserTaskPoolMapper userTaskPoolMapper;

    @Override
    public List<Pool> getPool(long userID) {
        List<String> poolNameList = userTaskPoolMapper.getPoolNameByUserID(userID);
        if(poolNameList.isEmpty()){
            return null;
        }

        List<Pool> pools = new LinkedList<>();

        for(String poolName : poolNameList){
            Pool tmp = new Pool(userID,poolName,userTaskPoolMapper.getPoolByName(userID, poolName));
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
        if(!userTaskPoolMapper.getPoolByName(pool.getUserID(),pool.getPoolName()).isEmpty()){
            return Result.FAIL(ResultCode.POOL_NAME_EXISTED);
        }
        for(String taskName : pool.getList()){
            userTaskPoolMapper.setPool(pool.getUserID(), taskName, pool.getPoolName());
        }

        return Result.SUCCESS();
    }


}
