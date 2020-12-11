package com.dbDesign.Jacky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName DBDesignApplication
 * @Author Jacky
 * @Description 应用启动类
 **/
@ServletComponentScan   // 开启Filter的支持
@EnableTransactionManagement//启动事务
@MapperScan("com.dbDesign.Jacky.mapper")
@SpringBootApplication()
public class DBDesignApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DBDesignApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
