-- M_ShipmentSchedule: speed up opening the "Lieferdisposition" WebUI window.
--
-- The window's default ordering is the effective delivery date
-- COALESCE(DeliveryDate_Override, DeliveryDate) DESC. With no matching index the
-- WebUI view-selection build (INSERT INTO T_WEBUI_ViewSelection ... row_number()
-- OVER (ORDER BY COALESCE(DeliveryDate_Override, DeliveryDate) DESC ...) ... LIMIT)
-- has to seq-scan and sort the entire table on every open. On a large installation
-- (2.2M+ rows) that is several seconds per open.
--
-- This functional index matches the sort exactly (DESC NULLS LAST, then the key as a
-- stable tie-breaker), so the planner reads the first page pre-sorted from the index
-- and stops early at the LIMIT instead of scanning + sorting the whole table.
-- Measured on a 2.26M-row instance: ~7.4 s cold -> ~48 ms; buffers 255k -> 7.5k.
--
-- NULLS LAST is mandatory: the query orders DESC NULLS LAST, and a plain DESC index
-- (which defaults to NULLS FIRST) does not match the requested order and is ignored.
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_DeliveryDate_Eff
    ON M_ShipmentSchedule (COALESCE(DeliveryDate_Override, DeliveryDate) DESC NULLS LAST, M_ShipmentSchedule_ID ASC);
