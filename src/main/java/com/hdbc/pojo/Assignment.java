package com.hdbc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    /** 用户ID */
    private Long userID ;
    /** 小组ID */
    private Integer groupID ;
    /** 任务ID */
    private Integer taskID ;
    /** 任务名 */
    private String taskName ;


}
