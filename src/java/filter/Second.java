/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 * @author Altysh
 */
public class Second implements Filter {
    
   
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpResp = (HttpServletRequest) request;        
     Cookie[] co =  httpResp.getCookies();
     boolean cookieIsOn = false;
     if(co!=null)
     {
//         for(Cookie cookie:co)
//         {
//           if(cookie.getName().equals("check")) 
//           {
//               cookieIsOn = true;
//               break;
//           }
//         }
         cookieIsOn=true;
         
     }
     if(cookieIsOn)
         {
             chain.doFilter(request, response);
         }
     else {
         HttpServletResponse hres =(HttpServletResponse)response;
         
         if (!httpResp.getServletPath().equals("/errorpage/enablecookie.jsp")&&!httpResp.getServletPath().contains("/css")&&!httpResp.getServletPath().contains("/font")&&!httpResp.getServletPath().contains("/images")&&!httpResp.getServletPath().contains("/js")) {
                httpResp.getRequestDispatcher("/errorpage/enablecookie.jsp").forward(request, response);
             //  hres.sendRedirect("errorpage/enablecookie.jsp");
            }else{
                chain.doFilter(request, response);
            }
         //httpResp.getRequestDispatcher("errorpage/enablecookie.jsp").forward(request, response);
         
         
     }
            
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
