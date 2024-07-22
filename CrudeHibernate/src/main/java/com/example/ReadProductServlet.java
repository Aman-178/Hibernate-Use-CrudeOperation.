package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@WebServlet("/DataList")
public class ReadProductServlet extends HttpServlet {
     private static final SessionFactory sessionFactory=Dao.getSessionFactory();

   

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
     
        List<DataList> dataList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Register");
            List<Register> resultList = query.getResultList();
            for (Register entity : resultList) {
                DataList data = new DataList();
                data.setUsername(entity.getUsername());
                data.setEmail(entity.getEmail());
                data.setPassword(entity.getPassword());
                dataList.add(data);
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
      Gson gson = new GsonBuilder().create();
      String json = gson.toJson(dataList);

        // Send JSON response
        out.println(json);
      
    }
   
}
