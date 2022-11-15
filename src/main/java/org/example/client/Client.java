package org.example.client;

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

    public static void main(String arg[]) {
        SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

//`       <---------------1--------------->   //create 3 tables - user, shop, product
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
//        userORM.createTable();
//
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
//        productORM.createTable();
//
        Repository<Shop> shopORM = new Repository<>(Shop.class, sqlConfig);
//        shopORM.createTable(sqlConfig);
//        <---------------1--------------->



//        <---------------2--------------->   //Insert 3 users to user table
//        userORM.add(User.createUser("shai", "levi", "shai@gmail.com", 1));
//        userORM.add(User.createUser("omar", "hamdea", "omar@gmail.com", 2) ,sqlConfig);
//        userORM.add(User.createUser("rany", "saliman", "rany@gmail.com", 3) ,sqlConfig);


        productORM.add(Product.createProduct("shampoo", 25.3, 1));
//        productORM.add(Product.createProduct("table", 250.6, 2) ,sqlConfig);
//        productORM.add(Product.createProduct("pen", 12.3, 3) ,sqlConfig);

        //TODO - if the useremail ( UNIQUE ) already inside the DB -> throw exception
        //TODO - if 'id' is AutoIncrement let DB handle it

//        <---------------2--------------->



//        <---------------3--------------->   //Delete users with deleteMany but use id
//        userORM.deleteItemsByProperty("id", 1, sqlConfig);
//        userORM.deleteItemsByProperty("id", 2, sqlConfig);
//        userORM.deleteItemsByProperty("id", 3, sqlConfig);
//        <---------------3--------------->



//        <---------------4--------------->   //Add all users
//        List<User> users = new ArrayList<>();
//        users.add(User.createUser("shai", "levi", "shai@gmail.com", 1));
//        users.add(User.createUser("omar", "hamdea", "omar@gmail.com", 2));
//        users.add(User.createUser("rany", "saliman", "rany@gmail.com", 3));
//        userORM.addAll(users, sqlConfig);
//        <---------------4--------------->



//        <---------------5--------------->   //Select all users
//        ResultSet rs = userORM.selectAll(sqlConfig);
//        System.out.println(rs);
//
        //TODO - return list of items and not result set by using reflection
//        <---------------5--------------->



//        <---------------6--------------->   //select by Id
//        List<User> userList = userORM.selectById(1);
//        for(User u : userList) {
//            System.out.println(u);
//        }

//        <---------------6--------------->



//        <---------------7--------------->   //Delete User Table (Truncate)
//        userORM.deleteTable(sqlConfig);
//        <---------------7--------------->


//        <---------------7--------------->   //Update User
//        User user = User.createUser("omar", "hm", "omar@gmail.com", 2);
//        User updatedUser = userORM.update(user);
//        System.out.println(updatedUser);
        //        <---------------7--------------->

    }
}
