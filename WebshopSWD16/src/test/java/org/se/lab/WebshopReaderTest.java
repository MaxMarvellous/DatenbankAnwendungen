package org.se.lab;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import webshop.persistence.Transaction;
import webshop.persistence.config.PersistenceUnitProperties;

import org.se.lab.persistence.Persistence;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)

public class WebshopReaderTest extends WebShopTest {


    final static String adminUser = "webshop_florian";
    final static String adminPassword = "klug";

    final static String user = "webshop_apprentice";

    final static String password = "apprentice";

    final static String kunde1_name = "hans";
    final static String kunde1_email = "mail";
    final static String kunde1_passwort = "xxx";


    @BeforeClass
    public static void setup() {

        System.out.println(
                "Connecting to " + PersistenceUnitProperties.getUrl());


        Persistence.connect(adminUser, adminPassword);
        WebshopReaderTest.initialize();
        Transaction.begin();

        kundeRepository.create(kunde1_email, kunde1_name, kunde1_passwort);
        Transaction.commit();

        Persistence.close();

        Persistence.connect(user, password);
        WebshopReaderTest.initialize();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();

        Persistence.connect(adminUser, adminPassword);
        reset(Persistence.getEntityManager());
        Persistence.close();
    }


    @Test
    public void a_createEntity() {
        Transaction.begin();

        try {
            kundeRepository.create("xxx", "xxx", "xxx");
            Transaction.commit();

            Assert.fail();
        } catch (Exception exc) {
            System.out.println(exc);
            assertTrue(permissionDenied(exc));
            Transaction.rollback();
        }
    }

    @Test
    public void d_modify() {
        Transaction.begin();

        Kunde kunde = kundeRepository.findByName(name);

        kunde.setEmail(newMail);
        Assert.assertEquals(kunde.getEmail(), newMail);

        try {
            Transaction.commit();
        } catch (Exception exc) {
            assertTrue(permissionDenied(exc));
        }
    }

    @Test
    public void c_createView() {
        Transaction.begin();

        try {
            Persistence.getEntityManager().createNativeQuery(createTestWebshop).executeUpdate();
            Transaction.commit();

            Assert.fail();
        } catch (Exception exc) {
            System.out.println(exc);
            assertTrue(permissionDenied(exc));
            Transaction.rollback();
        }
    }

    @Test
    public void e_remove() {
        try {
            reset(Persistence.getEntityManager());
        } catch (Exception exc) {
            assertTrue(permissionDenied(exc));
        }
    }

    @Test
    public void b_read() {
        Kunde kunde = kundeRepository.findByName(name);
        Assert.assertNotNull(kunde);
        Assert.assertEquals(kunde.getName_kunde(), name);
    }
}
