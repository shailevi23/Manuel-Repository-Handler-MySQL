package org.example.ORM;

import org.example.SQLconnection.ConnectHandler;
import org.example.SqlConfig.SqlConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

//    public void select(Class<T> entity){
//        StringBuilder stringBuilder = createSelectQuery(entity);
//        execute(stringBuilder.toString());
//    }
    public <T> T  selectAll(SqlConfig sqlConfig) throws SQLException {
        ResultSet resultSet = executeAndReturn(createSelectAllQuery(), sqlConfig);
        return null;
    }

    public List<T> selectById(int id, SqlConfig sqlConfig) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String query = createSelectByFieldQuery("id", id);
        ResultSet rs = executeAndReturn(query, sqlConfig);
        List<T> result = new ArrayList<>();

        while(rs.next()){

            int currId = rs.getInt("id");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");

            Constructor<T> constructor;
            constructor= (Constructor<T>) clz.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            T item = constructor.newInstance(currId, firstName, lastName);

            result.add(item);
        }

        return result;
    }
    public String createSelectAllQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM summery_project.");
        stringBuilder.append(clz.getSimpleName().toLowerCase());
        stringBuilder.append(";");
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    public String createSelectByFieldQuery(String field, Integer value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM summery_project.");
        stringBuilder.append(clz.getSimpleName().toLowerCase());
        stringBuilder.append(" WHERE ").append(field);
        stringBuilder.append("= ").append(value.toString());
        stringBuilder.append(";");
        System.out.println(stringBuilder);
        return stringBuilder.toString();
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

    private ResultSet executeAndReturn(String query, SqlConfig sqlConfig) {
        ConnectHandler c = new ConnectHandler(sqlConfig);
        try(Connection connect = c.connect()){
            Statement statement = connect.createStatement();
            return statement.executeQuery(query);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private String createFindByPropertyQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(clz.getSimpleName());
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


    private String createAddQuery(T object) {
        StringBuilder sb = new StringBuilder();

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

    public void addAll(List<T> objects, SqlConfig sqlConfig) {
        for(T obj : objects) {
            add(obj, sqlConfig);
        }
    }


    public void add(T obj, SqlConfig sqlConfig) {
        String query = createAddQuery(obj);
        execute(query, sqlConfig);
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
