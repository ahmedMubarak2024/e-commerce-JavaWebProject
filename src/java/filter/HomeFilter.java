/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import child_daos_implementation.UserCardDaoImplemntation;
import child_daos_implementation.UserDaoImplementation;
import dtos.ShopCardItem;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author abanoub samy
 */
public class HomeFilter implements Filter {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;
    
    private UserCardDaoImplemntation uc;

 

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        userImpl = new UserDaoImplementation(dataSource);
         uc = new UserCardDaoImplemntation(dataSource);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        System.out.println("ana fy el home filteeeerr");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession userSession = req.getSession();
        
        

        if (userSession.getAttribute("logedInUser") == null) {

            Cookie cookies[] = req.getCookies();

            if (cookies != null) {

                if (cookies.length > 1) {

                    for (Cookie temp : cookies) {
                        if (temp.getName().equals("userEmail")) {
                            User newUser = new User();
                            newUser.setUserEmail(temp.getValue());
                            newUser = userImpl.select(newUser);

                           
                            try {
                                fillUserCard(newUser);
                            } catch (SQLException ex) {
                                Logger.getLogger(HomeFilter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                             userSession.setAttribute("logedInUser", newUser);
                           

                        }

                    }
                }
            }
        }
        
      
        System.out.println("el saf7a hatft7 2ho");
         HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Cache-Control", "no-cache, no-store, must");
          res.setHeader("Pragma", "no-cache");
         chain.doFilter(request, response);
        
        
        
          res.setHeader("Cache-Control", "no-cache, no-store, must");
          res.setHeader("Pragma", "no-cache");
        System.out.println("response was set");
        
    }

    @Override
    public void destroy() {


    }

    private void fillUserCard(User newUser) throws SQLException, IOException {

      
          ArrayList<ShopCardItem> s =uc.getCardItems(newUser.getUserId());
          System.out.println("size b2a fy eh"+s.size());
          newUser.setUserShopCart(s);


    }

   
}
