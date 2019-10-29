package com.example.wanwuhan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;


@SpringBootApplication(scanBasePackages = "com.example.wanwuhan")
@MapperScan("com.example.wanwuhan.mapper") //设置mapper接口的扫描包
@EntityScan("com.example.wanwuhan.pojo")
@EnableJpaRepositories("com.example.wanwuhan.dao")
public class WanwuhanApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WanwuhanApplication.class, args);
    }
    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
