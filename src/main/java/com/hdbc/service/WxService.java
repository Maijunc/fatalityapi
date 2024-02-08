package com.hdbc.service;

public interface WxService {
    public String wxDecrypt(String encryptedData, String sessionId, String vi) throws Exception;

    public String getStringRandom(int length);

}
