/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_implementation;

import child_daos_interfaces.UserHistoryDaoInterface;
import dtos.UserHistory;
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
public class UserHistoryDaoImplementation implements UserHistoryDaoInterface{
    
    
    private  DataSource dataSource ;
    private  Connection con;

    public UserHistoryDaoImplementation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserHistoryDaoImplementation(Connection con) {
        this.con = con;
    }
    
    

    @Override
    public boolean insert(UserHistory t) throws SQLException {

       
        PreparedStatement pst =null;
       
        
        try
        {
        pst = con.prepareStatement("INSERT INTO user_history VALUES (?,?,?,?,?,?)");
        
        pst.setString(1, t.getUserEmail());
        pst.setString(2, t.getItemName());
        pst.setInt(3, t.getQuantity());
        pst.setTimestamp(4, t.getTime());
        pst.setDouble(5, t.getItemPrice());
        pst.setString(6, t.getItemCategory());
        
        pst.executeUpdate();
        return  true;
        }
        catch(Exception ex)
        {
            
            ex.printStackTrace();
            return false;
        }
        


    }
    
    
    
    public ArrayList<UserHistory> selectAllHistoryOfSpecificUser(String  userEmail)throws SQLException
    {
        
        ArrayList<UserHistory> allHistory = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        con = dataSource.getConnection();
        
        try
        {
          pst = con.prepareStatement("SELECT * FROM user_history WHERE userEmail = ?");
          
          pst.setString(1, userEmail);
          
          rs = pst.executeQuery();
          
          while(rs.next())
          {
              UserHistory uh = new UserHistory();
              uh.setUserEmail(rs.getString(1));
              uh.setItemName(rs.getString(2));
              uh.setQuantity(rs.getInt(3));
              uh.setTime(rs.getTimestamp(4));
              uh.setItemPrice(rs.getDouble(5));
              uh.setItemCategory(rs.getString(6));
              
              allHistory.add(uh);
              
              
          }
          
          return  allHistory;
            
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            
            return  null;
            
            
        }
        finally
        {
            
            close(con, rs, pst);
        }
        
    }

    @Override
    public UserHistory select(UserHistory t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(UserHistory t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<UserHistory> convertResultSetToArrayList(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<UserHistory> reterieveAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
          public void close() {

        try {

            if (con != null) {

                con.close();
            }

            
        } catch (SQLException e) {

            e.getMessage();
        }

    }
    
}
