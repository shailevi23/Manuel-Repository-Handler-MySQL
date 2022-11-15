package org.exampleTests.ORM;

import org.example.ORM.Repository;
import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepoLogicTest {

    SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

    //`       <---------------1--------------->   //create 3 tables - user, shop, product
    Repository<User> userORM = new Repository<>(User.class, sqlConfig);
    Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
    Repository<Shop> shopORM = new Repository<>(Shop.class, sqlConfig);


    public void selectAllTest() {
        List<User> users = userORM.selectAll();
        System.out.println("Users has been selected: " + users.toString());
    }
    @Test
    void createSelectAllQueryLogic() {
    }

    @Test
    void createSelectByFieldQuery() {
    }

    @Test
    void findObj() {
    }

    @Test
    void createAddQueryLogic() {
    }

    @Test
    void checkInstanceOfFieldsAndAppendObjectToJson() {
    }

    @Test
    void createTableQueryLogic() {
    }

    @Test
    void deleteTableQueryLogic() {
    }

    @Test
    void deleteSingleItemByAnyPropertyLogic() {
    }

    @Test
    void deleteManyItemsByAnyPropertyQueryLogic() {
    }

    @Test
    void createUpdateQueryLogic() {
    }

    @Test
    void mapInit() {
    }
}