package org.example.exampleClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    Shop shop;

    @BeforeEach
    void createProduct() {
        ArrayList<Product> prodList = new ArrayList<>();
        prodList.add(Product.createProductWithoutId("name", 2.0));
        shop = Shop.createShopWithoutId("name", prodList);
    }


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
        Product product = Product.createProductWithoutId("name name", 2.0);
        ArrayList<Product> prodList = shop.getProductList();
        prodList.add(product);
        shop.setProductList(prodList);
        assertEquals(shop.getProductList(), prodList);
    }

    @Test
    void setProductList_productListIsNull_throwsException() {
        assertThrows(NullPointerException.class, () -> shop.setProductList(null), "Expected NullPointerException and something else happened");
    }

    @Test
    void createShop_isNotNull_isEquals(){
        Product product = Product.createProductWithoutId("name name", 2.0);
        ArrayList<Product> prodList = shop.getProductList();
        prodList.add(product);
        assertNotNull(Shop.createShop("first", prodList, 1));
    }
}