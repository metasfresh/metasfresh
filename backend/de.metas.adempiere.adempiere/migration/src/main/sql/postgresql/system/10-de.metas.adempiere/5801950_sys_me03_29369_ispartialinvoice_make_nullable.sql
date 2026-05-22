-- 2026-05-09
-- C_DocType.IsPartialInvoice + C_Invoice.IsPartialInvoice -> make NULLABLE 3-state
-- iter-3 https://github.com/metasfresh/me03/issues/29369
--
-- Original migrations 5800200 + 5800210 added these as CHAR(1) NOT NULL DEFAULT 'Y'.
-- The 'Y' default polluted all invoices/doctypes that never intended to express Partial intent,
-- breaking the closePartiallyInvoiced_InvoiceCandidates semantic. The redesigned model has 3 states:
--   * NULL = NA (no user intent -- legacy qty-based close applies, preserves pre-iter-3 behavior)
--   * 'Y'  = explicit Partial (more invoices coming, skip auto-close)
--   * 'N'  = explicit Final (cap, qty-based close applies)
-- See Java enum de.metas.invoice.IsPartialInvoice for the typed mapping.

-- 1. Drop the existing CHECK constraints first (recreated below to allow NULL).
--    The default Postgres constraint name is <table>_<column>_check.
ALTER TABLE C_DocType DROP CONSTRAINT IF EXISTS c_doctype_ispartialinvoice_check;
ALTER TABLE C_Invoice DROP CONSTRAINT IF EXISTS c_invoice_ispartialinvoice_check;

-- 2. Make the columns nullable and drop the 'Y' default via t_alter_column
--    (the columns already physically exist, so t_alter_column is the right tool here).
--    The t_alter_column trigger interprets the literal string 'null' (4 chars) for nullclause
--    as "drop not null" and the literal string 'null' for defaultclause as "drop default";
--    passing SQL NULL would be a no-op (the trigger only reacts when the value IS NOT NULL).
INSERT INTO t_alter_column values('C_DocType','IsPartialInvoice','CHAR(1)','null','null');
INSERT INTO t_alter_column values('C_Invoice','IsPartialInvoice','CHAR(1)','null','null');

-- 3. Migrate existing 'Y' rows (the legacy default) to NULL = NA.
--    Rows explicitly set to 'N' (e.g. Endabrechnung doctype, see 5801420) are preserved.
UPDATE C_DocType SET IsPartialInvoice = NULL, Updated = TO_TIMESTAMP('2026-05-09 00:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 99 WHERE IsPartialInvoice = 'Y';
UPDATE C_Invoice SET IsPartialInvoice = NULL, Updated = TO_TIMESTAMP('2026-05-09 00:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 99 WHERE IsPartialInvoice = 'Y';

-- 4. Recreate the CHECK constraints, now allowing NULL explicitly.
ALTER TABLE C_DocType ADD CONSTRAINT c_doctype_ispartialinvoice_check CHECK (IsPartialInvoice IS NULL OR IsPartialInvoice IN ('Y','N'));
ALTER TABLE C_Invoice ADD CONSTRAINT c_invoice_ispartialinvoice_check CHECK (IsPartialInvoice IS NULL OR IsPartialInvoice IN ('Y','N'));

-- 5. AD_Column metadata: align IsMandatory + DefaultValue with the new 3-state model.
UPDATE AD_Column
   SET IsMandatory  = 'N',
       DefaultValue = NULL,
       Updated      = TO_TIMESTAMP('2026-05-09 00:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 100
 WHERE ColumnName   = 'IsPartialInvoice'
   AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('C_DocType','C_Invoice'));
