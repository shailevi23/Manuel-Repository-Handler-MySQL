package org.example.ORM;

import java.lang.reflect.Field;
import java.sql.*;

public class Repository<T> {

    private final Class<T> clz;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private Connection connection;


    public Repository(Class<T> clz, Connection connection) {
        this.clz = clz;
        this.connection = connection;
    }

    public void createTable() {
        StringBuilder stringBuilder = createTableQuery();
        execute(stringBuilder.toString());
    }

//    public void select(Class<T> entity){
//        StringBuilder stringBuilder = createSelectQuery(entity);
//        execute(stringBuilder.toString());
//    }
    public ResultSet select(Class<T> entity) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet rs = query.executeQuery("SELECT * FROM " + entity);
        return rs;
    }
    public StringBuilder createSelectQuery(Class<T> entity) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM summery_project.");
        stringBuilder.append(entity.getSimpleName().toLowerCase());
        stringBuilder.append(";");
        System.out.println(stringBuilder);
        return stringBuilder;
    }

    public StringBuilder createTableQuery() {
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
        return stringBuilder;
    }


    public void connect(String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(DB_URL + "summery_project", user, password);

        } catch(ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void execute(String query) {
        try{
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
}