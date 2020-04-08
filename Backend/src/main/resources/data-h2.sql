SET NAMES 'utf8';
--
-- Delete data from the table 'user_authority'
--
TRUNCATE TABLE user_authority;
--
-- Delete data from the table 'user'
--
DELETE FROM user;
--
-- Delete data from the table 'authority'
--
DELETE FROM authority;
--
-- Inserting data into table authority
--
INSERT INTO authority(id, type) VALUES
(1, 'ROLE_ADMIN');
--
-- Inserting data into table user
--
INSERT INTO user(id, email, first_name, last_name, password) VALUES
(1, 'Dickens@example.com', 'Alfredia', 'Howard', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra'),
(2, 'AundreaAllan@example.com', 'Malorie', 'Forney', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra'),
(3, 'WaldoLopes@example.com', 'Norman', 'Acker', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra'),
(4, 'AbigailBrunner@example.com', 'Adolph', 'Crockett', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra'),
(5, 'Aleida_Law@example.com', 'Abe', 'Steadman', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');
--
-- Inserting data into table user_authority
--
INSERT INTO user_authority(user_id, authority_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1);
