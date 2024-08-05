--liquibase formatted sql

--changeset init:1

CREATE TABLE IF NOT EXISTS users
(
    id      BIGSERIAL PRIMARY KEY,
    email   VARCHAR(255) NOT NULL UNIQUE,
    chat_id BIGINT
);

CREATE TABLE IF NOT EXISTS reminder
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(4096),
    remind      TIMESTAMP    NOT NULL,
    user_id     BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);