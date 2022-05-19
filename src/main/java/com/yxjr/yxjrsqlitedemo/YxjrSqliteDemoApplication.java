package com.yxjr.yxjrsqlitedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class YxjrSqliteDemoApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(YxjrSqliteDemoApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(YxjrSqliteDemoApplication.class, args);
    }

}
