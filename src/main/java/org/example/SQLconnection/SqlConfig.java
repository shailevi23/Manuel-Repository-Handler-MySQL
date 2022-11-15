package org.example.SQLconnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlConfig {
    String dbName;
    String user;
    String password;

    final String dbUrl = "jdbc:mysql://localhost:3306/";
    final String schemaName;

    private static Logger logger = LogManager.getLogger(SqlConfig.class.getName());

    public SqlConfig(String dbName, String user, String password) {
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        logger.info("SQL configuration was set for database - " + dbName + "for the user " + user + " and password " + password);
        this.schemaName = "summery_project";
        //CREATE SCHEME LATER
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

    public String getSchemaName() {
        return schemaName;
    }

    public String getDB_URL() {
        return dbUrl;
    }

    public String getDBurlAndName() {
        return dbUrl + dbName;
    }
}
