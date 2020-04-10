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
(1, 'selfsigned', '02.02.2022', true, 'issuer issuer','issuer_aliasss', 'AA123', '01.01.2020', 'ROOT'),
(2, 'alias2', '02.05.2022', true, 'issuer issuer','issuer_aliass2', 'AA124', '01.01.2020', 'ENDUSER'),
(3, 'alias3', '22.02.2022', true, 'issuer issuer','issuer_aliass3', 'AA127', '01.01.2020', 'ENDUSER'),
(4, 'alias6', '22.02.2022', true, 'issuer issuer','issuer_aliass6', 'AA556', '01.01.2020', 'ENDUSER'),
(5, 'alias7', '22.02.2022', true, 'issuer issuer','issuer_aliass7', 'AA176', '01.01.2020', 'ENDUSER'),
(6, 'alias4', '12.02.2022', true, 'issuer issuer','issuer_aliass4', 'AA348', '01.01.2020', 'INTERMEDIATE'),
(7, 'alias5', '04.04.2021', true, 'issuer issuer','issuer_aliass5', 'AA885', '01.01.2020', 'INTERMEDIATE'),
(8, 'alias8', '04.04.2021', true, 'issuer issuer','issuer_aliass8', 'AA965', '01.01.2020', 'INTERMEDIATE'),
(9, 'alias9', '04.04.2021', true, 'issuer issuer','issuer_aliass9', 'AA163', '01.01.2020', 'INTERMEDIATE');
