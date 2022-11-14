package org.example.SQLconnection;

import java.sql.*;

public class MysqlCon {

    public static void main(String arg[])
    {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/summeryproject",
                    "root", "root");


            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                    "SELECT * FROM test_table;");
            int code;
            String title;
            while (resultSet.next()) {
                code = resultSet.getInt("id");
                title = resultSet.getString("name").trim();
                System.out.println("Id : " + code
                        + " name : " + title);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    } // function ends
    }

