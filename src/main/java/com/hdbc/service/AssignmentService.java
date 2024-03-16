package com.hdbc.service;

public interface AssignmentService {
    boolean assign(Long userID, Integer groupID, Integer taskID, String taskName);

    boolean joinGroup(Long userID, Integer groupID);
}
