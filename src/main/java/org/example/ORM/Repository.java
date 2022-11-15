package org.example.ORM;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Anottations.AutoIncrement;
import org.example.SQLconnection.ConnectHandler;
import org.example.SQLconnection.SqlConfig;
import org.example.Utils.Utils;

import javax.swing.plaf.PanelUI;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository<T> {

    private final Class<T> clz;
    private RepoLogic<T> repoLogic;

    private SqlConfig sqlConfig;

    private static Logger logger = LogManager.getLogger(Repository.class.getName());


    public Repository(Class<T> clz, SqlConfig sqlConfig) {
        this.clz = clz;
        this.sqlConfig = sqlConfig;
        repoLogic = new RepoLogic<>(clz);
    }

    public boolean createTable() {
        return executeBoolean(repoLogic.createTableQueryLogic());
    }

    public boolean deleteTable() {
        return executeBoolean(repoLogic.deleteTableQueryLogic());
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

    public T add(T obj) {
        execute(repoLogic.createAddQueryLogic(obj));
        List<T> list = executeAndReturn(repoLogic.findObj(obj));
        return list.get(list.size() - 1);
    }

    public boolean addAll(List<T> objects) {
        for(T obj : objects) {
            add(obj);
        }
        return true;
    }

    //TODO - not working
    public List<T> selectById(int id){
        return executeAndReturn(repoLogic.createSelectByFieldQuery("id", id));

    }

    private void execute(String query) {
        try(Connection c = ConnectHandler.connect(this.sqlConfig)){
            logger.info("Connection created for " + this.sqlConfig.getDbName());
            Statement statement = c.createStatement();
            statement.execute(query);
        }catch(SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed",e);
        }
    }

    private boolean executeBoolean(String query) {
        try(Connection c = ConnectHandler.connect(this.sqlConfig)){
            Statement statement = c.createStatement();
            return !statement.execute(query);
        } catch(SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed",e);
        }
    }

    private List<T> executeAndReturn(String query) {
        List<T> result = new ArrayList<>();
        try{
            Connection c = ConnectHandler.connect(this.sqlConfig);
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(query);

            Gson gson = new Gson();

            while (rs.next()) {
                Constructor<T> constructor = clz.getConstructor(null);
                T item = constructor.newInstance();
                Field[]  declaredFields = clz.getDeclaredFields();
                for(Field field : declaredFields){
                        field.setAccessible(true);
                        if(Utils.mapInit().containsKey(field.getType()) || field.getType().equals(String.class)){
                            field.set(item, rs.getObject(field.getName()));
                        }
                        else{
                            field.set(item, gson.fromJson(rs.getObject(field.getName()).toString(), field.getType()));
                        }
                }
                result.add(item);
            }

        } catch(SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
        return result;
    }

    public int exe(String query) {
        try{
            Connection c = ConnectHandler.connect(this.sqlConfig);
            Statement statement = c.createStatement();

            return statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

        } catch(SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
    }

    public T update(T obj) {
        execute(repoLogic.createUpdateQueryLogic(obj));
        return executeAndReturn(repoLogic.findObj(obj)).get(0);
    }

    public List<T> update(Map<String, Object> fieldsToUpdate, Map<String, Object>  filtersField) {
//        execute(repoLogic.createUpdateQueryByFilterLogic(fieldsToUpdate, filtersField));
//        return executeAndReturn(repoLogic.createUpdateQueryByFilterLogic(fieldsToUpdate, filtersField));

        return null;
    }

//    public List<T> updateByProperty(String filedName, String value) {
//        executeAndReturn(repoLogic.createSelectByFieldQuery(filedName, value));
//
//    }





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
