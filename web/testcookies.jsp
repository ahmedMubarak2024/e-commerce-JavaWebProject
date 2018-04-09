<%-- 
    Document   : testcookies
    Created on : Feb 6, 2018, 11:17:06 PM
    Author     : abanoub samy
--%>

<%@page import="dtos.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <%

            User u = (User) session.getAttribute("logedInUser");
            out.print(u.getUserName());
            Cookie cookies[] = request.getCookies();

            if (cookies != null) {

                for (Cookie temp : cookies) {

                    if (temp.getName().equals("userEmail")) {

                        out.print(temp.getValue());
                    } else if (temp.getName().equals("userPass")) {
                        out.print(temp.getValue());

                    }

                }

            }

        %>
    </body>
</html>
