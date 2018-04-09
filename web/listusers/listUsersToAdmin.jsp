<%-- 
    Document   : login
    Created on : Feb 5, 2018, 7:59:25 AM
    Author     : Mayada Saleh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="dtos.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Super Market an Ecommerce Online Shopping Category Flat Bootstrap Responsive Website Template | Login :: w3layouts</title>
        <!-- for-mobile-apps -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="Super Market Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
              Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
            function hideURLbar(){ window.scrollTo(0,1); } </script>
        <!-- //for-mobile-apps -->
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
        <!-- font-awesome icons -->
        <link href="css/font-awesome.css" rel="stylesheet"> 
        <!-- //font-awesome icons -->
        <!-- js -->
        <script src="js/jquery-1.11.1.min.js"></script>
        <!-- //js -->
        <link href='//fonts.googleapis.com/css?family=Raleway:400,100,100italic,200,200italic,300,400italic,500,500italic,600,600italic,700,700italic,800,800italic,900,900italic' rel='stylesheet' type='text/css'>
        <link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>
        <!-- start-smoth-scrolling -->
        <script type="text/javascript" src="js/move-top.js"></script>
        <script type="text/javascript" src="js/easing.js"></script>
        <script type="text/javascript">
            jQuery(document).ready(function ($) {
                $(".scroll").click(function (event) {
                    event.preventDefault();
                    $('html,body').animate({scrollTop: $(this.hash).offset().top}, 1000);
                });
            });
        </script>
        <!-- start-smoth-scrolling -->

        <link href="css/main.css" rel="stylesheet" type="text/css" />
    </head>


    
    <body>
        <!-- header -->
        <div class="agileits_header">
            <div class="container" >
                <div class="w3l_offers">
                    <p>SALE UP TO 70% OFF. USE CODE "SALE70%" . <a href="HomeServlet">SHOP NOW</a></p>
                </div>
          

            </div>
        </div>

        <div class="logo_products">
            <div class="container">
                  <div class="w3ls_logo_products_left1">
                    <ul class="phone_email">
                        <li><i class="fa fa-phone" aria-hidden="true"></i>Order online or call us : (+0123) 234 567</li>

                    </ul>
                </div>
                <div align="right" class="w3ls_logo_products_left" >
                    <h1><a href="HomeServlet">super Market</a></h1>
                </div>
                
                <div class="w3l_search">
                    <form id="formm" action="../ViewProfileControl" method="post">
                        <input type="search" name="Search" placeholder="Search for a User..." required="" id="inputMail">
                        <button type="submit" class="search-internal btn btn-default search" aria-label="Left Align" onclick="fn1()">
                        <i class="fa fa-search" aria-hidden="true"> </i>
                        </button>

                        <script language="JavaScript" type="text/JavaScript">
                            document.getElementById("formm").addEventListener("click", function(event){
                                event.preventDefault()
                                });
                            function fn1()
                            {
                            var neww = document.getElementById("inputMail").value;
                            
                            
                            <c:url var="temp" value="ViewProfileControl">
                            <c:param name="command" value="SELECTONE"/>
                            <c:param name="usermail" value="${neww}"/>
                             </c:url>
                            document.getElementById("formm").action="${temp}";
                            document.getElementById("formm").submit();
                            }
                        </script>
                        <div class="clearfix"> 
                        </div>
                    </form>
                </div>
                
              
                
                <c:if test="${param.notUser == 'notFound'}">
                    
                     
                    <div class='wronguser'><p>There is no user by this Email</p></div>
                    
                    
                    </c:if>
                 
                <div class="clearfix"> </div>
            </div>
        </div>
        <!-- //header -->
        <!-- navigation -->
        <div class="navigation-agileits">
            <div class="container">
                <nav class="navbar navbar-default">


                </nav>
            </div>
        </div>

        <!-- //navigation -->
        <!-- breadcrumbs -->
        <div class="breadcrumbs">
            <div class="container">
                <ol class="breadcrumb breadcrumb1 animated wow slideInLeft" data-wow-delay=".5s">
                    <li><a href="HomeServlet"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>Home</a></li>
                    <li class="active">User Profile Info</li>
                </ol>
            </div>
        </div>
        <!-- //breadcrumbs -->
        <!-- login -->


        <div class="limiter">
            <div class="container-table100">
                <div class="wrap-table100">
                    <div class="table100">
                        <table>
                            <thead>
                                <tr class="table100-head">
                                    <th class="column1">User Name</th>
                                    <th class="column2">User Email</th>
                                    <th class="column3">View Profile</th>
                                    <th class="column4">View History</th>

                                </tr>


                            </thead>
                            <tbody>



                                <c:forEach var="tempUser" items="${User_LIST}">
                                    <!-- set up a link for each student -->
                                    <c:url var="tempLink" value="ViewProfileControl">
                                        <c:param name="command" value="LOAD" />
                                        <c:param name="usermail" value="${tempUser.userEmail}" />
                                    </c:url>
                                    
                                    
                                    
                                     <c:url var="tempLink2" value="GetUserHistory">
                                        <c:param name="command" value="LOAD" />
                                        <c:param name="usermail" value="${tempUser.userEmail}" />
                                    </c:url>


                                    <tr>
                                        <td class ="column1"> ${tempUser.userName} </td>
                                        <td class ="column2"> ${tempUser.userEmail} </td>
                                        <td class ="column3"> <a href="${tempLink}">View User Profile</a></td>
                                        <td class ="column4"> <a href="${tempLink2}">View User History</a></td>

                                    </tr>

                                </c:forEach>


                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!--include the footer-->

        <jsp:include page="/footer/footerpage.html"/>



    </body>
</html>