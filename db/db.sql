DROP DATABASE IF EXISTS LaVitaJewelry;
CREATE DATABASE LaVitaJewelry;
USE LaVitaJewelry;

-- USERS
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    email      VARCHAR(100)   NOT NULL,
    username   VARCHAR(50)    NOT NULL,
    password   VARCHAR(128)    NOT NULL,
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
    id           INT             NOT NULL AUTO_INCREMENT,
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
    userEmail     VARCHAR(50)            NOT NULL,
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
    userEmail     varchar(50)                 NOT NULL,
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
    userEmail      varchar(50)          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userEmail) REFERENCES users(email)
        ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO users (email, username, password, firstname, lastname, admin) VALUES
("mario@gmail.com", "user1", SHA2('mario123', 512), 'Mario', 'Rossi', 0),
("luigi@gmail.com", "user2", SHA2('luigi123', 512), 'Luigi', 'Verdi', 0),
("admin@gmail.com", "admin", SHA2('admin1', 512), 'Admin', 'Admin', 1),
("musano@gmail.com", "mus", SHA2('123', 512), "Francesco", "Musano", 0),
("ivano@gmail.com", "ivan", SHA2('123', 512), "Ivan", "Russo", 0);

INSERT INTO address (city, province, postalCode, street, streetNumber, userEmail) VALUES
('Roma', 'RM', '00100', 'Via Roma', '1', 'mario@gmail.com'),
('Milano', 'MI', '20100', 'Corso Milano', '2', 'luigi@gmail.com'),
("Solofra", "AV", "83029", "Via Roma", "1", "musano@gmail.com"),
("Milano", "MI", "20100", "Via Roma", "1", "ivano@gmail.com");

/* INTO category (nome) VALUES
("collane"),
("anelli"),
("bracciali"),
("orecchini");
*/
-- Insert values into 'prodotto' table without the 'immagine' field
/* INSERT INTO product (id, nome, descrizione, quantita, costo, sesso, immagine, categoriaNome) VALUES 
(1, 'Collana Infinity', 'Eterno romanticismo: I simboli dell’amore e dell’infinito sono fusi insieme in questa elegante collana G&G Jewelry. Un cuore in cristallo bianco è intrecciato con un simbolo dell’infinito placcato nella tonalità oro rosa in questo adorabile gioiello, una rappresentazione visiva della promessa di amore eterno. Un’idea regalo perfetta per la persona che ami.', 50, 130, 'f', 'assets/img/collana1.jpg', 'collane'),
(2, 'Collana Mesmera', 'Scegli la pura eleganza di questa sfavillante collana Mesmera. Il design luminoso è realizzato con una fila di G&G Jewelry Zirconi che catturano la luce su una montatura placcata rodio in un mix straordinario di tagli e dimensioni. L’allungamento completato da una chiusura a moschettone e da una pioggia di sorprendenti G&G Jewelry Zirconia conferiscono a questo gioiello un tocco di lusso in più. Regala questo gioiello a una persona cara che non vorrà rinunciarvi mai più.', 30, 300, 'f', 'assets/img/collana2.jpg', 'collane'),
3, 'Collana Gema', 'Questa collana cristallina è un gioiello di grande effetto che si distingue per la bellezza di ogni dettaglio. Simbolo dell’abile savoir-faire di G&G Jewelry, il design placcato rodio presenta una moltitudine di cristalli incastonati a griffe, in un mix di tagli e in diverse tonalità di blu, tutto sapientemente disposto in una forma organica ma precisa. L’effetto di insieme è di massima intensità e rappresenta il modo perfetto per infondere glamour ad una serata raffinata.', 15, 750, 'f', 'assets/img/collana3.jpg', 'collane'),
(4, 'Anello Vittore', 'Questo anello sfoggia il luminoso splendore delle pietre sfaccettate luccicanti. Elementi bianchi, a forma di goccia, disposti in un’unica fila riflettono e rifrangono meravigliosamente la luce. Le forme pure ed eleganti rendono questo anello perfetto da abbinare ad altri gioielli e per creare trendy look sovrapposti con altri anelli in coordinato. Una scelta eccellente per impreziosire gli outfit per le occasioni speciali e un’idea regalo di grande ispirazione.', 10, 160, 'f', 'assets/img/anello1.jpg', 'anelli'),
(5, 'Anello Hyperbola', 'Questo anello Hyperbola intrecciato integra più stili in un unico design meravigliosamente compatto. Le sue due fasce placcate rodio sovrapposte sono impreziosite da G&G Jewelry Zirconia trasparenti con montature a binario e a griffe. Un cristallo aggiuntivo taglio Oval è inframmezzato per un impatto aggiuntivo. Abbinalo ad un bangle coordinato per un look davvero mozzafiato.', 50, 140, 'f', 'assets/img/anello2.jpg', 'anelli'),
(6, 'Anello Cocktail Lucent', 'Questo splendente anello Lucent è realizzato per catturare il massimo della luce e procurare gioia a chi lo indossa. Magistralmente tagliato con 138 sfaccettature, questo design tempestato da cristalli combina una bellissima trasparenza con un intarsio in acciaio inossidabile. Chic e audace, questo iconico anello ottagonale offre infinite possibilità di styling per ogni look.', 50, 250, 'f', 'assets/img/anello3.jpg', 'anelli'),
(7, 'Bracciale Angelic', 'Un classico intramontabile. Il braccialetto rodiato propone una fila di Clear Crystal a taglio circolare, ognuno incorniciato dal pavé di Clear Crystal. Il gioiello si abbina perfettamente agli altri accessori della collezione Angelic.', 50, 160, 'f', 'assets/img/bracciale1.jpg', 'bracciali'),
(8, 'Bracciale Millenia', 'Trova il divertimento nella moda con questo gioioso bracciale Millenia blu, che contiene almeno il 30% di G&G Jewelry ReCreated™ crystals, i nostri cristalli più sostenibili ad oggi. La creazione è placcata rodio e presenta una vivace sfumatura di cristalli taglio Octagon. Affascinante ed espressivo, questo bracciale è perfetto per le giornate che meritano un tocco di colore vivace.', 30, 250, 'f', 'assets/img/bracciale2.jpg', 'bracciali'),
(9, 'Bracciale Mesmera', 'Questo bracciale Mesmera sorprende con la bellezza di ogni suo dettaglio. Incastonato in una placcatura rodio, l’elegante gioiello presenta una serie di cristalli con montatura a griffe in un’audace varietà di tagli. Un’ulteriore pietra è disposta all’estremità dell’allungamento pendente per un tocco di lusso extra. Un modo davvero radioso per esprimere il tuo senso dello stile.', 25, 200, 'f', 'assets/img/bracciale3.jpg', 'bracciali'),
(10, 'Orecchini a lobo Matrix', 'Questi straordinari orecchini a buco sono una fresca interpretazione del classico orecchino con perla. Ciascuna creazione placcata rodio presenta un’unica Crystal Pearl G&G Jewelry bianca e una G&G Jewelry Zirconia taglio Round, collegate tra loro per dare vita a un insieme lussuoso e lucente. Indossali con un pendente Matrix coordinato per amplificare la radiosità.', 40, 80, 'f', 'assets/img/orecchini1.jpg', 'orecchini'),
(11, 'Orecchini a lobo Constella', 'Preparati a risplendere con questi incantevoli orecchini a lobo della linea Constella. Questo irresistibile paio, che esibisce pietre trasparenti illuminate dalla squisita tecnica pavé G&G Jewelry, è rifinito da una montatura placcata rodio. Instant wonder per ogni giorno.', 10, 100, 'f', 'assets/img/orecchini2.jpg', 'orecchini'),
(12, 'Orecchini pendenti Mesmera', 'Per un massimo di radiosità, scegli questi orecchini della famiglia Mesmera che catturano la luce. Lo sfavillante design di ogni gioiello sfoggia audaci cristalli con montatura a griffe, incastonati in una struttura placcata rodio e ogni pietra è realizzata in una forma diversa. Un regalo semplice ma elegante che ravviverà ogni occasione speciale.', 60, 130, 'f', 'assets/img/orecchini3.jpg', 'orecchini');
*/
-- Insert values into 'ordine' table
-- Insert values into 'ordine' table
/*INSERT INTO orders (id, data, costoTotale, utenteEmail) VALUES
(1, '2023-01-10', 560.00, 'mario@gmail.com'),  -- Collana Infinity + Collana Mesmera + Orecchini pendenti Mesmera
(2, '2023-02-15', 1600.00, 'luigi@gmail.com'),  -- 2 * Collana Gema + Orecchini a lobo Constella
(3, '2023-03-20', 240.00, 'musano@gmail.com'),  -- Anello Vittore + Orecchini a lobo Matrix
(4, '2023-04-25', 340.00, 'ivano@gmail.com'),  -- Anello Hyperbola + Bracciale Mesmera
(5, '2023-05-30', 660.00, 'mario@gmail.com');   -- Anello Cocktail Lucent + Bracciale Angelic + Bracciale Millenia
*/
/*
-- Insert values into 'inserimento' table
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
*/
-- Insert values into 'metodoDiPagamento' table
INSERT INTO payment_method (type, iban, cardNumber, userEmail) VALUES
('CARD', NULL, '1234-5678-9123-4567', 'mario@gmail.com'),
('CARD', NULL, '9876-5432-1098-7654', 'luigi@gmail.com'),
("CARD", NULL, "1234-1234-1234-1234", "musano@gmail.com"),
("CARD", NULL, "7777-7777-7777-7777", "ivano@gmail.com");