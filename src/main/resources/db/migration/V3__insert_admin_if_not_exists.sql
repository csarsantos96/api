-- V3__insert_admin_if_not_exists.sql

INSERT INTO usuarios (nome, email, senha)
SELECT 'Admin', 'admin@forumhub.com', '$2a$10$bbbmMmkd.WdxrXULoFW0gotWE1poTrb1qLpZGo0MoRdQtPfP2Cq'
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE email = 'admin@forumhub.com'
);
