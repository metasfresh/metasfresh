-- Run mode: SWING_CLIENT

-- me03#31052 Nach Beträgen suchen können: enable amount from/to (Between) filter

-- Column: C_Invoice_Candidate.NetAmtToInvoice
-- 2026-07-28T10:00:00.000Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-28 10:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545315
;

-- Column: C_Invoice_Candidate.NetAmtInvoiced
-- 2026-07-28T10:00:00.000Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-28 10:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=545854
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Freigabe zur Fakturierung
-- Column: C_Invoice_Candidate.ApprovalForInvoicing
-- 2026-07-28T14:49:49.966Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-07-28 15:49:49.966','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541058
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyOrdered
-- Column: C_Invoice_Candidate.QtyOrdered
-- 2026-07-28T14:49:50.346Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-07-28 15:49:50.346','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548108
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Gelieferte Menge
-- Column: C_Invoice_Candidate.QtyDelivered
-- 2026-07-28T14:49:50.727Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-07-28 15:49:50.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548109
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Lieferung geschlossen
-- Column: C_Invoice_Candidate.IsDeliveryClosed
-- 2026-07-28T14:49:51.109Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-07-28 15:49:51.109','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=609542
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2026-07-28T14:49:51.497Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-07-28 15:49:51.497','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2026-07-28T14:49:51.857Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-07-28 15:49:51.857','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2026-07-28T14:49:52.219Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-07-28 15:49:52.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541084
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> totals.NetAmtInvoiced
-- Column: C_Invoice_Candidate.NetAmtInvoiced
-- 2026-07-28T14:49:52.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-07-28 15:49:52.591','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=560339
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> totals.NetAmtToInvoice
-- Column: C_Invoice_Candidate.NetAmtToInvoice
-- 2026-07-28T14:49:53.232Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-07-28 15:49:53.232','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=560340
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2026-07-28T14:49:53.860Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-07-28 15:49:53.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541085
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> financial.Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2026-07-28T14:49:54.240Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-07-28 15:49:54.24','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611336
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2026-07-28T14:49:54.678Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-07-28 15:49:54.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2026-07-28T14:49:55.065Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-07-28 15:49:55.064','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548120
;

-- Run mode: SWING_CLIENT

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyOrdered
-- Column: C_Invoice_Candidate.QtyOrdered
-- 2026-07-28T14:56:42.192Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-07-28 15:56:42.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573388
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Gelieferte Menge
-- Column: C_Invoice_Candidate.QtyDelivered
-- 2026-07-28T14:56:42.552Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-07-28 15:56:42.552','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573389
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2026-07-28T14:56:42.906Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-07-28 15:56:42.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573390
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2026-07-28T14:56:43.326Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-07-28 15:56:43.326','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573392
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2026-07-28T14:56:43.695Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-07-28 15:56:43.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573406
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> totals.NetAmtInvoiced
-- Column: C_Invoice_Candidate.NetAmtInvoiced
-- 2026-07-28T14:56:44.076Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-07-28 15:56:44.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573418
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> totals.NetAmtToInvoice
-- Column: C_Invoice_Candidate.NetAmtToInvoice
-- 2026-07-28T14:56:44.458Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-07-28 15:56:44.458','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573419
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2026-07-28T14:56:44.823Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-07-28 15:56:44.823','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573407
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Rechnungs-Belegart
-- Column: C_Invoice_Candidate.C_DocTypeInvoice_ID
-- 2026-07-28T14:56:45.186Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-07-28 15:56:45.186','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573430
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2026-07-28T14:56:45.546Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-07-28 15:56:45.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573397
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2026-07-28T14:56:45.901Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2026-07-28 15:56:45.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=573420
;


