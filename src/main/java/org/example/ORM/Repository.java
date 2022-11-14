package org.example.ORM;

import org.example.SQLconnection.ConnectHandler;
import org.example.SqlConfig.SqlConfig;

import java.lang.reflect.Field;
import java.sql.*;

public class Repository<T> {

    private final Class<T> clz;

    public Repository(Class<T> clz) {
        this.clz = clz;
    }

    public void createTable(SqlConfig sqlConfig) {
        execute(createTableQuery(), sqlConfig);
    }

    public void deleteTable(SqlConfig sqlConfig) {
        execute(deleteTableQuery(), sqlConfig);
    }

    public void deleteItemsByProperty(Object property, Object value, SqlConfig sqlConfig) {
        execute(deleteManyItemsByAnyPropertyQuery(property, value), sqlConfig);
    }


    private String createTableQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ");
        stringBuilder.append(clz.getSimpleName().toLowerCase());
        stringBuilder.append(" (\n");

        for(Field field : clz.getDeclaredFields()) {
            stringBuilder.append(field.getName());
            stringBuilder.append(" ");
            stringBuilder.append(getMySQLDataType(field.getType().getSimpleName()));
            stringBuilder.append(",\n");
        }
        stringBuilder.replace(stringBuilder.toString().length() - 2, stringBuilder.toString().length(), "\n);");
        return stringBuilder.toString();
    }


    //@SqlConfigAnnotation
    private void execute(String query, SqlConfig sqlConfig) {
        ConnectHandler c = new ConnectHandler(sqlConfig);
        try(Connection connect = c.connect()){
            Statement statement = connect.createStatement();
            statement.execute(query);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
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


    private String getMySQLDataType(String javaType) {
        switch(javaType) {
            case "int":
            case "Integer":
                return "int(11)";
            case "long":
            case "Long":
                return "BIGINT(50)";
            case "float":
            case "Float":
                return "FLOAT(24)";
            case "double":
            case "Double":
                return "FLOAT(53)";
            case "boolean":
            case "Boolean":
                return "BOOLEAN";
            case "Date":
                return "DATE";
            default:
                return "varchar(255)";
        }
    }


    //Delete entire table (truncate)
    private String deleteTableQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("TRUNCATE TABLE ").append(clz.getSimpleName().toLowerCase()).append(";\n");
        System.out.println(sb.toString());
        return sb.toString();
    }

    //Single item deletion by any property (delete user with email x)
    private String deleteManyItemsByAnyPropertyQuery(Object property, Object value){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ").append(property.toString()).append("=");
        if(value.getClass().getSimpleName().equals("Integer")){
            sb.append(value.toString());
            return sb.toString();
        }
        if(value.getClass().getSimpleName().equals("int")){
            sb.append(value.toString());
            return sb.toString();
        }
        if(value.getClass().getSimpleName().equals("Double")){
            sb.append(value.toString());
            return sb.toString();
        }
        if(value.getClass().getSimpleName().equals("double")){
            sb.append(value.toString());
            return sb.toString();
        }
        if(value.getClass().getSimpleName().equals("float")){
            sb.append(value.toString());
            return sb.toString();
        }

        sb.append("'").append(value.toString()).append("'");
        System.out.println(sb.toString());
        return sb.toString();
    }

    //Multiple item deletion by any property (delete all users called x)
    public void deleteItemByAnyProperty(Object property){

    }


    //use Annotations when reading from db

//for (Field field : usr.getClass().getDeclaredFields()) {
//        DBField dbField = field.getAnnotation(DBField.class);
//        System.out.println("field name: " + dbField.name());
//
//        // changed the access to public
//        field.setAccessible(true);
//        Object value = field.get(usr);
//        System.out.println("field value: " + value);
//
//        System.out.println("field type: " + dbField.type());
//        System.out.println("is primary: " + dbField.isPrimaryKey());

}
