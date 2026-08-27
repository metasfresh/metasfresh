-- PP_Cost_Collector carries only its primary key and a partial index for unposted rows, so a "collectors of
-- this order" lookup otherwise scans the whole table. The virtual column PP_Order.CostDifference does one such
-- lookup per row it renders, which makes a grid over completed orders scan that table once per line.
--
-- PP_Order_ID is highly selective - an order has a handful of collectors out of tens of thousands - so a plain
-- btree turns each of those scans into a two-row index lookup.

CREATE INDEX IF NOT EXISTS PP_Cost_Collector_PP_Order_ID ON PP_Cost_Collector (PP_Order_ID);
