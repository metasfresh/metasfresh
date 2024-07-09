-- 2023-12-22T13:41:39.531489400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582878,0,'FilenamePattern',TO_TIMESTAMP('2023-12-22 15:41:39.142','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Filename Pattern','Filename Pattern',TO_TIMESTAMP('2023-12-22 15:41:39.142','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-22T13:41:39.543908200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582878 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Process.FilenamePattern
-- 2023-12-22T13:41:46.619296800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587765,582878,0,10,284,'FilenamePattern',TO_TIMESTAMP('2023-12-22 15:41:46.435','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Filename Pattern',0,0,TO_TIMESTAMP('2023-12-22 15:41:46.435','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-22T13:41:46.622980400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587765 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-22T13:41:47.254306400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582878) 
;

alter table ad_process add column FilenamePattern varchar(2000);

-- 2023-12-22T13:43:25.516399700Z
INSERT INTO t_alter_column values('ad_process','FilenamePattern','VARCHAR(2000)',null,null)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Filename Pattern
-- Column: AD_Process.FilenamePattern
-- 2023-12-22T13:44:15.803958600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587765,723342,0,245,TO_TIMESTAMP('2023-12-22 15:44:15.609','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D','Y','N','N','N','N','N','N','N','Filename Pattern',TO_TIMESTAMP('2023-12-22 15:44:15.609','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-22T13:44:15.807105400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-22T13:44:15.810264300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582878) 
;

-- 2023-12-22T13:44:15.821758400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723342
;

-- 2023-12-22T13:44:15.823956300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723342)
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.Filename Pattern
-- Column: AD_Process.FilenamePattern
-- 2023-12-22T13:47:34.589827Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723342,0,245,541397,622090,'F',TO_TIMESTAMP('2023-12-22 15:47:34.416','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Filename Pattern',80,0,0,TO_TIMESTAMP('2023-12-22 15:47:34.416','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Filename Pattern
-- Column: AD_Process.FilenamePattern
-- 2023-12-22T13:48:41.384207700Z
UPDATE AD_Field SET DisplayLogic='@IsReport@=''Y''', IsDisplayed='Y', SeqNo=237,Updated=TO_TIMESTAMP('2023-12-22 15:48:41.384','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723342
;

