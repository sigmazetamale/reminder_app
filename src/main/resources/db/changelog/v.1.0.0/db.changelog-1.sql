--liquibase formatted sql

--changeset init:1

CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE
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

INSERT INTO users (email)
VALUES ('user1@example.com'),
       ('user2@example.com'),
       ('user123@example.com'),
       ('user2132213@example.com'),
       ('user3123123@example.com'),
       ('user123123123123@example.com'),
       ('user3333333333333333@example.com');

INSERT INTO reminder (title, description, remind, user_id)
VALUES ('Заголовок напоминания123123 1', 'Описание напоминания 11231', '2024-05-17 10:00:00', 1),
       ('Заголовок напоминани123я 2', 'Описание напомина123123ния 2', '2021-03-18 15:30:00', 2),
       ('Заголовок напоминания123123 1', 'Описание напоминания 11231', '2020-05-17 10:00:00', 4),
       ('Заголовок напоминани123я 2', 'Описание напомина123123ния 2', '2020-01-18 15:30:00', 3),
       ('Заголовок напоминания123123 1', 'Описание напоминания 11231', '2024-05-17 11:00:00', 1),
       ('Заголовок напоминани123я 2', 'Описание напомина123123ния 2', '2022-03-18 18:30:00', 5),
       ('Заголовок напоминани1easdя 3', 'Описание наasdasdпоминания 3', '2023-02-19 10:01:00', 6);


