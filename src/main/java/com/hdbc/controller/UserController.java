package com.hdbc.controller;

import com.hdbc.common.Result;
import com.hdbc.common.ResultCode;
import com.hdbc.handler.NoAuth;
import com.hdbc.model.WXAuth;
import com.hdbc.pojo.Group;
import com.hdbc.pojo.GroupWithTasks;
import com.hdbc.pojo.Task;
import com.hdbc.service.TaskService;
import com.hdbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    //getSessionID
    @GetMapping("/getSessionID")
    @NoAuth
    public Result getSessionID(String code)
    {
        log.info("获取SessionID");

        String sessionID = userService.getSessionID(code);
        Map<String, String> hashMap = new HashMap<>();
        //返回一个键值对
        hashMap.put("sessionID",sessionID);
        return Result.SUCCESS(hashMap);
    }

    @PostMapping("/authLogin")
    @NoAuth
    public Result authLogin(@RequestBody WXAuth wxAuth)
    {
        Result result = userService.authLogin(wxAuth);
        log.info("{}",result);
        return result;
    }

    @GetMapping("/userinfo")
    public Result userinfo(Boolean refresh){
        return userService.userinfo(refresh);
    }

    @GetMapping("/getGroupHistory")
    public Result getGroupHistory(Integer userID)
    {
        log.info("历史记录");

        //获取该用户ID对应的所有group
        List<Group> groupList = userService.getGroups(userID);

        try{
            List<GroupWithTasks> groupWithTasksList = new LinkedList<>();


            for(Group group : groupList)
            {
                //将每个小组对应的tasks封装到GroupWithTasks容器中
                List<Task> tasks = taskService.getTasks(group.getGroupID());
                groupWithTasksList.add(new GroupWithTasks(group, tasks));
            }

            if(groupWithTasksList.isEmpty())
                return Result.FAIL(ResultCode.GROUP_NO_FOUND);
            else
                return Result.SUCCESS(groupWithTasksList);
        }
        catch (Exception e) {
            return Result.FAIL(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }

    }
}
