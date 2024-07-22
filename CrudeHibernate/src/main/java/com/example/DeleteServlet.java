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
@WebServlet("/Delete")
public class DeleteServlet extends HttpServlet {
    private static final SessionFactory sessionfactory=Dao.getSessionFactory();
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        
       response.setContentType("text/html");
       PrintWriter out=response.getWriter();
       
       String UserId=request.getParameter("userid");
       int userId=Integer.parseInt(UserId);
       
       Session session=sessionfactory.openSession();
       Transaction transaction = session.beginTransaction();
       Register user = session.load(Register.class, userId);
       session.delete(user);
       out.println("succcessfully Deleted");
       transaction.commit();
       
        
       session.close();
    }
    
}
