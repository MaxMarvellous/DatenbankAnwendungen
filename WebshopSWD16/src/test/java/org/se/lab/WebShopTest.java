package org.se.lab;

import java.util.List;

import static org.junit.Assert.*;

import webshop.util.Printer;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import webshop.persistence.Transaction;

import org.se.lab.persistence.Persistence;

import javax.persistence.EntityManager;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public abstract class WebShopTest {





    final static String name = "name";
    final static String mail = "mail";
    final static String pw = "xxx";

    final static String newMail = "newMail";
    static final String createTestWebshop = "CREATE OR REPLACE VIEW webshop.testKunde AS SELECT * FROM webshop.kunde";

    static final String deleteTestWebshop = "DROP VIEW webshop.testKunde";

    static KundeRepository kundeRepository = null;
    static AdresseRepository adresseRepository = null;
    static ArtikelRepository artikelRepository = null;
    static BestellungRepository bestellungRepository = null;


    static boolean permissionDenied(Exception exc) {

        System.out.println(exc.getMessage());

        return exc.getMessage().contains("permission")
                && exc.getMessage().contains("denied");
    }

    public static void reset(EntityManager em) {
        Transaction.begin();


        try {

            em.createQuery("DELETE FROM Adresse").executeUpdate();
            em.createQuery("DELETE FROM Artikel").executeUpdate();
            em.createQuery("DELETE FROM Bestellung").executeUpdate();
            em.createQuery("DELETE FROM Kunde").executeUpdate();

            Transaction.commit();

        }catch (Exception exc){
            System.out.println(exc);

            Transaction.rollback();
        }

    }

    public static void initialize() {
        kundeRepository = new KundeRepository();
        adresseRepository = new AdresseRepository();
        artikelRepository = new ArtikelRepository();
        bestellungRepository = new BestellungRepository();
    }

}




