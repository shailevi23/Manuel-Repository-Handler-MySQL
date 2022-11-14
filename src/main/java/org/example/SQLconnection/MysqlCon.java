package org.example.SQLconnection;

import org.example.MethodsClass;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;

import java.sql.*;

public class MysqlCon<T> {

    public static void main(String arg[]) throws SQLException {

        MethodsClass<User> userORM = new MethodsClass<>();
        Connection connection = userORM.connect();
//        userORM.execute(userORM.createTableByEntity(User.class).toString(), connection);
//
//
//        MethodsClass<Product> productORM = new MethodsClass<>();
//        productORM.execute(productORM.createTableByEntity(Product.class).toString(), connection);

        MethodsClass<Shop> shopORM = new MethodsClass<>();
        shopORM.execute(shopORM.createTableByEntity(Shop.class).toString(), connection);

        connection.close();
    }
}

