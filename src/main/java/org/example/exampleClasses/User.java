package org.example.exampleClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;
import org.example.Anottations.Unique;

public class User {

    @AutoIncrement
    @PrimaryKey
    private int id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @Unique
    @NotNull
    private String email;

    private static Logger logger = LogManager.getLogger(User.class.getName());

    public User() {
    }

    public static User createUser(String firstName, String lastName, String email, int id){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setId(id);
        logger.info("created user named "+ firstName + " " + lastName);
        return user;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        if(id < 0){
            logger.error("trying to set id to be negative");
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        if(firstName == null){
            logger.error("trying to set first name to null");
            throw new NullPointerException("First name is null");
        }

        if(firstName.equals("")){
            logger.error("trying to set first name to empty");
            throw new IllegalArgumentException("First name cannot be empty, Please insert product name");
        }

        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        if(lastName == null){
            logger.error("trying to set last name to null");
            throw new NullPointerException("Last name is null");
        }

        if(lastName.equals("")){
            logger.error("trying to set last name to empty");
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
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
