
package org.se.lab;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.se.lab.Kunde;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class KundeTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "webshop";

    static final int id_kunde = 158;
    static final String email = "XXXXX";
    static final String name_kunde = "testname";
    static final String passwort = "abc";


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
        Kunde testKunde = new Kunde(email, name_kunde, passwort);
        assertNotNull(testKunde);
        manager.persist(testKunde);
        transaction.commit();

        System.out.println("Created and Persisted " + testKunde);

    }

    @Test
    public void modify() {
        Kunde testKunde = manager.find(Kunde.class, id_kunde);
        assertNotNull(testKunde);
        System.out.println("Found " + testKunde);


    }


    @Test
    public void remove() {
        Kunde testKunde = manager.find(Kunde.class, id_kunde);
        assertNotNull(testKunde);

        transaction.begin();
        manager.remove(testKunde);
        transaction.commit();

        testKunde = manager.find(Kunde.class, id_kunde);
        assertNull(testKunde);

        System.out.println("Removed " + id_kunde);


    }

}
