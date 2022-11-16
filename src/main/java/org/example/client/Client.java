package org.example.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ORM.Repository;
import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private static Logger logger = LogManager.getLogger(Client.class.getName());
    public static void main(String arg[]) {
        SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

        //TODO working
//       <---------------1--------------->   //create 3 tables - user, shop, product
        Repository<User> userORM = new Repository<>(User.class, sqlConfig);
        Repository<Product> productORM = new Repository<>(Product.class, sqlConfig);
        Repository<Shop> shopORM = new Repository<>(Shop.class, sqlConfig);

//        boolean res;
//        res = userORM.createTable();
//        logger.info("Is User table has been created? " + res);

//        res = productORM.createTable();
//        logger.info("Is User table has been created? " + res);
//
//        res = shopORM.createTable();
//        logger.info("Is User table has been created? " + res);
//        <---------------1--------------->


        //TODO working
//        <---------------2--------------->   //Insert 3 users to user table
//        User user;
//        user = userORM.add(User.createUserWithoutId("mdfss", "assmfdsa", "asadsmfasd@gmail.com"));
//        logger.info("User has been created: " + user.toString());
//        user = userORM.add(User.createUser("omar", "hamdea", "omar@gmail.com", 2));
//        logger.info("User has been created: " + user.toString());
//        user = userORM.add(User.createUserWithoutId("rany", "saliman", "rany@gmail.com"));
//        logger.info("User has been created: " + user.toString());
//

//        boolean addRes;
//        addRes = productORM.add(Product.createProductWithoutId("shampoo", 25.3));
//        logger.info("Product has been created: " + addRes);
//        addRes = productORM.add(Product.createProduct("table", 250.6, 2));
//        logger.info("Product has been created: " + prod.toString());
//        addRes = productORM.add(Product.createProduct("pen", 12.3, 3));
//        logger.info("Product has been created: " + prod.toString());

//        addRes = productORM.add(Product.createProductWithoutId("new", 23.3));
//        logger.info("Product has been created: " + prod.toString());
//        ArrayList<Product> prodList = new ArrayList<>();
//        prodList.add(Product.createProductWithoutId("new1", 2.0));
//        prodList.add(Product.createProductWithoutId("new2", 19.3));
//        prodList.add(Product.createProductWithoutId("new3", 10.3));
//        shopORM.add(Shop.createShopWithoutId("name", prodList));
//        <---------------2--------------->


        //TODO working
//        <---------------3--------------->   //Delete users with deleteMany but use id
//         ArrayList<Product> prodList = new ArrayList<>();
//        prodList.add(Product.createProductWithoutId("new1", 2.0));
//        prodList.add(Product.createProductWithoutId("new2", 19.3));
//        prodList.add(Product.createProductWithoutId("new3", 10.3));
//        shopORM.deleteItemsByProperty("productList", prodList);

//        userORM.deleteItemsByProperty("id", 2);
//        <---------------3--------------->

//        <---------------3.5------------->   //Delete user
//        User user;
//        user = userORM.add(User.createUserWithoutId("mdsss", "asssfdsa", "assasmfasd@gmail.com"));
//        userORM.deleteEntireObject(user);
//        <---------------3.5------------->


        //TODO working
//        <---------------4--------------->   //Add all users
//        List<User> users = new ArrayList<>();
//        users.add(User.createUserWithoutId("shai", "levi", "shai213@gmail.com"));
//        users.add(User.createUserWithoutId("omar", "hamdea", "omar123@gmail.com"));
//        users.add(User.createUserWithoutId("rany", "saliman", "rany123@gmail.com"));
//        List<User> res = userORM.addAll(users);
//
//        for(User u : res) {
//            System.out.println(u);
//        }
//
//        logger.info("Users has been created: " + res);

//        <---------------4--------------->


        //TODO working
//        <---------------5--------------->   //Select all users
//        List<User> users = userORM.selectAll();
//        for(User u : users) {
//            System.out.println(u);
//        }
//        logger.info("Users has been selected: " + users.toString());

//        List<Shop> shop = shopORM.selectAll();
//        logger.info("Shop has been selected: " + shop.toString());
//        <---------------5--------------->


        //TODO working
//        <---------------6--------------->   //select by Id
//        List<User> userList = userORM.selectById(1);
//        for(User u : userList) {
//            logger.info("User has been selected: " + u);
//        }
//
//        List<Product> prod = productORM.selectById(9);
//        for(Product u : prod) {
//            logger.info("User has been selected: " + u);
//        }
//        <---------------6--------------->


        //TODO working
//        <---------------7--------------->   //Update Entire Entity
//        User userAdd = userORM.add(User.createUserWithoutId("haland", "thebest", "haland@gmail.com"));
//        userAdd.setLastName("hamdea");
//        User updatedUser = userORM.update(userAdd);
//        logger.info("User has been updated: " + updatedUser.toString());

//        ArrayList<Product> prodList = new ArrayList<>();
//        prodList.add(Product.createProductWithoutId("new1", 2.0));
//        prodList.add(Product.createProductWithoutId("new2", 19.3));
//        prodList.add(Product.createProductWithoutId("new3", 10.3));
//        Shop shop = shopORM.add(Shop.createShopWithoutId("supername", prodList));
//        prodList.add(Product.createProductWithoutId("new4", 15.3));
//        shop.setProductList(prodList);
//        shopORM.update(shop);
//        shopORM.deleteEntireObject(shop);

//        <---------------7--------------->


        //TODO working
//        <---------------7--------------->
//        List<User> users = userORM.updateByProperty("firstName", "hmdea", "lastName", "hamdea");
//        for(User u : users) {
//            System.out.println(u.toString());
//        }
//        <---------------7--------------->

        //TODO working
//        <---------------8--------------->   //Delete User Table (Truncate)
//        boolean res = userORM.truncateTable();
//        logger.info("User table has been truncated: " + res);
//        <---------------8--------------->



//        Map<String, Object> fieldsToUpdate = new HashMap<>();
//        Map<String, Object> filterFields = new HashMap<>();
//
//        fieldsToUpdate.put("email","stam@gmail.com");
//        filterFields.put("firstName","omar");
//
//
//        List<User> userList = userORM.update(fieldsToUpdate, filterFields);
//        for(User u : userList) {
//            logger.info("User has been selected: " + u);
//        }

    }
}
