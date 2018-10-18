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

public class WebshopAdminTest extends WebShopTest {

    final static String admin = "webshop_florian";
    final static String password = "klug";


    @BeforeClass
    public static void setup() {
        Persistence.connect(admin, password);

        reset(Persistence.getEntityManager());

        WebshopAdminTest.initialize();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();
    }

    @Test
    public void b_createEntity() {
        Transaction.begin();

        kundeRepository.create("new", "new", "new");
        Transaction.commit();
        Kunde kunde = kundeRepository.findByName("new");
        Assert.assertNotNull(kunde);
        Assert.assertEquals(kunde.getName_kunde(), "new");
    }


    @Test
    public void d_createView() {
        Transaction.begin();
        Persistence.getEntityManager().createNativeQuery(createTestWebshop).executeUpdate();
        Transaction.commit();
    }

    @Test
    public void e_deleteView() {
        Transaction.begin();
        Persistence.getEntityManager().createNativeQuery(deleteTestWebshop).executeUpdate();
        Transaction.commit();
    }


    @Test
    public void c_modifyEntity() {
        Kunde kunde = kundeRepository.findByName("new");

        Transaction.begin();
        kunde.setEmail("newEmail");
        Transaction.commit();
        Assert.assertEquals(kunde.getEmail(), "newEmail");

    }

    @Test
    public void a_removeEntity() {
        reset(Persistence.getEntityManager());
    }
}
