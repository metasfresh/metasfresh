-- Window "Zahlungsplan" (AD_Window_ID=275), header tab "Rechnung" (AD_Tab_ID=501): follow-up to 5829360.
-- Right column like the other document windows (sales order, sales invoice): no 'flags' group (that name means
-- IsActive first), but a 'posted' group with posting status and accounting date before the organisation.

-- the group holding the payment schedule flags is not a 'flags' group
UPDATE AD_UI_ElementGroup SET Name='payment schedule', Updated=TO_TIMESTAMP('2026-10-14 15:40:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=555827
;

-- new group 'posted' between 'payment schedule' (20) and 'org' (30)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549785,555829 /*From ID Server*/,TO_TIMESTAMP('2026-10-14 15:40:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',25,NULL,TO_TIMESTAMP('2026-10-14 15:40:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Posted: shown in the form and in the grid, after the document status (150) and before the organisation (160)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6894,0,501,555829,655042 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-14 15:40:03','YYYY-MM-DD HH24:MI:SS'),100,'Buchungsstatus','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','Y','Y','N','Buchungsstatus',10,155,0,TO_TIMESTAMP('2026-10-14 15:40:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- DateAcct: shown in the form only
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6893,0,501,555829,655043 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-14 15:40:04','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','Buchungsdatum',20,0,0,TO_TIMESTAMP('2026-10-14 15:40:04','YYYY-MM-DD HH24:MI:SS'),100)
;
