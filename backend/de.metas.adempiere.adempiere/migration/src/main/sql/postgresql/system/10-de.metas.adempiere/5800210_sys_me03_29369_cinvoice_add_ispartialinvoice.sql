-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: per-invoice override for IsPartialInvoice. Default 'Y' (Partial) per AC #11.
-- Editable while DocStatus IN (DR, IP); readonly afterwards (enforced via AD_Field.ReadOnlyLogic + Java guard).
SELECT db_alter_table(
    'C_Invoice',
    'ALTER TABLE C_Invoice ADD COLUMN IsPartialInvoice CHAR(1) DEFAULT ''Y'' NOT NULL CHECK (IsPartialInvoice IN (''Y'', ''N''))'
);
