package be.crydust;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;

public class CatTest {

    @Test
    public void testJPQL() {
        System.out.println("*** testJPQL");
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("manager");
            EntityManager em = null;
            try {
                em = emf.createEntityManager();

                System.out.println("*** works with hibernate and eclipselink");
                System.out.println("*** \"SELECT c FROM Cat c LEFT JOIN c.kittens as kitten WHERE c.owner = :owner\"");
                em.createQuery("SELECT c FROM Cat c LEFT JOIN c.kittens as kitten WHERE c.owner = :owner", Cat.class)
                        .setParameter("owner", null)
                        .getResultList();

                System.out.println("*** fails with hibernate, works with eclipselink");
                System.out.println("*** \"SELECT c FROM Cat c LEFT JOIN c.kittens as kitten ON c.owner = :owner\"");
                em.createQuery("SELECT c FROM Cat c LEFT JOIN c.kittens as kitten ON c.owner = :owner", Cat.class)
                        .setParameter("owner", null)
                        .getResultList();

            } finally {
                if (em != null) {
                    em.close();
                }
            }
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    @Test
    public void testHQL() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        System.out.println("*** testHQL");
        try {
            Class.forName("org.eclipse.persistence.jpa.PersistenceProvider", false, getClass().getClassLoader());
            System.out.println("*** eclipselink detected, skipping HQL test");
            return;
        } catch (ClassNotFoundException ignore) {
        }
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("manager");
            EntityManager em = null;
            try {
                em = emf.createEntityManager();

                Class<?> sessionClass = Class.forName("org.hibernate.Session", false, getClass().getClassLoader());
                Method createQueryMethod = sessionClass.getMethod("createQuery", String.class);
                createQueryMethod.invoke(
                        em.unwrap(sessionClass),
                        "SELECT cat FROM Cat cat LEFT JOIN cat.kittens as kitten WITH kitten.owner=:owner");

                em.getTransaction().rollback();
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
