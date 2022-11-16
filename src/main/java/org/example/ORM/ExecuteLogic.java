package org.example.ORM;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.SQLconnection.ConnectHandler;
import org.example.SQLconnection.SqlConfig;
import org.example.Utils.Utils;
import org.example.client.Client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExecuteLogic<T> {
    private static Logger logger = LogManager.getLogger(ExecuteLogic.class.getName());
    private SqlConfig sqlConfig;
    private Class<T> clz;

    public ExecuteLogic(SqlConfig sqlConfig, Class<T> clz) {
        this.sqlConfig = sqlConfig;
        this.clz = clz;
    }

    void execute(String query) {
        try(Connection c = ConnectHandler.connect(this.sqlConfig)){
            logger.info("Connection created for " + this.sqlConfig.getDbName());
            Statement statement = c.createStatement();
            statement.execute(query);
        }catch(SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed",e);
        }
    }

    boolean executeBoolean(String query) {
        try(Connection c = ConnectHandler.connect(this.sqlConfig)){
            Statement statement = c.createStatement();
            statement.execute(query);
            return true;
        } catch(SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Connection failed",e);
        }
    }

    List<T> executeAndReturn(String query) {
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



}
