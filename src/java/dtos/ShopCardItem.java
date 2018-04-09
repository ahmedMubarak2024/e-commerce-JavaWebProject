/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author Altysh
 */
public class ShopCardItem implements Serializable{
    Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }
    int quntity = 1;
    int totalprice = 0;

    public ShopCardItem() {
    }

    @Override
    public String toString() {
        return item.toString()+quntity ; //To change body of generated methods, choose Tools | Templates.
    }
    
}
