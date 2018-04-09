/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_implementation;

import dtos.ShopCardItem;
import java.util.ArrayList;

/**
 *
 * @author Altysh
 */
public class BuyOperation {
    ArrayList<ShopCardItem> buyed;
    boolean done = false;

    public BuyOperation() {
        buyed = new ArrayList<>();
    }

    public ArrayList<ShopCardItem> getBuyed() {
        return buyed;
    }

    public void setBuyed(ArrayList<ShopCardItem> buyed) {
        this.buyed = buyed;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
