/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import beans.RegistrationBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ejb.EJB;

import java.io.IOException;

@WebServlet("/Register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private RegistrationBean registrationBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String result = registrationBean.register(username, password);

        if (result == null) {
            response.sendRedirect("login.jsp"); // Registration successful, redirect to login
        } else {
            request.setAttribute("errorMessage", result); // Set error message
            request.getRequestDispatcher("register.jsp").forward(request, response); // Forward to registration page with error
        }
    }
}
