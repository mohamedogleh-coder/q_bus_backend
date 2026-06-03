DROP TRIGGER IF EXISTS tr_after_insert_bus ON buses;
DROP FUNCTION IF EXISTS after_register_bus_create_seats_fn();

CREATE
OR REPLACE FUNCTION after_insert_bus_fn()
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

INSERT INTO print_documents(id) VALUES (NEW.id);

RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER tr_after_insert_bus
    AFTER INSERT
    ON buses
    FOR EACH ROW
    EXECUTE FUNCTION after_insert_bus_fn();