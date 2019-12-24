package com.farsunset.boot.dds.jpa.repository;

import javax.persistence.EntityManagerFactory;

public class EntityManagerRepository {

    protected EntityManagerFactory entityManagerFactory;

    public EntityManagerRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;

    }

}
