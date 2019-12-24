package com.farsunset.dds.example.service.impl;

import com.farsunset.boot.dds.jpa.holder.DynamicRepositoryHolder;
import com.farsunset.dds.example.model.Metric;
import com.farsunset.dds.example.repository.MetricRepository;
import com.farsunset.dds.example.service.MetricService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    private DynamicRepositoryHolder<MetricRepository> metricRepository = new DynamicRepositoryHolder<>(MetricRepository.class);


    @Transactional
    @Override
    public void delete(Integer id) {
        metricRepository.get().deleteById(id);
    }

    @Override
    public List<Metric> findList() {
        return metricRepository.get().findAll();
    }


}
