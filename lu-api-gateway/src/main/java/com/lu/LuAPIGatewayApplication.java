package com.lu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 鹿又笑
 * @Create: 2024/7/2 10:04
 * @description:
 */
@SpringBootApplication
@EnableDubbo
public class LuAPIGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuAPIGatewayApplication.class, args);
    }

}