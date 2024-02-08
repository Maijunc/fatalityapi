package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.service.FeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("user")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @GetMapping("/insertFeedBack")
    public Result insertFeedBack(Integer userID, String feedbackText)
    {
        feedBackService.insertFeedBack(userID, feedbackText);

        return Result.SUCCESS();
    }
}
