-- PP_Cost_Collector carries only its primary key and a partial index for unposted rows, so a "collectors of
-- this order" lookup otherwise scans the whole table. The Kostensammler tab of the production-order window
-- (AD_Tab 53053) does exactly that lookup every time an order is opened, and the cost-collector creation in the
-- post-calculation action does it again per run.
--
-- PP_Order_ID is highly selective - an order has a handful of collectors out of tens of thousands - so a plain
-- btree turns each of those scans into a two-row index lookup.
--
-- Measured on a customer-flavored local stack while PP_Order.CostDifference still joined PP_Cost_Collector:
-- ~1.4 s/page before, sub-second after. That summand has since been dropped from the column (see 5820790),
-- so the column itself no longer touches this table; the index is kept for the lookups named above.

CREATE INDEX IF NOT EXISTS PP_Cost_Collector_PP_Order_ID ON PP_Cost_Collector (PP_Order_ID);
