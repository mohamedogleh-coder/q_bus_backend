CREATE TABLE buses
(
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    palate_number varchar(10)                                                  not null,
    steering_side varchar(10) check (steering_side in ('Midigle', 'Bidixle') ) not null,
    category_id   smallint                                                     not null references categories (id) on delete cascade,
    image_url     text,
    constraint plate_number_unq unique (palate_number)
);

CREATE TABLE bus_seats
(
    id          smallserial PRIMARY KEY,
    seat_number smallint NOT NULL,
    damaged     boolean DEFAULT FALSE,
    bus_id      uuid     NOT NULL REFERENCES buses (id) ON DELETE CASCADE, -- KANNA WAA INUU UUID NOQDO!
    CONSTRAINT seat_unq UNIQUE (seat_number, bus_id)
);

 CREATE
OR REPLACE FUNCTION after_register_bus_create_seats_fn()
    RETURNS TRIGGER AS $$
DECLARE
v_num_of_seats smallint;
    i
smallint;
BEGIN
SELECT number_of_seats
INTO v_num_of_seats
FROM categories
WHERE id = NEW.category_id;

FOR i IN 1..v_num_of_seats LOOP
        INSERT INTO bus_seats (seat_number, bus_id)
        VALUES (i, NEW.id);
END LOOP;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER tr_after_insert_bus
    AFTER INSERT
    ON buses
    FOR EACH ROW
    EXECUTE FUNCTION after_register_bus_create_seats_fn();