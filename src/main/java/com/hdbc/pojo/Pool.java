package com.hdbc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pool {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long userID;
    private String poolName;

    //item list
    private List<String> list;
}
