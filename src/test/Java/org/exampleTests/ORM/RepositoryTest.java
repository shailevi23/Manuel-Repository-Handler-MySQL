package org.exampleTests.ORM;

import org.example.ORM.Repository;
import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        boolean addRes;
        addRes = userORM.add(User.createUser("shai", "levi", "shai@gmail.com", 1));
        assertEquals(true, addRes);
    }
    @Test
    void addingProductToTable() {
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
        boolean addRes;
        addRes = productORM.add(Product.createProductWithoutId("shampoo", 25.3));
        assertEquals(true, addRes);
    }

    @Test
    void addingAllUsersToTable(){
        List<User> users = new ArrayList<>();
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        users.add(User.createUser("shai", "levi", "shaasdfi@gmail.com", 1));
        users.add(User.createUser("omar", "hamdea", "omarasdf@gmail.com", 2));
        users.add(User.createUser("rany", "saliman", "ranyfdas@gmail.com", 3));
        assertEquals(true, userORM.addAll(users));
    }

    @Test
    void deletingUsersTable(){
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        assertEquals(true, userORM.deleteTable());
    }

    @Test
    void deletingProductsTable(){
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
        assertEquals(true, productORM.deleteTable());
    }

    @Test
    void deletingShopTable(){
        Repository<Shop> shopORM = new Repository<>(Shop.class, sqlConfig);
        assertEquals(true, shopORM.deleteTable());
    }

    @Test
    void selectingAllUsersInTable(){
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        List<User> users = userORM.selectAll();
    }

    @Test
    void selectingAllProductsInTable(){
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
        List<Product> products = productORM.selectAll();
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