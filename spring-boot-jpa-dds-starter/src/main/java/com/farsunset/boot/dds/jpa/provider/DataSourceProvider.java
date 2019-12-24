package com.farsunset.boot.dds.jpa.provider;

import com.farsunset.boot.dds.jpa.model.DataSource;
import org.springframework.context.ApplicationContext;

import java.util.List;

public interface DataSourceProvider {

     List<DataSource> getDataSource(ApplicationContext applicationContext);

}
