CREATE DATABASE IF NOT EXISTS `log_parser`;

USE `log_parser`;

CREATE TABLE IF NOT EXISTS `log_entries` (
	`logEntryId` INT(11) NOT NULL AUTO_INCREMENT,
    `requestDate` TIMESTAMP,
    `ip` VARCHAR(16),
    `httpRequest` VARCHAR(128),
    `status` INT(11),
    `userAgent` VARCHAR(512),
    PRIMARY KEY (`logEntryId`)
);

CREATE TABLE IF NOT EXISTS `blocked` (
	`ip` VARCHAR(16) NOT NULL,
    `comment` VARCHAR(1024),
    PRIMARY KEY (`ip`)
);