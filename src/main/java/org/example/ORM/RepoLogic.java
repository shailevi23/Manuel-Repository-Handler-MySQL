package org.example.ORM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Anottations.AutoIncrement;
import org.example.Anottations.PrimaryKey;
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
        logger.info("creating SELECT * FROM " + clz.getSimpleName() + " Query");
        return "SELECT * FROM " + clz.getSimpleName().toLowerCase() + ";";
    }

    public String createSelectByFieldQuery(String field, Integer value) {
        logger.info("creating SELECT * FROM " + clz.getSimpleName() + " WHERE " + field + " = " + value);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(clz.getSimpleName().toLowerCase());
        stringBuilder.append(" WHERE ").append(field);
        stringBuilder.append("= ").append(value.toString());
        stringBuilder.append(";");
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    public String findObj(T object) {
        logger.info("creating SELECT * FROM " + clz.getSimpleName());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ");


        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                sb.append(field.getName());
                sb.append(" = ");
                if(field.get(object) instanceof Integer) {
                    sb.append(field.get(object));
                    sb.append(" AND ");
                }
                else {
                    sb.append("'");
                    sb.append(field.get(object));
                    sb.append("' AND ");
                }
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        sb.replace(sb.length() - 5, sb.length(),";" );

        System.out.println(sb);
        return sb.toString();
    }


    //<----------------------------------ADD---------------------------------->
    String createAddQueryLogic(T object) {
        StringBuilder sb = new StringBuilder();
        logger.info("creating INSERT INTO " + clz.getSimpleName() + " Query");
        sb.append("INSERT INTO ");
        sb.append(clz.getSimpleName().toLowerCase());
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
        StringBuilder sb = new StringBuilder();
        logger.info("Creating table for " + clz.getSimpleName());
        sb.append("CREATE TABLE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" (\n");

        int CountPrimaryKeys = 0;
        int CountAutoIncrement = 0;

        for(Field field : clz.getDeclaredFields()) {
            if(field.getAnnotation(PrimaryKey.class) != null){
                CountPrimaryKeys += 1;
                if(CountPrimaryKeys > 1){
                    throw new IllegalArgumentException("Cant Have 2 Primary Keys values in table");
                }
            }

            if(field.getAnnotation(AutoIncrement.class) != null){
                CountAutoIncrement += 1;
                if(CountAutoIncrement > 1){
                    throw new IllegalArgumentException("Cant Have 2 Auto Increment values in table");
                }
            }

            sb.append(field.getName());
            sb.append(" ");
            sb.append(getMySQLDataType(field.getType().getSimpleName()));
            sb.append(",\n");
        }
        sb.replace(sb.toString().length() - 2, sb.toString().length(), "\n);");
        return sb.toString();
    }

    //<----------------------------------DELETE---------------------------------->
    String deleteTableQueryLogic(){
        StringBuilder sb = new StringBuilder();
        logger.info("Truncating table " + clz.getSimpleName());
        sb.append("TRUNCATE TABLE ").append(clz.getSimpleName().toLowerCase()).append(";\n");
        return sb.toString();
    }


    public void deleteSingleItemByAnyPropertyLogic(Object property){
        //TODO
    }


    String deleteManyItemsByAnyPropertyQueryLogic(Object property, Object value){
        StringBuilder sb = new StringBuilder();
        logger.info("Deleting many items by specific property");
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


    String createUpdateQueryLogic(T object) {
        StringBuilder sb = new StringBuilder();
        StringBuilder whereString = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" SET ");

        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                if(fieldName.equals("id")) {
                    whereString.append(" WHERE id = ").append(field.get(object));
                    continue;
                } else {

                    sb.append(fieldName);
                    sb.append(" = ");
                }

                if(field.get(object) instanceof Integer) {
                    sb.append(field.get(object));
                    sb.append(", ");
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
        sb.deleteCharAt(sb.length() -1 );
        sb.append(whereString);
        sb.append(";");
        System.out.println(sb);
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
