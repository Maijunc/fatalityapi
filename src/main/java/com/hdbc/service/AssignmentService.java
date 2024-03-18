package com.hdbc.service;

import com.hdbc.common.Result;

public interface AssignmentService {
    boolean assign(Long userID, Integer groupID, Integer taskID, String taskName);

    boolean joinGroup(Long userID, Integer groupID);

    Result getAssignInfo(Long userID, Integer groupID);

    Result randomAssign(Integer groupID);

    boolean groupIsFull(Integer groupID);
}
