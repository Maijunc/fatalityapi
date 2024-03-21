package com.hdbc.service;

import com.hdbc.common.Result;
import com.hdbc.pojo.Pool;

import java.util.List;

public interface WhatToDoTodayService {
    List<Pool> getPool(long userID);

    List<String> getDefaultPool(String aspect, String defaultPoolName);

    Result setPool(Pool pool);

    Result deletePool(long userID, String poolName);

    Result updatePool(Long userID, String poolName, List<String> newItems, List<String> deleteItems);
}
