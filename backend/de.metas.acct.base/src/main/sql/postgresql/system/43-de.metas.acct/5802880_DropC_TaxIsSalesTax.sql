/*From ID Server*/

-- Drop C_Tax.IsSalesTax — the US/CA non-deductible-sales-tax flag that has no semantic
-- meaning for EU/DACH VAT customers (every active C_Tax row in the metasfresh customer
-- base has IsSalesTax='N'). After this migration, the AP tax-line posting always uses
-- T_Credit_Acct (deductible input tax); there is no longer a branch to T_Expense_Acct via the flag.
--
-- T_Expense_Acct on C_Tax_Acct is repurposed as a per-tax-rate override of the product's
-- P_Expense_Acct, symmetric to how T_Revenue_Acct overrides P_Revenue_Acct.
--
-- *** GATING: only ship after the cross-customer audit confirms zero live IsSalesTax='Y' ***
-- *** usage. If any customer has IsSalesTax='Y' on an active tax rate with booked       ***
-- *** invoices, this migration is destructive — investigate before running.             ***

-- ============================================================================
-- BACKUPS — full-table snapshots into the backup schema. Restorable via the
-- backup.backup_tables index. Drop these once the migration is confirmed clean.
-- The C_Tax backup is the critical one (it preserves the IsSalesTax column we
-- are about to physically drop); the AD_* backups protect against an unintended
-- delete blast radius from the FK chain.
-- ============================================================================
SELECT backup_table('AD_Element',      '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_Element_Trl',  '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_Element_Link', '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_Column',       '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_Column_Trl',   '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_Field',        '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_Field_Trl',    '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('AD_UI_Element',   '_pre_drop_C_Tax_IsSalesTax');
SELECT backup_table('C_Tax',           '_pre_drop_C_Tax_IsSalesTax');

-- ============================================================================
-- DELETES (ordered to satisfy FK constraints)
--
-- All cleanup is anchored on AD_Column_ID=14528 (the column being dropped).
-- Standard AD_Field rows that the subqueries will sweep:
--
--   AD_Field_ID | Window_ID | Window           | Tab_ID | Tab
--   ------------+-----------+------------------+--------+-------------
--   12489       | 137       | Tax Rate         | 174    | Tax
--   54487       | 53022     | Tax Rate Parent  | 53078  | Tax Children
--   54514       | 53022     | Tax Rate Parent  | 53079  | Tax Parent
--
-- Plus any AD_Field rows that customer repos added via a custom window
-- (AD_Window.Overrides_Window_ID = 137 or 53022) referencing AD_Column_ID=14528
-- — all swept by the same subquery. A hardcoded-AD_Field_ID list would miss
-- those and block the AD_Column DELETE later.
-- ============================================================================

-- WebUI layout (catches standard + any custom-window AD_UI_Element rows)
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- Element-link rows (WebUI labels-picker relations)
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- AD_Field translations
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- Per-field context-menu entries (usually 0 rows; defensive)
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- AD_UI_ElementField (usually 0 rows; defensive)
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- User-customised field definitions (usually 0 rows; defensive)
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- User-saved sort preferences (usually 0 rows; defensive)
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN
    (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 14528)
;

-- AD_Field rows (sweeps standard fields 12489/54487/54514 plus any custom-window copies)
DELETE FROM AD_Field WHERE AD_Column_ID = 14528
;

-- Grid renumber: Swing-client auto-renumbers SeqNoGrid (but not SeqNo) on field delete
-- to close the gap left by the deleted row. SQL DELETEs don't, so close the gaps here
-- per row, following the Swing-export style (explicit AD_Field_ID + new value). Form
-- SeqNo is left as-is (matching Swing behaviour).
--
-- Tab 174 (Tax / Window 137 Tax Rate) — IsSalesTax was at SeqNoGrid=90
UPDATE AD_Field SET SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=973
;
UPDATE AD_Field SET SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.001','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=8195
;
UPDATE AD_Field SET SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.002','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=3076
;
UPDATE AD_Field SET SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=2871
;
UPDATE AD_Field SET SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.004','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54440
;
UPDATE AD_Field SET SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.005','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=974
;
UPDATE AD_Field SET SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.006','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=976
;
UPDATE AD_Field SET SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.007','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=56409
;
UPDATE AD_Field SET SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=645189
;
UPDATE AD_Field SET SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.009','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=646823
;
UPDATE AD_Field SET SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.010','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=646824
;
UPDATE AD_Field SET SeqNoGrid=220,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.011','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=646825
;
UPDATE AD_Field SET SeqNoGrid=230,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.012','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=649715
;

-- Tab 53078 (Tax Children / Window 53022 Tax Rate Parent) — IsSalesTax was at SeqNoGrid=120
UPDATE AD_Field SET SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.020','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54488
;
UPDATE AD_Field SET SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.021','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54489
;
UPDATE AD_Field SET SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54490
;
UPDATE AD_Field SET SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.023','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54491
;
UPDATE AD_Field SET SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.024','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54492
;
UPDATE AD_Field SET SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54493
;
UPDATE AD_Field SET SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.026','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54494
;
UPDATE AD_Field SET SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.027','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54495
;
UPDATE AD_Field SET SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54496
;
UPDATE AD_Field SET SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.029','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54497
;

-- Tab 53079 (Tax Parent / Window 53022 Tax Rate Parent) — IsSalesTax was at SeqNoGrid=120
UPDATE AD_Field SET SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.040','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54515
;
UPDATE AD_Field SET SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54516
;
UPDATE AD_Field SET SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.042','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54517
;
UPDATE AD_Field SET SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.043','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54518
;
UPDATE AD_Field SET SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.044','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54519
;
UPDATE AD_Field SET SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-05-18 16:30:00.045','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=54520
;

-- AD_Column translations
DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 14528
;

-- AD_Column row
DELETE FROM AD_Column WHERE AD_Column_ID = 14528
;

-- AD_Element translations (element is referenced only by the column being deleted)
DELETE FROM AD_Element_Trl WHERE AD_Element_ID = 2870
;

-- AD_Element row
DELETE FROM AD_Element WHERE AD_Element_ID = 2870
;

-- DDL: drop the physical column (via db_alter_table so dependent views, if any, are handled).
-- IF EXISTS makes the migration idempotent against partial-prior-run replays.
SELECT db_alter_table('C_Tax', 'ALTER TABLE C_Tax DROP COLUMN IF EXISTS IsSalesTax;')
;
