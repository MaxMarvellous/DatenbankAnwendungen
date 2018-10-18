
package org.se.lab;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.se.lab.Adresse;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class AdresseTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "webshop";


    static final int id_adresse = 123;
    static final String land = "AUT";
    static final String stadt = "Graz";
    static final String strasse = "Alte Poststraße";
    static final int hausnummer = 69;

    @BeforeClass
    public static void setup() {
        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        assertNotNull(factory);
        manager = factory.createEntityManager();
        assertNotNull(manager);

        transaction = manager.getTransaction();

    }

    @AfterClass
    public static void teardown() {
        if (manager == null)
            return;

        manager.close();
        factory.close();
    }


    @Test
    public void create() {
        transaction.begin();
        Adresse testAdresse = new Adresse(land, stadt, strasse,hausnummer);
        assertNotNull(testAdresse);
        manager.persist(testAdresse);
        transaction.commit();

        System.out.println("Created and Persisted " + testAdresse);

    }

    @Test
    public void modify() {
        Adresse testAdresse = manager.find(Adresse.class, id_adresse);
        assertNotNull(testAdresse);
        System.out.println("Found " + testAdresse);


    }


    @Test
    public void remove() {
        Adresse testAdresse = manager.find(Adresse.class, id_adresse);
        assertNotNull(testAdresse);

        transaction.begin();
        manager.remove(testAdresse);
        transaction.commit();

        testAdresse = manager.find(Adresse.class, id_adresse);
        assertNull(testAdresse);

        System.out.println("Removed " + id_adresse);


    }

}
