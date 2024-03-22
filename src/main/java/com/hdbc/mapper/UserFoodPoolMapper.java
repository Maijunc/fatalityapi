package com.hdbc.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserFoodPoolMapper {
    @Select("select distinct pool_name from user_food_pool where user_id = #{userID}")
    List<String> getPoolNameByUserID(long userID);

    @Select("select food_name from user_food_pool where user_id = #{userID} and pool_name = #{poolName}")
    List<String> getPoolByName(long userID,String poolName);

    @Insert("insert into user_food_pool(user_id, food_name, pool_name) " +
            "values (#{userID},#{foodName},#{poolName})")
    @Options(useGeneratedKeys=true, keyColumn="user_food_id")
    void setPool(long userID, String foodName, String poolName);

    @Delete("delete from user_food_pool where user_id = #{userID} and pool_name = #{poolName}")
    int deletePool(long userID, String poolName);

    //插入单个内容
    void insertItem(@Param("userID") Long userID, @Param("poolName")  String poolName, @Param("newItem")  String newItem);

    //删除单个内容
    @Delete("delete from user_food_pool where user_id = #{userID} and pool_name = #{poolName} and food_name = #{deleteItem}")
    void deleteItem(Long userID, String poolName, String deleteItem);

    //批量插入
    void batchInsertItems(@Param("userID") Long userID, @Param("poolName") String poolName, @Param("items") List<String> items);

    //批量删除
    void batchDeleteItems(@Param("userID") Long userID, @Param("poolName") String poolName, @Param("items") List<String> items);

}
