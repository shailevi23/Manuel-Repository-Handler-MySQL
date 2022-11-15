package org.example.ORM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
         return executeAndReturn(repoLogic.findObj(obj)).get(0);
    }

    public List<T> addAll(List<T> objects) {
        List<T> resList= new ArrayList<>();
        for(T obj : objects) {
            resList.add(add(obj));
        }
        return resList;
    }

    //TODO - not working
    public List<T> selectById(int id){
        return executeAndReturn(repoLogic.createSelectByFieldQuery("id", id));

    }

    private void execute(String query) {
        try(Connection c = ConnectHandler.connect(this.sqlConfig)){
            logger.info("Connection created for " + sqlConfig.getDbName());
            Statement statement = c.createStatement();
            statement.execute(query);

        } catch(SQLException e) {
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
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed", e);
        }
        return result;
    }

    public T update(T obj) {
        execute(repoLogic.createUpdateQueryLogic(obj));
        return executeAndReturn(repoLogic.findObj(obj)).get(0);
    }

}
