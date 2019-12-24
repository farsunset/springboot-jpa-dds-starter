package com.farsunset.dds.example.config;

import com.farsunset.dds.example.mvc.interceptor.DataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    private DataSourceInterceptor dataSourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {



        registry.addInterceptor(dataSourceInterceptor).order(1)
                .addPathPatterns("/metric/**")
                .addPathPatterns("/rule/**");

    }

}