
package org.se.lab;


import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import webshop.persistence.Transaction;

import org.se.lab.persistence.Persistence;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class BestellungKundeTest {
    static final boolean verbose = true;

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

    static BestellungRepository bestellungRepository = new BestellungRepository();
    static KundeRepository kundeRepository = new KundeRepository();

    static Bestellung bestellung1;
    static Bestellung bestellung2;

    static Kunde kunde1;
    static Kunde kunde2;

    @BeforeClass
    public static void setup() {
        bestellungRepository = new BestellungRepository();
        kundeRepository = new KundeRepository();

        Transaction.begin();
        BestellungRepository.reset();
        KundeRepository.reset();
        Transaction.commit();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();
    }


    @Test
    public void a_create() {

        //bestellungen
        Transaction.begin();
        //kunden

        kunde1 = kundeRepository.create(kunde1_email, kunde1_passwort, kunde1_name);
        kunde2 = kundeRepository.create(kunde2_email, kunde2_passwort, kunde2_name);

        assertNotNull(kunde1);
        assertNotNull(kunde2);

        bestellung1 = bestellungRepository.create(bestellung1_lieferdatum, bestellung1_bestelldatum, kunde1);
        bestellung2 = bestellungRepository.create(bestellung2_lieferdatum, bestellung2_bestelldatum, kunde2);


        assertNotNull(bestellung1);
        assertNotNull(bestellung2);


        Transaction.commit();
    }

    @Test
    public void verify() {
        if (verbose) System.out.println("Persisted " + bestellung1);
        if (verbose) System.out.println("Persisted " + bestellung2);
        if (verbose) System.out.println("Persisted " + kunde1);
        if (verbose) System.out.println("Persisted " + kunde2);

        //Bestellung

        List<Bestellung> bestellungen = bestellungRepository.findAll();
        assertEquals(2, bestellungen.size());

        if (verbose) bestellungRepository.printAll();

        assertTrue(bestellungen.contains(bestellung1));
        assertTrue(bestellungen.contains(bestellung2));

        assertEquals(kunde1, bestellung1.getKunde());
        assertEquals(kunde2, bestellung2.getKunde());

        // Kunden
        List<Kunde> kunden = kundeRepository.findAll();
        assertEquals(2, kunden.size());

        if (verbose) kundeRepository.printAll();

        assertEquals(1, kunde1.getBestellungen().size());
        assertEquals(1, kunde2.getBestellungen().size());

        List<Bestellung> kunde1best =
                kunden.get(kunden.indexOf(kunde1)).getBestellungen();

        List<Bestellung> kunde2best =
                kunden.get(kunden.indexOf(kunde2)).getBestellungen();

        assertTrue(kunde1best.contains(bestellung1));
        assertTrue(kunde2best.contains(bestellung2));

        if (verbose) kundeRepository.printAll();
    }
}
