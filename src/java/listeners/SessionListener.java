/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author abanoub samy
 */
public class SessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        System.out.println("a7la session hatft7");
        
        
        
        
        

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        System.out.println("a7la session dy wla eh ht2fl ");

    }
    
}
