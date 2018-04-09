/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author abanoub samy
 */
public class Item implements Serializable{
     private int item_id;
    private String item_name;
    private byte[] item_pic;
    private int item_quntity;
    private int item_price;
    private String item_catagory;
  

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public byte[] getItem_pic() {
        return item_pic;
    }

    public void setItem_pic(byte[] item_pic) {
        this.item_pic = item_pic;
    }

    public int getItem_quntity() {
        return item_quntity;
    }

    public void setItem_quntity(int item_quntity) {
        this.item_quntity = item_quntity;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public String getItem_catagory() {
        return item_catagory;
    }

    public void setItem_catagory(String item_catagory) {
        this.item_catagory = item_catagory;
    }

    @Override
    public String toString() {
        return item_name+item_catagory; //To change body of generated methods, choose Tools | Templates.
    }

    
}
