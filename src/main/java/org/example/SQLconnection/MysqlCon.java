package org.example.SQLconnection;

import org.example.ORM.Repository;
import org.example.SqlConfig.SqlConfig;
import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.example.exampleClasses.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlCon<T> {

    public static void main(String arg[]) throws SQLException {
        //user input for: dbName, user, password
        SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "root");

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + sqlConfig.getDbName(), sqlConfig.getUser(), sqlConfig.getPassword());

        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        Repository<User> userORM = new Repository<>(User.class, connection);
        userORM.connect(sqlConfig.getUser(), sqlConfig.getPassword());
        userORM.createTable();
        System.out.println(userORM.select(User.class).toString());

        Repository<Product> productORM = new Repository<>(Product.class, connection);
        productORM.createTable();

        Repository<Shop> shopORM = new Repository<>(Shop.class, connection);
        shopORM.createTable();

        //Delete User Table (Truncate)
//        Repository<User> userORM = new Repository<>(User.class);
//        Connection connection = userORM.connect();
//        userORM.execute(userORM.deleteTable(), connection);\

        //Delete user by id
//        Repository<User> userORM = new Repository<>(User.class);
//        Connection connection = userORM.connect();
//        userORM.execute(userORM.deleteManyItemsByAnyProperty("id", 50), connection);

        connection.close();
    }
}
