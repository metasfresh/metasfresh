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
-- Safe to enforce, on structure rather than on one environment's row count: the column already carries the
-- foreign key cuom_mdeliveryplanning -> c_uom(c_uom_id), and no C_UOM with id 0 exists, so the "set but
-- zero" value that would satisfy NOT NULL while still being unusable cannot occur anywhere the table does.
-- That leaves only genuinely NULL rows, which SET NOT NULL rejects loudly at deploy rather than silently.
-- Corroborated on the deep_tundra stack: 6224 rows, none null or zero - but the FK is the argument.
--
-- No backup_table: this alters only the column's nullability and its dictionary flag - no row is modified.

UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-09-11 00:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585130
;

SELECT db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ALTER COLUMN C_UOM_ID SET NOT NULL')
;
