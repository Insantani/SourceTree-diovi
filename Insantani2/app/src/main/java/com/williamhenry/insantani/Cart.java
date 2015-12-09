package com.williamhenry.insantani;

import android.graphics.Bitmap;

public class Cart {
    String productName;
    int qty;
    String farmer;
    float price;
    Bitmap image;
    int id;
    String uom;
    int stock;


    public Cart(String productName, int qty, String farmer, float price, Bitmap image,int id, String uom, int stock) {
        this.productName = productName;
        this.qty = qty;
        this.farmer = farmer;
        this.price = price;
        this.image = image;
        this.id=id;
        this.uom=uom;
        this.stock=stock;
    }

    public String getProductName() {

        return productName;
    }

    public int getQty() {
        return qty;
    }

    public String getFarmer() {

        return farmer;
    }

    public float getPrice() {

        return price;
    }

    public Bitmap getImage() {

        return image;
    }
    public int getId() {

        return id;
    }
    public String getUom() {

        return uom;
    }

    public int getStock() {

        return stock;
    }
    public void setQuantity(int qty){
        this.qty=qty;
    }


}
