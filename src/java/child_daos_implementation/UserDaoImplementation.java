/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_implementation;

import child_daos_interfaces.UserDaoInterface;
import dtos.User;
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
public class UserDaoImplementation implements UserDaoInterface {

    private final DataSource dataSource;

    public UserDaoImplementation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User login(String email, String password) {

        User selectedUser = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {

            con = dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM user "
                    + "WHERE userEmail = ? AND userPassword = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, email);
            pst.setString(2, password);

            rs = pst.executeQuery();

            if (rs.next()) {
                InputStream s = null;
                Blob img = rs.getBlob(7);

                if (img != null) {
                    s = img.getBinaryStream();
                }

                selectedUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toString(), s, rs.getString(8), rs.getString(9), rs.getString(10), rs.getDouble(11), null);
                return selectedUser;

            } else {

                return null;
            }

        } catch (SQLException ex) {
            ex.getMessage();
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            close(con, rs, pst);

        }

    }

    @Override
    public boolean insert(User t) {

        Connection con = null;

        PreparedStatement pst = null;

        try {

            con = dataSource.getConnection();

            pst = con.prepareStatement("INSERT INTO user "
                    + "(userName,userEmail,userPassword,userAddress,userDob,userPic,userGender,userType,userJop,userCredit)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?)  ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, t.getUserName());
            pst.setString(2, t.getUserEmail());
            pst.setString(3, t.getUserPassword());
            pst.setString(4, t.getUserAddress());
            pst.setString(5, t.getUserDob());
            pst.setBlob(6, t.getUserPic());
            pst.setString(7, t.getUserGender());
            pst.setString(8, "user");
            pst.setString(9, t.getUserJop());
            pst.setDouble(10, t.getUserCredit());

            pst.executeUpdate();

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        } finally {

            close(con, null, pst);
        }

    }

    @Override
    public User select(User t) {
        User retrievedUser = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
           con = dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM user WHERE userEmail = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, t.getUserEmail());

            rs = pst.executeQuery();

            if (rs.next()) {
                
                InputStream s = null;
                Blob img = rs.getBlob(7);

                if (img != null) {
                    s = img.getBinaryStream();
                }

                retrievedUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toString(), s, rs.getString(8), rs.getString(9), rs.getString(10), rs.getDouble(11), null);
                return retrievedUser;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            ex.getMessage();
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);

            return retrievedUser;
        }
        finally {

            close(con, rs, pst);
        }
    }

    @Override
    public boolean update(User t) {
        Connection con = null;

        PreparedStatement pst = null;

        try {

            con = dataSource.getConnection();
            pst = con.prepareStatement("UPDATE user SET"
                    + " userName = ? ,userPassword = ?,"
                    + "userAddress = ? ,userDob = ? ,userPic = ?, "
                    + "userGender = ? ,userJop = ? ,userCredit = ? "
                    +"where userEmail = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, t.getUserName());
            pst.setString(2, t.getUserPassword());
            pst.setString(3, t.getUserAddress());
            pst.setString(4, t.getUserDob());
            pst.setBlob(5, t.getUserPic());
            pst.setString(6, t.getUserGender());
            pst.setString(7, t.getUserJop());
            pst.setDouble(8, t.getUserCredit());
            pst.setString(9,t.getUserEmail());

            pst.executeUpdate();

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {

            close(con, null, pst);

        }

    }

    @Override
    public ArrayList<User> convertResultSetToArrayList(ResultSet rs) {

        try {
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
//                InputStream s = null;
//                Blob img = rs.getBlob(7);
//                if(img!=null)
//                {
//                s = img.getBinaryStream();
//                
//                }
                User retrievedUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toString(), null, rs.getString(8), rs.getString(9), rs.getString(10), rs.getDouble(11), null);
                users.add(retrievedUser);
            }

            return users;

            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {

            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
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

    @Override
    public boolean checkIfEmailExists(String email) {

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            con = dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM user WHERE userEmail = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, email);

            rs = pst.executeQuery();

            if (rs.next()) {
                return true;

            } else {

                return false;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            
            return false;

        }
        finally{
            close(con, rs, pst);
        }

    }

    @Override
    public ArrayList<User> reterieveAll() {
        ArrayList<User> users = null;

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            con = dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM user", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();

            if (rs != null) {
                users = convertResultSetToArrayList(rs);
            }
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            close(con, rs, pst);
        }

        return users;

    }
}
