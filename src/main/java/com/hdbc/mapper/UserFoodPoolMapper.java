package com.hdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserFoodPoolMapper {
    @Select("select food_name from user_food_pool where user_id = #{userID}")
    List<String> getPool(Integer userID);
}
