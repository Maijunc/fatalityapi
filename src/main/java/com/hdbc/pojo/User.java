package com.hdbc.pojo;

import lombok.Data;

@Data
public class User {
    private long userID;

    private String nickname;

    private String openid;

    private String sessionKey;

    private String portrait;

    private String gender;

    private String username;

    private String password;

    private String background;

    private String wxUnionID;
}
