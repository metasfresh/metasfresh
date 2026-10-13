-- Window: Auftrags-Board / Order Board
-- Creates the WebUI window over M_Picking_OrderBoard_v (defined in 5809900/5809895)
-- Three tabs filtered by OrderBoardStatus: Wartend(W), In Kommissionierung(K), Packen(P)
-- Menu placement: under "Picking" (541856), SeqNo=3, next to Traffic Manager (542241 at SeqNo=2)
--
-- IDs allocated from idserver.metas.de on 2026-07-01:
--   Script         5810350  (from ID 581035)
--   AD_Window      542168
--   AD_Tab         549335   (Wartend / W)
--   AD_Tab         549336   (In Kommissionierung / K)
--   AD_Tab         549337   (Packen / P)
--   AD_Element     585090   (tab name: Wartend)
--   AD_Element     585091   (tab name: In Kommissionierung)
--   AD_Element     585092   (tab name: Packen)
--   AD_Menu        542344
--   AD_Field tab1  781382(ProductValue) 781383(ProductName) 781384(C_UOM_ID)
--                  781385(DeliveryDate) 781386(CountryName) 781387(C_Country_ID)
--                  781388(QtyTotal)     781389(OrderLineCount)
--   AD_Field tab2  781390(ProductValue) 781391(ProductName) 781392(C_UOM_ID)
--                  781393(DeliveryDate) 781394(CountryName) 781395(C_Country_ID)
--                  781396(QtyTotal)     781397(OrderLineCount)
--   AD_Field tab3  781398(ProductValue) 781399(ProductName) 781400(C_UOM_ID)
--                  781401(DeliveryDate) 781402(CountryName) 781403(C_Country_ID)
--                  781404(QtyTotal)     781405(OrderLineCount)
--   AD_UI_Section  547844(tab1) 547845(tab2) 547846(tab3)
--   AD_UI_Column   549585(tab1) 549586(tab2) 549587(tab3)
--   AD_UI_ElemGrp  555489(tab1) 555490(tab2) 555491(tab3)
--   AD_UI_Elem tab1 652498(ProductValue) 652499(ProductName) 652500(C_UOM_ID)
--                   652501(DeliveryDate) 652502(CountryName) 652503(C_Country_ID)
--                   652504(QtyTotal)     652505(OrderLineCount)
--   AD_UI_Elem tab2 652506(ProductValue) 652507(ProductName) 652508(C_UOM_ID)
--                   652509(DeliveryDate) 652510(CountryName) 652511(C_Country_ID)
--                   652512(QtyTotal)     652513(OrderLineCount)
--   AD_UI_Elem tab3 652514(ProductValue) 652515(ProductName) 652516(C_UOM_ID)
--                   652517(DeliveryDate) 652518(CountryName) 652519(C_Country_ID)
--                   652520(QtyTotal)     652521(OrderLineCount)
-- Reused:
--   AD_Element  585064  (window name: Auftrags-Board / Order Board — from 5809900)
--   AD_Table    542622  (M_Picking_OrderBoard_v)
--   AD_Column   592898(ProductValue) 592899(ProductName) 592900(C_UOM_ID)
--               592902(DeliveryDate) 592903(C_Country_ID) 592904(CountryName)
--               592905(QtyTotal)     592906(OrderLineCount)
--   Menu parent 541856  (Picking), sibling 542241 (Traffic Manager) at SeqNo=2
--   AD_Tree_ID  10

-- ============================================================
-- 0. Enable filter flags on AD_Column for the two filter fields
-- ============================================================
UPDATE AD_Column
SET    IsSelectionColumn = 'Y',
       IsRangeFilter     = 'Y',
       Updated           = TO_TIMESTAMP('2026-07-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Column_ID = 592902 /*DeliveryDate*/;

UPDATE AD_Column
SET    IsSelectionColumn = 'Y',
       Updated           = TO_TIMESTAMP('2026-07-01 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Column_ID = 592903 /*C_Country_ID*/;

-- ============================================================
-- 1. AD_Elements for the three tab names
-- ============================================================

-- 1a. Wartend / Waiting (tab 1)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (585090/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_Wartend', 'Wartend', 'Wartend', 'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585090, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 585090
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585090);

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Wartend', PrintName = 'Wartend',
       Updated = TO_TIMESTAMP('2026-07-01 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585090 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Wartend', PrintName = 'Wartend',
       Updated = TO_TIMESTAMP('2026-07-01 10:01:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585090 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'Waiting', PrintName = 'Waiting',
       Updated = TO_TIMESTAMP('2026-07-01 10:01:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585090 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585090, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585090, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585090, 'en_US');

-- 1b. In Kommissionierung / In Picking (tab 2)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (585091/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_InKommissionierung', 'In Kommissionierung', 'In Kommissionierung', 'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585091, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 585091
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585091);

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'In Kommissionierung', PrintName = 'In Kommissionierung',
       Updated = TO_TIMESTAMP('2026-07-01 10:02:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585091 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'In Kommissionierung', PrintName = 'In Kommissionierung',
       Updated = TO_TIMESTAMP('2026-07-01 10:02:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585091 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'In Picking', PrintName = 'In Picking',
       Updated = TO_TIMESTAMP('2026-07-01 10:02:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585091 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585091, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585091, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585091, 'en_US');

-- 1c. Packen / Packing (tab 3)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (585092/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_Packen', 'Packen', 'Packen', 'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585092, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 585092
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585092);

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Packen', PrintName = 'Packen',
       Updated = TO_TIMESTAMP('2026-07-01 10:03:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585092 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Packen', PrintName = 'Packen',
       Updated = TO_TIMESTAMP('2026-07-01 10:03:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585092 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'Packing', PrintName = 'Packing',
       Updated = TO_TIMESTAMP('2026-07-01 10:03:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585092 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585092, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585092, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585092, 'en_US');

-- ============================================================
-- 2. AD_Window (reuses element 585064: Auftrags-Board / Order Board)
-- ============================================================
INSERT INTO AD_Window
    (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, EntityType,
     AD_Element_ID, InternalName)
VALUES
    (542168/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Auftrags-Board', 'M', 'Y', 'D',
     585064 /*Auftrags-Board element from 5809900*/, 'orderBoard');

INSERT INTO AD_Window_Trl
    (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542168, 'N', w.Name, w.Description, w.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Window w
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND w.AD_Window_ID = 542168
  AND  NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = 542168);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585064, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585064, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585064, 'en_US');

DELETE FROM AD_Element_Link WHERE AD_Window_ID = 542168;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Window(542168);

-- Register as the primary window on M_Picking_OrderBoard_v
UPDATE AD_Table
SET    AD_Window_ID = 542168,
       Updated      = TO_TIMESTAMP('2026-07-01 10:10:10', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Table_ID = 542622;

-- ============================================================
-- 3. Three tabs — one per OrderBoardStatus value
-- ============================================================

-- Tab 1: Wartend (OrderBoardStatus = 'W')
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID, WhereClause)
VALUES
    (549335/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:15:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:15:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Wartend', 542168, 542622, 0, 10,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 585090, 'OrderBoardStatus=''W''');

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549335, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:15:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:15:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549335
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549335);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585090, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585090, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585090, 'en_US');

-- Tab 2: In Kommissionierung (OrderBoardStatus = 'K')
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID, WhereClause)
VALUES
    (549336/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:16:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:16:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'In Kommissionierung', 542168, 542622, 0, 20,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 585091, 'OrderBoardStatus=''K''');

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549336, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:16:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:16:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549336
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549336);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585091, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585091, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585091, 'en_US');

-- Tab 3: Packen (OrderBoardStatus = 'P')
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID, WhereClause)
VALUES
    (549337/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:17:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:17:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Packen', 542168, 542622, 0, 30,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 585092, 'OrderBoardStatus=''P''');

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549337, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:17:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:17:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549337
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549337);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585092, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585092, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585092, 'en_US');

-- ============================================================
-- 4. AD_Fields — Tab 1 (Wartend / 549335)
-- Grid: ProductValue(10) ProductName(20) C_UOM_ID(30) DeliveryDate(40) CountryName(50) QtyTotal(60) OrderLineCount(70)
-- Form: same + C_Country_ID(50 form, not grid) shifts CountryName to 60 form/50 grid
-- ============================================================

-- ProductValue
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781382/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Produktnummer', 549335, 592898 /*ProductValue*/,
     'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781382, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781382
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781382);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675 /*ProductValue*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781382;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781382);

-- ProductName
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781383/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Produktname', 549335, 592899 /*ProductName*/,
     'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781383, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781383
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781383);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659 /*ProductName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781383;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781383);

-- C_UOM_ID
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781384/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Maßeinheit', 549335, 592900 /*C_UOM_ID*/,
     'Y', 30, 'Y', 30,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781384, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781384
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781384);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215 /*C_UOM_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781384;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781384);

-- DeliveryDate
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781385/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferdatum', 549335, 592902 /*DeliveryDate*/,
     'Y', 40, 'Y', 40,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781385, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781385
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781385);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376 /*DeliveryDate*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781385;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781385);

-- CountryName (text, shown in grid — C_Country_ID lookup covers form filter)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781386/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Landname', 549335, 592904 /*CountryName*/,
     'Y', 50, 'Y', 50,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781386, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781386
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781386);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585 /*CountryName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781386;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781386);

-- C_Country_ID (filter field, shown in form, not in grid — text column CountryName covers grid)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781387/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Land', 549335, 592903 /*C_Country_ID*/,
     'Y', 55, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781387, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781387
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781387);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192 /*C_Country_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781387;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781387);

-- QtyTotal
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781388/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:21:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:21:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Menge gesamt', 549335, 592905 /*QtyTotal*/,
     'Y', 60, 'Y', 60,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781388, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:21:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:21:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781388
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781388);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061 /*QtyTotal*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781388;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781388);

-- OrderLineCount
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (781389/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:21:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:21:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Auftragszeilen', 549335, 592906 /*OrderLineCount*/,
     'Y', 70, 'Y', 70,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781389, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:21:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:21:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781389
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781389);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062 /*OrderLineCount*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781389;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781389);

-- ============================================================
-- 5. AD_UI layout — Tab 1 (549335)
-- ============================================================
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Value)
VALUES
    (547844/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:25:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:25:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549335, 10, 'main');

INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES
    (549585/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:25:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:25:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547844, 10);

INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555489/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:25:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:25:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549585, 10, 'primary', 'default');

-- UI Elements for Tab 1
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781382 /*ProductValue*/, 0, 549335, 555489, 652498/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0,
    TO_TIMESTAMP('2026-07-01 10:26:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781383 /*ProductName*/, 0, 549335, 555489, 652499/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Produktname', 20, 20, 0,
    TO_TIMESTAMP('2026-07-01 10:26:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781384 /*C_UOM_ID*/, 0, 549335, 555489, 652500/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0,
    TO_TIMESTAMP('2026-07-01 10:26:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781385 /*DeliveryDate*/, 0, 549335, 555489, 652501/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0,
    TO_TIMESTAMP('2026-07-01 10:26:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781386 /*CountryName*/, 0, 549335, 555489, 652502/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Landname', 50, 50, 0,
    TO_TIMESTAMP('2026-07-01 10:26:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781387 /*C_Country_ID*/, 0, 549335, 555489, 652503/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'N', 'N', 'Land', 55, 0, 0,
    TO_TIMESTAMP('2026-07-01 10:26:05', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781388 /*QtyTotal*/, 0, 549335, 555489, 652504/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Menge gesamt', 60, 60, 0,
    TO_TIMESTAMP('2026-07-01 10:26:06', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781389 /*OrderLineCount*/, 0, 549335, 555489, 652505/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Auftragszeilen', 70, 70, 0,
    TO_TIMESTAMP('2026-07-01 10:26:07', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 6. AD_Fields — Tab 2 (In Kommissionierung / 549336)
-- ============================================================

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781390/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktnummer', 549336, 592898, 'Y', 10, 'Y', 10, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781390, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781390
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781390);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781390;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781390);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781391/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktname', 549336, 592899, 'Y', 20, 'Y', 20, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781391, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781391
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781391);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781391;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781391);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781392/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Maßeinheit', 549336, 592900, 'Y', 30, 'Y', 30, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781392, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781392
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781392);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781392;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781392);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781393/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Lieferdatum', 549336, 592902, 'Y', 40, 'Y', 40, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781393, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781393
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781393);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781393;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781393);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781394/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Landname', 549336, 592904, 'Y', 50, 'Y', 50, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781394, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781394
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781394);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781394;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781394);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781395/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Land', 549336, 592903, 'Y', 55, 'N', 0, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781395, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781395
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781395);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781395;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781395);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781396/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge gesamt', 549336, 592905, 'Y', 60, 'Y', 60, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781396, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781396
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781396);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781396;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781396);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781397/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Auftragszeilen', 549336, 592906, 'Y', 70, 'Y', 70, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781397, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781397
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781397);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781397;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781397);

-- ============================================================
-- 7. AD_UI layout — Tab 2 (549336)
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES (547845/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:40:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:40:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 549336, 10, 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (549586/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:40:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:40:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 547845, 10);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555490/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:40:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:40:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 549586, 10, 'primary', 'default');

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781390, 0, 549336, 555490, 652506/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0, TO_TIMESTAMP('2026-07-01 10:41:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781391, 0, 549336, 555490, 652507/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktname', 20, 20, 0, TO_TIMESTAMP('2026-07-01 10:41:01', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781392, 0, 549336, 555490, 652508/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0, TO_TIMESTAMP('2026-07-01 10:41:02', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781393, 0, 549336, 555490, 652509/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0, TO_TIMESTAMP('2026-07-01 10:41:03', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781394, 0, 549336, 555490, 652510/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Landname', 50, 50, 0, TO_TIMESTAMP('2026-07-01 10:41:04', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781395, 0, 549336, 555490, 652511/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Land', 55, 0, 0, TO_TIMESTAMP('2026-07-01 10:41:05', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781396, 0, 549336, 555490, 652512/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Menge gesamt', 60, 60, 0, TO_TIMESTAMP('2026-07-01 10:41:06', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781397, 0, 549336, 555490, 652513/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Auftragszeilen', 70, 70, 0, TO_TIMESTAMP('2026-07-01 10:41:07', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 8. AD_Fields — Tab 3 (Packen / 549337)
-- ============================================================

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781398/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Produktnummer', 549337, 592898, 'Y', 10, 'Y', 10, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781398, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781398
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781398);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781398;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781398);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781399/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Produktname', 549337, 592899, 'Y', 20, 'Y', 20, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781399, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781399
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781399);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781399;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781399);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781400/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Maßeinheit', 549337, 592900, 'Y', 30, 'Y', 30, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781400, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781400
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781400);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781400;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781400);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781401/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Lieferdatum', 549337, 592902, 'Y', 40, 'Y', 40, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781401, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:31', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781401
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781401);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781401;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781401);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781402/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:40', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Landname', 549337, 592904, 'Y', 50, 'Y', 50, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781402, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:41', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781402
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781402);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781402;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781402);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781403/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:50', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Land', 549337, 592903, 'Y', 55, 'N', 0, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781403, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:51', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781403
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781403);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781403;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781403);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781404/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Menge gesamt', 549337, 592905, 'Y', 60, 'Y', 60, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781404, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781404
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781404);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781404;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781404);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781405/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Auftragszeilen', 549337, 592906, 'Y', 70, 'Y', 70, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781405, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=781405
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=781405);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781405;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781405);

-- ============================================================
-- 9. AD_UI layout — Tab 3 (549337)
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES (547846/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:55:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:55:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 549337, 10, 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (549587/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:55:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:55:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 547846, 10);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555491/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:55:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:55:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 549587, 10, 'primary', 'default');

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781398, 0, 549337, 555491, 652514/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0, TO_TIMESTAMP('2026-07-01 10:56:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781399, 0, 549337, 555491, 652515/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktname', 20, 20, 0, TO_TIMESTAMP('2026-07-01 10:56:01', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781400, 0, 549337, 555491, 652516/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0, TO_TIMESTAMP('2026-07-01 10:56:02', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781401, 0, 549337, 555491, 652517/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0, TO_TIMESTAMP('2026-07-01 10:56:03', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781402, 0, 549337, 555491, 652518/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Landname', 50, 50, 0, TO_TIMESTAMP('2026-07-01 10:56:04', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781403, 0, 549337, 555491, 652519/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Land', 55, 0, 0, TO_TIMESTAMP('2026-07-01 10:56:05', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781404, 0, 549337, 555491, 652520/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Menge gesamt', 60, 60, 0, TO_TIMESTAMP('2026-07-01 10:56:06', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781405, 0, 549337, 555491, 652521/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Auftragszeilen', 70, 70, 0, TO_TIMESTAMP('2026-07-01 10:56:07', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 10. AD_Menu + tree placement (Picking parent 541856, SeqNo=3 after Traffic Manager at 2)
-- ============================================================
INSERT INTO AD_Menu
    (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary,
     EntityType, AD_Element_ID, InternalName)
VALUES
    (542344/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Auftrags-Board', 'W', 542168, 'N',
     'D', 585064, 'orderBoard');

INSERT INTO AD_Menu_Trl
    (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542344, 'N', m.Name, m.Description,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Menu m
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND m.AD_Menu_ID = 542344
  AND  NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = 542344);

/* DDL */ SELECT update_menu_translation_from_ad_element(585064, NULL);

-- Shift existing children of 541856 at SeqNo >= 3 to make room
-- (In reverse order to avoid transient SeqNo conflicts)
UPDATE AD_TreeNodeMM SET SeqNo=6, Updated=TO_TIMESTAMP('2026-07-01 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE  Node_ID=541908 /*Print Picking Slot QR Codes*/ AND AD_Tree_ID=10 AND Parent_ID=541856;

UPDATE AD_TreeNodeMM SET SeqNo=5, Updated=TO_TIMESTAMP('2026-07-01 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE  Node_ID=541858 /*Picking Job Step*/ AND AD_Tree_ID=10 AND Parent_ID=541856;

UPDATE AD_TreeNodeMM SET SeqNo=4, Updated=TO_TIMESTAMP('2026-07-01 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE  Node_ID=541857 /*Picking Job*/ AND AD_Tree_ID=10 AND Parent_ID=541856;

-- Place Order Board at SeqNo=3 under Picking parent
INSERT INTO AD_TreeNodeMM
    (AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
VALUES
    (0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10 /*AD_Tree_ID*/, 542344/*From ID Server*/, 541856 /*Parent=Picking*/, 3);
