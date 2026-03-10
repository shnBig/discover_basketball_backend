package com.gain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gain.mapper")
public class DiscoverBasketballApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoverBasketballApplication.class, args);
    }
}
