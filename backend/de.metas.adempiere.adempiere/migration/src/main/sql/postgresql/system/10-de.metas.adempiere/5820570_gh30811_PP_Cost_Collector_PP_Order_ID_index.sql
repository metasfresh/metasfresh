-- PP_Cost_Collector has only its primary key and a partial index for unposted rows, so a "collectors of
-- this order" lookup scans the whole table. The Kostensammler tab of the production-order window
-- (AD_Tab 53053) does that lookup on every order open, and the post-calculation action once per run.
-- PP_Order_ID is highly selective, so a plain btree turns those scans into index lookups.

CREATE INDEX IF NOT EXISTS PP_Cost_Collector_PP_Order_ID ON PP_Cost_Collector (PP_Order_ID);
