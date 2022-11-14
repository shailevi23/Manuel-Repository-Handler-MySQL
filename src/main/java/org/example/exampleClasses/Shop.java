package org.example.exampleClasses;

import org.example.Anottations.AutoIncrement;
import org.example.Anottations.NotNull;
import org.example.Anottations.PrimaryKey;

import java.util.List;

public class Shop {

    @AutoIncrement
    @PrimaryKey
    private int id;
    @NotNull
    private String name;
    @NotNull
    private List<Product> productList;


    private Shop(){

    }
    public static Shop createShop(String name, List<Product> productList, int id) {
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

    public void setId(int id) {
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
