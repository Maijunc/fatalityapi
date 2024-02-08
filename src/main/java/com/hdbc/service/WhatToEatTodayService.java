package com.hdbc.service;

import java.util.List;

public interface WhatToEatTodayService {
    List<String> getPool(Integer userID);

    List<String> getDefaultPool();
}
