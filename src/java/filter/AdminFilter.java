/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import dtos.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author abanoub samy
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        System.out.println("ana fy el adminfilteeeerr");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession userSession = req.getSession(false);

        if (userSession != null) {
            User u = (User) userSession.getAttribute("logedInUser");

            if (u.getUserType().equals("admin")) {
                System.out.println("ana  5lst el admin filteeeerr");
                chain.doFilter(request, response);

            } else {

                System.out.println("msh admin yaallaa");
                //req.getRequestDispatcher("")
                res.sendRedirect("errorpage/error.jsp");
            }

        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
