-- Task Q14 (delivery planning quantities): M_ShippingPackage.ActualLoadQty and .ActualDischargeQuantity
-- stop being physical columns written at generation time (a planned->actual copy, the "frozen figure"
-- defect) and become derived (ColumnSQL, see the AD_Column migration 5822240) that mirror the planning's
-- own ActualLoadQty/ActualDischargeQuantity through the M_Delivery_Planning_Alloc allocation.
--
-- The two views that read these columns directly were repointed at the planning (dp, already joined in
-- both) in migrations 5822210 / 5822220 - this script must run AFTER those, or the DROP COLUMN fails on
-- the dependent views.
--
-- Dependency sweep run before this script (views/functions/val-rules/ColumnSQL/EXP_FormatLine referencing
-- actualloadqty/actualdischargequantity on M_ShippingPackage): the two views above, both already
-- repointed at the planning; db_alter_table's own dependent-view walk additionally found two views that
-- depend on M_ShippingPackage TRANSITIVELY through those two (report.fresh_c_order_sscc_label_report,
-- public.historical_m_inout_json_v) - db_alter_table drops and recreates all dependents (deepest first)
-- from their CURRENT definition automatically, so no separate handling is needed for them: by the time
-- this script runs, the two direct views already expose the same column names from the new source.

SELECT backup_table('m_shippingpackage', '_31789_Q14_drop_actual_columns');

SELECT db_alter_table('M_ShippingPackage', 'ALTER TABLE M_ShippingPackage DROP COLUMN ActualLoadQty');
SELECT db_alter_table('M_ShippingPackage', 'ALTER TABLE M_ShippingPackage DROP COLUMN ActualDischargeQuantity');
