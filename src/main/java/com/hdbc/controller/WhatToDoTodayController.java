package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.handler.NoAuth;
import com.hdbc.pojo.Pool;
import com.hdbc.service.WhatToDoTodayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("whatToDoToday")
@Slf4j
public class WhatToDoTodayController {

    @Autowired
    private WhatToDoTodayService whatToDoTodayService;
    @GetMapping("/getPool")
    @NoAuth
    public Result getPool(Integer userID)
    {
        log.info("获取事务池");
        return Result.SUCCESS(whatToDoTodayService.getPool(userID));
    }

    @GetMapping("/getDefaultPool")
    @NoAuth
    public Result getDefaultPool(String defaultPoolName)
    {
        log.info("获取默认事务池");
        return Result.SUCCESS(whatToDoTodayService.getDefaultPool("事务", defaultPoolName));
    }

    @PostMapping("/setPool")
    @NoAuth
    public Result setPool(@RequestBody Pool pool)
    {
        log.info("设置用户事务池");
        whatToDoTodayService.setPool(pool);
        return Result.SUCCESS();
    }
}
