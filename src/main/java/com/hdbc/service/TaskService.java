package com.hdbc.service;

import com.hdbc.pojo.Task;

import java.util.List;

public interface TaskService {
    void insert(Task task, Integer groupID);

    void updateLeftNumber(Integer groupID, Integer taskID, int num);

    Task getTask(Integer groupID, Integer taskID);

    List<Task> getTasks(Integer groupID);
}
