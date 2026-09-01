-- #############################################################################
-- Migration A: move the SFTP polling/directory settings onto the ENDPOINT.
--
-- The three SFTP transport settings SftpPollingIntervalMs / SftpProcessedDirectory
-- / SftpErrorDirectory currently live on ExternalSystem_Config_ScriptedImportConversion,
-- but they are properties of the sFTP CONNECTION (which is the endpoint), alongside
-- SftpHost/Port/Username/RemotePath that already live on ExternalSystem_Endpoint
-- (window 541967, tab 548506). This aligns with the issue's "endpoint = single source
-- of truth" design.
--
-- This script ADDS the 3 columns + fields + UI elements to ExternalSystem_Endpoint
-- (shown when TransportType=SFTP, in the existing "sftp" element group 554996). The
-- companion script 5814460 removes them from the config windows and drops the config
-- columns. The three AD_Elements 584678/584679/584680 are REUSED (keyed by columnname)
-- — no new elements are created; when 5814460 later drops the config columns, the
-- elements remain, now backing the endpoint columns.
--
-- IDs from idserver.metas.de (2026-07-17):
--   AD_Column   592967 (SftpPollingIntervalMs), 592968 (SftpProcessedDirectory), 592969 (SftpErrorDirectory)
--   AD_Field    781746 (SftpPollingIntervalMs), 781747 (SftpProcessedDirectory), 781748 (SftpErrorDirectory)
--   AD_UI_Element 652676 / 652677 / 652678
-- #############################################################################

-- ============================================================================
-- 1. Physical columns on ExternalSystem_Endpoint
-- ============================================================================
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpPollingIntervalMs INTEGER DEFAULT 60000;
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpProcessedDirectory VARCHAR(255);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpErrorDirectory VARCHAR(255);

-- ============================================================================
-- 2. AD_Column rows (AD_Table_ID=542551 = ExternalSystem_Endpoint), reusing the
--    existing AD_Elements 584678/584679/584680.
-- ============================================================================

-- SftpPollingIntervalMs: INTEGER (ref 11), nullable, UI default 60000
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592967 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584678, 542551, 'SftpPollingIntervalMs', 'SFTP Abfrageintervall (ms)',
        'Wie oft der SFTP-Server auf neue Dateien geprüft wird, in Millisekunden (Standard: 60000 = 1 Minute)',
        0, 'de.metas.externalsystem', 11,
        'N', '60000', 'Y', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592967
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584678);

-- SftpProcessedDirectory: VARCHAR(255), nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592968 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584679, 542551, 'SftpProcessedDirectory', 'SFTP Verzeichnis (verarbeitet)',
        'Remote-Verzeichnis, in das erfolgreich verarbeitete Dateien verschoben werden',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        255, 'N', 'N', 'N',
        'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592968
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584679);

-- SftpErrorDirectory: VARCHAR(255), nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592969 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584680, 542551, 'SftpErrorDirectory', 'SFTP Verzeichnis (Fehler)',
        'Remote-Verzeichnis, in das fehlgeschlagene Dateien verschoben werden',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        255, 'N', 'N', 'N',
        'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592969
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584680);

-- ============================================================================
-- 3. AD_Field rows on the endpoint tab 548506 (shown when TransportType=SFTP)
-- ============================================================================

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (781746 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592967, 548506, 'SFTP Abfrageintervall (ms)',
        'Wie oft der SFTP-Server auf neue Dateien geprüft wird, in Millisekunden (Standard: 60000 = 1 Minute)',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 781746
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (781747 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592968, 548506, 'SFTP Verzeichnis (verarbeitet)',
        'Remote-Verzeichnis, in das erfolgreich verarbeitete Dateien verschoben werden',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 781747
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (781748 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592969, 548506, 'SFTP Verzeichnis (Fehler)',
        'Remote-Verzeichnis, in das fehlgeschlagene Dateien verschoben werden',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 781748
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- ============================================================================
-- 3b. Propagate the (already-translated) element captions/descriptions to the new
--     AD_Field_Trl + AD_Column_Trl rows. The AD_Field rows were created with the
--     German base Name, which the _Trl copy inherited for EVERY language; without
--     this the live WebUI (AD_Field_vt.name = COALESCE(f_trl, f, c_trl, c)) would
--     show German labels to en_US users. The elements 584678/584679/584680 already
--     carry correct en_US translations.
-- ============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, 'it_CH');

-- ============================================================================
-- 4. AD_UI_Element rows on tab 548506, existing "sftp" group 554996 (after
--    SftpFilenamePattern at seqno 70).
-- ============================================================================

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652676 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
	548506, 554996, 781746, 'SFTP Abfrageintervall (ms)', 80, 'N', 'Y', 'N', 0, 'N', 'F');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652677 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
	548506, 554996, 781747, 'SFTP Verzeichnis (verarbeitet)', 90, 'N', 'Y', 'N', 0, 'N', 'F');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField,
	IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsDisplayed_SideList, AD_UI_ElementType)
VALUES (652678 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-07-17 10:00', 'YYYY-MM-DD HH24:MI'), 100,
	548506, 554996, 781748, 'SFTP Verzeichnis (Fehler)', 100, 'N', 'Y', 'N', 0, 'N', 'F');
