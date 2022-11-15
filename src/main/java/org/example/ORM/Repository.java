package org.example.ORM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.SQLconnection.SqlConfig;
import java.util.ArrayList;
import java.util.List;

public class Repository<T> {

    private final Class<T> clz;
    private QueryLogic<T> queryLogic;
    private SqlConfig sqlConfig;
    private ExecuteLogic<T> executeLogic;
    private static Logger logger = LogManager.getLogger(Repository.class.getName());


    public Repository(Class<T> clz, SqlConfig sqlConfig) {
        this.clz = clz;
        this.sqlConfig = sqlConfig;
        this.queryLogic = new QueryLogic<>(clz);
        this.executeLogic = new ExecuteLogic<T>(this.sqlConfig, this.clz);
    }

    public boolean createTable() {
        return executeLogic.executeBoolean(queryLogic.createTableQuery());
    }

    public boolean truncateTable() {
        return executeLogic.executeBoolean(queryLogic.truncateTableQuery());
    }

    public void deleteItemsByProperty(String property, Object value) {
        executeLogic.execute(queryLogic.deleteManyByAnyPropertyQuery(property, value));
    }

    public void deleteEntireObject(Object obj){
        executeLogic.execute(queryLogic.deleteSingleByAnyPropertyQuery(obj));
    }

    public List<T> selectAll() {
        return executeLogic.executeAndReturn(queryLogic.selectAllQueryLogic());
    }

    public T add(T obj) {
        executeLogic.execute(queryLogic.insertObjectQuery(obj));
        List<T> list = executeLogic.executeAndReturn(queryLogic.findObjQuery(obj));
        return list.get(list.size() - 1);
    }

    public List<T> addAll(List<T> objects) {
        List<T> res = new ArrayList<>();
        for(T obj : objects) {
            res.add(add(obj));
        }
        return res;
    }

    public List<T> selectById(String fieldName, int value){
        return executeLogic.executeAndReturn(queryLogic.selectByIdQuery(fieldName, value));

    }

    public T update(Object obj) {
        executeLogic.execute(queryLogic.updateEntireObjectQuery(obj));
        List<T> list = executeLogic.executeAndReturn(queryLogic.findObjQuery(obj));
        return list.get(list.size() - 1);
    }

    public List<T> updateByProperty(String filedName, Object fieldValue, String filterFieldName, Object filterValue) {
        executeLogic.execute(queryLogic.updateSinglePropertyQuery(filedName, fieldValue, filterFieldName, filterValue));
        return executeLogic.executeAndReturn(queryLogic.selectByManyFiltersQuery(filedName, fieldValue, filterFieldName, filterValue));
    }

}
