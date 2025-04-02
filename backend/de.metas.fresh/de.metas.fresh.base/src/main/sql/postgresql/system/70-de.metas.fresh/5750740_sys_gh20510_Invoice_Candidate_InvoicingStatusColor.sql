-- 2025-04-02T16:29:39.432Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583564,0,'InvoicingStatusColor_ID',TO_TIMESTAMP('2025-04-02 17:29:38','YYYY-MM-DD HH24:MI:SS'),100,'Rot: Es wurden keine Artikel in Rechnung gestellt. Gelb: Die Rechnungsmenge wurde teilweise berechnet. Grün: Die Gesamtmenge wurde in Rechnung gestellt. Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.','D','Rot: Es wurden keine Artikel in Rechnung gestellt.
Gelb: Die Rechnungsmenge wurde teilweise berechnet.
Grün: Die Gesamtmenge wurde in Rechnung gestellt.
Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.','Y','Rechnungsstatus','Rechnungsstatus',TO_TIMESTAMP('2025-04-02 17:29:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-04-02T16:29:39.485Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583564 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InvoicingStatusColor_ID
-- 2025-04-02T16:30:32.357Z
UPDATE AD_Element_Trl SET Description='Red: No items have been invoiced. Yellow: Partial invoicing quantity has been done. Green: The full quantity has been invoiced. Blue: More than the expected quantity has been invoiced.', Help='Red: No items have been invoiced.
Yellow: Partial invoicing quantity has been done.
Green: The full quantity has been invoiced.
Blue: More than the expected quantity has been invoiced.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-02 17:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583564 AND AD_Language='en_US'
;

-- 2025-04-02T16:30:32.475Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583564,'en_US') 
;

-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- Column SQL (old): null
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- 2025-04-02T16:34:02.535Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589829,583564,0,27,540974,540270,540702,'XX','InvoicingStatusColor_ID','(CASE WHEN C_Invoice_Candidate.QtyInvoiced = 0 THEN 1000000 WHEN (C_Invoice_Candidate.QtyInvoiced > 0 AND C_Invoice_Candidate.QtyInvoiced < C_Invoice_Candidate.QtyToInvoice) THEN 1000003 WHEN (C_Invoice_Candidate.QtyInvoiced = C_Invoice_Candidate.QtyToInvoice) THEN 1000001 WHEN (C_Invoice_Candidate.QtyInvoiced > C_Invoice_Candidate.QtyToInvoice) THEN 540006 END)',TO_TIMESTAMP('2025-04-02 17:34:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Rot: Es wurden keine Artikel in Rechnung gestellt. Gelb: Die Rechnungsmenge wurde teilweise berechnet. Grün: Die Gesamtmenge wurde in Rechnung gestellt. Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.','de.metas.invoicecandidate',0,10,'Rot: Es wurden keine Artikel in Rechnung gestellt.
Gelb: Die Rechnungsmenge wurde teilweise berechnet.
Grün: Die Gesamtmenge wurde in Rechnung gestellt.
Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Rechnungsstatus',0,0,TO_TIMESTAMP('2025-04-02 17:34:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2025-04-02T16:34:02.586Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-02T16:34:02.687Z
/* DDL */  select update_Column_Translation_From_AD_Element(583564) 
;

-- Field: Rechnungsdisposition_OLD -> Rechnungskandidaten -> Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- 2025-04-02T16:35:02.903Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589829,741857,0,540279,0,TO_TIMESTAMP('2025-04-02 17:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Rot: Es wurden keine Artikel in Rechnung gestellt. Gelb: Die Rechnungsmenge wurde teilweise berechnet. Grün: Die Gesamtmenge wurde in Rechnung gestellt. Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.',0,'D','Rot: Es wurden keine Artikel in Rechnung gestellt.
Gelb: Die Rechnungsmenge wurde teilweise berechnet.
Grün: Die Gesamtmenge wurde in Rechnung gestellt.
Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.',0,'Y','Y','Y','N','N','N','N','N','Rechnungsstatus',0,580,0,1,1,TO_TIMESTAMP('2025-04-02 17:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-04-02T16:35:02.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=741857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-02T16:35:03.003Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583564) 
;

-- 2025-04-02T16:35:03.055Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741857
;

-- 2025-04-02T16:35:03.105Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741857)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- 2025-04-02T16:35:26.362Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741857,0,540279,542704,631303,'F',TO_TIMESTAMP('2025-04-02 17:35:25','YYYY-MM-DD HH24:MI:SS'),100,'Rot: Es wurden keine Artikel in Rechnung gestellt. Gelb: Die Rechnungsmenge wurde teilweise berechnet. Grün: Die Gesamtmenge wurde in Rechnung gestellt. Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.','Rot: Es wurden keine Artikel in Rechnung gestellt.
Gelb: Die Rechnungsmenge wurde teilweise berechnet.
Grün: Die Gesamtmenge wurde in Rechnung gestellt.
Blau: Es wurde mehr als die erwartete Menge in Rechnung gestellt.','Y','N','N','Y','N','N','N',0,'Rechnungsstatus',100,0,0,TO_TIMESTAMP('2025-04-02 17:35:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2025-04-02T16:37:51.344Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-04-02 17:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2025-04-02T16:37:51.762Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-04-02 17:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- 2025-04-02T16:37:52.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-04-02 17:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631303
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2025-04-02T16:37:52.579Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-04-02 17:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541084
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2025-04-02T16:37:52.986Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-04-02 17:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541085
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2025-04-02T16:37:53.386Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-04-02 17:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548120
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2025-04-02T16:37:53.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2025-04-02 17:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

