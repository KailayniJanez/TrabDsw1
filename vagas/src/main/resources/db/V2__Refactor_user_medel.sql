-- 1. Criar nova estrutura de tabelas
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dtype VARCHAR(31) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL
);

-- 2. Migrar dados de admin
INSERT INTO usuarios (id, dtype, email, senha, nome)
SELECT id, 'UsuarioAdmin', email, senha, 'Administrador' FROM admin;

-- 3. Migrar dados de empresa
INSERT INTO usuarios (id, dtype, email, senha, nome)
SELECT id, 'Empresa', email, senha, nome FROM empresa;

-- 4. Criar tabela empresa com herança
CREATE TABLE empresa (
    id BIGINT PRIMARY KEY,
    cnpj VARCHAR(14),
    descricao TEXT,
    cidade VARCHAR(100),
    FOREIGN KEY (id) REFERENCES usuarios(id)
);

-- 5. Migrar dados específicos de empresa
INSERT INTO empresa (id, cnpj, descricao, cidade)
SELECT id, cnpj, descricao, cidade FROM old_empresa;

-- 6. Migrar dados de profissional
INSERT INTO usuarios (id, dtype, email, senha, nome)
SELECT id, 'Profissional', email, senha, nome FROM profissional;

-- 7. Criar tabela profissional com herança
CREATE TABLE profissional (
    id BIGINT PRIMARY KEY,
    cpf VARCHAR(11),
    telefone VARCHAR(15),
    sexo VARCHAR(20),
    data_nascimento DATE,
    FOREIGN KEY (id) REFERENCES usuarios(id)
);

-- 8. Migrar dados específicos de profissional
INSERT INTO profissional (id, cpf, telefone, sexo, data_nascimento)
SELECT id, cpf, telefone, sexo, data_nascimento FROM old_profissional;

-- 9. Remover tabelas antigas (após confirmar migração)
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS old_empresa;
DROP TABLE IF EXISTS old_profissional;