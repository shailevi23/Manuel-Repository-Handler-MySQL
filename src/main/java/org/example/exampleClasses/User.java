package org.example.exampleClasses;

import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;
import org.example.Anottations.Unique;

public class User {

    @AutoIncrement
    @PrimaryKey
    private Integer id;
    private String firstName;
    @NotNull
    private String lastName;
    @Unique
    @NotNull
    private String email;


    public User() {
    }

    public static User createUserWithoutId(String firstName, String lastName, String email){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setId(null);
        return user;
    }

    public static User createUser(String firstName, String lastName, String email, Integer id){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setId(id);
        return user;
    }

    public int getId() {
        return id;
    }


    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
