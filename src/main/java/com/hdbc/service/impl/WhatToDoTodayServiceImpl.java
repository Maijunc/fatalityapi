package com.hdbc.service.impl;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
import com.hdbc.mapper.DefaultPoolMapper;
import com.hdbc.mapper.UserTaskPoolMapper;
import com.hdbc.pojo.Pool;
import com.hdbc.service.WhatToDoTodayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
            if(tmp.getList().get(0) == null)
                tmp.setList(null);
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
        //已经存在该池子
        if(!userTaskPoolMapper.getPoolByName(pool.getUserID(),pool.getPoolName()).isEmpty()){
            return Result.FAIL(ResultCode.POOL_NAME_EXISTED);
        }
        List<String> poolList = pool.getList();
        //如果传过来是空数据或者是空值，就生成一条任务名为null的数据
        if(poolList.isEmpty() || poolList == null)
            userTaskPoolMapper.setPool(pool.getUserID(), null, pool.getPoolName());

        for(String taskName : poolList){
            userTaskPoolMapper.setPool(pool.getUserID(), taskName, pool.getPoolName());
        }

        return Result.SUCCESS();
    }

    @Override
    public Result deletePool(long userID, String poolName) {

        if(userTaskPoolMapper.deletePool(userID,poolName) == 0)
        {
            return Result.FAIL(ResultCode.POOL_NOT_EXIST);
        }

        return Result.SUCCESS();
    }

    @Override
    public Result updatePool(Long userID, String poolName, List<String> newItems, List<String> deleteItems) {
        //插入池子中新增的物品
        List<String> poolInDB = userTaskPoolMapper.getPoolByName(userID,poolName);
        //重复性检验
        Iterator<String> iterator = newItems.iterator();
        while (iterator.hasNext()) {
            String newitem = iterator.next();
            for (String item : poolInDB) {
                //防止空指针
                if(item == null)
                    continue;
                if (item.equals(newitem)) {
                    iterator.remove();
                    break; // 只需要删除一次
                }
            }
        }

        if(!newItems.isEmpty()) {
            //添加的时候删除空值
            userTaskPoolMapper.deleteNull(userID,poolName);

            userTaskPoolMapper.batchInsertItems(userID, poolName, newItems);
        }
        userTaskPoolMapper.batchDeleteItems(userID, poolName, deleteItems);
        return Result.SUCCESS();
    }

}
