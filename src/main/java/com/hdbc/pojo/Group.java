package com.hdbc.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Group {
    private Integer groupID ;
    /** 小组名 */
    private String groupName ;
    /** 创建时间 */
    private LocalDateTime createTime ;
    /** 更新时间 */
    private LocalDateTime updateTime ;

    public Group()
    {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    public Group(Integer groupID, String groupName, LocalDateTime createTime, LocalDateTime updateTime)
    {
        this.groupID = groupID;
        this.groupName = groupName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Group(Integer groupID, String groupName)
    {
        this.groupID = groupID;
        this.groupName = groupName;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Group(String groupName)
    {
        this.groupName = groupName;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public String toString()
    {
        // 定义格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化为字符串
        String formattedCreateTime = createTime.format(formatter);

        String formattedUpdateTime = updateTime.format(formatter);

        return "groupID:"+groupID+", "+"groupName:"+groupName+", "
        + "createTime:"+formattedCreateTime+", "+"updateTime:"+formattedUpdateTime;
    }
}
