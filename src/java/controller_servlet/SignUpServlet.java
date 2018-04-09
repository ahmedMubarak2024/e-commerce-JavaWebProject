/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.UserDaoImplementation;
import dtos.User;
import java.io.IOException;
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
public class SignUpServlet extends HttpServlet {

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

        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String password = request.getParameter("userPassword");
        String address = request.getParameter("userAddress");
       
        String userGender = request.getParameter("userGender");
        String userJob = request.getParameter("userJop");
        Double userCredit = Double.valueOf(request.getParameter("userCredit"));
        String userDob = request.getParameter("year")+"-"+request.getParameter("month")+"-"+request.getParameter("day");

        System.out.println(userName);
        System.out.println(userEmail);
        System.out.println(password);
        System.out.println(address);
        System.out.println(userDob);
        System.out.println(userGender);
        System.out.println(userJob);
        System.out.println(userCredit);

        if (userImpl.checkIfEmailExists(userEmail)) {

            //send redirect 3la el signup page
            
            
            
        } else {
            User newUser = new User();

            newUser.setUserName(userName);
            newUser.setUserEmail(userEmail);
            newUser.setUserPassword(password);
            newUser.setUserAddress(address);
            newUser.setUserDob(userDob);
            newUser.setUserGender(userGender);
            newUser.setUserJop(userJob);
            newUser.setUserCredit(userCredit);
            newUser.setUserPic(null);

            if (userImpl.insert(newUser)) {

                System.out.println("user inserted  successful");

                //create session
                
                HttpSession userSession = request.getSession();
                userSession.setAttribute("logedInUser", newUser);

                //create cookies
                Cookie emailCookie = new Cookie("userEmail", newUser.getUserEmail());
                Cookie passCookie = new Cookie("userPass", newUser.getUserPassword());

                emailCookie.setMaxAge(60 * 60 * 24 * 7);
                passCookie.setMaxAge(60 * 60 * 24 * 7);

                response.addCookie(emailCookie);
                response.addCookie(passCookie);

                response.sendRedirect("HomeServlet");
            }

        }

    }

}
