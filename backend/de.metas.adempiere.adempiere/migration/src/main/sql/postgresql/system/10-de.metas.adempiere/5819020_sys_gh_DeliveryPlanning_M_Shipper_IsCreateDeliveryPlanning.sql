-- Run mode: SWING_CLIENT

-- Add M_Shipper.IsCreateDeliveryPlanning (char(1), default 'N', mandatory) + AD metadata (AC-13a)
--
-- IDs allocated from idserver.metas.de on 2026-08-13:
--   AD_Element    585298 (new IsCreateDeliveryPlanning label for M_Shipper)
--   AD_Column     593302 (M_Shipper.IsCreateDeliveryPlanning, default 'N', NOT NULL)
--   AD_Field      782286 (Lieferweg window 142 / tab 185)
--   AD_UI_Element 653136 (flag group 541020, SeqNo 40)

-- Element: IsCreateDeliveryPlanning
-- 2026-08-13T00:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585298 /*From ID Server*/,0,'IsCreateDeliveryPlanning',TO_TIMESTAMP('2026-08-13 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferplanung erstellen','Lieferplanung erstellen',TO_TIMESTAMP('2026-08-13 00:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-08-13T00:00:00.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585298
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCreateDeliveryPlanning (de_CH)
-- 2026-08-13T00:00:01.000Z
UPDATE AD_Element_Trl
SET IsTranslated='N',Updated=TO_TIMESTAMP('2026-08-13 00:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585298 AND AD_Language='de_CH'
;

-- 2026-08-13T00:00:01.010Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585298,'de_CH')
;

-- 2026-08-13T00:00:01.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585298,'de_CH')
;

-- Element: IsCreateDeliveryPlanning (de_DE, base language)
-- 2026-08-13T00:00:02.000Z
UPDATE AD_Element_Trl
SET IsTranslated='N',Updated=TO_TIMESTAMP('2026-08-13 00:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585298 AND AD_Language='de_DE'
;

-- 2026-08-13T00:00:02.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585298,'de_DE')
;

-- Element: IsCreateDeliveryPlanning (en_US)
-- 2026-08-13T00:00:03.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Create Delivery Planning', PrintName='Create Delivery Planning',
    Updated=TO_TIMESTAMP('2026-08-13 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585298 AND AD_Language='en_US'
;

-- 2026-08-13T00:00:03.010Z
UPDATE AD_Element base
SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-08-13T00:00:03.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585298,'en_US')
;

-- Column: M_Shipper.IsCreateDeliveryPlanning
-- AD_Table_ID=253 (M_Shipper), AD_Reference_ID=20 (Yes-No)
-- 2026-08-13T00:00:04.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version)
VALUES (0,593302 /*From ID Server*/,585298,0,20,253,'XX','IsCreateDeliveryPlanning',TO_TIMESTAMP('2026-08-13 00:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferplanung erstellen',0,0,TO_TIMESTAMP('2026-08-13 00:00:04','YYYY-MM-DD HH24:MI:SS'),100,'NP',0)
;

-- 2026-08-13T00:00:04.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593302
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-13T00:00:04.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(585298)
;

-- Physical column: M_Shipper.IsCreateDeliveryPlanning CHAR(1) DEFAULT 'N' NOT NULL
-- 2026-08-13T00:00:05.000Z
/* DDL */ SELECT public.db_alter_table('M_Shipper','ALTER TABLE public.M_Shipper ADD COLUMN IsCreateDeliveryPlanning CHAR(1) DEFAULT ''N'' CHECK (IsCreateDeliveryPlanning IN (''Y'',''N'')) NOT NULL')
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Lieferplanung erstellen
-- Column: M_Shipper.IsCreateDeliveryPlanning
-- 2026-08-13T00:00:06.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593302,782286 /*From ID Server*/,0,185,TO_TIMESTAMP('2026-08-13 00:00:06','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Lieferplanung erstellen',TO_TIMESTAMP('2026-08-13 00:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-08-13T00:00:06.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782286
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-08-13T00:00:06.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585298)
;

-- 2026-08-13T00:00:06.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782286
;

-- 2026-08-13T00:00:06.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(782286)
;

-- UI Element: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 10 -> flag.Lieferplanung erstellen
-- Column: M_Shipper.IsCreateDeliveryPlanning
-- Placed in flag group (541020, col 20, sec 10), SeqNo 40 (after API Lieferweg-Abfrage at 30)
-- 2026-08-13T00:00:07.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,782286,0,185,541020,653136 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-13 00:00:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferplanung erstellen',40,0,0,TO_TIMESTAMP('2026-08-13 00:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;
