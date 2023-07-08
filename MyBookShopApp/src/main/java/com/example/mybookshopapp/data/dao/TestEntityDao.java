package com.example.mybookshopapp.data.dao;

import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHiberneteDao<TestEntity> {
    public TestEntityDao() {
        super();
        setClazz(TestEntity.class);
    }
}
