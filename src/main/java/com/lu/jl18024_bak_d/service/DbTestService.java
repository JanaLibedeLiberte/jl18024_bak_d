package com.lu.jl18024_bak_d.service;

import com.lu.jl18024_bak_d.model.*;
import com.lu.jl18024_bak_d.repository.mongo.T1O1MongoRepo;
import com.lu.jl18024_bak_d.repository.mongo.T2O1MongoRepo;
import com.lu.jl18024_bak_d.repository.mongo.T3O1MongoRepo;
import com.lu.jl18024_bak_d.repository.sql.T1O1SqlRepo;
import com.lu.jl18024_bak_d.repository.sql.T2O1SqlRepo;
import com.lu.jl18024_bak_d.repository.sql.T3O1SqlRepo;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.LinkedList;

@Service
public class DbTestService {
    private static final String stringValue = "value";

    private final T1O1SqlRepo test1SqlRepo;
    private final T1O1MongoRepo test1MongoRepo;
    private final T2O1SqlRepo test2SqlRepo;
    private final T2O1MongoRepo test2MongoRepo;
    private final T3O1SqlRepo test3SqlRepo;
    private final T3O1MongoRepo test3MongoRepo;

    public DbTestService(T1O1SqlRepo test1SqlRepo, T1O1MongoRepo test1MongoRepo, T2O1SqlRepo test2SqlRepo, T2O1MongoRepo test2MongoRepo, T3O1SqlRepo test3SqlRepo, T3O1MongoRepo test3MongoRepo) {
        this.test1SqlRepo = test1SqlRepo;
        this.test1MongoRepo = test1MongoRepo;
        this.test2SqlRepo = test2SqlRepo;
        this.test2MongoRepo = test2MongoRepo;
        this.test3SqlRepo = test3SqlRepo;
        this.test3MongoRepo = test3MongoRepo;
    }

    public TestResponse test1(Integer count) {
        // Create list with some count of Test1Object1 instances with only 1 filled field
        LinkedList<Test1Object1> objectList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Test1Object1 objectInstance = new Test1Object1();
            String fieldName = "nullableField" + ((i%20) + 1);
            Field objectField = null;
            try {
                objectField = objectInstance.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            objectField.setAccessible(true);
            try {
                objectField.set(objectInstance, stringValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            objectList.add(objectInstance);
        }

        // At first make empty all the related tables (SQL)
        test1SqlRepo.deleteAll();

        // Store list to MariaDB database (SQL) and count time
        long startSqlInsertQuery = System.nanoTime();
        test1SqlRepo.saveAll(objectList);
        long endSqlInsertQuery = System.nanoTime();
        long sqlInsertTime = endSqlInsertQuery - startSqlInsertQuery;

        // Read list from MariaDB database (SQL) and count time
        long startSqlSelectQuery = System.nanoTime();
        Iterable<Test1Object1> listFromSqlDb = test1SqlRepo.findAll();
        long endSqlSelectQuery = System.nanoTime();
        long sqlSelectTime = endSqlSelectQuery - startSqlSelectQuery;

        // Clear also MongoDB collection
        test1MongoRepo.deleteAll();

        // Store list to MongoDB database and count time
        long startMongoInsertQuery = System.nanoTime();
        test1MongoRepo.saveAll(objectList);
        long endMongoInsertQuery = System.nanoTime();
        long mongoInsertTime = endMongoInsertQuery - startMongoInsertQuery;

        // Read list from MongoDB database and count time
        long startMongoSelectQuery = System.nanoTime();
        Iterable<Test1Object1> listFromMongoDb = test1MongoRepo.findAll();
        long endMongoSelectQuery = System.nanoTime();
        long mongoSelectTime = endMongoSelectQuery - startMongoSelectQuery;

        TestResponse response = new TestResponse();
        response.setSqlStoreTime(sqlInsertTime);
        response.setSqlReadTime(sqlSelectTime);
        response.setMongoStoreTime(mongoInsertTime);
        response.setMongoReadTime(mongoSelectTime);

        return response;
    }

    public TestResponse test2(Integer count) {
        // Create list with embedded objects on some deepness
        LinkedList<Test1Object1> objectList = new LinkedList<>();
        Test2Object1 rootObject = new Test2Object1();
        Test2Object1 actualLeaf = rootObject;
        for (int i = 0; i < count; i++) {
            Test2Object1 newLeaf = new Test2Object1();
            actualLeaf.setParent(newLeaf);
            actualLeaf = newLeaf;
        }

        // At first make empty all the related tables (SQL)
        test2SqlRepo.deleteAll();

        // Store object chain to MariaDB database (SQL) and count time
        long startSqlInsertQuery = System.nanoTime();
        test2SqlRepo.save(rootObject);
        long endSqlInsertQuery = System.nanoTime();
        long sqlInsertTime = endSqlInsertQuery - startSqlInsertQuery;

        // Read object from MariaDB database (SQL), list all object chain and count time
        long startSqlSelectQuery = System.nanoTime();
        Test2Object1 SqlReadObject = test2SqlRepo.findById(rootObject.getId()).get();
        while(SqlReadObject.getParent() != null) {
            SqlReadObject = SqlReadObject.getParent();
        }
        long endSqlSelectQuery = System.nanoTime();
        long sqlSelectTime = endSqlSelectQuery - startSqlSelectQuery;

        // Clear also MongoDB collection
        test2MongoRepo.deleteAll();

        // Store object chain to MongoDB database and count time
        long startMongoInsertQuery = System.nanoTime();
        test2MongoRepo.save(rootObject);
        long endMongoInsertQuery = System.nanoTime();
        long mongoInsertTime = endMongoInsertQuery - startMongoInsertQuery;

        // Read object from MongoDB database, list all object chain and count time
        long startMongoSelectQuery = System.nanoTime();
        Test2Object1 mongoReadObject = test2MongoRepo.findById(rootObject.getId()).get();
        while(mongoReadObject.getParent() != null) {
            mongoReadObject = mongoReadObject.getParent();
        }
        long endMongoSelectQuery = System.nanoTime();
        long mongoSelectTime = endMongoSelectQuery - startMongoSelectQuery;

        TestResponse response = new TestResponse();
        response.setSqlStoreTime(sqlInsertTime);
        response.setSqlReadTime(sqlSelectTime);
        response.setMongoStoreTime(mongoInsertTime);
        response.setMongoReadTime(mongoSelectTime);

        return response;
    }

    public TestResponse test3(Integer count) {
        // Create list with some count of Test3Object1 instances with partially filled embedded object fields
        LinkedList<Test3Object1> objectList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Test3Object1 objectInstance = new Test3Object1();
            Test3Object2 object2Instance = new Test3Object2();
            Test3Object3 object3Instance = new Test3Object3();
            if (count % 4 > 0) {
                object2Instance.setField1(stringValue);
                object2Instance.setField2(stringValue);
                object2Instance.setField3(stringValue);
                object3Instance.setField3(stringValue);
            }
            if (count % 3 > 0) {
                object3Instance.setField1(stringValue);
                object3Instance.setField2(stringValue);
                object2Instance.setField4(stringValue);
            }
            objectInstance.setField1(object2Instance);
            objectInstance.setField2(object3Instance);
            objectList.add(objectInstance);
        }

        // At first make empty all the related tables (SQL)
        test3SqlRepo.deleteAll();

        // Store list to MariaDB database (SQL) and count time
        long startSqlInsertQuery = System.nanoTime();
        test3SqlRepo.saveAll(objectList);
        long endSqlInsertQuery = System.nanoTime();
        long sqlInsertTime = endSqlInsertQuery - startSqlInsertQuery;

        // Read list from MariaDB database (SQL) and count time
        long startSqlSelectQuery = System.nanoTime();
        Iterable<Test3Object1> listFromSqlDb = test3SqlRepo.findAll();
        long endSqlSelectQuery = System.nanoTime();
        long sqlSelectTime = endSqlSelectQuery - startSqlSelectQuery;

        // Clear also MongoDB collection
        test3MongoRepo.deleteAll();

        // Store list to MongoDB database and count time
        long startMongoInsertQuery = System.nanoTime();
        test3MongoRepo.saveAll(objectList);
        long endMongoInsertQuery = System.nanoTime();
        long mongoInsertTime = endMongoInsertQuery - startMongoInsertQuery;

        // Read list from MongoDB database and count time
        long startMongoSelectQuery = System.nanoTime();
        Iterable<Test3Object1> listFromMongoDb = test3MongoRepo.findAll();
        long endMongoSelectQuery = System.nanoTime();
        long mongoSelectTime = endMongoSelectQuery - startMongoSelectQuery;

        TestResponse response = new TestResponse();
        response.setSqlStoreTime(sqlInsertTime);
        response.setSqlReadTime(sqlSelectTime);
        response.setMongoStoreTime(mongoInsertTime);
        response.setMongoReadTime(mongoSelectTime);

        return response;
    }

    public void test1clear() {
        test1SqlRepo.deleteAll();
        test1MongoRepo.deleteAll();
    }

    public void test2clear() {
        test2SqlRepo.deleteAll();
        test2MongoRepo.deleteAll();
    }

    public void test3clear() {
        test3SqlRepo.deleteAll();
        test3MongoRepo.deleteAll();
    }
}
