DROP DATABASE IF EXISTS LaVitaJewelry;
CREATE DATABASE LaVitaJewelry;
USE LaVitaJewelry;

-- USERS
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    email      VARCHAR(100)   NOT NULL,
    username   VARCHAR(50)    NOT NULL,
    password   VARCHAR(128)   NOT NULL,
    firstName  VARCHAR(50)    NOT NULL,
    lastName   VARCHAR(50)    DEFAULT NULL,
    admin      TINYINT(1)     NOT NULL DEFAULT 0,
    PRIMARY KEY (email),
    UNIQUE (username)
);

-- CATEGORY
DROP TABLE IF EXISTS category;
CREATE TABLE category (
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (name)
);

-- PRODUCT
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id           INT             NOT NULL,
    name         VARCHAR(100)    NOT NULL,
    description  VARCHAR(500)    NOT NULL,
    quantity     INT             NOT NULL,
    price        DOUBLE          NOT NULL,
    gender       ENUM('M','F')   NOT NULL,
    image        VARCHAR(1000)   DEFAULT NULL,
    categoryName VARCHAR(50)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (categoryName) REFERENCES category(name)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ADDRESS
DROP TABLE IF EXISTS address;
CREATE TABLE address (
    id            INT            NOT NULL AUTO_INCREMENT,
    city          VARCHAR(50)    NOT NULL,
    province      VARCHAR(10)    NOT NULL,
    postalCode    VARCHAR(10)    NOT NULL,
    street        VARCHAR(50)    NOT NULL,
    streetNumber  VARCHAR(10)    NOT NULL,
    userEmail     VARCHAR(50)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userEmail) REFERENCES users(email)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- PAYMENT METHOD
DROP TABLE IF EXISTS payment_method;
CREATE TABLE payment_method (
    id           INT                  NOT NULL AUTO_INCREMENT,
    type         ENUM('CARD','IBAN')  NOT NULL,
    iban         CHAR(27)             DEFAULT NULL,
    cardNumber   VARCHAR(19)          DEFAULT NULL,
    userEmail    VARCHAR(50)          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userEmail) REFERENCES users(email)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ORDERS
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id         INT          NOT NULL AUTO_INCREMENT,
    date       DATE         NOT NULL,
    totalCost  DOUBLE       NOT NULL,
    userEmail  VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userEmail) REFERENCES users(email)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- INSERIMENTO
DROP TABLE IF EXISTS inserimento;
CREATE TABLE inserimento (
    id         INT            NOT NULL AUTO_INCREMENT,
    prodottoId INT            NOT NULL,
    ordineId   INT            NOT NULL,
    quantita   INT            NOT NULL,
    immagine   VARCHAR(1000)  DEFAULT NULL,
    nome       VARCHAR(100)   NOT NULL,
    costo      INT            NOT NULL,
    PRIMARY KEY (id, ordineId),
    FOREIGN KEY (ordineId) REFERENCES orders(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- INSERT users
INSERT INTO users (email, username, password, firstname, lastname, admin) VALUES
("mario@gmail.com", "user1", SHA2('mario123', 512), 'Mario', 'Rossi', 0),
("luigi@gmail.com", "user2", SHA2('luigi123', 512), 'Luigi', 'Verdi', 0),
("admin@gmail.com", "admin", SHA2('admin1', 512), 'Admin', 'Admin', 1),
("musano@gmail.com", "mus", SHA2('123', 512), "Francesco", "Musano", 0),
("ivano@gmail.com", "ivan", SHA2('123', 512), "Ivan", "Russo", 0);

-- INSERT address
INSERT INTO address (city, province, postalCode, street, streetNumber, userEmail) VALUES
('Roma', 'RM', '00100', 'Via Roma', '1', 'mario@gmail.com'),
('Milano', 'MI', '20100', 'Corso Milano', '2', 'luigi@gmail.com'),
("Solofra", "AV", "83029", "Via Roma", "1", "musano@gmail.com"),
("Milano", "MI", "20100", "Via Roma", "1", "ivano@gmail.com");

-- INSERT category
INSERT INTO category (name) VALUES
("collane"),
("anelli"),
("bracciali"),
("orecchini");

-- INSERT product
INSERT INTO product (id, name, description, quantity, price, gender, image, categoryName) VALUES 
(1, 'Collana Infinity', 'Eterno romanticismo: I simboli dell’amore e dell’infinito sono fusi insieme in questa elegante collana LaVita Jewelry.', 50, 130, 'f', 'assets/img/collana1.jpg', 'collane'),
(2, 'Collana Mesmera', 'Scegli la pura eleganza di questa sfavillante collana Mesmera con zirconi luminosi.', 30, 300, 'f', 'assets/img/collana2.jpg', 'collane'),
(3, 'Collana Gema', 'Collana cristallina placcata rodio, mix di tagli e tonalità blu.', 15, 750, 'f', 'assets/img/collana3.jpg', 'collane'),
(4, 'Anello Vittore', 'Anello con pietre bianche sfaccettate a forma di goccia.', 10, 160, 'f', 'assets/img/anello1.jpg', 'anelli'),
(5, 'Anello Hyperbola', 'Design intrecciato con zirconia trasparente, placcato rodio.', 50, 140, 'f', 'assets/img/anello2.jpg', 'anelli'),
(6, 'Anello Cocktail Lucent', 'Design ottagonale con 138 sfaccettature, trasparente e brillante.', 50, 250, 'f', 'assets/img/anello3.jpg', 'anelli'),
(7, 'Bracciale Angelic', 'Braccialetto rodiato con Clear Crystal.', 50, 160, 'f', 'assets/img/bracciale1.jpg', 'bracciali'),
(8, 'Bracciale Millenia', 'Bracciale sostenibile con cristalli ReCreated™ blu.', 30, 250, 'f', 'assets/img/bracciale2.jpg', 'bracciali'),
(9, 'Bracciale Mesmera', 'Cristalli con montatura a griffe in un design elegante.', 25, 200, 'f', 'assets/img/bracciale3.jpg', 'bracciali'),
(10, 'Orecchini a lobo Matrix', 'Orecchini con Crystal Pearl e Zirconia taglio Round.', 40, 80, 'f', 'assets/img/orecchini1.jpg', 'orecchini'),
(11, 'Orecchini a lobo Constella', 'Pietre trasparenti in tecnica pavé, placcati rodio.', 10, 100, 'f', 'assets/img/orecchini2.jpg', 'orecchini'),
(12, 'Orecchini pendenti Mesmera', 'Design sfavillante con cristalli audaci e montatura a griffe.', 60, 130, 'f', 'assets/img/orecchini3.jpg', 'orecchini');

-- INSERT orders
INSERT INTO orders (id, date, totalCost, userEmail) VALUES
(1, '2025-01-10', 560.00, 'mario@gmail.com'),
(2, '2025-02-15', 1600.00, 'luigi@gmail.com'),
(3, '2025-03-20', 240.00, 'musano@gmail.com'),
(4, '2025-04-25', 340.00, 'ivano@gmail.com'),
(5, '2025-05-30', 660.00, 'mario@gmail.com');

-- INSERT inserimento
INSERT INTO inserimento (id, prodottoId, ordineId, quantita, immagine, nome, costo) VALUES
(1, 1, 1, 1, 'assets/img/collana1.jpg', 'Collana Infinity', 130),
(2, 2, 1, 1, 'assets/img/collana2.jpg', 'Collana Mesmera', 300),
(3, 12, 1, 1, 'assets/img/orecchini3.jpg', 'Orecchini pendenti Mesmera', 130),
(4, 3, 2, 2, 'assets/img/collana3.jpg', 'Collana Gema', 750),
(5, 11, 2, 1, 'assets/img/orecchini2.jpg', 'Orecchini a lobo Constella', 100),
(6, 4, 3, 1, 'assets/img/anello1.jpg', 'Anello Vittore', 160),
(7, 10, 3, 1, 'assets/img/orecchini1.jpg', 'Orecchini a lobo Matrix', 80),
(8, 5, 4, 1, 'assets/img/anello2.jpg', 'Anello Hyperbola', 140),
(9, 9, 4, 1, 'assets/img/bracciale3.jpg', 'Bracciale Mesmera', 200),
(10, 6, 5, 1, 'assets/img/anello3.jpg', 'Anello Cocktail Lucent', 250),
(11, 7, 5, 1, 'assets/img/bracciale1.jpg', 'Bracciale Angelic', 160),
(12, 8, 5, 1, 'assets/img/bracciale2.jpg', 'Bracciale Millenia', 250);

-- INSERT payment_method
INSERT INTO payment_method (type, iban, cardNumber, userEmail) VALUES
('CARD', NULL, '1234-5678-9123-4567', 'mario@gmail.com'),
('CARD', NULL, '9876-5432-1098-7654', 'luigi@gmail.com'),
("CARD", NULL, "1234-1234-1234-1234", "musano@gmail.com"),
("CARD", NULL, "7777-7777-7777-7777", "ivano@gmail.com");
