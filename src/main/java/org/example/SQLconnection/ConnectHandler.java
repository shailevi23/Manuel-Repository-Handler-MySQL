package org.example.SQLconnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ORM.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectHandler {

    private static Logger logger = LogManager.getLogger(ConnectHandler.class.getName());

    private ConnectHandler(){

    }


    public static Connection connect( SqlConfig sqlConfig) {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(sqlConfig.getDBurlAndName(), sqlConfig.getUser(), sqlConfig.getPassword());

        } catch(ClassNotFoundException | SQLException e) {
            logger.error("couldn't open connection");
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void closeConnection(Connection c) {
        logger.info("closing connection");
        try {
            c.close();
        } catch(SQLException e) {
            logger.error("couldn't close connection");
            throw new RuntimeException(e);
        }
    }
}
