package com.hdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FoodPoolMapper {
    @Select("select food_name from food_pool")
    List<String> getPool();
}
