/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.UserDaoImplementation;
import dtos.User;
import java.io.IOException;
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
 * @author Mayada Saleh
 */
public class AdminShowUsers extends HttpServlet {

  
    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;

    @Override
    public void init() {

        userImpl = new UserDaoImplementation(dataSource);

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            listUsers(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AdminShowUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
   
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        // get users from UserDaoImplementation
        ArrayList<User> users = null;
        users = userImpl.reterieveAll();

        // add users to the request
        request.setAttribute("User_LIST", users);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("listusers/listUsersToAdmin.jsp");
        dispatcher.forward(request, response);
       

    }

}
