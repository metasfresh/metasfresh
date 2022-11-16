-- 2022-11-16T11:03:26.913Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581699,0,'IsSynchTranslation',TO_TIMESTAMP('2022-11-16 11:03:26.816','YYYY-MM-DD HH24:MI:SS.US'),100,'Synch Translation on Element Translation Update','D','Synch Translation on Element Translation Update','Y','Synch Translation','Synch Translation',TO_TIMESTAMP('2022-11-16 11:03:26.816','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-16T11:03:26.917Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581699 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Language.IsSynchTranslation
-- 2022-11-16T11:03:45.115Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585033,581699,0,20,111,'IsSynchTranslation',TO_TIMESTAMP('2022-11-16 11:03:45.053','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Synch Translation on Element Translation Update','D',0,1,'Synch Translation on Element Translation Update','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Synch Translation',0,0,TO_TIMESTAMP('2022-11-16 11:03:45.053','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-11-16T11:03:45.117Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585033 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:03:45.137Z
/* DDL */  select update_Column_Translation_From_AD_Element(581699) 
;

-- 2022-11-16T11:03:53.491Z
/* DDL */ SELECT public.db_alter_table('AD_Language','ALTER TABLE public.AD_Language ADD COLUMN IsSynchTranslation CHAR(1) DEFAULT ''N'' CHECK (IsSynchTranslation IN (''Y'',''N'')) NOT NULL')
;

-- Field: Sprache(106,D) -> Sprache(112,D) -> Synch Translation
-- Column: AD_Language.IsSynchTranslation
-- 2022-11-16T11:05:28.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585033,708062,0,112,0,TO_TIMESTAMP('2022-11-16 11:05:28.603','YYYY-MM-DD HH24:MI:SS.US'),100,'Synch Translation on Element Translation Update',0,'D','Synch Translation on Element Translation Update',0,'Y','Y','Y','N','N','N','N','N','Synch Translation',0,160,0,1,1,TO_TIMESTAMP('2022-11-16 11:05:28.603','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-16T11:05:28.693Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T11:05:28.695Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581699) 
;

-- 2022-11-16T11:05:28.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708062
;

-- 2022-11-16T11:05:28.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708062)
;


-- Field: Sprache(106,D) -> Sprache(112,D) -> Synch Translation
-- Column: AD_Language.IsSynchTranslation
-- 2022-11-16T11:10:22.812Z
UPDATE AD_Field SET IsSameLine='Y', SeqNo=135,Updated=TO_TIMESTAMP('2022-11-16 11:10:22.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708062
;

-- Field: Sprache(106,D) -> Sprache(112,D) -> Synch Translation
-- Column: AD_Language.IsSynchTranslation
-- 2022-11-16T11:10:57.937Z
UPDATE AD_Field SET DisplayLength=1,Updated=TO_TIMESTAMP('2022-11-16 11:10:57.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=708062
;


-- Column: AD_Language.IsSynchTranslation
-- 2022-11-16T11:16:58.928Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2022-11-16 11:16:58.928','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585033
;

-- 2022-11-16T11:27:27.680Z
INSERT INTO t_alter_column values('ad_language','IsSynchTranslation','CHAR(1)',null,'Y')
;

-- 2022-11-16T11:27:27.827Z
UPDATE AD_Language SET IsSynchTranslation='Y' WHERE IsSynchTranslation IS NULL
;


