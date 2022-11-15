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

    private SqlConfig sqlConfig;

    public Repository(Class<T> clz, SqlConfig sqlConfig) {
        this.clz = clz;
        this.sqlConfig = sqlConfig;
        repoLogic = new RepoLogic<>(clz);
    }

    public void createTable() {
        execute(repoLogic.createTableQueryLogic());
    }

    public void deleteTable() {
        execute(repoLogic.deleteTableQueryLogic());
    }

    public void deleteItemsByProperty(Object property, Object value) {
        execute(repoLogic.deleteManyItemsByAnyPropertyQueryLogic(property, value));
    }

    //TODO
//    public void deleteSingleItemByAnyProperty(Object property){
//        execute(repoLogic.deleteSingleItemByAnyPropertyLogic(property));
//    }

    public List<T> selectAll() {
        return executeAndReturn(repoLogic.createSelectAllQueryLogic());
    }

    public void add(T obj) {
        execute(repoLogic.createAddQueryLogic(obj));
    }

    public void addAll(List<T> objects) {
        for(T obj : objects) {
            add(obj);
        }
    }

    //TODO - not working
    public List<T> selectById(int id){
        return executeAndReturn(repoLogic.createSelectByFieldQuery("id", id));

    }

    private void execute(String query) {

        try(Connection c = ConnectHandler.connect(this.sqlConfig)){
            Statement statement = c.createStatement();
            statement.execute(query);

        } catch(SQLException e) {
            throw new RuntimeException("Connection failed",e);
        }
    }

    private List<T> executeAndReturn(String query) {
        List<T> result = new ArrayList<>();
        try{
            Connection c = ConnectHandler.connect(this.sqlConfig);
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(query);

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

        } catch(SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Connection failed", e);
        }
        return result;
    }

    public T update(T obj) {
        execute(repoLogic.createUpdateQueryLogic(obj));
        return executeAndReturn(repoLogic.findObj(obj)).get(0);
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
