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