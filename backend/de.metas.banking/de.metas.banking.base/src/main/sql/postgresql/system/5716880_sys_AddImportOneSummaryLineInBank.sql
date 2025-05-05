-- Run mode: SWING_CLIENT

-- 2024-02-07T14:12:40.271Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582959,0,'IsImportAsSingleSummaryLine',TO_TIMESTAMP('2024-02-07 16:12:40.062','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Import As Summary Line','Import As Summary Line',TO_TIMESTAMP('2024-02-07 16:12:40.062','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-07T14:12:40.282Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582959 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Bank.IsImportAsSingleSummaryLine
-- 2024-02-07T14:12:50.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587904,582959,0,20,296,'IsImportAsSingleSummaryLine',TO_TIMESTAMP('2024-02-07 16:12:50.172','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Import As Summary Line',0,0,TO_TIMESTAMP('2024-02-07 16:12:50.172','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T14:12:50.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587904 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T14:12:50.320Z
/* DDL */  select update_Column_Translation_From_AD_Element(582959)
;

-- 2024-02-07T14:12:55.175Z
/* DDL */ SELECT public.db_alter_table('C_Bank','ALTER TABLE public.C_Bank ADD COLUMN IsImportAsSingleSummaryLine CHAR(1) DEFAULT ''N'' CHECK (IsImportAsSingleSummaryLine IN (''Y'',''N'')) NOT NULL')
;

-- Field: Bank(158,D) -> Bank(227,D) -> Import As Summary Line
-- Column: C_Bank.IsImportAsSingleSummaryLine
-- 2024-02-07T14:14:47.051Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587904,725162,0,227,0,TO_TIMESTAMP('2024-02-07 16:14:46.909','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Import As Summary Line',0,110,0,1,1,TO_TIMESTAMP('2024-02-07 16:14:46.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-07T14:14:47.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-07T14:14:47.056Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582959)
;

-- 2024-02-07T14:14:47.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725162
;

-- 2024-02-07T14:14:47.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725162)
;

-- UI Element: Bank(158,D) -> Bank(227,D) -> main -> 10 -> default.Import As Summary Line
-- Column: C_Bank.IsImportAsSingleSummaryLine
-- 2024-02-07T14:15:14.628Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,725162,0,227,540313,622924,'F',TO_TIMESTAMP('2024-02-07 16:15:14.492','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Import As Summary Line',110,0,0,TO_TIMESTAMP('2024-02-07 16:15:14.492','YYYY-MM-DD HH24:MI:SS.US'),100)
;


update  ad_element_trl
set name ='Als Zusammenfassungszeile importieren', printname='Als Zusammenfassungszeile importieren'
where ad_element_id=582959
and ad_language in ('de_DE', 'de_CH');


update  ad_element
set name ='Als Zusammenfassungszeile importieren', printname='Als Zusammenfassungszeile importieren'
where ad_element_id=582959;

 select update_Column_Translation_From_AD_Element(582959)
;

select update_FieldTranslation_From_AD_Name_Element(582959)
;

update AD_UI_Element set name='Als Zusammenfassungszeile importieren'
where AD_Field_ID=725162;

select update_FieldTranslation_From_AD_Name_Element(582959);

update AD_UI_Element set name='Als Zusammenfassungszeile importieren'
where AD_Field_ID=725163;

update  ad_field_trl
set name ='Als Zusammenfassungszeile importieren'
where ad_field_id in (725162,725163)
  and ad_language in ('de_DE', 'de_CH');
