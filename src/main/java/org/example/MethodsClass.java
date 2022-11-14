package org.example;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;

public class MethodsClass<T>{

//    private final Class<T> clz;


//    public MethodsClass(Class<T> clz) {
//        this.clz = clz;
//    }


    //READ

public StringBuilder readAllItems(Class<T> entity) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("SELECT * FROM summery_project.");
    stringBuilder.append(entity.getSimpleName().toLowerCase());
    stringBuilder.append(";");
    System.out.println(stringBuilder);
    return stringBuilder;
//    Connection connection = null;
//    try {
//        // below two lines are used for connectivity.
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        connection = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/summeryproject",
//                "root", "root");
//
//
//        Statement statement;
//        statement = connection.createStatement();
//        ResultSet resultSet;
//        resultSet = statement.executeQuery(
//                "SELECT * FROM summerproject.test_table;");
//        int code;
//        String title;
//        while (resultSet.next()) {
//            code = resultSet.getInt("id");
//            title = resultSet.getString("name").trim();
//            System.out.println("Id : " + code
//                    + " name : " + title);
//        }
//        resultSet.close();
//        statement.close();
//        connection.close();
//    } catch (Exception exception) {
//        System.out.println(exception);
//    }



}

    //get all the items in a table
//    public List<T> allItemsInTable(){
//        return;
//    }

    //Get one item by id
//    public <T> T getItemById(Object id){
//        return;
//    }

    //Get one/many item by any property (get user(s) by name)
//    public <T> T getItemsByProperty(Object name){
//        return;
//    }



    //UPDATE

    //Update a single property of a single item (update email for user with id x)
//    public <T> T updateSinglePropertyOfSingleItem(Object property, Object id){
//
//    }




    //DELETE

    //Single item deletion by any property (delete user with email x)
//    public void deleteItemByAnyProperty(Object property){
//
//    }

}


