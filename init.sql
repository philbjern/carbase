DROP SCHEMA IF EXISTS `cardb`;

CREATE SCHEMA `cardb`;
USE `cardb`;

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `make` varchar(20) DEFAULT NULL,
  `model` varchar(20) DEFAULT NULL,
  `production_year` varchar(4) DEFAULT NULL,
  `engine_volume` varchar(4) DEFAULT NULL,
  `fuel_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

DROP SCHEMA IF EXISTS `persondb`;

CREATE SCHEMA `persondb`;
USE `persondb`;

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `registered_on` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

DROP SCHEMA IF EXISTS `carsharingdb`;

CREATE SCHEMA `carsharingdb`;
USE `carsharingdb`;

DROP TABLE IF EXISTS `carsharing`;

CREATE TABLE `carsharing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL,
  `person_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
)

DROP USER 'carbaseadmin'@'%';

CREATE USER 'carbaseadmin'@'%' IDENTIFIED BY 'carbaseadmin';
GRANT ALL PRIVILEGES ON persondb.* TO 'carbaseadmin'@'%';
GRANT ALL PRIVILEGES ON cardb.* TO 'carbaseadmin'@'%';
GRANT ALL PRIVILEGES ON carsharingdb.* TO 'carbaseadmin'@'%';
GRANT ALL PRIVILEGES ON carbase.* TO 'carbaseadmin'@'%';
