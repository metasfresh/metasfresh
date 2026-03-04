-- ExternalSystem_Outbound_Endpoint: add ContentType as mandatory RefList column
-- Allows configuring the HTTP Content-Type header per endpoint (e.g. application/xml for EPCIS)

-- 1. Create AD_Reference (List) for ContentType values
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542065, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystemOutboundEndpointContentType', 'HTTP Content-Type header for outbound endpoint', 'L', 'de.metas.externalsystem', 'N');

-- 2. Create AD_Ref_List entries
-- application/json (default)
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544153, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542065, 'application/json', 'application_json', 'application/json', 'de.metas.externalsystem');

-- application/xml
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544154, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542065, 'application/xml', 'application_xml', 'application/xml', 'de.metas.externalsystem');

-- text/xml
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544155, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542065, 'text/xml', 'text_xml', 'text/xml', 'de.metas.externalsystem');

-- 3. AD_Column: change from String(10) to List(17) with reference, make mandatory
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       PersonalDataCategory)
VALUES (592116, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        543391, 542551, 'ContentType', 'Content type', 'HTTP Content-Type header value (e.g. application/json, application/xml)',
        0, 'de.metas.externalsystem', 17, 542065,
        'Y', 'application/json', 'Y', 'N', 'N', 'N',
        40, 'N', 'N', 'N',
        'NP');

-- 4. Physical column + populate existing rows + make NOT NULL
SELECT db_alter_table('ExternalSystem_Outbound_Endpoint', 'ADD COLUMN IF NOT EXISTS ContentType VARCHAR(40)');

UPDATE ExternalSystem_Outbound_Endpoint SET ContentType = 'application/json' WHERE ContentType IS NULL;

INSERT INTO t_alter_column VALUES ('ExternalSystem_Outbound_Endpoint', 'ContentType', 'VARCHAR(40)', 'NOT NULL', 'application/json');

-- 5. AD_Field on the Outbound Endpoint window/tab
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      IsDisplayed, IsReadOnly, IsSameLine, IsCentrallyMaintained, IsFieldOnly)
VALUES (774765, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592116, 548506, 'Content type', 'HTTP Content-Type header value (e.g. application/json, application/xml)', 'de.metas.externalsystem',
        'Y', 'N', 'N', 'Y', 'N');
