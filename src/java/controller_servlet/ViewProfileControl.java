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
public class ViewProfileControl extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;

    @Override
    public void init() {

        userImpl = new UserDaoImplementation(dataSource);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //System.out.println("ana fy view profile control");
        String theCommand = request.getParameter("command");
        String button1 = request.getParameter("Search");
        System.out.println(theCommand);
        System.out.println(button1);

        //if (button1 != null) {
        System.out.println(theCommand);
        if (theCommand == null) {
            theCommand = "LIST";
        }

        switch (theCommand) {

            case "LIST":

                try {
                    listUsers(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ViewProfileControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            case "SELECTONE":

                try {
                    loadSelectedUser(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ViewProfileControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            case "LOAD":

                try {
                    loadUsers(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ViewProfileControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            default: {
                try {
                    listUsers(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(ViewProfileControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        ArrayList<User> users = null;
        users = userImpl.reterieveAll();

        request.setAttribute("Users_LIST", users);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/listusers/listUsersToAdmin.jsp");
        dispatcher.forward(request, response);

    }

    private void loadUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        String theUserMail = request.getParameter("usermail");
        
        System.out.println("user email "+theUserMail);

        User t = new User();
        t.setUserEmail(theUserMail);
        try{
        t = userImpl.select(t);
        System.out.println(t.getUserId());
        System.out.println(t.getUserCredit());
        request.setAttribute("ViewedUser", t);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listusers/viewUserProfile.jsp");
        dispatcher.forward(request, response);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void loadSelectedUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        ArrayList<User> t = new ArrayList<>();

        String theUserMail = request.getParameter("Search");
        //String theUserName = request.getParameter("username");
        User e = new User();

        e.setUserEmail(theUserMail);
        //t.setUserName(theUserName);
        e = userImpl.select(e);

        //System.out.println("11111111111111");
        if (e == null) {

            // System.out.println("222222222222222");
            ArrayList<User> users = null;
            users = userImpl.reterieveAll();
            // add users to the request
            request.setAttribute("User_LIST", users);
            // send to JSP page (view)
            RequestDispatcher dispatcher = request.getRequestDispatcher("listusers/listUsersToAdmin.jsp?notUser=notFound");
            dispatcher.forward(request, response);

        } else {
            t.add(e);
            System.out.println(theUserMail);
            System.out.println("bbbbbbbbbbbbbbbbbbbbbh");
            request.setAttribute("User_LIST", t);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listusers/listUsersToAdmin.jsp");
            dispatcher.forward(request, response);
        }
    }

}
