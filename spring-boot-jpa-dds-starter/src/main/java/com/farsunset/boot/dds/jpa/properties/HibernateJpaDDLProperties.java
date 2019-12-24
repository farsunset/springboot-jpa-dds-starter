package com.farsunset.boot.dds.jpa.properties;

import com.farsunset.boot.dds.jpa.provider.DataSourceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jpa.ddl.hibernate")
public class HibernateJpaDDLProperties {

    private boolean enable;
    private String[] entityPackage;
    private String repositoryPackage;
    private Class<DataSourceProvider> dataSourceProvider;
    private String driverClassName;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String[] getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String[] entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getRepositoryPackage() {
        return repositoryPackage;
    }

    public void setRepositoryPackage(String repositoryPackage) {
        this.repositoryPackage = repositoryPackage;
    }

    public Class<DataSourceProvider> getDataSourceProvider() {
        return dataSourceProvider;
    }

    public void setDataSourceProvider(Class<DataSourceProvider> dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
