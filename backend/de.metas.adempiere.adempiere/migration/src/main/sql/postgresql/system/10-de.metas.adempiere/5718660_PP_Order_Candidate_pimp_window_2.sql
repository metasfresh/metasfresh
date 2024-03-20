-- UI Element: Produktionsdisposition -> Produktionsdisposition.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- 2024-03-06T14:31:40.047Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-03-06 16:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594542
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Produktionsressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Produktionsressource
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2024-03-06T14:31:44.951Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-03-06 16:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594539
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T14:31:47.620Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2024-03-06 16:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623744
;

-- Column: PP_Order_Candidate.M_Product_ID
-- Column: PP_Order_Candidate.M_Product_ID
-- 2024-03-06T14:34:19.215Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2024-03-06 16:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577880
;

-- Column: PP_Order_Candidate.S_Resource_ID
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2024-03-06T14:34:19.616Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2024-03-06 16:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577877
;

-- Column: PP_Order_Candidate.WorkStation_ID
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T14:34:20.027Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2024-03-06 16:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587967
;

-- Column: PP_Order_Candidate.C_OrderLine_ID
-- Column: PP_Order_Candidate.C_OrderLine_ID
-- 2024-03-06T14:34:20.417Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2024-03-06 16:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577882
;

-- Column: PP_Order_Candidate.DateStartSchedule
-- Column: PP_Order_Candidate.DateStartSchedule
-- 2024-03-06T14:34:20.744Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2024-03-06 16:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577884
;

-- Column: PP_Order_Candidate.AD_Org_ID
-- Column: PP_Order_Candidate.AD_Org_ID
-- 2024-03-06T14:34:21.031Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2024-03-06 16:34:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577869
;

-- Column: PP_Order_Candidate.Processed
-- Column: PP_Order_Candidate.Processed
-- 2024-03-06T14:34:21.312Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2024-03-06 16:34:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577887
;

