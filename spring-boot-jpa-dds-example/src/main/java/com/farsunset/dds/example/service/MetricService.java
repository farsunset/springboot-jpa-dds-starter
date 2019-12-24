package com.farsunset.dds.example.service;

import com.farsunset.dds.example.model.Metric;

import java.util.List;

/**
 * @author Administrator
 */
public interface MetricService {


    void delete(Integer id);



    List<Metric> findList();

}
