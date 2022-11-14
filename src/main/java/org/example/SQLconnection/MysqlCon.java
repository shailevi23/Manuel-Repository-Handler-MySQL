package org.example.SQLconnection;

import org.example.ORM.Repository;
import org.example.SqlConfig.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlCon<T> {

    public static void main(String arg[]) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //user input for: dbName, user, password
        SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

//        Repository<User> userORM = new Repository<>(User.class);
//        userORM.createTable(sqlConfig);
//
//        Repository<Product> productORM = new Repository<>(Product.class);
//        productORM.createTable(sqlConfig);
//
//        Repository<Shop> shopORM = new Repository<>(Shop.class);
//        shopORM.createTable(sqlConfig);

        //Delete User Table (Truncate)
//        Repository<Shop> shopORM = new Repository<>(Shop.class);
//        shopORM.deleteTable(sqlConfig);

        //Delete user by id
        Repository<User> userORM = new Repository<>(User.class);
        userORM.deleteItemsByProperty("id", 50, sqlConfig);

    }
}
