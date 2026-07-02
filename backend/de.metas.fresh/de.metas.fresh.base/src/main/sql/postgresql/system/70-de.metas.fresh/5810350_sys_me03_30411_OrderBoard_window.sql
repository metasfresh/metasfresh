-- Window: Auftrags-Board / Order Board
-- Creates the WebUI window over M_Picking_OrderBoard_v (defined in 5809900/5809895)
-- Three tabs filtered by OrderBoardStatus: Wartend(W), In Kommissionierung(K), Packen(P)
-- Menu placement: under "Picking" (541856), SeqNo=3, next to Traffic Manager (542241 at SeqNo=2)
--
-- IDs allocated from idserver.metas.de on 2026-07-01:
--   Script         5810350  (from ID 581035)
--   AD_Window      581036
--   AD_Tab         581037   (Wartend / W)
--   AD_Tab         581038   (In Kommissionierung / K)
--   AD_Tab         581039   (Packen / P)
--   AD_Element     581040   (tab name: Wartend)
--   AD_Element     581041   (tab name: In Kommissionierung)
--   AD_Element     581042   (tab name: Packen)
--   AD_Menu        581043
--   AD_Field tab1  581044(ProductValue) 581045(ProductName) 581046(C_UOM_ID)
--                  581047(DeliveryDate) 581048(CountryName) 581049(C_Country_ID)
--                  581050(QtyTotal)     581095(OrderLineCount)
--   AD_Field tab2  581051(ProductValue) 581052(ProductName) 581053(C_UOM_ID)
--                  581054(DeliveryDate) 581055(CountryName) 581056(C_Country_ID)
--                  581057(QtyTotal)     581096(OrderLineCount)
--   AD_Field tab3  581058(ProductValue) 581059(ProductName) 581060(C_UOM_ID)
--                  581061(DeliveryDate) 581062(CountryName) 581063(C_Country_ID)
--                  581064(QtyTotal)     581097(OrderLineCount)
--   AD_UI_Section  581065(tab1) 581075(tab2) 581085(tab3)
--   AD_UI_Column   581066(tab1) 581076(tab2) 581086(tab3)
--   AD_UI_ElemGrp  581067(tab1) 581077(tab2) 581087(tab3)
--   AD_UI_Elem tab1 581068(ProductValue) 581069(ProductName) 581070(C_UOM_ID)
--                   581071(DeliveryDate) 581072(CountryName) 581073(C_Country_ID)
--                   581074(QtyTotal)     581098(OrderLineCount)
--   AD_UI_Elem tab2 581078(ProductValue) 581079(ProductName) 581080(C_UOM_ID)
--                   581081(DeliveryDate) 581082(CountryName) 581083(C_Country_ID)
--                   581084(QtyTotal)     581099(OrderLineCount)
--   AD_UI_Elem tab3 581088(ProductValue) 581089(ProductName) 581090(C_UOM_ID)
--                   581091(DeliveryDate) 581092(CountryName) 581093(C_Country_ID)
--                   581094(QtyTotal)     581100(OrderLineCount)
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
    (581040/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_Wartend', 'Wartend', 'Wartend', 'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581040, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 581040
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 581040);

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Wartend', PrintName = 'Wartend',
       Updated = TO_TIMESTAMP('2026-07-01 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581040 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Wartend', PrintName = 'Wartend',
       Updated = TO_TIMESTAMP('2026-07-01 10:01:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581040 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'Waiting', PrintName = 'Waiting',
       Updated = TO_TIMESTAMP('2026-07-01 10:01:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581040 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581040, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581040, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581040, 'en_US');

-- 1b. In Kommissionierung / In Picking (tab 2)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (581041/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_InKommissionierung', 'In Kommissionierung', 'In Kommissionierung', 'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581041, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 581041
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 581041);

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'In Kommissionierung', PrintName = 'In Kommissionierung',
       Updated = TO_TIMESTAMP('2026-07-01 10:02:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581041 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'In Kommissionierung', PrintName = 'In Kommissionierung',
       Updated = TO_TIMESTAMP('2026-07-01 10:02:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581041 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'In Picking', PrintName = 'In Picking',
       Updated = TO_TIMESTAMP('2026-07-01 10:02:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581041 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581041, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581041, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581041, 'en_US');

-- 1c. Packen / Packing (tab 3)
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (581042/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Picking_OrderBoard_Packen', 'Packen', 'Packen', 'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated, Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581042, 'N', e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Element e
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND e.AD_Element_ID = 581042
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 581042);

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Packen', PrintName = 'Packen',
       Updated = TO_TIMESTAMP('2026-07-01 10:03:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581042 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    IsTranslated = 'N', Name = 'Packen', PrintName = 'Packen',
       Updated = TO_TIMESTAMP('2026-07-01 10:03:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581042 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y', Name = 'Packing', PrintName = 'Packing',
       Updated = TO_TIMESTAMP('2026-07-01 10:03:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 581042 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581042, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581042, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581042, 'en_US');

-- ============================================================
-- 2. AD_Window (reuses element 585064: Auftrags-Board / Order Board)
-- ============================================================
INSERT INTO AD_Window
    (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, EntityType,
     AD_Element_ID, InternalName)
VALUES
    (581036/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Auftrags-Board', 'M', 'Y', 'D',
     585064 /*Auftrags-Board element from 5809900*/, 'orderBoard');

INSERT INTO AD_Window_Trl
    (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581036, 'N', w.Name, w.Description, w.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Window w
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND w.AD_Window_ID = 581036
  AND  NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = 581036);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585064, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585064, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585064, 'en_US');

DELETE FROM AD_Element_Link WHERE AD_Window_ID = 581036;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Window(581036);

-- Register as the primary window on M_Picking_OrderBoard_v
UPDATE AD_Table
SET    AD_Window_ID = 581036,
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
    (581037/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:15:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:15:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Wartend', 581036, 542622, 0, 10,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 581040, 'OrderBoardStatus=''W''');

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581037, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:15:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:15:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 581037
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 581037);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581040, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581040, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581040, 'en_US');

-- Tab 2: In Kommissionierung (OrderBoardStatus = 'K')
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID, WhereClause)
VALUES
    (581038/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:16:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:16:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'In Kommissionierung', 581036, 542622, 0, 20,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 581041, 'OrderBoardStatus=''K''');

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581038, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:16:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:16:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 581038
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 581038);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581041, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581041, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581041, 'en_US');

-- Tab 3: Packen (OrderBoardStatus = 'P')
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID, WhereClause)
VALUES
    (581039/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:17:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:17:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Packen', 581036, 542622, 0, 30,
     'N', 'N', 'N', 'Y',
     'N', 'N',
     'D', 581042, 'OrderBoardStatus=''P''');

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581039, 'N', t.Name, t.Description, t.Help, NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:17:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:17:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 581039
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 581039);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581042, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581042, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581042, 'en_US');

-- ============================================================
-- 4. AD_Fields — Tab 1 (Wartend / 581037)
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
    (581044/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Produktnummer', 581037, 592898 /*ProductValue*/,
     'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581044, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581044
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581044);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675 /*ProductValue*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581044;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581044);

-- ProductName
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581045/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Produktname', 581037, 592899 /*ProductName*/,
     'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581045, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581045
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581045);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659 /*ProductName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581045;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581045);

-- C_UOM_ID
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581046/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Maßeinheit', 581037, 592900 /*C_UOM_ID*/,
     'Y', 30, 'Y', 30,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581046, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581046
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581046);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215 /*C_UOM_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581046;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581046);

-- DeliveryDate
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581047/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Lieferdatum', 581037, 592902 /*DeliveryDate*/,
     'Y', 40, 'Y', 40,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581047, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581047
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581047);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376 /*DeliveryDate*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581047;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581047);

-- CountryName (text, shown in grid — C_Country_ID lookup covers form filter)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581048/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Landname', 581037, 592904 /*CountryName*/,
     'Y', 50, 'Y', 50,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581048, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581048
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581048);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585 /*CountryName*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581048;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581048);

-- C_Country_ID (filter field, shown in form, not in grid — text column CountryName covers grid)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581049/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:20:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:20:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Land', 581037, 592903 /*C_Country_ID*/,
     'Y', 55, 'N', 0,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581049, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:20:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:20:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581049
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581049);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192 /*C_Country_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581049;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581049);

-- QtyTotal
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581050/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:21:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:21:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Menge gesamt', 581037, 592905 /*QtyTotal*/,
     'Y', 60, 'Y', 60,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581050, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:21:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:21:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581050
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581050);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061 /*QtyTotal*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581050;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581050);

-- OrderLineCount
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES
    (581095/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:21:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:21:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Auftragszeilen', 581037, 592906 /*OrderLineCount*/,
     'Y', 70, 'Y', 70,
     'N', 'N', 'N', 'N', 'D');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581095, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 10:21:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 10:21:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 581095
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 581095);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062 /*OrderLineCount*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 581095;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581095);

-- ============================================================
-- 5. AD_UI layout — Tab 1 (581037)
-- ============================================================
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Value)
VALUES
    (581065/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:25:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:25:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     581037, 10, 'main');

INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES
    (581066/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:25:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:25:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     581065, 10);

INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (581067/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 10:25:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 10:25:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     581066, 10, 'primary', 'default');

-- UI Elements for Tab 1
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581044 /*ProductValue*/, 0, 581037, 581067, 581068/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0,
    TO_TIMESTAMP('2026-07-01 10:26:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581045 /*ProductName*/, 0, 581037, 581067, 581069/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Produktname', 20, 20, 0,
    TO_TIMESTAMP('2026-07-01 10:26:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581046 /*C_UOM_ID*/, 0, 581037, 581067, 581070/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0,
    TO_TIMESTAMP('2026-07-01 10:26:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581047 /*DeliveryDate*/, 0, 581037, 581067, 581071/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0,
    TO_TIMESTAMP('2026-07-01 10:26:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581048 /*CountryName*/, 0, 581037, 581067, 581072/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Landname', 50, 50, 0,
    TO_TIMESTAMP('2026-07-01 10:26:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581049 /*C_Country_ID*/, 0, 581037, 581067, 581073/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'N', 'N', 'Land', 55, 0, 0,
    TO_TIMESTAMP('2026-07-01 10:26:05', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581050 /*QtyTotal*/, 0, 581037, 581067, 581074/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Menge gesamt', 60, 60, 0,
    TO_TIMESTAMP('2026-07-01 10:26:06', 'YYYY-MM-DD HH24:MI:SS'), 100);

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581095 /*OrderLineCount*/, 0, 581037, 581067, 581098/*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-01 10:26:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N', 'Auftragszeilen', 70, 70, 0,
    TO_TIMESTAMP('2026-07-01 10:26:07', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 6. AD_Fields — Tab 2 (In Kommissionierung / 581038)
-- ============================================================

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581051/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktnummer', 581038, 592898, 'Y', 10, 'Y', 10, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581051, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581051
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581051);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581051;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581051);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581052/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Produktname', 581038, 592899, 'Y', 20, 'Y', 20, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581052, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581052
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581052);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581052;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581052);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581053/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Maßeinheit', 581038, 592900, 'Y', 30, 'Y', 30, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581053, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581053
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581053);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581053;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581053);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581054/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Lieferdatum', 581038, 592902, 'Y', 40, 'Y', 40, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581054, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581054
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581054);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581054;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581054);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581055/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Landname', 581038, 592904, 'Y', 50, 'Y', 50, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581055, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581055
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581055);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581055;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581055);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581056/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Land', 581038, 592903, 'Y', 55, 'N', 0, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581056, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:35:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:35:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581056
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581056);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581056;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581056);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581057/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Menge gesamt', 581038, 592905, 'Y', 60, 'Y', 60, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581057, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581057
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581057);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581057;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581057);

INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581096/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Auftragszeilen', 581038, 592906, 'Y', 70, 'Y', 70, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581096, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-01 10:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-01 10:36:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581096
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581096);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581096;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581096);

-- ============================================================
-- 7. AD_UI layout — Tab 2 (581038)
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES (581075/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:40:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:40:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 581038, 10, 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (581076/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:40:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:40:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 581075, 10);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (581077/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:40:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:40:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 581076, 10, 'primary', 'default');

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581051, 0, 581038, 581077, 581078/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0, TO_TIMESTAMP('2026-07-01 10:41:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581052, 0, 581038, 581077, 581079/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktname', 20, 20, 0, TO_TIMESTAMP('2026-07-01 10:41:01', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581053, 0, 581038, 581077, 581080/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0, TO_TIMESTAMP('2026-07-01 10:41:02', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581054, 0, 581038, 581077, 581081/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0, TO_TIMESTAMP('2026-07-01 10:41:03', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581055, 0, 581038, 581077, 581082/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Landname', 50, 50, 0, TO_TIMESTAMP('2026-07-01 10:41:04', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581056, 0, 581038, 581077, 581083/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Land', 55, 0, 0, TO_TIMESTAMP('2026-07-01 10:41:05', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581057, 0, 581038, 581077, 581084/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Menge gesamt', 60, 60, 0, TO_TIMESTAMP('2026-07-01 10:41:06', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581096, 0, 581038, 581077, 581099/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:41:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Auftragszeilen', 70, 70, 0, TO_TIMESTAMP('2026-07-01 10:41:07', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 8. AD_Fields — Tab 3 (Packen / 581039)
-- ============================================================

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581058/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Produktnummer', 581039, 592898, 'Y', 10, 'Y', 10, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581058, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581058
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581058);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1675);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581058;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581058);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581059/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Produktname', 581039, 592899, 'Y', 20, 'Y', 20, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581059, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581059
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581059);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2659);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581059;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581059);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581060/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Maßeinheit', 581039, 592900, 'Y', 30, 'Y', 30, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581060, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581060
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581060);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581060;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581060);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581061/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Lieferdatum', 581039, 592902, 'Y', 40, 'Y', 40, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581061, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:31', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581061
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581061);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541376);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581061;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581061);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581062/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:40', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Landname', 581039, 592904, 'Y', 50, 'Y', 50, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581062, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:41', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581062
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581062);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2585);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581062;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581062);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581063/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:50', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Land', 581039, 592903, 'Y', 55, 'N', 0, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581063, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:50:51', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:50:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581063
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581063);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(192);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581063;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581063);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581064/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Menge gesamt', 581039, 592905, 'Y', 60, 'Y', 60, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581064, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581064
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581064);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585061);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581064;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581064);

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (581097/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Auftragszeilen', 581039, 592906, 'Y', 70, 'Y', 70, 'N', 'N', 'N', 'N', 'D');
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581097, 'N', f.Name, f.Description, f.Help, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:51:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:51:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND f.AD_Field_ID=581097
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=581097);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585062);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=581097;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(581097);

-- ============================================================
-- 9. AD_UI layout — Tab 3 (581039)
-- ============================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES (581085/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:55:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:55:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 581039, 10, 'main');

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (581086/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:55:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:55:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 581085, 10);

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (581087/*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-01 10:55:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-01 10:55:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 581086, 10, 'primary', 'default');

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581058, 0, 581039, 581087, 581088/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktnummer', 10, 10, 0, TO_TIMESTAMP('2026-07-01 10:56:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581059, 0, 581039, 581087, 581089/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produktname', 20, 20, 0, TO_TIMESTAMP('2026-07-01 10:56:01', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581060, 0, 581039, 581087, 581090/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Maßeinheit', 30, 30, 0, TO_TIMESTAMP('2026-07-01 10:56:02', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581061, 0, 581039, 581087, 581091/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Lieferdatum', 40, 40, 0, TO_TIMESTAMP('2026-07-01 10:56:03', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581062, 0, 581039, 581087, 581092/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Landname', 50, 50, 0, TO_TIMESTAMP('2026-07-01 10:56:04', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581063, 0, 581039, 581087, 581093/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Land', 55, 0, 0, TO_TIMESTAMP('2026-07-01 10:56:05', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581064, 0, 581039, 581087, 581094/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Menge gesamt', 60, 60, 0, TO_TIMESTAMP('2026-07-01 10:56:06', 'YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 581097, 0, 581039, 581087, 581100/*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-01 10:56:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Auftragszeilen', 70, 70, 0, TO_TIMESTAMP('2026-07-01 10:56:07', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 10. AD_Menu + tree placement (Picking parent 541856, SeqNo=3 after Traffic Manager at 2)
-- ============================================================
INSERT INTO AD_Menu
    (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary,
     EntityType, AD_Element_ID, InternalName)
VALUES
    (581043/*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Auftrags-Board', 'W', 581036, 'N',
     'D', 585064, 'orderBoard');

INSERT INTO AD_Menu_Trl
    (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 581043, 'N', m.Name, m.Description,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-01 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-01 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Menu m
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND m.AD_Menu_ID = 581043
  AND  NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = 581043);

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
     10 /*AD_Tree_ID*/, 581043/*From ID Server*/, 541856 /*Parent=Picking*/, 3);
