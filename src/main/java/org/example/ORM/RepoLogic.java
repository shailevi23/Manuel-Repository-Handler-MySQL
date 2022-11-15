package org.example.ORM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.SQLconnection.ConnectHandler;
import org.example.SQLconnection.SqlConfig;
import org.example.exampleClasses.Shop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepoLogic<T>{

    private final Class<T> clz;

    private static Logger logger = LogManager.getLogger(RepoLogic.class.getName());
    public RepoLogic(Class<T> clz) {
        this.clz = clz;
    }

    //<----------------------------------READ---------------------------------->
    public String createSelectAllQueryLogic() {
        logger.info("creating SELECT * FROM " + clz + " Query");
        return "SELECT * FROM " + clz.getSimpleName().toLowerCase() + ";";
    }

    public String createSelectByFieldQuery(String field, Integer value) {
        logger.info("creating SELECT * FROM " + clz + " WHERE " + field + " = " + value);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(clz.getSimpleName().toLowerCase());
        stringBuilder.append(" WHERE ").append(field);
        stringBuilder.append("= ").append(value.toString());
        stringBuilder.append(";");
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }


    //<----------------------------------ADD---------------------------------->
    String createAddQueryLogic(T object) {
        StringBuilder sb = new StringBuilder();
        logger.info("creating INSERT INTO " + clz + " Query");
        sb.append("INSERT INTO ");
        sb.append(clz.getSimpleName());
        sb.append(" VALUES (");

        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(field.get(object) instanceof Integer) {
                    sb.append(field.get(object));
                    sb.append(",");
                }
                else {
                    sb.append("'");
                    sb.append(field.get(object));
                    sb.append("',");
                }
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        System.out.println(sb.toString());
        return sb.toString();
    }

    //<----------------------------------CREATE TABLE---------------------------------->
    String createTableQueryLogic() {
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

    //<----------------------------------DELETE---------------------------------->
    String deleteTableQueryLogic(){
        StringBuilder sb = new StringBuilder();
        sb.append("TRUNCATE TABLE ").append(clz.getSimpleName().toLowerCase()).append(";\n");
        return sb.toString();
    }


    public void deleteSingleItemByAnyPropertyLogic(Object property){
        //TODO
    }


    String deleteManyItemsByAnyPropertyQueryLogic(Object property, Object value){
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

    //<----------------------------------HELPERS---------------------------------->
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

}
