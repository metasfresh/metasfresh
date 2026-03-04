-- M_InOut_ExternalSystem_ScriptedAdapter_ExportLog: Export log for scripted adapter exports
-- Tracks the status (Pending/Sent/Error) of each export invocation per shipment+config combination.
-- Managed programmatically — no window/tab needed.

-- =============================================
-- 1. AD_Reference (List): ExternalSystemScriptedAdapterExportStatus
-- =============================================
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542066, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystemScriptedAdapterExportStatus', 'Status of a scripted adapter export invocation', 'L', 'de.metas.externalsystem', 'N');

-- Pending
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544156, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        542066, 'P', 'Pending', 'Pending', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Ref_List_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, IsTranslated)
SELECT 544156, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, 'Ausstehend', 'Y'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID = 544156 AND AD_Language = 'de_DE');

UPDATE AD_Ref_List_Trl SET Name = 'Pending', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544156 AND AD_Language = 'en_US';

-- Sent
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544157, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        542066, 'S', 'Sent', 'Sent', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Ref_List_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, IsTranslated)
SELECT 544157, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, 'Gesendet', 'Y'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID = 544157 AND AD_Language = 'de_DE');

UPDATE AD_Ref_List_Trl SET Name = 'Sent', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544157 AND AD_Language = 'en_US';

-- Error
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544158, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        542066, 'E', 'Error', 'Error', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Ref_List_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, IsTranslated)
SELECT 544158, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, 'Fehler', 'Y'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl WHERE AD_Ref_List_ID = 544158 AND AD_Language = 'de_DE');

UPDATE AD_Ref_List_Trl SET Name = 'Error', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544158 AND AD_Language = 'en_US';

-- =============================================
-- 2. AD_Element for PK
-- =============================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584614, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        'M_InOut_ExternalSystem_ScriptedAdapter_ExportLog_ID',
        'M_InOut ExternalSystem ScriptedAdapter ExportLog',
        'M_InOut ExternalSystem ScriptedAdapter ExportLog',
        'de.metas.externalsystem');

-- AD_Element for SendDate
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584615, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SendDate', 'Send Date', 'Send Date', 'de.metas.externalsystem');

-- AD_Element for ExternalSystemScriptedAdapterExportStatus
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584616, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystemScriptedAdapterExportStatus',
        'Export Status',
        'Export Status',
        'de.metas.externalsystem');

-- =============================================
-- 3. AD_Table
-- =============================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, EntityType, IsView, AccessLevel, AD_Window_ID,
                      IsHighVolume, IsChangeLog, IsDeleteable, IsSecurityEnabled, ReplicationType)
VALUES (542585, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        'M_InOut_ExternalSystem_ScriptedAdapter_ExportLog',
        'M_InOut ExternalSystem ScriptedAdapter ExportLog',
        'de.metas.externalsystem', 'N', '3', NULL,
        'N', 'N', 'Y', 'N', 'L');

-- =============================================
-- 4. AD_Columns (standard + domain)
-- =============================================

-- PK
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592150, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        584614, 542585, 'M_InOut_ExternalSystem_ScriptedAdapter_ExportLog_ID',
        'M_InOut ExternalSystem ScriptedAdapter ExportLog',
        0, 'de.metas.externalsystem', 13,
        'Y', 'N', 'N', 'Y', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- AD_Client_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592151, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        102, 542585, 'AD_Client_ID', 'Client',
        0, 'de.metas.externalsystem', 19,
        'Y', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- AD_Org_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592152, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        113, 542585, 'AD_Org_ID', 'Organisation',
        0, 'de.metas.externalsystem', 30,
        'Y', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- IsActive
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592153, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        348, 542585, 'IsActive', 'Active',
        0, 'de.metas.externalsystem', 20,
        'Y', 'Y', 'Y', 'N', 'N', 'N',
        1, 'N', 'N', 'N',
        'NP');

-- Created
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592154, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        245, 542585, 'Created', 'Created',
        0, 'de.metas.externalsystem', 16,
        'Y', 'N', 'N', 'N', 'N',
        7, 'N', 'N', 'N',
        'NP');

-- CreatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592155, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        246, 542585, 'CreatedBy', 'Created By',
        0, 'de.metas.externalsystem', 18, 110,
        'Y', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- Updated
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592156, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        607, 542585, 'Updated', 'Updated',
        0, 'de.metas.externalsystem', 16,
        'Y', 'N', 'N', 'N', 'N',
        7, 'N', 'N', 'N',
        'NP');

-- UpdatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592157, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        608, 542585, 'UpdatedBy', 'Updated By',
        0, 'de.metas.externalsystem', 18, 110,
        'Y', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- =============================================
-- Domain columns
-- =============================================

-- M_InOut_ID (FK, mandatory)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592158, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        1025, 542585, 'M_InOut_ID', 'Shipment/Receipt',
        0, 'de.metas.externalsystem', 30,
        'Y', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- ExternalSystem_Config_ScriptedExportConversion_ID (FK, mandatory)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592159, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        (SELECT ad_element_id FROM ad_element WHERE columnname = 'ExternalSystem_Config_ScriptedExportConversion_ID'),
        542585, 'ExternalSystem_Config_ScriptedExportConversion_ID', 'Scripted Export Conversion Config',
        0, 'de.metas.externalsystem', 30,
        'Y', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- ExternalSystemScriptedAdapterExportStatus (RefList, mandatory, default 'P')
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592160, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        584616, 542585, 'ExternalSystemScriptedAdapterExportStatus', 'Export Status',
        0, 'de.metas.externalsystem', 17, 542066,
        'Y', 'P', 'Y', 'N', 'N', 'N',
        1, 'N', 'N', 'N',
        'NP');

-- ErrorMsg (Text, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592161, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        1021, 542585, 'ErrorMsg', 'Error Message',
        0, 'de.metas.externalsystem', 14,
        'N', 'Y', 'N', 'N', 'N',
        2000, 'N', 'N', 'N',
        'NP');

-- SendDate (Timestamp, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592162, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        584615, 542585, 'SendDate', 'Send Date',
        0, 'de.metas.externalsystem', 16,
        'N', 'Y', 'N', 'N', 'N',
        7, 'N', 'N', 'N',
        'NP');

-- AD_PInstance_ID (FK, nullable)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592163, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 13:00', 'YYYY-MM-DD HH24:MI'), 100,
        114, 542585, 'AD_PInstance_ID', 'Process Instance',
        0, 'de.metas.externalsystem', 30,
        'N', 'N', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- =============================================
-- 5. Physical table
-- =============================================
CREATE TABLE IF NOT EXISTS M_InOut_ExternalSystem_ScriptedAdapter_ExportLog
(
    M_InOut_ExternalSystem_ScriptedAdapter_ExportLog_ID BIGSERIAL PRIMARY KEY,
    AD_Client_ID                                         NUMERIC(10)  NOT NULL,
    AD_Org_ID                                            NUMERIC(10)  NOT NULL,
    IsActive                                             CHAR(1)      NOT NULL DEFAULT 'Y',
    Created                                              TIMESTAMP    NOT NULL DEFAULT now(),
    CreatedBy                                            NUMERIC(10)  NOT NULL,
    Updated                                              TIMESTAMP    NOT NULL DEFAULT now(),
    UpdatedBy                                            NUMERIC(10)  NOT NULL,
    M_InOut_ID                                           NUMERIC(10)  NOT NULL,
    ExternalSystem_Config_ScriptedExportConversion_ID    NUMERIC(10)  NOT NULL,
    ExternalSystemScriptedAdapterExportStatus            VARCHAR(1)   NOT NULL DEFAULT 'P',
    ErrorMsg                                             TEXT,
    SendDate                                             TIMESTAMP,
    AD_PInstance_ID                                      NUMERIC(10)
);
