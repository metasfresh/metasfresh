-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T09:04:31.669Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585131,542958,0,30,541071,260,'C_VAT_Code_ID',TO_TIMESTAMP('2022-11-24 11:04:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'VAT Code',0,0,TO_TIMESTAMP('2022-11-24 11:04:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-24T09:04:32.078Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585131 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-24T09:04:32.174Z
/* DDL */  select update_Column_Translation_From_AD_Element(542958) 
;

-- 2022-11-24T09:04:48.352Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN C_VAT_Code_ID NUMERIC(10)')
;

-- 2022-11-24T09:04:49.542Z
ALTER TABLE C_OrderLine ADD CONSTRAINT CVATCode_COrderLine FOREIGN KEY (C_VAT_Code_ID) REFERENCES public.C_VAT_Code DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-24T09:05:47.373Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585132,542958,0,30,541071,540270,'C_VAT_Code_ID',TO_TIMESTAMP('2022-11-24 11:05:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'VAT Code',0,0,TO_TIMESTAMP('2022-11-24 11:05:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-24T09:05:47.762Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585132 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-24T09:05:47.829Z
/* DDL */  select update_Column_Translation_From_AD_Element(542958) 
;

-- 2022-11-24T09:06:00.392Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_VAT_Code_ID NUMERIC(10)')
;

-- 2022-11-24T09:06:00.724Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CVATCode_CInvoiceCandidate FOREIGN KEY (C_VAT_Code_ID) REFERENCES public.C_VAT_Code DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.C_VAT_Code_ID
-- 2022-11-24T09:07:06.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585133,542958,0,30,541071,318,'C_VAT_Code_ID',TO_TIMESTAMP('2022-11-24 11:07:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'VAT Code',0,0,TO_TIMESTAMP('2022-11-24 11:07:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-24T09:07:06.912Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585133 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-24T09:07:06.978Z
/* DDL */  select update_Column_Translation_From_AD_Element(542958) 
;

-- 2022-11-24T09:07:16.637Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN C_VAT_Code_ID NUMERIC(10)')
;

-- 2022-11-24T09:07:17.472Z
ALTER TABLE C_Invoice ADD CONSTRAINT CVATCode_CInvoice FOREIGN KEY (C_VAT_Code_ID) REFERENCES public.C_VAT_Code DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_InvoiceTax.C_VAT_Code_ID
-- 2022-11-24T09:10:11.314Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585134,542958,0,19,334,'C_VAT_Code_ID',TO_TIMESTAMP('2022-11-24 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'VAT Code',0,0,TO_TIMESTAMP('2022-11-24 11:10:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-24T09:10:11.705Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585134 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-24T09:10:11.771Z
/* DDL */  select update_Column_Translation_From_AD_Element(542958) 
;

-- 2022-11-24T09:10:18.757Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceTax','ALTER TABLE public.C_InvoiceTax ADD COLUMN C_VAT_Code_ID NUMERIC(10)')
;

-- 2022-11-24T09:10:18.970Z
ALTER TABLE C_InvoiceTax ADD CONSTRAINT CVATCode_CInvoiceTax FOREIGN KEY (C_VAT_Code_ID) REFERENCES public.C_VAT_Code DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-24T10:36:54.658Z
UPDATE AD_Column SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2022-11-24 12:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585132
;

-- Column: C_Invoice_Candidate.PaymentRule
-- 2022-11-24T10:37:12.064Z
UPDATE AD_Column SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2022-11-24 12:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579213
;

-- 2022-11-24T10:51:04.922Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581729,0,'IsManualTax',TO_TIMESTAMP('2022-11-24 12:51:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Manual Tax','Manual Tax',TO_TIMESTAMP('2022-11-24 12:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T10:51:05.317Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581729 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_TaxCategory.IsManualTax
-- 2022-11-24T10:51:28.169Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585135,581729,0,20,252,'IsManualTax',TO_TIMESTAMP('2022-11-24 12:51:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Manual Tax',0,0,TO_TIMESTAMP('2022-11-24 12:51:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-24T10:51:28.559Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585135 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-24T10:51:28.627Z
/* DDL */  select update_Column_Translation_From_AD_Element(581729) 
;

-- 2022-11-24T10:51:35.516Z
/* DDL */ SELECT public.db_alter_table('C_TaxCategory','ALTER TABLE public.C_TaxCategory ADD COLUMN IsManualTax CHAR(1) DEFAULT ''N'' CHECK (IsManualTax IN (''Y'',''N'')) NOT NULL')
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T10:58:38.636Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585131,708192,0,187,0,TO_TIMESTAMP('2022-11-24 12:58:38','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code',0,370,0,1,1,TO_TIMESTAMP('2022-11-24 12:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T10:58:38.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708192 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T10:58:38.665Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T10:58:38.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708192
;

-- 2022-11-24T10:58:38.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708192)
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T11:02:23.261Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708192,0,187,1000005,613568,'F',TO_TIMESTAMP('2022-11-24 13:02:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',440,0,0,TO_TIMESTAMP('2022-11-24 13:02:23','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T11:05:54.020Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585131,708193,0,293,0,TO_TIMESTAMP('2022-11-24 13:05:53','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code',0,170,0,1,1,TO_TIMESTAMP('2022-11-24 13:05:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:05:54.024Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:05:54.025Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:05:54.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708193
;

-- 2022-11-24T11:05:54.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708193)
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T11:06:18.271Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708192,0,293,540927,613569,'F',TO_TIMESTAMP('2022-11-24 13:06:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',260,0,0,TO_TIMESTAMP('2022-11-24 13:06:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Steuerkategorie(138,D) -> Steuerkategorie(176,D) -> Manual Tax
-- Column: C_TaxCategory.IsManualTax
-- 2022-11-24T11:07:15.981Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585135,708194,0,176,0,TO_TIMESTAMP('2022-11-24 13:07:15','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Manual Tax',0,100,0,1,1,TO_TIMESTAMP('2022-11-24 13:07:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:07:15.982Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:07:15.984Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581729) 
;

-- 2022-11-24T11:07:15.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708194
;

-- 2022-11-24T11:07:15.986Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708194)
;

-- Tab: Steuerkategorie(138,D) -> Steuerkategorie(176,D)
-- UI Section: advanced view
-- 2022-11-24T11:07:39.278Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,176,545307,TO_TIMESTAMP('2022-11-24 13:07:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-24 13:07:39','YYYY-MM-DD HH24:MI:SS'),100,'advanced view')
;

-- 2022-11-24T11:07:39.279Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545307 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Steuerkategorie(138,D) -> Steuerkategorie(176,D) -> advanced view
-- UI Column: 10
-- 2022-11-24T11:07:42.738Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546467,545307,TO_TIMESTAMP('2022-11-24 13:07:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-24 13:07:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Steuerkategorie(138,D) -> Steuerkategorie(176,D) -> advanced view -> 10
-- UI Element Group: advanced view
-- 2022-11-24T11:07:55.520Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546467,550054,TO_TIMESTAMP('2022-11-24 13:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced view',10,TO_TIMESTAMP('2022-11-24 13:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Steuerkategorie(138,D) -> Steuerkategorie(176,D) -> advanced view -> 10 -> advanced view.Manual Tax
-- Column: C_TaxCategory.IsManualTax
-- 2022-11-24T11:08:11.205Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708194,0,176,550054,613570,'F',TO_TIMESTAMP('2022-11-24 13:08:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Manual Tax',10,0,0,TO_TIMESTAMP('2022-11-24 13:08:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Steuerkategorie(138,D) -> Steuerkategorie(176,D) -> advanced view -> 10 -> advanced view.Manual Tax
-- Column: C_TaxCategory.IsManualTax
-- 2022-11-24T11:08:14.779Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-11-24 13:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613570
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-24T11:09:16.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585132,708195,0,540279,0,TO_TIMESTAMP('2022-11-24 13:09:16','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code',0,560,0,1,1,TO_TIMESTAMP('2022-11-24 13:09:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:09:16.790Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:09:16.791Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:09:16.793Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708195
;

-- 2022-11-24T11:09:16.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708195)
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-24T11:09:40.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708195,0,540279,540056,613571,'F',TO_TIMESTAMP('2022-11-24 13:09:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'VAT Code',1050,0,0,TO_TIMESTAMP('2022-11-24 13:09:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-24T11:10:22.623Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585132,708196,0,543052,0,TO_TIMESTAMP('2022-11-24 13:10:22','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code',0,530,0,1,1,TO_TIMESTAMP('2022-11-24 13:10:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:10:22.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:10:22.625Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:10:22.628Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708196
;

-- 2022-11-24T11:10:22.629Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708196)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-24T11:10:35.755Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708196,0,543052,544370,613572,'F',TO_TIMESTAMP('2022-11-24 13:10:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'VAT Code',1030,0,0,TO_TIMESTAMP('2022-11-24 13:10:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:12:45.759Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585136,542958,0,30,541071,333,'C_VAT_Code_ID',TO_TIMESTAMP('2022-11-24 13:12:45','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'VAT Code',0,0,TO_TIMESTAMP('2022-11-24 13:12:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-24T11:12:45.761Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585136 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-24T11:12:45.763Z
/* DDL */  select update_Column_Translation_From_AD_Element(542958) 
;

-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:13:02.292Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-24 13:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585136
;

-- 2022-11-24T11:13:09.360Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN C_VAT_Code_ID NUMERIC(10)')
;

-- 2022-11-24T11:13:09.646Z
ALTER TABLE C_InvoiceLine ADD CONSTRAINT CVATCode_CInvoiceLine FOREIGN KEY (C_VAT_Code_ID) REFERENCES public.C_VAT_Code DEFERRABLE INITIALLY DEFERRED
;

-- Field: Rechnung(167,D) -> Rechnungsposition(270,D) -> VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:13:46.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585136,708197,0,270,0,TO_TIMESTAMP('2022-11-24 13:13:46','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code',0,210,0,1,1,TO_TIMESTAMP('2022-11-24 13:13:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:13:46.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:13:46.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:13:46.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708197
;

-- 2022-11-24T11:13:46.382Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708197)
;

-- Field: Rechnung(167,D) -> Rechnungsposition(270,D) -> VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:15:27.189Z
UPDATE AD_Field SET ReadOnlyLogic='@C_Order_ID/-1@=-1',Updated=TO_TIMESTAMP('2022-11-24 13:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708197
;

-- UI Element: Rechnung(167,D) -> Rechnungsposition(270,D) -> main -> 10 -> default.VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:15:50.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708197,0,270,540023,613573,'F',TO_TIMESTAMP('2022-11-24 13:15:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',230,0,0,TO_TIMESTAMP('2022-11-24 13:15:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnung(167,D) -> Rechnungs Steuer(271,D) -> VAT Code
-- Column: C_InvoiceTax.C_VAT_Code_ID
-- 2022-11-24T11:16:29.238Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585134,708198,0,271,0,TO_TIMESTAMP('2022-11-24 13:16:29','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','VAT Code',0,100,0,1,1,TO_TIMESTAMP('2022-11-24 13:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:16:29.239Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:16:29.241Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:16:29.243Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708198
;

-- 2022-11-24T11:16:29.244Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708198)
;

-- UI Element: Rechnung(167,D) -> Rechnungs Steuer(271,D) -> main -> 10 -> default.VAT Code
-- Column: C_InvoiceTax.C_VAT_Code_ID
-- 2022-11-24T11:17:01.416Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708198,0,271,540024,613574,'F',TO_TIMESTAMP('2022-11-24 13:17:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',90,0,0,TO_TIMESTAMP('2022-11-24 13:17:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:18:34.262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,ReadOnlyLogic,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585136,708199,0,291,0,TO_TIMESTAMP('2022-11-24 13:18:34','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code','@DocStatus/''''@=''DR''',0,110,0,1,1,TO_TIMESTAMP('2022-11-24 13:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:18:34.263Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:18:34.265Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:18:34.268Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708199
;

-- 2022-11-24T11:18:34.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708199)
;

-- UI Element: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-11-24T11:18:56.922Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708199,0,291,540219,613575,'F',TO_TIMESTAMP('2022-11-24 13:18:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',140,0,0,TO_TIMESTAMP('2022-11-24 13:18:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Eingangsrechnung(183,D) -> Rechnungs-Steuer(292,D) -> VAT Code
-- Column: C_InvoiceTax.C_VAT_Code_ID
-- 2022-11-24T11:19:21.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585134,708200,0,292,0,TO_TIMESTAMP('2022-11-24 13:19:21','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','VAT Code',0,100,0,1,1,TO_TIMESTAMP('2022-11-24 13:19:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T11:19:21.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T11:19:21.408Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542958) 
;

-- 2022-11-24T11:19:21.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708200
;

-- 2022-11-24T11:19:21.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708200)
;

-- UI Element: Eingangsrechnung(183,D) -> Rechnungs-Steuer(292,D) -> main -> 10 -> default.VAT Code
-- Column: C_InvoiceTax.C_VAT_Code_ID
-- 2022-11-24T11:19:38.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708200,0,292,540220,613576,'F',TO_TIMESTAMP('2022-11-24 13:19:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',10,0,0,TO_TIMESTAMP('2022-11-24 13:19:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: VAT Code for soTrx
-- 2022-11-24T11:52:56.746Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540610,'C_Vat_Code.IsSoTrx =@IsSoTrx/Y@',TO_TIMESTAMP('2022-11-24 13:52:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VAT Code for soTrx','S',TO_TIMESTAMP('2022-11-24 13:52:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: VAT_Code_for_soTrx
-- 2022-11-24T11:53:51.125Z
UPDATE AD_Val_Rule SET Name='VAT_Code_for_soTrx',Updated=TO_TIMESTAMP('2022-11-24 13:53:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T16:11:45.194Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-24 18:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- 2022-11-24T16:27:40.513Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE C_Invoice DROP COLUMN IF EXISTS C_VAT_Code_ID')
;

-- Column: C_Invoice.C_VAT_Code_ID
-- 2022-11-24T16:27:41.036Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585133
;

-- 2022-11-24T16:27:41.040Z
DELETE FROM AD_Column WHERE AD_Column_ID=585133
;

-- Name: VAT_Code_for_soTrx
-- 2022-11-24T19:04:13.746Z
UPDATE AD_Val_Rule SET Code='C_Vat_Code.IsSoTrx =''@IsSoTrx/Y@'' OR C_Vat_Code.IsSoTrx IS NULL',Updated=TO_TIMESTAMP('2022-11-24 21:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-11-24T16:11:45.194Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-24 18:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- 2022-11-24T16:27:40.513Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE C_Invoice DROP COLUMN IF EXISTS C_VAT_Code_ID')
;

-- Column: C_Invoice.C_VAT_Code_ID
-- 2022-11-24T16:27:41.036Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585133
;

-- 2022-11-24T16:27:41.040Z
DELETE FROM AD_Column WHERE AD_Column_ID=585133
;

-- 2022-11-25T13:44:19.807Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581738,0,'C_VAT_Code_Override_ID',TO_TIMESTAMP('2022-11-25 15:44:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VAT Code','VAT Code',TO_TIMESTAMP('2022-11-25 15:44:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T13:44:19.811Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581738 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-11-25T13:45:05.747Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585141,581738,0,30,541071,540270,'C_VAT_Code_Override_ID',TO_TIMESTAMP('2022-11-25 15:45:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'VAT Code',0,0,TO_TIMESTAMP('2022-11-25 15:45:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-25T13:45:05.749Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585141 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-25T13:45:05.778Z
/* DDL */  select update_Column_Translation_From_AD_Element(581738)
;

-- Name: VAT_Code_for_soTrx
-- 2022-11-25T13:46:11.760Z
UPDATE AD_Val_Rule SET Code='C_Vat_Code.IsSoTrx =''@IsSoTrx/Y@'' OR C_Vat_Code.IsSoTrx IS NULL AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2022-11-25 15:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-11-25T13:46:18.521Z
UPDATE AD_Column SET AD_Val_Rule_ID=540610,Updated=TO_TIMESTAMP('2022-11-25 15:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585141
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-11-25T14:21:31.258Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585141,708211,0,540279,0,TO_TIMESTAMP('2022-11-25 16:21:31','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','VAT Code',0,560,0,1,1,TO_TIMESTAMP('2022-11-25 16:21:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T14:21:31.262Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-25T14:21:31.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581738)
;

-- 2022-11-25T14:21:31.276Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708211
;

-- 2022-11-25T14:21:31.277Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708211)
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-11-25T14:21:40.409Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-25 16:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708195
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-11-25T14:22:22.244Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708211,0,540279,540056,613587,'F',TO_TIMESTAMP('2022-11-25 16:22:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'VAT Code',1050,0,0,TO_TIMESTAMP('2022-11-25 16:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: C_VAT_Code_Override_ID
-- 2022-11-25T14:26:36.928Z
UPDATE AD_Element_Trl SET Name='VAT Code abw.', PrintName='VAT Code abw.',Updated=TO_TIMESTAMP('2022-11-25 16:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581738 AND AD_Language='de_CH'
;

-- 2022-11-25T14:26:36.930Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581738,'de_CH')
;

-- Element: C_VAT_Code_Override_ID
-- 2022-11-25T14:26:40.968Z
UPDATE AD_Element_Trl SET Name='VAT Code abw.', PrintName='VAT Code abw.',Updated=TO_TIMESTAMP('2022-11-25 16:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581738 AND AD_Language='de_DE'
;

-- 2022-11-25T14:26:40.969Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581738,'de_DE')
;

-- 2022-11-25T14:26:40.970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581738,'de_DE')
;

-- Element: C_VAT_Code_Override_ID
-- 2022-11-25T14:26:44.897Z
UPDATE AD_Element_Trl SET Name='VAT Code abw.', PrintName='VAT Code abw.',Updated=TO_TIMESTAMP('2022-11-25 16:26:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581738 AND AD_Language='nl_NL'
;

-- 2022-11-25T14:26:44.899Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581738,'nl_NL')
;

-- Element: C_VAT_Code_Override_ID
-- 2022-11-25T14:26:50.684Z
UPDATE AD_Element_Trl SET Name='VAT Code override', PrintName='VAT Code override',Updated=TO_TIMESTAMP('2022-11-25 16:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581738 AND AD_Language='en_US'
;

-- 2022-11-25T14:26:50.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581738,'en_US')
;

-- 2022-11-25T14:51:06.463Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_VAT_Code_Override_ID NUMERIC(10)')
;

-- 2022-11-25T14:51:06.738Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CVATCodeOverride_CInvoiceCandidate FOREIGN KEY (C_VAT_Code_Override_ID) REFERENCES public.C_VAT_Code DEFERRABLE INITIALLY DEFERRED
;

-- 2023-05-02
-- This UC doesn't seem to work: e.g. what if we have the same vatcode valid at different times?
-- Or one code for STOtrx=Y and another one for SOTrx=N
-- I also checked at de.metas.acct.vatcode.impl.VATCodeDAO.findVATCode
/*
-- 2022-11-25T15:14:12.014Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540714,0,540703,TO_TIMESTAMP('2022-11-25 17:14:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','unique_vatcode_organization','N',TO_TIMESTAMP('2022-11-25 17:14:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T15:14:12.019Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540714 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-11-25T15:14:35.152Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553150,541282,540714,0,TO_TIMESTAMP('2022-11-25 17:14:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-11-25 17:14:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T15:14:44.358Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553140,541283,540714,0,TO_TIMESTAMP('2022-11-25 17:14:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-11-25 17:14:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T15:14:52.105Z
CREATE UNIQUE INDEX unique_vatcode_organization ON C_VAT_Code (VATCode,AD_Org_ID)
;
*/

-- Name: VAT_Code_for_soTrx
-- 2022-11-25T15:37:11.849Z
UPDATE AD_Val_Rule SET Code='(C_Vat_Code.IsSoTrx =''@IsSoTrx/Y@'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2022-11-25 17:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Name: VAT_Code_for_soTrx
-- 2022-11-25T15:37:45.856Z
UPDATE AD_Val_Rule SET Code='(C_Vat_Code.IsSoTrx =''@IsSoTrx/Y@''X OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2022-11-25 17:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Name: VAT_Code_for_SO
-- 2022-11-25T15:53:10.975Z
UPDATE AD_Val_Rule SET Code='(C_Vat_Code.IsSoTrx =''Y'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)', Name='VAT_Code_for_SO',Updated=TO_TIMESTAMP('2022-11-25 17:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540610
;

-- Name: VAT_Code_for_PO
-- 2022-11-25T15:53:19.703Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540611,'(C_Vat_Code.IsSoTrx =''N'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',TO_TIMESTAMP('2022-11-25 17:53:19','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','VAT_Code_for_PO','S',TO_TIMESTAMP('2022-11-25 17:53:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-11-25T15:54:07.132Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2022-11-25 17:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585141
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> VAT Code abw.
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-11-25T15:55:16.913Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540610,Updated=TO_TIMESTAMP('2022-11-25 17:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708211
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T12:35:58.545Z
UPDATE AD_UI_Element SET SeqNo=245,Updated=TO_TIMESTAMP('2022-12-06 14:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613569
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T12:37:19.723Z
UPDATE AD_Column SET AD_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-06 14:37:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.Section Code
-- Column: C_OrderLine.M_SectionCode_ID
-- 2022-12-06T13:10:21.124Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2022-12-06 15:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611507
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.Project
-- Column: C_OrderLine.C_Project_ID
-- 2022-12-06T13:10:21.130Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2022-12-06 15:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573743
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T13:10:21.135Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2022-12-06 15:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613569
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T13:11:28.738Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2022-12-06 15:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T13:11:50.024Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-06 15:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708193
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T13:41:25.988Z
UPDATE AD_UI_Element SET SeqNo=260,Updated=TO_TIMESTAMP('2022-12-06 15:41:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613569
;

-- UI Element: Bestellung(181,D) -> Bestellposition(293,D) -> main -> 10 -> main.VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-06T13:45:07.649Z
UPDATE AD_UI_Element SET AD_Field_ID=708193,Updated=TO_TIMESTAMP('2022-12-06 15:45:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613569
;

-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-12-06T16:46:18.335Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2022-12-06 18:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585132
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> VAT Code abw.
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-12-06T16:46:48.725Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585141,708908,0,543052,0,TO_TIMESTAMP('2022-12-06 18:46:48','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','VAT Code abw.',0,540,0,1,1,TO_TIMESTAMP('2022-12-06 18:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-06T16:46:48.727Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-06T16:46:48.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581738)
;

-- 2022-12-06T16:46:48.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708908
;

-- 2022-12-06T16:46:48.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708908)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.VAT Code abw.
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-12-06T16:47:11.723Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708908,0,543052,544370,613911,'F',TO_TIMESTAMP('2022-12-06 18:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'VAT Code abw.',1040,0,0,TO_TIMESTAMP('2022-12-06 18:47:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> VAT Code
-- Column: C_Invoice_Candidate.C_VAT_Code_ID
-- 2022-12-06T16:47:51.913Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-12-06 18:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708196
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> VAT Code abw.
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-12-07T11:13:27.271Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-07 13:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708908
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> VAT Code abw.
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-12-07T11:39:02.920Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540028,Updated=TO_TIMESTAMP('2022-12-07 13:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708908
;

-- Name: VAT_Code_for_SoTrx
-- 2022-12-07T11:41:11.569Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540612,'(C_Vat_Code.IsSoTrx =''@IsSoTrx/Y@'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',TO_TIMESTAMP('2022-12-07 13:41:11','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','VAT_Code_for_SoTrx','S',TO_TIMESTAMP('2022-12-07 13:41:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-07T11:41:28.530Z
UPDATE AD_Column SET AD_Val_Rule_ID=540612,Updated=TO_TIMESTAMP('2022-12-07 13:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-07T11:42:24.066Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2022-12-07 13:42:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-12-07T11:42:41.978Z
UPDATE AD_Column SET AD_Val_Rule_ID=540612,Updated=TO_TIMESTAMP('2022-12-07 13:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585141
;

-- Name: VAT_Code_for_SoTrx
-- 2022-12-07T11:48:30.087Z
UPDATE AD_Val_Rule SET Code='(C_Vat_Code.IsSoTrx =''@IsSOTrx/N@'' OR C_Vat_Code.IsSoTrx IS NULL) AND C_Vat_Code.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2022-12-07 13:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540612
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> VAT Code abw.
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2022-12-07T11:50:41.127Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-07 13:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708908
;

-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-12-07T11:53:21.988Z
UPDATE AD_Column SET AD_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-07 13:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585136
;

-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-12-07T11:53:40.715Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2022-12-07 13:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585136
;

-- Field: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-12-07T11:53:56.247Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-07 13:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708199
;

-- Field: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-12-07T11:57:02.741Z
UPDATE AD_Field SET ReadOnlyLogic='@DocStatus/''''@!=''DR''',Updated=TO_TIMESTAMP('2022-12-07 13:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708199
;

-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-07T16:04:27.186Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541071, AD_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-07 18:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708193
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> VAT Code
-- Column: C_OrderLine.C_VAT_Code_ID
-- 2022-12-07T16:05:33.489Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541071, AD_Val_Rule_ID=540610, Filter_Val_Rule_ID=540610,Updated=TO_TIMESTAMP('2022-12-07 18:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708192
;

-- Field: Rechnung(167,D) -> Rechnungsposition(270,D) -> Packvorschrift
-- Column: C_InvoiceLine.M_HU_PI_Item_Product_ID
-- 2022-12-07T16:06:22.347Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541071, AD_Val_Rule_ID=540610, Filter_Val_Rule_ID=540610,Updated=TO_TIMESTAMP('2022-12-07 18:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556180
;

-- Field: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> VAT Code
-- Column: C_InvoiceLine.C_VAT_Code_ID
-- 2022-12-07T16:07:22.127Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541071, AD_Val_Rule_ID=540611,Updated=TO_TIMESTAMP('2022-12-07 18:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708199
;
