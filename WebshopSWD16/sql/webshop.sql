SET SCHEMA 'webshop';


CREATE SEQUENCE bestellung_sequence   START WITH 1001 INCREMENT BY 1;

CREATE SEQUENCE artikel_sequence    START WITH 5001 INCREMENT BY  1;

CREATE SEQUENCE kunde_sequence   START WITH 101 INCREMENT BY 1;

CREATE SEQUENCE adresse_sequence    START WITH 100001 INCREMENT BY  1;


CREATE TABLE adresse (
  id         INT NOT NULL DEFAULT nextval ('adresse_sequence'),
  land       VARCHAR,
  plz        INT,
  stadt      VARCHAR,
  strasse    VARCHAR,
  hausnummer INT
);

ALTER TABLE adresse
  ADD CONSTRAINT adresse_pk PRIMARY KEY (id);


CREATE TABLE kunde (
  id         INT NOT NULL DEFAULT nextval ('kunde_sequence'),
  email      VARCHAR,
  passwort   VARCHAR,
  name_kunde VARCHAR,
  adresse_id INT UNIQUE
);


ALTER TABLE kunde
  ADD CONSTRAINT kunde_pk PRIMARY KEY (id);

ALTER TABLE kunde
  ADD CONSTRAINT adresse_pk FOREIGN KEY (adresse_id)
REFERENCES adresse;


CREATE TABLE bestellung (
  id           INT NOT NULL DEFAULT nextval ('bestellung_sequence'),
  lieferdatum  DATE,
  bestelldatum DATE,
  kunde_id     INT NOT NULL
);

ALTER TABLE bestellung
  ADD CONSTRAINT bestellung_pk PRIMARY KEY (id);

ALTER TABLE bestellung
  ADD CONSTRAINT kunde_fk FOREIGN KEY (kunde_id)
REFERENCES kunde;


CREATE TABLE artikel (
  id           INT NOT NULL DEFAULT nextval ('artikel_sequence'),
  preis        DECIMAL,
  name_artikel VARCHAR
);
ALTER TABLE artikel
  ADD CONSTRAINT artikel_pk PRIMARY KEY (id);

CREATE TABLE bestellung_artikel (
  bestellung_id INT NOT NULL,
  artikel_id    INT NOT NULL
);
ALTER TABLE bestellung_artikel
  ADD CONSTRAINT bestellung_artikel_pk PRIMARY KEY (bestellung_id, artikel_id);

ALTER TABLE bestellung_artikel
  ADD CONSTRAINT bestellung_fk FOREIGN KEY (bestellung_id) REFERENCES bestellung;

ALTER TABLE bestellung_artikel
  ADD CONSTRAINT artikel_fk FOREIGN KEY (artikel_id) REFERENCES artikel;
