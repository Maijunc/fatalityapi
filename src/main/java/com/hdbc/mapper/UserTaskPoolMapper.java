package com.hdbc.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTaskPoolMapper {
    @Select("select distinct pool_name from user_task_pool where user_id = #{userID}")
    List<String> getPoolNameByUserID(long userID);

    @Select("select task_name from user_task_pool where user_id = #{userID} and pool_name = #{poolName}")
    List<String> getPoolByName(long userID,String poolName);

    @Insert("insert into user_task_pool(user_id, task_name, pool_name) " +
            "values (#{userID},#{taskName},#{poolName})")
    @Options(useGeneratedKeys=true, keyColumn="user_task_id")
    void setPool(long userID, String taskName, String poolName);
}