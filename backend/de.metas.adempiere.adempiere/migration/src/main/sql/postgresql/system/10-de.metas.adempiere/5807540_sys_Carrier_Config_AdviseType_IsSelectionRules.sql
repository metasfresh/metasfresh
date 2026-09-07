-- nShift Carrier_Config: two new columns shown in tab 548455 ("nShift Konfiguration", window 142 "Lieferweg")
--   AdviseType        Char(1) LIST (Ship/Order), mandatory, default 'S' (Ship) — selects which nShift advise endpoint is used
--   IsSelectionRules  Char(1) YesNo, mandatory, default 'Y' — activates nShift Selection Rules during carrier advise
--
-- IDs allocated from idserver.metas.de on 2026-06-12:
--   AD_Reference   542106  (new list reference "AdviseType" — ValidationType='L', holds Ship/Order)
--   AD_Ref_List    544261  (Value 'S' / Name 'Ship'  — DEFAULT; written-out name, intentionally NOT translated)
--   AD_Ref_List    544262  (Value 'O' / Name 'Order'                ; written-out name, intentionally NOT translated)
--   AD_Element     584987  (AdviseType — label "Lieferweg-Abfrage-Typ" / "Carrier Advise Type", consistent with AD_Process M_ShipmentSchedule_Advise "Lieferweg-Abfrage" / "Carrier Advise")
--   AD_Element     584988  (IsSelectionRules — label "Auswahlregeln" / en_US "Selection Rules")
--   AD_Column      592804  (Carrier_Config.AdviseType)
--   AD_Column      592805  (Carrier_Config.IsSelectionRules)
--   AD_Field       780759  (AdviseType field in window 142 / tab 548455)
--   AD_Field       780760  (IsSelectionRules field in window 142 / tab 548455)
--   AD_UI_Element  652054  (AdviseType UI element — group 553597, after Service Level)
--   AD_UI_Element  652055  (IsSelectionRules UI element — group 553597, after AdviseType)

-- ============================================================
-- AD_Reference + AD_Ref_List for AdviseType (List: Ship / Order)
-- Ref-list Names are the written-out words ('Ship' / 'Order') and are intentionally
-- NOT translated: the same written-out name must show in every language, so the
-- skeleton _Trl rows mirror the base Name and no en_US override is applied.
-- ============================================================

-- AD_Reference (ValidationType='L')
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,
                          Created,CreatedBy,EntityType,IsActive,Name,
                          Updated,UpdatedBy,ValidationType,VFormat)
VALUES (0,0,542106 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','AdviseType',
        TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'L',NULL)
;

-- AD_Reference_Trl skeleton
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID,Name,IsTranslated,
                              AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Reference_ID,t.Name,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542106
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- AD_Ref_List: Ship (Value 'S' — the DEFAULT)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544261 /*From ID Server*/,542106,
        TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Ship',
        TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'S','Ship')
;

-- AD_Ref_List: Order (Value 'O')
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544262 /*From ID Server*/,542106,
        TO_TIMESTAMP('2026-06-12 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Order',
        TO_TIMESTAMP('2026-06-12 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'O','Order')
;

-- AD_Ref_List_Trl skeleton (mirrors the written-out base Name in every language; NO en_US override on purpose)
INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,t.AD_Ref_List_ID,
       t.Created,t.CreatedBy,t.Description,'Y','Y',t.Name,
       t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID IN (544261,544262)
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- ============================================================
-- AdviseType
-- ============================================================

-- AD_Element: AdviseType
INSERT INTO AD_Element (AD_Client_ID,AD_Org_ID,AD_Element_ID,ColumnName,
                        Created,CreatedBy,Description,EntityType,Help,IsActive,
                        Name,PrintName,
                        Updated,UpdatedBy)
VALUES (0,0,584987 /*From ID Server*/,'AdviseType',
        TO_TIMESTAMP('2026-06-12 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Art der Lieferweg-Abfrage beim Spediteur (Lieferweg- vs. Auftrags-Abfrage).','D',
        'Art der Lieferweg-Abfrage beim Spediteur (Lieferweg- vs. Auftrags-Abfrage).','Y',
        'Lieferweg-Abfrage-Typ','Lieferweg-Abfrage-Typ',
        TO_TIMESTAMP('2026-06-12 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Element_Trl skeleton
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,
                            AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Element_ID,t.Description,t.Help,t.Name,t.PrintName,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584987
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US translation
UPDATE AD_Element_Trl
SET Name='Carrier Advise Type',PrintName='Carrier Advise Type',
    Description='Which carrier-advise endpoint is used (ship advise vs order advise).',
    Help='Which carrier-advise endpoint is used (ship advise vs order advise).',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:01:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=584987
;

-- de_DE / de_CH: base language text is already German, mark as translated
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:01:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID=584987
;
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:01:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID=584987
;

-- DDL: new column with default 'O' (Order), backfill existing rows, then NOT NULL (matches AD_Column.IsMandatory='Y')
ALTER TABLE carrier_config ADD COLUMN IF NOT EXISTS AdviseType CHAR(1) DEFAULT 'O';
UPDATE carrier_config SET AdviseType='O' WHERE AdviseType IS NULL;
ALTER TABLE carrier_config ALTER COLUMN AdviseType SET NOT NULL;
SELECT public.db_alter_table('carrier_config', 'ALTER TABLE public.carrier_config ADD CONSTRAINT AdviseType_Check CHECK (AdviseType IN (''S'',''O''))');

-- AD_Column: AdviseType (List reference 17, AD_Reference_Value_ID = new reference 542106)
INSERT INTO AD_Column (AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Element_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,
                       ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,
                       EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,
                       IsExcludeFromZoomTargets,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,
                       IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,PersonalDataCategory,
                       Name,Updated,UpdatedBy,Version)
VALUES (0,0,592804 /*From ID Server*/,584987,17,542106,542540,
        'AdviseType',TO_TIMESTAMP('2026-06-12 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','O',
        'D',1,'Y','Y','N','N',
        'Y','N','N','N','Y',
        'N','N','N','Y','NP',
        'Lieferweg-Abfrage-Typ',TO_TIMESTAMP('2026-06-12 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- AD_Column_Trl skeleton
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592804
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================
-- IsSelectionRules — German label "Auswahlregeln" (en_US: nShift-portal term "Selection Rules")
-- ============================================================

-- AD_Element: IsSelectionRules
INSERT INTO AD_Element (AD_Client_ID,AD_Org_ID,AD_Element_ID,ColumnName,
                        Created,CreatedBy,Description,EntityType,Help,IsActive,
                        Name,PrintName,
                        Updated,UpdatedBy)
VALUES (0,0,584988 /*From ID Server*/,'IsSelectionRules',
        TO_TIMESTAMP('2026-06-12 10:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Aktiviert die nShift Selection Rules beim Carrier-Advise.','D',
        'Wenn gesetzt, werden beim Carrier-Advise die nShift Selection Rules aktiviert.','Y',
        'Auswahlregeln','Auswahlregeln',
        TO_TIMESTAMP('2026-06-12 10:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Element_Trl skeleton
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,
                            AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Element_ID,t.Description,t.Help,t.Name,t.PrintName,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584988
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US override: label is the nShift product term "Selection Rules" (German base label is "Auswahlregeln")
UPDATE AD_Element_Trl
SET Name='Selection Rules',PrintName='Selection Rules',
    Description='Activates the nShift Selection Rules during carrier advise.',
    Help='When set, activates nShift Selection Rules during carrier advise.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:03:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=584988
;

-- de_DE / de_CH: base language text is already German, mark as translated
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:03:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID=584988
;
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:03:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID=584988
;

-- DDL: new YesNo column with default 'Y', backfill existing rows, then NOT NULL (matches AD_Column.IsMandatory='Y')
ALTER TABLE carrier_config ADD COLUMN IF NOT EXISTS IsSelectionRules CHAR(1) DEFAULT 'Y';
UPDATE carrier_config SET IsSelectionRules='Y' WHERE IsSelectionRules IS NULL;
ALTER TABLE carrier_config ALTER COLUMN IsSelectionRules SET NOT NULL;
SELECT public.db_alter_table('carrier_config', 'ALTER TABLE public.carrier_config ADD CONSTRAINT IsSelectionRules_Check CHECK (IsSelectionRules IN (''Y'',''N''))');

-- AD_Column: IsSelectionRules (YesNo reference 20)
INSERT INTO AD_Column (AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Element_ID,AD_Reference_ID,AD_Table_ID,
                       ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,
                       EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,
                       IsExcludeFromZoomTargets,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,
                       IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,PersonalDataCategory,
                       Name,Updated,UpdatedBy,Version)
VALUES (0,0,592805 /*From ID Server*/,584988,20,542540,
        'IsSelectionRules',TO_TIMESTAMP('2026-06-12 10:04:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y',
        'D',1,'Y','Y','N','N',
        'Y','N','N','N','Y',
        'N','N','N','Y','NP',
        'Auswahlregeln',TO_TIMESTAMP('2026-06-12 10:04:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- AD_Column_Trl skeleton
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592805
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================
-- Window display: AD_Field + AD_UI_Element in tab 548455 (window 142),
-- placed in the same UI element group (553597) as Service Level, after it.
-- ============================================================

-- AD_Field: AdviseType
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,DisplayLength,EntityType,IsActive,
                      IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,
                      Name,SeqNo,SeqNoGrid,SortNo,
                      Updated,UpdatedBy)
VALUES (0,0,780759 /*From ID Server*/,592804,548455,
        TO_TIMESTAMP('2026-06-12 10:05:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y',
        'Y','N','N','N','N','N','N',
        'Lieferweg-Abfrage-Typ',0,0,0,
        TO_TIMESTAMP('2026-06-12 10:05:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Field_Trl skeleton
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,
                          AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780759
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(584987);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780759;
SELECT AD_Element_Link_Create_Missing_Field(780759);

-- AD_UI_Element: AdviseType (group 553597, SeqNo 85 — after Service Level at 80)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,
                           SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           Name,SeqNo,
                           Updated,UpdatedBy,WidgetSize)
VALUES (0,0,652054 /*From ID Server*/,780759,553597,548455,
        TO_TIMESTAMP('2026-06-12 10:05:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N',
        0,'N',0,'F',
        'Lieferweg-Abfrage-Typ',85,
        TO_TIMESTAMP('2026-06-12 10:05:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- AD_Field: IsSelectionRules
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,DisplayLength,EntityType,IsActive,
                      IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,
                      Name,SeqNo,SeqNoGrid,SortNo,
                      Updated,UpdatedBy)
VALUES (0,0,780760 /*From ID Server*/,592805,548455,
        TO_TIMESTAMP('2026-06-12 10:06:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y',
        'Y','N','N','N','N','N','N',
        'Auswahlregeln',0,0,0,
        TO_TIMESTAMP('2026-06-12 10:06:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Field_Trl skeleton
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,
                          AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780760
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(584988);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780760;
SELECT AD_Element_Link_Create_Missing_Field(780760);

-- AD_UI_Element: IsSelectionRules (group 553597, SeqNo 87 — after AdviseType)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,
                           SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           Name,SeqNo,
                           Updated,UpdatedBy,WidgetSize)
VALUES (0,0,652055 /*From ID Server*/,780760,553597,548455,
        TO_TIMESTAMP('2026-06-12 10:06:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N',
        0,'N',0,'F',
        'Auswahlregeln',87,
        TO_TIMESTAMP('2026-06-12 10:06:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Propagate element translations to columns' AND fields' _Trl rows.
-- Placed after the AD_Field + AD_Field_Trl skeletons exist so the cascade reaches the field _Trl
-- rows (incl. the IsTranslated flag), not only the columns.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584987, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584988, NULL);
