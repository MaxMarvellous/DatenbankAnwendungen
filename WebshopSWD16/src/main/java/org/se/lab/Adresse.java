package org.se.lab;

import javax.persistence.*;


@Entity
@NamedQueries({
        @NamedQuery (name="adresse.findByLand", query = "SELECT a FROM Adresse a WHERE a.land =:land"),
        @NamedQuery(name = "adresse.findCustomer", query = "SELECT k FROM Adresse a JOIN a.kunde k WHERE   a.id = :id"),
        @NamedQuery(name="adresse.findAll", query="SELECT a FROM Adresse a")
})
public class Adresse {


    @SequenceGenerator(name = "adresseIdGenerator",
            sequenceName = "adresse_sequence",
            allocationSize = 1)

    @Id
    @GeneratedValue(generator = "adresseIdGenerator")
    private int id;
    private String land;
    private String stadt;
    private String strasse;
    private int hausnummer;
    private int plz;


    @OneToOne(mappedBy = "adresse")
    private Kunde kunde;

    protected Adresse() {
    }


    Adresse(String land, String stadt, String strasse, int hausnummer, int plz) {
        this.land = land;
        this.stadt = stadt;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;


    }

    public int getId() {
        return id;
    }

    public String getLand() {
        return land;
    }

    public String getStadt() {
        return stadt;
    }

    public String getStrasse() {
        return strasse;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public int getPlz() {
        return plz;
    }


    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + getId() +
                ", land='" + getLand() + '\'' +
                ", stadt='" + getStadt() + '\'' +
                ", strasse='" + getStrasse() + '\'' +
                ", hausnummer=" + getHausnummer() +
                ", kunde=" + getKunde() +
                '}';
    }

    //One to One
    void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Kunde getKunde() {
        return kunde;
    }


}
