-- 2025-04-02T15:56:18.050Z
INSERT INTO AD_Color (AD_Client_ID,AD_Color_ID,AD_Org_ID,Alpha,Alpha_1,Blue,Blue_1,ColorType,Created,CreatedBy,Green,Green_1,ImageAlpha,IsActive,IsDefault,LineDistance,LineWidth,Name,Red,Red_1,RepeatDistance,Updated,UpdatedBy) VALUES (0,540006,0,0,0,204,0,'F',TO_TIMESTAMP('2025-04-02 16:56:17','YYYY-MM-DD HH24:MI:SS'),100,102,0,0,'Y','N',0,0,'Blue',0,0,0,TO_TIMESTAMP('2025-04-02 16:56:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-04-02T14:55:11.057Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583563,0,'DeliveryStatusColor_ID',TO_TIMESTAMP('2025-04-02 15:55:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferstatus','Lieferstatus',TO_TIMESTAMP('2025-04-02 15:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-04-02T14:55:11.109Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583563 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DeliveryStatusColor_ID
-- 2025-04-02T14:55:38.924Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery Status', PrintName='Delivery Status',Updated=TO_TIMESTAMP('2025-04-02 15:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583563 AND AD_Language='en_US'
;

-- 2025-04-02T14:55:39.054Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583563,'en_US') 
;

-- Element: DeliveryStatusColor_ID
-- 2025-04-02T16:07:22.846Z
UPDATE AD_Element_Trl SET Help='Rot: Keine Artikel geliefert.
Gelb: Teillieferung erfolgt.
Grün: Die gesamte Bestellung wurde geliefert.
Blau: Es wurden mehr Artikel als bestellt geliefert.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-02 17:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583563 AND AD_Language='de_DE'
;

-- 2025-04-02T16:07:22.950Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583563,'de_DE') 
;

-- 2025-04-02T16:07:23.004Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583563,'de_DE') 
;

-- Element: DeliveryStatusColor_ID
-- 2025-04-02T16:07:55.090Z
UPDATE AD_Element_Trl SET Description='Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.',Updated=TO_TIMESTAMP('2025-04-02 17:07:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583563 AND AD_Language='de_DE'
;

-- 2025-04-02T16:07:55.189Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583563,'de_DE') 
;

-- 2025-04-02T16:07:55.242Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583563,'de_DE') 
;

-- Element: DeliveryStatusColor_ID
-- 2025-04-02T16:09:51.944Z
UPDATE AD_Element_Trl SET Description='Red : No items have been delivered. Yellow: Partial delivery has been made. Green: The full order has been delivered. Blue: More items than ordered have been delivered.', Help='Red : No items have been delivered.
Yellow: Partial delivery has been made.
Green: The full order has been delivered.
Blue: More items than ordered have been delivered.',Updated=TO_TIMESTAMP('2025-04-02 17:09:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583563 AND AD_Language='en_US'
;

-- 2025-04-02T16:09:52.045Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583563,'en_US') 
;

-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T14:55:57.567Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589828,583563,0,19,540974,540270,540702,'XX','DeliveryStatusColor_ID',TO_TIMESTAMP('2025-04-02 15:55:56','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferstatus',0,0,TO_TIMESTAMP('2025-04-02 15:55:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2025-04-02T14:55:57.616Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-02T14:55:57.719Z
/* DDL */  select update_Column_Translation_From_AD_Element(583563) 
;

-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T14:56:17.219Z
UPDATE AD_Column SET AD_Reference_ID=18, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-04-02 15:56:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589828
;

-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T15:09:48.885Z
UPDATE AD_Column SET AD_Reference_ID=27,Updated=TO_TIMESTAMP('2025-04-02 16:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589828
;

-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- Column SQL (old): (CASE WHEN C_Invoice_Candidate.QtyDelivered = 0 THEN 1000000 WHEN (C_Invoice_Candidate.QtyDelivered > 0 AND C_Invoice_Candidate.QtyDelivered < C_Invoice_Candidate.QtyOrdered) THEN 1000003 WHEN (C_Invoice_Candidate.QtyDelivered = C_Invoice_Candidate.QtyOrdered) THEN 1000001 WHEN (C_Invoice_Candidate.QtyDelivered > C_Invoice_Candidate.QtyOrdered) THEN 1000004 END)
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- Column SQL (old): (CASE WHEN C_Invoice_Candidate.QtyDelivered = 0 THEN 1000000 WHEN (C_Invoice_Candidate.QtyDelivered > 0 AND C_Invoice_Candidate.QtyDelivered < C_Invoice_Candidate.QtyOrdered) THEN 1000003 WHEN (C_Invoice_Candidate.QtyDelivered = C_Invoice_Candidate.QtyOrdered) THEN 1000001 WHEN (C_Invoice_Candidate.QtyDelivered > C_Invoice_Candidate.QtyOrdered) THEN 1000004 END)
-- 2025-04-02T15:57:24.984Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_Invoice_Candidate.QtyDelivered = 0 THEN 1000000 WHEN (C_Invoice_Candidate.QtyDelivered > 0 AND C_Invoice_Candidate.QtyDelivered < C_Invoice_Candidate.QtyOrdered) THEN 1000003 WHEN (C_Invoice_Candidate.QtyDelivered = C_Invoice_Candidate.QtyOrdered) THEN 1000001 WHEN (C_Invoice_Candidate.QtyDelivered > C_Invoice_Candidate.QtyOrdered) THEN 540006 END)',Updated=TO_TIMESTAMP('2025-04-02 16:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589828
;

-- Field: Rechnungsdisposition_OLD -> Rechnungskandidaten -> Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T16:16:50.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589828,741856,0,540279,0,TO_TIMESTAMP('2025-04-02 17:16:49','YYYY-MM-DD HH24:MI:SS'),100,'Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.',0,'D','Rot: Keine Artikel geliefert.
Gelb: Teillieferung erfolgt.
Grün: Die gesamte Bestellung wurde geliefert.
Blau: Es wurden mehr Artikel als bestellt geliefert.',0,'Y','Y','Y','N','N','N','N','N','Lieferstatus',0,570,0,1,1,TO_TIMESTAMP('2025-04-02 17:16:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-04-02T16:16:50.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=741856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-02T16:16:51.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583563) 
;

-- 2025-04-02T16:16:51.054Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741856
;

-- 2025-04-02T16:16:51.105Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741856)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T16:17:40.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741856,0,540279,542704,631302,'F',TO_TIMESTAMP('2025-04-02 17:17:40','YYYY-MM-DD HH24:MI:SS'),100,'Rot: Keine Artikel geliefert. Gelb: Teillieferung erfolgt. Grün: Die gesamte Bestellung wurde geliefert. Blau: Es wurden mehr Artikel als bestellt geliefert.','Rot: Keine Artikel geliefert.
Gelb: Teillieferung erfolgt.
Grün: Die gesamte Bestellung wurde geliefert.
Blau: Es wurden mehr Artikel als bestellt geliefert.','Y','N','N','Y','N','N','N',0,'Lieferstatus',90,0,0,TO_TIMESTAMP('2025-04-02 17:17:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T16:17:59.235Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-04-02 17:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631302
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2025-04-02T16:18:25.315Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-04-02 17:18:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=631302
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2025-04-02T16:18:25.738Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-04-02 17:18:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2025-04-02T16:18:26.177Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-04-02 17:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2025-04-02T16:18:26.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-04-02 17:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541084
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2025-04-02T16:18:26.998Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-04-02 17:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541085
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2025-04-02T16:18:27.400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-04-02 17:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548120
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2025-04-02T16:18:27.815Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-04-02 17:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

