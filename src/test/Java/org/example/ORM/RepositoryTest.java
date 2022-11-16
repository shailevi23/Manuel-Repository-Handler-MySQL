package org.example.ORM;

import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    SqlConfig sqlConfig;
    Repository<User> userORM;
    Repository<Product> productORM;
    Repository<Shop> shopORM;
    @BeforeEach
    void createProduct() {
        this.sqlConfig = new SqlConfig("summery_project", "root", "root");
        this.userORM = new Repository<>(User.class, sqlConfig);
        this.productORM = new Repository<>(Product.class, sqlConfig);
        this.shopORM = new Repository<>(Shop.class, sqlConfig);
    }


    @Test
    void createUserTableTest_tableExists_throwsException() {
        assertThrows(RuntimeException.class, () -> userORM.createTable(), "Expected RuntimeException and something else happened");
    }

    @Test
    void createProductTableTest_tableExists_throwsException() {
        assertThrows(RuntimeException.class, () -> productORM.createTable(), "Expected RuntimeException and something else happened");
    }
    @Test
    void createShopTableTest_tableExists_throwsException() {
        assertThrows(RuntimeException.class, () -> shopORM.createTable(), "Expected RuntimeException and something else happened");
    }

    @Test
    void addingUserToTable_userAddedSuccessfully_isEquals() {
//        User newUser = User.createUser("shai", "levi", "shai@gmail.com", 1);
//        User addedUser = userORM.add(User.createUser("shai", "levi", "shai@gmail.com", 1));
//        assertTrue(addedUser.equals(newUser));
    }
    @Test
    void addingProductToTable_productAddedSuccessfully_isEquals() {
//        Product newProd = Product.createProductWithoutId("shampoo", 25.3);
//        Product addedProd =  productORM.add(Product.createProductWithoutId("shampoo", 25.3));
//        assertEquals(newProd, addedProd);
    }

    @Test
    void addingAllUsersToTable_addedAllUserToTable_isEquals(){
        List<User> users = new ArrayList<>();
        users.add(User.createUser("shai", "levi", "shaasdfi@gmail.com", 1));
        users.add(User.createUser("omar", "hamdea", "omarasdf@gmail.com", 2));
        users.add(User.createUser("rany", "saliman", "ranyfdas@gmail.com", 3));
        assertNotNull(userORM.addAll(users));
    }

    @Test
    void deletingUsersTable(){
        userORM.truncateTable();
        List<User> users = userORM.selectAll();
        assertEquals(users.size(), 0);
    }

    @Test
    void deletingProductsTable(){
        productORM.truncateTable();
        List<Product> prod = productORM.selectAll();
        assertEquals(prod.size(), 0);
    }

    @Test
    void selectingAllUsersInTable(){
        List<User> users = userORM.selectAll();
        assertNotNull(users);
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