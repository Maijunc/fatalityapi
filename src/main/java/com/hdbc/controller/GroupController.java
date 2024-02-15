package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
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
    public Result assignTask(Integer userID, Integer groupID, Integer taskID)
    {
        log.info("分配任务");

        //查询该groupID和taskID对应的task
        Task task = taskService.getTask(groupID,taskID);
        if(task == null)
            return Result.FAIL("未找到该小组对应的任务！");

        //剩余任务人数-1
        if(task.getLeftNumber() - 1 >= 0)
            taskService.updateLeftNumber(groupID,taskID,1);
        else
            return Result.FAIL("该任务已满员!");

        //插入一条用户任务匹配数据
        if(!assignmentService.insert(userID,groupID,taskID,task.getTaskName())){
            return Result.FAIL(ResultCode.ASSIGNMENT_EXISTED);
        }


        //更新修改时间
        groupService.updateTime(groupID);

        return Result.SUCCESS();
    }

}
