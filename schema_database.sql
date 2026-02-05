CREATE DATABASE IF NOT EXISTS rubrica_db;
USE rubrica_db;

CREATE TABLE IF NOT EXISTS persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    indirizzo VARCHAR(100),
    telefono VARCHAR(20),
    eta INT
);

CREATE TABLE IF NOT EXISTS utente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);

-- Insert a default user (User: admin, Pass: admin)
INSERT IGNORE INTO utente (username, password) VALUES ('admin', 'admin');