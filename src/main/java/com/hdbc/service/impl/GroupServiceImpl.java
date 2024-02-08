package com.hdbc.service.impl;

import com.hdbc.mapper.GroupMapper;
import com.hdbc.pojo.Group;
import com.hdbc.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public void insert(Group group) {
        groupMapper.insert(group);
    }
}
