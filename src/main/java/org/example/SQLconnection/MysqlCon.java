package org.example.SQLconnection;

import org.example.MethodsClass;
import org.example.exampleClasses.User;

import java.lang.reflect.Field;
import java.sql.*;

public class MysqlCon<T> {

    public static void main(String arg[]) throws SQLException {

        MethodsClass<User> methodsClass = new MethodsClass<>();
        Connection connection = methodsClass.connect();
        methodsClass.executeQuary(methodsClass.createTableByEntity(User.class).toString(), connection);
        connection.close();
    }
}

