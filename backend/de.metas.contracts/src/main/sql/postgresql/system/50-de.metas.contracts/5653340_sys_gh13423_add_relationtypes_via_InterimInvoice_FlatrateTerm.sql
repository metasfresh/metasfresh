-- Name: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- 2022-08-26T10:52:53.480Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541645,TO_TIMESTAMP('2022-08-26 13:52:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm',TO_TIMESTAMP('2022-08-26 13:52:53','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-26T10:52:53.482Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541645 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-26T10:54:03.296Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,544906,0,541645,540270,540983,TO_TIMESTAMP('2022-08-26 13:54:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-26 13:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-26T10:55:34.203Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,541177,540358,TO_TIMESTAMP('2022-08-26 13:55:34','YYYY-MM-DD HH24:MI:SS'),100,'C_Order (PO) -> C_InvoiceCandidate via C_InterimInvoice_FlatrateTerm','de.metas.contracts','Y','N','C_Order (PO) -> C_InvoiceCandidate via C_InterimInvoice_FT',TO_TIMESTAMP('2022-08-26 13:55:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-26T10:56:15.878Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541645,Updated=TO_TIMESTAMP('2022-08-26 13:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540358
;



-- Reference: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-26T11:09:54.751Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1              from c_invoice_candidate ic                       JOIN c_interiminvoice_flatrateterm iift on ic.c_invoice_candidate_id = iift.c_interim_invoice_candidate_id OR ic.c_invoice_candidate_id = iift.c_invoice_candidate_withholding_id                       JOIN C_Order o on iift.C_Order_ID = o.C_Order_ID              where o.C_Order_ID = @C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2022-08-26 14:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Name: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- 2022-08-26T11:10:12.549Z
UPDATE AD_Reference SET Help='uses C_Order_ID',Updated=TO_TIMESTAMP('2022-08-26 14:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Reference: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-26T11:15:36.596Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1              from c_invoice_candidate ic                       JOIN c_interiminvoice_flatrateterm iift on ic.c_invoice_candidate_id = iift.c_interim_invoice_candidate_id OR ic.c_invoice_candidate_id = iift.c_invoice_candidate_withholding_id                       JOIN C_OrderLine ol on ol.c_orderline_id = iift.c_orderline_id                       JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID              where o.C_Order_ID = @C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2022-08-26 14:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Reference: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-26T11:16:57.548Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1              from c_invoice_candidate ic                       JOIN c_interiminvoice_flatrateterm iift on ic.c_invoice_candidate_id = iift.c_interim_invoice_candidate_id OR ic.c_invoice_candidate_id = iift.c_invoice_candidate_withholding_id                       JOIN C_OrderLine ol on ol.c_orderline_id = iift.c_orderline_id                       JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID              where o.C_Order_ID = @C_Order_ID/-1@ AND c_invoice_Candidate.c_invoice_candidate_id = ic.c_invoice_candidate_id)',Updated=TO_TIMESTAMP('2022-08-26 14:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Reference: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-26T11:18:14.102Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1              from c_invoice_candidate ic                       JOIN c_interiminvoice_flatrateterm iift on ic.c_invoice_candidate_id = iift.c_interim_invoice_candidate_id OR ic.c_invoice_candidate_id = iift.c_invoice_candidate_withholding_id                       JOIN C_OrderLine ol on ol.c_orderline_id = iift.c_orderline_id                       JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID              where o.C_Order_ID = @C_Order_ID/-1@ AND c_invoice_Candidate.c_invoice_candidate_id = ic.c_invoice_candidate_id) OR c_order_id = @C_Order_ID/-1@',Updated=TO_TIMESTAMP('2022-08-26 14:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Reference: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-26T11:18:29.292Z
UPDATE AD_Ref_Table SET WhereClause='c_order_id = @C_Order_ID/-1@ OR EXISTS(SELECT 1              from c_invoice_candidate ic                       JOIN c_interiminvoice_flatrateterm iift on ic.c_invoice_candidate_id = iift.c_interim_invoice_candidate_id OR ic.c_invoice_candidate_id = iift.c_invoice_candidate_withholding_id                       JOIN C_OrderLine ol on ol.c_orderline_id = iift.c_orderline_id                       JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID              where o.C_Order_ID = @C_Order_ID/-1@ AND c_invoice_Candidate.c_invoice_candidate_id = ic.c_invoice_candidate_id)',Updated=TO_TIMESTAMP('2022-08-26 14:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Name: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- 2022-08-26T11:22:05.025Z
UPDATE AD_Reference SET Help='uses C_InterimInvoice.FlatrateTerm.C_OrderLine_ID, includes default relation (using ic.C_order_ID)',Updated=TO_TIMESTAMP('2022-08-26 14:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541645
;

-- Name: C_Invoice via C_InterimInvoice_FlatrateTerm
-- 2022-08-26T11:22:34.653Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541646,TO_TIMESTAMP('2022-08-26 14:22:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','uses C_InterimInvoice.FlatrateTerm.C_OrderLine_ID, includes default relation (using C_Invoice.C_order_ID)','Y','N','C_Invoice via C_InterimInvoice_FlatrateTerm',TO_TIMESTAMP('2022-08-26 14:22:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-26T11:22:34.654Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541646 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2022-08-26T11:24:29.769Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3484,0,541646,318,183,TO_TIMESTAMP('2022-08-26 14:24:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-26 14:24:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Invoice(PO) via C_InterimInvoice_FlatrateTerm
-- 2022-08-26T11:24:40.885Z
UPDATE AD_Reference SET Help='uses C_InterimInvoice.FlatrateTerm.C_OrderLine_ID, includes default relation (using C_Invoice.C_order_ID) ', Name='C_Invoice(PO) via C_InterimInvoice_FlatrateTerm',Updated=TO_TIMESTAMP('2022-08-26 14:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541646
;

-- Reference: C_Invoice(PO) via C_InterimInvoice_FlatrateTerm
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2022-08-26T11:31:16.762Z
UPDATE AD_Ref_Table SET WhereClause='c_order_id = @C_Order_ID/-1@ OR EXISTS(SELECT 1          from c_invoice i                   JOIN c_invoiceline il on i.c_invoice_id = il.c_invoice_id                   JOIN c_invoice_line_alloc ila on ila.c_invoiceline_id = il.c_invoiceline_id                   JOIN c_invoice_candidate ic on ila.c_invoice_candidate_id = ic.c_invoice_candidate_id                   JOIN c_interiminvoice_flatrateterm iift on ic.c_invoice_candidate_id = iift.c_interim_invoice_candidate_id OR ic.c_invoice_candidate_id = iift.c_invoice_candidate_withholding_id                   JOIN C_OrderLine ol on ol.c_orderline_id = iift.c_orderline_id                   JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID          where o.C_Order_ID =  @C_Order_ID/-1@            AND C_invoice.c_invoice_id = i.c_invoice_id         )',Updated=TO_TIMESTAMP('2022-08-26 14:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541646
;

-- Name: C_Invoice(Vendor)targetFor_C_Order
-- 2022-08-26T11:36:25.256Z
UPDATE AD_Reference SET Help='Uses:
- C_Invoice.C_Order_ID
- C_InterimInvoice_FlatrateTerm.C_Interim_Invoice_Candidate_ID via C_Invoice_Line_Alloc
- C_InterimInvoice_FlatrateTerm.C_Invoice_Candidate_Withholding_ID via C_Invoice_Line_Alloc', Name='C_Invoice(Vendor)targetFor_C_Order',Updated=TO_TIMESTAMP('2022-08-26 14:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541646
;

-- 2022-08-26T11:36:54.386Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,336,540359,TO_TIMESTAMP('2022-08-26 14:36:54','YYYY-MM-DD HH24:MI:SS'),100,'C_Order (PO) -> C_Invoice via C_InterimInvoice_FlatrateTerm','de.metas.swat','Y','N','C_Order (PO) -> C_InvoiceCandidate via C_InterimInvoice_FT',TO_TIMESTAMP('2022-08-26 14:36:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Invoice(Vendor) targetFor C_Order
-- 2022-08-26T11:39:47.053Z
UPDATE AD_Reference_Trl SET Name='C_Invoice(Vendor) targetFor C_Order',Updated=TO_TIMESTAMP('2022-08-26 14:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541646
;

-- 2022-08-26T11:39:50.365Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541646,Updated=TO_TIMESTAMP('2022-08-26 14:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540359
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Rechnungs-Belegart
-- Column: C_Invoice_Candidate.C_DocTypeInvoice_ID
-- 2022-08-26T11:46:33.580Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-26 14:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573430
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2022-08-26T11:46:33.586Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-26 14:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573407
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2022-08-26T11:46:33.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-08-26 14:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573397
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2022-08-26T11:46:33.596Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-08-26 14:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573420
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2022-08-26T11:47:51.366Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-26 14:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573407
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2022-08-26T11:47:51.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-26 14:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573397
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Rechnungs-Belegart
-- Column: C_Invoice_Candidate.C_DocTypeInvoice_ID
-- 2022-08-26T11:47:51.376Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-08-26 14:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573430
;

