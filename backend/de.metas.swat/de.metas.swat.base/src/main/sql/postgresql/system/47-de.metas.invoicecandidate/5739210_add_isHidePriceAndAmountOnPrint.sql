-- Column: C_Invoice_Candidate.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:32:49.948Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589386,583368,0,20,540270,'IsHidePriceAndAmountOnPrint',TO_TIMESTAMP('2024-11-14 12:32:49.816','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.invoicecandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis und Betrag bei Druck ausblenden',0,0,TO_TIMESTAMP('2024-11-14 12:32:49.816','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-14T11:32:49.950Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589386 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-14T11:32:49.956Z
/* DDL */  select update_Column_Translation_From_AD_Element(583368)
;

-- 2024-11-14T11:32:53.173Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsHidePriceAndAmountOnPrint CHAR(1) DEFAULT ''N'' CHECK (IsHidePriceAndAmountOnPrint IN (''Y'',''N'')) NOT NULL')
;

-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Preis und Betrag bei Druck ausblenden
-- Column: C_Invoice_Candidate.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:42:31.071Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589386,733823,0,540279,TO_TIMESTAMP('2024-11-14 12:42:30.751','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Preis und Betrag bei Druck ausblenden',TO_TIMESTAMP('2024-11-14 12:42:30.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T11:42:31.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-14T11:42:31.078Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583368)
;

-- 2024-11-14T11:42:31.087Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733823
;

-- 2024-11-14T11:42:31.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733823)
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Preis und Betrag bei Druck ausblenden
-- Column: C_Invoice_Candidate.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:43:19.326Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733823,0,540279,540056,627173,'F',TO_TIMESTAMP('2024-11-14 12:43:19.17','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Preis und Betrag bei Druck ausblenden',1085,0,0,TO_TIMESTAMP('2024-11-14 12:43:19.17','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Preis und Betrag bei Druck ausblenden
-- Column: C_Invoice_Candidate.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:43:42.848Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-14 12:43:42.848','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=733823
;

-- Field: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Preis und Betrag bei Druck ausblenden
-- Column: C_Invoice_Candidate.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:45:52.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589386,733824,0,543052,TO_TIMESTAMP('2024-11-14 12:45:52.403','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Preis und Betrag bei Druck ausblenden',TO_TIMESTAMP('2024-11-14 12:45:52.403','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-14T11:45:52.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-14T11:45:52.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583368)
;

-- 2024-11-14T11:45:52.539Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733824
;

-- 2024-11-14T11:45:52.541Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733824)
;

-- Field: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Einstellungen für modulare Verträge
-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-11-14T11:46:10.496Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-14 12:46:10.496','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731889
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Preis und Betrag bei Druck ausblenden
-- Column: C_Invoice_Candidate.IsHidePriceAndAmountOnPrint
-- 2024-11-14T11:47:05.751Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733824,0,543052,544370,627174,'F',TO_TIMESTAMP('2024-11-14 12:47:05.627','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Preis und Betrag bei Druck ausblenden',1060,0,0,TO_TIMESTAMP('2024-11-14 12:47:05.627','YYYY-MM-DD HH24:MI:SS.US'),100)
;
