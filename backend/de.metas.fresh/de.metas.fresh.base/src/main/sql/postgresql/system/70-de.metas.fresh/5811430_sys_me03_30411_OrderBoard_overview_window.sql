-- Restructures the Order Board window (542168) to add an Overview parent tab backed by
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
--   AD_Element  585098   (tab name: Übersicht / Overview)
--   AD_Tab      549338   (Overview tab)
--   AD_Field    781407   (ProductValue  in overview tab)
--   AD_Field    781408   (ProductName   in overview tab)
--   AD_Field    781409   (C_UOM_ID      in overview tab)
--   AD_Field    781410   (DeliveryDate  in overview tab)
--   AD_Field    781411   (CountryName   in overview tab)
--   AD_Field    781412   (C_Country_ID  in overview tab, form-only filter)
--   AD_Field    781413   (QtyWaiting    in overview tab)
--   AD_Field    781414   (QtyPicking    in overview tab)
--   AD_Field    781415   (QtyPacking    in overview tab)
--   AD_Field    781416   (QtyTotal      in overview tab)
--   AD_Field    781417   (OrderLineCount in overview tab)
--   AD_UI_Section    547847   (overview tab)
--   AD_UI_Column     549588   (overview tab)
--   AD_UI_ElementGroup 555492 (overview tab)
--   AD_UI_Element  652523   (ProductValue)
--   AD_UI_Element  652524   (ProductName)
--   AD_UI_Element  652525   (C_UOM_ID)
--   AD_UI_Element  652526   (DeliveryDate)
--   AD_UI_Element  652527   (CountryName)
--   AD_UI_Element  652528   (C_Country_ID, form-only)
--   AD_UI_Element  652529   (QtyWaiting)
--   AD_UI_Element  652530   (QtyPicking)
--   AD_UI_Element  652531   (QtyPacking)
--   AD_UI_Element  652532   (QtyTotal)
--   AD_UI_Element  652533   (OrderLineCount)
-- Reused:
--   AD_Window   542168  (Auftrags-Board)
--   AD_Table    542626  (M_Picking_OrderBoard_Overview_v)
--   AD_Table    542622  (M_Picking_OrderBoard_v)
--   AD_Tab      549335/549336/549337 (W/K/P, updated to TabLevel=1)
--   AD_Column   592960  (M_Picking_OrderBoard_Overview_v_ID FK in M_Picking_OrderBoard_v)
--   AD_Column   592942(ProductValue) 592943(ProductName) 592944(C_UOM_ID)
--               592945(DeliveryDate) 592946(C_Country_ID) 592947(CountryName)
--               592948(QtyWaiting)  592949(QtyPicking)   592950(QtyPacking)
--               592951(QtyTotal)    592952(OrderLineCount)

-- ============================================================
-- 1. Register the Overview view as the window's primary backing table
-- ============================================================
UPDATE AD_Table
SET    AD_Window_ID = 542168,
       Updated      = TO_TIMESTAMP('2026-07-02 22:35:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Table_ID = 542626
;

-- ============================================================
-- 2. AD_Element for the Overview tab name
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (585098/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_Uebersicht', 'Übersicht', 'Übersicht', 'D')
;

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585098, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 22:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-02 22:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 585098
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585098)
;

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Übersicht', PrintName = 'Übersicht',
       Updated = TO_TIMESTAMP('2026-07-02 22:35:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585098 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'Overview', PrintName = 'Overview',
       Updated = TO_TIMESTAMP('2026-07-02 22:35:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585098 AND AD_Language = 'en_US'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585098, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585098, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585098, 'en_US');

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
    (549338/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Übersicht', 542168, 542626, 0, 10,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 585098)
;

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549338, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 22:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-02 22:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549338
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549338)
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585098, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585098, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585098, 'en_US');

-- ============================================================
-- 4. Promote W/K/P tabs to TabLevel=1 child tabs
--    Parent_Column_ID = 592960 (M_Picking_OrderBoard_Overview_v_ID in M_Picking_OrderBoard_v)
--    SeqNo shifted: 20 / 30 / 40 (so they follow the overview at SeqNo=10)
-- ============================================================
UPDATE AD_Tab
SET    TabLevel          = 1,
       Parent_Column_ID  = 592960,
       SeqNo             = 20,
       Updated           = TO_TIMESTAMP('2026-07-02 22:35:30', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Tab_ID = 549335 -- Wartend (W)
;

UPDATE AD_Tab
SET    TabLevel          = 1,
       Parent_Column_ID  = 592960,
       SeqNo             = 30,
       Updated           = TO_TIMESTAMP('2026-07-02 22:35:31', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Tab_ID = 549336 -- In Kommissionierung (K)
;

UPDATE AD_Tab
SET    TabLevel          = 1,
       Parent_Column_ID  = 592960,
       SeqNo             = 40,
       Updated           = TO_TIMESTAMP('2026-07-02 22:35:32', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Tab_ID = 549337 -- Packen (P)
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
VALUES (781407/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktnummer', 549338, 592942 /*ProductValue*/, 'Y', 10, 'Y', 10, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781407, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781407
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781407)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675 /*ProductValue*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781407;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781407);

-- ProductName
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781408/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktname', 549338, 592943 /*ProductName*/, 'Y', 20, 'Y', 20, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781408, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781408
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781408)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659 /*ProductName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781408;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781408);

-- C_UOM_ID
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781409/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Maßeinheit', 549338, 592944 /*C_UOM_ID*/, 'Y', 30, 'Y', 30, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781409, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781409
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781409)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215 /*C_UOM_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781409;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781409);

-- DeliveryDate
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781410/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Lieferdatum', 549338, 592945 /*DeliveryDate*/, 'Y', 40, 'Y', 40, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781410, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781410
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781410)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376 /*DeliveryDate*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781410;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781410);

-- CountryName (text, shown in grid)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781411/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Landname', 549338, 592947 /*CountryName*/, 'Y', 50, 'Y', 50, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781411, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781411
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781411)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585 /*CountryName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781411;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781411);

-- C_Country_ID (filter field, form-only — text CountryName covers grid)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781412/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Land', 549338, 592946 /*C_Country_ID*/, 'Y', 55, 'N', 0, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781412, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:36:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:36:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781412
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781412)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192 /*C_Country_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781412;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781412);

-- QtyWaiting
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781413/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge wartend', 549338, 592948 /*QtyWaiting*/, 'Y', 60, 'Y', 60, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781413, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781413
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781413)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585095 /*QtyWaiting*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781413;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781413);

-- QtyPicking
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781414/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge in Kommissionierung', 549338, 592949 /*QtyPicking*/, 'Y', 70, 'Y', 70, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781414, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781414
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781414)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585096 /*QtyPicking*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781414;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781414);

-- QtyPacking
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781415/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge packen', 549338, 592950 /*QtyPacking*/, 'Y', 80, 'Y', 80, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781415, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781415
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781415)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585097 /*QtyPacking*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781415;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781415);

-- QtyTotal
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781416/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge gesamt', 549338, 592951 /*QtyTotal*/, 'Y', 90, 'Y', 90, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781416, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781416
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781416)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061 /*QtyTotal*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781416;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781416);

-- OrderLineCount
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781417/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Auftragszeilen', 549338, 592952 /*OrderLineCount*/, 'Y', 100, 'Y', 100, 'N', 'N', 'N', 'N', 'D')
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781417, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 22:37:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 22:37:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781417
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781417)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062 /*OrderLineCount*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781417;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781417);

-- ============================================================
-- 6. AD_UI layout for the Overview tab
-- ============================================================
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Value)
VALUES
    (547847/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:38:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:38:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549338, 10, 'main')
;

INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES
    (549588/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:38:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:38:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547847, 10)
;

INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555492/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-02 22:38:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-02 22:38:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549588, 10, 'primary', 'default')
;

-- UI Elements for the Overview tab
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781407 /*ProductValue*/, 0, 549338, 555492, 652523/*From ID Server*/, 'F',
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
VALUES (0, 781408 /*ProductName*/, 0, 549338, 555492, 652524/*From ID Server*/, 'F',
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
VALUES (0, 781409 /*C_UOM_ID*/, 0, 549338, 555492, 652525/*From ID Server*/, 'F',
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
VALUES (0, 781410 /*DeliveryDate*/, 0, 549338, 555492, 652526/*From ID Server*/, 'F',
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
VALUES (0, 781411 /*CountryName*/, 0, 549338, 555492, 652527/*From ID Server*/, 'F',
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
VALUES (0, 781412 /*C_Country_ID*/, 0, 549338, 555492, 652528/*From ID Server*/, 'F',
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
VALUES (0, 781413 /*QtyWaiting*/, 0, 549338, 555492, 652529/*From ID Server*/, 'F',
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
VALUES (0, 781414 /*QtyPicking*/, 0, 549338, 555492, 652530/*From ID Server*/, 'F',
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
VALUES (0, 781415 /*QtyPacking*/, 0, 549338, 555492, 652531/*From ID Server*/, 'F',
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
VALUES (0, 781416 /*QtyTotal*/, 0, 549338, 555492, 652532/*From ID Server*/, 'F',
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
VALUES (0, 781417 /*OrderLineCount*/, 0, 549338, 555492, 652533/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-02 22:38:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Auftragszeilen', 100, 100, 0,
    TO_TIMESTAMP('2026-07-02 22:38:40', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
