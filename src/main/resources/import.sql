INSERT INTO tb_user (name, email, password) VALUES ('Felipe Mandri', 'felipe@gmail.com', '$2a$10$71KMqEk340X6pjfU6uY2h.8ly5pR7d6eAp2sY4uP2gRxRJfNM89P.');
INSERT INTO tb_user (name, email, password) VALUES ('João Felix', 'joao@gmail.com', '$2a$10$71KMqEk340X6pjfU6uY2h.8ly5pR7d6eAp2sY4uP2gRxRJfNM89P.');

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);