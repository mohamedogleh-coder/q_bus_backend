DROP VIEW IF EXISTS get_scanned_seat_information_view;


CREATE VIEW get_scanned_seat_information_view as
SELECT b.id as booking_id,
       bus.id as bus_id,
       s.station_name,
       s.station_cost,
       bus.palate_number,
       c.category_name,
       c.model_year,
       json_agg(json_build_object('merchant_id',bm.id,'merchant_number', bm.merchant_number, 'provider_service', p.provider_service,
                                  'provider_name', p.provider_name)) as merchants
FROM bookings b
         join buses bus on bus.id = b.bus_id
         join bus_merchants bm on bus.id = bm.bus_id
         join categories c on c.id = bus.category_id
         join providers p on p.id = bm.provider_id
         join stations s on s.id = b.station_id
WHERE b.status = 'active'
group by b.id,bus.id, s.station_name, s.station_cost, bus.palate_number, c.category_name, c.model_year;
