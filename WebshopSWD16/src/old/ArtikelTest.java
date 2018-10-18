
package org.se.lab;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.se.lab.Artikel;

import java.util.Calendar;
import java.util.GregorianCalendar;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class ArtikelTest {


    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "webshop";

    static final int id_artikel = 112;
    static final double preis = 22.22;
    static final String name = "TestArtikel";


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
        Artikel testArtikel = new Artikel(preis, name);
        assertNotNull(testArtikel);
        manager.persist(testArtikel);
        transaction.commit();

        System.out.println("Created and Persisted " + testArtikel);

    }

    @Test
    public void modify() {
        Artikel testArtikel = manager.find(Artikel.class, id_artikel);
        assertNotNull(testArtikel);
        System.out.println("Found " + testArtikel);


    }

    @Test
    public void remove() {
        Artikel testArtikel = manager.find(Artikel.class, id_artikel);
        assertNotNull(testArtikel);

        transaction.begin();
        manager.remove(testArtikel);
        transaction.commit();

        testArtikel = manager.find(Artikel.class, id_artikel);
        assertNull(testArtikel);

        System.out.println("Removed " + id_artikel);


    }

}
