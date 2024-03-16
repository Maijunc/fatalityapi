package com.hdbc.service.impl;

import com.hdbc.mapper.AssignmentMapper;
import com.hdbc.pojo.Assignment;
import com.hdbc.pojo.Task;
import com.hdbc.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public boolean assign(Long userID, Integer groupID, Integer taskID, String taskName) {
        Assignment assignment =assignmentMapper.getAssignment(userID,groupID);

        //如果还没有加入小组就先加入小组
        if(assignmentMapper.check(userID,groupID) <= 0){
            joinGroup(userID,groupID);
        }
        else if(assignment.getTaskID() != null && assignment.getTaskName() != null){
            return false;
        }
        assignmentMapper.updateTask(userID, groupID, taskID, taskName);

        return true;
    }

    @Override
    public boolean joinGroup(Long userID, Integer groupID) {
        //如果已经加入了，就返回false
        if(assignmentMapper.check(userID,groupID) > 0){
            return false;
        }

        assignmentMapper.insert(userID,groupID,null,null);

        return true;
    }


}
