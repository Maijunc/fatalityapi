package com.hdbc.mapper;

import com.hdbc.pojo.Group;
import com.hdbc.pojo.Task;
import com.hdbc.pojo.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GroupMapper {
    @Insert("insert into t_group(group_name, create_time, update_time)" +
    " values (#{groupName},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys=true, keyProperty="groupID", keyColumn="group_id")
    void insert(Group group);

    @Update("update t_group set update_time = #{updateTime} where group_id = #{groupID}")
    void updateTime(Integer groupID, LocalDateTime updateTime);

}
