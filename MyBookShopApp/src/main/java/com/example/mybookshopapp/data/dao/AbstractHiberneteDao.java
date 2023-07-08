package com.example.mybookshopapp.data.dao;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractHiberneteDao<T> {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    private Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(Long id) {
        return getSession().get(clazz, id);
    }


    public Session getSession() {
        return entityManagerFactory.createEntityManager().unwrap(Session.class);
    }
}
