-- #############################################################################
-- Migration: bring the parent config window's scripted-import child tab (548472,
-- window 541024 "Externes System Konfiguration") to parity with the dedicated
-- scripted-import window's root tab (548473, window 541962), and retire the
-- legacy free-text EndpointName field from the UI on both tabs.
--
-- Finding: AD_Field rows for the endpoint FK + the 3 SFTP fields already existed
-- on tab 548472 (774936-774939, table ExternalSystem_Config_ScriptedImportConversion)
-- but were never paired with an AD_UI_Element/AD_UI_ElementGroup on that tab -- so
-- the WebUI silently never rendered them there (fields exist, no UI element -> no
-- widget). This script only adds the missing UI-layer wiring; no new AD_Field rows
-- are needed.
-- #############################################################################

-- ============================================================================
-- 1. Dedicated AD_Element for the endpoint field label ("Endpunkt" / "Endpoint")
--    NOT reusing the shared AD_Element_ID=584191 ("ExternalSystem Endpoint"):
--    that element also backs ExternalSystem_Config_ScriptedExportConversion and
--    ExternalSystem_Endpoint themselves (export tab / endpoint-definition window),
--    which are out of scope here. Fork instead of mutating a shared element.
-- ============================================================================

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (585107 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-15 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-15 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	NULL, 'Endpunkt', 'Endpunkt', NULL, 'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585107
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name = 'Endpunkt', PrintName = 'Endpunkt', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-15 16:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585107 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name = 'Endpoint', PrintName = 'Endpoint', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-15 16:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585107 AND AD_Language = 'en_US';

-- ============================================================================
-- 2. AD_UI_ElementGroup "sftp" on tab 548472, left column 548531 -- mirrors the
--    existing group 554997 on tab 548473's left column 548533.
-- ============================================================================

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (555513 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-15 16:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-15 16:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
	548531, 'sftp', 20, NULL);

-- ============================================================================
-- 3. AD_UI_Elements on tab 548472 for the already-existing AD_Field rows
--    774936 (ExternalSystem_Endpoint_ID), 774937 (SftpPollingIntervalMs),
--    774938 (SftpProcessedDirectory), 774939 (SftpErrorDirectory) -- mirrors
--    648581-648584 on tab 548473 exactly (same SeqNo / grid flags).
-- ============================================================================

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652667 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-15 16:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-15 16:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
	548472, 555513, 774936, 'Endpunkt', 10, 'N', 'Y', 'Y', 0, 'N', 'F');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652668 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-15 16:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-15 16:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
	548472, 555513, 774937, 'SFTP Abfrageintervall (ms)', 20, 'N', 'Y', 'N', 0, 'N', 'F');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652669 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-15 16:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-15 16:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
	548472, 555513, 774938, 'SFTP Verzeichnis (verarbeitet)', 30, 'N', 'Y', 'N', 0, 'N', 'F');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652670 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-15 16:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-15 16:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
	548472, 555513, 774939, 'SFTP Verzeichnis (Fehler)', 40, 'N', 'Y', 'N', 0, 'N', 'F');

-- Cosmetic: align the cached Name on the existing 548473 endpoint UI element too.
UPDATE AD_UI_Element
SET Name = 'Endpunkt', Updated = TO_TIMESTAMP('2026-07-15 16:00:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 648581;

-- ============================================================================
-- 4. Field-level label override -- point BOTH tabs' endpoint AD_Field at the
--    dedicated element (585107 -- fork, not shared-element mutation).
-- ============================================================================

UPDATE AD_Field
SET AD_Name_ID = 585107, Name = 'Endpunkt',
    Updated = TO_TIMESTAMP('2026-07-15 16:00:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (774932, 774936);

UPDATE AD_Field_Trl
SET Name = 'Endpunkt', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-15 16:00:45', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (774932, 774936) AND AD_Language IN ('de_DE', 'de_CH', 'fr_CH', 'it_CH');

UPDATE AD_Field_Trl
SET Name = 'Endpoint', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-15 16:00:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (774932, 774936) AND AD_Language = 'en_US';

-- ============================================================================
-- 5. Make the endpoint FK mandatory at the AD level -- this is the single AD_Column
--    shared by both tabs' AD_Field rows (neither has a field-level IsMandatory
--    override), so flipping it here makes the field mandatory in the WebUI on BOTH
--    548472 and 548473.
--    NOTE: the DB-level NOT NULL constraint is deliberately NOT added here. Migration
--    scripts run in numeric-prefix order, and a later migration backfills endpoints
--    for any pre-existing config (Value <- legacy EndpointName) BEFORE enforcing
--    NOT NULL. Adding the constraint here would abort the deploy on any instance that
--    still has a config with a null endpoint FK (the backfill has not run yet).
-- ============================================================================

UPDATE AD_Column
SET IsMandatory = 'Y', Updated = TO_TIMESTAMP('2026-07-15 16:00:55', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592250;

-- ============================================================================
-- 6. Retire the legacy free-text EndpointName field from the UI on BOTH import
--    tabs. The AD_Column and AD_Field rows are kept (column dropped in a later
--    task); only the UI placement is removed -- no AD_UI_ElementField satellites
--    reference these elements.
-- ============================================================================

DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID IN (637873, 637882);
