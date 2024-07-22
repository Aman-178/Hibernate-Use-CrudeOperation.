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


@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    
    
    private static final SessionFactory sessionFactory = Dao.getSessionFactory();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String userIdString = request.getParameter("userid");
        String columnName = request.getParameter("columnname");
        String columnValue = request.getParameter("columnValue");
        
        if (userIdString == null || columnName == null || columnValue == null ||
            userIdString.isEmpty() || columnName.isEmpty() || columnValue.isEmpty()) {
            out.println("<html><body><h2>Error: Missing parameters</h2></body></html>");
            return;
        }
        
        int userId;
        try {
            userId = Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
            out.println("<html><body><h2>Error: Invalid User ID format</h2></body></html>");
            return;
        }
        
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                
                // Load the user entity by its ID
                Register user = session.get(Register.class, userId);
                
                if (user != null) {
                    // Update the appropriate column based on columnName
                    if ("Name".equalsIgnoreCase(columnName)) {
                        user.setUsername(columnValue);
                    } else if ("Email".equalsIgnoreCase(columnName)) {
                        user.setEmail(columnValue);
                    } else if ("Password".equalsIgnoreCase(columnName)) {
                        user.setPassword(columnValue);
                    } else {
                        out.println("<html><body><h2>Error: Invalid column name</h2></body></html>");
                        return;
                    }
                    
                    // Commit the transaction to save the changes
                    session.update(user);
                    transaction.commit();
                    
                    out.println("<html><body><h2>User updated successfully!</h2></body></html>");
                } else {
                    out.println("<html><body><h2>User with ID " + userId + " not found</h2></body></html>");
                }
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new ServletException("Error updating user", ex);
            }
        } catch (Exception e) {
            throw new ServletException("Error opening Hibernate session", e);
        }
    }
}
