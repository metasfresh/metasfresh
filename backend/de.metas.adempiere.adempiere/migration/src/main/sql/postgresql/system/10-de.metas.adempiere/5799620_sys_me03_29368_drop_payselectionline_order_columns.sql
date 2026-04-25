-- https://github.com/metasfresh/me03/issues/29368  Phase 5 / T42c-DDL
-- Reverts the order-side surface introduced by https://github.com/metasfresh/metasfresh/pull/21485
-- on C_PaySelectionLine. After Phase 5 every pay-selection line is invoice-only — the two
-- order-side columns + their AD metadata + the UI surface are unreferenced. The Java cleanup
-- already landed (commits f0f5a9cb932, 2d7a733331b, b07b0482774); this migration drops the
-- physical schema + AD metadata.
--
-- The AD_Element rows themselves (558 = C_Order_ID, 584056 = C_OrderPaySchedule_ID) are
-- shared/core elements used elsewhere in the schema and are NOT touched.
--
-- For environments that may still hold PaySelectionLineType=Order test data, run the cleanup
-- SQL published at https://github.com/metasfresh/me03/issues/29368#issuecomment-4320178387
-- BEFORE applying this migration. Production was never affected (the feature was never
-- deployed there), so the DROP COLUMN below assumes zero rows reference these columns.

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
