-- Defaults ExternalSystem_Endpoint.Frequency (AD_Column 593642) to 60000 (milliseconds), matching the
-- SFTP transport's SftpPollingIntervalMs default (AD_Column 592967, script
-- 5814450_move_sftp_polling_fields_to_endpoint.sql).
--
-- Without a default, a LOCAL_FILE endpoint document can be saved with Frequency empty: the window's
-- validation accepts it (no MandatoryLogic on the column yet -- see 5826120 below), and a frequency <= 0
-- is read back as no frequency at all, so the child config's camel parameters carry no polling interval,
-- and the local-file route falls back to its own transport-wide default of 60000 rather than refusing it.
-- Defaulting the column to 60000 closes that gap for the common case (operator leaves the field blank),
-- so the route's fallback and the column's default agree.
--
-- AD_Column.DefaultValue fires on record CREATION only -- it never re-fires on an existing row, so it
-- cannot serve an endpoint that reaches LOCAL_FILE by a later transport switch. The endpoint interceptor
-- (de.metas.externalsystem.endpoint.interceptor.ExternalSystem_Endpoint) applies this same value on that
-- path: whenever a switch hides Frequency it resets the column to its default, so an endpoint that comes
-- back to LOCAL_FILE presents 60000 exactly as a newly created one does. The value below is therefore read
-- by two mechanisms, and the interceptor carries a verbatim copy of it that
-- externalSystemEndpointDisplayLogic.feature holds against this column.
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
