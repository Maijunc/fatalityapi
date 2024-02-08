package com.hdbc.mapper;

import com.hdbc.pojo.Group;
import com.hdbc.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper{

    @Select("select t_group.group_id, group_name, create_time, update_time from " +
            "t_assignment inner join t_group on t_assignment.group_id = t_group.group_id " +
            "where user_id = #{userID};")
    @Results({
            @Result(property = "groupID", column = "group_id"),
            //这里必须要手动映射，可能是因为有内连接的原因
            //如果不手动映射得到的group就是空的
    })
    List<Group> getGroups(Integer userID);

    @Select("SELECT * FROM t_user WHERE openid = #{openid} LIMIT 1")
    User selectByOpenId(@Param("openid") String openid);

    @Insert("insert into t_user(nickname, openid, session_key, portrait, gender, username, password, background, wx_union_id) " +
            "values (#{nickname},#{openid},#{sessionKey},#{portrait},#{gender},#{username},#{password},#{background},#{wxUnionID})")
    void insert(User user);
}
