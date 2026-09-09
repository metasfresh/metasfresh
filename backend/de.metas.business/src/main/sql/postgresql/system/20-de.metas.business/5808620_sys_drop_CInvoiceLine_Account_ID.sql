-- Drop unused orphan column C_InvoiceLine.Account_ID (AD_Column_ID=590530).
-- Zero non-null rows confirmed. Not wired to any business logic on any branch.
-- Shared AD_Element 148 (Account_ID) is preserved; only the C_InvoiceLine-specific
-- AD_Column 590530 and its two AD_Fields (757928, 750254) are removed.
-- Dependent sweep: fact_acct_transactions_view and fresh_statistics_kg_week_view reference
-- fact_acct.Account_ID (not c_invoiceline.Account_ID) — no action needed.
-- No EXP_FormatLine, no ColumnSQL, no AD_Val_Rule references to this column.
-- F01010.4 Invoice Accounting Overrides

-- ============================================================
-- 1. FK-chain cleanup for both AD_Fields (anchored by AD_Column_ID=590530)
-- ============================================================

-- 1a. AD_UI_Element (AD_Field_ID FK)
DELETE FROM AD_UI_Element
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1b. AD_UI_Element (Labels_Selector_Field_ID FK) -- usually 0 rows
DELETE FROM AD_UI_Element
WHERE Labels_Selector_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1c. AD_Element_Link (AD_Field_ID FK)
DELETE FROM AD_Element_Link
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1d. AD_Field_Trl
DELETE FROM AD_Field_Trl
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1e. AD_Field_ContextMenu
DELETE FROM AD_Field_ContextMenu
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1f. AD_UI_ElementField
DELETE FROM AD_UI_ElementField
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1g. AD_UserDef_Field
DELETE FROM AD_UserDef_Field
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1h. AD_User_SortPref_Line
DELETE FROM AD_User_SortPref_Line
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 590530)
;

-- 1i. AD_Field rows themselves
DELETE FROM AD_Field
WHERE AD_Column_ID = 590530
;

-- ============================================================
-- 2. SeqNoGrid renumber (Swing-parity, explicit per-row values)
-- ============================================================
-- Tab 548568: field 757928 was at SeqNoGrid=110 (last field) — no renumber needed.
-- Tab 291: field 750254 was at SeqNoGrid=110; shift fields 780497 and 780501 down.
UPDATE AD_Field
SET SeqNoGrid = 110,
    Updated = TO_TIMESTAMP('2026-06-18 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 780497
;

UPDATE AD_Field
SET SeqNoGrid = 120,
    Updated = TO_TIMESTAMP('2026-06-18 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID = 780501
;

-- ============================================================
-- 3. Delete AD_Column 590530 and its translations
-- ============================================================

DELETE FROM AD_Column_Trl
WHERE AD_Column_ID = 590530
;

DELETE FROM AD_Column
WHERE AD_Column_ID = 590530
;

-- ============================================================
-- 4. Drop the physical column (db_alter_table handles dependent views)
-- ============================================================

-- Defensive backup before the destructive DROP COLUMN (mandatory for non-AD business tables,
-- even though this column is verified 0-of-17694 non-null here — another instance might carry a stray row).
SELECT backup_table('c_invoiceline', '_pre_drop_account_id')
;

SELECT public.db_alter_table('C_InvoiceLine', 'ALTER TABLE C_InvoiceLine DROP COLUMN IF EXISTS Account_ID')
;
