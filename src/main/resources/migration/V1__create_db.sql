DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS planets;
DROP TABLE IF EXISTS clients;

CREATE TABLE clients(
 id INTEGER AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(200) NOT NULL,
 CONSTRAINT cli_name_min_length CHECK (char_length(name) >= 3));

CREATE TABLE planets(
 id CHAR(10) NOT NULL PRIMARY KEY,
 name VARCHAR(500) NOT NULL,
 CONSTRAINT planet_id_capital_only CHECK (UPPER(id) = id),
 CONSTRAINT planet_name_min_length CHECK (char_length(name) >= 1));

CREATE TABLE tickets(
 id INTEGER AUTO_INCREMENT PRIMARY KEY,
 created_at TIMESTAMP NOT NULL,
 client_id INTEGER NOT NULL,
 from_planet_id CHAR(10) NOT NULL,
 to_planet_id CHAR(10) NOT NULL,
 FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE,
 FOREIGN KEY(from_planet_id) REFERENCES planets(id) ON DELETE CASCADE,
 FOREIGN KEY(to_planet_id) REFERENCES planets(id) ON DELETE CASCADE);