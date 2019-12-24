# springboot-jpa-dds-starter

#### 介绍
基于springboot jpa 的saas业务动态数据源的实现，支持动态切换，增加，删除数据源.

#### 一般动态路由实现有2中形式
1 使用Spring框架的AbstractRoutingDataSource，这种方式适合静态路由数据。
  优点: 一个bean可以路由到多个数据源
  缺点：无法动态添加数据源
  
2 控制IOC容器，为每个数据源动态添加相应的Repository的相关bean。
  优点：可以动态添加数据源
  缺点：一个数据源对应一个bean，数据源过多会创建较多的bean，导致内存占用多
  
本例子使用的第2种技术方案，且基于jpa的实现，基于mybatis的方案后面有空再做吧


#### 如何设置多个数据源
参见application.properties配置
实现一个DataSourceProvider接口，返回获取到的多个数据源信息。

容器启动的时候，会做bean的生成装配
参见starter的DynamicDataSourceHolder类



#### 如何路由

当前线程的ThreadLocal中需要设置对应的数据源ID，一般是在请求的拦截器中获得放入SourceThreadLocal，参见例子的DataSourceInterceptor
其他情况，自由发挥。
本例子初衷是提供一种解决问题的思路，最好是通过代码掌握原理，做出最合适自己的一个组件。