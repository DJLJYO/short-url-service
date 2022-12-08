package com.fasturl.shorturlservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author quanyi
 * @since 2022-12-8
 */
@SpringBootApplication
@MapperScan("com.fasturl.shorturlservice.mapper")
public class ShorturlserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorturlserviceApplication.class, args);
    }

}
