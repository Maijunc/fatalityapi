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

    void insertItem(@Param("userID") Long userID, @Param("poolName")  String poolName, @Param("newItem")  String newItem);

    @Delete("delete from user_food_pool where user_id = #{userID} and pool_name = #{poolName} and food_name = #{deleteItem}")
    void deleteItem(Long userID, String poolName, String deleteItem);
}
