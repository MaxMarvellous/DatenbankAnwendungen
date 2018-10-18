package org.se.lab;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@NamedQueries({
        @NamedQuery(name = "artikel.findOrder", query = "SELECT o FROM Artikel a JOIN a.bestellungen o WHERE   a.id = :id"),
        @NamedQuery(name="artikel.findAll", query="SELECT a FROM Artikel a")

})
public class Artikel {

    @SequenceGenerator(name = "artikelIdGenerator",
            sequenceName = "artikel_sequence",
            allocationSize = 1)

    @Id
    @GeneratedValue(generator = "artikelIdGenerator")
    private int id;
    private double preis;
    private String name_artikel;

    @ManyToMany(mappedBy = "artikeln")
    private Collection<Bestellung> bestellungen = new ArrayList<>();


    protected Artikel() {
    }

    Artikel(double preis, String name_artikel) {
        this.preis = preis;
        this.name_artikel = name_artikel;
    }

    public int getId() {
        return id;
    }

    public double getPreis() {
        return preis;
    }

    public String getName_artikel() {
        return name_artikel;
    }


    // many to many -------------------------------
    public Collection<Bestellung> getBestellungen() {
        return bestellungen;
    }

    void add(Bestellung bestellung) {
        if (bestellungen.contains(bestellung))
            return;

        bestellungen.add(bestellung);
    }

}
