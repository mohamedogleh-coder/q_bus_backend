CREATE TABLE stations
(
    id           smallserial primary key,
    station_name varchar(100)   not null unique,
    station_cost numeric(12, 2) not null check ( station_cost > 0 )
);
