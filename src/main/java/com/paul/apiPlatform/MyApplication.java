package com.paul.apiPlatform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.paul.apiPlatform.mapper")
@EnableDubbo
public class MyApplication {

    public static void main(String[] args) {
//        new EmbeddedZooKeeper(21812,false).start();
        SpringApplication.run(MyApplication.class, args);
    }

}
