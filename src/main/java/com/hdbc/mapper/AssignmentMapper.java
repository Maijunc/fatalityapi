package com.hdbc.mapper;

import com.hdbc.pojo.Assignment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AssignmentMapper {
    @Insert("insert into t_assignment(user_id, group_id, task_id, task_name)" +
    " values (#{userID},#{groupID},#{taskID},#{taskName})")
    void insert(Long userID, Integer groupID, Integer taskID, String taskName);


    @Select("select count(*) FROM t_assignment where user_id = #{userID} " +
            "and group_id = #{groupID}")
    int check(Long userID, Integer groupID);

    @Select("select * FROM t_assignment where user_id = #{userID} and group_id = #{groupID}")
    @Results({
            @Result(property = "userID", column = "user_id"),
            @Result(property = "groupID", column = "group_id"),
            @Result(property = "taskID", column = "task_id"),
            @Result(property = "taskName", column = "task_name"),
    })
    Assignment getAssignment(Long userID, Integer groupID);

    @Update("update t_assignment SET task_id = #{taskID}, task_name = #{taskName} " +
            "where user_id = #{userID} and group_id = #{groupID}")
    void updateTask(Long userID, Integer groupID, Integer taskID, String taskName);

}
