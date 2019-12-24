package com.farsunset.dds.example.component;

import com.farsunset.boot.dds.jpa.model.DataSource;
import com.farsunset.boot.dds.jpa.provider.DataSourceProvider;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceProvider implements DataSourceProvider {

    @Override
    public List<DataSource> getDataSource(ApplicationContext applicationContext) {
        List<DataSource> dataSources = new ArrayList<>();
        /*
         @TODO
         在这里获取所有数据源，并且返回
         */
        return dataSources;
    }
}
