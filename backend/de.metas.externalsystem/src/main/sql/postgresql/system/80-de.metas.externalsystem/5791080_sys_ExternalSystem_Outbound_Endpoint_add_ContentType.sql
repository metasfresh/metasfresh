-- ExternalSystem_Outbound_Endpoint: add ContentType column
-- Allows configuring the HTTP Content-Type header per endpoint (e.g. application/xml for EPCIS)

-- AD_Column
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592116, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        543391, 542551, 'ContentType', 'Content type', 'HTTP Content-Type header value (e.g. application/json, application/xml)',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        255, 'N', 'N', 'N',
        'NP');

-- Physical column
SELECT db_alter_table('ExternalSystem_Outbound_Endpoint', 'ADD COLUMN IF NOT EXISTS ContentType VARCHAR(255)');

-- AD_Field on the Outbound Endpoint window/tab
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      IsDisplayed, IsReadOnly, IsSameLine, IsCentrallyMaintained, IsFieldOnly)
VALUES (774765, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592116, 548506, 'Content type', 'HTTP Content-Type header value (e.g. application/json, application/xml)', 'de.metas.externalsystem',
        'Y', 'N', 'N', 'Y', 'N');
