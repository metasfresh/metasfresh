-- #############################################################################
-- Migration: rename ExternalSystem_Outbound_Endpoint -> ExternalSystem_Endpoint
-- Issue: me03#28749 (SFTP bidirectional transport)
-- The table is renamed because it will serve both inbound and outbound purposes.
-- #############################################################################

-- 1. Rename the physical table
ALTER TABLE ExternalSystem_Outbound_Endpoint RENAME TO ExternalSystem_Endpoint;

-- 2. Rename the PK column
ALTER TABLE ExternalSystem_Endpoint
    RENAME COLUMN ExternalSystem_Outbound_Endpoint_ID TO ExternalSystem_Endpoint_ID;

-- 3. Rename the FK column on ScriptedExportConversion
ALTER TABLE ExternalSystem_Config_ScriptedExportConversion
    RENAME COLUMN ExternalSystem_Outbound_Endpoint_ID TO ExternalSystem_Endpoint_ID;

-- 4. Rename the sequence
ALTER SEQUENCE ExternalSystem_Outbound_Endpoint_Seq RENAME TO ExternalSystem_Endpoint_Seq;

-- 5. Update AD_Table
UPDATE AD_Table
SET TableName = 'ExternalSystem_Endpoint',
    Name      = 'ExternalSystem_Endpoint'
WHERE AD_Table_ID = 542551;

-- 6. Update AD_Column for the PK (on the renamed table, AD_Table_ID=542551)
UPDATE AD_Column
SET ColumnName = 'ExternalSystem_Endpoint_ID',
    Name       = 'ExternalSystem_Endpoint_ID'
WHERE ColumnName = 'ExternalSystem_Outbound_Endpoint_ID'
  AND AD_Table_ID = 542551;

-- 7. Update AD_Column for the FK (on ScriptedExportConversion, AD_Table_ID=542541)
UPDATE AD_Column
SET ColumnName = 'ExternalSystem_Endpoint_ID',
    Name       = 'ExternalSystem_Endpoint_ID'
WHERE ColumnName = 'ExternalSystem_Outbound_Endpoint_ID'
  AND AD_Table_ID = 542541;

-- 8. Update AD_Element (AD_Element_ID=584191)
UPDATE AD_Element
SET ColumnName = 'ExternalSystem_Endpoint_ID',
    Name       = 'ExternalSystem Endpoint',
    PrintName  = 'ExternalSystem Endpoint'
WHERE AD_Element_ID = 584191;

-- 9. Update AD_Element_Trl — base language entries (de_DE, de_CH): istranslated='N'
UPDATE AD_Element_Trl
SET Name          = 'ExternalSystem Endpoint',
    PrintName     = 'ExternalSystem Endpoint',
    IsTranslated  = 'N'
WHERE AD_Element_ID = 584191
  AND AD_Language IN ('de_DE', 'de_CH');

-- 10. Update AD_Element_Trl — English: istranslated='Y'
UPDATE AD_Element_Trl
SET Name         = 'ExternalSystem Endpoint',
    PrintName    = 'ExternalSystem Endpoint',
    IsTranslated = 'Y'
WHERE AD_Element_ID = 584191
  AND AD_Language = 'en_US';

-- 11. Update AD_Sequence
UPDATE AD_Sequence
SET Name = 'ExternalSystem_Endpoint'
WHERE Name = 'ExternalSystem_Outbound_Endpoint';
