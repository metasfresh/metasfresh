DROP TABLE IF EXISTS purchase_order_highestprice_per_day_mv
;

CREATE TABLE purchase_order_highestprice_per_day_mv AS
SELECT *
FROM purchase_order_highestprice_per_day_v
;

CREATE INDEX ON purchase_order_highestprice_per_day_mv (m_product_id)
;

CREATE INDEX ON purchase_order_highestprice_per_day_mv (date)
;

