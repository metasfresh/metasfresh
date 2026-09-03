-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: per-doctype default for IsPartialInvoice. Default 'Y' (Partial) per AC #11.
-- AC #11: default 'Y' is the safe choice — failing to mark Final strands prepay (visible),
-- failing to mark Partial silently consumes prepay (worse).
SELECT db_alter_table(
    'C_DocType',
    'ALTER TABLE C_DocType ADD COLUMN IsPartialInvoice CHAR(1) DEFAULT ''Y'' NOT NULL CHECK (IsPartialInvoice IN (''Y'', ''N''))'
);
