-- Carrier Service master-data window (single header tab)
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element  585002 (window/tab caption "Lieferweg-Service"/"Carrier Service")
--   AD_Window   542163
--   AD_Tab      549318 (header, Carrier_Service)
--   AD_UI_Section 547827 ; AD_UI_Column 549563 (left), 549564 (right)
--   AD_UI_ElementGroup 555459 (primary), 555460 (flags), 555461 (org)
--   AD_Field    781142 Name, 781143 M_Shipper_ID, 781144 ExternalId, 781145 IsActive, 781146 AD_Org_ID, 781147 AD_Client_ID
--   AD_UI_Element 652288..652293
--   AD_Menu     542339
--
-- Reused: AD_Table 542543 Carrier_Service; cols Name=591326 M_Shipper_ID=591324 ExternalId=591325
--                   IsActive=591320 AD_Org_ID=591317 AD_Client_ID=591316
--   Elements: Name=469 M_Shipper_ID=455 ExternalId=543939 IsActive=348 AD_Org_ID=113 AD_Client_ID=102
--   Menu: AD_Tree_ID=10, parent 1000016 (Logistik)

-- ============================================================
-- 1. AD_Element for window/tab caption
-- ============================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (585002 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Carrier_Service_Window', 'Lieferweg-Service', 'Lieferweg-Service', NULL, NULL, 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585002 /*From ID Server*/, 'N', e.Name, e.PrintName, e.Description, e.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND e.AD_Element_ID=585002
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=585002);

UPDATE AD_Element_Trl SET Name='Lieferweg-Service', PrintName='Lieferweg-Service', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585002 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Carrier Service', PrintName='Carrier Service', IsTranslated='Y',
     Updated=TO_TIMESTAMP('2026-06-15 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585002 AND AD_Language='en_US';

-- ============================================================
-- 2. AD_Window
-- ============================================================
INSERT INTO AD_Window (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, IsDefault, IsBetaFunctionality, EntityType, AD_Element_ID, InternalName)
VALUES (542163 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Service', 'M', 'Y', 'N', 'N', 'D', 585002 /*From ID Server*/, 'Carrier_Service');

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542163 /*From ID Server*/, 'N', w.Name, w.Description, w.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Window w
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND w.AD_Window_ID=542163
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=542163);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585002 /*window element*/);

UPDATE AD_Table SET AD_Window_ID=542163 /*From ID Server*/,
     Updated=TO_TIMESTAMP('2026-06-15 12:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID=542543;

-- ============================================================
-- 3. AD_Tab (header, level 0)
-- ============================================================
INSERT INTO AD_Tab (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly, IsInsertRecord, IsAdvancedTab, IsSortTab, HasTree,
     EntityType, AD_Element_ID)
VALUES (549318 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Service', 542163 /*From ID Server*/, 542543 /*Carrier_Service*/, 0, 10,
     'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N',
     'D', 585002 /*From ID Server*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help, CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549318 /*From ID Server*/, 'N', t.Name, t.Description, t.Help, NULL,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549318
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=549318);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585002 /*header tab element*/);

-- ============================================================
-- 4. Header tab fields (AD_Tab_ID=549318)
-- ============================================================
-- 4a. Name
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781142 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Name', 549318 /*From ID Server*/, 591326 /*Name*/, 'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781142 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781142
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781142);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(469 /*Name element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781142;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781142);

-- 4b. M_Shipper_ID
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781143 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg', 549318 /*From ID Server*/, 591324 /*M_Shipper_ID*/, 'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781143 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781143
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781143);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(455 /*M_Shipper_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781143;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781143);

-- 4c. ExternalId
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781144 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Externe ID', 549318 /*From ID Server*/, 591325 /*ExternalId*/, 'Y', 30, 'Y', 30,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781144 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781144
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781144);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(543939 /*ExternalId element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781144;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781144);

-- 4d. IsActive (flags group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781145 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv', 549318 /*From ID Server*/, 591320 /*IsActive*/, 'Y', 40, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781145 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781145
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781145);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781145;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781145);

-- 4e. AD_Org_ID (org group)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781146 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Organisation', 549318 /*From ID Server*/, 591317 /*AD_Org_ID*/, 'Y', 50, 'Y', 40,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781146 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781146
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781146);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113 /*AD_Org_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781146;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781146);

-- 4f. AD_Client_ID (org group, last)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781147 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant', 549318 /*From ID Server*/, 591316 /*AD_Client_ID*/, 'Y', 60, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781147 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781147
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781147);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102 /*AD_Client_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781147;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781147);

-- ============================================================
-- 5. UI structure — 2-column header layout
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Name, Value)
VALUES (547827 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549318, 10, 'main', 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549563 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547827, 10);

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES (549564 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547827, 20);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555459 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549563 /*left*/, 10, 'primary', 'default');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555460 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549564 /*right*/, 10, NULL, 'flags');

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555461 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549564 /*right*/, 20, NULL, 'default');

-- ============================================================
-- 6. AD_UI_Elements
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781142, 0, 549318, 555459, 652288 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 12:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Name', 10, 10, 0, TO_TIMESTAMP('2026-06-15 12:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781143, 0, 549318, 555459, 652289 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 12:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Lieferweg', 20, 20, 0, TO_TIMESTAMP('2026-06-15 12:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781144, 0, 549318, 555459, 652290 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 12:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Externe ID', 30, 30, 0, TO_TIMESTAMP('2026-06-15 12:06:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781145, 0, 549318, 555460, 652291 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 12:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Aktiv', 10, 0, 0, TO_TIMESTAMP('2026-06-15 12:06:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781146, 0, 549318, 555461, 652292 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 12:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N',
     'Organisation', 10, 40, 0, TO_TIMESTAMP('2026-06-15 12:06:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781147, 0, 549318, 555461, 652293 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-15 12:06:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N',
     'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-06-15 12:06:05', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Default grid sort: master data sorts by Name ascending
UPDATE AD_Field SET SortNo=1, Updated=TO_TIMESTAMP('2026-06-15 12:06:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781142 /*Carrier_Service header Name*/;

-- ============================================================
-- 7. Filter config — Name + M_Shipper_ID default filters
-- ============================================================
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,
     Updated=TO_TIMESTAMP('2026-06-15 12:07:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591326 /*Carrier_Service.Name*/;

UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,
     Updated=TO_TIMESTAMP('2026-06-15 12:07:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591324 /*Carrier_Service.M_Shipper_ID*/;

-- ============================================================
-- 8. AD_Menu + tree placement
-- ============================================================
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary, EntityType, AD_Element_ID, InternalName)
VALUES (542339 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferweg-Service', 'W', 542163 /*From ID Server*/, 'N', 'D', 585002 /*From ID Server*/, 'Carrier_Service_Window');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542339 /*From ID Server*/, 'N', m.Name, m.Description,
     0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:08:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Menu m
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND m.AD_Menu_ID=542339
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=542339);

SELECT update_menu_translation_from_ad_element(585002 /*element id*/, NULL /*all languages*/);

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
VALUES (0, 0, 'Y',
     TO_TIMESTAMP('2026-06-15 12:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-15 12:08:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10 /*menu tree*/, 542339 /*new menu*/, 1000016 /*Logistik*/, 98);
