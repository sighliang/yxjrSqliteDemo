package com.yxjr.yxjrDevOps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class YxjrDevOpsApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(YxjrDevOpsApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(YxjrDevOpsApplication.class, args);
    }

}
