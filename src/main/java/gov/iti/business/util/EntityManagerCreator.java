package gov.iti.business.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerCreator {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hr");

    public static EntityManager generateEntityManager(){
        return emf.createEntityManager();
    }

    public static void closeEntityManagerFactory(){
        emf.close();
    }
}
