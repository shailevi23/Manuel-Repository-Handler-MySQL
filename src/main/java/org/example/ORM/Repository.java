package org.example.ORM;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Repository<T> {
        private  Class<T> clz;
        static String summeryProjectScheme = "summery_project";

        public Repository(Class<T> clz) {
            this.clz = clz;
        }

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
                    "jdbc:mysql://localhost:3306/" + summeryProjectScheme,
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

    private String createFindQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(clz.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }


    //Single item deletion by any property (delete user with email x)
//    public void deleteItemByAnyProperty(Object property){
//
//    }


    //Multiple item deletion by any property (delete all users called x)
//    public void deleteManyItemsByAnyProperty(Object property){
//
//    }


    //Delete entire table (truncate)
    public String deleteTable(){
        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT * FROM ").append(clz.getSimpleName().toLowerCase()).append(";\n");
        sb.append("TRUNCATE " + "`").append(summeryProjectScheme).append("`.`").append(clz.getSimpleName().toLowerCase()).append("`;\n");
        System.out.println(sb.toString());
        return sb.toString();
    }
}
