-- Restructures the Order Board window (581036) to add an Overview parent tab backed by
-- M_Picking_OrderBoard_Overview_v, and converts the three W/K/P tabs to child sub-tabs
-- (TabLevel=1) linked via M_Picking_OrderBoard_Overview_v_ID.
--
-- Before this script: window has 3 peer tabs (TabLevel=0, SeqNo=10/20/30).
-- After this script:
--   TabLevel=0  SeqNo=10  Übersicht (Overview)     → M_Picking_OrderBoard_Overview_v
--   TabLevel=1  SeqNo=20  Wartend (W)               → M_Picking_OrderBoard_v, filtered W
--   TabLevel=1  SeqNo=30  In Kommissionierung (K)   → M_Picking_OrderBoard_v, filtered K
--   TabLevel=1  SeqNo=40  Packen (P)                → M_Picking_OrderBoard_v, filtered P
--
-- IDs allocated from idserver.metas.de on 2026-07-02:
--   AD_Element  581170   (tab name: Übersicht / Overview)
--   AD_Tab      581171   (Overview tab)
--   AD_Field    581172   (ProductValue  in overview tab)
--   AD_Field    581173   (ProductName   in overview tab)
--   AD_Field    581174   (C_UOM_ID      in overview tab)
--   AD_Field    581175   (DeliveryDate  in overview tab)
--   AD_Field    581176   (CountryName   in overview tab)
--   AD_Field    581177   (C_Country_ID  in overview tab, form-only filter)
--   AD_Field    581178   (QtyWaiting    in overview tab)
--   AD_Field    581179   (QtyPicking    in overview tab)
--   AD_Field    581180   (QtyPacking    in overview tab)
--   AD_Field    581181   (QtyTotal      in overview tab)
--   AD_Field    581182   (OrderLineCount in overview tab)
--   AD_UI_Section    581183   (overview tab)
--   AD_UI_Column     581184   (overview tab)
--   AD_UI_ElementGroup 581185 (overview tab)
--   AD_UI_Element  581186   (ProductValue)
--   AD_UI_Element  581187   (ProductName)
--   AD_UI_Element  581188   (C_UOM_ID)
--   AD_UI_Element  581189   (DeliveryDate)
--   AD_UI_Element  581190   (CountryName)
--   AD_UI_Element  581191   (C_Country_ID, form-only)
--   AD_UI_Element  581192   (QtyWaiting)
--   AD_UI_Element  581193   (QtyPicking)
--   AD_UI_Element  581194   (QtyPacking)
--   AD_UI_Element  581195   (QtyTotal)
--   AD_UI_Element  581196   (OrderLineCount)
-- Reused:
--   AD_Window   581036  (Auftrags-Board)
--   AD_Table    581144  (M_Picking_OrderBoard_Overview_v)
--   AD_Table    542622  (M_Picking_OrderBoard_v)
--   AD_Tab      581037/581038/581039 (W/K/P, updated to TabLevel=1)
--   AD_Column   581169  (M_Picking_OrderBoard_Overview_v_ID FK in M_Picking_OrderBoard_v)
--   AD_Column   581151(ProductValue) 581152(ProductName) 581153(C_UOM_ID)
--               581154(DeliveryDate) 581155(C_Country_ID) 581156(CountryName)
--               581157(QtyWaiting)  581158(QtyPicking)   581159(QtyPacking)
--               581160(QtyTotal)    581161(OrderLineCount)

-- ============================================================
-- 1. Register the Overview view as the window's primary backing table
-- ============================================================
UPDATE AD_Table
SET    AD_Window_ID = 581036,
       Updated      = TO_TIMESTAMP('2026-07-02 22:35:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Table_ID = 581144
;

-- ============================================================
-- 2. AD_Element for the Overview tab name
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (581170/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_Uebersicht', 'Übersicht', 'Übersicht', 'D')
;

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581170, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 22:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-02 22:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 581170
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 581170)
;

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Übersicht', PrintName = 'Übersicht',
       Updated = TO_TIMESTAMP('2026-07-02 22:35:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581170 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'Overview', PrintName = 'Overview',
       Updated = TO_TIMESTAMP('2026-07-02 22:35:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581170 AND AD_Language = 'en_US'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581170, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581170, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581170, 'en_US');

-- ============================================================
-- 3. Overview AD_Tab (TabLevel=0, SeqNo=10)
-- ============================================================
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID)
VALUES
    (581171/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Übersicht', 581036, 581144, 0, 10,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 581170)
;

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581171, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 22:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-02 22:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 581171
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 581171)
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581170, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581170, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581170, 'en_US');

-- ============================================================
-- 4. Promote W/K/P tabs to TabLevel=1 child tabs
--    Parent_Column_ID = 581169 (M_Picking_OrderBoard_Overview_v_ID in M_Picking_OrderBoard_v)
--    SeqNo shifted: 20 / 30 / 40 (so they follow the overview at SeqNo=10)
-- ============================================================
UPDATE AD_Tab
SET    TabLevel          = 1,
       Parent_Column_ID  = 581169,
       SeqNo             = 20,
       Updated           = TO_TIMESTAMP('2026-07-02 22:35:30', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Tab_ID = 581037 -- Wartend (W)
;

UPDATE AD_Tab
SET    TabLevel          = 1,
       Parent_Column_ID  = 581169,
       SeqNo             = 30,
       Updated           = TO_TIMESTAMP('2026-07-02 22:35:31', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Tab_ID = 581038 -- In Kommissionierung (K)
;

UPDATE AD_Tab
SET    TabLevel          = 1,
       Parent_Column_ID  = 581169,
       SeqNo             = 40,
       Updated           = TO_TIMESTAMP('2026-07-02 22:35:32', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Tab_ID = 581039 -- Packen (P)
;

-- ============================================================
-- 5. AD_Fields for the Overview tab
-- Grid: ProductValue(10) ProductName(20) C_UOM_ID(30) DeliveryDate(40)
--       CountryName(50) QtyWaiting(60) QtyPicking(70) QtyPacking(80) QtyTotal(90) OrderLineCount(100)
-- Form: same + C_Country_ID (form-only, not in grid)
-- ============================================================

-- ProductValue
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581172/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktnummer', 581171, 581151 /*ProductValue*/, 'Y', 10, 'Y', 10, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581172, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581172
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581172)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675 /*ProductValue*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581172;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581172);

-- ProductName
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581173/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktname', 581171, 581152 /*ProductName*/, 'Y', 20, 'Y', 20, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581173, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581173
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581173)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659 /*ProductName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581173;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581173);

-- C_UOM_ID
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581174/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Maßeinheit', 581171, 581153 /*C_UOM_ID*/, 'Y', 30, 'Y', 30, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581174, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581174
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581174)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215 /*C_UOM_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581174;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581174);

-- DeliveryDate
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581175/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Lieferdatum', 581171, 581154 /*DeliveryDate*/, 'Y', 40, 'Y', 40, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581175, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581175
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581175)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376 /*DeliveryDate*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581175;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581175);

-- CountryName (text, shown in grid)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581176/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Landname', 581171, 581156 /*CountryName*/, 'Y', 50, 'Y', 50, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581176, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581176
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581176)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585 /*CountryName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581176;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581176);

-- C_Country_ID (filter field, form-only — text CountryName covers grid)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581177/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Land', 581171, 581155 /*C_Country_ID*/, 'Y', 55, 'N', 0, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581177, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581177
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581177)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192 /*C_Country_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581177;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581177);

-- QtyWaiting
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581178/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge wartend', 581171, 581157 /*QtyWaiting*/, 'Y', 60, 'Y', 60, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581178, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581178
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581178)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(581146 /*QtyWaiting*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581178;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581178);

-- QtyPicking
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581179/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge in Kommissionierung', 581171, 581158 /*QtyPicking*/, 'Y', 70, 'Y', 70, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581179, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581179
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581179)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(581147 /*QtyPicking*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581179;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581179);

-- QtyPacking
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581180/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge packen', 581171, 581159 /*QtyPacking*/, 'Y', 80, 'Y', 80, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581180, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581180
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581180)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(581148 /*QtyPacking*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581180;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581180);

-- QtyTotal
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581181/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge gesamt', 581171, 581160 /*QtyTotal*/, 'Y', 90, 'Y', 90, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581181, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581181
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581181)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061 /*QtyTotal*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581181;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581181);

-- OrderLineCount
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581182/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Auftragszeilen', 581171, 581161 /*OrderLineCount*/, 'Y', 100, 'Y', 100, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581182, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581182
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581182)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062 /*OrderLineCount*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581182;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581182);

-- ============================================================
-- 6. AD_UI layout for the Overview tab
-- ============================================================
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Value)
VALUES
    (581183/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:38:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:38:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     581171, 10, 'main')
;

INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES
    (581184/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:38:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:38:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     581183, 10)
;

INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (581185/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:38:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:38:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     581184, 10, 'primary', 'default')
;

-- UI Elements for the Overview tab
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581172 /*ProductValue*/, 0, 581171, 581185, 581186/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0,
    TO_TIMESTAMP('2026-07-02 22:38:30', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581173 /*ProductName*/, 0, 581171, 581185, 581187/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:31', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Produktname', 20, 20, 0,
    TO_TIMESTAMP('2026-07-02 22:38:31', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581174 /*C_UOM_ID*/, 0, 581171, 581185, 581188/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0,
    TO_TIMESTAMP('2026-07-02 22:38:32', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581175 /*DeliveryDate*/, 0, 581171, 581185, 581189/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:33', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0,
    TO_TIMESTAMP('2026-07-02 22:38:33', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581176 /*CountryName*/, 0, 581171, 581185, 581190/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Landname', 50, 50, 0,
    TO_TIMESTAMP('2026-07-02 22:38:34', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581177 /*C_Country_ID*/, 0, 581171, 581185, 581191/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'N', 'N', 'Land', 55, 0, 0,
    TO_TIMESTAMP('2026-07-02 22:38:35', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581178 /*QtyWaiting*/, 0, 581171, 581185, 581192/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Menge wartend', 60, 60, 0,
    TO_TIMESTAMP('2026-07-02 22:38:36', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581179 /*QtyPicking*/, 0, 581171, 581185, 581193/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Menge in Kommissionierung', 70, 70, 0,
    TO_TIMESTAMP('2026-07-02 22:38:37', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581180 /*QtyPacking*/, 0, 581171, 581185, 581194/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:38', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Menge packen', 80, 80, 0,
    TO_TIMESTAMP('2026-07-02 22:38:38', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581181 /*QtyTotal*/, 0, 581171, 581185, 581195/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:39', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Menge gesamt', 90, 90, 0,
    TO_TIMESTAMP('2026-07-02 22:38:39', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581182 /*OrderLineCount*/, 0, 581171, 581185, 581196/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Auftragszeilen', 100, 100, 0,
    TO_TIMESTAMP('2026-07-02 22:38:40', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
