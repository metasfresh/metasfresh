-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.IsEdiInvoicRecipient
-- 2026-03-10T13:30:15.710Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592213,542000,0,20,540270,'XX','IsEdiInvoicRecipient','(SELECT IsEdiInvoicRecipient from C_BPartner where C_BPartner_ID = C_Invoice_Candidate.Bill_BPartner_ID)',TO_TIMESTAMP('2026-03-10 13:30:15.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','','de.metas.invoicecandidate',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N',0,'Erhält EDI-INVOIC',0,0,TO_TIMESTAMP('2026-03-10 13:30:15.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-10T13:30:15.898Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-10T13:30:16.049Z
/* DDL */  select update_Column_Translation_From_AD_Element(542000)
;

-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2026-03-10T13:33:01.335Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2026-03-10 13:33:01.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=575016
;

-- Column: C_Invoice_Candidate.C_BPartner_SalesRep_ID
-- 2026-03-10T13:33:13.714Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2026-03-10 13:33:13.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568741
;

-- Column: C_Invoice_Candidate.IsAutoInvoice
-- 2026-03-10T13:33:21.294Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2026-03-10 13:33:21.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591495
;

-- Column: C_Invoice_Candidate.Bill_BPartner_ID
-- 2026-03-10T13:33:28.822Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2026-03-10 13:33:28.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544920
;

-- Column: C_Invoice_Candidate.POReference
-- 2026-03-10T13:33:36.659Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2026-03-10 13:33:36.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551469
;

-- Column: C_Invoice_Candidate.ExternalHeaderId
-- 2026-03-10T13:33:43.948Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2026-03-10 13:33:43.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569216
;

-- Column: C_Invoice_Candidate.C_Activity_ID
-- 2026-03-10T13:33:52.325Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2026-03-10 13:33:52.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551072
;

-- Column: C_Invoice_Candidate.C_DocTypeInvoice_ID
-- 2026-03-10T13:34:00.687Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2026-03-10 13:34:00.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551286
;

-- Column: C_Invoice_Candidate.C_ILCandHandler_ID
-- 2026-03-10T13:34:08.418Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2026-03-10 13:34:08.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=546203
;

-- Column: C_Invoice_Candidate.C_InvoiceSchedule_ID
-- 2026-03-10T13:34:17.800Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2026-03-10 13:34:17.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=546600
;

-- Column: C_Invoice_Candidate.C_Order_BPartner
-- 2026-03-10T13:34:28.426Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2026-03-10 13:34:28.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551953
;

-- Column: C_Invoice_Candidate.C_Order_ID
-- 2026-03-10T13:34:41.290Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2026-03-10 13:34:41.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544913
;

-- Column: C_Invoice_Candidate.DateToInvoice
-- 2026-03-10T13:34:49.307Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2026-03-10 13:34:49.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=546339
;

-- Column: C_Invoice_Candidate.DeliveryDate
-- 2026-03-10T13:34:58.286Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=140,Updated=TO_TIMESTAMP('2026-03-10 13:34:58.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551761
;

-- Column: C_Invoice_Candidate.ApprovalForInvoicing
-- 2026-03-10T13:35:06.907Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=150,Updated=TO_TIMESTAMP('2026-03-10 13:35:06.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551293
;

-- Column: C_Invoice_Candidate.IsEdiInvoicRecipient
-- 2026-03-10T13:35:14.685Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160,Updated=TO_TIMESTAMP('2026-03-10 13:35:14.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592213
;

-- Column: C_Invoice_Candidate.IsError
-- 2026-03-10T13:35:21.739Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=170,Updated=TO_TIMESTAMP('2026-03-10 13:35:21.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=546227
;

-- Column: C_Invoice_Candidate.IsMaterialTracking
-- 2026-03-10T13:35:32.050Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=180,Updated=TO_TIMESTAMP('2026-03-10 13:35:32.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551796
;

-- Column: C_Invoice_Candidate.Processed
-- 2026-03-10T13:35:42.774Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190,Updated=TO_TIMESTAMP('2026-03-10 13:35:42.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=545771
;

-- Column: C_Invoice_Candidate.IsSOTrx
-- 2026-03-10T13:35:52.314Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=200,Updated=TO_TIMESTAMP('2026-03-10 13:35:52.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549847
;

-- Column: C_Invoice_Candidate.IsInDispute
-- 2026-03-10T13:36:03.170Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=210,Updated=TO_TIMESTAMP('2026-03-10 13:36:03.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550515
;

-- Column: C_Invoice_Candidate.M_InOut_ID
-- 2026-03-10T13:36:11.625Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=220,Updated=TO_TIMESTAMP('2026-03-10 13:36:11.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551762
;

-- Column: C_Invoice_Candidate.M_Material_Tracking_ID
-- 2026-03-10T13:36:21.631Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=230,Updated=TO_TIMESTAMP('2026-03-10 13:36:21.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551290
;

-- Column: C_Invoice_Candidate.M_PricingSystem_ID
-- 2026-03-10T13:36:29.898Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=240,Updated=TO_TIMESTAMP('2026-03-10 13:36:29.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=545852
;

-- Column: C_Invoice_Candidate.M_Product_Category_ID
-- 2026-03-10T13:36:38.739Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=250,Updated=TO_TIMESTAMP('2026-03-10 13:36:38.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=552289
;

-- Column: C_Invoice_Candidate.M_Product_ID
-- 2026-03-10T13:36:48.284Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=260,Updated=TO_TIMESTAMP('2026-03-10 13:36:48.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544923
;

-- Column: C_Invoice_Candidate.QtyDelivered
-- 2026-03-10T13:36:58.145Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=270,Updated=TO_TIMESTAMP('2026-03-10 13:36:58.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=545314
;

-- Column: C_Invoice_Candidate.C_Currency_ID
-- 2026-03-10T13:37:06.097Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=280,Updated=TO_TIMESTAMP('2026-03-10 13:37:06.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=545316
;

-- Column: C_Invoice_Candidate.IsActive
-- 2026-03-10T13:37:15.121Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=290,Updated=TO_TIMESTAMP('2026-03-10 13:37:15.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544907
;

-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2026-03-10T13:37:21.821Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=300,Updated=TO_TIMESTAMP('2026-03-10 13:37:21.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544902
;

-- Field: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Erhält EDI-INVOIC
-- Column: C_Invoice_Candidate.IsEdiInvoicRecipient
-- 2026-03-10T13:41:18.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592213,774893,0,540279,0,TO_TIMESTAMP('2026-03-10 13:41:17.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erhält EDI-INVOIC',0,0,600,0,1,1,TO_TIMESTAMP('2026-03-10 13:41:17.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-10T13:41:18.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-10T13:41:18.646Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542000)
;

-- 2026-03-10T13:41:18.715Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774893
;

-- 2026-03-10T13:41:18.778Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774893)
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Erhält EDI-INVOIC
-- Column: C_Invoice_Candidate.IsEdiInvoicRecipient
-- 2026-03-10T13:42:36.984Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774893,0,540279,540058,648542,'F',TO_TIMESTAMP('2026-03-10 13:42:36.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erhält EDI-INVOIC',39,0,0,TO_TIMESTAMP('2026-03-10 13:42:36.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Erhält EDI-INVOIC
-- Column: C_Invoice_Candidate.IsEdiInvoicRecipient
-- 2026-03-10T13:43:26.695Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-10 13:43:26.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648542
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyOrdered
-- Column: C_Invoice_Candidate.QtyOrdered
-- 2026-03-10T13:43:27.223Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-03-10 13:43:27.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548108
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Gelieferte Menge
-- Column: C_Invoice_Candidate.QtyDelivered
-- 2026-03-10T13:43:27.649Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-03-10 13:43:27.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548109
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Lieferstatus
-- Column: C_Invoice_Candidate.DeliveryStatusColor_ID
-- 2026-03-10T13:43:28.077Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-03-10 13:43:28.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631302
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2026-03-10T13:43:28.506Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-03-10 13:43:28.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2026-03-10T13:43:28.935Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-03-10 13:43:28.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.Rechnungsstatus
-- Column: C_Invoice_Candidate.InvoicingStatusColor_ID
-- 2026-03-10T13:43:29.362Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-03-10 13:43:29.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631303
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2026-03-10T13:43:29.791Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-03-10 13:43:29.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541084
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> qtyInUOM.QtyDeliveredInUOM
-- Column: C_Invoice_Candidate.QtyDeliveredInUOM
-- 2026-03-10T13:43:30.219Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-03-10 13:43:30.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560329
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2026-03-10T13:43:30.646Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-03-10 13:43:30.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541085
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> totals.NetAmtInvoiced
-- Column: C_Invoice_Candidate.NetAmtInvoiced
-- 2026-03-10T13:43:31.073Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-03-10 13:43:31.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560339
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2026-03-10T13:43:31.504Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2026-03-10 13:43:31.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548120
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2026-03-10T13:43:31.927Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2026-03-10 13:43:31.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

-- UI Element: Rechnungsdisposition Verkauf(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> totals.NetAmtToInvoice
-- Column: C_Invoice_Candidate.NetAmtToInvoice
-- 2026-03-10T13:43:32.349Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2026-03-10 13:43:32.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560340
;

