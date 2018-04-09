/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author abanoub samy
 */
public class RunServletFirst implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        System.out.println("ana fy el run serv first filteeeerr");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        switch (req.getServletPath()) {

            case "/listusers/listUsersToAdmin.jsp":
                System.out.println("shakolo infinite loop");
                res.sendRedirect("../AdminShowUsers");
                break;

            case "/products.jsp":

                res.sendRedirect("HomeServlet");

                break;

            case "/listusers/viewUserProfile.jsp":

                res.sendRedirect("../AdminShowUsers");

                break;
                
               

        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
