CREATE TABLE bookings
(
    id           serial primary key,
    bus_id       uuid not null references buses (id) on delete cascade,
    station_id   smallint not null references stations (id) on delete restrict,
    booking_date date default current_date,
    status       varchar(10) check ( status in ('active', 'waiting', 'closed')) not null
);

CREATE UNIQUE INDEX no_double_active_or_waiting_unq
    on bookings (bus_id) where status in ('active','waiting');

CREATE TABLE booking_seats
(
    id           bigserial primary key,
    booking_id   int            not null references bookings (id) on delete cascade,
    seat_number  smallint       not null,
    amount_paid  numeric(12, 2) not null check ( amount_paid > 0 ),
    booking_time timestamp default current_timestamp,
    sent_from    varchar(20)    not null,
    sent_to      varchar(20)    not null
);



CREATE
OR REPLACE FUNCTION verify_station_cost()
RETURNS TRIGGER AS $$
DECLARE
required_cost NUMERIC(12, 2);
BEGIN

SELECT s.cost
INTO required_cost
FROM bookings b
         JOIN stations s ON b.station_id = s.id
WHERE b.id = NEW.booking_id;

IF
required_cost IS NULL THEN
        RAISE EXCEPTION 'Booking ID % or associated station cost not found.', NEW.booking_id;
END IF;

     IF
NEW.amount_paid <> required_cost THEN
        RAISE EXCEPTION 'Payment rejected! The required station cost is %, but you provided %.',
            required_cost, NEW.amount_paid;
END IF;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;



CREATE TRIGGER check_amount_paid_before_save
    BEFORE INSERT OR
UPDATE ON booking_seats
    FOR EACH ROW
    EXECUTE FUNCTION verify_station_cost();