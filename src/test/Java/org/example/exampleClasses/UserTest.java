package org.example.exampleClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void createProduct() {
        user = User.createUserWithoutId("firstname", "lastname", "sha@gmail.com");
    }

    @Test
    void setId_idIsValid_isEqual() {
        user.setId(5);
        assertEquals(user.getId(), 5, "Excepted equal 5");
    }

    @Test
    void setFirstName_FirstNameIsValid_isEquals() {
        user.setFirstName("israel");
        assertEquals(user.getFirstName(), "israel", "Expected equal values");
    }

    @Test
    void setFirstName_FirstNameIsNull_throwsException() {
        assertThrows(NullPointerException.class, () -> user.setFirstName(null), "Expected NullPointerException and something else happened");
    }

    @Test
    void setFirstName_FirstNameIsEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setFirstName(""), "Expected IllegalArgumentException and something else happened");
    }


    @Test
    void setLastName_LastNameIsValid_isEquals() {
        user.setLastName("israeli");
        assertEquals(user.getLastName(), "israeli", "Expected equal values");
    }

    @Test
    void setLastName_LastNameIsNull_throwsException() {
        assertThrows(NullPointerException.class, () -> user.setLastName(null), "Expected NullPointerException and something else happened");
    }

    @Test
    void setLastName_LastNameIsEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setLastName(""), "Expected IllegalArgumentException and something else happened");
    }

    @Test
    void createUser_isNotNull_isEquals(){
        assertNotNull(User.createUser("first", "last", "email@gmail.com", 1));
    }
}