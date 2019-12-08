package com.dragon.datax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName DataxAdminApplication
 * @Author pengl
 * @Date 2018/11/20 14:49
 * @Description SpringBoot启动类
 * @Version 1.0
 */
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class DataxAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataxAdminApplication.class, args);
    }
}
