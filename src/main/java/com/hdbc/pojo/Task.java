package com.hdbc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Task {
    private Integer taskID ;
    /** 任务名 */
    private String taskName ;
    /** 任务人数 */
    private Integer teamNumber ;
    /** 剩余任务人数 */
    private Integer leftNumber ;

    public Task(Integer taskID, String taskName, Integer teamNumber)
    {
        this.taskID = taskID;
        this.taskName = taskName;
        this.teamNumber = teamNumber;
        this.leftNumber = teamNumber;
    }

}
