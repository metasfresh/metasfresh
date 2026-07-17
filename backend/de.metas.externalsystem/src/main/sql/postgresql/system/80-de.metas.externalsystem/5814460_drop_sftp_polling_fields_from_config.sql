-- #############################################################################
-- Migration B (companion to 5814450): remove the SFTP polling/directory settings
-- from the scripted-import CONFIG now that they live on the endpoint (5814450).
--
-- Removes the AD_UI_Element + AD_Field rows on BOTH config tabs (548472 parent-window
-- child tab, 548473 dedicated-window root tab) and drops the 3 physical columns from
-- ExternalSystem_Config_ScriptedImportConversion.
--
-- The AD_Elements 584678/584679/584680 are NOT deleted — 5814450 repointed them to
-- the new endpoint columns (they are keyed by columnname), so they remain in use.
--
-- No backfill: verified 0 configs hold any non-default value in these 3 fields.
-- #############################################################################

-- 1. Remove the UI placement on both config tabs.
--    Tab 548472: UI elements 652668/652669/652670 (fields 774937/774938/774939)
--    Tab 548473: UI elements 648582/648583/648584 (fields 774933/774934/774935)
DELETE FROM AD_UI_ElementField WHERE AD_UI_Element_ID IN (652668, 652669, 652670, 648582, 648583, 648584);
DELETE FROM AD_UI_Element      WHERE AD_UI_Element_ID IN (652668, 652669, 652670, 648582, 648583, 648584);

-- 2. Remove the AD_Field rows on both tabs.
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (774937, 774938, 774939, 774933, 774934, 774935);
DELETE FROM AD_Field     WHERE AD_Field_ID IN (774937, 774938, 774939, 774933, 774934, 774935);

-- 3. Remove the AD_Column rows (config table). The shared AD_Elements stay.
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (592251, 592252, 592253);
DELETE FROM AD_Column     WHERE AD_Column_ID IN (592251, 592252, 592253);

-- 4. Drop the physical columns.
SELECT backup_table('ExternalSystem_Config_ScriptedImportConversion', '_5814460');
SELECT public.db_alter_table('ExternalSystem_Config_ScriptedImportConversion',
	'ALTER TABLE public.ExternalSystem_Config_ScriptedImportConversion
	   DROP COLUMN IF EXISTS SftpPollingIntervalMs,
	   DROP COLUMN IF EXISTS SftpProcessedDirectory,
	   DROP COLUMN IF EXISTS SftpErrorDirectory');
