package org.example.exampleClasses;

import org.example.ORM.DBField;

public class User {

    @DBField(name = "id", isPrimaryKey = true, type = int.class)
    private int id;
    @DBField(name = "firstName", isPrimaryKey = false, type = String.class)
    private String firstName;
    @DBField(name = "lastName", isPrimaryKey = false, type = String.class)
    private String lastName;


    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        if(id < 0){
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        if(firstName == null){
            throw new NullPointerException("First name is null");
        }

        if(firstName.equals("")){
            throw new IllegalArgumentException("First name cannot be empty, Please insert product name");
        }

        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        if(lastName == null){
            throw new NullPointerException("Last name is null");
        }

        if(lastName.equals("")){
            throw new IllegalArgumentException("Lat name cannot be empty, Please insert product name");
        }
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
