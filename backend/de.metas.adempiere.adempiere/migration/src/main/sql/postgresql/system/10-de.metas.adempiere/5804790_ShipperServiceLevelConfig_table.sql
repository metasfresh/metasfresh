-- nShift service levels: add M_Shipper_ServiceLevel_Config table + AD metadata

CREATE TABLE M_Shipper_ServiceLevel_Config
(
    M_Shipper_ServiceLevel_Config_ID numeric(10) NOT NULL,
    AD_Client_ID                     numeric(10) NOT NULL,
    AD_Org_ID                        numeric(10) NOT NULL,
    IsActive                         char(1)     NOT NULL DEFAULT 'Y',
    Created                          timestamp   NOT NULL DEFAULT now(),
    CreatedBy                        numeric(10) NOT NULL,
    Updated                          timestamp   NOT NULL DEFAULT now(),
    UpdatedBy                        numeric(10) NOT NULL,
    M_Shipper_ID                     numeric(10) NOT NULL,
    SeqNo                            numeric(10) NOT NULL,
    External_System_ID               numeric(10),
    ServiceLevel                     varchar(60) NOT NULL,
    CONSTRAINT M_Shipper_ServiceLevel_Config_PK PRIMARY KEY (M_Shipper_ServiceLevel_Config_ID)
);

ALTER TABLE M_Shipper_ServiceLevel_Config
    ADD CONSTRAINT M_Shipper_ServiceLevel_Config_Shipper
        FOREIGN KEY (M_Shipper_ID) REFERENCES M_Shipper (M_Shipper_ID) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Shipper_ServiceLevel_Config
    ADD CONSTRAINT M_Shipper_ServiceLevel_Config_ExternalSystem
        FOREIGN KEY (External_System_ID) REFERENCES ExternalSystem (ExternalSystem_ID) DEFERRABLE INITIALLY DEFERRED;

-- AD_Element for the PK column
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584917 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'M_Shipper_ServiceLevel_Config_ID', 'Service Level Konfiguration', 'Service Level Konfiguration', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                             Name, PrintName, Description, Help,
                             IsTranslated, AD_Client_ID, AD_Org_ID,
                             Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 584917
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Name         = 'Service Level Config',
    PrintName    = 'Service Level Config',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-26 14:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584917
  AND AD_Language = 'en_US';

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-26 14:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584917
  AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Table
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
                      Created, CreatedBy, Updated, UpdatedBy,
                      Name, TableName, IsView, AccessLevel, EntityType,
                      ImportTable, IsChangeLog, ReplicationType, IsHighVolume)
VALUES (542606 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:01:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:01:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Service Level Konfiguration', 'M_Shipper_ServiceLevel_Config', 'N', '3', 'D',
        'N', 'Y', 'L', 'N');

-- AD_Table_Trl
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
                           Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542606
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID);

UPDATE AD_Table_Trl
SET Name         = 'Service Level Config',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-26 14:01:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Table_ID = 542606
  AND AD_Language = 'en_US';

-- AD_Column: M_Shipper_ServiceLevel_Config_ID (PK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592626 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Service Level Konfiguration', 542606, 584917, 'M_Shipper_ServiceLevel_Config_ID',
        13, 10,
        'Y', 'Y', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: AD_Client_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592627 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Mandant', 542606, 102, 'AD_Client_ID',
        19, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: AD_Org_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592628 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Sektion', 542606, 113, 'AD_Org_ID',
        30, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: IsActive
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength, DefaultValue,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592629 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Aktiv', 542606, 348, 'IsActive',
        20, 1, 'Y',
        'Y', 'N', 'N', 'Y', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: Created
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592630 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Erstellt', 542606, 245, 'Created',
        16, 29,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: CreatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592631 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:05.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:05.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Erstellt durch', 542606, 246, 'CreatedBy',
        18, 110, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: Updated
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592632 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:06.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:06.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Aktualisiert', 542606, 607, 'Updated',
        16, 29,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: UpdatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592633 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:07.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:07.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Aktualisiert durch', 542606, 608, 'UpdatedBy',
        18, 110, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: M_Shipper_ID (parent FK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592634 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:08.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:08.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Lieferweg', 542606, 455, 'M_Shipper_ID',
        19, 10,
        'Y', 'N', 'Y', 'N', 'N',
        'N', 'N', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: SeqNo
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592635 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:09.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:09.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Reihenfolge', 542606, 566, 'SeqNo',
        11, 22,
        'Y', 'N', 'N', 'Y', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: External_System_ID (nullable FK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592636 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:10.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:10.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Externes System', 542606, 583968, 'External_System_ID',
        30, 541988, 10,
        'N', 'N', 'N', 'Y', 'N',
        'N', 'N', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column: ServiceLevel
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (592637 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:02:11.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-05-26 14:02:11.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Service Level', 542606, 584123, 'ServiceLevel',
        10, 60,
        'Y', 'N', 'N', 'Y', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'N', 0, 'D',
        'NP');

-- AD_Column_Trl: skeleton rows for all 12 new columns (one per active system language)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID IN (592626, 592627, 592628, 592629, 592630, 592631, 592632, 592633, 592634, 592635, 592636, 592637)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate element translations into the skeleton AD_Column_Trl rows
SELECT update_Column_Translation_From_AD_Element(584917); -- M_Shipper_ServiceLevel_Config_ID
SELECT update_Column_Translation_From_AD_Element(102);    -- AD_Client_ID
SELECT update_Column_Translation_From_AD_Element(113);    -- AD_Org_ID
SELECT update_Column_Translation_From_AD_Element(348);    -- IsActive
SELECT update_Column_Translation_From_AD_Element(245);    -- Created
SELECT update_Column_Translation_From_AD_Element(246);    -- CreatedBy
SELECT update_Column_Translation_From_AD_Element(607);    -- Updated
SELECT update_Column_Translation_From_AD_Element(608);    -- UpdatedBy
SELECT update_Column_Translation_From_AD_Element(455);    -- M_Shipper_ID
SELECT update_Column_Translation_From_AD_Element(566);    -- SeqNo
SELECT update_Column_Translation_From_AD_Element(583968); -- External_System_ID
SELECT update_Column_Translation_From_AD_Element(584123); -- ServiceLevel

