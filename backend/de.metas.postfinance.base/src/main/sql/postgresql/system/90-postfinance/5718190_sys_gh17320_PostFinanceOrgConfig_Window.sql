-- Run mode: SWING_CLIENT

-- 2024-02-29T16:59:18.708Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582983,0,'IsArchiveData',TO_TIMESTAMP('2024-02-29 18:59:17.608','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Archive Data','Archive Data',TO_TIMESTAMP('2024-02-29 18:59:17.608','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-29T16:59:18.741Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582983 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsArchiveData
-- 2024-02-29T16:59:42.310Z
UPDATE AD_Element_Trl SET Name='Daten archivieren', PrintName='Daten archivieren',Updated=TO_TIMESTAMP('2024-02-29 18:59:42.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582983 AND AD_Language='de_CH'
;

-- 2024-02-29T16:59:42.344Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582983,'de_CH')
;

-- Element: IsArchiveData
-- 2024-02-29T16:59:45.277Z
UPDATE AD_Element_Trl SET Name='Daten archivieren', PrintName='Daten archivieren',Updated=TO_TIMESTAMP('2024-02-29 18:59:45.277','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582983 AND AD_Language='de_DE'
;

-- 2024-02-29T16:59:45.278Z
UPDATE AD_Element SET Name='Daten archivieren', PrintName='Daten archivieren' WHERE AD_Element_ID=582983
;

-- 2024-02-29T16:59:45.773Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582983,'de_DE')
;

-- 2024-02-29T16:59:45.776Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582983,'de_DE')
;

-- Element: IsArchiveData
-- 2024-02-29T16:59:48.032Z
UPDATE AD_Element_Trl SET Name='Daten archivieren', PrintName='Daten archivieren',Updated=TO_TIMESTAMP('2024-02-29 18:59:48.032','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582983 AND AD_Language='fr_CH'
;

-- 2024-02-29T16:59:48.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582983,'fr_CH')
;

-- Element: IsArchiveData
-- 2024-02-29T16:59:49.921Z
UPDATE AD_Element_Trl SET Name='Daten archivieren', PrintName='Daten archivieren',Updated=TO_TIMESTAMP('2024-02-29 18:59:49.921','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582983 AND AD_Language='it_IT'
;

-- 2024-02-29T16:59:49.923Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582983,'it_IT')
;

-- Column: PostFinance_Org_Config.IsArchiveData
-- 2024-02-29T17:00:24.191Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587966,582983,0,20,542386,'IsArchiveData',TO_TIMESTAMP('2024-02-29 19:00:24.006','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Daten archivieren',0,0,TO_TIMESTAMP('2024-02-29 19:00:24.006','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-29T17:00:24.196Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587966 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-29T17:00:24.204Z
/* DDL */  select update_Column_Translation_From_AD_Element(582983)
;

-- 2024-02-29T17:00:26.351Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Org_Config','ALTER TABLE public.PostFinance_Org_Config ADD COLUMN IsArchiveData CHAR(1) DEFAULT ''N'' CHECK (IsArchiveData IN (''Y'',''N'')) NOT NULL')
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Daten archivieren
-- Column: PostFinance_Org_Config.IsArchiveData
-- 2024-02-29T17:00:47.306Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587966,725346,0,547355,TO_TIMESTAMP('2024-02-29 19:00:47.12','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Daten archivieren',TO_TIMESTAMP('2024-02-29 19:00:47.12','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-29T17:00:47.310Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-29T17:00:47.314Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582983)
;

-- 2024-02-29T17:00:47.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725346
;

-- 2024-02-29T17:00:47.362Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725346)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Daten archivieren
-- Column: PostFinance_Org_Config.IsArchiveData
-- 2024-02-29T17:01:10.810Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725346,0,547355,623014,551424,'F',TO_TIMESTAMP('2024-02-29 19:01:10.598','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Daten archivieren',21,0,0,TO_TIMESTAMP('2024-02-29 19:01:10.598','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Daten archivieren
-- Column: PostFinance_Org_Config.IsArchiveData
-- 2024-02-29T17:17:03.279Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-02-29 19:17:03.279','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623014
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Aktiv
-- Column: PostFinance_Org_Config.IsActive
-- 2024-02-29T17:17:03.288Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-02-29 19:17:03.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622121
;

-- Tab: PostFinance Kunden-Registrierungsnachricht(541769,D) -> PostFinance Kunden-Registrierungsnachricht
-- Table: PostFinance_Customer_Registration_Message
-- 2024-03-01T14:33:21.103Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2024-03-01 16:33:21.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547410
;

