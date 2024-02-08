package com.hdbc.service;

import com.hdbc.common.Result;
import com.hdbc.model.WXAuth;
import com.hdbc.pojo.Group;
import com.hdbc.pojo.GroupWithTasks;

import java.util.List;

public interface UserService {
    String getSessionID(String code);

    List<Group> getGroups(Integer userID);

    Result authLogin(WXAuth wxAuth);

    Result userinfo(String token, Boolean refresh);
}
