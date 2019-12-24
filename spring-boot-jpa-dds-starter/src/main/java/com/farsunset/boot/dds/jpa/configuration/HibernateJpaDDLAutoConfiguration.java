package com.farsunset.boot.dds.jpa.configuration;

import com.farsunset.boot.dds.jpa.holder.DataSourceCache;
import com.farsunset.boot.dds.jpa.holder.DynamicDataSourceHolder;
import com.farsunset.boot.dds.jpa.holder.DynamicTransactionManager;
import com.farsunset.boot.dds.jpa.properties.HibernateJpaDDLProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(value = HibernateJpaDDLProperties.class)
public class HibernateJpaDDLAutoConfiguration implements TransactionManagementConfigurer {

    @Resource
    private ApplicationContext applicationContext;


    @Bean
    public DataSourceCache dataSourceCache(){
        return new DataSourceCache();
    }

    @Bean(initMethod = "init")
    public DynamicDataSourceHolder dynamicDataSourceHolder(){
        return new DynamicDataSourceHolder();
    }


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DynamicTransactionManager(applicationContext);
    }

}
