-- nShift Carrier_Config: new ShipType column
--   ShipType          Char(1) LIST (Ship/Order), mandatory, default 'O' (Order) — selects which nShift shipment-create endpoint is used
--
-- AdviseType / IsSelectionRules already default to Order / 'Y' at introduction in
-- 5807540_sys_Carrier_Config_AdviseType_IsSelectionRules.sql — nothing to flip here.
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_Element    584992  (ShipType — label "Versand-Typ" / "Ship Type")
--   AD_Column     592809  (Carrier_Config.ShipType)
--   AD_Field      781117  (ShipType field in window 142 / tab 548455)
--   AD_UI_Element 652263  (ShipType UI element — group 553597, SeqNo 89, after IsSelectionRules at 87)
--
-- Reused IDs (already exist, not created here):
--   AD_Reference  542106  (AdviseType LIST: Ship='S' / Order='O') — reused for ShipType
--   AD_Table_ID   542540  (Carrier_Config)
--   AD_Tab_ID     548455  (nShift Konfiguration, window 142)
--   AD_UI_ElementGroup_ID 553597

-- ============================================================
-- ShipType
-- ============================================================

-- AD_Element: ShipType (German base language per convention)
INSERT INTO AD_Element (AD_Client_ID,AD_Org_ID,AD_Element_ID,ColumnName,
                        Created,CreatedBy,Description,EntityType,Help,IsActive,
                        Name,PrintName,
                        Updated,UpdatedBy)
VALUES (0,0,584992 /*From ID Server*/,'ShipType',
        TO_TIMESTAMP('2026-06-15 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Art der Versanderstellung beim Spediteur (Versand vs. Auftrags-Versand).','D',
        'Art der Versanderstellung beim Spediteur (Versand vs. Auftrags-Versand).','Y',
        'Versand-Typ','Versand-Typ',
        TO_TIMESTAMP('2026-06-15 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Element_Trl skeleton (copies base row into every active system language)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,
                            AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Element_ID,t.Description,t.Help,t.Name,t.PrintName,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584992
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US translation (strictly later timestamp — required for update_FieldTranslation_From_AD_Name_Element guard)
UPDATE AD_Element_Trl
SET Name='Ship Type',PrintName='Ship Type',
    Description='Which nShift endpoint creates the shipment (shipment vs order-advice submit).',
    Help='Which nShift endpoint creates the shipment (shipment vs order-advice submit).',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-15 10:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=584992
;

-- de_DE / de_CH: base language text is already German, mark as translated
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-15 10:00:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID=584992
;
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-15 10:00:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID=584992
;

-- DDL: new column with default 'O', backfill existing NULLs, then NOT NULL + CHECK
-- (new column — must use ALTER TABLE ADD COLUMN, not t_alter_column which requires the column to pre-exist)
ALTER TABLE carrier_config ADD COLUMN IF NOT EXISTS ShipType CHAR(1) DEFAULT 'O';
UPDATE carrier_config SET ShipType='O' WHERE ShipType IS NULL;
ALTER TABLE carrier_config ALTER COLUMN ShipType SET NOT NULL;
SELECT public.db_alter_table('carrier_config', 'ALTER TABLE public.carrier_config ADD CONSTRAINT ShipType_Check CHECK (ShipType IN (''S'',''O''))');

-- AD_Column: ShipType (List ref 17, AD_Reference_Value_ID = 542106, same as AdviseType)
INSERT INTO AD_Column (AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Element_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,
                       ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,
                       EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,
                       IsExcludeFromZoomTargets,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,
                       IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,PersonalDataCategory,
                       Name,Updated,UpdatedBy,Version)
VALUES (0,0,592809 /*From ID Server*/,584992,17,542106,542540,
        'ShipType',TO_TIMESTAMP('2026-06-15 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','O',
        'D',1,'Y','Y','N','N',
        'Y','N','N','N','Y',
        'N','N','N','Y','NP',
        'Versand-Typ',TO_TIMESTAMP('2026-06-15 10:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- AD_Column_Trl skeleton
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592809
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- ============================================================
-- Propagate element translations to ShipType column _Trl rows
-- (called here for the column; will be called again after AD_Field to catch field _Trl rows)
-- ============================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584992, NULL);

-- ============================================================
-- Window display: AD_Field + AD_UI_Element in tab 548455 (window 142),
-- placed in the same UI element group (553597), SeqNo 89 — after IsSelectionRules at 87
-- ============================================================

-- AD_Field: ShipType
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,DisplayLength,EntityType,IsActive,
                      IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,
                      Name,SeqNo,SeqNoGrid,SortNo,
                      Updated,UpdatedBy)
VALUES (0,0,781117 /*From ID Server*/,592809,548455,
        TO_TIMESTAMP('2026-06-15 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y',
        'Y','N','N','N','N','N','N',
        'Versand-Typ',0,0,0,
        TO_TIMESTAMP('2026-06-15 10:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- AD_Field_Trl skeleton
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,
                          AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',
       t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781117
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate element translations → field (AD_Element_ID, not AD_Field_ID)
SELECT update_FieldTranslation_From_AD_Name_Element(584992);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781117;
SELECT AD_Element_Link_Create_Missing_Field(781117);

-- AD_UI_Element: ShipType (group 553597, SeqNo 89 — after IsSelectionRules at 87)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,
                           SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           Name,SeqNo,
                           Updated,UpdatedBy,WidgetSize)
VALUES (0,0,652263 /*From ID Server*/,781117,553597,548455,
        TO_TIMESTAMP('2026-06-15 10:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N',
        0,'N',0,'F',
        'Versand-Typ',89,
        TO_TIMESTAMP('2026-06-15 10:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Final propagation: re-run after AD_Field is created so field _Trl rows (created at 10:02:00)
-- pick up element translations (set at 10:00:12–14) — timestamps differ, guard passes
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584992, NULL);
