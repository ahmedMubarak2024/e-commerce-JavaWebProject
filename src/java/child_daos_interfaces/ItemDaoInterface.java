
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_interfaces;

import dtos.Item;
import java.sql.SQLException;
import java.util.ArrayList;
import parent_dao_interface.ParentDao;

/**
 *
 * @author abanoub samy
 */
public interface ItemDaoInterface extends ParentDao<Item>{
    String ITEM_ID = "item_id";
    String ITEM_NAME = "item_name";
    String ITEM_QUTITY = "qubtity";
    String ITEM_PIC = "pic";
    String ITEM_CATAGORY = "catagory";
    String ITEM_PRICE = "price";
    String ITEM_VENDOR = "vendor_id";

    public ArrayList<Item> getItem(int index)throws SQLException;

    public boolean decrementItem(int itemId)throws SQLException;
    
    
    public boolean deleteItem(int itemId)throws SQLException;
    
    public ArrayList<Item> getItemByName(String nameToSearch)throws SQLException;
    public ArrayList<Item> getItemByPrice(int from , int to)throws SQLException;
    
    public ArrayList<Item> getItemByCat(String catToSearch)throws SQLException;
    
    public ArrayList<String> getAllCategories()throws SQLException;
    
    public ArrayList<Item> getItemByNameInCategory(String nameToSearch, String currentCategory) throws SQLException;



    
}

