package org.se.lab;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import webshop.persistence.Transaction;

import org.se.lab.persistence.Persistence;

import javax.persistence.EntityManager;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)

public class WebShopWriterTest extends WebShopTest {
    final static String writer = "webshop_teamleader";

    final static String password = "teamleader";
    final static String admin = "webshop_florian";
    final static String adminPassword = "klug";


    @BeforeClass
    public static void setup() {
        Persistence.connect(writer, password);
        WebShopWriterTest.initialize();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();
        Persistence.connect(admin, adminPassword);
        reset(Persistence.getEntityManager());
        Persistence.close();
    }


    @Test
    public void a_createEntity() {
        Transaction.begin();
        kundeRepository.create(mail, name, pw);
        Transaction.commit();
    }

    @Test
    public void b_modifyEntry() {
        Kunde kunde = kundeRepository.findByName(name);

        Transaction.begin();
        kunde.setEmail(newMail);
        Transaction.commit();
        Assert.assertEquals(kunde.getEmail(), newMail);
    }

    @Test
    public void d_removeEntity() {

        try {
            reset(Persistence.getEntityManager());
        } catch (Exception exc) {
            assertTrue(permissionDenied(exc));
            Transaction.rollback();

        }
    }

   /* @Test
    public void c_createView() {

        Transaction.begin();
        try {
            Persistence.getEntityManager().createNativeQuery(createTestWebshop).executeUpdate();
            Transaction.commit();
        } catch (Exception exc) {
            System.out.println(exc);
            assertTrue(permissionDenied(exc));
            Transaction.rollback();
        }
    }*/
}
