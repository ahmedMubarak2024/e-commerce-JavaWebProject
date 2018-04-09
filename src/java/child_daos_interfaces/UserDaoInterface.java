/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package child_daos_interfaces;

import dtos.User;
import parent_dao_interface.ParentDao;

/**
 *
 * @author abanoub samy
 */
public interface UserDaoInterface extends ParentDao<User>{
    
    
    public User login(String email,String password );
    public boolean checkIfEmailExists(String email);
    
}
