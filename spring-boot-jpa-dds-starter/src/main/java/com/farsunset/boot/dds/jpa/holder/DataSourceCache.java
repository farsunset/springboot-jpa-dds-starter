package com.farsunset.boot.dds.jpa.holder;

import com.farsunset.boot.dds.jpa.model.DataSource;

import java.util.ArrayList;
import java.util.List;

public class DataSourceCache {

    private List<DataSource> dataSourceList;

    public List<DataSource> getDataSourceList() {
        return dataSourceList;
    }

    public void setDataSourceList(List<DataSource> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    public void add(DataSource source){
        dataSourceList.add(source);
    }

    public List<Integer> getSourceKeyList() {
        List<Integer> keyList = new ArrayList<>(dataSourceList.size());
        dataSourceList.forEach(source -> keyList.add(source.getKey()));
        return keyList;
    }

}

