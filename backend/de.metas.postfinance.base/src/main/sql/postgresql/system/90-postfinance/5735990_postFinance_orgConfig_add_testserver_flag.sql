-- 2024-10-07T10:47:30.099Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583307,0,'IsTestserver',TO_TIMESTAMP('2024-10-07 12:47:29.941','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Testserver','Testserver',TO_TIMESTAMP('2024-10-07 12:47:29.941','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-07T10:47:30.105Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583307 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_Org_Config.IsTestserver
-- 2024-10-07T10:48:06.581Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589244,583307,0,20,542386,'IsTestserver',TO_TIMESTAMP('2024-10-07 12:48:06.466','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Testserver',0,0,TO_TIMESTAMP('2024-10-07 12:48:06.466','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-07T10:48:06.584Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589244 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-07T10:48:06.609Z
/* DDL */  select update_Column_Translation_From_AD_Element(583307)
;

-- 2024-10-07T10:48:16.777Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Org_Config','ALTER TABLE public.PostFinance_Org_Config ADD COLUMN IsTestserver CHAR(1) DEFAULT ''Y'' CHECK (IsTestserver IN (''Y'',''N'')) NOT NULL')
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Testserver
-- Column: PostFinance_Org_Config.IsTestserver
-- 2024-10-07T10:48:51.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589244,731849,0,547355,TO_TIMESTAMP('2024-10-07 12:48:51.453','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Testserver',TO_TIMESTAMP('2024-10-07 12:48:51.453','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-07T10:48:51.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-07T10:48:51.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583307)
;

-- 2024-10-07T10:48:51.580Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731849
;

-- 2024-10-07T10:48:51.581Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731849)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Testserver
-- Column: PostFinance_Org_Config.IsTestserver
-- 2024-10-07T10:49:47.136Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731849,0,547355,551424,626151,'F',TO_TIMESTAMP('2024-10-07 12:49:47.023','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Testserver',15,0,0,TO_TIMESTAMP('2024-10-07 12:49:47.023','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Testserver
-- Column: PostFinance_Org_Config.IsTestserver
-- 2024-10-07T10:50:00.210Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-07 12:50:00.21','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626151
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Verwenden Sie den Papierrechnungsservice
-- Column: PostFinance_Org_Config.IsUsePaperBill
-- 2024-10-07T10:50:00.217Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-07 12:50:00.217','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622118
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Daten archivieren
-- Column: PostFinance_Org_Config.IsArchiveData
-- 2024-10-07T10:50:00.221Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-07 12:50:00.221','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623014
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Aktiv
-- Column: PostFinance_Org_Config.IsActive
-- 2024-10-07T10:50:00.227Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-07 12:50:00.227','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622121
;

