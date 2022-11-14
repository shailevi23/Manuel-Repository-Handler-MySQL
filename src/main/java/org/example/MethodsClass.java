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




    //ADD

    //Add a single item to a table
//    public <T> T addSingleItem(Class<T> item){
//
//    }

    //Add multiple items
//    public List<T> T addMultipleItems(List<T> item){
//
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


    //Multiple item deletion by any property (delete all users called x)
//    public void deleteManyItemsByAnyProperty(Object property){
//
//    }




//    Table creation
    public void tableCreation(String table, Map<String,Object> fields){

    }


//    Create a table based on an entity
    public StringBuilder createTableByEntity(Class<T> entity){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ");
        stringBuilder.append(entity.getSimpleName().toLowerCase());
        stringBuilder.append(" (\n");

        for (Field field : entity.getDeclaredFields()) {
            stringBuilder.append(field.getName());
            stringBuilder.append(" ");
            if(field.getType().getSimpleName().equals("int")) {
                stringBuilder.append("int(11)");
            }
            if(field.getType().getSimpleName().equals("String")){
                stringBuilder.append("varchar(255)");
            }
            if(field.getType().getSimpleName().equals("Double")){
                stringBuilder.append("double(5,3)");
            }
            if(field.getType().getSimpleName().equals("List")){
                stringBuilder.append("varchar(255)");
            }
            stringBuilder.append(",\n");
        }
        stringBuilder.replace(stringBuilder.toString().length() -2, stringBuilder.toString().length(), "\n);");
        System.out.println(stringBuilder);
        return stringBuilder;
    }

    public Connection connect(){
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/summery_project",
                    "root", "root");

        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        return connection;
    } // function ends

    public void execute(String quary, Connection connection) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        statement.execute(quary);

    }

    private String createFindQuery(String field, Class<Object> type) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }
}


