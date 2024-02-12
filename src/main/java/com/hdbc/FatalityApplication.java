package com.hdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

@SpringBootApplication
public class FatalityApplication {

    public static void main(String[] args) {
        SpringApplication.run(FatalityApplication.class, args);
    }

}
