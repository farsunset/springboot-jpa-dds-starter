
package com.farsunset.dds.example.mvc.controller;


import com.farsunset.dds.example.model.Rule;
import com.farsunset.dds.example.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/rule",produces = "application/json")
public class RuleController {

    @Resource
    private RuleService ruleService;


    @GetMapping(value = "/list")
    public ResponseEntity<List<Rule>> list() {

        return ResponseEntity.ok(ruleService.findList());
    }



}
