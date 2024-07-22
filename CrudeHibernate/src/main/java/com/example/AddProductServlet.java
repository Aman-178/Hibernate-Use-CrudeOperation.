/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author ACCESS
 */
@WebServlet("/registration")
public class AddProductServlet extends HttpServlet{
 private static final SessionFactory sessionFactory=Dao.getSessionFactory();

    
   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        Register register = new Register(username, email, password);
      
             Session session = sessionFactory.openSession();
             Transaction transaction = null;
             try {
                 transaction = session.beginTransaction();
                 session.save(register);
                 transaction.commit();
                 session.close();
                out.println("Successfully Registered");
             } catch (Exception e) {
                 e.printStackTrace();
             } 
         }
     
   
    
}
