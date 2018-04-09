/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import child_daos_implementation.UserDaoImplementation;
import dtos.User;
import java.io.IOException;
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
public class FilterAll implements Filter {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private UserDaoImplementation userImpl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        userImpl = new UserDaoImplementation(dataSource);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        System.out.println("ana fy el allfilteeeerr");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

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

                            userSession.setAttribute("logedInUser", newUser);
                            chain.doFilter(request, response);

                        }

                    }

                } else {

                    res.sendRedirect("loginpage/login.jsp");
                }
            } else {

                res.sendRedirect("loginpage/login.jsp");
            }

        }
        
        
        
        else {
            chain.doFilter(request, response);
          
        }
    }

    @Override
    public void destroy() {
    }

}
