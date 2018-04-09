/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class DeleteItem extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;
    private ItemDaoImplementation itemImpl;
     @Override

    public void init() {

       
        itemImpl = new ItemDaoImplementation(dataSource);

    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
       
            
           itemImpl.deleteItem(Integer.parseInt(request.getParameter("itemId")));
            
            System.out.println("deleted successs");
            
            response.sendRedirect("HomeServlet");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DeleteItem.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            itemImpl.close();
        }
    }

   
}
