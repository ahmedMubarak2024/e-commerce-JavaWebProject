/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dtos.Item;
import java.util.ArrayList;

/**
 *
 * @author Altysh
 */
public class JspRow {
    
    
  private ArrayList<Item> elements;

    public JspRow() {
        elements = new ArrayList<>();
    }

    public ArrayList<Item> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Item> elements) {
        this.elements = elements;
    }
    
}
