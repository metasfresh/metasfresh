DROP INDEX IF EXISTS c_order_cost_created_orderline_uq
;

CREATE UNIQUE INDEX c_order_cost_created_orderline_uq ON c_order_cost (c_order_id, created_orderline_id)
;

