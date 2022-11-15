package org.example.exampleClasses;


import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;

public class Product {

    @AutoIncrement
    @NotNull
    @PrimaryKey
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Double price;


    public Product(){

    }

    public static Product createProductWithoutId(String name, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setId(null);
        return product;
    }


    public static Product createProduct(String name, Double price, int id) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setId(id);
        return product;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
