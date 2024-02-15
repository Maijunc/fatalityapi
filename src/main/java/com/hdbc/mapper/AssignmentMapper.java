package com.hdbc.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssignmentMapper {
    @Insert("insert into t_assignment(user_id, group_id, task_id, task_name)" +
    " values (#{userID},#{groupID},#{taskID},#{taskName})")
    void insert(Integer userID, Integer groupID, Integer taskID, String taskName);


    @Select("select count(*) FROM t_assignment where user_id = #{userID} " +
            "and group_id = #{groupID}")
    int check(Integer userID, Integer groupID);
}
