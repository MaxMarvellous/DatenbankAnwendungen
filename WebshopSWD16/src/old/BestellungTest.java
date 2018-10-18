
package org.se.lab;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Date;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class BestellungTest {

    static Date date = new Date(2017, 10, 20);

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "webshop";

    static final int id_bestellung = 22;
    static final Date lieferdatum = date;
    static final Date bestelldatum = date;


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
        Bestellung testBestellung = new Bestellung(lieferdatum, bestelldatum);
        assertNotNull(testBestellung);
        manager.persist(testBestellung);
        transaction.commit();

        System.out.println("Created and Persisted " + testBestellung);

    }

    @Test
    public void modify() {
        Bestellung testBestellung = manager.find(Bestellung.class, id_bestellung);
        assertNotNull(testBestellung);
        System.out.println("Found " + testBestellung);


    }

    @Test
    public void remove() {
        Bestellung testBestellung = manager.find(Bestellung.class, id_bestellung);
        assertNotNull(testBestellung);

        transaction.begin();
        manager.remove(testBestellung);
        transaction.commit();

        testBestellung = manager.find(Bestellung.class, id_bestellung);
        assertNull(testBestellung);

        System.out.println("Removed " + id_bestellung);


    }

}
