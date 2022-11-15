package org.example.ORM;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;
import org.example.Anottations.Unique;
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
import java.util.Arrays;
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
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" WHERE ").append(field);
        sb.append("= ").append(value.toString());
        sb.append(";");
        System.out.println(sb);
        return sb.toString();
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
                if(field.getAnnotation(AutoIncrement.class) != null){
                    sb.append("NULL,");
                }
                else if(map.containsKey(field.getType())) {
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
        StringBuilder sb = new StringBuilder();
        logger.info("Creating table for " + clz.getSimpleName());
        sb.append("CREATE TABLE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" (\n");

        AnnotationsHandler annotationHandler = new AnnotationsHandler(0, 0, null);

        for(Field field : clz.getDeclaredFields()) {
            sb.append(field.getName());
            sb.append(" ");
            sb.append(getMySQLDataType(field.getType().getSimpleName()));
            annotationHandle(field, sb, annotationHandler);
            sb.append(",\n");
        }
        sb.append("PRIMARY KEY (").append(annotationHandler.getPrimaryField()).append(")");
        if(annotationHandler.getUniqueField().size() == 1){
            sb.append(",\n");
            sb.append("UNIQUE (").append(annotationHandler.getUniqueField().get(0)).append(")");
        }
        else if(annotationHandler.getUniqueField().size() > 1){
            sb.append(",\n");
            sb.append("CONSTRAINT UC_").append(clz.getSimpleName()).append(" UNIQUE (");
            for (String fieldName: annotationHandler.getUniqueField()) {
                sb.append(fieldName);
                sb.append(",");
            }
            sb.replace(sb.length() - 1, sb.length(), ")");
        }
        sb.append("\n);");
        System.out.println(sb.toString());
        return sb.toString();
    }

    private void annotationHandle(Field field, StringBuilder sb ,AnnotationsHandler annotationsHandler) {
        if(field.getAnnotation(PrimaryKey.class) != null){
            annotationsHandler.setCountPrimaryKeys(annotationsHandler.getCountPrimaryKeys() + 1);
            annotationsHandler.setPrimaryField(field.getName());
            if(annotationsHandler.getCountPrimaryKeys() > 1){
                throw new IllegalArgumentException(annotationsHandler.messagePrimaryKey());
            }
        }

        if(field.getAnnotation(Unique.class) != null){
            annotationsHandler.getUniqueField().add(field.getName());
        }


        if(field.getAnnotation(NotNull.class) != null){
            sb.append(" NOT NULL");
        }

        if(field.getAnnotation(AutoIncrement.class) != null){
            sb.append(" AUTO_INCREMENT");
            annotationsHandler.setCountAutoIncrement(annotationsHandler.getCountAutoIncrement() + 1);
            if(annotationsHandler.getCountAutoIncrement() > 1){
                throw new IllegalArgumentException(annotationsHandler.messageAutoIncrement());
            }
        }
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

    String createUpdateQueryByFilterLogic(Map<String, Object> fieldsToUpdate, Map<String, Object>  filtersField) {
        StringBuilder sb = new StringBuilder();
        StringBuilder whereString = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(clz.getSimpleName().toLowerCase());
        sb.append(" SET ");

        for (Map.Entry<String,Object> entry : fieldsToUpdate.entrySet()){
            if(entry.getKey().equals("Intger") || entry.getKey().equals("int")) {
                sb.append(entry.getValue());
                sb.append(", ");
            }
            else {
                sb.append("'");
                sb.append(entry.getValue());
                sb.append("',");
            }
        }
        sb.deleteCharAt(sb.length() -1 );
        sb.append(" WHERE ");

        for (Map.Entry<String,Object> entry : filtersField.entrySet()){
            if(entry.getKey().equals("Intger") || entry.getKey().equals("int")) {
                sb.append(entry.getValue());
                sb.append(", ");
            }
            else {
                sb.append("'");
                sb.append(entry.getValue());
                sb.append("',");
            }
        }

        sb.deleteCharAt(sb.length() -1 );
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
