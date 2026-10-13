-- me03#29557: Change M_Product.DepositType from CHAR(3) to VARCHAR(3)
--
-- The original migration (58023600_sys_me03_29557_M_Product_DepositType.sql) created the
-- column as CHAR(3). PostgreSQL space-pads CHAR(n) columns on read, so the 2-char value
-- 'RC' was stored and emitted as 'RC ' (with a trailing space). Two symptoms:
--   * Outgoing INVOIC-JSON carried "Product_DepositType": "RC "  — wrong wire format for
--     downstream EDIFACT converters expecting a clean "RC".
--   * WebUI save-after-switch (e.g. RC -> NRC) failed with the framework's optimistic-
--     concurrency check:
--       "Document's field was changed from when we last queried it ...
--        Document field initial value: RC   PO value: RC "
--     The document framework loaded the trimmed value 'RC' as the initial value but the
--     PO save handler compared against the raw CHAR-padded 'RC ' — the inequality
--     triggers a spurious "changed since query" failure on save.
--
-- Fix: switch to VARCHAR(3), then RTRIM any space-padded values that already exist in
-- production data so subsequent reads/serialisations get the clean value.

-- 1) Backup row(s) that carry the column today (defensive — usually <1k rows, but
--    cheap insurance against the RTRIM accidentally clobbering a value we didn't expect)
SELECT backup_table('m_product', '_pre_5802960_deposittype_varchar');

-- 2) Switch the physical column type. t_alter_column handles view dependencies + the
--    existing CHECK constraint stays attached (the constraint matches IN ('NRC','RC')
--    by value, not by data type).
INSERT INTO t_alter_column VALUES ('M_Product','DepositType','VARCHAR(3)',NULL,NULL);

-- 3) Strip the trailing space the CHAR(3) padding left on 2-char values like 'RC'.
--    'NRC' is unaffected (length 3 — no padding). NULL is unaffected.
UPDATE m_product
SET DepositType = RTRIM(DepositType),
    Updated     = TO_TIMESTAMP('2026-05-18 17:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 99 -- Support — flagged as a migration-script change, not business logic
WHERE DepositType IS NOT NULL
  AND DepositType <> RTRIM(DepositType);
