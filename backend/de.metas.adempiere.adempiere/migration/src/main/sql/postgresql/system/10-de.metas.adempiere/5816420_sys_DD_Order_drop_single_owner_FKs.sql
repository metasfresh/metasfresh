-- DD_Order replenishment — drop the single-owner FK columns from DD_Order and DD_OrderLine.
--
-- This is the point of no return for the FK-based navigation: a consolidated distribution order serves
-- several demand schedules, so it has no single owning schedule to point at. From here on, the
-- DD_OrderLine_PickingJobSchedule association is the only link between a demand schedule and the
-- distribution order(s) serving it, in both directions.
--
-- MUST run after 5816390_sys_DD_OrderLine_PickingJobSchedule_backfill.sql: that script reads these
-- column values to create the association rows for distribution orders that already exist at rollout.
-- Once the columns are gone the values cannot be recovered, and an open order without association rows
-- is invisible to the periodic drift reconciliation, which would then issue a second order for demand
-- the first one already covers.
--
-- Dropped:
--   DD_Order.M_ShipmentSchedule_ID           AD_Column 592625  (added by 5804730)
--   DD_Order.M_Picking_Job_Schedule_ID       AD_Column 592792  (added by 5807020)
--   DD_OrderLine.M_ShipmentSchedule_ID       AD_Column 592666  (added by 5804740)
--   DD_OrderLine.M_Picking_Job_Schedule_ID   AD_Column 592793  (added by 5807050)
--   AD_Field 780487 / 780488 + AD_UI_Element 651844 / 651845 — the "Lieferdisposition" field on the
--   Distributionsauftrag window header and line tabs (added by 5804750). Anchored by AD_Column_ID so any
--   further field on the same column (e.g. on an override window) is swept too.
--   FK constraints mshipmentschedule_ddorder / mshipmentschedule_ddorderline and the DEFERRABLE
--   INITIALLY DEFERRED mpickingjobschedule_ddorder / mpickingjobschedule_ddorderline, plus the four
--   partial indexes on those columns.
--
-- Pre-drop dependency sweep on a customer-faithful DB: no view, function, AD_Val_Rule, virtual
-- AD_Column.ColumnSQL or EXP_FormatLine references any of the four columns.
--
-- Re-runnable: the DDL uses IF EXISTS, and every DELETE resolves through the AD_Column rows it also
-- removes, so a second run is a no-op.

SELECT backup_table('dd_order', '_drop_single_owner_fks');
SELECT backup_table('dd_orderline', '_drop_single_owner_fks');

-- =============================================================================
-- 1. AD_Field FK chain, then the AD_Fields themselves
-- =============================================================================
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793))
;
DELETE FROM AD_Field WHERE AD_Column_ID IN (592625, 592666, 592792, 592793)
;

-- The two fields sat at the end of their tabs (SeqNo/SeqNoGrid 990 on the UI element, AD_Field.SeqNoGrid
-- unset), so removing them leaves no gap to renumber.

-- =============================================================================
-- 2. AD_Column translations, then the AD_Columns
-- =============================================================================
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (592625, 592666, 592792, 592793)
;
DELETE FROM AD_Column WHERE AD_Column_ID IN (592625, 592666, 592792, 592793)
;

-- =============================================================================
-- 3. Physical DDL — FK constraints, partial indexes, then the columns
-- =============================================================================
ALTER TABLE DD_Order     DROP CONSTRAINT IF EXISTS mshipmentschedule_ddorder;
ALTER TABLE DD_Order     DROP CONSTRAINT IF EXISTS mpickingjobschedule_ddorder;
ALTER TABLE DD_OrderLine DROP CONSTRAINT IF EXISTS mshipmentschedule_ddorderline;
ALTER TABLE DD_OrderLine DROP CONSTRAINT IF EXISTS mpickingjobschedule_ddorderline;

DROP INDEX IF EXISTS dd_order_m_shipmentschedule_idx;
DROP INDEX IF EXISTS dd_order_m_pickingjobschedule_idx;
DROP INDEX IF EXISTS dd_orderline_m_shipmentschedule_idx;
DROP INDEX IF EXISTS dd_orderline_m_pickingjobschedule_idx;

/* DDL */ SELECT public.db_alter_table('DD_Order','ALTER TABLE DD_Order DROP COLUMN IF EXISTS M_ShipmentSchedule_ID, DROP COLUMN IF EXISTS M_Picking_Job_Schedule_ID')
;
/* DDL */ SELECT public.db_alter_table('DD_OrderLine','ALTER TABLE DD_OrderLine DROP COLUMN IF EXISTS M_ShipmentSchedule_ID, DROP COLUMN IF EXISTS M_Picking_Job_Schedule_ID')
;
