-- Field: Bank Statement(194,D) -> Statement Line(329,D) -> Invoice
-- Column: C_BankStatementLine.C_Invoice_ID
-- 2022-10-25T08:45:55.774Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-10-25 10:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8934
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Sektion
-- Column: C_BankStatementLine.AD_Org_ID
-- 2022-10-25T08:47:53.588Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-25 10:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543090
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Beschreibung
-- Column: C_BankStatementLine.Description
-- 2022-10-25T08:48:30.564Z
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2022-10-25 10:48:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543093
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Memo
-- Column: C_BankStatementLine.Memo
-- 2022-10-25T09:39:18.990Z
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2022-10-25 11:39:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543113
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Memo
-- Column: C_BankStatementLine.Memo
-- 2022-10-25T09:39:48.783Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-10-25 11:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543113
;

