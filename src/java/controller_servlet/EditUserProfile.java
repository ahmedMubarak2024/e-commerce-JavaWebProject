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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class EditUserProfile extends HttpServlet {

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
        
        System.out.println(userName);
        System.out.println(userEmail);
        System.out.println(password);
        System.out.println(address);
        System.out.println(userJob);
        System.out.println(userCredit);
        
          User newUser = new User();

            newUser.setUserName(userName);
            newUser.setUserEmail(userEmail);
            newUser.setUserPassword(password);
            newUser.setUserAddress(address);
            newUser.setUserGender(userGender);
            newUser.setUserJop(userJob);
            newUser.setUserCredit(userCredit);
            newUser.setUserDob("2001-01-01");
            HttpSession userSession2 = request.getSession();
                User l = (User) userSession2.getAttribute("logedInUser");
            
           
             if(userImpl.update(newUser))
             {
                 
                 System.out.println("user updated successfully");
                 newUser = userImpl.select(newUser);
                 newUser.setUserShopCart(l.getUserShopCart());
                 HttpSession userSession = request.getSession();
                 userSession.setAttribute("logedInUser", newUser);
                 response.sendRedirect("HomeServlet");
                 
             }
             
             else
             {
                 System.out.println("error in updateding user");
                 
             }
      
             
           
            
      
      
    }

}
