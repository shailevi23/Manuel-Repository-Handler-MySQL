package org.example.ORM;

import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoLogic<T>{

    private final Class<T> clz;

    private static Logger logger = LogManager.getLogger(RepoLogic.class.getName());
    Map<Object,String> map = new HashMap<>();

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
            mapInit(map);
            try {
                sb.append(field.getName());
                sb.append(" = ");
                if(map.containsKey(field.getType())){
                    sb.append(field.get(object));
                    sb.append(" AND ");
                }
                else if (field.get(object) instanceof String){
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

        checkInstanceOfFieldsAndAppendObjectToJson(object, sb);

        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        System.out.println(sb.toString());
        return sb.toString();
    }

    void checkInstanceOfFieldsAndAppendObjectToJson(T object, StringBuilder sb){
        Gson gson = new Gson();
        mapInit(map);
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(map.containsKey(field.getType())) {
                    sb.append(field.get(object));
                    sb.append(",");
                }
                else if(field.get(object) instanceof String) {
                    sb.append("'");
                    sb.append(field.get(object));
                    sb.append("',");
                    map.containsKey(object);
                } else {
                    sb.append(gson.toJson(object));
                    sb.append(",");
                }
            } catch(IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //<----------------------------------CREATE TABLE---------------------------------->
    String createTableQueryLogic() {
        StringBuilder stringBuilder = new StringBuilder();
        logger.info("Creating table for " + clz.getName());
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
        logger.info("Truncating table " + clz.getName());
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

    public void mapInit(Map map){
        map.put(Integer.class, "Integer");
        map.put(int.class, "int");
        map.put(long.class, "long");
        map.put(Long.class, "Long");
        map.put(double.class, "double");
        map.put(Double.class, "Double");
        map.put(boolean.class, "boolean");
        map.put(Boolean.class, "Boolean");
    }

}
