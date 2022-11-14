package org.example.exampleClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void createProduct(){
        user = new User(1, "firstname", "lastname");
    }

    @Test
    void setId_idIsValid_isEqual() {
        user.setId(5);
        assertEquals(user.getId(), 5, "Excepted equal 5");
    }


    @Test
    void setId_idIsNegative_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setId(-1), "Expected IllegalArgumentException and something else happened");
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
}