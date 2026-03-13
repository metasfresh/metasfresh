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
VALUES (1003140, 0, 0, 'Y',
	now(), 100, now(), 100,
	'SftpPollingIntervalMs', 'SFTP Abfrageintervall (ms)', 'SFTP Abfrageintervall (ms)',
	'Wie oft der SFTP-Server auf neue Dateien geprüft wird, in Millisekunden (Standard: 60000 = 1 Minute)',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 1003140
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'SFTP Polling Interval (ms)',
    PrintName    = 'SFTP Polling Interval (ms)',
    Description  = 'How often to check the SFTP server for new files, in milliseconds (default: 60000 = 1 minute)',
    IsTranslated = 'Y'
WHERE AD_Element_ID = 1003140 AND AD_Language = 'en_US';

-- 1b. SftpProcessedDirectory
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (1003141, 0, 0, 'Y',
	now(), 100, now(), 100,
	'SftpProcessedDirectory', 'SFTP Verzeichnis (verarbeitet)', 'SFTP Verzeichnis (verarbeitet)',
	'Remote-Verzeichnis, in das erfolgreich verarbeitete Dateien verschoben werden',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 1003141
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'SFTP Processed Directory',
    PrintName    = 'SFTP Processed Directory',
    Description  = 'Remote directory to move successfully processed files to',
    IsTranslated = 'Y'
WHERE AD_Element_ID = 1003141 AND AD_Language = 'en_US';

-- 1c. SftpErrorDirectory
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (1003142, 0, 0, 'Y',
	now(), 100, now(), 100,
	'SftpErrorDirectory', 'SFTP Verzeichnis (Fehler)', 'SFTP Verzeichnis (Fehler)',
	'Remote-Verzeichnis, in das fehlgeschlagene Dateien verschoben werden',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 1003142
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'SFTP Error Directory',
    PrintName    = 'SFTP Error Directory',
    Description  = 'Remote directory to move files that failed processing to',
    IsTranslated = 'Y'
WHERE AD_Element_ID = 1003142 AND AD_Language = 'en_US';


-- ============================================================================
-- 2. AD_Columns on ExternalSystem_Config_ScriptedImportConversion (AD_Table_ID=542546)
-- ============================================================================

-- 2a. ExternalSystem_Endpoint_ID (FK → ExternalSystem_Endpoint, nullable, Search)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (1000070, 0, 0, 'Y',
	now(), 100, now(), 100,
	0, 542546, 584191,
	'ExternalSystem_Endpoint_ID', 'ExternalSystem Endpoint', 'Endpoint configuration for SFTP transport',
	30, NULL,
	10, 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'de.metas.externalsystem', 'N', 'Y', 0);

-- 2b. SftpPollingIntervalMs (INTEGER, nullable, default 60000)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, DefaultValue, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	MandatoryLogic,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (1000071, 0, 0, 'Y',
	now(), 100, now(), 100,
	0, 542546, 1003140,
	'SftpPollingIntervalMs', 'SFTP Abfrageintervall (ms)',
	'Wie oft der SFTP-Server auf neue Dateien geprüft wird, in Millisekunden (Standard: 60000 = 1 Minute)',
	11, NULL,
	10, '60000', 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	NULL,
	'de.metas.externalsystem', 'N', 'Y', 0);

-- 2c. SftpProcessedDirectory (VARCHAR 255, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (1000072, 0, 0, 'Y',
	now(), 100, now(), 100,
	0, 542546, 1003141,
	'SftpProcessedDirectory', 'SFTP Verzeichnis (verarbeitet)',
	'Remote-Verzeichnis, in das erfolgreich verarbeitete Dateien verschoben werden',
	10, NULL,
	255, 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'de.metas.externalsystem', 'N', 'Y', 0);

-- 2d. SftpErrorDirectory (VARCHAR 255, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Version, AD_Table_ID, AD_Element_ID,
	ColumnName, Name, Description,
	AD_Reference_ID, AD_Reference_Value_ID,
	FieldLength, IsMandatory, IsUpdateable, IsIdentifier,
	IsKey, IsParent, IsTranslated, IsEncrypted,
	EntityType, IsSelectionColumn, IsAllowLogging, SeqNo)
VALUES (1000073, 0, 0, 'Y',
	now(), 100, now(), 100,
	0, 542546, 1003142,
	'SftpErrorDirectory', 'SFTP Verzeichnis (Fehler)',
	'Remote-Verzeichnis, in das fehlgeschlagene Dateien verschoben werden',
	10, NULL,
	255, 'N', 'Y', 'N',
	'N', 'N', 'N', 'N',
	'de.metas.externalsystem', 'N', 'Y', 0);


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
VALUES (1000020, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000070, 'ExternalSystem Endpoint', 'Y', 'N', 'N', 'N',
	'de.metas.externalsystem', 'Y');

-- 4b. SftpPollingIntervalMs field
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (1000021, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000071, 'SFTP Abfrageintervall (ms)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

-- 4c. SftpProcessedDirectory field
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (1000022, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000072, 'SFTP Verzeichnis (verarbeitet)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

-- 4d. SftpErrorDirectory field
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (1000023, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000073, 'SFTP Verzeichnis (Fehler)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');


-- ============================================================================
-- 5. AD_Fields for Tab 548472 (Skriptbasierte Importkonvertierung — parent window 541024)
-- ============================================================================

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	EntityType, IsDisplayedGrid)
VALUES (1000024, 0, 0, 'Y',
	now(), 100, now(), 100,
	548472, 1000070, 'ExternalSystem Endpoint', 'Y', 'N', 'N', 'N',
	'de.metas.externalsystem', 'Y');

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (1000025, 0, 0, 'Y',
	now(), 100, now(), 100,
	548472, 1000071, 'SFTP Abfrageintervall (ms)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (1000026, 0, 0, 'Y',
	now(), 100, now(), 100,
	548472, 1000072, 'SFTP Verzeichnis (verarbeitet)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, IsHeading,
	DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (1000027, 0, 0, 'Y',
	now(), 100, now(), 100,
	548472, 1000073, 'SFTP Verzeichnis (Fehler)', 'Y', 'N', 'N', 'N',
	'@ExternalSystem_Endpoint_ID/0@>0', 'de.metas.externalsystem', 'N');


-- ============================================================================
-- 6. AD_UI_ElementGroup for SFTP fields (Tab 548473, Column 1)
-- ============================================================================

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_UI_Column_ID, Name, SeqNo, UIStyle)
VALUES (1000050, 0, 0, 'Y',
	now(), 100, now(), 100,
	548533, 'sftp', 20, NULL);


-- ============================================================================
-- 7. AD_UI_Elements for Tab 548473 (dedicated window)
-- ============================================================================

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (1000520, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000050, 1000020, 'ExternalSystem Endpoint', 10, 'N', 'Y', 'Y', 'N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (1000521, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000050, 1000021, 'SFTP Abfrageintervall (ms)', 20, 'N', 'Y', 'N', 'N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (1000522, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000050, 1000022, 'SFTP Verzeichnis (verarbeitet)', 30, 'N', 'Y', 'N', 'N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList)
VALUES (1000523, 0, 0, 'Y',
	now(), 100, now(), 100,
	548473, 1000050, 1000023, 'SFTP Verzeichnis (Fehler)', 40, 'N', 'Y', 'N', 'N');
