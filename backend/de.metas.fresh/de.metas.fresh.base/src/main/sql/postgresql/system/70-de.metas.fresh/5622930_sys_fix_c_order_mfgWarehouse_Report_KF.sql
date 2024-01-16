
ALTER TABLE c_order_mfgwarehouse_report
    DROP CONSTRAINT corder_cordermfgwarehouserepor
;

ALTER TABLE c_order_mfgwarehouse_report
    ADD CONSTRAINT corder_cordermfgwarehouserepor
        FOREIGN KEY (c_order_id) REFERENCES c_order
            DEFERRABLE INITIALLY DEFERRED
;
