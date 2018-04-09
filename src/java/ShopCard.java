/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import child_daos_implementation.BuyOperation;
import child_daos_implementation.ItemDaoImplementation;
import child_daos_implementation.UserCardDaoImplemntation;
import child_daos_implementation.UserDaoImplementation;
import child_daos_implementation.UserHistoryDaoImplementation;
import dtos.Item;
import dtos.JspShopCardItem;
import dtos.ShopCardItem;
import dtos.User;
import dtos.UserHistory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Altysh
 */
@WebServlet(urlPatterns = {"/ShopCard"})
public class ShopCard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;
    private UserCardDaoImplemntation uc;

    @Override

    public void init() {

        userImpl = new UserDaoImplementation(dataSource);
        uc = new UserCardDaoImplemntation(dataSource);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemDaoImplementation item = null;
        try {
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("logedInUser");
            String id = request.getParameter("obj");
            String state = request.getParameter("state");
            // System.out.println(user.getUserName());
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(id);
            String name = (String) json.get("n");
            Long i = (Long)json.get("id");
          
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/e_commerce", "root", "");
            item = new ItemDaoImplementation();
            item.setCon(con);
            Item shopItem = item.select(i.intValue());
            ShopCardItem element = new ShopCardItem();
            element.setItem(shopItem);
            System.out.println(id);
            element.setQuntity((int) ((long) json.get("qu")));
            if (state == null) {
                user.add_to_shop_card(element);
                JSONObject total = new JSONObject();
                total.put("total", calculate(user.getUserShopCart()));
                response.getWriter().println(total);

            } else {
                user.remove_element(element);
                JSONObject total = new JSONObject();
                total.put("total", calculate(user.getUserShopCart()));
                response.getWriter().println(total);
            }
            System.out.println(user.getUserShopCart());
            //response.getWriter().print("<html></html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (item != null) {
                item.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemDaoImplementation itemDao = null;
        try {
            String state = request.getParameter("state");
            HttpSession session = request.getSession(false);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/e_commerce", "root", "");
            itemDao = new ItemDaoImplementation();
            itemDao.setCon(con);
            if (session.getAttribute("logedInUser") != null) {
                User user = ((User) session.getAttribute("logedInUser"));
                ArrayList<ShopCardItem> list = user.getUserShopCart();
                JSONArray ja = new JSONArray();
                switch (state) {
                    case "card":

                        //JSONArray ja = new JSONArray();
                        for (ShopCardItem item : list) {
                            JSONObject obj = new JSONObject();
                            obj.put("name", item.getItem().getItem_name());
                            obj.put("id", item.getItem().getItem_id());
                            obj.put("quntity", item.getItem().getItem_quntity());
                            obj.put("price", item.getItem().getItem_price());
                            obj.put("qu", item.getQuntity());

                            //obj.put("id", item.getItem().getItem_id());
                            ja.add(obj);
                        }
                        response.getWriter().println(ja);
                        break;
                    case "buy":
                        BuyOperation buy = itemDao.buy(list);
                        // JSONArray ja = new JSONArray();
                        for (ShopCardItem item : buy.getBuyed()) {
                            JSONObject obj = new JSONObject();
                            obj.put("name", item.getItem().getItem_name());
                            obj.put("id", item.getItem().getItem_id());
                            obj.put("quntity", item.getItem().getItem_quntity());
                            obj.put("price", item.getItem().getItem_price());
                            obj.put("qu", item.getQuntity());

                            //obj.put("id", item.getItem().getItem_id());
                            ja.add(obj);
                        }
                        JSONObject main = new JSONObject();
                        main.put("array", ja);
                        // main.put("bal", user.getUserCredit());
                        main.put("status", buy.isDone());
                        main.put("total", calculate(buy.getBuyed()));

                        if (buy.isDone()) {
                            user.setUserCredit(user.getUserCredit() - calculate(buy.getBuyed()));
                            System.out.println(user.getUserCredit());
                            userImpl.update(user);
                            uc.deleteAll(user.getUserId());

                            saveUserHistoryInDb(user.getUserEmail(), list);

                            for (ShopCardItem sci : list) {

                            }
                            list.clear();
                        } else {
                            list = buy.getBuyed();
                            user.setUserShopCart(list);
                            //session.setAttribute("logedInUser", list);
                        }
                        main.put("bal", user.getUserCredit());
                        response.getWriter().println(main);
                        break;
                    case "save":
                        System.out.println("save");
                        putCardItemsInDb(request, response);
                        break;
                    case "total":
                        JSONObject total = new JSONObject();
                        total.put("total", calculate(list));
                        total.put("balnce", user.getUserCredit());
                        response.getWriter().println(total);
                        break;

                    //JSONObject mainObj = new JSONObject();
                    // mainObj.put("card", ja);
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (itemDao != null) {
                itemDao.close();
            }
        }
    }

    public void putCardItemsInDb(HttpServletRequest request, HttpServletResponse response) throws SQLException {

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

    public int calculate(ArrayList<ShopCardItem> list) {
        int total = 0;
        total = list.stream().map((item) -> (item.getQuntity() * item.getItem().getItem_price())).reduce(total, Integer::sum);
        System.out.println(total);
        return total;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void saveUserHistoryInDb(String userEmail, ArrayList<ShopCardItem> list) {
UserHistoryDaoImplementation uh = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/e_commerce", "root", "");
            
            
            if (list != null) {
                uh = new UserHistoryDaoImplementation(con);
                
                for (ShopCardItem sItem : list) {
                    
                    UserHistory u = new UserHistory();
                    u.setUserEmail(userEmail);
                    u.setItemName(sItem.getItem().getItem_name());
                    u.setItemCategory(sItem.getItem().getItem_catagory());
                    u.setItemPrice(sItem.getItem().getItem_price());
                    u.setQuantity(sItem.getQuntity());
                    u.setTime(new Timestamp(System.currentTimeMillis()));
                    
                    uh.insert(u);
                    
                }
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShopCard.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(uh!=null)
            uh.close();
        }

    }

}
