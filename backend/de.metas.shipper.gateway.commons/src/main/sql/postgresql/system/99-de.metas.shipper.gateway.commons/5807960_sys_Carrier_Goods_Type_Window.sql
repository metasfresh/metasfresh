-- Carrier Goods Type master-data window (single header tab)
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element  585001 (window/tab caption "Lieferweg-Warenart"/"Carrier Goods Type")
--   AD_Window   542162
--   AD_Tab      549317 (header, Carrier_Goods_Type)
--   AD_UI_Section 547826 ; AD_UI_Column 549561 (left), 549562 (right)
--   AD_UI_ElementGroup 555456 (primary), 555457 (flags), 555458 (org)
--   AD_Field    781136 Name, 781137 M_Shipper_ID, 781138 ExternalId, 781139 IsActive, 781140 AD_Org_ID, 781141 AD_Client_ID
--   AD_UI_Element 652282..652287
--   AD_Menu     542338
--
-- Reused: AD_Table 542542 Carrier_Goods_Type; cols Name=591315 M_Shipper_ID=591313 ExternalId=591314
--                   IsActive=591309 AD_Org_ID=591306 AD_Client_ID=591305
--   Elements: Name=469 M_Shipper_ID=455 ExternalId=543939 IsActive=348 AD_Org_ID=113 AD_Client_ID=102
--   Menu: AD_Tree_ID=10, parent 1000016 (Logistik)

-- ============================================================
-- 1. AD_Element for window/tab caption
-- ============================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (585001 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Carrier_Goods_Type_Window', 'Lieferweg-Warenart', 'Lieferweg-Warenart', NULL, NULL, 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585001 /*From ID Server*/, 'N', e.Name, e.PrintName, e.Description, e.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND e.AD_Element_ID=585001
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=585001);

UPDATE AD_Element_Trl SET Name='Lieferweg-Warenart', PrintName='Lieferweg-Warenart', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585001 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Carrier Goods Type', PrintName='Carrier Goods Type', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585001 AND AD_Language='en_US';

-- ============================================================
-- 2. AD_Window
-- ============================================================
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, IsDefault, IsBetaFunctionality, EntityType, AD_Element_ID, InternalName)
VALUES (542162 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Warenart', 'M', 'Y', 'N', 'N', 'D', 585001 /*From ID Server*/, 'Carrier_Goods_Type');

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542162 /*From ID Server*/, 'N', w.Name, w.Description, w.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Window w
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND w.AD_Window_ID=542162
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=542162);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585001 /*window element*/);

UPDATE AD_Table SET AD_Window_ID=542162 /*From ID Server*/,
     Updated=TO_TIMESTAMP('2026-06-15 11:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID=542542;

-- ============================================================
-- 3. AD_Tab (header, level 0)
-- ============================================================
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly, IsInsertRecord, IsAdvancedTab, IsSortTab, HasTree,
     EntityType, AD_Element_ID)
VALUES (549317 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Warenart', 542162 /*From ID Server*/, 542542 /*Carrier_Goods_Type*/, 0, 10,
     'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N',
     'D', 585001 /*From ID Server*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help, CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549317 /*From ID Server*/, 'N', t.Name, t.Description, t.Help, NULL,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549317
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=549317);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585001 /*header tab element*/);

-- ============================================================
-- 4. Header tab fields (AD_Tab_ID=549317)
-- ============================================================
-- 4a. Name
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781136 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Name', 549317 /*From ID Server*/, 591315 /*Name*/, 'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781136 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781136
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781136);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(469 /*Name element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781136;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781136);

-- 4b. M_Shipper_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781137 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg', 549317 /*From ID Server*/, 591313 /*M_Shipper_ID*/, 'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781137 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781137
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781137);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(455 /*M_Shipper_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781137;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781137);

-- 4c. ExternalId
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781138 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Externe ID', 549317 /*From ID Server*/, 591314 /*ExternalId*/, 'Y', 30, 'Y', 30,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781138 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781138
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781138);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(543939 /*ExternalId element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781138;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781138);

-- 4d. IsActive (flags group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781139 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv', 549317 /*From ID Server*/, 591309 /*IsActive*/, 'Y', 40, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781139 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781139
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781139);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781139;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781139);

-- 4e. AD_Org_ID (org group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781140 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Organisation', 549317 /*From ID Server*/, 591306 /*AD_Org_ID*/, 'Y', 50, 'Y', 40,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781140 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781140
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781140);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113 /*AD_Org_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781140;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781140);

-- 4f. AD_Client_ID (org group, last)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781141 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant', 549317 /*From ID Server*/, 591305 /*AD_Client_ID*/, 'Y', 60, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781141 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781141
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781141);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102 /*AD_Client_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781141;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781141);

-- ============================================================
-- 5. UI structure — 2-column header layout
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Name, Value)
VALUES (547826 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549317, 10, 'main', 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549561 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547826, 10);

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549562 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547826, 20);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555456 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549561 /*left*/, 10, 'primary', 'default');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555457 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549562 /*right*/, 10, NULL, 'flags');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555458 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549562 /*right*/, 20, NULL, 'default');

-- ============================================================
-- 6. AD_UI_Elements
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781136, 0, 549317, 555456, 652282 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 11:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Name', 10, 10, 0, TO_TIMESTAMP('2026-06-15 11:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781137, 0, 549317, 555456, 652283 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 11:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Lieferweg', 20, 20, 0, TO_TIMESTAMP('2026-06-15 11:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781138, 0, 549317, 555456, 652284 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 11:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Externe ID', 30, 30, 0, TO_TIMESTAMP('2026-06-15 11:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781139, 0, 549317, 555457, 652285 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 11:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Aktiv', 10, 0, 0, TO_TIMESTAMP('2026-06-15 11:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781140, 0, 549317, 555458, 652286 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 11:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Organisation', 10, 40, 0, TO_TIMESTAMP('2026-06-15 11:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781141, 0, 549317, 555458, 652287 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 11:06:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-06-15 11:06:05', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Default grid sort: master data sorts by Name ascending
UPDATE AD_Field SET SortNo=1, Updated=TO_TIMESTAMP('2026-06-15 11:06:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781136 /*Carrier_Goods_Type header Name*/;

-- ============================================================
-- 7. Filter config — Name + M_Shipper_ID default filters
-- ============================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,
     Updated=TO_TIMESTAMP('2026-06-15 11:07:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591315 /*Carrier_Goods_Type.Name*/;

UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,
     Updated=TO_TIMESTAMP('2026-06-15 11:07:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591313 /*Carrier_Goods_Type.M_Shipper_ID*/;

-- ============================================================
-- 8. AD_Menu + tree placement
-- ============================================================
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary, EntityType, AD_Element_ID, InternalName)
VALUES (542338 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Warenart', 'W', 542162 /*From ID Server*/, 'N', 'D', 585001 /*From ID Server*/, 'Carrier_Goods_Type_Window');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542338 /*From ID Server*/, 'N', m.Name, m.Description,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Menu m
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND m.AD_Menu_ID=542338
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=542338);

SELECT update_menu_translation_from_ad_element(585001 /*element id*/, NULL /*all languages*/);

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
VALUES (0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 11:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 11:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10 /*menu tree*/, 542338 /*new menu*/, 1000016 /*Logistik*/, 88);
