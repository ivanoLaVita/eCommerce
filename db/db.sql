DROP database IF EXISTS LaVitaJewelry;
CREATE database LaVitaJewelry;
USE LaVitaJewelry;

DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
email        varchar(100)    NOT NULL,
username     varchar(50)     NOT NULL,
password     varchar(64)     NOT NULL,
nome         varchar(50)     NOT NULL,
cognome      varchar(50)     DEFAULT NULL,
admin        tinyint(1)      NOT NULL DEFAULT '0',
PRIMARY KEY (email),
UNIQUE (username)
);

DROP TABLE IF EXISTS indirizzo;
CREATE TABLE indirizzo (
id             int             NOT NULL AUTO_INCREMENT,
citta          varchar(50)     NOT NULL,
provincia      varchar(10)     NOT NULL,
cap            varchar(10)     NOT NULL,
via            varchar(50)     NOT NULL,
civico         varchar(10)     NOT NULL,
utenteEmail    varchar(50)     NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (utenteEmail) REFERENCES utente(email) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS metodoDiPagamento;
CREATE TABLE metodoDiPagamento (
id             int                     NOT NULL AUTO_INCREMENT,
tipo           enum('carta','iban')    NOT NULL,
iban           char(27)                DEFAULT NULL,
numeroCarta    varchar(19)             DEFAULT NULL,
utenteEmail    varchar(50)             NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (utenteEmail) REFERENCES utente(email) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS ordine;
CREATE TABLE ordine (
id             int             NOT NULL AUTO_INCREMENT,
data           date            NOT NULL,
costoTotale    double          NOT NULL,
utenteEmail    varchar(50)     NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (utenteEmail) REFERENCES utente(email) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS categoria;
CREATE TABLE categoria (
nome varchar(50)     NOT NULL,
PRIMARY KEY (nome)
);







