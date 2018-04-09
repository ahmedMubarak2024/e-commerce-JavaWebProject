/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author abanoub samy
 */
public class User {

    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userAddress;
    private String userDob;
    private InputStream userPic;
    private String userGender;
    private String userType;
    private String userJop;
    private double userCredit;
    private ArrayList<ShopCardItem> userShopCart;

    public User() {
        userShopCart = new ArrayList<>();
    }

    public User(Integer userId, String userName, String userEmail, String userPassword, String userAddress, String userDob, InputStream userPic, String userGender, String userType, String userJop, double userCredit, ArrayList<ShopCardItem> userShopCart) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userDob = userDob;
        this.userPic = userPic;
        this.userGender = userGender;
        this.userType = userType;
        this.userJop = userJop;
        this.userCredit = userCredit;
        this.userShopCart = new ArrayList<>();
    }

    public User(String userName, String userEmail, String userPassword, String userAddress, String userDob, InputStream userPic, String userGender, String userJop, double userCredit, ArrayList<ShopCardItem> userShopCart) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddress = userAddress;
        this.userDob = userDob;
        this.userPic = userPic;
        this.userGender = userGender;
        this.userJop = userJop;
        this.userCredit = userCredit;
        this.userShopCart = new ArrayList<>();
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the userAddress
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * @return the userDob
     */
    public String getUserDob() {
        return userDob;
    }

    /**
     * @param userDob the userDob to set
     */
    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    /**
     * @return the userPic
     */
    public InputStream getUserPic() {
        return userPic;
    }

    /**
     * @param userPic the userPic to set
     */
    public void setUserPic(InputStream userPic) {
        this.userPic = userPic;
    }

    /**
     * @return the userGender
     */
    public String getUserGender() {
        return userGender;
    }

    /**
     * @param userGender the userGender to set
     */
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the userJop
     */
    public String getUserJop() {
        return userJop;
    }

    /**
     * @param userJop the userJop to set
     */
    public void setUserJop(String userJop) {
        this.userJop = userJop;
    }

    /**
     * @return the userCredit
     */
    public double getUserCredit() {
        return userCredit;
    }

    /**
     * @param userCredit the userCredit to set
     */
    public void setUserCredit(double userCredit) {
        this.userCredit = userCredit;
    }

    /**
     * @return the userShopCart
     */
    public ArrayList<ShopCardItem> getUserShopCart() {
        return userShopCart;
    }

    /**
     * @param userShopCart the userShopCart to set
     */
    public void setUserShopCart(ArrayList<ShopCardItem> userShopCart) {
        this.userShopCart = userShopCart;
    }
    public void add_to_shop_card(ShopCardItem newItem)
    {
        boolean add = true;
        for(ShopCardItem item:userShopCart)
        {
            if(item.getItem().getItem_id() == newItem.getItem().getItem_id())
            {
                item.setQuntity(newItem.getQuntity());
                add = false;
                break;
            }
            
        }
        if(add)
        {
            userShopCart.add(newItem);
        }
    }
    public void remove_element(ShopCardItem dropedItem)
    {
        for(int i = 0;i<userShopCart.size();i++)
        {
            if(userShopCart.get(i).item.getItem_id()==dropedItem.item.getItem_id())
            {
               userShopCart.remove(i);
               break;
            }
        }
       
    }

}
