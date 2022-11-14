package org.example.SQLconnection;

import org.example.SqlConfig.SqlConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectHandler {


    SqlConfig sqlConfig;

    public ConnectHandler(SqlConfig sqlConfig){
        this.sqlConfig = sqlConfig;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(sqlConfig.getDBurlAndName(), sqlConfig.getUser(), sqlConfig.getPassword());

        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public void close(Connection connect) throws SQLException {
        connect.close();
    }
}
