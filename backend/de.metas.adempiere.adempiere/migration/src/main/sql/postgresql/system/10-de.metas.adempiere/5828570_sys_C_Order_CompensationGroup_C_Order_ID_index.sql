-- Contract compensation groups: index on the order foreign key of the order compensation group table,
-- so that reading an order's (contract-created) compensation groups on each order completion does not seq-scan the table.

CREATE INDEX IF NOT EXISTS c_order_compensationgroup_c_order_id
    ON C_Order_CompensationGroup (C_Order_ID)
;
