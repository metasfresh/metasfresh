-- Tab: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct)
-- UI Section: main
-- 2022-12-17T13:54:29.893Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546731,545359,TO_TIMESTAMP('2022-12-17 15:54:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-17 15:54:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-12-17T13:54:29.900Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545359 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main
-- UI Column: 10
-- 2022-12-17T13:54:33.537Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546537,545359,TO_TIMESTAMP('2022-12-17 15:54:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-17 15:54:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main
-- UI Column: 20
-- 2022-12-17T13:55:11.409Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546538,545359,TO_TIMESTAMP('2022-12-17 15:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-12-17 15:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10
-- UI Element Group: seqno&description
-- 2022-12-17T13:55:23.758Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546537,550191,TO_TIMESTAMP('2022-12-17 15:55:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','seqno&description',10,TO_TIMESTAMP('2022-12-17 15:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-17T13:55:35.395Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710046,0,546731,550191,614553,'F',TO_TIMESTAMP('2022-12-17 15:55:35','YYYY-MM-DD HH24:MI:SS'),100,'','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','SeqNo.',10,0,0,TO_TIMESTAMP('2022-12-17 15:55:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.Description
-- Column: SAP_GLJournalLine.Description
-- 2022-12-17T13:55:42.853Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710040,0,546731,550191,614554,'F',TO_TIMESTAMP('2022-12-17 15:55:42','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Description',20,0,0,TO_TIMESTAMP('2022-12-17 15:55:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10
-- UI Element Group: account&amounts
-- 2022-12-17T13:55:52.483Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546537,550192,TO_TIMESTAMP('2022-12-17 15:55:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','account&amounts',20,TO_TIMESTAMP('2022-12-17 15:55:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-17T13:56:17.579Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710036,0,546731,550192,614555,'F',TO_TIMESTAMP('2022-12-17 15:56:17','YYYY-MM-DD HH24:MI:SS'),100,'Valid Account Combination','The Combination identifies a valid combination of element which represent a GL account.','Y','N','Y','N','N','Combination',10,0,0,TO_TIMESTAMP('2022-12-17 15:56:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-17T13:56:26.001Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710037,0,546731,550192,614556,'F',TO_TIMESTAMP('2022-12-17 15:56:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','DR/CR',20,0,0,TO_TIMESTAMP('2022-12-17 15:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Amount
-- Column: SAP_GLJournalLine.Amount
-- 2022-12-17T13:56:33.539Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710038,0,546731,550192,614557,'F',TO_TIMESTAMP('2022-12-17 15:56:33','YYYY-MM-DD HH24:MI:SS'),100,'Amount in a defined currency','The Amount indicates the amount for this document line.','Y','N','Y','N','N','Amount',30,0,0,TO_TIMESTAMP('2022-12-17 15:56:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-17T13:56:40.022Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710039,0,546731,550192,614558,'F',TO_TIMESTAMP('2022-12-17 15:56:39','YYYY-MM-DD HH24:MI:SS'),100,'Amount Balance in Currency of Accounting Schema','Y','N','Y','N','N','Accounted Amount',40,0,0,TO_TIMESTAMP('2022-12-17 15:56:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-17T13:56:48.504Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710041,0,546731,550192,614559,'F',TO_TIMESTAMP('2022-12-17 15:56:48','YYYY-MM-DD HH24:MI:SS'),100,'Tax identifier','The Tax indicates the type of tax used in document line.','Y','N','Y','N','N','Tax',50,0,0,TO_TIMESTAMP('2022-12-17 15:56:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20
-- UI Element Group: dimension
-- 2022-12-17T13:57:00.519Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546538,550193,TO_TIMESTAMP('2022-12-17 15:57:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','dimension',10,TO_TIMESTAMP('2022-12-17 15:57:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2022-12-17T13:57:23.173Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710042,0,546731,550193,614560,'F',TO_TIMESTAMP('2022-12-17 15:57:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Section Code',10,0,0,TO_TIMESTAMP('2022-12-17 15:57:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-17T13:57:31.599Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710043,0,546731,550193,614561,'F',TO_TIMESTAMP('2022-12-17 15:57:31','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item','Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','Product',20,0,0,TO_TIMESTAMP('2022-12-17 15:57:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_Order_ID
-- 2022-12-17T13:57:42.275Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710045,0,546731,550193,614562,'F',TO_TIMESTAMP('2022-12-17 15:57:42','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Sales order',30,0,0,TO_TIMESTAMP('2022-12-17 15:57:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2022-12-17T13:57:48.695Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710044,0,546731,550193,614563,'F',TO_TIMESTAMP('2022-12-17 15:57:48','YYYY-MM-DD HH24:MI:SS'),100,'Business Activity','Activities indicate tasks that are performed and used to utilize Activity based Costing','Y','N','Y','N','N','Activity',40,0,0,TO_TIMESTAMP('2022-12-17 15:57:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20
-- UI Element Group: org&client
-- 2022-12-17T13:57:57.275Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546538,550194,TO_TIMESTAMP('2022-12-17 15:57:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',20,TO_TIMESTAMP('2022-12-17 15:57:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2022-12-17T13:58:05.356Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710032,0,546731,550194,614564,'F',TO_TIMESTAMP('2022-12-17 15:58:05','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2022-12-17 15:58:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> org&client.Client
-- Column: SAP_GLJournalLine.AD_Client_ID
-- 2022-12-17T13:58:11.680Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710031,0,546731,550194,614565,'F',TO_TIMESTAMP('2022-12-17 15:58:11','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','N','N','Client',20,0,0,TO_TIMESTAMP('2022-12-17 15:58:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-17T13:59:09.729Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614553
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-17T13:59:09.735Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614556
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-17T13:59:09.741Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614555
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Amount
-- Column: SAP_GLJournalLine.Amount
-- 2022-12-17T13:59:09.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614557
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2022-12-17T13:59:09.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614558
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-17T13:59:09.759Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614559
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.Description
-- Column: SAP_GLJournalLine.Description
-- 2022-12-17T13:59:09.765Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614554
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2022-12-17T13:59:09.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614560
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-17T13:59:09.775Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614561
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_Order_ID
-- 2022-12-17T13:59:09.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614562
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2022-12-17T13:59:09.784Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614563
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2022-12-17T13:59:09.789Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-12-17 15:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614564
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.SeqNo.
-- Column: SAP_GLJournalLine.Line
-- 2022-12-17T13:59:34.579Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-12-17 15:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614553
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-17T13:59:57.223Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-12-17 15:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614555
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Parent
-- Column: SAP_GLJournalLine.Parent_ID
-- 2022-12-17T14:01:02.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585416,710048,0,546731,TO_TIMESTAMP('2022-12-17 16:01:02','YYYY-MM-DD HH24:MI:SS'),100,'Parent of Entity',10,'de.metas.acct','The Parent indicates the value used to represent the next level in a hierarchy or report to level for a record','Y','N','N','N','N','N','N','N','Parent',TO_TIMESTAMP('2022-12-17 16:01:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-17T14:01:02.366Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-17T14:01:02.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(496) 
;

-- 2022-12-17T14:01:02.392Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710048
;

-- 2022-12-17T14:01:02.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710048)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10
-- UI Element Group: tax
-- 2022-12-17T14:01:17.047Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546537,550195,TO_TIMESTAMP('2022-12-17 16:01:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','tax',30,TO_TIMESTAMP('2022-12-17 16:01:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2022-12-17T14:01:26.057Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550195, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-12-17 16:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614559
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Parent
-- Column: SAP_GLJournalLine.Parent_ID
-- 2022-12-17T14:01:40.595Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710048,0,546731,550195,614566,'F',TO_TIMESTAMP('2022-12-17 16:01:40','YYYY-MM-DD HH24:MI:SS'),100,'Parent of Entity','The Parent indicates the value used to represent the next level in a hierarchy or report to level for a record','Y','N','Y','N','N','Parent',20,0,0,TO_TIMESTAMP('2022-12-17 16:01:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Parent
-- Column: SAP_GLJournalLine.Parent_ID
-- 2022-12-17T14:01:59.605Z
UPDATE AD_Field SET DisplayLogic='@Parent_ID@>0',Updated=TO_TIMESTAMP('2022-12-17 16:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710048
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2022-12-17T14:12:20.021Z
UPDATE AD_UI_Element SET WidgetSize='',Updated=TO_TIMESTAMP('2022-12-17 16:12:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614555
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2022-12-17T14:12:26.994Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-12-17 16:12:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614556
;

