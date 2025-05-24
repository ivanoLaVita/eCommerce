DROP DATABASE IF EXISTS LaVitaJewelry;
CREATE DATABASE LaVitaJewelry;
USE LaVitaJewelry;

-- UTENTE
DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
    id        INT            NOT NULL AUTO_INCREMENT,
    email     VARCHAR(100)   NOT NULL,
    username  VARCHAR(50)    NOT NULL,
    password  VARCHAR(64)    NOT NULL,
    nome      VARCHAR(50)    NOT NULL,
    cognome   VARCHAR(50)    DEFAULT NULL,
    admin     TINYINT(1)     NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (username)
);

-- CATEGORIA
DROP TABLE IF EXISTS categoria;
CREATE TABLE categoria (
    nome VARCHAR(50) NOT NULL,
    PRIMARY KEY (nome)
);

-- PRODOTTO
DROP TABLE IF EXISTS prodotto;
CREATE TABLE prodotto (
    id             INT             NOT NULL,
    nome           VARCHAR(100)    NOT NULL,
    descrizione    VARCHAR(500)    NOT NULL,
    quantita       INT             NOT NULL,
    costo          DOUBLE          NOT NULL,
    sesso          ENUM('m','f')   NOT NULL,
    immagine       VARCHAR(1000)   DEFAULT NULL,
    categoriaNome  VARCHAR(50)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (categoriaNome) REFERENCES categoria(nome) ON UPDATE CASCADE ON DELETE CASCADE
);

-- INDIRIZZO
DROP TABLE IF EXISTS indirizzo;
CREATE TABLE indirizzo (
    id         INT           NOT NULL AUTO_INCREMENT,
    citta      VARCHAR(50)   NOT NULL,
    provincia  VARCHAR(10)   NOT NULL,
    cap        VARCHAR(10)   NOT NULL,
    via        VARCHAR(50)   NOT NULL,
    civico     VARCHAR(10)   NOT NULL,
    utenteId   INT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (utenteId) REFERENCES utente(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- METODO DI PAGAMENTO
DROP TABLE IF EXISTS metodoDiPagamento;
CREATE TABLE metodoDiPagamento (
    id           INT                  NOT NULL AUTO_INCREMENT,
    tipo         ENUM('carta','iban') NOT NULL,
    iban         CHAR(27)             DEFAULT NULL,
    numeroCarta  VARCHAR(19)          DEFAULT NULL,
    utenteId     INT                  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (utenteId) REFERENCES utente(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- ORDINE
DROP TABLE IF EXISTS ordine;
CREATE TABLE ordine (
    id           INT          NOT NULL AUTO_INCREMENT,
    data         DATE         NOT NULL,
    costoTotale  DOUBLE       NOT NULL,
    utenteId     INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (utenteId) REFERENCES utente(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- INSERIMENTO (prodotti in un ordine)
DROP TABLE IF EXISTS inserimento;
CREATE TABLE inserimento (
    id         INT           NOT NULL AUTO_INCREMENT,
    prodottoId INT           NOT NULL,
    ordineId   INT           NOT NULL,
    quantita   INT           NOT NULL,
    immagine   VARCHAR(1000) DEFAULT NULL,
    nome       VARCHAR(100)  NOT NULL,
    costo      DOUBLE        NOT NULL,
    PRIMARY KEY (id, ordineId),
    FOREIGN KEY (ordineId) REFERENCES ordine(id) ON UPDATE CASCADE ON DELETE CASCADE
);
