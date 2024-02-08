package com.hdbc.mapper;

import com.hdbc.pojo.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Insert("insert into t_task(task_id, task_name, team_number, left_number, group_id)" +
    "values (#{task.taskID},#{task.taskName},#{task.teamNumber},#{task.leftNumber},#{groupID})")
    void insert(Task task, Integer groupID);

    @Update("update t_task set left_number = left_number - #{num} where task_id = #{taskID} and "
    + "group_id = #{groupID}")
    void updateLeftNumber(Integer groupID, Integer taskID, int num);

    @Select("select task_id, task_name, team_number, left_number from t_task where group_id = #{groupID} and task_id = #{taskID}")
    Task getTask(Integer groupID, Integer taskID);

    @Select("select task_id, task_name, team_number, left_number from t_task where group_id = #{groupID}")
    List<Task> getTasks(Integer groupID);
}
