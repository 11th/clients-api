-- liquibase formatted sql

-- changeSet 11th:1
CREATE TABLE IF NOT EXISTS clients(
    id BIGSERIAL PRIMARY KEY NOT NUll
);