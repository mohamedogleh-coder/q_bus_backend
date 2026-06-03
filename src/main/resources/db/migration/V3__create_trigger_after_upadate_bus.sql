CREATE OR REPLACE FUNCTION after_update_bus_create_seats_fn()
    RETURNS TRIGGER AS $$
DECLARE
v_old_num_of_seats smallint;
    v_new_num_of_seats smallint;
    i smallint;
BEGIN

IF (NEW.category_id <> OLD.category_id) THEN

SELECT number_of_seats
INTO v_old_num_of_seats
FROM categories WHERE id = OLD.category_id;

SELECT number_of_seats
INTO v_new_num_of_seats
FROM categories WHERE id = NEW.category_id;

IF (v_old_num_of_seats = v_new_num_of_seats) THEN
            RETURN NEW;

ELSIF (v_old_num_of_seats > v_new_num_of_seats) THEN
    DELETE FROM bus_seats
    WHERE bus_id = NEW.id AND seat_number > v_new_num_of_seats;

ELSE
    FOR i IN (v_old_num_of_seats + 1)..v_new_num_of_seats LOOP
    INSERT INTO bus_seats (seat_number, bus_id)VALUES (i, NEW.id);
END LOOP;
END IF;

END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER tr_after_update_bus
    AFTER UPDATE ON buses
                        FOR EACH ROW
                        EXECUTE FUNCTION after_update_bus_create_seats_fn();