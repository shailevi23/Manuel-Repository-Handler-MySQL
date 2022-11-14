package org.example.ORM;

import org.example.SQLconnection.ConnectHandler;
import org.example.SQLconnection.SqlConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository<T> {

    private final Class<T> clz;
    private RepoLogic<T> repoLogic;

    public Repository(Class<T> clz) {
        this.clz = clz;
        repoLogic = new RepoLogic<>(clz);
    }

    public void createTable(SqlConfig sqlConfig) {
        execute(repoLogic.createTableQueryLogic(), sqlConfig);
    }

    public void deleteTable(SqlConfig sqlConfig) {
        execute(repoLogic.deleteTableQueryLogic(), sqlConfig);
    }

    public void deleteItemsByProperty(Object property, Object value, SqlConfig sqlConfig) {
        execute(repoLogic.deleteManyItemsByAnyPropertyQueryLogic(property, value), sqlConfig);
    }

    //TODO
//    public void deleteSingleItemByAnyProperty(Object property, SqlConfig sqlConfig){
//        execute(repoLogic.deleteSingleItemByAnyPropertyLogic(property), sqlConfig);
//    }

    public ResultSet selectAll(SqlConfig sqlConfig) {
        return executeAndReturn(repoLogic.createSelectAllQueryLogic(), sqlConfig);
    }

    public void add(T obj, SqlConfig sqlConfig) {
        execute(repoLogic.createAddQueryLogic(obj), sqlConfig);
    }

    public void addAll(List<T> objects, SqlConfig sqlConfig) {
        for(T obj : objects) {
            add(obj, sqlConfig);
        }
    }


    //TODO - not working
    public List<T> selectById(int id, SqlConfig sqlConfig){
        ResultSet rs = executeAndReturn(repoLogic.createSelectByFieldQuery("id", id), sqlConfig);
        List<T> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Constructor<T> constructor = clz.getConstructor(null);
                T item = constructor.newInstance();
                Field[]  declaredFields = clz.getDeclaredFields();
                for(Field field : declaredFields){
                    field.setAccessible(true);
                    field.set(item, rs.getObject(field.getName()));
                }
                result.add(item);
            }
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Couldn't use reflection properly and create an instance", e);
        }
        catch (SQLException e) {
            throw new RuntimeException("Database access error or closed result set", e);
        }
        return result;
    }


    private void execute(String query, SqlConfig sqlConfig) {
        ConnectHandler c = new ConnectHandler(sqlConfig);
        try(Connection connect = c.connect()){
            Statement statement = connect.createStatement();
            statement.execute(query);

        } catch(SQLException e) {
            throw new RuntimeException("Connection failed",e);
        }
    }

    private ResultSet executeAndReturn(String query, SqlConfig sqlConfig) {
        ConnectHandler c = new ConnectHandler(sqlConfig);
        try(Connection connect = c.connect()){
            Statement statement = connect.createStatement();
            return statement.executeQuery(query);

        } catch(SQLException e) {
            throw new RuntimeException("Connection failed", e);
        }
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
