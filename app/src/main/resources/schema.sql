-- Tabela de experiências
CREATE TABLE experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255) NOT NULL DEFAULT 'N/A', -- Valor padrão
    years_of_experience INT NOT NULL,
    primary_technology VARCHAR(255) NOT NULL,
    secondary_technology VARCHAR(255) NULL
);

-- Tabela de usuários
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(15),
    linked_in_profile VARCHAR(255), -- Movido para a tabela de usuários
    experience_id BIGINT,
    FOREIGN KEY (experience_id) REFERENCES experiences(id)
);
