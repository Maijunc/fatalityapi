package com.hdbc.service.impl;

import com.hdbc.mapper.TaskMapper;
import com.hdbc.pojo.Task;
import com.hdbc.service.TaskService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void insert(Task task, Integer groupID) {
        taskMapper.insert(task,groupID);
    }

    @Override
    public void updateLeftNumber(Integer groupID, Integer taskID, int num) {
        taskMapper.updateLeftNumber(groupID, taskID, num);
    }

    @Override
    public Task getTask(Integer groupID, Integer taskID) {
        return taskMapper.getTask(groupID, taskID);
    }

    @Override
    public List<Task> getTasks(Integer groupID) {
        return taskMapper.getTasks(groupID);
    }
}
