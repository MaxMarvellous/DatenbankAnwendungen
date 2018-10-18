
package org.se.lab;


import java.util.List;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import webshop.persistence.Transaction;

import org.se.lab.persistence.Persistence;

import javax.persistence.NoResultException;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class BestellungArtikelTest {
    static Date date1 = new Date(2017, 10, 20);
    static Date date2 = new Date(2017, 10, 21);
    static Date date3 = new Date(2017, 10, 22);

    static final boolean verbose = true;

    static Kunde kunde1;
    static Kunde kunde2;

    //Kunde

    static final String kunde1_email = "hans@mail.com";
    static final String kunde1_passwort = "****";
    static final String kunde1_name = "Hans";

    static final String kunde2_email = "franz@mail.com";
    static final String kunde2_passwort = "****";
    static final String kunde2_name = "Franz";


    //Bestellung
    static final Date bestellung1_lieferdatum = date1;
    static final Date bestellung2_lieferdatum = date2;
    static final Date bestellung3_lieferdatum = date3;
    static final Date bestellung1_bestelldatum = date1;
    static final Date bestellung2_bestelldatum = date2;
    static final Date bestellung3_bestelldatum = date3;

    static final double artikel1_preis = 10.00;
    static final double artikel2_preis = 20.00;
    static final String artikel1_name = "Schuhe";
    static final String artikel2_name = "Socken";

    static BestellungRepository bestellungRepository;
    static ArtikelRepository artikelRepository;
    static KundeRepository kundeRepository;

    static Bestellung bestellung1;
    static Bestellung bestellung2;
    static Bestellung bestellung3;

    static Artikel schuhe;
    static Artikel socken;



    @BeforeClass
    public static void setup() {
        bestellungRepository = new BestellungRepository();
        artikelRepository = new ArtikelRepository();
        kundeRepository = new KundeRepository();

        Transaction.begin();
        BestellungRepository.reset();
        ArtikelRepository.reset();
        KundeRepository.reset();

        Transaction.commit();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();
    }

    @Test
    public void a_create() {

        Transaction.begin();

        kunde1 = kundeRepository.create(kunde1_email, kunde1_passwort, kunde1_name);
        kunde2 = kundeRepository.create(kunde2_email, kunde2_passwort, kunde2_name);

        assertNotNull(kunde1);
        assertNotNull(kunde2);


        schuhe = artikelRepository.create(artikel1_preis, artikel1_name);
        socken = artikelRepository.create(artikel2_preis, artikel2_name);

        assertNotNull(schuhe);
        assertNotNull(socken);

        bestellung1 = bestellungRepository.create(bestellung1_lieferdatum, bestellung1_bestelldatum,kunde1);
        bestellung2 = bestellungRepository.create(bestellung2_lieferdatum, bestellung2_bestelldatum,kunde2);
        bestellung3 = bestellungRepository.create(bestellung3_lieferdatum, bestellung3_bestelldatum,kunde2);

        assertNotNull(bestellung1);
        assertNotNull(bestellung2);
        assertNotNull(bestellung3);

        Transaction.commit();
    }


    @Test
    public void b_join() {
        Transaction.begin();

        bestellung2.add(schuhe);
        bestellung1.add(socken);
        bestellung3.add(schuhe);
        bestellung3.add(socken);

        Transaction.commit();
    }

    @Test
    public void c_verify() {
        if (verbose) bestellungRepository.printAll();
        if (verbose) artikelRepository.printAll();

        // Bestellungen --------------------------------------------------
        List<Bestellung> bestellungen = bestellungRepository.findAll();

        assertEquals(3, bestellungen.size());
        assertTrue(bestellungen.contains(bestellung2));
        assertTrue(bestellungen.contains(bestellung3));


        // Artikel   -------------------------------------------------
        List<Artikel> artikeln = artikelRepository.findAll();

        assertEquals(2, artikeln.size());
        assertTrue(artikeln.contains(schuhe));
        assertTrue(artikeln.contains(socken));

        // Artikel -> Bestellungen ---------------------------------------

        assertEquals(2, schuhe.getBestellungen().size());
        assertEquals(2, socken.getBestellungen().size());

        assertTrue(schuhe.getBestellungen().contains(bestellung2));
        assertFalse(schuhe.getBestellungen().contains(bestellung1));

        // Bestellungen -> Artikel   -------------------------------------

        assertEquals(1, bestellung1.getArtikeln().size());
        assertFalse(bestellung1.getArtikeln().contains(schuhe));
        assertTrue(bestellung1.getArtikeln().contains(socken));

        assertEquals(2, bestellung3.getArtikeln().size());
        assertTrue(bestellung3.getArtikeln().contains(schuhe));
        assertTrue(bestellung3.getArtikeln().contains(socken));
    }

    @Test
    public void d_findCustomerbyOrder(){
        List<Kunde> customer = bestellungRepository.findCustomer(kunde1.getId());
        assertEquals ( kunde1.getId(),  customer.get(0).getId());
        try {
            artikelRepository.findOrder (123);
            fail ();
        }
        catch (NoResultException exc) {}
    }
}
