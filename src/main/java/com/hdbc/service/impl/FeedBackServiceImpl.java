package com.hdbc.service.impl;

import com.hdbc.mapper.FeedBackMapper;
import com.hdbc.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;


    @Override
    public void insertFeedBack(Integer userID, String feedbackText) {
        feedBackMapper.insertFeedBack(userID, feedbackText);
    }
}
