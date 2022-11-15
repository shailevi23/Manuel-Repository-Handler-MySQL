package org.example.exampleClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;

public class Product {

    @AutoIncrement
    @PrimaryKey
    private int id;
    @NotNull
    private String name;
    @NotNull
    private Double price;

    private static Logger logger = LogManager.getLogger(Product.class.getName());

    private Product(){

    }

    public static Product createProduct(String name, Double price, int id) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setId(id);
        logger.info("created product named "+ name);
        return product;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        if(name == null){
            logger.error("trying to set product name to null");
            throw new NullPointerException("Name is null");
        }

        if(name.equals("")){
            logger.error("Product name is empty");
            throw new IllegalArgumentException("Name cannot be empty, Please insert product name");
        }

        this.name = name;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        if(price < 0){
            logger.error("trying to set product's price to negative number");
            throw new IllegalArgumentException("Price cannot be a negative number");
        }

        this.price = price;
    }
}
