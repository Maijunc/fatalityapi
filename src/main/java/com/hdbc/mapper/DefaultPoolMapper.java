package com.hdbc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DefaultPoolMapper {
    @Select("select item_name from default_pool where aspect = #{aspect} and default_pool_name = #{defaultPoolName}")
    List<String> getPool(String aspect, String defaultPoolName);
}
