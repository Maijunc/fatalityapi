package com.hdbc.mapper;

import com.hdbc.pojo.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface GroupMapper {
    @Insert("insert into t_group(group_name, create_time, update_time)" +
    " values (#{groupName},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys=true, keyProperty="groupID", keyColumn="group_id")
    public void insert(Group group);
}
