package com.hdbc.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FeedBackMapper {

    @Insert("insert into t_feedback(user_id, feedback_text) " +
            " values (#{userID},#{feedbackText})")
    void insertFeedBack(Integer userID, String feedbackText);
}
