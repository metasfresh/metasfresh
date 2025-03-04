-- Column: C_Invoice_Candidate.C_BPartner_SalesRep_ID
-- Column: C_Invoice_Candidate.C_BPartner_SalesRep_ID
-- 2025-02-28T14:20:33.316Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-02-28 15:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568741
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.C_BPartner_SalesRep_ID
-- Column: C_Invoice_Candidate.C_BPartner_SalesRep_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> default.C_BPartner_SalesRep_ID
-- Column: C_Invoice_Candidate.C_BPartner_SalesRep_ID
-- 2025-02-28T14:25:18.975Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-02-28 15:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562101
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.C_Order_ID
-- Column: C_Invoice_Candidate.C_Order_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> default.C_Order_ID
-- Column: C_Invoice_Candidate.C_Order_ID
-- 2025-02-28T14:25:19.398Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-02-28 15:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541081
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Produkt
-- Column: C_Invoice_Candidate.M_Product_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> default.Produkt
-- Column: C_Invoice_Candidate.M_Product_ID
-- 2025-02-28T14:25:19.842Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-02-28 15:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541082
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Produkt-Kategorie
-- Column: C_Invoice_Candidate.M_Product_Category_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> default.Produkt-Kategorie
-- Column: C_Invoice_Candidate.M_Product_Category_ID
-- 2025-02-28T14:25:20.305Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-02-28 15:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541083
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.IsToRecompute
-- Column: C_Invoice_Candidate.IsToRecompute
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.IsToRecompute
-- Column: C_Invoice_Candidate.IsToRecompute
-- 2025-02-28T14:25:20.742Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-02-28 15:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541086
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Freigabe zur Fakturierung
-- Column: C_Invoice_Candidate.ApprovalForInvoicing
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Freigabe zur Fakturierung
-- Column: C_Invoice_Candidate.ApprovalForInvoicing
-- 2025-02-28T14:25:21.152Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-02-28 15:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541058
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.QtyOrdered
-- Column: C_Invoice_Candidate.QtyOrdered
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyOrdered
-- Column: C_Invoice_Candidate.QtyOrdered
-- 2025-02-28T14:25:21.553Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-02-28 15:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548108
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Gelieferte Menge
-- Column: C_Invoice_Candidate.QtyDelivered
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Gelieferte Menge
-- Column: C_Invoice_Candidate.QtyDelivered
-- 2025-02-28T14:25:21.954Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-02-28 15:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548109
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2025-02-28T14:25:22.376Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-02-28 15:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2025-02-28T14:25:22.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-02-28 15:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2025-02-28T14:25:23.215Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-02-28 15:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541084
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2025-02-28T14:25:23.627Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-02-28 15:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541085
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2025-02-28T14:25:24.038Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-02-28 15:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548120
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2025-02-28T14:25:24.435Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-02-28 15:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

