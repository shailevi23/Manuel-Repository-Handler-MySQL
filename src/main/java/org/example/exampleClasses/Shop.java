package org.example.exampleClasses;


import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    @NotNull
    @AutoIncrement
    @PrimaryKey
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private ArrayList<Product> productList;



    public Shop(){

    }

    public static Shop createShopWithoutId(String name, ArrayList<Product> productList) {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setProductList(productList);
        shop.setId(null);
        return shop;
    }
    public static Shop createShop(String name, ArrayList<Product> productList, Integer id) {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setProductList(productList);
        shop.setId(id);
        return shop;
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


    public ArrayList<Product> getProductList() {
        return productList;
    }


    public void setProductList(ArrayList<Product> productList) {
        if(productList == null){
            throw new NullPointerException("Product list is null");
        }
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productList=" + productList +
                '}';
    }
}
