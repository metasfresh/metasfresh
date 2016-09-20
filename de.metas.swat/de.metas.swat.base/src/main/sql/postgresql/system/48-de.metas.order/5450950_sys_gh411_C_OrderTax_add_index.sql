
DROP INDEX IF EXISTS c_ordertax_c_order_id;
CREATE INDEX c_ordertax_c_order_id
   ON c_ordertax (c_order_id ASC NULLS LAST);
