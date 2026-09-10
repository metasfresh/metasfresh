-- M_Delivery_Planning.C_UOM_ID becomes mandatory, resolving an inconsistency inside the table itself.
--
-- All five quantity columns are already mandatory in BOTH layers - AD_Column.IsMandatory='Y' and physically
-- NOT NULL, four of them defaulting to 0:
--     QtyOrdered, PlannedLoadedQuantity, ActualLoadQty, PlannedDischargeQuantity, ActualDischargeQuantity
-- while C_UOM_ID was AD-optional AND physically nullable with no default. So the table guaranteed a row
-- always has five quantities but permitted them to have no UNIT - a number with no scale, which no reader
-- can interpret. Every consumer that turns those columns into a Quantity needs the UOM, so the column was
-- mandatory in fact and optional only on paper.
--
-- Safe to enforce: all 6224 rows on the deep_tundra stack already carry a C_UOM_ID (0 null or 0-valued), so
-- SET NOT NULL validates without rewriting data, and no backfill is needed.
--
-- No backup_table: this alters only the column's nullability and its dictionary flag - no row is modified.

UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-09-11 00:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585130
;

SELECT db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ALTER COLUMN C_UOM_ID SET NOT NULL')
;
