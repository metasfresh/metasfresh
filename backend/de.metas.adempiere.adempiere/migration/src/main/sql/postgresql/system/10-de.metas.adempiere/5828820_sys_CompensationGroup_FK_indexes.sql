-- Compensation groups: indexes on the foreign keys used to navigate between orders, order compensation groups,
-- contracts and compensation group schemas, so that zooming / filtering / related-documents lookups and FK checks
-- on delete do not seq-scan the tables.
-- (C_Order_CompensationGroup.C_Order_ID is already indexed by c_order_compensationgroup_c_order_id.)

CREATE INDEX IF NOT EXISTS c_order_compensationgroup_c_flatrate_term_id
    ON C_Order_CompensationGroup (C_Flatrate_Term_ID)
;

CREATE INDEX IF NOT EXISTS c_order_compensationgroup_c_compensationgroup_schema_id
    ON C_Order_CompensationGroup (C_CompensationGroup_Schema_ID)
;

-- Most order lines are not in a compensation group, so index only the lines that are.
CREATE INDEX IF NOT EXISTS c_orderline_c_order_compensationgroup_id
    ON C_OrderLine (C_Order_CompensationGroup_ID)
    WHERE C_Order_CompensationGroup_ID IS NOT NULL
;
