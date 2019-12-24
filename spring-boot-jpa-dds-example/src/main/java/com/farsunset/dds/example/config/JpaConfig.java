package com.farsunset.dds.example.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
/*
这里需要指定公共数据的配置路径
公共数据的repository路径
使用该目录下的repository 不受动态路由的影响
 */
@EntityScan("com.farsunset.ddl.example.common.model")
@EnableJpaRepositories(basePackages = "com.farsunset.ddl.example.common.repository")
public class JpaConfig {

}