
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import dtos.Item;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import utils.ImageUtils;
import utils.InputStreamUtails;

/**
 *
 * @author Altysh
 */
@WebServlet("/Item_Add")
@MultipartConfig
public class Item_Add extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemDaoImplementation item=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/e_commerce", "root", "");
             item = new ItemDaoImplementation();
            item.setCon(con);
            Item dto = new Item();
            dto.setItem_name(InputStreamUtails.getStringFromInputStream(request.getPart("item_name").getInputStream()));
            dto.setItem_catagory(InputStreamUtails.getStringFromInputStream(request.getPart("catagory").getInputStream()));
            Part filePart = request.getPart("pic");
            dto.setItem_pic(ImageUtils.getBytesFromFile(filePart));

            dto.setItem_price(Integer.parseInt(InputStreamUtails.getStringFromInputStream(request.getPart("price").getInputStream())));
            dto.setItem_quntity(Integer.parseInt(InputStreamUtails.getStringFromInputStream(request.getPart("qutity").getInputStream())));
            item.insert(dto);
        } catch (SQLException ex) {
            Logger.getLogger(Item_Add.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Item_Add.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (item != null) {
                item.close();
            }
        }
        //  request.getRequestDispatcher("/additem/addNewItem.jsp").include(request, response);
        response.sendRedirect("additem/addNewItem.jsp");
    }

}
