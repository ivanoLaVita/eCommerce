DROP DATABASE IF EXISTS LaVitaJewelry;
CREATE DATABASE LaVitaJewelry;
USE LaVitaJewelry;

-- USERS
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id         INT            NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100)   NOT NULL,
    username   VARCHAR(50)    NOT NULL,
    password   VARCHAR(64)    NOT NULL,
    firstName  VARCHAR(50)    NOT NULL,
    lastName   VARCHAR(50)    DEFAULT NULL,
    admin      TINYINT(1)     NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (email),
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
    gender       ENUM('m','f')   NOT NULL,
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
    userId        INT            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- PAYMENT METHOD
DROP TABLE IF EXISTS payment_method;
CREATE TABLE payment_method (
    id           INT                  NOT NULL AUTO_INCREMENT,
    type         ENUM('CARD','IBAN')  NOT NULL,
    iban         CHAR(27)             DEFAULT NULL,
    cardNumber   VARCHAR(19)          DEFAULT NULL,
    userId       INT                  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ORDERS
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id         INT          NOT NULL AUTO_INCREMENT,
    date       DATE         NOT NULL,
    totalCost  DOUBLE       NOT NULL,
    userId     INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ORDER_ITEM
DROP TABLE IF EXISTS order_item;
CREATE TABLE order_item (
    id         INT           NOT NULL AUTO_INCREMENT,
    productId  INT           NOT NULL,
    orderId    INT           NOT NULL,
    quantity   INT           NOT NULL,
    image      VARCHAR(1000) DEFAULT NULL,
    name       VARCHAR(100)  NOT NULL,
    price      DOUBLE        NOT NULL,
    PRIMARY KEY (id, orderId),
    FOREIGN KEY (orderId) REFERENCES orders(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);
