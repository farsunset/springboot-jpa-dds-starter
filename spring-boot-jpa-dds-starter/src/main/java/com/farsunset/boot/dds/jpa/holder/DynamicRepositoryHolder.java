package com.farsunset.boot.dds.jpa.holder;

import com.farsunset.boot.dds.jpa.text.Text;
import com.farsunset.boot.dds.jpa.thread.SourceThreadLocal;
import org.springframework.context.ApplicationContext;

public class DynamicRepositoryHolder<T> {

     private static ApplicationContext applicationContext;

     static void setApplicationContext(ApplicationContext applicationContext) {
          DynamicRepositoryHolder.applicationContext = applicationContext;
     }


     private Class<T> rawType;

     public DynamicRepositoryHolder(Class<T> rawType){
          this.rawType = rawType;
     }

     public T get(){

          String key = SourceThreadLocal.getSourceKey();

          String beanName =  Text.getRepositoryName(key,rawType);

          return applicationContext.getBean(beanName,rawType);
     }



}
