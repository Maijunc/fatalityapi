package com.hdbc.mapper;

import com.hdbc.pojo.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface GroupMapper {
    @Insert("insert into t_group(group_name, create_time, update_time)" +
    " values (#{groupName},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys=true, keyProperty="groupID", keyColumn="group_id")
    public void insert(Group group);

    @Update("update t_group set update_time = #{updateTime} where group_id = #{groupID}")
    void updateTime(Integer groupID, LocalDateTime updateTime);
}
