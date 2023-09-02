-- liquibase formatted sql

-- changeSet 11th:1
create table if not exists clients
(
    client_id   bigserial primary key not null,
    first_name  varchar(50) not null,
    last_name   varchar(70)
);

create table if not exists contacts
(
    contact_id      bigserial primary key not null,
    contact_type    varchar(10) not null,
    client_id       bigint not null,
    value           varchar(20) not null,
    foreign key (client_id) references clients (client_id) on delete cascade
);