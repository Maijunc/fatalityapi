package com.hdbc.mapper;

import org.apache.ibatis.annotations.*;

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

    @Delete("delete from user_task_pool where user_id = #{userID} and pool_name = #{poolName}")
    int deletePool(long userID, String poolName);

    void insertItem(@Param("userID") Long userID, @Param("poolName")  String poolName, @Param("newItem")  String newItem);

    @Delete("delete from user_task_pool where user_id = #{userID} and pool_name = #{poolName} and task_name = #{deleteItem}")
    void deleteItem(Long userID, String poolName, String deleteItem);
}
