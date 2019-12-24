
package com.farsunset.dds.example.mvc.controller;


import com.farsunset.dds.example.model.Metric;
import com.farsunset.dds.example.service.MetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/metric",produces = "application/json")
public class MetricController  {

    @Resource
    private MetricService metricService;


    @GetMapping(value = "/list")
    public ResponseEntity<List<Metric>> list() {


        return ResponseEntity.ok(metricService.findList());
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String>  delete(@PathVariable("id") int id) {

        metricService.delete(id);
        return ResponseEntity.ok("ok");
    }

}
