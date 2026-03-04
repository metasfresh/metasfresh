-- ExternalSystem_Config_ScriptedExportConversion: add SeqNo column
-- Controls execution order when multiple configs trigger on the same document completion.
-- MandatoryLogic: mandatory only when IsTriggerOnComplete='Y'

-- AD_Column for SeqNo (reuse standard AD_Element 566)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, DefaultValue, MandatoryLogic,
                       IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592148, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        566, 542541, 'SeqNo', 'Reihenfolge', 'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',
        0, 'de.metas.externalsystem', 11,
        'N', '10', '@IsTriggerOnComplete@=''Y''',
        'Y', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        'NP');

-- Physical column
SELECT db_alter_table('ExternalSystem_Config_ScriptedExportConversion', 'ADD COLUMN IF NOT EXISTS SeqNo INTEGER');

-- Backfill existing TriggerOnComplete records
UPDATE ExternalSystem_Config_ScriptedExportConversion
SET SeqNo = 10
WHERE SeqNo IS NULL
  AND IsTriggerOnComplete = 'Y'
  AND IsActive = 'Y';

-- AD_Field on standalone window tab (548471)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      IsDisplayed, IsReadOnly, IsSameLine, IsCentrallyMaintained, IsFieldOnly)
VALUES (774820, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        592148, 548471, 'Reihenfolge', 'Zur Bestimmung der Reihenfolge der Einträge', 'de.metas.externalsystem',
        'Y', 'N', 'N', 'Y', 'N');
