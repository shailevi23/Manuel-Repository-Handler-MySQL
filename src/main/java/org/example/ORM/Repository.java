package org.example.ORM;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Repository<T> {

    private final Class<T> clz;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private Connection connection;


    public Repository(Class<T> clz, Connection connection) {
        this.clz = clz;
        this.connection = connection;
    }


    public void createTable() {
        String query = createTableQuery();
        execute(query);
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


    private String createFindQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(clz.getSimpleName());
        sb.append(" WHERE " + field + "=?");
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
}