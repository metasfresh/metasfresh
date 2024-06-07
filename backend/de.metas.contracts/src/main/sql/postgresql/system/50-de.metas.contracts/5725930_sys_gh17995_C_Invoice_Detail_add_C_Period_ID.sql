-- Column: C_Invoice_Detail.C_Period_ID
-- 2024-06-07T09:13:41.010Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588335,206,0,19,540614,'C_Period_ID',TO_TIMESTAMP('2024-06-07 11:13:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Periode des Kalenders','D',0,10,'"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Periode',0,0,TO_TIMESTAMP('2024-06-07 11:13:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-07T09:13:41.018Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588335 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-07T09:13:41.058Z
/* DDL */  select update_Column_Translation_From_AD_Element(206) 
;

-- 2024-06-07T09:13:58.322Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Detail','ALTER TABLE public.C_Invoice_Detail ADD COLUMN C_Period_ID NUMERIC(10)')
;

-- 2024-06-07T09:13:58.376Z
ALTER TABLE C_Invoice_Detail ADD CONSTRAINT CPeriod_CInvoiceDetail FOREIGN KEY (C_Period_ID) REFERENCES public.C_Period DEFERRABLE INITIALLY DEFERRED
;

-- Field: Rechnungsdisposition -> Rechnungszeilendetails -> Periode
-- Column: C_Invoice_Detail.C_Period_ID
-- 2024-06-07T09:15:21.400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588335,728996,0,543628,0,TO_TIMESTAMP('2024-06-07 11:15:21','YYYY-MM-DD HH24:MI:SS'),100,'Periode des Kalenders',0,'de.metas.customer.ma193','"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.',0,'Y','Y','Y','N','N','N','N','N','Periode',0,200,0,1,1,TO_TIMESTAMP('2024-06-07 11:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-07T09:15:21.404Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-07T09:15:21.410Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(206) 
;

-- 2024-06-07T09:15:21.459Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728996
;

-- 2024-06-07T09:15:21.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728996)
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.C_Period_ID
-- Column: C_Invoice_Detail.C_Period_ID
-- 2024-06-07T09:16:08.918Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728996,0,543628,545263,624940,'F',TO_TIMESTAMP('2024-06-07 11:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Periode des Kalenders','"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','N','Y','N','N','N',0,'C_Period_ID',260,0,0,TO_TIMESTAMP('2024-06-07 11:16:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.C_Period_ID
-- Column: C_Invoice_Detail.C_Period_ID
-- 2024-06-07T09:16:21.065Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2024-06-07 11:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624940
;

-- UI Element: Rechnungsdisposition -> Rechnungszeilendetails.Sektion
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2024-06-07T09:16:21.078Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2024-06-07 11:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580650
;

