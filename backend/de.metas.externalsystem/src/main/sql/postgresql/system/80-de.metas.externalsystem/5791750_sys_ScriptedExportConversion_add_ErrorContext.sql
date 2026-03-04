-- ExternalSystem_Config_ScriptedExportConversion: add ExternalSystemErrorContext column
-- Specifies which error context to use for callbacks when the external system invocation fails.

-- AD_Element
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584613, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystemErrorContext', 'External System Error Context', 'External System Error Context', 'de.metas.externalsystem');

-- AD_Element_Trl (English)
UPDATE AD_Element_Trl
SET Name         = 'External System Error Context',
    PrintName    = 'External System Error Context',
    Updated      = TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100,
    IsTranslated = 'Y'
WHERE AD_Element_ID = 584613
  AND AD_Language = 'en_US';

-- AD_Column
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592149, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        584613, 542541, 'ExternalSystemErrorContext', 'External System Error Context',
        'Error context identifier for error listener callbacks (e.g. EDI, ScriptedAdapterExport)',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        40, 'N', 'N', 'N',
        'NP');

-- Physical column
SELECT db_alter_table('ExternalSystem_Config_ScriptedExportConversion', 'ALTER TABLE public.ExternalSystem_Config_ScriptedExportConversion ADD COLUMN IF NOT EXISTS ExternalSystemErrorContext VARCHAR(40)');

-- AD_Field on standalone window tab (548471)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      IsDisplayed, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774821, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        592149, 548471, 'External System Error Context',
        'Error context identifier for error listener callbacks', 'de.metas.externalsystem',
        'Y', 'N', 'N', 'N');
