-- Drops the order-side surface on C_PaySelectionLine (C_Order_ID + C_OrderPaySchedule_ID +
-- their AD metadata + the UI field). The shared AD_Element rows (558, 584056) are used
-- elsewhere and are not touched.

-- Defensive backup before any destructive change to a non-AD table.
SELECT backup_table('c_payselectionline', '_drop_order_columns')
;

-- =============================================================================
-- 1. Delete the UI surface for C_PaySelectionLine.C_Order_ID
-- =============================================================================

-- AD_UI_Element pointing at the C_Order_ID field on tab 353 (C_PaySelectionLine)
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637869
;

-- AD_Field translations for the C_Order_ID field
DELETE FROM AD_Field_Trl WHERE AD_Field_ID=754994
;

-- AD_Field for C_Order_ID on tab 353
DELETE FROM AD_Field WHERE AD_Field_ID=754994
;

-- =============================================================================
-- 2. Delete AD_Column metadata for both columns
-- =============================================================================

-- C_Order_ID column translations + row
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=591353
;
DELETE FROM AD_Column WHERE AD_Column_ID=591353
;

-- C_OrderPaySchedule_ID column translations + row
-- Note: 591370 is data-only — no AD_Field / AD_UI_Element was ever created for it.
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=591370
;
DELETE FROM AD_Column WHERE AD_Column_ID=591370
;

-- =============================================================================
-- 3. Drop the physical columns. Dependent FK constraints (COrder_CPaySelectionLine,
-- COrderPaySchedule_CPaySelectionLine) are dropped automatically by PostgreSQL when
-- their column disappears.
-- =============================================================================

/* DDL */ SELECT public.db_alter_table('C_PaySelectionLine','ALTER TABLE C_PaySelectionLine DROP COLUMN IF EXISTS C_Order_ID')
;

/* DDL */ SELECT public.db_alter_table('C_PaySelectionLine','ALTER TABLE C_PaySelectionLine DROP COLUMN IF EXISTS C_OrderPaySchedule_ID')
;
