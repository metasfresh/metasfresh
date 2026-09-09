-- Carrier Product master-data window (3 tabs: header + 2 read-only allocation child tabs)
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element  584998 (window/header-tab caption "Lieferweg-Produkt"/"Carrier Product")
--   AD_Element  584999 (GoodsType child-tab caption "Warenarten"/"Goods Types")
--   AD_Element  585000 (Service child-tab caption "Services"/"Services")
--   AD_Window   542161
--   AD_Tab      549314 (header, Carrier_Product),  549315 (GoodsType alloc, RO),  549316 (Service alloc, RO)
--   AD_UI_Section 547823 (hdr), 547824 (goods), 547825 (svc)
--   AD_UI_Column  549557 (hdr left), 549558 (hdr right), 549559 (goods), 549560 (svc)
--   AD_UI_ElementGroup 555451 (primary), 555452 (flags), 555453 (org), 555454 (goods), 555455 (svc)
--   AD_Field    781128 Name, 781129 M_Shipper_ID, 781130 ExternalId, 781131 IsActive, 781132 AD_Org_ID, 781133 AD_Client_ID
--   AD_Field    781134 Carrier_Goods_Type_ID (goods tab), 781135 Carrier_Service_ID (svc tab)
--   AD_UI_Element 652274..652279 (hdr), 652280 (goods), 652281 (svc)
--   AD_Menu     542337
--
-- Reused tables/columns/elements:
--   AD_Table 542545 Carrier_Product; cols Name=591351 M_Shipper_ID=591349 ExternalId=591350
--                   IsActive=591345 AD_Org_ID=591342 AD_Client_ID=591341
--   AD_Table 542607 Carrier_Product_GoodsType_Alloc; Carrier_Goods_Type_ID=592647, parent FK Carrier_Product_ID=592646
--   AD_Table 542608 Carrier_Product_Service_Alloc;   Carrier_Service_ID=592657,   parent FK Carrier_Product_ID=592656
--   Elements: Name=469 M_Shipper_ID=455 ExternalId=543939 IsActive=348 AD_Org_ID=113 AD_Client_ID=102
--             Carrier_Goods_Type_ID=584112 Carrier_Service_ID=584113
--   Menu: AD_Tree_ID=10, parent 1000016 (Logistik), alongside the shipment-order windows

-- ============================================================
-- 1. AD_Element for window/header-tab + child-tab captions
-- ============================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584998 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Carrier_Product_Window', 'Lieferweg-Produkt', 'Lieferweg-Produkt', NULL, NULL, 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584998 /*From ID Server*/, 'N', e.Name, e.PrintName, e.Description, e.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND e.AD_Element_ID=584998
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=584998);

UPDATE AD_Element_Trl SET Name='Lieferweg-Produkt', PrintName='Lieferweg-Produkt', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584998 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Carrier Product', PrintName='Carrier Product', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584998 AND AD_Language='en_US';

-- GoodsType child-tab caption
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584999 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Carrier_Product_GoodsType_Alloc_Tab', 'Warenarten', 'Warenarten', NULL, NULL, 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584999 /*From ID Server*/, 'N', e.Name, e.PrintName, e.Description, e.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND e.AD_Element_ID=584999
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=584999);

UPDATE AD_Element_Trl SET Name='Warenarten', PrintName='Warenarten', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584999 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Goods Types', PrintName='Goods Types', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584999 AND AD_Language='en_US';

-- Service child-tab caption
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (585000 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Carrier_Product_Service_Alloc_Tab', 'Services', 'Services', NULL, NULL, 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585000 /*From ID Server*/, 'N', e.Name, e.PrintName, e.Description, e.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND e.AD_Element_ID=585000
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=585000);

UPDATE AD_Element_Trl SET Name='Services', PrintName='Services', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585000 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Services', PrintName='Services', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585000 AND AD_Language='en_US';

-- ============================================================
-- 2. AD_Window
-- ============================================================
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, IsDefault, IsBetaFunctionality, EntityType, AD_Element_ID, InternalName)
VALUES (542161 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Produkt', 'M', 'Y', 'N', 'N', 'D', 584998 /*From ID Server*/, 'Carrier_Product');

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542161 /*From ID Server*/, 'N', w.Name, w.Description, w.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Window w
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND w.AD_Window_ID=542161
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=542161);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584998 /*window element*/);

UPDATE AD_Table SET AD_Window_ID=542161 /*From ID Server*/,
     Updated=TO_TIMESTAMP('2026-06-15 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID=542545;

-- ============================================================
-- 3. AD_Tabs
-- ============================================================
-- 3a. Header tab (Carrier_Product, level 0)
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly, IsInsertRecord, IsAdvancedTab, IsSortTab, HasTree,
     EntityType, AD_Element_ID)
VALUES (549314 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Produkt', 542161 /*From ID Server*/, 542545 /*Carrier_Product*/, 0, 10,
     'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N',
     'D', 584998 /*From ID Server*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help, CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549314 /*From ID Server*/, 'N', t.Name, t.Description, t.Help, NULL,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549314
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=549314);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584998 /*header tab element*/);

-- 3b. GoodsType allocation child tab (level 1, read-only, no insert/delete)
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly, IsInsertRecord, IsAdvancedTab, IsSortTab, HasTree,
     EntityType, AD_Element_ID, AD_Column_ID, Parent_Column_ID)
VALUES (549315 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Warenarten', 542161 /*From ID Server*/, 542607 /*Carrier_Product_GoodsType_Alloc*/, 1, 20,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N',
     'D', 584999 /*From ID Server*/,
     592646 /*AD_Column_ID = Carrier_Product_ID FK on alloc table*/,
     591348 /*Parent_Column_ID = Carrier_Product_ID PK on parent Carrier_Product*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help, CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549315 /*From ID Server*/, 'N', t.Name, t.Description, t.Help, NULL,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:02:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:02:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549315
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=549315);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584999 /*goods tab element*/);

-- 3c. Service allocation child tab (level 1, read-only, no insert/delete)
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly, IsInsertRecord, IsAdvancedTab, IsSortTab, HasTree,
     EntityType, AD_Element_ID, AD_Column_ID, Parent_Column_ID)
VALUES (549316 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:02:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:02:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Services', 542161 /*From ID Server*/, 542608 /*Carrier_Product_Service_Alloc*/, 1, 30,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N',
     'D', 585000 /*From ID Server*/,
     592656 /*AD_Column_ID = Carrier_Product_ID FK on alloc table*/,
     591348 /*Parent_Column_ID = Carrier_Product_ID PK on parent Carrier_Product*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help, CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549316 /*From ID Server*/, 'N', t.Name, t.Description, t.Help, NULL,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:02:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:02:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549316
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=549316);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585000 /*service tab element*/);

-- ============================================================
-- 4. Header tab fields (AD_Tab_ID=549314)
-- ============================================================
-- 4a. Name (mandatory, primary)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781128 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Name', 549314 /*From ID Server*/, 591351 /*Name*/, 'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781128 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781128
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781128);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(469 /*Name element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781128;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781128);

-- 4b. M_Shipper_ID (FK lookup)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781129 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg', 549314 /*From ID Server*/, 591349 /*M_Shipper_ID*/, 'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781129 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781129
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781129);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(455 /*M_Shipper_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781129;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781129);

-- 4c. ExternalId
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781130 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Externe ID', 549314 /*From ID Server*/, 591350 /*ExternalId*/, 'Y', 30, 'Y', 30,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781130 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781130
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781130);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(543939 /*ExternalId element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781130;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781130);

-- 4d. IsActive (flags group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781131 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv', 549314 /*From ID Server*/, 591345 /*IsActive*/, 'Y', 40, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781131 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781131
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781131);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781131;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781131);

-- 4e. AD_Org_ID (org group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781132 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Organisation', 549314 /*From ID Server*/, 591342 /*AD_Org_ID*/, 'Y', 50, 'Y', 40,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781132 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781132
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781132);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113 /*AD_Org_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781132;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781132);

-- 4f. AD_Client_ID (org group, last)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781133 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant', 549314 /*From ID Server*/, 591341 /*AD_Client_ID*/, 'Y', 60, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781133 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781133
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781133);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102 /*AD_Client_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781133;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781133);

-- ============================================================
-- 5. Child tab fields
-- ============================================================
-- 5a. GoodsType tab: Carrier_Goods_Type_ID (read-only)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly, EntityType)
VALUES (781134 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Materialzuordnung je Lieferweg', 549315 /*GoodsType tab*/, 592647 /*Carrier_Goods_Type_ID*/, 'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'Y', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781134 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:04:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:04:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781134
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781134);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584112 /*Carrier_Goods_Type_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781134;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781134);

-- 5b. Service tab: Carrier_Service_ID (read-only)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly, EntityType)
VALUES (781135 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:04:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:04:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Servicekatalog', 549316 /*Service tab*/, 592657 /*Carrier_Service_ID*/, 'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'Y', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781135 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:04:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:04:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781135
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781135);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584113 /*Carrier_Service_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781135;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781135);

-- ============================================================
-- 6. UI structure — header tab (2-column), child tabs (single column)
-- ============================================================
-- Header section + left/right columns
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Name, Value)
VALUES (547823 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549314 /*header tab*/, 10, 'main', 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549557 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547823, 10);

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549558 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547823, 20);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555451 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549557 /*left*/, 10, 'primary', 'default');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555452 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549558 /*right*/, 10, NULL, 'flags');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555453 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549558 /*right*/, 20, NULL, 'default');

-- GoodsType child tab UI structure (single column / single group)
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Name, Value)
VALUES (547824 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549315 /*goods tab*/, 10, 'main', 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549559 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547824, 10);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555454 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549559, 10, 'primary', 'default');

-- Service child tab UI structure (single column / single group)
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Name, Value)
VALUES (547825 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549316 /*svc tab*/, 10, 'main', 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549560 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547825, 10);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555455 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:05:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:05:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549560, 10, 'primary', 'default');

-- ============================================================
-- 7. AD_UI_Elements (pair every AD_Field)
-- ============================================================
-- Header: Name (primary, form+grid)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781128, 0, 549314, 555451, 652274 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Name', 10, 10, 0, TO_TIMESTAMP('2026-06-15 10:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Header: M_Shipper_ID (primary, form+grid)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781129, 0, 549314, 555451, 652275 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Lieferweg', 20, 20, 0, TO_TIMESTAMP('2026-06-15 10:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Header: ExternalId (primary, form+grid)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781130, 0, 549314, 555451, 652276 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Externe ID', 30, 30, 0, TO_TIMESTAMP('2026-06-15 10:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Header: IsActive (flags, form only)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781131, 0, 549314, 555452, 652277 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Aktiv', 10, 0, 0, TO_TIMESTAMP('2026-06-15 10:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Header: AD_Org_ID (org, form+grid last)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781132, 0, 549314, 555453, 652278 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Organisation', 10, 40, 0, TO_TIMESTAMP('2026-06-15 10:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Header: AD_Client_ID (org, form only — Client not in grid)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781133, 0, 549314, 555453, 652279 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-06-15 10:06:05', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- GoodsType tab: Carrier_Goods_Type_ID
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781134, 0, 549315, 555454, 652280 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Materialzuordnung je Lieferweg', 10, 10, 0, TO_TIMESTAMP('2026-06-15 10:06:10', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Service tab: Carrier_Service_ID
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781135, 0, 549316, 555455, 652281 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 10:06:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Lieferweg-Servicekatalog', 10, 10, 0, TO_TIMESTAMP('2026-06-15 10:06:20', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Default grid sort: master data sorts by Name ascending
UPDATE AD_Field SET SortNo=1, Updated=TO_TIMESTAMP('2026-06-15 10:06:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781128 /*Carrier_Product header Name*/;

-- ============================================================
-- 8. Filter config — make Name + M_Shipper_ID default filters with proper order
-- ============================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,
     Updated=TO_TIMESTAMP('2026-06-15 10:07:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591351 /*Carrier_Product.Name*/;

UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,
     Updated=TO_TIMESTAMP('2026-06-15 10:07:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591349 /*Carrier_Product.M_Shipper_ID*/;

-- ============================================================
-- 9. AD_Menu + tree placement (parent 1000016 Logistik, alongside the shipment-order windows)
-- ============================================================
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary, EntityType, AD_Element_ID, InternalName)
VALUES (542337 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Produkt', 'W', 542161 /*From ID Server*/, 'N', 'D', 584998 /*From ID Server*/, 'Carrier_Product_Window');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542337 /*From ID Server*/, 'N', m.Name, m.Description,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Menu m
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND m.AD_Menu_ID=542337
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=542337);

SELECT update_menu_translation_from_ad_element(584998 /*element id*/, NULL /*all languages*/);

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
VALUES (0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 10:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 10:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10 /*menu tree*/, 542337 /*new menu*/, 1000016 /*Logistik*/, 78);
