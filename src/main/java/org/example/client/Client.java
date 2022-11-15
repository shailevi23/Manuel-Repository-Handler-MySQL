package org.example.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ORM.RepoLogic;
import org.example.ORM.Repository;
import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static Logger logger = LogManager.getLogger(RepoLogic.class.getName());
    public static void main(String arg[]) {
        SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

//`       <---------------1--------------->   //create 3 tables - user, shop, product
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
        Repository<Shop> shopORM = new Repository<>(Shop.class, sqlConfig);


//        boolean res;
//        res = userORM.createTable();
//        logger.info("Is User table has been created? " + res);
//
//        res = productORM.createTable();
//        logger.info("Is User table has been created? " + res);
//
//        res = shopORM.createTable();
//        logger.info("Is User table has been created? " + res);
//        <---------------1--------------->



//        <---------------2--------------->   //Insert 3 users to user table
//        User user;
//        user = userORM.add(User.createUser("shai", "levi", "shai@gmail.com", 1));
//        logger.info("User has been created: " + user.toString());
//        user = userORM.add(User.createUser("omar", "hamdea", "omar@gmail.com", 2));
//        logger.info("User has been created: " + user.toString());
//        user = userORM.add(User.createUser("rany", "saliman", "rany@gmail.com", 3));
//        logger.info("User has been created: " + user.toString());


//        Product prod;
//        prod = productORM.add(Product.createProduct("shampoo", 25.3, 1));
//        logger.info("Product has been created: " + prod.toString());
//        prod = productORM.add(Product.createProduct("table", 250.6, 2));
//        logger.info("Product has been created: " + prod.toString());
//        prod = productORM.add(Product.createProduct("pen", 12.3, 3));
//        logger.info("Product has been created: " + prod.toString());
        //TODO - if the useremail ( UNIQUE ) already inside the DB -> throw exception
        //TODO - if 'id' is AutoIncrement let DB handle it
        //TODO - handle type's by MAP and handle JSON

//        <---------------2--------------->



//        <---------------3--------------->   //Delete users with deleteMany but use id
//        userORM.deleteItemsByProperty("id", 1);
//        userORM.deleteItemsByProperty("id", 2);
//        userORM.deleteItemsByProperty("id", 3);
//        <---------------3--------------->



//        <---------------4--------------->   //Add all users
//        List<User> users = new ArrayList<>();
//        users.add(User.createUser("shai", "levi", "shai@gmail.com", 1));
//        users.add(User.createUser("omar", "hamdea", "omar@gmail.com", 2));
//        users.add(User.createUser("rany", "saliman", "rany@gmail.com", 3));
//        List<User> getUsers = userORM.addAll(users);
//        logger.info("Users has been created: " + getUsers.toString());

//        <---------------4--------------->



//        <---------------5--------------->   //Select all users
//        List<User> users = userORM.selectAll();
//        logger.info("Users has been selected: " + users.toString());
//
        //TODO - return list of items and not result set by using reflection
//        <---------------5--------------->



//        <---------------6--------------->   //select by Id
//        List<User> userList = userORM.selectById(1);
//        for(User u : userList) {
//            logger.info("User has been selected: " + u);
//        }

//        <---------------6--------------->



//        <---------------7--------------->   //Update User
//        User user = User.createUser("omar", "hm", "omar@gmail.com", 2);
//        User updatedUser = userORM.update(user);
//        logger.info("User has been updated: " + updatedUser.toString());
//        <---------------7--------------->


//        <---------------7--------------->   //Delete User Table (Truncate)
        boolean res = userORM.deleteTable();
        logger.info("User table has been truncated: " + res);
//        <---------------7--------------->

    }
}
