-- Run mode: SWING_CLIENT

-- 2024-09-30T11:42:36.444Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583294,0,'FreeStorageCostDays',TO_TIMESTAMP('2024-09-30 13:42:36.186','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Kostenlose Lagertage','Kostenlose Lagertage',TO_TIMESTAMP('2024-09-30 13:42:36.186','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-30T11:42:36.468Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583294 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FreeStorageCostDays
-- 2024-09-30T11:46:58.993Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-30 13:46:58.993','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583294 AND AD_Language='de_CH'
;

-- 2024-09-30T11:46:59.015Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583294,'de_CH')
;

-- Element: FreeStorageCostDays
-- 2024-09-30T11:46:59.845Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-30 13:46:59.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583294 AND AD_Language='de_DE'
;

-- 2024-09-30T11:46:59.847Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583294,'de_DE')
;

-- 2024-09-30T11:46:59.848Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583294,'de_DE')
;

-- Element: FreeStorageCostDays
-- 2024-09-30T11:47:49.188Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Free Storage Days', PrintName='Free Storage Days',Updated=TO_TIMESTAMP('2024-09-30 13:47:49.188','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583294 AND AD_Language='en_US'
;

-- 2024-09-30T11:47:49.191Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583294,'en_US')
;

-- Column: ModCntr_Settings.FreeStorageCostDays
-- 2024-09-30T11:50:17.383Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589176,583294,0,11,542339,'FreeStorageCostDays',TO_TIMESTAMP('2024-09-30 13:50:17.25','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','de.metas.contracts',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kostenlose Lagertage',0,0,TO_TIMESTAMP('2024-09-30 13:50:17.25','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-30T11:50:17.385Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589176 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-30T11:50:17.390Z
/* DDL */  select update_Column_Translation_From_AD_Element(583294)
;

-- 2024-09-30T11:50:19.268Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN FreeStorageCostDays NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Kostenlose Lagertage
-- Column: ModCntr_Settings.FreeStorageCostDays
-- 2024-09-30T11:50:59.312Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589176,731794,0,547013,TO_TIMESTAMP('2024-09-30 13:50:59.162','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Kostenlose Lagertage',TO_TIMESTAMP('2024-09-30 13:50:59.162','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-30T11:50:59.314Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-30T11:50:59.322Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583294)
;

-- 2024-09-30T11:50:59.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731794
;

-- 2024-09-30T11:50:59.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731794)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Kostenlose Lagertage
-- Column: ModCntr_Settings.FreeStorageCostDays
-- 2024-09-30T11:51:38.073Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731794,0,547013,551809,626108,'F',TO_TIMESTAMP('2024-09-30 13:51:37.927','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Kostenlose Lagertage',50,0,0,TO_TIMESTAMP('2024-09-30 13:51:37.927','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Kostenlose Lagertage
-- Column: ModCntr_Settings.FreeStorageCostDays
-- 2024-09-30T11:52:50.631Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''Y''',Updated=TO_TIMESTAMP('2024-09-30 13:52:50.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731794
;

