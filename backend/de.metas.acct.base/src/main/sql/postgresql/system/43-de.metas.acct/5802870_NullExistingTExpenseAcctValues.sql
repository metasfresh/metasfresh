/*From ID Server*/

-- Clear out auto-seeded T_Expense_Acct values so the column becomes "deliberately-set-only"
-- across all installations (matches T_Revenue_Acct's existing behaviour).
--
-- Before this migration:
--   * Every C_Tax_Acct row inherits T_Expense_Acct from C_AcctSchema_Default at creation time.
--   * In practice this means 100% of active rows carry a value, but it is not a customer-chosen
--     override — it is the schema default. Any value here is noise.
--
-- After this migration:
--   * Both source (C_AcctSchema_Default) and targets (C_Tax_Acct) are NULL.
--   * A non-NULL T_Expense_Acct value on a C_Tax_Acct row is now a deliberate customer override
--     that triggers the AccountProvider.getProductAccount(P_Expense_Acct, ...) override branch.
--
-- See also:
--   * 5802860_MakeTExpenseAcctNullable.sql — must run before this migration.
--   * https://github.com/metasfresh/mf15/issues/4137 — separate cleanup of similarly-rotten
--     T_Revenue_Acct seed values on 4 Swiss tax rates.

-- ============================================================================
-- BACKUPS — full-table snapshots into the backup schema. Restorable via the
-- backup.backup_tables index. Drop these once the migration is confirmed clean.
-- ============================================================================
SELECT backup_table('C_Tax_Acct',          '_pre_null_T_Expense_Acct');
SELECT backup_table('C_AcctSchema_Default','_pre_null_T_Expense_Acct');

-- ============================================================================
-- NULL the values
-- ============================================================================
UPDATE C_Tax_Acct
   SET T_Expense_Acct = NULL,
       Updated = TO_TIMESTAMP('2026-05-18 15:30:00.000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy = 99
 WHERE T_Expense_Acct IS NOT NULL
;

UPDATE C_AcctSchema_Default
   SET T_Expense_Acct = NULL,
       Updated = TO_TIMESTAMP('2026-05-18 15:30:00.001','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy = 99
 WHERE T_Expense_Acct IS NOT NULL
;
