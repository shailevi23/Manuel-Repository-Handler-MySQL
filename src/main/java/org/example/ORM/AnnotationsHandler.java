package org.example.ORM;

import java.util.ArrayList;

public class AnnotationsHandler {

    int countPrimaryKeys;
    int countAutoIncrement;
    String primaryField;
    final ArrayList<String> uniqueField;

    public AnnotationsHandler(int countPrimaryKeys, int countAutoIncrement, String primaryField) {
        this.countPrimaryKeys = countPrimaryKeys;
        this.countAutoIncrement = countAutoIncrement;
        this.primaryField = primaryField;
        this.uniqueField = new ArrayList<>();
    }

    public int getCountPrimaryKeys() {
        return countPrimaryKeys;
    }

    public void setCountPrimaryKeys(int countPrimaryKeys) {
        this.countPrimaryKeys = countPrimaryKeys;
    }

    public int getCountAutoIncrement() {
        return countAutoIncrement;
    }

    public void setCountAutoIncrement(int countAutoIncrement) {
        this.countAutoIncrement = countAutoIncrement;
    }

    public String getPrimaryField() {
        return primaryField;
    }

    public void setPrimaryField(String primaryField) {
        this.primaryField = primaryField;
    }

    public ArrayList<String> getUniqueField() {
        return uniqueField;
    }

    public String messagePrimaryKey(){
        return "Cant Have 2 Primary Keys values in table";
    }

    public String messageAutoIncrement(){
        return "Cant Have 2 Auto Increment values in table";
    }

}
