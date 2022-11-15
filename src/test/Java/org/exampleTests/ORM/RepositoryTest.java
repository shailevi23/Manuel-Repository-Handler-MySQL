package org.exampleTests.ORM;

import org.example.ORM.Repository;
import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

    //`       <---------------1--------------->   //create 3 tables - user, shop, product
    @Test
    void createUserTableTest() {
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        assertEquals(true,userORM.createTable());
    }

    @Test
    void createProductTableTest() {
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
        assertEquals(true,productORM.createTable());
    }
    @Test
    void createShopTableTest() {
        Repository<Shop> shopORM = new Repository<>(Shop.class, sqlConfig);
        assertEquals(true,shopORM.createTable());
    }

    @Test
    void addingUserToTable() {
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        User user;
        user = userORM.add(User.createUser("rany", "saliman", "ranytest@gmail.com", 5));
        assertEquals("rany", user.getFirstName());
    }

    @Test
    void deleteTable() {
    }

    @Test
    void deleteItemsByProperty() {
    }

    @Test
    void selectAll() {
    }

    @Test
    void add() {
    }

    @Test
    void addAll() {
    }

    @Test
    void selectById() {
    }

    @Test
    void update() {
    }
}