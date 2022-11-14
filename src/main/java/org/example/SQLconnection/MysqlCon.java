package org.example.SQLconnection;

import org.example.MethodsClass;
import org.example.ORM.Repository;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;

import java.sql.*;

public class MysqlCon<T> {

    public static void main(String arg[]) throws SQLException {

        Repository<User> userORM = new Repository<>(User.class);
        Connection connection = userORM.connect();
        userORM.execute(userORM.createTableByEntity(User.class).toString(), connection);


        Repository<Product> productORM = new Repository<>(Product.class);
        productORM.execute(productORM.createTableByEntity(Product.class).toString(), connection);

        Repository<Shop> shopORM = new Repository<>(Shop.class);
        shopORM.execute(shopORM.createTableByEntity(Shop.class).toString(), connection);

        connection.close();
    }
}

