
-- Field: Bankauszug -> Auszugs-Position -> Effektives Datum
-- Column: C_BankStatementLine.StatementLineDate
-- 2024-08-28T09:06:36.198Z
UPDATE AD_Field SET AD_Reference_ID=15,Updated=TO_TIMESTAMP('2024-08-28 11:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=639387
;

-- Field: Bankauszug -> Auszugs-Position -> Buchungsdatum
-- Column: C_BankStatementLine.DateAcct
-- 2024-08-28T09:06:51.276Z
UPDATE AD_Field SET AD_Reference_ID=15,Updated=TO_TIMESTAMP('2024-08-28 11:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=639370
;

-- UI Element: Bankauszug -> Auszugs-Position.Imported Bill Partner Name
-- Column: C_BankStatementLine.ImportedBillPartnerName
-- 2024-08-28T09:07:23.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-08-28 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581341
;

-- UI Element: Bankauszug -> Auszugs-Position.Abgeglichen
-- Column: C_BankStatementLine.IsReconciled
-- 2024-08-28T09:07:23.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-08-28 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581335
;

-- UI Element: Bankauszug -> Auszugs-Position.Zahlung
-- Column: C_BankStatementLine.C_Payment_ID
-- 2024-08-28T09:07:23.684Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-08-28 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581337
;

-- UI Element: Bankauszug -> Auszugs-Position.Geschäftspartner
-- Column: C_BankStatementLine.C_BPartner_ID
-- 2024-08-28T09:07:23.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-08-28 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581339
;

-- UI Element: Bankauszug -> Auszugs-Position.Debitor No./Kreditor No.
-- Column: C_BankStatementLine.DebitorOrCreditorId
-- 2024-08-28T09:07:23.953Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-08-28 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581340
;

-- UI Element: Bankauszug -> Auszugs-Position.Beschreibung
-- Column: C_BankStatementLine.Description
-- 2024-08-28T09:07:24.095Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-08-28 11:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581342
;

-- UI Element: Bankauszug -> Auszugs-Position.Sektion
-- Column: C_BankStatementLine.AD_Org_ID
-- 2024-08-28T09:07:24.239Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-08-28 11:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581347
;

