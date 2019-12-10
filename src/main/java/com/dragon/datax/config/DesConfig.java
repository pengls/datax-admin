package com.dragon.datax.config;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DesConfig
 * @Author pengl
 * @Date 2019-12-09 15:06
 * @Description des加密/解密
 * @Version 1.0
 */
@Configuration
public class DesConfig {
    @Value("${encry.key}")
    private String encryKey;

    @Bean
    public DES getDes(){
        return SecureUtil.des(encryKey.getBytes());
    }
}
