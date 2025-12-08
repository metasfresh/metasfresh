-- UI Element: Geschäftspartner -> Nutzer / Kontakt.eMail per Rechnung
-- Column: AD_User.IsInvoiceEmailEnabled
-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.eMail per Rechnung
-- Column: AD_User.IsInvoiceEmailEnabled
-- 2024-12-16T13:21:39.680Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-12-16 14:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544832
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Rechnung per eMail
-- Column: C_BPartner.IsInvoiceEmailEnabled
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Rechnung per eMail
-- Column: C_BPartner.IsInvoiceEmailEnabled
-- 2024-12-16T13:31:41.959Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557309,0,220,1000011,627395,'F',TO_TIMESTAMP('2024-12-16 14:31:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung per eMail',70,0,0,TO_TIMESTAMP('2024-12-16 14:31:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Rechnung per eMail
-- Column: C_BPartner.IsInvoiceEmailEnabled
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Rechnung per eMail
-- Column: C_BPartner.IsInvoiceEmailEnabled
-- 2024-12-16T13:33:43.475Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545715
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Replikations Standard
-- Column: C_BPartner.IsReplicationLookupDefault
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Replikations Standard
-- Column: C_BPartner.IsReplicationLookupDefault
-- 2024-12-16T15:00:48.064Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540671, SeqNo=160,Updated=TO_TIMESTAMP('2024-12-16 16:00:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000085
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Replikations Standard
-- Column: C_BPartner.IsReplicationLookupDefault
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Replikations Standard
-- Column: C_BPartner.IsReplicationLookupDefault
-- 2024-12-16T15:03:07.995Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-12-16 16:03:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000085
;

