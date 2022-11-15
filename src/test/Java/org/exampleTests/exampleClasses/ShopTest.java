package org.exampleTests.exampleClasses;

import org.example.exampleClasses.Product;
import org.example.exampleClasses.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    Shop shop;

    @BeforeEach
//    void createProduct() {
//        shop = new Shop("name", List.of(new Product("name", 2.0)));
//    }


    @Test
    void setName_nameIsValid_isEquals() {
        shop.setName("some new name of store");
        assertEquals(shop.getName(), "some new name of store", "Expected equal name");
    }

    @Test
    void setName_nameIsNull_throwsException() {
        assertThrows(NullPointerException.class, () -> shop.setName(null), "Expected NullPointerException and something else happened");
    }

    @Test
    void setName_nameIsEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> shop.setName(""), "Expected IllegalArgumentException and something else happened");
    }

    @Test
    void setProductList_productListAreValid_isEquals() {
//        Product product = new Product("name name", 2.0);
//        shop.setProductList(List.of(product, product, product));
//        assertEquals(shop.getProductList(), List.of(product, product, product));
    }

    @Test
    void setProductList_productListIsNull_throwsException() {
        assertThrows(NullPointerException.class, () -> shop.setProductList(null), "Expected NullPointerException and something else happened");
    }
}