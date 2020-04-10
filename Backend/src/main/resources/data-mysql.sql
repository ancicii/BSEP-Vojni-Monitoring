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
-- Delete data from the table 'issuer'
--
DELETE FROM certificate;
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



INSERT INTO certificate(id, alias, end_date, is_active, issuer_name, issueralias, serial_number, start_date, type) VALUES
(1, 'selfsigned', '2022-02-02 16:52:30', true, 'issuer issuer','selfsigned', 'AA123', '2020-01-01 16:52:30', 'ROOT'),
(2, 'aliass2', '02.02.2022', true, 'issuer issuer','selfsigned', 'AA123', '01.01.2020', 'ROOT'),
(3, 'aliass3', '02.02.2022', true, 'issuer issuer','selfsigned', 'AA123', '01.01.2020', 'ROOT'),
(4, 'aliass4', '02.02.2022', true, 'issuer issuer','aliass2', 'AA123', '01.01.2020', 'ROOT'),
(5, 'aliass5', '02.02.2022', true, 'issuer issuer','aliass2', 'AA123', '01.01.2020', 'ROOT'),
(6, 'aliass6', '02.02.2022', true, 'issuer issuer','aliass5', 'AA123', '01.01.2020', 'ROOT'),
(7, 'aliass7', '02.02.2022', true, 'issuer issuer','aliass3', 'AA123', '01.01.2020', 'ROOT'),
(8, 'aliass8', '02.02.2022', true, 'issuer issuer','aliass3', 'AA123', '01.01.2020', 'ROOT')

