package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.data.dao.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityCrudRepository extends CrudRepository<TestEntity, Long> {
}
