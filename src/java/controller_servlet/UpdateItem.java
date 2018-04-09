/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller_servlet;

import child_daos_implementation.ItemDaoImplementation;
import dtos.Item;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import utils.ImageUtils;
import utils.InputStreamUtails;

/**
 *
 * @author abanoub samy
 */
@MultipartConfig
public class UpdateItem extends HttpServlet {

    @Resource(name = "jdbc/eCommerce")
    private DataSource dataSource;

    private ItemDaoImplementation itemImpl;

    @Override

    public void init() {

        itemImpl = new ItemDaoImplementation(dataSource);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Item dto = new Item();
        dto.setItem_id(Integer.parseInt(InputStreamUtails.getStringFromInputStream(request.getPart("item_id").getInputStream())));

        dto.setItem_name(InputStreamUtails.getStringFromInputStream(request.getPart("item_name").getInputStream()));
        dto.setItem_catagory(InputStreamUtails.getStringFromInputStream(request.getPart("catagory").getInputStream()));

        dto.setItem_price(Integer.parseInt(InputStreamUtails.getStringFromInputStream(request.getPart("price").getInputStream())));
        dto.setItem_quntity(Integer.parseInt(InputStreamUtails.getStringFromInputStream(request.getPart("qutity").getInputStream())));

        try {

            itemImpl.update(dto);

            response.sendRedirect("HomeServlet");

        } catch (SQLException ex) {
            Logger.getLogger(UpdateItem.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            itemImpl.close();
        }

    }

}
