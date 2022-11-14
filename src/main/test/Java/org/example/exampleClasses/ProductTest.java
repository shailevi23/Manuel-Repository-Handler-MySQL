package org.example.exampleClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @BeforeEach
    void createProduct(){
        product = new Product("name", 2.0);
    }

    @Test
    void setName_nameIsValid_isEquals() {
        product.setName("some new name of product");
        assertEquals(product.getName(), "some new name of product");
    }

    @Test
    void setName_nameIsNull_throwsException() {
        assertThrows(NullPointerException.class, () -> product.setName(null), "Expected NullPointerException and something else happened");
    }

    @Test
    void setName_nameIsEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> product.setName(""), "Expected IllegalArgumentException and something else happened");
    }

    @Test
    void setPrice_priceIsValid_isEquals() {
        product.setPrice(5.5);
        assertEquals(product.getPrice(), 5.5);
    }

    @Test
    void setPrice_priceIsNegative_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> product.setPrice(-1.0), "Expected IllegalArgumentException and something else happened");
    }
}