package org.se.lab;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@NamedQueries({

        @NamedQuery(name = "bestellung.findCustomer", query = "SELECT k FROM Bestellung b JOIN Kunde k WHERE   k.id = :id"),
        @NamedQuery(name="bestellung.findAll", query="SELECT b FROM Bestellung b")

})
public class Bestellung {

    @SequenceGenerator(name = "bestellungIdGenerator",
            sequenceName = "bestellung_sequence",
            allocationSize = 1)


    @Id
    @GeneratedValue(generator = "bestellungIdGenerator")
    private int id;

    @Temporal(TemporalType.DATE)
    private Date bestelldatum;
    @Temporal(TemporalType.DATE)
    private Date lieferdatum;


    @ManyToOne(optional = false)
    private Kunde kunde;


    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "bestellung_id")
            , inverseJoinColumns = @JoinColumn(name = "artikel_id"))
    private Collection<Artikel> artikeln = new ArrayList<>();

    protected Bestellung() {
    }

    Bestellung(Date lieferdatum, Date bestelldatum,Kunde kunde) {
        //this.kunde= kunde;
        set(kunde);
        this.bestelldatum = bestelldatum;
        this.lieferdatum = lieferdatum;
    }

    public int getId() {
        return id;
    }

    public Date getLieferdatum() {
        return lieferdatum;
    }

    public Date getBestelldatum() {
        return bestelldatum;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Bestellung && ((Bestellung) o).id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }


    //many to one

    public Kunde getKunde() {
        return kunde;
    }

    public void set(Kunde kunde) {

        if (this.kunde != null)
            throw new IllegalStateException("Kunde already set!");

        this.kunde = kunde;

        kunde.add(this);
    }


    public void moveTo(Kunde kunde) {
        if (this.kunde != null) {
            if (kunde.equals(this.kunde))
                return;

            this.kunde.remove(this);

            this.kunde = null;
        }

        set(kunde);
    }

    // many to many -------------------------------
    public void add(Artikel artikel) {
        if (artikeln.contains(artikel))
            return;

        artikeln.add(artikel);
        artikel.add(this);
    }

    public Collection<Artikel> getArtikeln() {
        return artikeln;
    }

}
