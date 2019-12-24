package com.farsunset.dds.example.service.impl;

import com.farsunset.boot.dds.jpa.holder.DynamicRepositoryHolder;
import com.farsunset.dds.example.model.Rule;
import com.farsunset.dds.example.repository.RuleRepository;
import com.farsunset.dds.example.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {


    private DynamicRepositoryHolder<RuleRepository> ruleRepository = new DynamicRepositoryHolder<>(RuleRepository.class);

    @Override
    public List<Rule> findList() {
        return ruleRepository.get().findAll();
    }
}
