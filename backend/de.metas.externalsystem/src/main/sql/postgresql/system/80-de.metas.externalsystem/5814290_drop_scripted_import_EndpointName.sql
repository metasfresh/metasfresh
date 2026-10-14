-- #############################################################################
-- Migration: retire the legacy EndpointName column from the scripted-import config.
-- The endpoint (ExternalSystem_Endpoint) is now the single source of the channel
-- identity (its Value); EndpointName is evolutionary sediment.
--
-- Order (single transaction):
--   1. BACKFILL — for any existing config that still has a free-text EndpointName
--      but no endpoint FK, create an ExternalSystem_Endpoint (Value = EndpointName,
--      TransportType inferred from the config's SFTP fields) and link it, so no live
--      config is stranded when the FK becomes mandatory. No-op where none exist.
--   2. Make the endpoint FK NOT NULL at the DB level (AD-level IsMandatory was set
--      earlier; the backfill guarantees no null FKs remain).
--   3. Drop the EndpointName AD_Field / AD_Column / AD_Element and the physical column.
-- Model regen (I_/X_ExternalSystem_Config_ScriptedImportConversion) follows the apply.
-- #############################################################################

-- 1. Backfill: create+link an endpoint for each stranded config (Value=EndpointName).
--    ExternalSystem_Endpoint IDs come from its sequence (AD_Sequence 556558).
DO $backfill$
DECLARE
	r RECORD;
	v_endpoint_id INTEGER;
BEGIN
	FOR r IN
		SELECT * FROM ExternalSystem_Config_ScriptedImportConversion
		WHERE EndpointName IS NOT NULL AND ExternalSystem_Endpoint_ID IS NULL
	LOOP
		v_endpoint_id := nextidfunc(556558, 'N');
		INSERT INTO ExternalSystem_Endpoint (
			ExternalSystem_Endpoint_ID, AD_Client_ID, AD_Org_ID, IsActive,
			Created, CreatedBy, Updated, UpdatedBy,
			Value, TransportType, IsArrayFanOut)
		VALUES (
			v_endpoint_id, r.AD_Client_ID, r.AD_Org_ID, 'Y',
			now(), 100, now(), 100,
			r.EndpointName,
			CASE WHEN r.SftpPollingIntervalMs IS NOT NULL
			       OR r.SftpProcessedDirectory IS NOT NULL
			       OR r.SftpErrorDirectory IS NOT NULL
			     THEN 'SFTP' ELSE 'HTTP' END,
			'N');
		UPDATE ExternalSystem_Config_ScriptedImportConversion
		   SET ExternalSystem_Endpoint_ID = v_endpoint_id
		 WHERE ExternalSystem_Config_ScriptedImportConversion_ID = r.ExternalSystem_Config_ScriptedImportConversion_ID;
	END LOOP;
END
$backfill$;

-- 2. Make the endpoint FK NOT NULL at the DB level (backfill ensured no nulls remain).
INSERT INTO t_alter_column VALUES ('externalsystem_config_scriptedimportconversion', 'ExternalSystem_Endpoint_ID', 'INTEGER', 'NOT NULL', NULL);

-- 3. Drop the legacy EndpointName field + column + element (its AD_UI_Elements were
--    already removed earlier). AD_Field 755004 (tab 548472) + 755014 (tab 548473);
--    AD_Column 591365; AD_Element 584118 (element-specific, not shared).
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (755004, 755014);
DELETE FROM AD_Field     WHERE AD_Field_ID IN (755004, 755014);
DELETE FROM AD_Column    WHERE AD_Column_ID = 591365;
DELETE FROM AD_Element_Trl WHERE AD_Element_ID = 584118;
DELETE FROM AD_Element     WHERE AD_Element_ID = 584118;

SELECT backup_table('ExternalSystem_Config_ScriptedImportConversion', '_5814290');
SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion',
	'ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion DROP COLUMN IF EXISTS EndpointName');
