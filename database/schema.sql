-- Script de criação do banco de dados
CREATE DATABASE IF NOT EXISTS parking_db;
USE parking_db;

-- Tabela de Vagas
CREATE TABLE IF NOT EXISTS vagas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    status ENUM('LIVRE', 'OCUPADA') DEFAULT 'LIVRE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Ocupações (Histórico de qual carro está em qual vaga)
CREATE TABLE IF NOT EXISTS ocupacoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    vaga_id INT NOT NULL,
    placa_carro VARCHAR(20) NOT NULL,
    hora_entrada DATETIME NOT NULL,
    hora_saida DATETIME,
    valor_pago DECIMAL(10, 2),
    FOREIGN KEY (vaga_id) REFERENCES vagas(id)
);

-- Inserir 10 vagas de exemplo
INSERT INTO vagas (numero) VALUES
(1), (2), (3), (4), (5),
(6), (7), (8), (9), (10);
