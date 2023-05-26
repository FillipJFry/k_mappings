DELETE FROM tickets;
DELETE FROM planets;
DELETE FROM clients;

INSERT INTO clients(name) VALUES
 ('Vasya'), ('Fry'), ('Дід Панас'), ('John Glenn'), ('Richard Stallman'),
 ('Steve Wozniak'), ('Neil Armstrong'), ('Michael Collins'), ('Buzz Aldrin'), ('Elon Musk');

SET @vasya = (SELECT id FROM clients WHERE name = 'Vasya');
SET @fry = (SELECT id FROM clients WHERE name = 'Fry');
SET @didP = (SELECT id FROM clients WHERE name = 'Дід Панас');
SET @glenn = (SELECT id FROM clients WHERE name = 'John Glenn');
SET @stallman = (SELECT id FROM clients WHERE name = 'Richard Stallman');
SET @woz = (SELECT id FROM clients WHERE name = 'Steve Wozniak');
SET @neil = (SELECT id FROM clients WHERE name = 'Neil Armstrong');
SET @buzz = (SELECT id FROM clients WHERE name = 'Buzz Aldrin');
SET @michael = (SELECT id FROM clients WHERE name = 'Michael Collins');
SET @musk = (SELECT id FROM clients WHERE name = 'Elon Musk');

INSERT INTO planets VALUES ('ERTH', 'Earth'),
 ('MARS', 'Mars'), ('MOON', 'Moon'), ('CERS', 'Ceres'),
 ('TTN', 'Titan'), ('EUR', 'Europe'), ('VNS', 'Venus');

INSERT INTO tickets(created_at, client_id, from_planet_id, to_planet_id) VALUES
 (NOW(), @vasya, 'ERTH', 'EUR'),
 (NOW(), @fry, 'MARS', 'CERS'),
 (NOW(), @didP, 'VNS', 'ERTH'),
 (NOW(), @glenn, 'ERTH', 'ERTH'),
 (NOW(), @stallman, 'TTN', 'MOON'),
 (NOW(), @woz, 'MOON', 'MARS'),
 (NOW(), @neil, 'ERTH', 'MOON'),
 (NOW(), @buzz, 'ERTH', 'MOON'),
 (NOW(), @michael, 'ERTH', 'MOON'),
 (NOW(), @musk, 'ERTH', 'MARS');