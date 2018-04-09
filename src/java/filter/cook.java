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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usama
 */
// filter for user functions and to insure there is a session
public class cook implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter initialized");
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURL().toString();
        String queryString = req.getQueryString();
        Cookie[] cookies = req.getCookies();
        System.out.println("cookie filter url :" + url + " String " + queryString);
        if (cookies != null) {
            if (queryString != null && queryString.contains("chk")) {
                queryString = queryString.substring(0, queryString.indexOf("chk"));
                res.sendRedirect(url + queryString);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (req.getParameter("chk") == null) {
                Cookie checkCookie = new Cookie("Check", " ");
                checkCookie.setMaxAge(60 * 60);
                res.addCookie(checkCookie);
                if (queryString == null) {
                    res.sendRedirect(url + "?chk=chk");
                } else {
                    res.sendRedirect(url + queryString + "&chk=chk");
                }
            } else {
                res.sendRedirect("errorpage/enablecookie.jsp");
            }
        }

    }

    public void destroy() {
        System.out.println("Filter destroyed");
        this.filterConfig = null;
    }
}