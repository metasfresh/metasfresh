-- 2023-11-16T16:50:06.645160200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582807,0,'StoreProcessResultFilePath',TO_TIMESTAMP('2023-11-16 17:50:06.296','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Process Result Storing File Path','Process Result Storing File Path',TO_TIMESTAMP('2023-11-16 17:50:06.296','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-16T16:50:06.656154700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582807 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Process.StoreProcessResultFilePath
-- 2023-11-16T16:53:42.609995500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587652,582807,0,10,284,'StoreProcessResultFilePath',TO_TIMESTAMP('2023-11-16 17:53:42.395','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Process Result Storing File Path',0,0,TO_TIMESTAMP('2023-11-16 17:53:42.395','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-16T16:53:42.612075200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587652 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-16T16:53:43.121787100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582807)
;

-- 2023-11-16T16:53:43.121787100Z
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN StoreProcessResultFilePath VARCHAR(500)')
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Path
-- Column: AD_Process.StoreProcessResultFilePath
-- 2023-11-17T08:19:01.171222700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587652,721875,0,245,0,TO_TIMESTAMP('2023-11-17 09:19:00.775','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Process Result Storing File Path',0,350,0,1,1,TO_TIMESTAMP('2023-11-17 09:19:00.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-17T08:19:01.180426100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-17T08:19:01.199941600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582807)
;

-- 2023-11-17T08:19:01.221237500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721875
;

-- 2023-11-17T08:19:01.227627400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721875)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Path
-- Column: AD_Process.StoreProcessResultFilePath
-- 2023-11-17T08:19:58.117626700Z
UPDATE AD_Field SET DisplayLength=255,Updated=TO_TIMESTAMP('2023-11-17 09:19:58.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721875
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Path
-- Column: AD_Process.StoreProcessResultFilePath
-- 2023-11-17T08:20:34.201105400Z
UPDATE AD_Field SET SeqNo=85,Updated=TO_TIMESTAMP('2023-11-17 09:20:34.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721875
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Path
-- Column: AD_Process.StoreProcessResultFilePath
-- 2023-11-17T08:27:48.221418Z
UPDATE AD_Field SET DisplayLogic='@IsReport@=''Y''', SeqNo=235, SpanX=999,Updated=TO_TIMESTAMP('2023-11-17 09:27:48.221','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721875
;

