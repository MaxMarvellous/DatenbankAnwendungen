package org.se.lab;

import webshop.persistence.Persistence;

import javax.persistence.TypedQuery;

public class KundeRepository extends org.se.lab.persistence.Repository<Kunde>
        implements org.se.lab.persistence.IRepository<Kunde> {

    public KundeRepository() {
        super(Kunde.class);
    }

    public Kunde create(String email, String name_kunde, String passwort) {
        Kunde kunde = new Kunde(email, name_kunde, passwort);

        entityManager.persist(kunde);

        return kunde;
    }

    public Kunde findByName(String name_kunde) {
        TypedQuery<Kunde> query = entityManager.createNamedQuery("kunde.findByName", Kunde.class);
        query.setParameter("name_kunde", name_kunde);

        return query.getSingleResult();
    }

    public Kunde findCustomerOnAddres(int id) {
        TypedQuery<Kunde> query = entityManager.createNamedQuery("kunde.findCustomerOnAddress", Kunde.class).setParameter("id",id);
        return query.getSingleResult();
    }

    public Kunde findAllKunden() {
        TypedQuery<Kunde> query = entityManager.createNamedQuery("kunde.findAll", Kunde.class);
        return query.getSingleResult();
    }

    static void reset() {
        Persistence.resetTable(schema, table);
        Persistence.resetSequence(schema, sequence);
    }

    static final String table = "kunde";
    static final String sequence = "kunde_sequence";
    static final String schema = "webshop";

}
