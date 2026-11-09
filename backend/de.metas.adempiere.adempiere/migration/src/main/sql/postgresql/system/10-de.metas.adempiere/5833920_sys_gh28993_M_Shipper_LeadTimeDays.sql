-- Add M_Shipper.LeadTimeDays (Integer, nullable, not mandatory) + AD metadata + field on the Lieferweg window.
-- Number of (working) days the shipper needs to deliver; empty means "no lead time configured".
--
-- IDs allocated from idserver.metas.de on 2026-11-09:
--   AD_Element    585613
--   AD_Column     593825 (M_Shipper.LeadTimeDays)
--   AD_Field      788177 (Lieferweg window 142 / tab 185)
--   AD_UI_Element 656494 (main group 541019, SeqNo 60)

-- Element: LeadTimeDays
-- 2026-11-09T10:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585613 /*From ID Server*/,0,'LeadTimeDays',TO_TIMESTAMP('2026-11-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferzeit (Tage)','Lieferzeit (Tage)',TO_TIMESTAMP('2026-11-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-11-09T10:00:00.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585613
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LeadTimeDays (de_CH, same text as de_DE)
-- 2026-11-09T10:00:01.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585613 AND AD_Language='de_CH'
;

-- 2026-11-09T10:00:01.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585613,'de_CH')
;

-- Element: LeadTimeDays (de_DE, base language)
-- 2026-11-09T10:00:02.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585613 AND AD_Language='de_DE'
;

-- 2026-11-09T10:00:02.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585613,'de_DE')
;

-- Element: LeadTimeDays (en_US)
-- 2026-11-09T10:00:03.000Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Lead time (days)', PrintName='Lead time (days)',
    Updated=TO_TIMESTAMP('2026-11-09 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585613 AND AD_Language='en_US'
;

-- 2026-11-09T10:00:03.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585613,'en_US')
;

-- Column: M_Shipper.LeadTimeDays
-- AD_Table_ID=253 (M_Shipper), AD_Reference_ID=11 (Integer), nullable
-- 2026-11-09T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version)
VALUES (0,593825 /*From ID Server*/,585613,0,11,253,'XX','LeadTimeDays',TO_TIMESTAMP('2026-11-09 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferzeit (Tage)',0,0,TO_TIMESTAMP('2026-11-09 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'NP',0)
;

-- 2026-11-09T10:01:00.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593825
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-11-09T10:01:00.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(585613)
;

-- Physical column: M_Shipper.LeadTimeDays INTEGER (nullable)
-- 2026-11-09T10:01:01.000Z
/* DDL */ SELECT public.db_alter_table('M_Shipper','ALTER TABLE public.M_Shipper ADD COLUMN LeadTimeDays INTEGER')
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Lieferzeit (Tage)
-- 2026-11-09T10:02:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593825,788177 /*From ID Server*/,0,185,TO_TIMESTAMP('2026-11-09 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','Lieferzeit (Tage)',TO_TIMESTAMP('2026-11-09 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-11-09T10:02:00.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=788177
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-09T10:02:00.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585613)
;

-- 2026-11-09T10:02:00.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788177
;

-- 2026-11-09T10:02:00.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(788177)
;

-- UI Element: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 10 -> Lieferzeit (Tage)
-- Placed in main group (541019), SeqNo 60 (after Priorität at 50)
-- 2026-11-09T10:02:30.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,788177,0,185,541019,656494 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-09 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferzeit (Tage)',60,0,0,TO_TIMESTAMP('2026-11-09 10:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;
