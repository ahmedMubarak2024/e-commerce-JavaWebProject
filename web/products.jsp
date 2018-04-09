<%@page import="dtos.Item"%>
<%@page import="java.io.Console"%>
<%@page import="dtos.User"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
    <head>
        <title>Super Market an Ecommerce Online Shopping Category Flat Bootstrap Responsive Website Template | Products :: w3layouts</title>
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
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.css">
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
            function loaddata()
            {
                var xp = document.cookie;
                console.log(xp);
                var x = new XMLHttpRequest();
                x.open("get", "ShopCard?state=card", true);
                x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                x.onreadystatechange = function () {
                      	if (this.readyState === 4 && this.status === 200) {
                        // alert(this.responseText);	
                        try {
                            var parsed = JSON.parse(this.responseText);
                            for (var i = 0, len = parsed.length; i < len; i++)
                            {
                                elements.push(new el(parsed[i].name, parsed[i].price, parsed[i].quntity, parsed[i].id, parsed[i].qu));

                            }
                        } catch (e) {

                        }


                        //console.log(elements);
                    }
                };
                x.send();
                

            }
        </script>
        <!-- start-smoth-scrolling -->
    </head>

    <body onload="loaddata()">
        <!-- header -->
        <% 
//response.setHeader("Cache-Control", "no-cache, no-store, must");
     //     response.setHeader("Pragma", "no-cache");
        %>
        <div class="agileits_header">
            <div class="container">
                <div class="w3l_offers">
                    <p>SALE UP TO 70% OFF. USE CODE "SALE70%" . <a href="HomeServlet">SHOP NOW</a></p>
                </div>
                <div class="agile-login">
                    <ul>





                        <c:if test="${sessionScope.logedInUser!=null}">

                            <li><a href="edituserprofile/edituserprof.jsp">
                                    ${sessionScope.logedInUser.userName}
                                </a></li>

                            <c:if test="${sessionScope.logedInUser.userType == 'admin'}">

                                <li><a href="AdminShowUsers">Show All Users</a></li>
                                <li><a href="additem/addNewItem.jsp">Add Item</a></li>

                            </c:if>
                            <li><a href="SignOutServlet">Sign out</a></li>

                        </c:if>


                        <c:if test="${sessionScope.logedInUser==null}">

                            <li><a href="regesterpage/registered.jsp"> Create Account </a></li>
                            <li><a href="loginpage/login.jsp">Login</a></li>

                        </c:if>



                    </ul>
                </div>
                <div class="product_list_header">  


                    <c:if test="${sessionScope.logedInUser!=null}">
                        <button class="w3view-cart" type="button" name="submit" value="" data-toggle="modal" data-target="#exampleModalCenter"><i class="fa fa-cart-arrow-down" aria-hidden="true"></i></button>
                        </c:if>
                </div>
                <div class="clearfix"> </div>
            </div>
        </div>

        <div class="logo_products">
            <div class="container">
                <div class="w3ls_logo_products_left1">
                    <ul class="phone_email">
                        <li><i class="fa fa-phone" aria-hidden="true"></i>Order online or call us : (+0123) 234 567</li>

                    </ul>
                </div>
                <div class="w3ls_logo_products_left">
                    <h1><a href="HomeServlet">super Market</a></h1>

                    <c:if  test="${currentCategory !=null}">


                        <h1>${currentCategory}</h1>


                    </c:if>
                </div>


                <form action="SearchProduct" method="post">
                    <input type="text"  name ="serachMethod"  value ="search by price" hidden="">


                    <lable class="search">Search By Price : </lable>  <input type="text" name ="currentCategory" value="${currentCategory}" hidden="">


                    <select class="price" name ="from">

                        <option>100</option>

                        <option>1000</option>

                    </select>

                    <select class="max-price" name ="to">
                        <option>1000</option>

                        <option>2000</option>

                    </select>
                    <button type="submit" class="btn btn-default search" aria-label="Left Align">
                        <i class="fa fa-search" aria-hidden="true"> </i>
                    </button>
                    <div class="clearfix"></div>
                </form>


                <br>

                <div class="w3l_search">
                    <form action="SearchProduct" method="post">
                        <input type="text"  name ="serachMethod"  value ="search by name" hidden="">


                        <input type="text" name ="currentCategory" value="${currentCategory}" hidden="">
                        <input type="search" name="Search" placeholder="Search for a Product by name..." required="">
                        <button type="submit" class="btn btn-default search" aria-label="Left Align">
                            <i class="fa fa-search" aria-hidden="true"> </i>
                        </button>
                        <div class="clearfix"></div>
                    </form>
                </div>




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
                    <li class="active">Products</li>
                </ol>
            </div>
        </div>
        <!-- //breadcrumbs -->
        <!--- products --->
        <div class="products">
            <div class="container">
                <div class="col-md-4 products-left">
                    <div class="categories">
                        <h2>Categories</h2>
                        <ul class="cate">


                            <c:forEach items="${categ}" var="catego">


                                <c:url var="tempLink1" value="HomeServlet">
                                    <c:param name="command" value="cat" />
                                    <c:param name="categoryName" value="${catego}" />
                                </c:url>

                                <li><a href="${tempLink1}"><i class="fa fa-arrow-right" aria-hidden="true"></i>${catego}</a></li>

                            </c:forEach>

                        </ul>
                    </div>																																												
                </div>
                <div class="col-md-8 products-right">

                    <c:forEach items="${list}" var ="row">
                        <div class="agile_top_brands_grids">
                            <c:forEach items="${row.elements}" var = "item">
                                <div class="col-md-4 top_brand_left">
                                    <div class="hover14 column">
                                        <div class="agile_top_brand_left_grid">

                                            <div class="agile_top_brand_left_grid1">
                                                <figure>
                                                    <div class="snipcart-item block">
                                                        <div class="snipcart-thumb">
                                                            <a href="single.html"><img title=" " alt=" " src="GetImage?name=${item.item_id}"width="100" height="100"></a>		
                                                            <p>${item.item_name}</p>
                                                            <p>Price : ${item.item_price}<p>
                                                            <p>Category : ${item.item_catagory}<p>
                                                        </div>
                                                        <div class="snipcart-details top_brand_home_details">


                                                            <c:if test="${sessionScope.logedInUser!=null}">
                                                                <input type="button" name="submit" value="Add to  cart" class="button" onclick="addItem('${item.item_name}',${item.item_price},${item.item_quntity},${item.item_id})" >
                                                                <br><br>

                                                            </c:if>

                                                            <c:if  test="${sessionScope.logedInUser.userType == 'admin'}" > 
                                                                <c:url var='tempLink' value='DeleteItem'>
                                                                    <c:param name='itemId' value='${item.item_id}' />
                                                                </c:url>

                                                                <c:url var='tempLink2' value='itemtoedit'>
                                                                    <c:param name='itemId' value='${item.item_id}' />
                                                                </c:url>

                                                                <a href = '${tempLink}'>DELETE
                                                                    <br><br>
                                                                    <a href = '${tempLink2}'>EDIT

                                                                    </c:if>



                                                                    </div>
                                                                    </div>
                                                                    </figure>


                                                                    </div>
                                                                    </div>
                                                                    </div>
                                                                    </div>
                                                                </c:forEach>
                                                                <div class="clearfix"> </div>
                                                                </div>
                                                            </c:forEach>
                                                            <c:if test="${view=='catgory'}">

                                                                <nav class="numbering">

                                                                    <ul class="pagination paging">
                                                                        <li>
                                                                            <a href="HomeServlet?page=${pagenumber-1}&categoryName=${cata}&command=cat" aria-label="Previous">
                                                                                <span aria-hidden="true">&laquo;</span>
                                                                            </a>
                                                                        </li>
                                                                        <c:forEach begin="1" end="${page+1}"  varStatus="loop">
                                                                            <c:if test="${pagenumber+1==loop.index}">
                                                                                <li class="active"><a href="HomeServlet?page=${loop.index-1}&categoryName=${cata}&command=cat">${loop.index}<span class="sr-only">(current)</span></a></li> 
                                                                                </c:if>

                                                                            <c:if test="${pagenumber+1!=loop.index}">
                                                                                <li><a href="HomeServlet?page=${loop.index-1}&categoryName=${cata}&command=cat">${loop.index}</a></li>  
                                                                                </c:if>



                                                                        </c:forEach>
                                                                        <li>
                                                                            <a href="HomeServlet?page=${pagenumber+1}&categoryName=${cata}&command=cat" aria-label="Next">
                                                                                <span aria-hidden="true">&raquo;</span>
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </nav>
                                                            </c:if>
                                                            <c:if test="${view=='all'}">

                                                                <nav class="numbering">

                                                                    <ul class="pagination paging">
                                                                        <li>
                                                                            <a href="HomeServlet?page=${pagenumber-1}" aria-label="Previous">
                                                                                <span aria-hidden="true">&laquo;</span>
                                                                            </a>
                                                                        </li>
                                                                        <c:forEach begin="1" end="${page+1}"  varStatus="loop">
                                                                            <c:if test="${pagenumber+1==loop.index}">
                                                                                <li class="active"><a href="HomeServlet?page=${loop.index-1}">${loop.index}<span class="sr-only">(current)</span></a></li> 
                                                                                </c:if>

                                                                            <c:if test="${pagenumber+1!=loop.index}">
                                                                                <li><a href="HomeServlet?page=${loop.index-1}">${loop.index}</a></li>  
                                                                                </c:if>



                                                                        </c:forEach>
                                                                        <li>
                                                                            <a href="HomeServlet?page=${pagenumber+1}" aria-label="Next">
                                                                                <span aria-hidden="true">&raquo;</span>
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </nav>
                                                            </c:if>
                                                            </div>
                                                            <div class="clearfix"> </div>
                                                            </div>
                                                            </div>
                                                            <!--- products --->
                                                            <jsp:include page="/footer/footerpage.html"/>
                                                            <!-- Bootstrap Core JavaScript -->
                                                            <script src="js/bootstrap.min.js"></script>
                                                            <!-- top-header and slider -->
                                                            <!-- here stars scrolling icon -->
                                                            <script type="text/javascript">
                                                                    $(document).ready(function () {
                                                                        /*
                                                                         var defaults = {
                                                                         containerID: 'toTop', // fading element id
                                                                         containerHoverID: 'toTopHover', // fading element hover id
                                                                         scrollSpeed: 1200,
                                                                         easingType: 'linear' 
                                                                         };
                                                                         */

                                                                        $().UItoTop({easingType: 'easeOutQuart'});

                                                                    });
                                                            </script>
                                                            <!-- //here ends scrolling icon -->
                                                            <script src="js/minicart.min.js"></script>
                                                            <script>
                                                                    // Mini Cart


                                                                    if (~window.location.search.indexOf('reset=true')) {
                                                                        paypal.minicart.reset();
                                                                    }
                                                            </script>
                                                            <!-- main slider-banner -->
                                                            <script src="js/skdslider.min.js"></script>
                                                            <link href="css/skdslider.css" rel="stylesheet">
                                                            <script type="text/javascript">

                                                                    jQuery(document).ready(function () {
                                                                        jQuery('#demo1').skdslider({'delay': 5000, 'animationSpeed': 2000, 'showNextPrev': true, 'showPlayButton': true, 'autoSlide': true, 'animationType': 'fading'});

                                                                        jQuery('#responsive').change(function () {
                                                                            $('#responsive_wrapper').width(jQuery(this).val());
                                                                        });

                                                                    });
                                                                    var elements = [];
                                                                    function el(name, value, quntity, id, qu)
                                                                    {
                                                                        this.n = name;
                                                                        this.price = value;
                                                                        this.quntity = quntity;
                                                                        this.id = id;
                                                                        if (arguments.length === 5)
                                                                            this.qu = qu;
                                                                        else
                                                                            this.qu = 1;
                                                                        this.check = function (name) {
                                                                            //console.log(n);
                                                                            return this.n === name;
                                                                        };
                                                                    }
                                                                    function addItem(itemName, itemPrice, ItemQuntity, item_id, qu)
                                                                    {


                                                                        var excit = false;
                                                                        if (arguments.length === 5)
                                                                            this.qu = qu;
                                                                        else
                                                                            this.qu = 1;
                                                                        for (var exc = 0; exc < elements.length; exc++)
                                                                        {
                                                                            console.log(elements[exc]);
                                                                            if (elements[exc].check(itemName))
                                                                            {

                                                                                excit = true;
                                                                                break;
                                                                            }
                                                                        }
                                                                        if (!excit) {
                                                                            var total = document.getElementById("totalprice");
                                                                            var e = new el(itemName, itemPrice, ItemQuntity, item_id, this.qu);
                                                                            var string = JSON.stringify(e);
                                                                            var x = new XMLHttpRequest();
                                                                            x.open("POST", "ShopCard", true);
                                                                            x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                                            x.onreadystatechange = function () {
                                                                                  	if (this.readyState === 4 && this.status === 200) {
                                                                                    // alert(this.responseText);		
                                                                                    var parsed = JSON.parse(this.responseText);
                                                                                    total.textContent = parsed.total;
                                                                                    //console.log(elements);
                                                                                }
                                                                            };
                                                                            x.send("obj=" + string);
                                                                            elements.push(e);
                                                                            //console.log(itemName + itemPrice + ItemQuntity);
                                                                            var para = document.createElement("div");
                                                                            para.id = "div";
                                                                            para.className = "form-group row";
                                                                            var name = document.createElement("label");
                                                                            name.id = "name";
                                                                            name.className = "col-sm-2 col-form-label";
                                                                            var node = document.createTextNode(itemName);
                                                                            name.appendChild(node);
                                                                            var price = document.createElement("label");
                                                                            price.id = "price";
                                                                            price.name = "price";
                                                                            price.className = "col-sm-2 col-form-label";
                                                                            node = document.createTextNode(itemPrice * this.qu);
                                                                            price.appendChild(node);
                                                                            var quntity = document.createElement("label");
                                                                            quntity.id = "quntity";
                                                                            quntity.className = "col-sm-2 col-form-label";
                                                                            node = document.createTextNode(this.qu);
                                                                            var buttonIncrement = document.createElement("button");
                                                                            buttonIncrement.className = "col-sm-2 col-form-label";
                                                                            buttonIncrement.style.width = "30px";
                                                                            buttonIncrement.style.height = "30px";
                                                                            buttonIncrement.style.backgroundImage= "url(images/plus.png)"; 
                                                                            buttonIncrement.style.marginRight = "10px";
                                                                            buttonIncrement.style.backgroundSize ="cover";

                                                                            //buttonIncrement.appendChild(plus);
                                                                            buttonIncrement.onclick = function () {
                                                                                increment(this.parentNode);
                                                                            };
                                                                            var buttonDecrment = document.createElement("button");
                                                                            buttonDecrment.style.backgroundImage= "url(images/minus.png)";
                                                                            buttonDecrment.onclick = function () {
                                                                                decrement(this.parentNode);
                                                                            };
                                                                            buttonDecrment.className = "col-sm-2 col-form-label";
                                                                            buttonDecrment.style.width = "30px";
                                                                            buttonDecrment.style.height = "30px";
                                                                            buttonDecrment.style.marginRight = "10px";
                                                                            buttonDecrment.style.backgroundSize ="cover";
                                                                            quntity.appendChild(node);
                                                                            var buttonclose = document.createElement("button");
                                                                            buttonclose.className = "col-sm-2 col-form-label";
                                                                            buttonclose.style.width = "30px";
                                                                            buttonclose.style.height = "30px";
                                                                            buttonclose.style.backgroundImage=  "url(images/x.jpg)";
                                                                            buttonclose.style.marginRight = "10px";
                                                                            buttonclose.style.backgroundSize ="cover";
                                                                            buttonclose.onclick = function () {
                                                                                removeItem(this.parentNode);
                                                                            };
                                                                            var hPrice = document.createElement("label");
                                                                            node = document.createTextNode(itemPrice);
                                                                            hPrice.hidden = "true";
                                                                            hPrice.id = "RealPrice";
                                                                            hPrice.appendChild(node);
                                                                            para.appendChild(name);
                                                                            para.appendChild(quntity);
                                                                            para.appendChild(hPrice);
                                                                            para.appendChild(price);
                                                                            para.appendChild(buttonIncrement);
                                                                            para.appendChild(buttonDecrment);
                                                                            para.appendChild(buttonclose);

                                                                            document.getElementById("card").appendChild(para);

                                                                            //x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");


                                                                        }

                                                                    }
                                                                    function removeItem(item)
                                                                    {
                                                                        document.getElementById("card").removeChild(item);
                                                                        var name = item.childNodes.item(0).textContent;
                                                                        var total = document.getElementById("totalprice");
                                                                        for (var inc = 0; inc < elements.length; inc++)
                                                                        {
                                                                            if (elements[inc].check(name))
                                                                            {


                                                                                var string = JSON.stringify(elements[inc]);
                                                                                console.log(string);
                                                                                var x = new XMLHttpRequest();
                                                                                x.open("POST", "ShopCard", true);
                                                                                x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                                                x.onreadystatechange = function () {
                                                                                      	if (this.readyState === 4 && this.status === 200) {
                                                                                        // alert(this.responseText);		
                                                                                        var parsed = JSON.parse(this.responseText);
                                                                                        total.textContent = parsed.total;
                                                                                        //console.log(elements);
                                                                                    }
                                                                                };
                                                                                x.send("obj=" + string + "&state=remove");
                                                                                elements.splice(inc, 1);



                                                                            }
                                                                        }


                                                                    }
                                                                    function increment(b)
                                                                    {
                                                                        var label = b.childNodes.item(1);
                                                                        var name = b.childNodes.item(0).textContent;
                                                                        var ok = true;
                                                                        for (var inc = 0; inc < elements.length; inc++)
                                                                        {
                                                                            if (elements[inc].check(name))
                                                                            {


                                                                                if (elements[inc].qu >= elements[inc].quntity)
                                                                                {
                                                                                    ok = false;
                                                                                } else {
                                                                                    elements[inc].qu++;
                                                                                    var string = JSON.stringify(elements[inc]);
                                                                                    console.log(string);
                                                                                    var x = new XMLHttpRequest();
                                                                                    x.open("POST", "ShopCard", true);
                                                                                    x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                                                    var total = document.getElementById("totalprice");
                                                                                    //x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                                                    x.onreadystatechange = function () {
                                                                                          	if (this.readyState === 4 && this.status === 200) {
                                                                                            // alert(this.responseText);		
                                                                                            var parsed = JSON.parse(this.responseText);
                                                                                            total.textContent = parsed.total;
                                                                                            var Realprice = b.childNodes.item(2);
                                                                                            var price = b.childNodes.item(3);

                                                                                            var count = label.textContent;
                                                                                            price.textContent = (parseInt(Realprice.textContent) * (parseInt(count) + 1));
                                                                                            console.log(count);
                                                                                            label.textContent = (parseInt(count) + 1) + "";
                                                                                            //console.log(elements);
                                                                                        }
                                                                                    };
                                                                                    x.send("obj=" + string);
                                                                                    break;


                                                                                }
                                                                            }
                                                                        }
                                                                        if (ok) {
                                                                            console.log(elements.length);



                                                                            // document.getElementById("card").removeChild(element[i]);
                                                                        }
                                                                    }


                                                                    function decrement(b)
                                                                    {
                                                                        var label = b.childNodes.item(1);
                                                                        var count = label.textContent;
                                                                        //console.log(count);
                                                                        var name = b.childNodes.item(0).textContent;
                                                                        var ok = true;


                                                                        if (parseInt(count) > 0)
                                                                        {
                                                                            for (var inc = 0; inc < elements.length; inc++)
                                                                            {
                                                                                if (elements[inc].check(name))
                                                                                {
                                                                                    //console.log(name);

                                                                                    elements[inc].qu--;

                                                                                    var string = JSON.stringify(elements[inc]);
                                                                                    console.log(string);
                                                                                    var x = new XMLHttpRequest();
                                                                                    x.open("POST", "ShopCard", true);
                                                                                    var total = document.getElementById("totalprice");
                                                                                    x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                                                    x.onreadystatechange = function () {
                                                                                          	if (this.readyState === 4 && this.status === 200) {
                                                                                            // alert(this.responseText);		
                                                                                            var parsed = JSON.parse(this.responseText);
                                                                                            total.textContent = parsed.total;
                                                                                            var Realprice = b.childNodes.item(2);
                                                                                            var price = b.childNodes.item(3);
                                                                                            label.textContent = (parseInt(count) - 1) + "";
                                                                                            price.textContent = (parseInt(Realprice.textContent) * (parseInt(count) - 1));
                                                                                            //console.log(elements);
                                                                                        }
                                                                                    };
                                                                                    x.send("obj=" + string);

                                                                                    //x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");



                                                                                }
                                                                            }

                                                                        }
                                                                    }
                                                                    function save_item()
                                                                    {
                                                                        var check = new XMLHttpRequest();
                                                                        check.open("get", "ShopCard?state=save", false);
                                                                        check.send();
                                                                    }
                                                                    function buy_item()
                                                                    {
                                                                        var good = true;
                                                                        var check = new XMLHttpRequest();
                                                                        check.open("get", "ShopCard?state=total", false);
                                                                        check.onreadystatechange = function () {
                                                                            if (this.readyState === 4 && this.status === 200) {
                                                                                var parsed = JSON.parse(this.responseText);
                                                                                if (parsed.total <= parsed.balnce)
                                                                                {
                                                                                    good = true;
                                                                                } else
                                                                                {
                                                                                    good = false;
                                                                                    alert("not enough blance");
                                                                                }
                                                                            }
                                                                        };
                                                                        check.send();
                                                                        if (good)
                                                                        {
                                                                            var x = new XMLHttpRequest();
                                                                            x.open("get", "ShopCard?state=buy", true);
                                                                            x.onreadystatechange = function () {
                                                                                  	if (this.readyState === 4 && this.status === 200) {
                                                                                    // alert(this.responseText);		
                                                                                    var parsed = JSON.parse(this.responseText);
                                                                                    if (parsed.status)
                                                                                    {
                                                                                        var items = parsed.array;
                                                                                        alert("the items was perchesd");
                                                                                        elements.splice(0, elements.length);
                                                                                        var element = document.getElementById("card");
                                                                                        while (element.firstChild)
                                                                                        {
                                                                                            element.removeChild(element.firstChild);
                                                                                        }
                                                                                        var total = document.getElementById("totalprice");
                                                                                        total.textContent = '0';
                                                                                        var balance = document.getElementById("balance");
                                                                                        balance.textContent = parsed.bal;




                                                                                    } else {
                                                                                        alert("some items is not avilible any more ");
                                                                                        var element = document.getElementById("card");
                                                                                        while (element.firstChild)
                                                                                        {
                                                                                            element.removeChild(element.firstChild);
                                                                                        }
                                                                                        elements.splice(0, elements.length);
                                                                                        for (var product = 0; product < parsed.array.length; product++)
                                                                                        {

                                                                                            addItem(parsed.array[product].name, parsed.array[product].price,
                                                                                                    parsed.array[product].quntity, parsed.array[product].id,
                                                                                                    parsed.array[product].qu);
                                                                                        }
                                                                                        var total = document.getElementById("totalprice");
                                                                                        total.textContent = parsed.total;
                                                                                        console.log("did work");

                                                                                    }
                                                                                    for (var i = 0, len = parsed.length; i < len; i++)
                                                                                    {
                                                                                        elements.push(new el(parsed[i].name, parsed[i].price, parsed[i].quntity, parsed[i].id, parsed[i].qu));

                                                                                    }
                                                                                    //console.log(elements);
                                                                                }
                                                                            };
                                                                            x.send();
                                                                        }
                                                                    }




                                                            </script>	
                                                            <!-- //main slider-banner --> 
                                                            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                                                <div class="modal-dialog modal-dialog-centered" role="document">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h1 class="modal-title" id="exampleModalLongTitle">Shopping Card</h1>
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>

                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label  class="col-sm-2 col-form-label">Name</label>
                                                                            <label  class="col-sm-2 col-form-label">Quntity</label>
                                                                            <label  class="col-sm-2 col-form-label">Price</label>
                                                                        </div>
                                                                        <div class="modal-body" id = "card">

                                                                            <c:forEach items="${card}" var="item">

                                                                                <div class="form-group row" id ="div"><label  class="col-sm-2 col-form-label" id="name">${item.name}</label><label  class="col-sm-2 col-form-label" id = "quntity">${item.itemcount}</label><label name="RealPrice" hidden="true" name = "price">${item.price}</label><label  class="col-sm-2 col-form-label" id="price">${item.totalPrice}</label>
                                                                                    <button class="col-sm-2 col-form-label" style="width: 30px;height: 30px;background-size: cover;background-image: url(images/plus.png);margin-right: 10px" onclick="increment(this.parentNode);"></button>
                                                                                    <button class="col-sm-2 col-form-label" style="width: 30px;height: 30px;background-size: cover;background-image: url(images/minus.jpg);margin-right: 10px" onclick="decrement(this.parentNode);"></button>
                                                                                    <button class="col-sm-2 col-form-label" style="width: 30px;height: 30px;background-size: cover;background-image: url(images/x.jpg);margin-right: 10px" onclick="removeItem(this.parentNode);"></button>
                                                                                </div>
                                                                            </c:forEach>
                                                                        </div>

                                                                        <div class="modal-footer">
                                                                            <h1 style="display: inline;float:left" > Total price </h1>
                                                                            <h1 style="float: left;margin-left: 50px;" id="totalprice"><c:if test="${total>0}">
                                                                                    <c:out value = "${total}"/>       
                                                                                </c:if>
                                                                                <c:if test="${total<=0}">
                                                                                    <c:out value = "0"/>       
                                                                                </c:if></h1> 
                                                                            <h1 style="float: left;margin-left: 50px;">Balance</h1>
                                                                            <h1 style="float: left;margin-left: 50px;" id="balance">${balance}</h1>
                                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                            <button type="button" class="btn btn-primary" onclick="buy_item();">Buy</button>
                                                                            <button type="button" class="btn btn-secondary" onclick="save_item();">Save</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            </body>
                                                            </html>