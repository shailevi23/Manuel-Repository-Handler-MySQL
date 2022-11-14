package org.example.exampleClasses;

import org.example.ORM.DBField;

public class Product {

    @DBField(name = "name", isPrimaryKey = false, type = String.class)
    String name;
    @DBField(name = "price", isPrimaryKey = false, type = Double.class)
    Double price;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
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


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be a negative number");
        }

        this.price = price;
    }
}
