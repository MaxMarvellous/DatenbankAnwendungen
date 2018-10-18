
package org.se.lab;

import webshop.persistence.Persistence;

import javax.persistence.TypedQuery;

public class AdresseRepository extends org.se.lab.persistence.Repository<Adresse>
        implements org.se.lab.persistence.IRepository<Adresse> {


    public AdresseRepository() {
        super(Adresse.class);
    }

    public Adresse create(String land, String stadt, String strasse, int hausnummer,int plz) {

        Adresse adr = new Adresse(land,stadt,strasse,hausnummer,plz);

        entityManager.persist(adr);

        return adr;
    }
    public Adresse findByLand (String land) {
        TypedQuery<Adresse> query = entityManager.createNamedQuery ( "adresse.findByLand" ,Adresse.class );
        query.setParameter ("land", land);

        return query.getSingleResult();
    }
    public Kunde findCustomer (int id) {
        TypedQuery<Kunde> query = entityManager.createNamedQuery ( "adresse.findCustomer" ,Kunde.class ).setParameter("id",id);

        return query.getSingleResult();
    }


    public Adresse findAllAdressen() {
        TypedQuery<Adresse> query = entityManager.createNamedQuery("adresse.findAll", Adresse.class);
        return query.getSingleResult();
    }
    static void reset() {
        Persistence.resetTable(schema,table);
        Persistence.resetSequence(schema,sequence);
    }


    static final String table = "adresse";
    static final String sequence = "adresse_sequence";
    static final String schema = "WEBSHOP";

}
