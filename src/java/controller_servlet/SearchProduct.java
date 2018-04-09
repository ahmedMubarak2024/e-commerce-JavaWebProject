/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import dtos.Item;
import java.io.IOException;
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
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class SearchProduct extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private ItemDaoImplementation itemImpl;

    @Override

    public void init() {

        itemImpl = new ItemDaoImplementation(dataSource);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchMethod = request.getParameter("serachMethod");
        System.out.println("search method ....  " + searchMethod);
        switch (searchMethod) {

            case "search by name":

        {
            try {
                searchByName(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SearchProduct.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                itemImpl.close();
            }
        }

                break;

            case "search by price": {
                try {
                    searchByPrice(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(SearchProduct.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    itemImpl.close();
                }
            }
            break;

        }

    }

    private void searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String nameToSearch = request.getParameter("Search");
        String currentCategory = request.getParameter("currentCategory");
        
        System.out.println("curent category in search by name method : "+currentCategory);

        if (nameToSearch.trim() != null) {

            if (currentCategory.trim() != "") {
                
                ArrayList<Item> resultItems = itemImpl.getItemByNameInCategory(nameToSearch,currentCategory);

                    // System.out.println("item nameeeeeeeee"+resultItems.get(0).getItem_name());
                    request.setAttribute("result", resultItems);

                    // response.sendRedirect("HomeServlet?res");
                    RequestDispatcher dis = request.getRequestDispatcher("HomeServlet?res");
                    dis.forward(request, response);
                

            } else {

                try {

                    
                    
                    ArrayList<Item> resultItems = itemImpl.getItemByName(nameToSearch);

                    // System.out.println("item nameeeeeeeee"+resultItems.get(0).getItem_name());
                    request.setAttribute("result", resultItems);

                    // response.sendRedirect("HomeServlet?res");
                    RequestDispatcher dis = request.getRequestDispatcher("HomeServlet?res");
                    dis.forward(request, response);

                } catch (SQLException ex) {
                    Logger.getLogger(SearchProduct.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    private void searchByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
   ArrayList<Item> resultItems = null;
        int from = Integer.parseInt(request.getParameter("from"));
        int to = Integer.parseInt(request.getParameter("to"));
        String currentCategory = request.getParameter("currentCategory");
        
        
        if(currentCategory.trim() != "")
        {
           resultItems = itemImpl.getItemByPrice(from, to,currentCategory);  
        }
        else
        {
           resultItems = itemImpl.getItemByPrice(from, to);
        
        
        }

        // System.out.println("item nameeeeeeeee"+resultItems.get(0).getItem_name());
        request.setAttribute("result", resultItems);

        // response.sendRedirect("HomeServlet?res");
        RequestDispatcher dis = request.getRequestDispatcher("HomeServlet?res");
        dis.forward(request, response);
    }

}
