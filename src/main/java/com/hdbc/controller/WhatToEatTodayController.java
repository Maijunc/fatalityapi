package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.handler.NoAuth;
import com.hdbc.pojo.Pool;
import com.hdbc.service.WhatToEatTodayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    public Result getDefaultPool(String defaultPoolName)
    {
        log.info("获取默认食物池");
        return Result.SUCCESS(whatToEatTodayService.getDefaultPool("食物", defaultPoolName));
    }

    @PostMapping("/setPool")
    public Result setPool(@RequestBody Pool pool)
    {
        log.info("设置用户食物池");
        Result result = whatToEatTodayService.setPool(pool);
        return result;
    }

    @PostMapping("/deletePool")
    public Result deletePool(@RequestBody Map<String, String> requestBody)
    {
        String userID = requestBody.get("userID");
        String poolName = requestBody.get("poolName");

        Long userIdLong = Long.valueOf(userID);

        log.info("删除用户食物池");
        Result result = whatToEatTodayService.deletePool(userIdLong,poolName);
        return result;
    }
}
