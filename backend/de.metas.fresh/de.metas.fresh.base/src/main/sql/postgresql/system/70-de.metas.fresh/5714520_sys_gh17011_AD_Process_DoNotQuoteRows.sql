-- 2023-12-21T08:31:19.393217900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582874,0,'DoNotQuoteRows',TO_TIMESTAMP('2023-12-21 10:31:19.241','YYYY-MM-DD HH24:MI:SS.US'),100,'If this is true the cell values in all rows will not be quoted anymore in the exported file.','D','Y','Do Not Quote Rows','Do Not Quote Rows',TO_TIMESTAMP('2023-12-21 10:31:19.241','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-21T08:31:19.399215200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582874 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T08:58:27.815686800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587763,582874,0,20,284,'DoNotQuoteRows',TO_TIMESTAMP('2023-12-21 10:58:27.636','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','If this is true the cell values in all rows will not be quoted anymore in the exported file.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Do Not Quote Rows',0,0,TO_TIMESTAMP('2023-12-21 10:58:27.636','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-21T08:58:27.822682800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587763 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-21T08:58:28.403034800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582874)
;

ALTER TABLE ad_process
    ADD DoNotQuoteRows CHAR(1);

-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T11:35:49.275659100Z
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2023-12-21 13:35:49.275','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587763
;

-- 2023-12-21T11:35:50.212277400Z
INSERT INTO t_alter_column values('ad_process','DoNotQuoteRows','CHAR(1)',null,'N')
;

-- 2023-12-21T08:35:04.902632200Z
UPDATE AD_Process SET DoNotQuoteRows='N' WHERE DoNotQuoteRows IS NULL
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Do Not Quote Headers
-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T11:57:31.814106900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587763,723341,0,245,TO_TIMESTAMP('2023-12-21 13:57:31.545','YYYY-MM-DD HH24:MI:SS.US'),100,'If this is true the cell values in all rows will not be quoted anymore in the exported file.',1,'D','Y','N','N','N','N','N','N','N','Do Not Quote Rows',TO_TIMESTAMP('2023-12-21 13:57:31.545','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-21T11:57:31.822809100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-21T11:57:31.870006700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582874)
;

-- 2023-12-21T11:57:31.897438300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723341
;

-- 2023-12-21T11:57:31.903438200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723341)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Do Not Quote Headers
-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T11:58:06.466567300Z
UPDATE AD_Field SET DisplayLogic='@SpreadsheetFormat@=csv',Updated=TO_TIMESTAMP('2023-12-21 13:58:06.466','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723341
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 20 -> flags.Do Not Quote Rows
-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T11:59:04.998843600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723341,0,245,622089,541395,'F',TO_TIMESTAMP('2023-12-21 13:59:04.803','YYYY-MM-DD HH24:MI:SS.US'),100,'If this is true the cell values in all rows will not be quoted anymore in the exported file.','Y','N','N','Y','N','N','N',0,'Do Not Quote Rows',110,0,0,TO_TIMESTAMP('2023-12-21 13:59:04.803','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Do Not Quote Rows
-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T12:29:57.378562700Z
UPDATE AD_Field SET SeqNo=410,Updated=TO_TIMESTAMP('2023-12-21 14:29:57.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723341
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Do Not Quote Rows
-- Column: AD_Process.DoNotQuoteRows
-- 2023-12-21T14:18:26.837649800Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-12-21 16:18:26.836','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723341
;
