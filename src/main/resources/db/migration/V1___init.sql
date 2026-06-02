CREATE TABLE categories
(
    id              smallserial primary key,
    category_name   varchar(100) not null,
    model_year      varchar(10)  not null,
    number_of_seats smallint     not null,
    image           text,
    constraint category_unq unique (category_name, model_year)
);

INSERT  INTO categories(category_name, model_year,number_of_seats)
values ('Coaster',2010,25);


