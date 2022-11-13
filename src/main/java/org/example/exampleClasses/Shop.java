package org.example.exampleClasses;

import java.util.List;

public class Shop {

    private String name;
    private List<Product> productList;

    public Shop(String name, List<Product> productList) {
        this.name = name;
        this.productList = productList;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Product> getProductList() {
        return productList;
    }


    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
