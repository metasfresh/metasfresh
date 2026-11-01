-- #############################################################################
-- Migration: Add SFTP inbound columns to ExternalSystem_Config_ScriptedImportConversion
-- Issue: me03#28749 (SFTP bidirectional transport)
-- These columns allow the ScriptedImportConversion to reference an ExternalSystem_Endpoint
-- for SFTP polling configuration (inbound file retrieval).
-- #############################################################################

-- ============================================================================
-- 1. AD_Elements (3 new: SftpPollingIntervalMs, SftpProcessedDirectory, SftpErrorDirectory)
--    ExternalSystem_Endpoint_ID element already exists (584191)
-- ============================================================================

-- 1a. SftpPollingIntervalMs
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (584678 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	'SftpPollingIntervalMs', 'SFTP Abfrageintervall (ms)', 'SFTP Abfrageintervall (ms)',
	'Wie oft der SFTP-Server auf neue Dateien geprüft wird, in Millisekunden (Standard: 60000 = 1 Minute)',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584678
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'SFTP Polling Interval (ms)',
    PrintName    = 'SFTP Polling Interval (ms)',
    Description  = 'How often to check the SFTP server for new files, in milliseconds (default: 60000 = 1 minute)',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584678 AND AD_Language = 'en_US';

-- 1b. SftpProcessedDirectory
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (584679 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	'SftpProcessedDirectory', 'SFTP Verzeichnis (verarbeitet)', 'SFTP Verzeichnis (verarbeitet)',
	'Remote-Verzeichnis, in das erfolgreich verarbeitete Dateien verschoben werden',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584679
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'SFTP Processed Directory',
    PrintName    = 'SFTP Processed Directory',
    Description  = 'Remote directory to move successfully processed files to',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584679 AND AD_Language = 'en_US';

-- 1c. SftpErrorDirectory
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (584680 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	'SftpErrorDirectory', 'SFTP Verzeichnis (Fehler)', 'SFTP Verzeichnis (Fehler)',
	'Remote-Verzeichnis, in das fehlgeschlagene Dateien verschoben werden',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584680
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'SFTP Error Directory',
    PrintName    = 'SFTP Error Directory',
    Description  = 'Remote directory to move files that failed processing to',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584680 AND AD_Language = 'en_US';


-- ============================================================================
-- 2. AD_Columns on ExternalSystem_Config_ScriptedImportConversion (AD_Table_ID=542546)
-- ============================================================================

-- 2a. ExternalSystem_Endpoint_ID (FK → ExternalSystem_Endpoint, nullable, TableDirect)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	PersonalDataCategory,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (592250 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	0, 542546, 584191,
	'ExternalSystem_Endpoint_ID', 'ExternalSystem Endpoint', 'Endpoint configuration for SFTP transport',
	19, NULL,
	10, 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'NP',
	'de.metas.externalsystem', 'N', 'Y', 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
	AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592250
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584191);

-- 2b. SftpPollingIntervalMs (INTEGER, nullable, default 60000)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, DefaultValue, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	PersonalDataCategory,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (592251 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	0, 542546, 584678,
	'SftpPollingIntervalMs', 'SFTP Abfrageintervall (ms)',
	'Wie oft der SFTP-Server auf neue Dateien geprüft wird, in Millisekunden (Standard: 60000 = 1 Minute)',
	11, NULL,
	10, '60000', 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'NP',
	'de.metas.externalsystem', 'N', 'Y', 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
	AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592251
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584678);

-- 2c. SftpProcessedDirectory (VARCHAR 255, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	PersonalDataCategory,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (592252 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	0, 542546, 584679,
	'SftpProcessedDirectory', 'SFTP Verzeichnis (verarbeitet)',
	'Remote-Verzeichnis, in das erfolgreich verarbeitete Dateien verschoben werden',
	10, NULL,
	255, 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'NP',
	'de.metas.externalsystem', 'N', 'Y', 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
	AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592252
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584679);

-- 2d. SftpErrorDirectory (VARCHAR 255, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	PersonalDataCategory,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (592253 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	0, 542546, 584680,
	'SftpErrorDirectory', 'SFTP Verzeichnis (Fehler)',
	'Remote-Verzeichnis, in das fehlgeschlagene Dateien verschoben werden',
	10, NULL,
	255, 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'NP',
	'de.metas.externalsystem', 'N', 'Y', 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
	AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592253
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584680);


-- ============================================================================
-- 3. Physical DDL
-- ============================================================================

ALTER TABLE ExternalSystem_Config_ScriptedImportConversion
	ADD COLUMN IF NOT EXISTS ExternalSystem_Endpoint_ID INTEGER REFERENCES ExternalSystem_Endpoint(ExternalSystem_Endpoint_ID);

ALTER TABLE ExternalSystem_Config_ScriptedImportConversion
	ADD COLUMN IF NOT EXISTS SftpPollingIntervalMs INTEGER DEFAULT 60000;

ALTER TABLE ExternalSystem_Config_ScriptedImportConversion
	ADD COLUMN IF NOT EXISTS SftpProcessedDirectory VARCHAR(255);

ALTER TABLE ExternalSystem_Config_ScriptedImportConversion
	ADD COLUMN IF NOT EXISTS SftpErrorDirectory VARCHAR(255);


-- ============================================================================
-- 4. AD_Fields for Tab 548473 (Skriptbasierte Importkonvertierung — dedicated window 541962)
--    Display logic: visible when an endpoint is set
-- ============================================================================

-- 4a. ExternalSystem_Endpoint_ID field (always visible)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	EntityType, IsDisplayedGrid)
VALUES (774932 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 592250, 'ExternalSystem Endpoint', 'Y', 'N', 'N', 'N',
	'de.metas.externalsystem', 'Y');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774932
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 4b. SftpPollingIntervalMs field
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (774933 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 592251, 'SFTP Abfrageintervall (ms)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774933
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 4c. SftpProcessedDirectory field
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (774934 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 592252, 'SFTP Verzeichnis (verarbeitet)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774934
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 4d. SftpErrorDirectory field
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (774935 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 592253, 'SFTP Verzeichnis (Fehler)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774935
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);


-- ============================================================================
-- 5. AD_Fields for Tab 548472 (Skriptbasierte Importkonvertierung — parent window 541024)
-- ============================================================================

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	EntityType, IsDisplayedGrid)
VALUES (774936 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548472, 592250, 'ExternalSystem Endpoint', 'Y', 'N', 'N', 'N',
	'de.metas.externalsystem', 'Y');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774936
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (774937 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548472, 592251, 'SFTP Abfrageintervall (ms)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774937
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (774938 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548472, 592252, 'SFTP Verzeichnis (verarbeitet)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774938
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (774939 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548472, 592253, 'SFTP Verzeichnis (Fehler)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774939
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);


-- ============================================================================
-- 6. AD_UI_ElementGroup for SFTP fields (Tab 548473, Column 1)
-- ============================================================================

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (554997 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548533, 'sftp', 20, NULL);


-- ============================================================================
-- 7. AD_UI_Elements for Tab 548473 (dedicated window)
-- ============================================================================

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (648581 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 554997, 774932, 'ExternalSystem Endpoint', 10, 'N', 'Y', 'Y', 'N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (648582 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 554997, 774933, 'SFTP Abfrageintervall (ms)', 20, 'N', 'Y', 'N', 'N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (648583 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 554997, 774934, 'SFTP Verzeichnis (verarbeitet)', 30, 'N', 'Y', 'N', 'N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (648584 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	TO_TIMESTAMP('2026-03-14 12:00', 'YYYY-MM-DD HH24:MI'), 100,
	548473, 554997, 774935, 'SFTP Verzeichnis (Fehler)', 40, 'N', 'Y', 'N', 'N');

-- Propagate element translations to all new columns and fields
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680);
