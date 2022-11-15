package org.example.SQLconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectHandler {


    private ConnectHandler(){

    }


    public static Connection connect( SqlConfig sqlConfig) {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(sqlConfig.getDBurlAndName(), sqlConfig.getUser(), sqlConfig.getPassword());

        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void closeConnection(Connection c) {
        try {
            c.close();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
