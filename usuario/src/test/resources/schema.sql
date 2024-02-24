CREATE TABLE usuario (
id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
username            VARCHAR(45) NOT NULL
);

CREATE TABLE seguido
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id  BIGINT NOT NULL,
    seguidos_id BIGINT NOT NULL,

    CONSTRAINT fk_usuario_seguido FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
