package com.farsunset.boot.dds.jpa.holder;

import com.farsunset.boot.dds.jpa.properties.HibernateJpaDDLProperties;
import com.farsunset.boot.dds.jpa.provider.DataSourceProvider;
import com.farsunset.boot.dds.jpa.repository.EntityManagerRepository;
import com.farsunset.boot.dds.jpa.text.Text;
import com.farsunset.boot.dds.jpa.model.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DynamicDataSourceHolder implements ResourceLoaderAware {


    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ObjectProvider<EntityPathResolver> resolver;

    @Resource
    private HibernateProperties hibernateProperties;

    @Resource
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    @Resource
    private JpaProperties jpaProperties;

    @Resource
    private HibernateJpaDDLProperties hibernateJpaDDLProperties;


    @Resource
    private DataSourceCache dataSourceCache;

    private HashSet<Class> jpaClassSet = new HashSet<>();

    private HashSet<Class> entityManagerClassSet = new HashSet<>();


    public void init() throws IllegalAccessException, InstantiationException {

        DynamicRepositoryHolder.setApplicationContext(applicationContext);

        DataSourceProvider provider = hibernateJpaDDLProperties.getDataSourceProvider().newInstance();

        dataSourceCache.setDataSourceList(provider.getDataSource(applicationContext));

        dataSourceCache.getDataSourceList().forEach(this::resolveDataSource);
    }

    public void add(DataSource source){

        dataSourceCache.add(source);
        resolveDataSource(source);
    }

    public void resolveDataSource(DataSource source){

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = createEntityManagerFactoryBean(source);
        resolvePlatformTransactionManager(entityManagerFactoryBean,source.getKey());

        resolveJpaRepository(source,entityManagerFactoryBean);
        resolveEntityManagerRepository(source,entityManagerFactoryBean);
    }

    private void resolveJpaRepository(DataSource source , LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){

        for (Class aClass : jpaClassSet) {

            JpaRepositoryFactoryBean bean = new JpaRepositoryFactoryBean(aClass);
            bean.setTransactionManager(Text.getTransactionManager(source.getKey()));
            bean.setEntityManager(entityManagerFactoryBean.getObject().createEntityManager());
            bean.setBeanClassLoader(this.getClass().getClassLoader());
            bean.setEntityPathResolver(resolver);
            bean.afterPropertiesSet();

            String beanName =  Text.getRepositoryName(source.getKey(),aClass);

            registerBean(beanName,bean);

        }
    }

    private String resolvePlatformTransactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean,int key){

        PlatformTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactoryBean.getObject());

        String beanName = Text.getTransactionManager(key);

        registerBean(beanName,transactionManager);

        return beanName;
    }

    private void resolveEntityManagerRepository(DataSource source , LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){

        for (Class aClass : entityManagerClassSet) {
            try {
                Constructor  constructor = aClass.getConstructor(EntityManagerFactory.class);

                Object bean = constructor.newInstance(entityManagerFactoryBean.getObject());

                String beanName =  Text.getRepositoryName(source.getKey(),aClass);

                registerBean(beanName,bean);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerBean(String beanName ,Object bean){

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

        if (beanFactory.containsBeanDefinition(beanName)){
            beanFactory.removeBeanDefinition(beanName);
            beanFactory.destroySingleton(beanName);
        }

        beanFactory.registerSingleton(beanName, bean);

    }

    private LocalContainerEntityManagerFactoryBean createEntityManagerFactoryBean(DataSource source){

        String database = getDatabaseName(source.getUrl());

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setConnectionTestQuery(source.getConnectionTestQuery());
        hikariDataSource.setMinimumIdle(source.getMinIdle());
        hikariDataSource.setUsername(source.getUsername());
        hikariDataSource.setPassword(source.getPassword());
        hikariDataSource.setJdbcUrl(source.getUrl());
        hikariDataSource.setDriverClassName(hibernateJpaDDLProperties.getDriverClassName());
        hikariDataSource.setMaximumPoolSize(source.getMaxActive());
        hikariDataSource.setPoolName(database);
        hikariDataSource.setConnectionTimeout(30000);
        hikariDataSource.setIdleTimeout(300000);
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = entityManagerFactoryBuilder
                .dataSource(hikariDataSource)
                .properties(properties)
                .packages(hibernateJpaDDLProperties.getEntityPackage())
                .persistenceUnit(database)
                .build();

        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;

    }


    private String getDatabaseName(String url){
        return  url.substring(url.lastIndexOf('/') + 1).split("[?]")[0];
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        String path = hibernateJpaDDLProperties.getRepositoryPackage().replace('.','/');

        String classPath = String.format("classpath*:%s/**/*.class",path);

        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        try {
            org.springframework.core.io.Resource[] resources = resolver.getResources(classPath);
            for (org.springframework.core.io.Resource r : resources) {
                MetadataReader reader = metaReader.getMetadataReader(r);
                Class tClass = Class.forName(reader.getClassMetadata().getClassName());
                if (tClass.isInterface() && isJpaRepositoryClass(tClass)){
                    jpaClassSet.add(tClass);
                }
                if (!tClass.isInterface() && tClass.getSuperclass() == EntityManagerRepository.class){
                    entityManagerClassSet.add(tClass);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private boolean isJpaRepositoryClass(Class tClass){

        List<Class> interfaceList = Arrays.asList(tClass.getInterfaces());

        return interfaceList.contains(JpaRepository.class)
                || interfaceList.contains(CrudRepository.class)
                || interfaceList.contains(PagingAndSortingRepository.class);

    }


}
