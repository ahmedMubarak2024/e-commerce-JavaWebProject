
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_implementation;

import child_daos_interfaces.ItemDaoInterface;
import dtos.Item;
import dtos.ShopCardItem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class ItemDaoImplementation implements ItemDaoInterface {

    private Connection con;
    PreparedStatement statemnt;
    ResultSet res;
    private DataSource dataSource;

    public ItemDaoImplementation() {

    }

    public ItemDaoImplementation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void close() {
        try {
            if (statemnt != null && !statemnt.isClosed()) {
                statemnt.close();
            }
            if (res != null && !res.isClosed()) {
                res.close();
            }
            
            if(con !=null && !con.isClosed())
            {
                
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public void closeCon(Connection con) throws SQLException
    {
        this.con.close();
    }

    @Override
    public ArrayList<Item> getItem(int index) throws SQLException {
        ArrayList<Item> list = new ArrayList<>();
        // con = dataSource.getConnection();
        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item LIMIT 10 OFFSET ?");
        statemnt.setInt(1, index);
        res = statemnt.executeQuery();
        while (res.next()) {
            try {
                Item item = new Item();
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_name(res.getString(ITEM_NAME));
                Blob b = res.getBlob(ITEM_PIC);
                byte[] bytes = getBytes(b);
                item.setItem_pic(bytes);
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
                item.setItem_price(res.getInt(ITEM_PRICE));

                list.add(item);

            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        return list;
    }

    @Override
    public boolean decrementItem(int itemId) throws SQLException {
        statemnt = con.prepareStatement("UPDATE item SET qubtity = qubtity -1 where item_id = ? and qubtity > 0");
        statemnt.setInt(1, itemId);
        int i = statemnt.executeUpdate();

        if (i > 0) {
          
            return true;
        } else {
            
            return false;
        }
    }

    public boolean decrementItem(int itemId, int newQuntity) throws SQLException {
        statemnt = con.prepareStatement("UPDATE item SET qubtity = ? where item_id = ? ");
        statemnt.setInt(1, newQuntity);
        statemnt.setInt(2, itemId);
        int i = statemnt.executeUpdate();

        if (i > 0) {
            
            return true;
        } else {
            
            return false;
        }
    }

    public byte[] getBytes(Blob b) throws IOException, SQLException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = b.getBinaryStream();
        byte[] buf = new byte[1024];
        int iterator = 0;
        while ((iterator = in.read(buf)) >= 0) {
            baos.write(buf, 0, iterator);

        }

        in.close();
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    @Override
    public boolean insert(Item item) throws SQLException {

        statemnt = con.prepareStatement(" INSERT INTO item (  qubtity, price, item_name, catagory, pic "
                + ") VALUES "
                + "( ? , ? , ? , ? , ?  )");
        statemnt.setInt(1, item.getItem_quntity());
        statemnt.setInt(2, item.getItem_price());
        statemnt.setString(3, item.getItem_name());
        statemnt.setString(4, item.getItem_catagory());
        statemnt.setBinaryStream(5, new ByteArrayInputStream(item.getItem_pic()));

        int i = statemnt.executeUpdate();
        if (i > 0) {
           
            return true;
        } else {
            
            return false;
        }

    }

    @Override
    public boolean update(Item item) throws SQLException {

        con = dataSource.getConnection();
        try {
            statemnt = con.prepareStatement("UPDATE item SET qubtity = ? , price = ? , item_name = ?, catagory =? where item_id = ?");
            statemnt.setInt(1, item.getItem_quntity());
            statemnt.setInt(2, item.getItem_price());
            statemnt.setString(3, item.getItem_name());
            statemnt.setString(4, item.getItem_catagory());
        
            statemnt.setInt(5, item.getItem_id());
            statemnt.executeUpdate();
            return true;

        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        } finally {

            close();
        }

    }

    @Override
    public Item select(Item t) throws SQLException {
        Item item = null;
       // con = dataSource.getConnection();
        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where item_id = ?");
        statemnt.setInt(1, t.getItem_id());
        res = statemnt.executeQuery();
        while (res.next()) {
            item = new Item();
            try {
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_name(res.getString(ITEM_NAME));
                item.setItem_pic(getBytes(res.getBlob(ITEM_PIC)));
                item.setItem_price(res.getInt(ITEM_PRICE));
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        
       // close();
        return item;
    }

    public Item select(String name) throws SQLException {
        Item item = null;
        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where item_name = ?");
        statemnt.setString(1, name);
        res = statemnt.executeQuery();
        while (res.next()) {
            item = new Item();
            try {
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_name(res.getString(ITEM_NAME));
                item.setItem_pic(getBytes(res.getBlob(ITEM_PIC)));
                item.setItem_price(res.getInt(ITEM_PRICE));
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
           

        }
        
       
        return item;
    }
    public Item select(int id) throws SQLException {
        Item item = null;
        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where item_id = ?");
        statemnt.setInt(1, id);
        res = statemnt.executeQuery();
        while (res.next()) {
            item = new Item();
            try {
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_name(res.getString(ITEM_NAME));
                item.setItem_pic(getBytes(res.getBlob(ITEM_PIC)));
                item.setItem_price(res.getInt(ITEM_PRICE));
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
           

        }
        
       
        return item;
    }

    @Override
    public ArrayList<Item> convertResultSetToArrayList(ResultSet rs) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Item> reterieveAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteItem(int itemId) throws SQLException {
        con = dataSource.getConnection();
        try {
            statemnt = con.prepareStatement("DELETE FROM item WHERE item_id = ?");

            statemnt.setInt(1, itemId);

            statemnt.executeUpdate();
            return true;
        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        } finally {

            close();
        }

    }

    @Override
    public ArrayList<Item> getItemByName(String nameToSearch) throws SQLException {

        ArrayList<Item> list = new ArrayList<>();

        con = dataSource.getConnection();

        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where item_name like ?");

        statemnt.setString(1, "%" + nameToSearch + "%");
        res = statemnt.executeQuery();
        while (res.next()) {
            try {
                Item item = new Item();
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_name(res.getString(ITEM_NAME));
                Blob b = res.getBlob(ITEM_PIC);
                byte[] bytes = getBytes(b);
                item.setItem_pic(bytes);
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
                item.setItem_price(res.getInt(ITEM_PRICE));

                list.add(item);

            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
        close();
        return list;

    }

    @Override
    public ArrayList<Item> getItemByCat(String catToSearch) throws SQLException {
        ArrayList<Item> list = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {

            con = dataSource.getConnection();

            pst = con.prepareStatement("select * from item where catagory = ?");

            pst.setString(1, catToSearch);

            res = pst.executeQuery();

            while (res.next()) {
                try {
                    Item item = new Item();
                    item.setItem_id(res.getInt(ITEM_ID));
                    item.setItem_catagory(res.getString(ITEM_CATAGORY));
                    item.setItem_name(res.getString(ITEM_NAME));
                    Blob b = res.getBlob(ITEM_PIC);
                    byte[] bytes = getBytes(b);
                    item.setItem_pic(bytes);
                    item.setItem_quntity(res.getInt(ITEM_QUTITY));
                    item.setItem_price(res.getInt(ITEM_PRICE));

                    list.add(item);

                } catch (IOException ex) {
                    Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            return list;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;

        } finally {

            close();
        }

    }
 public ArrayList<Item> getItemByCat(String catToSearch,int index) throws SQLException {
        ArrayList<Item> list = new ArrayList<>();
        //Connection con ;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {

           

            pst = con.prepareStatement("select * from item where catagory = ? LIMIT 10 OFFSET ?");

            pst.setString(1, catToSearch);
            pst.setInt(2, index);

            res = pst.executeQuery();

            while (res.next()) {
                try {
                    Item item = new Item();
                    item.setItem_id(res.getInt(ITEM_ID));
                    item.setItem_catagory(res.getString(ITEM_CATAGORY));
                    item.setItem_name(res.getString(ITEM_NAME));
                    Blob b = res.getBlob(ITEM_PIC);
                    byte[] bytes = getBytes(b);
                    item.setItem_pic(bytes);
                    item.setItem_quntity(res.getInt(ITEM_QUTITY));
                    item.setItem_price(res.getInt(ITEM_PRICE));

                    list.add(item);

                } catch (IOException ex) {
                    Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            return list;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;

        } finally {

            close();
        }

    }

    @Override
    public ArrayList<Item> getItemByPrice(int from, int to) throws SQLException {

        ArrayList<Item> list = new ArrayList<>();

        con = dataSource.getConnection();

        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where price between ? and ?");

        statemnt.setInt(1, from);
        statemnt.setInt(2, to);
        res = statemnt.executeQuery();
        while (res.next()) {
            try {
                Item item = new Item();
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_name(res.getString(ITEM_NAME));
                Blob b = res.getBlob(ITEM_PIC);
                byte[] bytes = getBytes(b);
                item.setItem_pic(bytes);
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
                item.setItem_price(res.getInt(ITEM_PRICE));

                list.add(item);

            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
        
        close();
        return list;

    }

    int getQuntity(int id) throws SQLException {
        Item item = null;
        statemnt = con.prepareStatement("SELECT  qubtity FROM item where item_id = ?");
        statemnt.setInt(1, id);
        res = statemnt.executeQuery();
        int quntity = 0;
        while (res.next()) {
            quntity = res.getInt(ITEM_QUTITY);

        }
        
        return quntity;
    }

    public BuyOperation buy(ArrayList<ShopCardItem> list) throws SQLException {
        ArrayList<ShopCardItem> buyed = new ArrayList<>();
        BuyOperation buy = new BuyOperation();
        synchronized (this) {
            boolean allAvilible = true;
            for (ShopCardItem item : list) {
                if (getQuntity(item.getItem().getItem_id()) >= item.getQuntity()) {
                    buyed.add(item);

                } else {
                    allAvilible = false;
                    item.setQuntity(getQuntity(item.getItem().getItem_id()));
                    item.getItem().setItem_quntity(item.getQuntity());
                    buyed.add(item);
                }
            }
            if (allAvilible) {
                for (ShopCardItem item : list) {
                    decrementItem(item.getItem().getItem_id(), getQuntity(item.getItem().getItem_id()) - item.getQuntity());
                }
            }

            buy.setBuyed(buyed);
            buy.setDone(allAvilible);
        }
        return buy;
    }

    @Override
    public ArrayList<String> getAllCategories() throws SQLException {

        ArrayList<String> allCategories = new ArrayList<>();

       // con = dataSource.getConnection();

        statemnt = con.prepareStatement("select distinct catagory from item");

        res = statemnt.executeQuery();

        while (res.next()) {

            allCategories.add(res.getString(1));

        }
        close();
        return allCategories;

    }

    @Override
    public ArrayList<Item> getItemByNameInCategory(String nameToSearch, String currentCategory) throws SQLException {


 ArrayList<Item> list = new ArrayList<>();

        con = dataSource.getConnection();

        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where item_name like ?"
                + "and catagory = ?");

        statemnt.setString(1, "%" + nameToSearch + "%");
        statemnt.setString(2,currentCategory);
        res = statemnt.executeQuery();
        while (res.next()) {
            try {
                Item item = new Item();
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_name(res.getString(ITEM_NAME));
                Blob b = res.getBlob(ITEM_PIC);
                byte[] bytes = getBytes(b);
                item.setItem_pic(bytes);
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
                item.setItem_price(res.getInt(ITEM_PRICE));

                list.add(item);

            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
        close();
        return list;


    }

    public ArrayList<Item> getItemByPrice(int from, int to, String currentCategory) throws SQLException {

 ArrayList<Item> list = new ArrayList<>();

        con = dataSource.getConnection();

        statemnt = con.prepareStatement("SELECT  qubtity, price, item_name, item_id, catagory, pic FROM item where price between ? and ?  and catagory = ?");

        statemnt.setInt(1, from);
        statemnt.setInt(2, to);
        statemnt.setString(3, currentCategory);
        res = statemnt.executeQuery();
        while (res.next()) {
            try {
                Item item = new Item();
                item.setItem_id(res.getInt(ITEM_ID));
                item.setItem_catagory(res.getString(ITEM_CATAGORY));
                item.setItem_name(res.getString(ITEM_NAME));
                Blob b = res.getBlob(ITEM_PIC);
                byte[] bytes = getBytes(b);
                item.setItem_pic(bytes);
                item.setItem_quntity(res.getInt(ITEM_QUTITY));
                item.setItem_price(res.getInt(ITEM_PRICE));

                list.add(item);

            } catch (IOException ex) {
                Logger.getLogger(ItemDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
        
        close();
        return list;

    }

   ////////////////////////////////////////////
      public int getPagesNumber() throws SQLException{
        int items = 0;
        int pages = 0;
        if(dataSource!=null)
        con=dataSource.getConnection();
        statemnt = con.prepareStatement("SELECT count(item_id) AS rowcount FROM item ");
       
          res = statemnt.executeQuery();
          if(res.next())
        items =  res.getInt("rowcount");
    
           
               
               pages = (int) Math.ceil(items/9);
           
           
          
          return pages;
    }
    
    
    
    
    
    public int getPagesNumberByCat(String Category) throws SQLException{
        int items = 0;
        int pages = 0;
        if(dataSource!=null)
            con=dataSource.getConnection();
        statemnt = con.prepareStatement("SELECT count(item_id) AS rowcount FROM item where catagory = ? ");
         statemnt.setString(1, Category);
          res = statemnt.executeQuery();
    
           while (res.next()) {
               pages = (int) Math.ceil(res.getInt("rowcount")/9);
           
           }
          
          return pages;
    } 
}
