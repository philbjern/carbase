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