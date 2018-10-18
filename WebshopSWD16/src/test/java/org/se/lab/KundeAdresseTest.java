
package org.se.lab;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import webshop.persistence.Transaction;
import javax.persistence.NoResultException;

import org.se.lab.persistence.Persistence;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class KundeAdresseTest {

    static final boolean verbose = true;


    //static final int kunde1_id = 1;
    static final String kunde1_email = "kunde1_email@mail.com";
    static final String kunde1_name = "hans";
    static final String kunde1_passwort = "abc";


    static final String kunde2_email = "kunde2_email@mail.com";
    static final String kunde2_name = "franz";
    static final String kunde2_passwort = "abc";

    static final int plz = 8010;
    static final String adresse_land = "AUT";
    static final String adresse_stadt = "Graz";
    static final String adresse_strasse = "Alte Poststraße";
    static final int adresse_hausnummer = 69;

    static KundeRepository kundeRepository;
    static AdresseRepository adresseRepository;
    static BestellungRepository bestellungRepository;

    static Kunde hans;
    static Adresse hans_adresse;

    @BeforeClass
    public static void setup() {
        bestellungRepository = new BestellungRepository();
        kundeRepository = new KundeRepository();
        adresseRepository = new AdresseRepository();

        Transaction.begin();
        BestellungRepository.reset();
        KundeRepository.reset();
        AdresseRepository.reset();
        Transaction.commit();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();
    }


    @Test
    public void a_create() {
        Transaction.begin();

        hans = kundeRepository.create(kunde1_email, kunde1_name, kunde1_passwort);

        hans_adresse = adresseRepository.create(adresse_land, adresse_stadt, adresse_strasse, adresse_hausnummer, plz);

        assertNotNull(hans);
        assertNotNull(hans_adresse);


        Transaction.commit();

        if (verbose) System.out.println("Persisted " + hans);
        if (verbose) System.out.println("Persisted " + hans_adresse);

    }

    @Test
    public void b_join() {
        Transaction.begin();

        hans.setAdresse(hans_adresse);

        Transaction.commit();
    }

    @Test
    public void c_verify() {

        // Kunde   -------------------------------------------------

        List<Kunde> kunden = kundeRepository.findAll();
        assertEquals(1, kunden.size());


        assertEquals(hans, kunden.get(0));

        assertEquals(hans_adresse, hans.getAdresse());

        if (verbose) for (Kunde ku : kunden)
            System.out.println("Found " + ku);


        // Adresse   ----------------------------------------

        List<Adresse> adressen = adresseRepository.findAll();
        assertEquals(1, adressen.size());

        assertEquals(hans_adresse, adressen.get(0));

        if (verbose) for (Adresse adr : adressen)
            System.out.println("Found " + adr);


        assertEquals(hans, hans_adresse.getKunde());


        System.out.println("\nzweiten Kunden zuweisen muss fehlschlagen:\n");

        Transaction.begin();
        Kunde franz = kundeRepository.create(kunde2_email, kunde2_name, kunde2_passwort);

        franz.setAdresse(hans_adresse);
        try {
            Transaction.commit();

            fail();
        } catch (Exception exc) {
        }

    }

    @Test
    public void d_findByCustomerName(){
        Kunde hans = kundeRepository.findByName(kunde1_name);
        assertEquals ( kunde1_name,  hans.getName_kunde());
        try {
            kundeRepository.findByName ("unknown");
            fail ();
        }
        catch (NoResultException exc) {}
    }
    @Test
    public void e_findByAddressLand(){
       Adresse hans_adresse = adresseRepository.findByLand(adresse_land);
        assertEquals ( adresse_land,  hans_adresse.getLand());
        try {
            kundeRepository.findByName ("xxx");
            fail ();
        }
        catch (NoResultException exc) {}
    }

    @Test
    public void f_findCustomer(){
        Kunde customer = adresseRepository.findCustomer(hans_adresse.getId());
        assertEquals ( hans.getId(),  customer.getId());
        try {
            adresseRepository.findCustomer (123);
            fail ();
        }
        catch (NoResultException exc) {}
    }
}
