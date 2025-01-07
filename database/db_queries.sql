DROP DATABASE IF EXISTS ID_card;

CREATE DATABASE ID_card;

USE ID_card;

CREATE TABLE citizen
(
    rank          int(14) PRIMARY KEY AUTO_INCREMENT,
    ID            varchar(20),
    firstName     VARCHAR(45),
    lastName      VARCHAR(45),
    birthDate     DATE,
    age           int(3),
    gender        VARCHAR(20),
    religion      VARCHAR(20),
    MaritalState  VARCHAR(20),
    husbandName   VARCHAR(45),
    occupation    VARCHAR(40),
    state         VARCHAR(40),
    address       VARCHAR(70),
    creatingDate  DATE,
    exDate        DATE
);
