package com.hdbc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdbc.common.Result;
import com.hdbc.handler.NoAuth;
import com.hdbc.service.WhatToEatTodayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("whatToEatToday")
@Slf4j
public class WhatToEatTodayController {

    @Autowired
    private WhatToEatTodayService whatToEatTodayService;
    @GetMapping("/getPool")
    @NoAuth
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


    private static final ObjectMapper JACKSON = new ObjectMapper();

    @PostMapping("/setPool")
    @NoAuth
    public Result setPool(@RequestBody Integer userID, @RequestBody List<String> items)
    {
        System.out.println(userID);
        return Result.SUCCESS();
    }
}
