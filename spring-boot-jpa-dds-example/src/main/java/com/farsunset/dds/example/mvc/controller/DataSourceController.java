
package com.farsunset.dds.example.mvc.controller;


import com.farsunset.boot.dds.jpa.holder.DynamicDataSourceHolder;
import com.farsunset.boot.dds.jpa.model.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/datasource",produces = "application/json")
public class DataSourceController {


    @Resource
    private DynamicDataSourceHolder dataSourceHolder;


    @PostMapping(value = "/add")
    public ResponseEntity<String>  add(@RequestBody DataSource source) {
        dataSourceHolder.add(source);
        return ResponseEntity.ok("ok");
    }

}
