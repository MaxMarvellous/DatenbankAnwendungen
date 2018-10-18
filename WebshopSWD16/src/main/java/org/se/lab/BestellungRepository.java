

package org.se.lab;

import webshop.persistence.Persistence;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class BestellungRepository extends org.se.lab.persistence.Repository<Bestellung>
        implements org.se.lab.persistence.IRepository<Bestellung> {

    public BestellungRepository() {
        super(Bestellung.class);
    }

    public Bestellung create(Date lieferdatum, Date bestelldatum, Kunde kunde) {
        Bestellung bestellung = new Bestellung(lieferdatum, bestelldatum, kunde);

        entityManager.persist(bestellung);

        return bestellung;
    }

    static void reset() {
        Persistence.resetTable(schema,junctionTable);
        Persistence.resetTable(schema,table);
        Persistence.resetSequence(schema,sequence);


    }

    static final String table = "bestellung";
    static final String sequence = "bestellung_sequence";
    static final String junctionTable = "bestellung_artikel";
    static final String schema = "webshop";


    public List<Kunde> findCustomer(int id) {
        TypedQuery<Kunde> query = entityManager.createNamedQuery("bestellung.findCustomer", Kunde.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Bestellung findAllBestellungen() {
        TypedQuery<Bestellung> query = entityManager.createNamedQuery("bestellung.findAll", Bestellung.class);
        return query.getSingleResult();
    }
}
