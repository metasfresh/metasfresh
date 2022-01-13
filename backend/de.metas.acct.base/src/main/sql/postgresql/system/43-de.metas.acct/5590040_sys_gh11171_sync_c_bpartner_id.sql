UPDATE m_warehouse
SET c_bpartner_id = loc.c_bpartner_id
FROM c_bpartner_location loc
WHERE m_warehouse.c_bpartner_location_id = loc.c_bpartner_location_id
;

COMMIT;

ALTER TABLE c_bpartner_location
    ADD UNIQUE (c_bpartner_id, c_bpartner_location_id)
;

ALTER TABLE m_warehouse
    ADD CONSTRAINT bpartner_location_warehouse
        FOREIGN KEY (c_bpartner_id, c_bpartner_location_id) REFERENCES c_bpartner_location (c_bpartner_id, c_bpartner_location_id)
            DEFERRABLE INITIALLY DEFERRED
;
