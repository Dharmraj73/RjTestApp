package com.example.test.dharmrajmachinetest.data.model;

/**
 * Created by Test on 6/1/2018.
 */

public class Item {

    public int id;
    public int category_id;
    public String name;
    public double price;
    public int quantity;
    public int categoryId;
    public boolean isImported;

    public double getTotal(){
        return price*quantity;
    }

    public double getSalesTax(){
        double res = (price / 100.0d) * 10;
        return res*quantity;
    }

    public double getImportedTax(){
        if(isImported){
            return quantity * ((price / 100.0f) * 5);
        }
        return 0.0d;
    }
}
