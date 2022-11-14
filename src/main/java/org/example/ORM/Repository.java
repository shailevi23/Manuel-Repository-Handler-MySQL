package org.example.ORM;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository<T> {

    private final Class<T> clz;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private Connection connection;
    static String summeryProjectScheme = "summery_project";

    public Repository(Class<T> clz, Connection connection) {
        this.clz = clz;
        this.connection = connection;
    }

    public void createTable() {
        String query = createTableQuery();
        execute(query);
    }

//    public void select(Class<T> entity){
//        StringBuilder stringBuilder = createSelectQuery(entity);
//        execute(stringBuilder.toString());
//    }
    public <T> T  selectAll() throws SQLException {
        String query = createSelectAllQuery();
        ResultSet resultSet = executeAndReturn(query);


        return null;
    }

    public List<T> selectById(int id) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String query = createSelectByFieldQuery("id", id);
        ResultSet rs = executeAndReturn(query);
        List<T> result = new ArrayList<>();

        while(rs.next()){

            int id1 = rs.getInt("id");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");


            Constructor<T> constructor;
            constructor= (Constructor<T>) clz.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            T item = constructor.newInstance(id1, firstName, lastName);

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

    public String createTableQuery() {
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


    public void connect(String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL + "summery_project", user, password);

        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(String query) {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeAndReturn(String query) {
        try {
            Statement statement = this.connection.createStatement();
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


    public String getMySQLDataType(String javaType) {
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

    public void addAll(List<T> objects) {
        for(T obj : objects) {
            add(obj);
        }
    }


    public void add(T obj) {
        String query = createAddQuery(obj);
        execute(query);
    }

    //Delete entire table (truncate)
    public String deleteTable(){
        StringBuilder sb = new StringBuilder();
        sb.append("TRUNCATE " + "`").append(summeryProjectScheme).append("`.`").append(clz.getSimpleName().toLowerCase()).append("`;\n");
        System.out.println(sb.toString());
        return sb.toString();
    }

    //Single item deletion by any property (delete user with email x)
    public String deleteManyItemsByAnyProperty(Object property, Object value){
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

}
