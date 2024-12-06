-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc type & date.Document No
-- Column: SAP_GLJournal.DocumentNo
-- 2022-12-17T07:25:52.228Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614521
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc type & date.Accounting Date
-- Column: SAP_GLJournal.DateAcct
-- 2022-12-17T07:25:52.240Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614531
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.Accounting Schema
-- Column: SAP_GLJournal.C_AcctSchema_ID
-- 2022-12-17T07:25:52.246Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614540
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.Posting Type
-- Column: SAP_GLJournal.PostingType
-- 2022-12-17T07:25:52.252Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614524
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> currency.Currency
-- Column: SAP_GLJournal.C_Currency_ID
-- 2022-12-17T07:25:52.259Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614525
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> control amts.Total Debit
-- Column: SAP_GLJournal.TotalDr
-- 2022-12-17T07:25:52.265Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614527
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> control amts.Total Credit
-- Column: SAP_GLJournal.TotalCr
-- 2022-12-17T07:25:52.271Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614528
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc status.Status
-- Column: SAP_GLJournal.DocStatus
-- 2022-12-17T07:25:52.277Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614533
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> posting.Posting status
-- Column: SAP_GLJournal.Posted
-- 2022-12-17T07:25:52.283Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614534
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> org.Section Code
-- Column: SAP_GLJournal.M_SectionCode_ID
-- 2022-12-17T07:25:52.289Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614536
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> org.Organisation
-- Column: SAP_GLJournal.AD_Org_ID
-- 2022-12-17T07:25:52.294Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-17 09:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614537
;

-- Column: SAP_GLJournal.GL_Category_ID
-- 2022-12-17T07:26:53.550Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-17 09:26:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585349
;

-- Column: SAP_GLJournal.DocStatus
-- 2022-12-17T07:27:50.776Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-17 09:27:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585346
;

-- Column: SAP_GLJournal.Description
-- 2022-12-17T07:28:03.983Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-17 09:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585344
;

-- Column: SAP_GLJournal.C_AcctSchema_ID
-- 2022-12-17T07:28:15.175Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-17 09:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585333
;

-- Column: SAP_GLJournal.C_Currency_ID
-- 2022-12-17T07:28:29.704Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-17 09:28:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585335
;

-- Column: SAP_GLJournal.C_DocType_ID
-- 2022-12-17T07:28:44.294Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-17 09:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585336
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Document No
-- Column: SAP_GLJournal.DocumentNo
-- 2022-12-17T07:29:48.347Z
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2022-12-17 09:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709983
;

