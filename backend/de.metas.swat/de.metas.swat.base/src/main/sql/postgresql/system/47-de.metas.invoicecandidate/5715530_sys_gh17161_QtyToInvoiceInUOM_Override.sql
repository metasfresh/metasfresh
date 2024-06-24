-- 2024-01-17T14:28:12.147Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582903,0,'QtyToInvoiceInUOM_Override',TO_TIMESTAMP('2024-01-17 15:28:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abzurechen abw.','Abzurechen abw.',TO_TIMESTAMP('2024-01-17 15:28:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-17T14:28:12.156Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582903 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QtyToInvoiceInUOM_Override
-- 2024-01-17T14:28:36.838Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='To invoice Override', PrintName='To invoice Override',Updated=TO_TIMESTAMP('2024-01-17 15:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582903 AND AD_Language='en_US'
;

-- 2024-01-17T14:28:36.859Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582903,'en_US') 
;

-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- 2024-01-17T14:29:14.550Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587806,582903,0,29,540270,'QtyToInvoiceInUOM_Override',TO_TIMESTAMP('2024-01-17 15:29:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abzurechen abw.',0,0,TO_TIMESTAMP('2024-01-17 15:29:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:29:14.554Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587806 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:29:14.563Z
/* DDL */  select update_Column_Translation_From_AD_Element(582903) 
;

-- 2024-01-17T14:29:20.895Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyToInvoiceInUOM_Override NUMERIC')
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- 2024-01-17T14:30:11.443Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587806,723819,0,540279,0,TO_TIMESTAMP('2024-01-17 15:30:11','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Abzurechen abw.',0,560,0,1,1,TO_TIMESTAMP('2024-01-17 15:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-17T14:30:11.447Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-17T14:30:11.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582903) 
;

-- 2024-01-17T14:30:11.465Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723819
;

-- 2024-01-17T14:30:11.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723819)
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- 2024-01-17T14:31:28.538Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723819,0,540279,542704,622114,'F',TO_TIMESTAMP('2024-01-17 15:31:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Abzurechen abw.',70,0,0,TO_TIMESTAMP('2024-01-17 15:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.QtyToInvoiceInUOM_Calc
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM
-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.QtyToInvoiceInUOM_Calc
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM
-- 2024-01-17T14:31:56.343Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2024-01-17 15:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560337
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- 2024-01-17T14:32:54.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587806,723820,0,543052,0,TO_TIMESTAMP('2024-01-17 15:32:53','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Abzurechen abw.',0,530,0,1,1,TO_TIMESTAMP('2024-01-17 15:32:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-17T14:32:54.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-17T14:32:54.221Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582903) 
;

-- 2024-01-17T14:32:54.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723820
;

-- 2024-01-17T14:32:54.230Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723820)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungskandidaten.Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Abzurechen abw.
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM_Override
-- 2024-01-17T14:34:03.691Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723820,0,543052,544367,622115,'F',TO_TIMESTAMP('2024-01-17 15:34:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Abzurechen abw.',70,0,0,TO_TIMESTAMP('2024-01-17 15:34:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungskandidaten.QtyToInvoiceInUOM_Calc
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM
-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.QtyToInvoiceInUOM_Calc
-- Column: C_Invoice_Candidate.QtyToInvoiceInUOM
-- 2024-01-17T14:34:23.477Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2024-01-17 15:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573416
;

-- Element: QtyToInvoice
-- 2024-01-17T21:11:06.243Z
UPDATE AD_Element_Trl SET Name='To invoice eff. (stock unit)', PrintName='To invoice eff. (stock unit)',Updated=TO_TIMESTAMP('2024-01-17 22:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='en_US'
;

-- 2024-01-17T21:11:06.268Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'en_US') 
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Maßeinheit
-- Column: C_Invoice_Candidate.C_UOM_ID
-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Maßeinheit
-- Column: C_Invoice_Candidate.C_UOM_ID
-- 2024-01-17T21:13:50.861Z
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Maßeinheit', Help='Eine eindeutige (nicht monetäre) Maßeinheit', Name='Maßeinheit',Updated=TO_TIMESTAMP('2024-01-17 22:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554200
;

-- 2024-01-17T21:13:50.863Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2024-01-17T21:13:50.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554200
;

-- 2024-01-17T21:13:50.918Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(554200)
;

