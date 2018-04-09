/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_implementation;

import static child_daos_interfaces.ItemDaoInterface.ITEM_CATAGORY;
import static child_daos_interfaces.ItemDaoInterface.ITEM_ID;
import static child_daos_interfaces.ItemDaoInterface.ITEM_NAME;
import static child_daos_interfaces.ItemDaoInterface.ITEM_PIC;
import static child_daos_interfaces.ItemDaoInterface.ITEM_PRICE;
import static child_daos_interfaces.ItemDaoInterface.ITEM_QUTITY;
import child_daos_interfaces.UserCardDaoInterface;
import dtos.Item;
import dtos.ShopCardItem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class UserCardDaoImplemntation implements UserCardDaoInterface {

    private final DataSource dataSource;

    public UserCardDaoImplemntation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void close(Connection con, ResultSet rs, PreparedStatement pst) {

        try {

            if (con != null) {

                con.close();
            }

            if (rs != null) {

                rs.close();

            }

            if (pst != null) {
                pst.close();

            }
        } catch (SQLException e) {

            e.getMessage();
        }

    }

    public boolean insert(int userId, ShopCardItem t) throws SQLException {

        Connection con = null;
        PreparedStatement pst = null;

        con = dataSource.getConnection();
        try {
            pst = con.prepareStatement("INSERT INTO user_card VALUES (?,?,?) ");
            pst.setInt(1, userId);
            pst.setInt(2, t.getItem().getItem_id());
            pst.setInt(3, t.getQuntity());

            pst.executeUpdate();
            return true;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        } finally {

            close(con, null, pst);
        }

    }

    public boolean deleteAll(int UserId) throws SQLException {

        Connection con = null;
        PreparedStatement pst = null;

        con = dataSource.getConnection();

        try {
            pst = con.prepareStatement("DELETE FROM user_card where userId = ?");
            pst.setInt(1, UserId);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {

            close(con, null, pst);
        }

    }

    public ArrayList<ShopCardItem> getCardItems(int userId) throws SQLException, IOException {

        ArrayList<ShopCardItem> shopCarditems = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        con = dataSource.getConnection();
        pst = con.prepareStatement("SELECT quantity,  qubtity, price, item_name, item_id, catagory, pic FROM item i ,user_card c WHERE i.item_id = c.itemId and userId =?");

        pst.setInt(1, userId);

        rs = pst.executeQuery();

        if (rs == null) {
            return null;
        }

        while (rs.next()) {
            ShopCardItem s = new ShopCardItem();
            s.setQuntity(rs.getInt(1));

            Item item = new Item();
            item.setItem_id(rs.getInt(ITEM_ID));
            item.setItem_catagory(rs.getString(ITEM_CATAGORY));
            item.setItem_name(rs.getString(ITEM_NAME));
            Blob b = rs.getBlob(ITEM_PIC);
            byte[] bytes = getBytes(b);
            item.setItem_pic(bytes);
            item.setItem_quntity(rs.getInt(ITEM_QUTITY));
            item.setItem_price(rs.getInt(ITEM_PRICE));

            s.setItem(item);
            shopCarditems.add(s);

        }

        return shopCarditems;

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
    public boolean update(ShopCardItem t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ShopCardItem> convertResultSetToArrayList(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ShopCardItem> reterieveAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(ShopCardItem t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ShopCardItem select(ShopCardItem t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
