package org.example.ORM;

public class AnnotationsHandler {

    int countPrimaryKeys;
    int countAutoIncrement;
    String primaryField;


    AnnotationsHandler(int countPrimaryKeys, int countAutoIncrement, String primaryField) {
        this.countPrimaryKeys = countPrimaryKeys;
        this.countAutoIncrement = countAutoIncrement;
        this.primaryField = primaryField;
    }


    int getCountPrimaryKeys() {
        return countPrimaryKeys;
    }


    void setCountPrimaryKeys(int countPrimaryKeys) {
        this.countPrimaryKeys = countPrimaryKeys;
    }


    int getCountAutoIncrement() {
        return countAutoIncrement;
    }


    void setCountAutoIncrement(int countAutoIncrement) {
        this.countAutoIncrement = countAutoIncrement;
    }


    String getPrimaryField() {
        return primaryField;
    }


    void setPrimaryField(String primaryField) {
        this.primaryField = primaryField;
    }


    String messagePrimaryKey() {
        return "Cant Have 2 Primary Keys values in table";
    }


    String messageAutoIncrement() {
        return "Cant Have 2 Auto Increment values in table";
    }

}
