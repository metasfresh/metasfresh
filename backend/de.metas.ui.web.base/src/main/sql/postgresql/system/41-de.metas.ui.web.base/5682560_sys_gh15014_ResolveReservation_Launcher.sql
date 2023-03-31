-- 2023-03-27T06:14:07.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582168,0,'ResolvedHours',TO_TIMESTAMP('2023-03-27 09:14:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Resolved hours','Resolved hours',TO_TIMESTAMP('2023-03-27 09:14:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T06:14:07.272Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582168 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T06:14:28.294Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586362,582168,0,11,542159,'ResolvedHours',TO_TIMESTAMP('2023-03-27 09:14:28','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Resolved hours',0,0,TO_TIMESTAMP('2023-03-27 09:14:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T06:14:28.298Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-27T06:14:28.355Z
/* DDL */  select update_Column_Translation_From_AD_Element(582168) 
;

-- 2023-03-27T06:14:30.034Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN ResolvedHours NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Prüfauftrag -> Prüfschritt -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T06:20:59.938Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586362,712945,0,546559,0,TO_TIMESTAMP('2023-03-27 09:20:59','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Resolved hours',0,10,0,1,1,TO_TIMESTAMP('2023-03-27 09:20:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T06:20:59.945Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-27T06:20:59.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582168) 
;

-- 2023-03-27T06:20:59.963Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712945
;

-- 2023-03-27T06:20:59.974Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712945)
;

-- commented out to prevent error:
-- ERROR:  insert or update on table "ad_ui_elementgroup" violates foreign key constraint "aduicolumn_aduielementgroup"
-- DETAIL:  Key (ad_ui_column_id)=(546300) is not present in table "ad_ui_column".
-- 2023-03-27T06:21:25.521Z
--INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546300,550447,TO_TIMESTAMP('2023-03-27 09:21:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','reservation',25,TO_TIMESTAMP('2023-03-27 09:21:25','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- UI Element: Prüfauftrag -> Prüfschritt.Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T06:21:34.071Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712945,0,546559,616066,550447,'F',TO_TIMESTAMP('2023-03-27 09:21:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Resolved hours',10,0,0,TO_TIMESTAMP('2023-03-27 09:21:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüfauftrag -> Prüfschritt -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T06:22:20.696Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-27 09:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712945
;

-- 2023-03-27T06:23:35.989Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582169,0,'IsFullyResolved',TO_TIMESTAMP('2023-03-27 09:23:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fully Resolved','Fully Resolved',TO_TIMESTAMP('2023-03-27 09:23:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T06:23:35.994Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582169 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-27T06:23:48.558Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586363,582169,0,20,542159,'IsFullyResolved',TO_TIMESTAMP('2023-03-27 09:23:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Fully Resolved',0,0,TO_TIMESTAMP('2023-03-27 09:23:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T06:23:48.559Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586363 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-27T06:23:48.561Z
/* DDL */  select update_Column_Translation_From_AD_Element(582169) 
;

-- 2023-03-27T06:23:49.178Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN IsFullyResolved CHAR(1) DEFAULT ''N'' CHECK (IsFullyResolved IN (''Y'',''N'')) NOT NULL')
;

-- Field: Prüfauftrag -> Prüfschritt -> Fully Resolved
-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-27T06:24:09.365Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586363,712946,0,546559,0,TO_TIMESTAMP('2023-03-27 09:24:09','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Fully Resolved',0,20,0,1,1,TO_TIMESTAMP('2023-03-27 09:24:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T06:24:09.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-27T06:24:09.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582169) 
;

-- 2023-03-27T06:24:09.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712946
;

-- 2023-03-27T06:24:09.375Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712946)
;

-- UI Element: Prüfauftrag -> Prüfschritt.Fully Resolved
-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-27T06:24:19.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712946,0,546559,616067,550447,'F',TO_TIMESTAMP('2023-03-27 09:24:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fully Resolved',20,0,0,TO_TIMESTAMP('2023-03-27 09:24:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T09:40:26.263Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582170,0,'ReservedHours',TO_TIMESTAMP('2023-03-27 12:40:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reserved hours','Reserved hours',TO_TIMESTAMP('2023-03-27 12:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T09:40:26.272Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582170 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-27T09:58:37.745Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586364,582170,0,11,542159,'ReservedHours','select sum(r.duration) from c_project_wo_step step join c_project_wo_resource r on step.c_project_wo_step_id = r.c_project_wo_step_id where step.c_project_wo_step_id = C_Project_WO_Step.C_Project_WO_Step_ID',TO_TIMESTAMP('2023-03-27 12:58:37','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Reserved hours',0,0,TO_TIMESTAMP('2023-03-27 12:58:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T09:58:37.750Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-27T09:58:37.764Z
/* DDL */  select update_Column_Translation_From_AD_Element(582170) 
;

-- 2023-03-27T10:04:09.709Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,586364,0,540144,542159,TO_TIMESTAMP('2023-03-27 13:04:09','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',583230,542159,TO_TIMESTAMP('2023-03-27 13:04:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-27T10:12:49.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586364,712947,0,546559,0,TO_TIMESTAMP('2023-03-27 13:12:49','YYYY-MM-DD HH24:MI:SS'),100,0,'','D',0,'Y','Y','Y','N','N','N','N','N','Reserved hours',0,30,0,1,1,TO_TIMESTAMP('2023-03-27 13:12:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:12:49.843Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712947 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-27T10:12:49.846Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582170) 
;

-- 2023-03-27T10:12:49.852Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712947
;

-- 2023-03-27T10:12:49.862Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712947)
;

-- UI Element: Prüfauftrag -> Prüfschritt.Fully Resolved
-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-27T10:13:04Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616067
;

-- 2023-03-27T10:13:04.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712946
;

-- Field: Prüfauftrag -> Prüfschritt -> Fully Resolved
-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-27T10:13:04.012Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712946
;

-- 2023-03-27T10:13:04.017Z
DELETE FROM AD_Field WHERE AD_Field_ID=712946
;

-- UI Element: Prüfauftrag -> Prüfschritt.Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-27T10:13:19.758Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712947,0,546559,616068,550447,'F',TO_TIMESTAMP('2023-03-27 13:13:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reserved hours',20,0,0,TO_TIMESTAMP('2023-03-27 13:13:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Prüfauftrag -> Prüfschritt -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T10:15:19.857Z
UPDATE AD_Field SET DefaultValue='@C_ProjectType_ID@ = 540011',Updated=TO_TIMESTAMP('2023-03-27 13:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712945
;

-- Field: Prüfauftrag -> Prüfschritt -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T10:16:26.088Z
UPDATE AD_Field SET DefaultValue='', DisplayLogic='@C_ProjectType_ID@ = 540011',Updated=TO_TIMESTAMP('2023-03-27 13:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712945
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-27T10:17:01.886Z
UPDATE AD_Field SET DisplayLogic='@C_ProjectType_ID@ = 540011',Updated=TO_TIMESTAMP('2023-03-27 13:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712947
;

-- 2023-03-27T10:20:30.273Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582171,0,'ReservationOrderAvailable',TO_TIMESTAMP('2023-03-27 13:20:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reservation order available','Reservation order available',TO_TIMESTAMP('2023-03-27 13:20:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:20:30.276Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582171 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-27T10:20:51.497Z
UPDATE AD_Element SET ColumnName='IsReservationOrderAvailable',Updated=TO_TIMESTAMP('2023-03-27 13:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582171
;

-- 2023-03-27T10:20:51.583Z
UPDATE AD_Column SET ColumnName='IsReservationOrderAvailable', Name='Reservation order available', Description=NULL, Help=NULL WHERE AD_Element_ID=582171
;

-- 2023-03-27T10:20:51.584Z
UPDATE AD_Process_Para SET ColumnName='IsReservationOrderAvailable', Name='Reservation order available', Description=NULL, Help=NULL, AD_Element_ID=582171 WHERE UPPER(ColumnName)='ISRESERVATIONORDERAVAILABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-27T10:20:51.601Z
UPDATE AD_Process_Para SET ColumnName='IsReservationOrderAvailable', Name='Reservation order available', Description=NULL, Help=NULL WHERE AD_Element_ID=582171 AND IsCentrallyMaintained='Y'
;

-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:23:31.343Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586365,582171,0,20,203,'IsReservationOrderAvailable','CASE  WHEN     (SELECT count(*) from c_project p     where C_Project.C_Project_Parent_ID = p.c_project_parent_id     AND p.c_projecttype_id = 540011)     <> 0 THEN ''Y''            ELSE ''N'' END;',TO_TIMESTAMP('2023-03-27 13:23:31','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Reservation order available',0,0,TO_TIMESTAMP('2023-03-27 13:23:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T10:23:31.347Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-27T10:23:31.356Z
/* DDL */  select update_Column_Translation_From_AD_Element(582171) 
;

-- Field: Prüfauftrag -> Projekt -> Reservation order available
-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:24:58.241Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586365,712948,0,546558,0,TO_TIMESTAMP('2023-03-27 13:24:58','YYYY-MM-DD HH24:MI:SS'),100,0,'@ProjectCategory@ = ''W''','D',0,'Y','Y','Y','N','N','N','N','N','Reservation order available',0,450,0,1,1,TO_TIMESTAMP('2023-03-27 13:24:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:24:58.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-27T10:24:58.248Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582171) 
;

-- 2023-03-27T10:24:58.251Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712948
;

-- 2023-03-27T10:24:58.256Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712948)
;

-- UI Element: Prüfauftrag -> Projekt.Reservation order available
-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:25:33.187Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712948,0,546558,616069,549754,'F',TO_TIMESTAMP('2023-03-27 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reservation order available',100,0,0,TO_TIMESTAMP('2023-03-27 13:25:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:25:55.200Z
UPDATE AD_Column SET ColumnSQL='CASE  WHEN     (SELECT count(*) from c_project p     where C_Project.C_Project_Parent_ID = p.c_project_parent_id     AND p.c_projecttype_id = 540011)     <> 0 THEN ''Y''            ELSE ''N'' END',Updated=TO_TIMESTAMP('2023-03-27 13:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586365
;

-- Field: Prüfauftrag -> Projekt -> Reservation order available
-- Column: C_Project.IsReservationOrderAvailable
-- 2023-03-27T10:27:44.053Z
UPDATE AD_Field SET DisplayLogic='@ProjectCategory@ = ''W'' & @C_ProjectType_ID@ != 540011 ',Updated=TO_TIMESTAMP('2023-03-27 13:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712948
;

-- 2023-03-27T10:30:01.654Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582172,0,'ReservationOpen',TO_TIMESTAMP('2023-03-27 13:30:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reservation open','Reservation open',TO_TIMESTAMP('2023-03-27 13:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:30:01.657Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582172 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:30:29.016Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586366,582172,0,11,203,'ReservationOpen',TO_TIMESTAMP('2023-03-27 13:30:28','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reservation open',0,0,TO_TIMESTAMP('2023-03-27 13:30:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-27T10:30:29.020Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586366 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:53:42.936Z
UPDATE AD_Column SET ColumnSQL='select sum(c_project_wo_resource.duration)as totalSum from c_project c         join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011          and C_Project.C_Project_Parent_ID = c.project_parent_id)                                       - (select sum(step.woactualfacilityhours)as totalSum from c_project c         join c_project_wo_step step on c.c_project_id = step.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011         and C_Project.C_Project_Parent_ID = c.c_project_parent_id', IsLazyLoading='Y', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-03-27 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Field: Prüfauftrag -> Projekt -> Reservation open
-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:54:08.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586366,712949,0,546558,0,TO_TIMESTAMP('2023-03-27 13:54:07','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Reservation open',0,460,0,1,1,TO_TIMESTAMP('2023-03-27 13:54:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-27T10:54:08.195Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-27T10:54:08.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582172) 
;

-- 2023-03-27T10:54:08.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712949
;

-- 2023-03-27T10:54:08.209Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712949)
;

-- Field: Prüfauftrag -> Projekt -> Reservation open
-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:54:19.879Z
UPDATE AD_Field SET DisplayLogic='@ProjectCategory@ = ''W'' & @C_ProjectType_ID@ != 540011 ',Updated=TO_TIMESTAMP('2023-03-27 13:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712949
;

-- UI Element: Prüfauftrag -> Projekt.Reservation open
-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:54:31.126Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712949,0,546558,616070,549754,'F',TO_TIMESTAMP('2023-03-27 13:54:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reservation open',110,0,0,TO_TIMESTAMP('2023-03-27 13:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:56:25.107Z
UPDATE AD_Column SET ColumnSQL='select sum(c_project_wo_resource.duration)as totalSum from c_project c         join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011          and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                                       - (select sum(step.woactualfacilityhours)as totalSum from c_project c         join c_project_wo_step step on c.c_project_id = step.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011         and C_Project.C_Project_Parent_ID = c.c_project_parent_id',Updated=TO_TIMESTAMP('2023-03-27 13:56:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T10:59:10.721Z
UPDATE AD_Column SET ColumnSQL='select sum(c_project_wo_resource.duration)as totalSum from c_project c         join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011          and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                                       - (select sum(step.resolvedhours)as totalSum from c_project c         join c_project_wo_step step on c.c_project_id = step.c_project_id         where c.projectcategory = ''W''         and c_projecttype_id = 540011         and C_Project.C_Project_Parent_ID = c.c_project_parent_id',Updated=TO_TIMESTAMP('2023-03-27 13:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T11:47:21.901Z
UPDATE AD_Column SET ColumnSQL=' CASE            WHEN                C_Project.C_ProjectType_ID = 540007 THEN                ((select sum(c_project_wo_resource.duration) as totalSum                 from c_project c                   join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id                     where c.projectcategory = ''W''                      and c.c_projecttype_id = 540011                      and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                   -                 (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id                  where c.projectcategory = ''W''                    and c_projecttype_id = 540011                    and C_Project.C_Project_Parent_ID = c.c_project_parent_id))              WHEN C_Project.C_ProjectType_ID = 540011 THEN     ((select sum(c_project_wo_resource.duration) as totalSum       from c_project c       join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id)     - (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id)) END',Updated=TO_TIMESTAMP('2023-03-27 14:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Field: Prüfauftrag -> Projekt -> Reservation open
-- Column: C_Project.ReservationOpen
-- 2023-03-27T11:48:27.319Z
UPDATE AD_Field SET DisplayLogic='@ProjectCategory@ = ''W''',Updated=TO_TIMESTAMP('2023-03-27 14:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712949
;

-- Column: C_Project.ReservationOpen
-- 2023-03-27T11:50:49.202Z
UPDATE AD_Column SET ColumnSQL='CASE            WHEN                C_Project.C_ProjectType_ID = 540007 THEN                ((select sum(c_project_wo_resource.duration) as totalSum                 from c_project c                   join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id                     where c.projectcategory = ''W''                      and c.c_projecttype_id = 540011                      and C_Project.C_Project_Parent_ID = c.c_project_parent_id)                   -                 (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id                  where c.projectcategory = ''W''                    and c_projecttype_id = 540011                    and C_Project.C_Project_Parent_ID = c.c_project_parent_id))              WHEN C_Project.C_ProjectType_ID = 540011 THEN     ((select sum(c_project_wo_resource.duration) as totalSum       from c_project c       join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id       where C_Project.C_Project_ID = c.c_project_id)     - (select sum(step.resolvedhours) as resolvedHours                  from c_project c                      join c_project_wo_step step on c.c_project_id = step.c_project_id                  where C_Project.C_Project_ID = c.c_project_id)) END',Updated=TO_TIMESTAMP('2023-03-27 14:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- UI Element: Prüfauftrag -> Prüfschritt.Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-27T12:20:28.590Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-27 15:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616068
;

-- UI Element: Prüfauftrag -> Prüfschritt.Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-27T12:20:28.599Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-27 15:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616066
;

-- UI Element: Prüfauftrag -> Prüfschritt.Datum Teilbericht
-- Column: C_Project_WO_Step.WOPartialReportDate
-- 2023-03-27T12:20:28.604Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-27 15:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611746
;

-- 2023-03-28T05:50:01.006Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585245,'Y','de.metas.ui.web.project.step.process.C_Project_WO_Step_ResolveReservationView_Launcher','N',TO_TIMESTAMP('2023-03-28 08:50:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Resolve reservation from reservation order','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-28 08:50:00','YYYY-MM-DD HH24:MI:SS'),100,'Resolve reservation from reservation order')
;

-- 2023-03-28T05:50:01.019Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585245 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-03-28T05:50:20.523Z
UPDATE AD_Process_Trl SET Name='Reservierung aus Reservierungsauftrag auflösen',Updated=TO_TIMESTAMP('2023-03-28 08:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585245
;

-- 2023-03-28T05:50:23.130Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Reservierung aus Reservierungsauftrag auflösen',Updated=TO_TIMESTAMP('2023-03-28 08:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585245
;

-- 2023-03-28T05:50:23.114Z
UPDATE AD_Process_Trl SET Name='Reservierung aus Reservierungsauftrag auflösen',Updated=TO_TIMESTAMP('2023-03-28 08:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585245
;

-- 2023-03-28T05:52:34.063Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585245,203,541373,TO_TIMESTAMP('2023-03-28 08:52:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-03-28 08:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2023-03-28T05:56:46.833Z
UPDATE AD_Table_Process SET AD_Window_ID=541553,Updated=TO_TIMESTAMP('2023-03-28 08:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541373
;

-- 2023-03-28T07:31:51.110Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585246,'Y','de.metas.ui.web.project.step.process.C_Project_WO_Step_ResolveHours','N',TO_TIMESTAMP('2023-03-28 10:31:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Resolve hours','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-28 10:31:50','YYYY-MM-DD HH24:MI:SS'),100,'Resolve hours')
;

-- 2023-03-28T07:31:51.127Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585246 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-03-28T07:31:54.436Z
UPDATE AD_Process_Trl SET Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-28 10:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585246
;

-- 2023-03-28T07:32:13.063Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-28 10:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585246
;

-- 2023-03-28T07:32:13.045Z
UPDATE AD_Process_Trl SET Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-28 10:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585246
;

-- 2023-03-28T07:33:44.750Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582174,0,TO_TIMESTAMP('2023-03-28 10:33:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hours','Hours',TO_TIMESTAMP('2023-03-28 10:33:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T07:33:44.757Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582174 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-28T07:34:01.906Z
UPDATE AD_Element SET ColumnName='Hours',Updated=TO_TIMESTAMP('2023-03-28 10:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174
;

-- 2023-03-28T07:34:01.910Z
UPDATE AD_Column SET ColumnName='Hours', Name='Hours', Description=NULL, Help=NULL WHERE AD_Element_ID=582174
;

-- 2023-03-28T07:34:01.913Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Hours', Description=NULL, Help=NULL, AD_Element_ID=582174 WHERE UPPER(ColumnName)='HOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-28T07:34:01.916Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Hours', Description=NULL, Help=NULL WHERE AD_Element_ID=582174 AND IsCentrallyMaintained='Y'
;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2023-03-28T07:34:43.426Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582174,0,585246,542591,11,'Hours',TO_TIMESTAMP('2023-03-28 10:34:43','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Hours',10,TO_TIMESTAMP('2023-03-28 10:34:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T07:34:43.429Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542591 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-03-28T07:35:02.175Z
UPDATE AD_Process_Para SET ValueMin='1',Updated=TO_TIMESTAMP('2023-03-28 10:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542591
;

-- 2023-03-28T07:38:32.514Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585247,'Y','de.metas.ui.web.project.step.process.C_Project_WO_Step_UndoResolution','N',TO_TIMESTAMP('2023-03-28 10:38:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Undo reservation resolution','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-28 10:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Undo reservation resolution')
;

-- 2023-03-28T07:38:32.517Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585247 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-03-28T09:19:06.404Z
UPDATE AD_Process SET AccessLevel='1',Updated=TO_TIMESTAMP('2023-03-28 12:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585247
;

-- 2023-03-28T09:44:00.192Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,586365,0,540145,203,TO_TIMESTAMP('2023-03-28 12:43:59','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',582994,203,TO_TIMESTAMP('2023-03-28 12:43:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T09:44:28.398Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,586366,0,540146,203,TO_TIMESTAMP('2023-03-28 12:44:28','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',1349,203,TO_TIMESTAMP('2023-03-28 12:44:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T09:44:36.030Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=1349,Updated=TO_TIMESTAMP('2023-03-28 12:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

