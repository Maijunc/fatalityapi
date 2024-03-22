package com.hdbc.mapper;

import com.hdbc.pojo.Assignment;
import com.hdbc.pojo.UserForShow;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    //插入一个新的分配项
    @Insert("insert into t_assignment(user_id, group_id, task_id, task_name)" +
    " values (#{userID},#{groupID},#{taskID},#{taskName})")
    void insert(Long userID, Integer groupID, Integer taskID, String taskName);

    //检查用户是否在小组内
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

    @Select("select user_id from t_assignment where group_id = #{groupID}")
    List<Long> getUserIDsInGroup(Integer groupID);

    //获取未分配任务的用户ID
    @Select("select user_id from t_assignment where group_id = #{groupID} and ISNULL(task_id) and task_name is null ")
    List<Long> getUserIDsWithNoTask(Integer groupID);

    //获取小组成员数量
    @Select("select count(*) from t_assignment where group_id = #{groupID}")
    int getGroupMemberCount(Integer groupID);

    List<UserForShow> getGroupMembers(Integer groupID);
}
