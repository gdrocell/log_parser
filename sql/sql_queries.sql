USE `log_parser`;

/**
 * SQL Part 1 :
 * Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
 */
 
SELECT `ip`, count(`ip`) as `numRequests` FROM `log_entries`
WHERE `requestDate` >= TIMESTAMP('2017-01-01 13:00:00') AND `requestDate` < TIMESTAMP('2017-01-01 14:00:00')
GROUP BY `ip` 
HAVING `numRequests` >= 100;

/**
 * SQL Part 2:
 * Write MySQL query to find requests made by a given IP.
 */
 
 SELECT * FROM `log_entries`
 WHERE `ip` = '192.168.228.188';
 