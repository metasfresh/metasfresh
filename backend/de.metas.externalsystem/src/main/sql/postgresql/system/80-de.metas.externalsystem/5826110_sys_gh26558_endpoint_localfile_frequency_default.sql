-- Defaults ExternalSystem_Endpoint.Frequency (AD_Column 593642) to 60000 (milliseconds), matching the
-- SFTP transport's SftpPollingIntervalMs default (AD_Column 592967, script
-- 5814450_move_sftp_polling_fields_to_endpoint.sql).
--
-- Without a default, a LOCAL_FILE endpoint document can be saved with Frequency empty: the window's
-- validation accepts it (no MandatoryLogic on the column), but the persisted NULL is later mapped to
-- "not configured" and the LOCAL_FILE import route refuses to start polling, requiring a positive
-- frequency value. The operator sees no error anywhere and the endpoint never polls. Defaulting the
-- column to 60000 closes that gap for the common case (operator leaves the field blank).
--
-- MandatoryLogic is intentionally NOT added on top of the default here, for symmetry with the SFTP
-- sibling column (592967), which ships default-only as well: the default already makes the broken
-- (unset) state unreachable through the window's normal save path, and requiring the operator to type a
-- value they would otherwise get for free adds friction without closing any additional gap.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826110 (this script)

-- ============================================================
-- Frequency (AD_Column 593642): physical column default + AD_Column.DefaultValue = 60000
-- ============================================================
INSERT INTO t_alter_column VALUES ('externalsystem_endpoint', 'Frequency', 'INTEGER', NULL, '60000');

UPDATE AD_Column
SET DefaultValue = '60000',
    Updated      = TO_TIMESTAMP('2026-09-24 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Column_ID = 593642;
