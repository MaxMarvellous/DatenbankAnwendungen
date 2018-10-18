package org.se.lab;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery (name="kunde.findByName", query = "SELECT k FROM Kunde k WHERE k.name_kunde =:name_kunde"),
        @NamedQuery(name = "kunde.findCustomerOnAddress", query = "SELECT a FROM Kunde k JOIN Adresse a WHERE   k.id = :id"),
        @NamedQuery(name="kunde.findAll", query="SELECT k FROM Kunde k")

})


public class Kunde {
    @SequenceGenerator(name = "kundeIdGenerator",
            sequenceName = "kunde_sequence",
            allocationSize = 1)

    @Id
    @GeneratedValue(generator = "kundeIdGenerator")
    private int id;
    private String email;
    private String name_kunde;
    private String passwort;


    @OneToOne
    private Adresse adresse;

    protected Kunde() {
    }

    @OneToMany(mappedBy = "kunde")
    private Collection<Bestellung> bestellungen = new ArrayList<>();

    Kunde(String email, String name_kunde, String passwort) {
        this.email = email;
        this.name_kunde = name_kunde;
        this.passwort = passwort;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getName_kunde() {
        return name_kunde;
    }


    //One to One

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;

        adresse.setKunde(this);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //One to Many
    void add(Bestellung bestellung) {
        if (bestellungen.contains(bestellung))
            return;

        bestellungen.add(bestellung);
    }

    void remove(Bestellung bestellung) {
        bestellungen.remove(bestellung);
    }

    public List<Bestellung> getBestellungen() {
        return (List<Bestellung>) bestellungen;
    }

    @Override
    public String toString() {
        String string = "Kunden id: " + getId() + ", Email: " + getEmail() + ", Name: " + getName_kunde();

        if (bestellungen == null)
            string += ", Bestellungen == null !!!";
        else if (bestellungen.size() == 0)
            string += " Bestellungen: none";
        else {
            string += " Bestellungen: ";
            for (Bestellung best : bestellungen) string += " " + best.getBestelldatum() + " " + best.getLieferdatum();
        }

        return string;

    }

}
