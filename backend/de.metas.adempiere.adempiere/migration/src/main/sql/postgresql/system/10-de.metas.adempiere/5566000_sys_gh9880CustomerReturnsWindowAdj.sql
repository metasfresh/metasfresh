-- 2020-08-31T14:53:02.377Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540080,544052,TO_TIMESTAMP('2020-08-31 16:53:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','order',15,TO_TIMESTAMP('2020-08-31 16:53:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:53:13.756Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57711,0,53271,544052,571053,'F',TO_TIMESTAMP('2020-08-31 16:53:13','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','N','N',0,'Auftrag',10,0,0,TO_TIMESTAMP('2020-08-31 16:53:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:53:21.853Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57712,0,53271,544052,571054,'F',TO_TIMESTAMP('2020-08-31 16:53:21','YYYY-MM-DD HH24:MI:SS'),100,'Date of Order','Indicates the Date an item was ordered.','Y','N','N','Y','N','N','N',0,'Auftragsdatum',20,0,0,TO_TIMESTAMP('2020-08-31 16:53:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:53:50.499Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57715,0,53271,544052,571055,'F',TO_TIMESTAMP('2020-08-31 16:53:45','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',30,0,0,TO_TIMESTAMP('2020-08-31 16:53:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:55:06.062Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=571055
;

-- 2020-08-31T14:55:06.072Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=571054
;

-- 2020-08-31T14:55:06.084Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=571053
;

-- 2020-08-31T14:55:06.091Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=544052
;

-- 2020-08-31T14:55:19.258Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57711,0,53271,540508,571056,'F',TO_TIMESTAMP('2020-08-31 16:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','N','N',0,'Auftrag',60,0,0,TO_TIMESTAMP('2020-08-31 16:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:55:25.227Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57712,0,53271,540508,571057,'F',TO_TIMESTAMP('2020-08-31 16:55:25','YYYY-MM-DD HH24:MI:SS'),100,'Date of Order','Indicates the Date an item was ordered.','Y','N','N','Y','N','N','N',0,'Auftragsdatum',70,0,0,TO_TIMESTAMP('2020-08-31 16:55:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T14:55:31.334Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57715,0,53271,540508,571058,'F',TO_TIMESTAMP('2020-08-31 16:55:31','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',80,0,0,TO_TIMESTAMP('2020-08-31 16:55:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-31T15:08:44.179Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2020-08-31 17:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57711
;

-- 2020-08-31T15:09:00.853Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2020-08-31 17:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57712
;

-- 2020-08-31T15:20:11.426Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=573591, Description='Enter Customer Returns', Help='The Customer Return Tab allows you to generate, maintain, enter and process Returns from a Customer. ', Name='Kundenrücklieferung',Updated=TO_TIMESTAMP('2020-08-31 17:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=53097
;

-- 2020-08-31T15:20:11.465Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Enter Customer Returns', IsActive='Y', Name='Kundenrücklieferung',Updated=TO_TIMESTAMP('2020-08-31 17:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540841
;

-- 2020-08-31T15:20:11.469Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Enter Customer Returns', IsActive='Y', Name='Kundenrücklieferung',Updated=TO_TIMESTAMP('2020-08-31 17:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53243
;

-- 2020-08-31T15:20:11.505Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(573591)
;

-- 2020-08-31T15:20:11.516Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=53097
;

-- 2020-08-31T15:20:11.526Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(53097)
;

-- 2020-08-31T15:20:29.341Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2020-08-31 17:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57711
;

-- 2020-08-31T15:27:10.721Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-08-31 17:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57715
;

-- 2020-08-31T15:28:45.660Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2020-08-31 17:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57715
;

-- 2020-08-31T15:29:02.500Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-08-31 17:29:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57715
;

-- 2020-09-02T15:12:45.288Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540509, SeqNo=50,Updated=TO_TIMESTAMP('2020-09-02 17:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=571056
;

-- 2020-09-02T15:14:23.279Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540509, SeqNo=60,Updated=TO_TIMESTAMP('2020-09-02 17:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=571057
;

-- 2020-09-02T15:14:34.046Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540509, SeqNo=70,Updated=TO_TIMESTAMP('2020-09-02 17:14:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=571058
;

