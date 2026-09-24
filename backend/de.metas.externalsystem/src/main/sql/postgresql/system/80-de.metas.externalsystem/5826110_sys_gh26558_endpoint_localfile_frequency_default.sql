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
-- The default covers record CREATION only (AD_Column.DefaultValue never re-fires on an existing row), so
-- it does not help an endpoint that reaches LOCAL_FILE by a later transport switch: switching away from
-- LOCAL_FILE clears Frequency to 0, and the default cannot put it back. That round trip is closed on the
-- application side, by re-defaulting the frequency whenever an endpoint switches back to the local-file
-- transport. The companion migration script additionally gates the column as mandatory under
-- TransportType=LOCAL_FILE, which stops the operator clearing the field out by hand.
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
