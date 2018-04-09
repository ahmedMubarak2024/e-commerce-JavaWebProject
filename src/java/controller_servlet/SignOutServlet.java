/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import child_daos_implementation.UserCardDaoImplemntation;
import child_daos_implementation.UserDaoImplementation;
import dtos.JspShopCardItem;
import dtos.ShopCardItem;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class SignOutServlet extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private  UserCardDaoImplemntation uc;

    @Override

    public void init() {

        uc = new UserCardDaoImplemntation(dataSource);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("hi im in sig out servlet");

        Cookie[] allCookis = request.getCookies();

        if (allCookis != null) {
            System.out.println("cookies not equal null");
            for (Cookie c : allCookis) {
                if (c.getName().equals("userEmail") || c.getName().equals("userPass")) {

                    c.setMaxAge(0);
                   c.setValue("");
                    response.addCookie(c);
                    System.out.println("cookies deleted");
                }

            }

        }

        try {
            putCardItemsInDb(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SignOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpSession userSession = request.getSession(false);
        userSession.invalidate();

        response.sendRedirect("HomeServlet");
    }

    public  void putCardItemsInDb(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        HttpSession userSession = request.getSession();
        User user = (User) userSession.getAttribute("logedInUser");

        if (user != null) {
//clear card data in data base
            uc.deleteAll(user.getUserId());

            ArrayList<JspShopCardItem> card = new ArrayList<>();
            for (ShopCardItem item : user.getUserShopCart()) {

                uc.insert(user.getUserId(), item);

            }

        }

    }
}
