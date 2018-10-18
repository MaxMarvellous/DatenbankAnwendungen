/*
 * project    company
 * subproject persistence
*/

package org.se.lab.persistence;

import javax.persistence.EntityManager;

public class Persistence extends webshop.persistence.Persistence
{

    public static final String persistenceUnit = "webshop";


    public static EntityManager connect ()
    {
        return connect (persistenceUnit);
    }

    public static EntityManager connect (String user, String password)
    {
        return connect (persistenceUnit, user, password);
    }
}
