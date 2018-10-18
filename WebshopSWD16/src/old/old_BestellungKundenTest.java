
package org.se.lab;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

import static org.junit.Assert.*;


@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class old_BestellungKundenTest {

    static Date date1 = new Date(2017, 10, 20);
    static Date date2 = new Date(2017, 10, 21);

    //Bestellung
    static final Date bestellung1_lieferdatum = date1;
    static final Date bestellung2_lieferdatum = date2;
    static final Date bestellung1_bestelldatum = date1;
    static final Date bestellung2_bestelldatum = date2;

    //Kunde

    static final String kunde1_email = "hans@mail.com";
    static final String kunde1_passwort = "****";
    static final String kunde1_name = "Hans";

    static final String kunde2_email = "franz@mail.com";
    static final String kunde2_passwort = "****";
    static final String kunde2_name = "Franz";


    static Bestellung bestellung1;
    static Bestellung bestellung2;

    static Kunde kunde1;
    static Kunde kunde2;


    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "webshop";

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
    public void test() {


        transaction.begin();
        Kunde kunde1 = new Kunde(kunde1_email, kunde1_passwort, kunde1_name);
        Kunde kunde2 = new Kunde(kunde2_email, kunde2_passwort, kunde2_name);

        Bestellung bestellung1 = new Bestellung(bestellung1_lieferdatum, bestellung1_bestelldatum,kunde1);
        Bestellung bestellung2 = new Bestellung(bestellung2_lieferdatum, bestellung2_bestelldatum,kunde1);
        Bestellung bestellung3 = new Bestellung(bestellung2_lieferdatum, bestellung2_bestelldatum,kunde1);
        assertNotNull(kunde1);
        assertNotNull(kunde2);
        assertNotNull(bestellung1);
        assertNotNull(bestellung2);
        assertNotNull(bestellung3);


        bestellung1.set(kunde1);
        bestellung2.set(kunde1);
        bestellung3.set(kunde2);

        assertEquals(kunde1, bestellung1.getKunde());
        assertEquals(kunde1, bestellung2.getKunde());

        assertTrue(kunde1.getBestellungen().contains(bestellung1));
        assertTrue(kunde2.getBestellungen().contains(bestellung3));

        manager.remove(kunde1);
        manager.remove(kunde2);
        manager.remove(bestellung1);
        manager.remove(bestellung2);


        transaction.commit();

    }

}