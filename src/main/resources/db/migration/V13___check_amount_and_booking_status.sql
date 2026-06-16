DROP TRIGGER IF EXISTS check_amount_paid_before_save ON booking_seats;
DROP FUNCTION IF EXISTS verify_station_cost();

CREATE OR REPLACE FUNCTION verify_station_cost()
RETURNS TRIGGER AS $$
DECLARE
required_cost NUMERIC(12, 2);
    current_status VARCHAR(10);
BEGIN
 SELECT s.station_cost, b.status
INTO required_cost, current_status
FROM bookings b
         JOIN stations s ON b.station_id = s.id
WHERE b.id = NEW.booking_id;

 IF required_cost IS NULL THEN
        RAISE EXCEPTION 'Booking ID % or associated station cost not found.', NEW.booking_id;
END IF;

     IF current_status = 'waiting' THEN
        RAISE EXCEPTION 'This bus is still in the waiting queue and is not active yet.';
    ELSIF current_status = 'closed' THEN
        RAISE EXCEPTION 'Bus is not ready for booking right now.';
END IF;

     IF NEW.amount_paid <> required_cost THEN
        RAISE EXCEPTION 'Payment rejected! The required station cost is %, but you provided %.', required_cost, NEW.amount_paid;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_amount_paid_before_save
    BEFORE INSERT OR UPDATE ON booking_seats
                         FOR EACH ROW
                         EXECUTE FUNCTION verify_station_cost();