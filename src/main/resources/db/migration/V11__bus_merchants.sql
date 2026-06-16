CREATE TABLE providers
(
    id               smallserial primary key,
    provider_name    varchar(20) not null unique,
    provider_service varchar(20) not null
);


INSERT INTO providers(provider_name, provider_service)
VALUES ('Telesom', 'Zaad Service'),
       ('Somtel', 'eDahab'),
       ('Soltelco', 'eCash');



CREATE TABLE bus_merchants
(
    id              smallserial primary key,
    provider_id     smallint    not null references providers (id) on delete restrict,
    merchant_number varchar(20) not null,
    bus_id          uuid        not null references buses (id) on delete cascade,
    constraint bus_merchant_unq unique (bus_id, provider_id, merchant_number)
);