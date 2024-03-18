package com.hdbc.service.impl;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
import com.hdbc.mapper.AssignmentMapper;
import com.hdbc.mapper.TaskMapper;
import com.hdbc.pojo.Assignment;
import com.hdbc.pojo.Task;
import com.hdbc.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private TaskMapper taskMapper;

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

    @Override
    public Result getAssignInfo(Long userID, Integer groupID) {
        //如果没有加入该小组
        if(assignmentMapper.check(userID,groupID) <= 0){
            return Result.FAIL(ResultCode.ASSIGNMENT_NOT_EXIST);
        }
        return Result.SUCCESS(assignmentMapper.getAssignment(userID,groupID));
    }

    @Override
    public Result randomAssign(Integer groupID) {
        // 判断小组是否满员，没满员就返回
        if(!groupIsFull(groupID))
        {
            return Result.FAIL(ResultCode.GROUP_IS_NOT_FULL);
        }
        // 1. 获取小组的所有任务，包括任务人数
        // 2. 获取小组内的所有用户ID，存入一个Vector中
        List<Assignment> assignmentList = new ArrayList<>();
        List<Assignment> resultList = new ArrayList<>();

        int taskSum = 0;
        for(Task task : taskMapper.getTasks(groupID))
        {
            int leftNum = task.getLeftNumber();
            for(int i = 0 ; i < leftNum; i++)
            {
                assignmentList.add(new Assignment(null,groupID,task.getTaskID(),task.getTaskName()));
            }
            taskSum += leftNum;
        }

        //说明小组没剩余任务
        if(taskSum == 0)
            return Result.FAIL(ResultCode.GROUP_TASK_ASSIGNED);


        //如果不设定种子，对象会采用默认的种子（默认当前系统时间的毫秒数为种子）
        Random numList = new Random();
        //获取的UserID的数量与leftNumber的总数应该是匹配的
        List<Long> userIDList = assignmentMapper.getUserIDsWithNoTask(groupID);

        if(userIDList.isEmpty())
            return Result.FAIL(ResultCode.GROUP_TASK_ASSIGNED);

        for(Long userID : userIDList)
        {
            //生成 [0,taskSum)中任意一个数
            int ramdomNum = numList.nextInt(taskSum);
            taskSum--;
            assignmentList.get(ramdomNum).setUserID(userID);
            resultList.add(assignmentList.remove(ramdomNum));
        }

        for(Assignment a: resultList)
        {
            //System.out.println(a);
            assignmentMapper.updateTask(a.getUserID(),a.getGroupID(),a.getTaskID(),a.getTaskName());
        }

        //更新leftNumber为0
        return Result.SUCCESS(resultList);
    }

    @Override
    public boolean groupIsFull(Integer groupID) {
        return assignmentMapper.getGroupMemberCount(groupID) >= taskMapper.getTeamNumberSumInGroup(groupID);
    }


}
