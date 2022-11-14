package org.example.SqlConfig;

public class SqlConfig {

    String dbName;
    String user;
    String password;

    final String schemaName;

    public SqlConfig(String dbName, String user, String password) {
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        this.schemaName = "summery_project";
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}