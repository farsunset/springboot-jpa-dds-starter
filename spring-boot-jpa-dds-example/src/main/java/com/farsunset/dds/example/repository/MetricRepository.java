
package com.farsunset.dds.example.repository;

import com.farsunset.dds.example.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MetricRepository extends JpaRepository<Metric, Integer> {

}
