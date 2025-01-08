INSERT INTO experiences (role, years_of_experience, primary_technology, secondary_technology)
VALUES
('Desenvolvedor Senior Java', 5, 'JAVA', 'SPRING_BOOT'),
('Engenheira de Software', 8, 'PYTHON', 'DJANGO'),
('Desenvolvedor Frontend', 3, 'JAVASCRIPT', NULL); -- `role` é obrigatório

-- Insere dados na tabela de usuários
INSERT INTO users (name, email, phone, linked_in_profile, experience_id)
VALUES
('John Doe', 'john.doe@example.com', '1234567890', 'https://www.linkedin.com/in/johndoe/', 1),
('Jane Smith', 'jane.smith@example.com', '0987654321', 'https://www.linkedin.com/in/janesmith/', 2),
('Carlos Eduardo', 'carlos.eduardo@example.com', '5551234567', 'https://www.linkedin.com/in/carloseduardo/', 3);
