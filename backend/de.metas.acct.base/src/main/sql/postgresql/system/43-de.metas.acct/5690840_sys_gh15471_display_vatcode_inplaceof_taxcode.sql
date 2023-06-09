-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.Tax Code
-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-09T10:42:50.559859231Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617939
;

-- 2023-06-09T10:42:50.565664091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716173
;

-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Steuercode
-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-09T10:42:50.569488546Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716173
;

-- 2023-06-09T10:42:50.572391897Z
DELETE FROM AD_Field WHERE AD_Field_ID=716173
;

-- Column: Fact_Acct_Transactions_View.TaxCode
-- 2023-06-09T10:43:03.168881313Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586739
;

-- 2023-06-09T10:43:03.171945633Z
DELETE FROM AD_Column WHERE AD_Column_ID=586739
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.VAT Code
-- Column: Fact_Acct_Transactions_View.VATCode
-- 2023-06-09T10:46:14.218017165Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710018,0,242,540307,617964,'F',TO_TIMESTAMP('2023-06-09 11:46:13.948','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'VAT Code',70,0,0,TO_TIMESTAMP('2023-06-09 11:46:13.948','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> document.VAT Code
-- Column: Fact_Acct_Transactions_View.VATCode
-- 2023-06-09T10:50:11.870586531Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-06-09 11:50:11.869','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617964
;

-- Column: Fact_Acct_Transactions_View.VATCode
-- 2023-06-09T10:57:35.392209032Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-06-09 11:57:35.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=571110
;

