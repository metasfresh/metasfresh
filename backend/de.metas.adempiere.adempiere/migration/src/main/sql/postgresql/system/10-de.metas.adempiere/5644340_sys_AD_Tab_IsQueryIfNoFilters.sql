-- 2022-06-20T12:32:05.882Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581046,0,'IsQueryIfNoFilters',TO_TIMESTAMP('2022-06-20 15:32:05','YYYY-MM-DD HH24:MI:SS'),100,'Allow view querying even if user didn''t apply any filter.','D','Usually we want this for any window/tab.
But in case the window/tab has a huge amount of data it makes no sense to query because we will just it and waste computing power and time to load.
The user will filter it anyway.','Y','Allow querying when not filtered','Allow querying when not filtered',TO_TIMESTAMP('2022-06-20 15:32:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-20T12:32:05.891Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Tab.IsQueryIfNoFilters
-- 2022-06-20T12:32:23.058Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583387,581046,0,20,106,'IsQueryIfNoFilters',TO_TIMESTAMP('2022-06-20 15:32:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Allow view querying even if user didn''t apply any filter.','D',0,1,'Usually we want this for any window/tab.
But in case the window/tab has a huge amount of data it makes no sense to query because we will just it and waste computing power and time to load.
The user will filter it anyway.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Allow querying when not filtered',0,0,TO_TIMESTAMP('2022-06-20 15:32:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-20T12:32:23.064Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-20T12:32:23.145Z
/* DDL */  select update_Column_Translation_From_AD_Element(581046) 
;

-- 2022-06-20T12:32:24.042Z
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN IsQueryIfNoFilters CHAR(1) DEFAULT ''Y'' CHECK (IsQueryIfNoFilters IN (''Y'',''N'')) NOT NULL')
;

-- Field: Fenster Verwaltung -> Register -> Allow querying when not filtered
-- Column: AD_Tab.IsQueryIfNoFilters
-- 2022-06-20T12:33:34.902Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583387,700731,0,106,TO_TIMESTAMP('2022-06-20 15:33:34','YYYY-MM-DD HH24:MI:SS'),100,'Allow view querying even if user didn''t apply any filter.',1,'D','Usually we want this for any window/tab.
But in case the window/tab has a huge amount of data it makes no sense to query because we will just it and waste computing power and time to load.
The user will filter it anyway.','Y','N','N','N','N','N','N','N','Allow querying when not filtered',TO_TIMESTAMP('2022-06-20 15:33:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-20T12:33:34.908Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700731 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-20T12:33:34.915Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581046) 
;

-- 2022-06-20T12:33:34.942Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700731
;

-- 2022-06-20T12:33:34.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700731)
;

-- Field: Fenster Verwaltung -> Register -> Allow querying when not filtered
-- Column: AD_Tab.IsQueryIfNoFilters
-- 2022-06-20T12:34:17.104Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=415,Updated=TO_TIMESTAMP('2022-06-20 15:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700731
;

-- Field: Fenster Verwaltung -> Register -> Query data on load
-- Column: AD_Tab.IsQueryOnLoad
-- 2022-06-20T12:35:27.985Z
UPDATE AD_Field SET DisplayLogic='@TabLevel@=0',Updated=TO_TIMESTAMP('2022-06-20 15:35:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542858
;

-- Field: Fenster Verwaltung -> Register -> Query data on load
-- Column: AD_Tab.IsQueryOnLoad
-- 2022-06-20T12:35:32.813Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-06-20 15:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542858
;

-- Field: Fenster Verwaltung -> Register -> Allow querying when not filtered
-- Column: AD_Tab.IsQueryIfNoFilters
-- 2022-06-20T12:35:53.007Z
UPDATE AD_Field SET DisplayLogic='@TabLevel@=0',Updated=TO_TIMESTAMP('2022-06-20 15:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700731
;

