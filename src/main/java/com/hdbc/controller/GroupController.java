package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
import com.hdbc.handler.NoAuth;
import com.hdbc.pojo.Group;
import com.hdbc.pojo.GroupWithTasks;
import com.hdbc.pojo.Task;
import com.hdbc.service.AssignmentService;
import com.hdbc.service.GroupService;
import com.hdbc.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/createGroup")
    public Result createGroup(@RequestBody GroupWithTasks groupWithTasks)
    {
        log.info("创建小组");
        //获取group对象
        Group group = groupWithTasks.getGroup();

        groupService.insert(group);

        for(Task task : groupWithTasks.getTasks())
            taskService.insert(task,group.getGroupID());

        String returnMessage = "groupID: " + group.getGroupID();

        return Result.SUCCESS(returnMessage);
    }

    @GetMapping("/assignTask")
    public Result assignTask(Long userID, Integer groupID, Integer taskID)
    {
        log.info("分配任务");

        //查询该groupID和taskID对应的task
        Task task = taskService.getTask(groupID,taskID);

        if(task == null) {
            return Result.FAIL("未找到该小组对应的任务！");
        }

        if(task.getLeftNumber() - 1 < 0) {
            return Result.FAIL("该任务已满员!");
        }

        //插入一条用户任务匹配数据
        if(!assignmentService.assign(userID,groupID,taskID,task.getTaskName())){
            return Result.FAIL(ResultCode.ASSIGNMENT_EXISTED);
        }

        //剩余任务人数-1
        taskService.updateLeftNumber(groupID, taskID, 1);

        //更新修改时间
        groupService.updateTime(groupID);

        return Result.SUCCESS();
    }

    @GetMapping("/getTasksByGroupID")
    @NoAuth
    public Result getTasksByGroupID(Integer groupID)
    {
        return Result.SUCCESS(taskService.getTasks(groupID));
    }

    @GetMapping("/joinGroup")
    public Result joinGroup(Long userID, Integer groupID)
    {
        //检查小组是否满员
        if(assignmentService.groupIsFull(groupID))
        {
            return Result.FAIL(ResultCode.GROUP_IS_FULLED);
        }
        //检查用户是否已经是小组成员
        if(!assignmentService.joinGroup(userID,groupID))
        {
            return Result.FAIL(ResultCode.USER_IS_IN_GROUP);
        }


        return Result.SUCCESS();
    }

    //获取用户在小组内的分工信息
    @GetMapping("/getAssignInfo")
    public Result getAssignInfo(Long userID, Integer groupID)
    {
        return assignmentService.getAssignInfo(userID,groupID);
    }

    //随机分工
    @GetMapping("/randomAssign")
    public Result randomAssign(Integer groupID)
    {
        return assignmentService.randomAssign(groupID);
    }
}
