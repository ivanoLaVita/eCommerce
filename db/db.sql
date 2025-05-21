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