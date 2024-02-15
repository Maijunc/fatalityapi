package com.hdbc.service.impl;

import com.hdbc.mapper.GroupMapper;
import com.hdbc.pojo.Group;
import com.hdbc.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public void insert(Group group) {
        groupMapper.insert(group);
    }

    @Override
    public void updateTime(Integer groupID) {
        LocalDateTime currentTime = LocalDateTime.now();

        groupMapper.updateTime(groupID,currentTime);
    }
}
