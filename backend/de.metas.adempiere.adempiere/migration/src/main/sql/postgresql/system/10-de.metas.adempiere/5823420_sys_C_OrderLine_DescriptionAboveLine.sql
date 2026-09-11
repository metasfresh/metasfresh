-- Run mode: SWING_CLIENT

-- New column C_OrderLine.DescriptionAboveLine (Text, nullable, 2048 chars) + a dedicated,
-- reusable AD_Element keyed on ColumnName='DescriptionAboveLine'. The element is intentionally
-- generic (not scoped to C_OrderLine) so it can later be reused for the same-named column on
-- another table. Description/Help are left empty on purpose -- a follow-up change fills them
-- once the feature's observed behaviour is known. No AD_Field/AD_UI_Element is created here --
-- a follow-up change places the field on the window.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_Element 585448 (ColumnName=DescriptionAboveLine, reusable across multiple tables)
--   AD_Column  593531 (C_OrderLine.DescriptionAboveLine)

-- Element: DescriptionAboveLine
-- 2026-09-09T12:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585448 /*From ID Server*/,0,'DescriptionAboveLine',TO_TIMESTAMP('2026-09-09 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,NULL,'D',NULL,'Y','Freitext über Position','Freitext über Position',TO_TIMESTAMP('2026-09-09 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-09T12:00:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585448 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DescriptionAboveLine (de_CH mirrors de_DE base text)
-- 2026-09-09T12:00:02.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585448 AND AD_Language='de_CH'
;

-- 2026-09-09T12:00:03.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585448,'de_CH')
;

-- Element: DescriptionAboveLine (de_DE base language)
-- 2026-09-09T12:00:04.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585448 AND AD_Language='de_DE'
;

-- 2026-09-09T12:00:05.000Z
/* DDL */ select update_ad_element_on_ad_element_trl_update(585448,'de_DE')
;

-- 2026-09-09T12:00:06.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585448,'de_DE')
;

-- Element: DescriptionAboveLine (en_US override)
-- 2026-09-09T12:00:07.000Z
UPDATE AD_Element_Trl SET Description=NULL, Help=NULL, IsTranslated='Y', Name='Free text above line', PrintName='Free text above line', Updated=TO_TIMESTAMP('2026-09-09 12:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585448 AND AD_Language='en_US'
;

-- 2026-09-09T12:00:08.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585448,'en_US')
;

-- Column: C_OrderLine.DescriptionAboveLine
-- AD_Table_ID=260 (C_OrderLine), AD_Reference_ID=14 (Text), FieldLength=2048, IsMandatory=N
-- 2026-09-09T12:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version)
VALUES (0,593531 /*From ID Server*/,585448,0,14,260,'XX','DescriptionAboveLine',TO_TIMESTAMP('2026-09-09 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2048,'Y','N','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Freitext über Position',0,0,TO_TIMESTAMP('2026-09-09 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,'NP',0)
;

-- 2026-09-09T12:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593531
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-09T12:01:02.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(585448)
;

-- Physical column: C_OrderLine.DescriptionAboveLine VARCHAR(2048)
-- 2026-09-09T12:02:00.000Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN DescriptionAboveLine VARCHAR(2048)')
;
