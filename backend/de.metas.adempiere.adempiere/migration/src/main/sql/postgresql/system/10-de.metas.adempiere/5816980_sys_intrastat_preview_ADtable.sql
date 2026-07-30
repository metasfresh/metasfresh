-- Intrastat preview window — new AD_Table backed by Intrastat_Preview_V, plus one
-- AD_Column per view column.
--
-- The view is created by migration 5816890 (system/70-de.metas.fresh/) and mirrors the
-- payload of report.Intrastat_Export, exposing 21 columns. AD_Elements are all present:
-- Intrastat_Preview_V_ID (PK) was created by migration 5816970; every other column
-- reuses an existing shared AD_Element. Column references use the same AD_Reference_IDs
-- as the sibling debug table (Intrastat_Report_Detail_V, AD_Table_ID=542587):
--   19 Table Direct · 30 Search · 20 Yes-No · 18 CreatedBy/UpdatedBy ·
--   16 Date+Time · 13 ID · 10 String · 22 Number.
-- IsSOTrx, C_Year_ID, C_Period_ID are the grid filters (AD_Column.IsSelectionColumn='Y').
-- All columns are read-only (IsUpdateable='N') and non-mandatory (IsMandatory='N') —
-- the AD_Table is view-backed and used only for previewing report.Intrastat_Export.

-- =====================================================================
-- 1. AD_Table: Intrastat_Preview_V
-- =====================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, TableName, EntityType, AccessLevel,
    IsView, IsHighVolume, IsChangeLog,
    IsDeleteable, IsSecurityEnabled,
    PersonalDataCategory)
VALUES (542632 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    'Intrastat_Preview_V', 'Intrastat_Preview_V', 'D', '3',
    'Y', 'Y', 'N',
    'N', 'N',
    'NP');

-- =====================================================================
-- 2. AD_Columns (21 columns — one per view column, in view order)
-- =====================================================================

-- Col 1: Intrastat_Preview_V_ID (PK, single IsKey='Y' column — view synthetic MD5→int PK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593053 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
    585149, 542632, 13,
    'Intrastat_Preview_V_ID', 'Intrastat-Vorschau', 'D',
    'Y', 'Y', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    10);

-- Col 2: AD_Client_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593054 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:02','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
    102, 542632, 19,
    'AD_Client_ID', 'Mandant', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    20);

-- Col 3: AD_Org_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593055 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:03','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
    113, 542632, 30,
    'AD_Org_ID', 'Sektion', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    30);

-- Col 4: IsActive
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593056 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:04','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
    348, 542632, 20,
    'IsActive', 'Aktiv', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    1, 0, 'NP',
    40);

-- Col 5: Created
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593057 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:05','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
    245, 542632, 16,
    'Created', 'Erstellt', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    29, 0, 'NP',
    50);

-- Col 6: CreatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593058 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:06','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
    246, 542632, 18,
    'CreatedBy', 'Erstellt durch', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    60);

-- Col 7: Updated
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593059 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:07','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
    607, 542632, 16,
    'Updated', 'Aktualisiert', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    29, 0, 'NP',
    70);

-- Col 8: UpdatedBy
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593060 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:08','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:08','YYYY-MM-DD HH24:MI:SS'), 100,
    608, 542632, 18,
    'UpdatedBy', 'Aktualisiert durch', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    80);

-- Col 9: IsSOTrx — grid filter (Sales vs Purchase direction)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593061 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:09','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:09','YYYY-MM-DD HH24:MI:SS'), 100,
    1106, 542632, 20,
    'IsSOTrx', 'Verkaufstransaktion', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    1, 0, 'NP',
    90);

-- Col 10: C_Year_ID — grid filter (Table Direct)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593062 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:10','YYYY-MM-DD HH24:MI:SS'), 100,
    223, 542632, 19,
    'C_Year_ID', 'Jahr', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    10, 0, 'NP',
    100);

-- Col 11: C_Period_ID — grid filter (Table Direct)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593063 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:11','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:11','YYYY-MM-DD HH24:MI:SS'), 100,
    206, 542632, 19,
    'C_Period_ID', 'Periode', 'D',
    'N', 'N', 'N', 'N',
    'Y', 'N',
    10, 0, 'NP',
    110);

-- Col 12: CNCode — commodity number
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593064 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:12','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:12','YYYY-MM-DD HH24:MI:SS'), 100,
    584088, 542632, 10,
    'CNCode', 'CN8 Code', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    255, 0, 'NP',
    120);

-- Col 13: GoodsDescription
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593065 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:13','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:13','YYYY-MM-DD HH24:MI:SS'), 100,
    584089, 542632, 10,
    'GoodsDescription', 'Goods description', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    600, 0, 'NP',
    130);

-- Col 14: CountryDestinationConsignment — 2-letter ISO
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593066 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:14','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:14','YYYY-MM-DD HH24:MI:SS'), 100,
    584090, 542632, 10,
    'CountryDestinationConsignment', 'Country of Destination/Consignment', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    2, 0, 'NP',
    140);

-- Col 15: CountryOfOrigin — 2-letter ISO
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593067 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:15','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:15','YYYY-MM-DD HH24:MI:SS'), 100,
    584091, 542632, 10,
    'CountryOfOrigin', 'Country of origin', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    2, 0, 'NP',
    150);

-- Col 16: IntrastaNatureOfTransaction — nature-of-transaction code (established metasfresh spelling)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593068 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:16','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:16','YYYY-MM-DD HH24:MI:SS'), 100,
    584085, 542632, 10,
    'IntrastaNatureOfTransaction', 'Art der Transaktion', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    40, 0, 'NP',
    160);

-- Col 17: NetMass — kg
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593069 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:17','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:17','YYYY-MM-DD HH24:MI:SS'), 100,
    584092, 542632, 22,
    'NetMass', 'Net mass', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    170);

-- Col 18: SupplementaryUnits — secondary UoM qty
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593070 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:18','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:18','YYYY-MM-DD HH24:MI:SS'), 100,
    584093, 542632, 22,
    'SupplementaryUnits', 'Supplementary units', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    180);

-- Col 19: InvoiceValue — line net
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593071 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:19','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:19','YYYY-MM-DD HH24:MI:SS'), 100,
    584094, 542632, 22,
    'InvoiceValue', 'Invoice value', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    190);

-- Col 20: StatisticalValue — computed statistical amount
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593072 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:20','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:20','YYYY-MM-DD HH24:MI:SS'), 100,
    584095, 542632, 22,
    'StatisticalValue', 'Statistical value', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    10, 0, 'NP',
    200);

-- Col 21: RecipientVATNo — destination VAT ID (personal-data tax id)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, AD_Reference_ID,
    ColumnName, Name, EntityType,
    IsKey, IsMandatory, IsUpdateable, IsParent,
    IsSelectionColumn, IsIdentifier,
    FieldLength, Version, PersonalDataCategory,
    SeqNo)
VALUES (593073 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:21','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:21','YYYY-MM-DD HH24:MI:SS'), 100,
    584096, 542632, 10,
    'RecipientVATNo', 'Recipient-VAT-No', 'D',
    'N', 'N', 'N', 'N',
    'N', 'N',
    60, 0, 'P',
    210);

-- =====================================================================
-- 3. Seed AD_Column_Trl skeleton rows for every active system language,
--    then propagate translations from AD_Element -> AD_Column.
-- =====================================================================
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Table_ID = 542632
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate name/description/help translations from each column's linked AD_Element.
-- (One call per distinct element covering the columns we just inserted.)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585149); -- Intrastat_Preview_V_ID (new)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(102);    -- AD_Client_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(113);    -- AD_Org_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(348);    -- IsActive
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(245);    -- Created
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(246);    -- CreatedBy
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(607);    -- Updated
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(608);    -- UpdatedBy
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1106);   -- IsSOTrx
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(223);    -- C_Year_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(206);    -- C_Period_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584088); -- CNCode
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584089); -- GoodsDescription
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584090); -- CountryDestinationConsignment
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584091); -- CountryOfOrigin
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584085); -- IntrastaNatureOfTransaction
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584092); -- NetMass
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584093); -- SupplementaryUnits
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584094); -- InvoiceValue
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584095); -- StatisticalValue
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584096); -- RecipientVATNo
