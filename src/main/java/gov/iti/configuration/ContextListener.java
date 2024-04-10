package gov.iti.configuration;

import gov.iti.business.util.EntityManagerCreator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("gov.iti.business.util.EntityManagerCreator");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerCreator.closeEntityManagerFactory();
    }
}