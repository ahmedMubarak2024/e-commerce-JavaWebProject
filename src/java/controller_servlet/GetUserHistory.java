/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.UserDaoImplementation;
import child_daos_implementation.UserHistoryDaoImplementation;
import dtos.UserHistory;
import java.io.IOException;
import java.io.PrintWriter;
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
public class GetUserHistory extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    UserHistoryDaoImplementation userHistory = null;

    @Override
    public void init() {

        userHistory = new UserHistoryDaoImplementation(dataSource);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String theUserMail = request.getParameter("usermail");

        try {

            
            ArrayList<UserHistory> uh = userHistory.selectAllHistoryOfSpecificUser(theUserMail);
            if(uh != null)
            {
            request.setAttribute("userHistroyList",uh );
            RequestDispatcher dispatcher = request.getRequestDispatcher("listusers/listUserHistory.jsp");
            dispatcher.forward(request, response);

            
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetUserHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
