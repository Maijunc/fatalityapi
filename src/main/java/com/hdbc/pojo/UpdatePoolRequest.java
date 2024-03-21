package com.hdbc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePoolRequest {
    private Long userID;
    private String poolName;
    private List<String> newItems;
    private List<String> deleteItems;

}
