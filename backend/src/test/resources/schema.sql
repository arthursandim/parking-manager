-- Schema SQL para testes H2
-- As tabelas são criadas automaticamente por este arquivo quando o perfil 'test' está ativo

CREATE TABLE IF NOT EXISTS vagas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    status VARCHAR(20) DEFAULT 'LIVRE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ocupacoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    vaga_id INT NOT NULL,
    placa_carro VARCHAR(20) NOT NULL,
    hora_entrada DATETIME NOT NULL,
    hora_saida DATETIME,
    valor_pago DECIMAL(10, 2),
    FOREIGN KEY (vaga_id) REFERENCES vagas(id)
);
