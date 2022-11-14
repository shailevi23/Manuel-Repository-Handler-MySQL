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
        if(name == null){
            throw new NullPointerException("Name is null");
        }

        if(name.equals("")){
            throw new IllegalArgumentException("Name cannot be empty, Please insert product name");
        }

        this.name = name;
    }


    public List<Product> getProductList() {
        return productList;
    }


    public void setProductList(List<Product> productList) {
        if(productList == null){
            throw new NullPointerException("Product list is null");
        }
        this.productList = productList;
    }

}
