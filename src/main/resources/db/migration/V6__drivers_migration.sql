CREATE TABLE public.bus_driver
(
    id           SMALLSERIAL PRIMARY KEY,
    driver_phone VARCHAR(20) NOT NULL,
    bus_id       UUID        NOT NULL REFERENCES public.buses (id) ON DELETE CASCADE,
    is_owner     BOOLEAN DEFAULT TRUE,
    is_default   BOOLEAN DEFAULT false,
    CONSTRAINT bus_driver_unq UNIQUE (bus_id, driver_phone)
);

CREATE UNIQUE INDEX unq_bus_single_owner
    ON bus_driver (bus_id) WHERE (is_owner = TRUE);

CREATE UNIQUE INDEX unq_bus_single_default
    ON bus_driver (bus_id) WHERE (is_default = TRUE);

