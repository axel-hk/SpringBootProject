package com.example.mybookshopapp.config;

import com.example.mybookshopapp.data.dao.TestEntity;
import com.example.mybookshopapp.data.repositories.TestEntityCrudRepository;
import com.example.mybookshopapp.data.dao.TestEntityDao;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerIml implements CommandLineRunner {

    EntityManagerFactory entityManagerFactory;
    TestEntityDao testEntityDao;

    TestEntityCrudRepository testEntityCrudRepository;

    @Autowired
    public CommandLineRunnerIml(EntityManagerFactory entityManagerFactory, TestEntityDao testEntityDao,
                                TestEntityCrudRepository testEntityCrudRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.testEntityDao = testEntityDao;
        this.testEntityCrudRepository = testEntityCrudRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i = 0; i < 5; i++) {
            createTestEntity(new TestEntity());
        }
        TestEntity readTestEntity = readTestEntityById(3L);
        if(readTestEntity!= null) {
            Logger.getLogger(CommandLineRunnerIml.class.getSimpleName()).info(readTestEntity.toString());
        }
        else throw new NullPointerException();

        TestEntity updatedTestEntity = updateTestEntityById(5L);
        if(updatedTestEntity!= null) {
            Logger.getLogger(CommandLineRunnerIml.class.getSimpleName()).info(updatedTestEntity.toString());
        }
        else throw new NullPointerException();

        //deleteTestEntityById(4L);

    }

    private void deleteTestEntityById(long id) {
        testEntityCrudRepository.delete(readTestEntityById(id));
        /*Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            TestEntity testEntity =readTestEntityById(id);
            TestEntity mergeTestEntity = (TestEntity) session.merge(testEntity);
            session.remove(mergeTestEntity);
            tx.commit();
        }catch (HibernateException hex){
            if (tx!= null){
                tx.rollback();
            }else{
                hex.printStackTrace();
            }
        }*/
    }

    private TestEntity updateTestEntityById(long id) {
        TestEntity result = testEntityCrudRepository.findById(id).get();
        result.setData("NEW TEST DATA");
        testEntityCrudRepository.save(result);
        return result;

    }

    private TestEntity readTestEntityById(long id) {
        return testEntityCrudRepository.findById(id).get();
//        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
//        Transaction tx = null;
//        TestEntity result = null;
//
//        try {
//            tx = session.beginTransaction();
//            result = session.find(TestEntity.class, id);
//            tx.commit();
//        } catch (HibernateException hex){
//            if (tx!= null){
//                tx.rollback();
//            }else{
//                hex.printStackTrace();
//            }
//        }
//        return result;
    }

    private void createTestEntity(TestEntity testEntity) {
        testEntity.setData(testEntity.getClass().getSimpleName() + testEntity.hashCode());
        testEntityCrudRepository.save(testEntity);

        /*Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            testEntity.setData(testEntity.getClass().getSimpleName() + testEntity.hashCode());
            session.persist(testEntity);
            tx.commit();
        } catch (HibernateException hex){
            if (tx!= null){
                tx.rollback();
            }else{
                hex.printStackTrace();
            }
        }*/
    }
}
