/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import child_daos_implementation.UserDaoImplementation;
import dtos.Item;
import dtos.JspShopCardItem;
import dtos.ShopCardItem;
import dtos.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import utils.ArrayUtail;

/**
 *
 * @author abanoub samy
 */
public class HomeServlet extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;

    private ItemDaoImplementation itemImpl;

    @Override

    public void init() {

        userImpl = new UserDaoImplementation(dataSource);
        itemImpl = new ItemDaoImplementation(dataSource);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String theCommand = request.getParameter("command");

        if (theCommand != null) {

            System.out.println("the commaands" + theCommand);
           
                showListByCategory(request, response);
           

        } else {
            ItemDaoImplementation item = null;
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/e_commerce", "root", "");
                item = new ItemDaoImplementation();
                item.setCon(con);

                System.out.println("hangyb el dataaaaaaaaaaaa");
             int pages = item.getPagesNumber();
                request.setAttribute("page",pages + "");
                request.setAttribute("view", "all");
                System.out.println("hit");
                int pagenumber = 0;
                if (request.getParameter("page") != null) {
                    pagenumber = Integer.parseInt(request.getParameter("page"));
                }
                if (pagenumber < 0) {
                    pagenumber = 0;
                }
                if(pages<pagenumber){
                    pagenumber=pages;
                }
                request.setAttribute("pagenumber", pagenumber + "");
                ArrayList<Item> list = item.getItem(pagenumber * 9);


                System.out.println("gbna el dataaaaaaaaaaaaaaaaaa");

                //System.out.println(" list size is :   "+list.size());
                //  System.out.println("list size after addind arrayutail : "+ArrayUtail.getArray(list).size());
                getcategories(request, response);
                request.setAttribute("list", ArrayUtail.getArray(list));

                checkLogIn(request, response);

            } catch (SQLException ex) {
                Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (item != null) {
                    item.close();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String check = request.getQueryString();
        if (check != null && check.equals("res")) {

            ArrayList<Item> list2 = (ArrayList<Item>) request.getAttribute("result");
            request.setAttribute("list", ArrayUtail.getArray(list2));

            try {
                getcategories(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            checkLogIn(request, response);

        }

    }

    public void checkLogIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession userSession = request.getSession();

        User user = (User) userSession.getAttribute("logedInUser");

        if (user != null) {

            updateCard(user.getUserShopCart());
            ArrayList<JspShopCardItem> card = new ArrayList<>();
            for (ShopCardItem item : user.getUserShopCart()) {
                JspShopCardItem cardItem = new JspShopCardItem();
                cardItem.setAllQutitiy(item.getItem().getItem_quntity());
                cardItem.setItemcount(item.getQuntity());
                cardItem.setId(item.getItem().getItem_id());
                cardItem.setName(item.getItem().getItem_name());
                cardItem.setPrice(item.getItem().getItem_price());
                cardItem.setTotalPrice(item.getQuntity() * item.getItem().getItem_price());
                card.add(cardItem);
            }
            request.setAttribute("balance", user.getUserCredit());
            request.setAttribute("card", card);
            request.setAttribute("total", calculate(user.getUserShopCart()));

        }

        RequestDispatcher d = request.getRequestDispatcher("/products.jsp");
        d.forward(request, response);

    }

    public int calculate(ArrayList<ShopCardItem> list) {
        int total = 0;
        for (ShopCardItem item : list) {
            total += (item.getQuntity() * item.getItem().getItem_price());
        }
        System.out.println(total);
        return total;
    }

    void updateCard(ArrayList<ShopCardItem> list) {
        ItemDaoImplementation item = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/e_commerce", "root", "");
            item = new ItemDaoImplementation();
            item.setCon(con);
            for (ShopCardItem data : list) {
                Item i = item.select(data.getItem());
                data.setItem(i);

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (item != null) {
                item.close();
            }
        }
    }

    private void getcategories(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {

        ItemDaoImplementation item;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/e_commerce", "root", "");
        item = new ItemDaoImplementation();
        item.setCon(con);

        ArrayList<String> cat = item.getAllCategories();

        item.close();

        request.setAttribute("categ", cat);

    }

    public void showListByCategory(HttpServletRequest request, HttpServletResponse response)  {
       ItemDaoImplementation itemImpl = null;
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/e_commerce", "root", "");
            itemImpl = new ItemDaoImplementation();
            itemImpl.setCon(con);
            String categoryName = request.getParameter("categoryName");
            request.setAttribute("cata", categoryName);
            request.setAttribute("view", "catgory");
            System.out.println("category name : " + categoryName);
            int pages = itemImpl.getPagesNumberByCat(categoryName);
            request.setAttribute("page",pages + "");
            System.out.println("hit");
            int pagenumber = 0;
            if (request.getParameter("page") != null) {
                pagenumber = Integer.parseInt(request.getParameter("page"));
            }
            if (pagenumber < 0) {
                pagenumber = 0;
            }
            if(pages<pagenumber){
                pagenumber=pages;
            }
            request.setAttribute("pagenumber", pagenumber + "");
            // ArrayList<Item> list = item.getItem(pagenumber * 9);
            
            ArrayList<Item> list = itemImpl.getItemByCat(categoryName,pagenumber*9);
            
            request.setAttribute("list", ArrayUtail.getArray(list));
            request.setAttribute("currentCategory", categoryName);
            
            getcategories(request, response);
            checkLogIn(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {if(itemImpl!=null)
            itemImpl.close();
        }

    }

}
