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
public class RegistrationBean{

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;
    
    public String register(String username, String password) {
        try {
            // Check if the user already exists
            User existingUser = em.find(User.class, username);
            if (existingUser != null) {
                return "User already exists."; // User already exists
            }

            // Create and persist the new user
            User user = new User(username, password);
            em.persist(user);
            return null; // Registration successful
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred: " + e.getMessage(); // Registration failed due to an exception
        }
    }
}
