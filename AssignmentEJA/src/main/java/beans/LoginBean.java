/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package beans;

import entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author aadik
 */
@Stateless
public class LoginBean{

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    public User authenticate(String username, String password) {
        try {
            User user = em.find(User.class, username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
