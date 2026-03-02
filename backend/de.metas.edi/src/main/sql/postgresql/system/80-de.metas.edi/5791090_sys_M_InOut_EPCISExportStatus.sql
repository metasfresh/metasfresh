-- M_InOut: add EPCISExportStatus column with reference list
-- Tracks EPCIS export status per shipment (Pending, Sent, Error)

-- AD_Element
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584601, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'EPCISExportStatus', 'EPCIS Export Status', 'EPCIS Export Status', 'de.metas.esb.edi');

-- AD_Element translation (German)
UPDATE AD_Element_Trl
SET Name         = 'EPCIS Exportstatus',
    PrintName    = 'EPCIS Exportstatus',
    Updated      = TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584601
  AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET Name         = 'EPCIS Exportstatus',
    PrintName    = 'EPCIS Exportstatus',
    Updated      = TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584601
  AND AD_Language = 'de_DE';

-- AD_Reference (List type)
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          Name, ValidationType, EntityType)
VALUES (542062, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'EPCIS_ExportStatus', 'L', 'de.metas.esb.edi');

-- AD_Ref_List entries (single char codes, same pattern as EDI_ExportStatus)
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544144, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542062, 'P', 'Noch nicht gesendet', 'de.metas.esb.edi');

INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544145, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542062, 'S', 'Gesendet', 'de.metas.esb.edi');

INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, Name, EntityType)
VALUES (544146, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542062, 'E', 'Fehler', 'de.metas.esb.edi');

-- AD_Ref_List translations
UPDATE AD_Ref_List_Trl SET Name = 'Pending', Updated = TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544144 AND AD_Language = 'en_US';

UPDATE AD_Ref_List_Trl SET Name = 'Sent', Updated = TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544145 AND AD_Language = 'en_US';

UPDATE AD_Ref_List_Trl SET Name = 'Error', Updated = TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544146 AND AD_Language = 'en_US';

-- AD_Column on M_InOut
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592117, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584601, 319, 'EPCISExportStatus', 'EPCIS Export Status',
        0, 'de.metas.esb.edi', 17, 542062,
        'N', 'Y', 'N', 'N', 'N',
        1, 'N', 'N', 'N',
        'NP');

-- Physical column
SELECT db_alter_table('M_InOut', 'ADD COLUMN IF NOT EXISTS EPCISExportStatus CHAR(1)');

-- AD_Field on Shipment window (Lieferung, AD_Window_ID=169, AD_Tab_ID=257)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, EntityType,
                      IsDisplayed, IsReadOnly, IsSameLine, IsCentrallyMaintained, IsFieldOnly)
VALUES (774766, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592117, 257, 'EPCIS Export Status', 'de.metas.esb.edi',
        'Y', 'Y', 'N', 'Y', 'N');
