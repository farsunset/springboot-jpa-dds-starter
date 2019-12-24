
package com.farsunset.dds.example.repository;

import com.farsunset.boot.dds.jpa.repository.EntityManagerRepository;
import com.farsunset.dds.example.model.Rule;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;


public class RuleRepository extends EntityManagerRepository {



    public RuleRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<Rule>  findAll() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query =  entityManager.createQuery("from Rule");
        List<Rule> dataList =  query.getResultList();

        entityManager.close();

        return dataList;
    }

}

