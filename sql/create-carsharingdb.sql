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