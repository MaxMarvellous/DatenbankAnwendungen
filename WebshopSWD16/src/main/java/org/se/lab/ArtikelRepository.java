

package org.se.lab;

import webshop.persistence.Persistence;

import javax.persistence.TypedQuery;

public class ArtikelRepository extends org.se.lab.persistence.Repository<Artikel>
        implements org.se.lab.persistence.IRepository<Artikel> {


    public ArtikelRepository() {
        super(Artikel.class);
    }

    public Artikel create(double preis, String name_artikel) {

        Artikel art = new Artikel(preis, name_artikel);

        entityManager.persist(art);

        return art;
    }

    static void reset() {
        Persistence.resetTable(schema, junctionTable);
        Persistence.resetTable(schema, table);
        Persistence.resetSequence(schema, sequence);
    }

    static final String table = "artikel";
    static final String sequence = "artikel_sequence";
    static final String junctionTable = BestellungRepository.junctionTable;
    static final String schema = "WEBSHOP";


    public Bestellung findOrder(int id) {
        TypedQuery<Bestellung> query = entityManager.createNamedQuery("artikel.findOrder", Bestellung.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public Artikel findAllArtikel() {
        TypedQuery<Artikel> query = entityManager.createNamedQuery("artikel.findAll", Artikel.class);
        return query.getSingleResult();
    }
}
