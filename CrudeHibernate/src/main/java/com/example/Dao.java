package com.example;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Dao {

    private static final SessionFactory sessionFactory;

            static {
                try {
                    Configuration configuration = new Configuration();
                    configuration.configure("hibernate.cfg.xml");
                    sessionFactory = configuration.buildSessionFactory();
                    System.out.println(sessionFactory);
                } catch (Throwable ex) {
                    System.err.println("Initial SessionFactory creation failed." + ex);
                    throw new ExceptionInInitializerError(ex);
                }
            }

            
       public static SessionFactory getSessionFactory(){
              return sessionFactory;
          }  
          
            
            
  

    

  
}
