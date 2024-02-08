package com.hdbc.service.impl;

import com.hdbc.mapper.AssignmentMapper;
import com.hdbc.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public void insert(Integer userID, Integer groupID, Integer taskID, String taskName) {
        assignmentMapper.insert(userID, groupID, taskID, taskName);
    }
}
