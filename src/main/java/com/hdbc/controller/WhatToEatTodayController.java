package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.handler.NoAuth;
import com.hdbc.service.WhatToEatTodayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("whatToEatToday")
@Slf4j
public class WhatToEatTodayController {

    @Autowired
    private WhatToEatTodayService whatToEatTodayService;
    @GetMapping("/getPool")
    public Result getPool(Integer userID)
    {
        log.info("获取食物池");
        return Result.SUCCESS(whatToEatTodayService.getPool(userID));
    }

    @GetMapping("/getDefaultPool")
    @NoAuth
    public Result getDefaultPool()
    {
        log.info("获取默认食物池");
        return Result.SUCCESS(whatToEatTodayService.getDefaultPool());
    }
}
