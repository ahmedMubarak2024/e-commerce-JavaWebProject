/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import child_daos_implementation.UserCardDaoImplemntation;
import child_daos_implementation.UserDaoImplementation;
import dtos.ShopCardItem;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Mayada Saleh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    //Mayada
    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;
    private UserCardDaoImplemntation uc;

    @Override
    public void init() {

        userImpl = new UserDaoImplementation(dataSource);
        uc = new UserCardDaoImplemntation(dataSource);
    }

    
   

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userEmail = request.getParameter("userEmail");
        String password = request.getParameter("userPassword");

        User newUser = new User();

        newUser = userImpl.login(userEmail, password);

        if (newUser != null) {

            //create session
            HttpSession userSession = request.getSession();
            
            try {
                fillUserCard(newUser);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            userSession.setAttribute("logedInUser", newUser);

            //create cookies
            Cookie emailCookie = new Cookie("userEmail", newUser.getUserEmail());
            Cookie passCookie = new Cookie("userPass", newUser.getUserPassword());

            emailCookie.setMaxAge(60 * 60 * 24 * 7);
            passCookie.setMaxAge(60 * 60 * 24 * 7);

            response.addCookie(emailCookie);
            response.addCookie(passCookie);

            response.sendRedirect("HomeServlet");

        } else {

            //user not exist in data base
            response.sendRedirect("loginpage/login.jsp?notUser=1");

        }

    }
    
    
      private void fillUserCard(User newUser) throws SQLException, IOException {

      
          ArrayList<ShopCardItem> s = uc.getCardItems(newUser.getUserId());
          System.out.println("size b2a fy eh"+s.size());
          newUser.setUserShopCart(s);


    }

}
