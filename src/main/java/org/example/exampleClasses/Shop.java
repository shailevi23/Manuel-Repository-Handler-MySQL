package org.example.exampleClasses;

import org.example.ORM.DBField;

import java.util.List;

public class Shop {

    @DBField(name = "name", isPrimaryKey = false, type = String.class)
    private String name;
    @DBField(name = "productList", isPrimaryKey = false, type = String.class)
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
