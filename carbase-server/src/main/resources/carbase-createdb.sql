DROP SCHEMA IF EXISTS `carbase`;

CREATE SCHEMA `carbase`;

use `carbase`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `make` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `production_year` int(11) DEFAULT NULL,
  `fuel` varchar(10) DEFAULT  NULL,
  `engine_volume` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `person_car`;

CREATE TABLE `person_car` (
  `car_id` int(11) NOT NULL,
  `person_id` int(11) NOT NULL,
  
  PRIMARY KEY (`car_id`, `person_id`),
    
  CONSTRAINT `FK_CAR` FOREIGN KEY (`car_id`) 
  REFERENCES `car` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  FOREIGN KEY (`person_id`) 
  REFERENCES `person` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
 
