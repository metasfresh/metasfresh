-- Run mode: SWING_CLIENT

-- 2024-11-14T11:30:35.092Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583368,0,'IsHidePriceAndAmountOnPrint',TO_TIMESTAMP('2024-11-14 12:30:34.876','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Preis und Betrag bei Druck ausblenden','Preis und Betrag bei Druck ausblenden',TO_TIMESTAMP('2024-11-14 12:30:34.876','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T11:30:35.119Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583368 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:30:58.860Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hide price and amount on print',Updated=TO_TIMESTAMP('2024-11-14 12:30:58.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583368 AND AD_Language='en_US'
;

-- 2024-11-14T11:30:58.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583368,'en_US')
;

-- Element: IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:30:59.400Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-14 12:30:59.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583368 AND AD_Language='de_CH'
;

-- 2024-11-14T11:30:59.403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583368,'de_CH')
;

-- Element: IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:31:00.206Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-14 12:31:00.206','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583368 AND AD_Language='de_DE'
;

-- 2024-11-14T11:31:00.209Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583368,'de_DE')
;

-- 2024-11-14T11:31:00.212Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583368,'de_DE')
;

-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:35:16.289Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589388,583368,0,20,333,'IsHidePriceAndAmountOnPrint',TO_TIMESTAMP('2024-11-14 12:35:16.004','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis und Betrag bei Druck ausblenden',0,0,TO_TIMESTAMP('2024-11-14 12:35:16.004','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-14T11:35:16.291Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-14T11:35:16.295Z
/* DDL */  select update_Column_Translation_From_AD_Element(583368)
;

-- 2024-11-14T11:35:17.760Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN IsHidePriceAndAmountOnPrint CHAR(1) DEFAULT ''N'' CHECK (IsHidePriceAndAmountOnPrint IN (''Y'',''N'')) NOT NULL')
;

-- Field: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> Preis und Betrag bei Druck ausblenden
-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T13:47:49.071Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589388,733827,0,270,TO_TIMESTAMP('2024-11-14 14:47:47.883','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Preis und Betrag bei Druck ausblenden',TO_TIMESTAMP('2024-11-14 14:47:47.883','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T13:47:49.074Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-14T13:47:49.077Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583368)
;

-- 2024-11-14T13:47:49.081Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733827
;

-- 2024-11-14T13:47:49.083Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733827)
;

-- Field: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> Preis und Betrag bei Druck ausblenden
-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T13:48:04.726Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-14 14:48:04.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=733827
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> main -> 10 -> default.Preis und Betrag bei Druck ausblenden
-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T14:00:01.876Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733827,0,270,540023,627179,'F',TO_TIMESTAMP('2024-11-14 15:00:01.744','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Preis und Betrag bei Druck ausblenden',260,0,0,TO_TIMESTAMP('2024-11-14 15:00:01.744','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Preis und Betrag bei Druck ausblenden
-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T13:51:49.802Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589388,733828,0,291,TO_TIMESTAMP('2024-11-14 14:51:49.678','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Preis und Betrag bei Druck ausblenden',TO_TIMESTAMP('2024-11-14 14:51:49.678','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T13:51:49.804Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-14T13:51:49.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583368)
;

-- 2024-11-14T13:51:49.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733828
;

-- 2024-11-14T13:51:49.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733828)
;

-- Field: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Preis und Betrag bei Druck ausblenden
-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T13:52:41.659Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-14 14:52:41.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=733828
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Preis und Betrag bei Druck ausblenden
-- Column: C_InvoiceLine.IsHidePriceAndAmountOnPrint
-- 2024-11-14T13:53:10.395Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733828,0,291,540219,627178,'F',TO_TIMESTAMP('2024-11-14 14:53:10.257','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Preis und Betrag bei Druck ausblenden',170,0,0,TO_TIMESTAMP('2024-11-14 14:53:10.257','YYYY-MM-DD HH24:MI:SS.US'),100)
;

