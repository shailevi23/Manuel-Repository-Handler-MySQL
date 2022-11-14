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
        SqlConfig sqlConfig = new SqlConfig("summery_project", "root", "omar135790864");

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + sqlConfig.getDbName(), sqlConfig.getUser(), sqlConfig.getPassword());

        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        Repository<User> userORM = new Repository<>(User.class, connection);
        userORM.connect("root", "omar135790864");
//        userORM.createTable();
//
//        User user1 = new User(112,"omar", "a");
//        User user2 = new User(113,"shay", "b");
//
//        List<User> userList = new ArrayList<>();
//        userList.add(user1);
//        userList.add(user2);
//        userORM.addAll(userList);

        List<User> userList = userORM.selectById(112);
        for(User u : userList) {
            System.out.println(u);
        }


//
//        Repository<Product> productORM = new Repository<>(Product.class, connection);
//        productORM.createTable();


//        Repository<Product> productORM = new Repository<>(Product.class, connection);
//        productORM.createTable();
//
//        Repository<Shop> shopORM = new Repository<>(Shop.class, connection);
//        shopORM.createTable();

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
