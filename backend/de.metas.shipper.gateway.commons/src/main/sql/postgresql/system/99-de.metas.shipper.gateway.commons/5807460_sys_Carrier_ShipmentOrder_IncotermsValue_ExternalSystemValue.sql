-- IDs allocated from idserver.metas.de:
--   AD_Element  584983  (IncotermsValue — "Incoterms-Wert"; new)
--   AD_Column   592802  (Carrier_ShipmentOrder.IncotermsValue)
--   AD_Column   592803  (Carrier_ShipmentOrder.ExternalSystem — reuses existing element 577608)
--   AD_Field    780757  (IncotermsValue field in window 541956 / tab 548456)
--   AD_Field    780758  (ExternalSystem field in window 541956 / tab 548456)
--   AD_UI_Element 652052 (IncotermsValue UI element)
--   AD_UI_Element 652053 (ExternalSystem UI element)

-- ============================================================
-- IncotermsValue
-- ============================================================

-- AD_Element: IncotermsValue
INSERT INTO AD_Element (AD_Client_ID,AD_Org_ID,AD_Element_ID,ColumnName,
                        Created,CreatedBy,EntityType,IsActive,
                        Name,PrintName,
                        Updated,UpdatedBy)
VALUES (0,0,584983 /*From ID Server*/,'IncotermsValue',
        TO_TIMESTAMP('2026-06-11 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y',
        'Incoterms-Wert','Incoterms-Wert',
        TO_TIMESTAMP('2026-06-11 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Element_Trl skeleton
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,
                            AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Element_ID,t.Description,t.Help,t.Name,t.PrintName,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584983
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US translation
UPDATE AD_Element_Trl
SET Name='Incoterms Value',PrintName='Incoterms Value',IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 10:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=584983
;

-- de_DE / de_CH: base language text is already German, mark as translated
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 10:00:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID=584983
;
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 10:00:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID=584983
;

-- DDL: add column
ALTER TABLE carrier_shipmentorder ADD COLUMN IF NOT EXISTS IncotermsValue VARCHAR(40);

-- AD_Column (EntityType='U' matches the table's entity type)
INSERT INTO AD_Column (AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Element_ID,AD_Reference_ID,AD_Table_ID,
                       ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,
                       EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,
                       IsExcludeFromZoomTargets,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,
                       IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,PersonalDataCategory,
                       Name,Updated,UpdatedBy,Version)
VALUES (0,0,592802 /*From ID Server*/,584983,10,542532,
        'IncotermsValue',TO_TIMESTAMP('2026-06-11 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N',NULL,
        'U',40,'Y','Y','N','N',
        'Y','N','N','N','N',
        'N','N','N','Y','NP',
        'Incoterms-Wert',TO_TIMESTAMP('2026-06-11 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- AD_Column_Trl skeleton
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592802
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================
-- ExternalSystem — reuses existing AD_Element 577608 (ColumnName='ExternalSystem')
-- ============================================================

-- Fix element 577608 base-language label to proper German
UPDATE AD_Element
SET Name='Externes System', PrintName='Externes System',
    Updated=TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=577608
;

-- Fix de_DE / de_CH translations: set proper German label with IsTranslated='Y'
UPDATE AD_Element_Trl
SET Name='Externes System', PrintName='Externes System', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=577608 AND AD_Language IN ('de_DE','de_CH')
;

-- Cascade label change to all AD_Column_Trl rows backed by element 577608
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577608, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577608, 'de_CH');

-- DDL: add column
ALTER TABLE carrier_shipmentorder ADD COLUMN IF NOT EXISTS ExternalSystem VARCHAR(40);

-- AD_Column (reuses element 577608; EntityType='U' matches the table)
INSERT INTO AD_Column (AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Element_ID,AD_Reference_ID,AD_Table_ID,
                       ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,
                       EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,
                       IsExcludeFromZoomTargets,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,
                       IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,PersonalDataCategory,
                       Name,Updated,UpdatedBy,Version)
VALUES (0,0,592803 /*From ID Server*/,577608 /*existing ExternalSystem element*/,10,542532,
        'ExternalSystem',TO_TIMESTAMP('2026-06-12 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N',NULL,
        'U',40,'Y','Y','N','N',
        'Y','N','N','N','N',
        'N','N','N','Y','NP',
        'Externes System',TO_TIMESTAMP('2026-06-12 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- AD_Column_Trl skeleton
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592803
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Propagate element translations to column _Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584983, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577608, NULL);

-- ============================================================
-- Window display: AD_Field + AD_UI_Element for IncotermsValue and ExternalSystem
-- in Carrier_ShipmentOrder window (AD_Window 541956, AD_Tab 548456)
-- placed read-only in the "main" UI element group (553598)
-- ============================================================

-- AD_Field: IncotermsValue
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,DisplayLength,EntityType,IsActive,
                      IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,
                      Name,SeqNo,SeqNoGrid,SortNo,
                      Updated,UpdatedBy)
VALUES (0,0,780757 /*From ID Server*/,592802,548456,
        TO_TIMESTAMP('2026-06-12 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'U','Y',
        'Y','Y','N','N','N','Y','N',
        'Incoterms-Wert',160,160,0,
        TO_TIMESTAMP('2026-06-12 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Field_Trl skeleton
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,
                          AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780757
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(584983);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780757;
SELECT AD_Element_Link_Create_Missing_Field(780757);

-- AD_UI_Element: IncotermsValue (in group "main" = 553598)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,
                           SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           Name,SeqNo,
                           Updated,UpdatedBy,WidgetSize)
VALUES (0,0,652052 /*From ID Server*/,780757,553598,548456,
        TO_TIMESTAMP('2026-06-12 10:02:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N',
        0,'N',0,'F',
        'Incoterms-Wert',70,
        TO_TIMESTAMP('2026-06-12 10:02:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- AD_Field: ExternalSystem
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,DisplayLength,EntityType,IsActive,
                      IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,
                      Name,SeqNo,SeqNoGrid,SortNo,
                      Updated,UpdatedBy)
VALUES (0,0,780758 /*From ID Server*/,592803,548456,
        TO_TIMESTAMP('2026-06-12 10:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'U','Y',
        'Y','Y','N','N','N','Y','N',
        'Externes System',170,170,0,
        TO_TIMESTAMP('2026-06-12 10:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Field_Trl skeleton
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,
                          AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780758
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(577608);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780758;
SELECT AD_Element_Link_Create_Missing_Field(780758);

-- AD_UI_Element: ExternalSystem (in group "main" = 553598)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,
                           SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           Name,SeqNo,
                           Updated,UpdatedBy,WidgetSize)
VALUES (0,0,652053 /*From ID Server*/,780758,553598,548456,
        TO_TIMESTAMP('2026-06-12 10:03:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N',
        0,'N',0,'F',
        'Externes System',80,
        TO_TIMESTAMP('2026-06-12 10:03:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;
