-- 2026-06-03
-- gh#25618 — Bestand pro Woche / Stock per week
-- Register MD_Stock_PerWeek_V as IsView='Y' AD_Table with 6 columns.
--
-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_Table_ID         542612  (MD_Stock_PerWeek_V)
--   AD_Column_ID        592706  (M_Product_ID)
--   AD_Column_ID        592707  (M_Warehouse_ID)
--   AD_Column_ID        592708  (WeekStartDate)
--   AD_Column_ID        592709  (QtyExpectedShipments)
--   AD_Column_ID        592710  (QtyExpectedReceipts)
--   AD_Column_ID        592711  (QtyATP)
--   AD_Element_ID       584938  (WeekStartDate — new)
--   AD_Element_ID       584939  (QtyExpectedShipments — new)
--   AD_Element_ID       584940  (QtyExpectedReceipts — new)
--   AD_Element_ID       584821  (QtyATP — reused standard; ColumnName='QtyATP', Name='Verfügbare Menge')
--                               Label override "Verfügbar (ATP)" applied via AD_Field.AD_Name_ID in Task 5.
--   AD_Element_ID       454     (M_Product_ID — reused standard)
--   AD_Element_ID       459     (M_Warehouse_ID — reused standard)
--
-- AD_Reference IDs used:
--   30 = Search  (M_Product_ID, M_Warehouse_ID — same as MD_Candidate)
--   15 = Date    (WeekStartDate)
--   29 = Quantity (QtyExpectedShipments, QtyExpectedReceipts, QtyATP)

-- ============================================================
-- 1. AD_Table
-- ============================================================
INSERT INTO AD_Table
    (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, TableName, AccessLevel, EntityType,
     IsView, IsDeleteable, IsHighVolume, IsSecurityEnabled, IsChangeLog, IsAutocomplete,
     ImportTable, ReplicationType, CopyColumnsFromTable, ACTriggerLength, LoadSeq)
VALUES
    (542612 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'MD Stock PerWeek V', 'MD_Stock_PerWeek_V', '3', 'de.metas.material.dispo',
     'Y', 'N', 'N', 'N', 'N', 'N',
     'N', 'L', 'N', 0, 0)
;

INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Table_ID=542612
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- ============================================================
-- 2. New AD_Element rows (WeekStartDate, QtyExpectedShipments, QtyExpectedReceipts)
--    M_Product_ID (454), M_Warehouse_ID (459), QtyATP (584821) are reused standard elements.
--    Base language (de_DE values) stored in AD_Element itself.
--    QtyATP: existing element 584821 (ColumnName='QtyATP') is reused; the desired label
--    "Verfügbar (ATP)" will be applied via AD_Field.AD_Name_ID in the window migration (Task 5).
-- ============================================================

-- 2a. WeekStartDate
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (584938 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'WeekStartDate', 'Wochenbeginn (KW)', 'Wochenbeginn (KW)', 'de.metas.material.dispo')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:01:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:01:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Element_ID=584938
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- de_DE / de_CH
UPDATE AD_Element_Trl
SET Name='Wochenbeginn (KW)', PrintName='Wochenbeginn (KW)', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 12:01:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584938 AND AD_Language IN ('de_DE','de_CH')
;
-- en_US
UPDATE AD_Element_Trl
SET Name='Week start', PrintName='Week start', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 12:01:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584938 AND AD_Language='en_US'
;

-- 2b. QtyExpectedShipments
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (584939 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'QtyExpectedShipments', 'Erwartete Lieferungen', 'Erwartete Lieferungen', 'de.metas.material.dispo')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:02:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:02:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Element_ID=584939
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name='Erwartete Lieferungen', PrintName='Erwartete Lieferungen', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 12:02:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584939 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl
SET Name='Expected shipments', PrintName='Expected shipments', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 12:02:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584939 AND AD_Language='en_US'
;

-- 2c. QtyExpectedReceipts
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (584940 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'QtyExpectedReceipts', 'Erwartete Wareneingänge', 'Erwartete Wareneingänge', 'de.metas.material.dispo')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:03:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:03:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Element_ID=584940
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name='Erwartete Wareneingänge', PrintName='Erwartete Wareneingänge', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 12:03:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584940 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl
SET Name='Expected receipts', PrintName='Expected receipts', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 12:03:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584940 AND AD_Language='en_US'
;

-- 2d. QtyATP — reuse existing standard element 584821 (ColumnName='QtyATP', Name='Verfügbare Menge').
--     A new element cannot be created because AD_Element.ColumnName has a UNIQUE constraint.
--     The window label "Verfügbar (ATP)" / "ATP" is applied via AD_Field.AD_Name_ID in Task 5.

-- ============================================================
-- 3. AD_Column — one per view column
--    View tables: IsKey='N', IsUpdateable='N', IsAlwaysUpdateable='N'
--    M_Product_ID and M_Warehouse_ID are non-null in the view => IsMandatory='Y'.
--    WeekStartDate is non-null in the view => IsMandatory='Y'.
--    Qty columns can be NULL (COALESCE default 0 in SELECT, but no DB NOT NULL) => IsMandatory='N'.
--    PersonalDataCategory='NP' for all 6 columns (technical/aggregated stock data).
-- ============================================================

-- 3a. M_Product_ID  (AD_Reference_ID=30 Search, reuse element 454)
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592706 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 454, 30,
     'M_Product_ID', 'Produkt', 'de.metas.material.dispo',
     10, 'N', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:10:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:10:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592706
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(454);

-- 3b. M_Warehouse_ID  (AD_Reference_ID=30 Search, reuse element 459)
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592707 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:11:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:11:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 459, 30,
     'M_Warehouse_ID', 'Lager', 'de.metas.material.dispo',
     10, 'N', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:11:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:11:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592707
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(459);

-- 3c. WeekStartDate  (AD_Reference_ID=15 Date, new element 584938)
--     IsMandatory='Y': non-null in the view (generated by generate_series, always a date).
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592708 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:12:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:12:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 584938, 15,
     'WeekStartDate', 'Wochenbeginn (KW)', 'de.metas.material.dispo',
     29, 'N', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:12:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:12:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592708
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584938);

-- 3d. QtyExpectedShipments  (AD_Reference_ID=29 Quantity, new element 584939)
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592709 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:13:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:13:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 584939, 29,
     'QtyExpectedShipments', 'Erwartete Lieferungen', 'de.metas.material.dispo',
     131089, 'N', 'N', 'N', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:13:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:13:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592709
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584939);

-- 3e. QtyExpectedReceipts  (AD_Reference_ID=29 Quantity, new element 584940)
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592710 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:14:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:14:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 584940, 29,
     'QtyExpectedReceipts', 'Erwartete Wareneingänge', 'de.metas.material.dispo',
     131089, 'N', 'N', 'N', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:14:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:14:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592710
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584940);

-- 3f. QtyATP  (AD_Reference_ID=29 Quantity, reuse existing element 584821)
--     Column-level name stays as the element default "Verfügbare Menge".
--     The window label "Verfügbar (ATP)" is applied via AD_Field.AD_Name_ID in Task 5.
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592711 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-03 12:15:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 12:15:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 584821, 29,
     'QtyATP', 'Verfügbare Menge', 'de.metas.material.dispo',
     131089, 'N', 'N', 'N', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-03 12:15:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-03 12:15:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592711
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584821);