-- Element: null
-- 2023-05-18T17:04:36.350831200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestellanforderung', PrintName='Bestellanforderung',Updated=TO_TIMESTAMP('2023-05-18 18:04:36.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574020 AND AD_Language='de_DE'
;

-- 2023-05-18T17:04:36.351900200Z
UPDATE AD_Element SET Name='Bestellanforderung', PrintName='Bestellanforderung' WHERE AD_Element_ID=574020
;

-- 2023-05-18T17:04:36.665202600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(574020,'de_DE') 
;

-- 2023-05-18T17:04:36.668503400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574020,'de_DE') 
;

-- Element: null
-- 2023-05-18T17:04:58.923695900Z
UPDATE AD_Element_Trl SET Name='Purchase Request', PrintName='Purchase Request',Updated=TO_TIMESTAMP('2023-05-18 18:04:58.923','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574020 AND AD_Language='en_US'
;

-- 2023-05-18T17:04:58.925813400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574020,'en_US') 
;

-- Element: null
-- 2023-05-18T17:05:44.399004600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestellanforderung', PrintName='Bestellanforderung',Updated=TO_TIMESTAMP('2023-05-18 18:05:44.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=575084 AND AD_Language='de_DE'
;

-- 2023-05-18T17:05:44.400039700Z
UPDATE AD_Element SET Name='Bestellanforderung', PrintName='Bestellanforderung' WHERE AD_Element_ID=575084
;

-- 2023-05-18T17:05:44.732886200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(575084,'de_DE') 
;

-- 2023-05-18T17:05:44.734456700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575084,'de_DE') 
;

-- Element: null
-- 2023-05-18T17:06:00.478061400Z
UPDATE AD_Element_Trl SET Name='Purchase Request', PrintName='Purchase Request',Updated=TO_TIMESTAMP('2023-05-18 18:06:00.477','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=575084 AND AD_Language='en_US'
;

-- 2023-05-18T17:06:00.480196Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575084,'en_US') 
;

-- Reordering children of `Purchase`
-- Node name: `Bestellanforderung`
-- 2023-05-18T17:07:53.804932400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=463 AND AD_Tree_ID=10
;

-- Node name: `Purchase Order (C_Order)`
-- 2023-05-18T17:07:53.810955700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- Node name: `Purchase Disposition (C_PurchaseCandidate)`
-- 2023-05-18T17:07:53.812013700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540979 AND AD_Tree_ID=10
;

-- Node name: `Procurement Planning (PMM_PurchaseCandidate)`
-- 2023-05-18T17:07:53.813055600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- Node name: `Material Tracking (M_Material_Tracking)`
-- 2023-05-18T17:07:53.814094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- Node name: `Product for Procurement Contracts (PMM_Product)`
-- 2023-05-18T17:07:53.814613400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- Node name: `Procurement Availability Trend (PMM_Week)`
-- 2023-05-18T17:07:53.815727900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- Node name: `Request for Proposal (C_RfQ)`
-- 2023-05-18T17:07:53.816761300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- Node name: `Request for Proposal Response`
-- 2023-05-18T17:07:53.817296100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- Node name: `Request for Proposal Responseline (C_RfQResponseLine)`
-- 2023-05-18T17:07:53.818365900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540880 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-05-18T17:07:53.819402300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-05-18T17:07:53.819919800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-05-18T17:07:53.820976900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- Tab: Bestellanforderung(322,D) -> Bedarf(641,D)
-- UI Section: main
-- 2023-05-18T18:08:36.856540Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,641,545582,TO_TIMESTAMP('2023-05-18 19:08:36.407','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-05-18 19:08:36.407','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-05-18T18:08:36.863848900Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545582 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Tab: Bestellanforderung(322,D) -> Bedarf(641,D)
-- UI Section: advanced edit
-- 2023-05-18T18:08:46.524659900Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,641,545583,TO_TIMESTAMP('2023-05-18 19:08:46.24','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-05-18 19:08:46.24','YYYY-MM-DD HH24:MI:SS.US'),100,'advanced edit')
;

-- 2023-05-18T18:08:46.526221100Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545583 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Bestellanforderung(322,D) -> Bedarf(641,D) -> main
-- UI Column: 10
-- 2023-05-18T18:08:59.426223900Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546824,545582,TO_TIMESTAMP('2023-05-18 19:08:59.176','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-05-18 19:08:59.176','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Bestellanforderung(322,D) -> Bedarf(641,D) -> main
-- UI Column: 20
-- 2023-05-18T18:09:00.948486900Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546825,545582,TO_TIMESTAMP('2023-05-18 19:09:00.777','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-05-18 19:09:00.777','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: main
-- 2023-05-18T18:09:34.340083700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546824,550695,TO_TIMESTAMP('2023-05-18 19:09:34.086','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-05-18 19:09:34.086','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: pricing
-- 2023-05-18T18:10:14.248765300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546824,550696,TO_TIMESTAMP('2023-05-18 19:10:14.014','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','pricing',20,TO_TIMESTAMP('2023-05-18 19:10:14.014','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: main
-- 2023-05-18T18:11:17.535289900Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546825,550697,TO_TIMESTAMP('2023-05-18 19:11:17.255','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2023-05-18 19:11:17.255','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: dates
-- 2023-05-18T18:11:24.137633300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546825,550698,TO_TIMESTAMP('2023-05-18 19:11:23.852','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','dates',20,TO_TIMESTAMP('2023-05-18 19:11:23.852','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: processed
-- 2023-05-18T18:12:20.340757100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546825,550699,TO_TIMESTAMP('2023-05-18 19:12:20.078','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','processed',30,TO_TIMESTAMP('2023-05-18 19:12:20.078','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: org
-- 2023-05-18T18:12:22.439075Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546825,550700,TO_TIMESTAMP('2023-05-18 19:12:22.271','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',40,TO_TIMESTAMP('2023-05-18 19:12:22.271','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Lieferkontakt
-- Column: M_Requisition.AD_User_ID
-- 2023-05-18T18:17:07.189251500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10028,0,641,550695,617439,'F',TO_TIMESTAMP('2023-05-18 19:17:06.911','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'Lieferkontakt',10,0,0,TO_TIMESTAMP('2023-05-18 19:17:06.911','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Lager
-- Column: M_Requisition.M_Warehouse_ID
-- 2023-05-18T18:18:05.480265400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10023,0,641,550695,617440,'F',TO_TIMESTAMP('2023-05-18 19:18:04.919','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',20,0,0,TO_TIMESTAMP('2023-05-18 19:18:04.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> pricing.Preisliste
-- Column: M_Requisition.M_PriceList_ID
-- 2023-05-18T18:19:14.355031200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10015,0,641,550696,617441,'F',TO_TIMESTAMP('2023-05-18 19:19:14.075','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnung der Preisliste','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','N','Y','N','N','N',0,'Preisliste',10,0,0,TO_TIMESTAMP('2023-05-18 19:19:14.075','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Priorität
-- Column: M_Requisition.PriorityRule
-- 2023-05-18T18:19:49.388040100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10020,0,641,550695,617442,'F',TO_TIMESTAMP('2023-05-18 19:19:49.095','YYYY-MM-DD HH24:MI:SS.US'),100,'Priority of a document','The Priority indicates the importance (high, medium, low) of this document','Y','N','N','Y','N','N','N',0,'Priorität',30,0,0,TO_TIMESTAMP('2023-05-18 19:19:49.095','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> pricing.Summe Zeilen
-- Column: M_Requisition.TotalLines
-- 2023-05-18T18:20:09.300367500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10033,0,641,550696,617443,'F',TO_TIMESTAMP('2023-05-18 19:20:08.99','YYYY-MM-DD HH24:MI:SS.US'),100,'Summe aller Zeilen zu diesem Beleg','Die Summe Zeilen zeigt die Summe aller Zeilen in Belegwährung an.','Y','N','N','Y','N','N','N',0,'Summe Zeilen',20,0,0,TO_TIMESTAMP('2023-05-18 19:20:08.99','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Beschreibung
-- Column: M_Requisition.Description
-- 2023-05-18T18:21:06.674508Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10032,0,641,550695,617444,'F',TO_TIMESTAMP('2023-05-18 19:21:06.389','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',40,0,0,TO_TIMESTAMP('2023-05-18 19:21:06.389','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> main.Belegart
-- Column: M_Requisition.C_DocType_ID
-- 2023-05-18T18:22:25.655018200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12679,0,641,550697,617445,'F',TO_TIMESTAMP('2023-05-18 19:22:25.397','YYYY-MM-DD HH24:MI:SS.US'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',10,0,0,TO_TIMESTAMP('2023-05-18 19:22:25.397','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> main.Nr.
-- Column: M_Requisition.DocumentNo
-- 2023-05-18T18:23:05.071807Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10019,0,641,550697,617446,'F',TO_TIMESTAMP('2023-05-18 19:23:04.758','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',20,0,0,TO_TIMESTAMP('2023-05-18 19:23:04.758','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> dates.Belegdatum
-- Column: M_Requisition.DateDoc
-- 2023-05-18T18:24:03.326514700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12488,0,641,550698,617447,'F',TO_TIMESTAMP('2023-05-18 19:24:03.009','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum des Belegs','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','N','Y','N','N','N',0,'Belegdatum',10,0,0,TO_TIMESTAMP('2023-05-18 19:24:03.009','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> dates.Zieldatum
-- Column: M_Requisition.DateRequired
-- 2023-05-18T18:29:10.303607Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10027,0,641,550698,617448,'F',TO_TIMESTAMP('2023-05-18 19:29:10.061','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zieldatum',20,0,0,TO_TIMESTAMP('2023-05-18 19:29:10.061','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> processed.Aktiv
-- Column: M_Requisition.IsActive
-- 2023-05-18T18:29:48.928156400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10021,0,641,550699,617449,'F',TO_TIMESTAMP('2023-05-18 19:29:48.698','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-05-18 19:29:48.698','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> processed.Freigegeben
-- Column: M_Requisition.IsApproved
-- 2023-05-18T18:30:03.246218400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10025,0,641,550699,617450,'F',TO_TIMESTAMP('2023-05-18 19:30:02.98','YYYY-MM-DD HH24:MI:SS.US'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','N','Y','N','N','N',0,'Freigegeben',20,0,0,TO_TIMESTAMP('2023-05-18 19:30:02.98','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> processed.Verarbeitet
-- Column: M_Requisition.Processed
-- 2023-05-18T18:30:19.292512600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10022,0,641,550699,617451,'F',TO_TIMESTAMP('2023-05-18 19:30:19.034','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',30,0,0,TO_TIMESTAMP('2023-05-18 19:30:19.034','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> org.Organisation
-- Column: M_Requisition.AD_Org_ID
-- 2023-05-18T18:30:41.562231500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10018,0,641,550700,617452,'F',TO_TIMESTAMP('2023-05-18 19:30:41.297','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-05-18 19:30:41.297','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> org.Mandant
-- Column: M_Requisition.AD_Client_ID
-- 2023-05-18T18:30:55.821678200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10024,0,641,550700,617453,'F',TO_TIMESTAMP('2023-05-18 19:30:55.553','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-05-18 19:30:55.553','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:33:21.562774700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582333,0,TO_TIMESTAMP('2023-05-18 19:33:21.302','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Bestellanforderungsnr.','Bestellanforderungsnr.',TO_TIMESTAMP('2023-05-18 19:33:21.302','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:33:21.564376400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582333 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T18:33:38.197184800Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Purchase requisition no.', PrintName='Purchase requisition no.',Updated=TO_TIMESTAMP('2023-05-18 19:33:38.197','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582333 AND AD_Language='en_US'
;

-- 2023-05-18T18:33:38.199790700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582333,'en_US') 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Bestellanforderungsnr.
-- Column: M_Requisition.DocumentNo
-- 2023-05-18T18:34:16.378193500Z
UPDATE AD_Field SET AD_Name_ID=582333, Description=NULL, Help=NULL, Name='Bestellanforderungsnr.',Updated=TO_TIMESTAMP('2023-05-18 19:34:16.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=10019
;

-- 2023-05-18T18:34:16.380884Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Bestellanforderungsnr.' WHERE AD_Field_ID=10019 AND AD_Language='de_DE'
;

-- 2023-05-18T18:34:16.383066400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582333) 
;

-- 2023-05-18T18:34:16.392717100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10019
;

-- 2023-05-18T18:34:16.394283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(10019)
;

-- Column: M_Requisition.Note
-- 2023-05-18T18:36:24.608664600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586660,1115,0,14,702,'Note',TO_TIMESTAMP('2023-05-18 19:36:24.351','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Optional weitere Information','D',0,2000,'Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Notiz',0,0,TO_TIMESTAMP('2023-05-18 19:36:24.351','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T18:36:24.610273400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586660 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T18:36:25.105278800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1115) 
;

-- 2023-05-18T18:36:28.142592600Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN Note VARCHAR(2000)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Notiz
-- Column: M_Requisition.Note
-- 2023-05-18T18:36:49.137192Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586660,715362,0,641,0,TO_TIMESTAMP('2023-05-18 19:36:48.87','YYYY-MM-DD HH24:MI:SS.US'),100,'Optional weitere Information',0,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',0,10010,0,1,1,TO_TIMESTAMP('2023-05-18 19:36:48.87','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:36:49.139389700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T18:36:49.140970Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2023-05-18T18:36:49.152265300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715362
;

-- 2023-05-18T18:36:49.154081800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715362)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: note
-- 2023-05-18T18:37:18.536659100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546824,550701,TO_TIMESTAMP('2023-05-18 19:37:18.277','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','note',30,TO_TIMESTAMP('2023-05-18 19:37:18.277','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> note.Notiz
-- Column: M_Requisition.Note
-- 2023-05-18T18:37:24.017018800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715362,0,641,550701,617454,'F',TO_TIMESTAMP('2023-05-18 19:37:23.751','YYYY-MM-DD HH24:MI:SS.US'),100,'Optional weitere Information','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','N','N','N',0,'Notiz',10,0,0,TO_TIMESTAMP('2023-05-18 19:37:23.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:54:26.105535500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582334,0,TO_TIMESTAMP('2023-05-18 19:54:25.829','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Antragsteller','Antragsteller',TO_TIMESTAMP('2023-05-18 19:54:25.829','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:54:26.107139100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582334 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T18:54:44.214949200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Requestor', PrintName='Requestor',Updated=TO_TIMESTAMP('2023-05-18 19:54:44.214','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582334 AND AD_Language='en_US'
;

-- 2023-05-18T18:54:44.217108800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582334,'en_US') 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Antragsteller
-- Column: M_Requisition.AD_User_ID
-- 2023-05-18T18:55:04.480739100Z
UPDATE AD_Field SET AD_Name_ID=582334, Description=NULL, Help=NULL, Name='Antragsteller',Updated=TO_TIMESTAMP('2023-05-18 19:55:04.48','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=10028
;

-- 2023-05-18T18:55:04.481848600Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Antragsteller' WHERE AD_Field_ID=10028 AND AD_Language='de_DE'
;

-- 2023-05-18T18:55:04.483452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582334) 
;

-- 2023-05-18T18:55:04.487166100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10028
;

-- 2023-05-18T18:55:04.488210800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(10028)
;

-- 2023-05-18T18:56:03.160240800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582335,0,'Author_ID',TO_TIMESTAMP('2023-05-18 19:56:02.891','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Autor','Autor',TO_TIMESTAMP('2023-05-18 19:56:02.891','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:56:03.162358900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582335 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Author_ID
-- 2023-05-18T18:56:22.550378400Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Author', PrintName='Author',Updated=TO_TIMESTAMP('2023-05-18 19:56:22.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582335 AND AD_Language='en_US'
;

-- 2023-05-18T18:56:22.553091Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582335,'en_US') 
;

-- Column: M_Requisition.Author_ID
-- 2023-05-18T18:57:04.991245100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586661,582335,0,30,110,702,'Author_ID',TO_TIMESTAMP('2023-05-18 19:57:04.719','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Autor',0,0,TO_TIMESTAMP('2023-05-18 19:57:04.719','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T18:57:04.993972200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586661 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T18:57:05.463128400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582335) 
;

-- 2023-05-18T18:57:07.200201200Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN Author_ID NUMERIC(10)')
;

-- 2023-05-18T18:57:07.279960Z
ALTER TABLE M_Requisition ADD CONSTRAINT Author_MRequisition FOREIGN KEY (Author_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Autor
-- Column: M_Requisition.Author_ID
-- 2023-05-18T18:57:28.819904600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586661,715363,0,641,0,TO_TIMESTAMP('2023-05-18 19:57:28.539','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Autor',0,10020,0,1,1,TO_TIMESTAMP('2023-05-18 19:57:28.539','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T18:57:28.822019600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T18:57:28.823069500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582335) 
;

-- 2023-05-18T18:57:28.826281500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715363
;

-- 2023-05-18T18:57:28.826811Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715363)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Autor
-- Column: M_Requisition.Author_ID
-- 2023-05-18T18:57:58.521661600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715363,0,641,550695,617455,'F',TO_TIMESTAMP('2023-05-18 19:57:58.274','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Autor',15,0,0,TO_TIMESTAMP('2023-05-18 19:57:58.274','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Requisition.AD_User_ID
-- 2023-05-18T18:58:49.773570900Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=110, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-05-18 19:58:49.773','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=11479
;

-- Column: M_Requisition.Author_ID
-- 2023-05-18T18:59:00.501728500Z
UPDATE AD_Column SET AD_Val_Rule_ID=164,Updated=TO_TIMESTAMP('2023-05-18 19:59:00.501','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586661
;

-- Column: M_Requisition.C_Activity_ID
-- 2023-05-18T19:00:25.288260300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586662,1005,0,30,540608,702,'C_Activity_ID',TO_TIMESTAMP('2023-05-18 20:00:25.003','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Kostenstelle','D',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2023-05-18 20:00:25.003','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T19:00:25.290352200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586662 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T19:00:25.728419700Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- 2023-05-18T19:00:27.821113900Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2023-05-18T19:00:27.899931200Z
ALTER TABLE M_Requisition ADD CONSTRAINT CActivity_MRequisition FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Kostenstelle
-- Column: M_Requisition.C_Activity_ID
-- 2023-05-18T19:01:12.036733300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586662,715364,0,641,0,TO_TIMESTAMP('2023-05-18 20:01:11.764','YYYY-MM-DD HH24:MI:SS.US'),100,'Kostenstelle',0,'D','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle',0,10030,0,1,1,TO_TIMESTAMP('2023-05-18 20:01:11.764','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:01:12.038272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T19:01:12.039850800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2023-05-18T19:01:12.059351600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715364
;

-- 2023-05-18T19:01:12.061603400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715364)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> pricing.Kostenstelle
-- Column: M_Requisition.C_Activity_ID
-- 2023-05-18T19:02:01.885795800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715364,0,641,550696,617456,'F',TO_TIMESTAMP('2023-05-18 20:02:01.635','YYYY-MM-DD HH24:MI:SS.US'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',15,0,0,TO_TIMESTAMP('2023-05-18 20:02:01.635','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:03:01.886085300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582336,0,TO_TIMESTAMP('2023-05-18 20:03:01.593','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Anfrage Details','Anfrage Details',TO_TIMESTAMP('2023-05-18 20:03:01.593','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:03:01.887659500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582336 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T19:03:17.081635700Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Request Details', PrintName='Request Details',Updated=TO_TIMESTAMP('2023-05-18 20:03:17.081','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582336 AND AD_Language='en_US'
;

-- 2023-05-18T19:03:17.083750Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582336,'en_US') 
;

-- Column: M_Requisition.Description
-- 2023-05-18T19:03:45.295022300Z
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-05-18 20:03:45.295','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=11484
;

-- 2023-05-18T19:03:47.253797300Z
INSERT INTO t_alter_column values('m_requisition','Description','VARCHAR(2000)',null,null)
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Anfrage Details
-- Column: M_Requisition.Description
-- 2023-05-18T19:04:12.084331600Z
UPDATE AD_Field SET AD_Name_ID=582336, Description=NULL, Help=NULL, Name='Anfrage Details',Updated=TO_TIMESTAMP('2023-05-18 20:04:12.084','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=10032
;

-- 2023-05-18T19:04:12.085895900Z
UPDATE AD_Field_Trl trl SET Name='Anfrage Details' WHERE AD_Field_ID=10032 AND AD_Language='de_DE'
;

-- 2023-05-18T19:04:12.086978700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582336) 
;

-- 2023-05-18T19:04:12.089596400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10032
;

-- 2023-05-18T19:04:12.090648700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(10032)
;

-- 2023-05-18T19:07:16.481484400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582337,0,'OrderNote',TO_TIMESTAMP('2023-05-18 20:07:16.17','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Anmerkung zur','Anmerkung zur',TO_TIMESTAMP('2023-05-18 20:07:16.17','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:07:16.797538100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582337 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: OrderNote
-- 2023-05-18T19:07:45.437425600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Order Note', PrintName='Order Note',Updated=TO_TIMESTAMP('2023-05-18 20:07:45.437','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582337 AND AD_Language='en_US'
;

-- 2023-05-18T19:07:45.439541800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582337,'en_US') 
;

-- Column: M_Requisition.OrderNote
-- 2023-05-18T19:08:04.795998700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586663,582337,0,14,702,'OrderNote',TO_TIMESTAMP('2023-05-18 20:08:04.439','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Anmerkung zur',0,0,TO_TIMESTAMP('2023-05-18 20:08:04.439','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T19:08:04.797614200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586663 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T19:08:05.242909700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582337) 
;

-- 2023-05-18T19:08:07.081200700Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN OrderNote VARCHAR(2000)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Anmerkung zur
-- Column: M_Requisition.OrderNote
-- 2023-05-18T19:08:30.535945100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586663,715365,0,641,0,TO_TIMESTAMP('2023-05-18 20:08:30.295','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Anmerkung zur',0,10040,0,1,1,TO_TIMESTAMP('2023-05-18 20:08:30.295','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:08:30.538097Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T19:08:30.539743900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582337) 
;

-- 2023-05-18T19:08:30.542396800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715365
;

-- 2023-05-18T19:08:30.543439300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715365)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> note.Anmerkung zur
-- Column: M_Requisition.OrderNote
-- 2023-05-18T19:08:45.037965600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715365,0,641,550701,617457,'F',TO_TIMESTAMP('2023-05-18 20:08:44.632','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Anmerkung zur',20,0,0,TO_TIMESTAMP('2023-05-18 20:08:44.632','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Requisition.PaymentRule
-- 2023-05-18T19:11:05.106919400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586664,1143,0,17,195,702,52033,'PaymentRule',TO_TIMESTAMP('2023-05-18 20:11:04.795','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Wie die Rechnung bezahlt wird','D',0,1,'Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsweise',0,0,TO_TIMESTAMP('2023-05-18 20:11:04.795','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T19:11:05.108471900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T19:11:05.534875700Z
/* DDL */  select update_Column_Translation_From_AD_Element(1143) 
;

-- 2023-05-18T19:11:07.008926300Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN PaymentRule CHAR(1)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Zahlungsweise
-- Column: M_Requisition.PaymentRule
-- 2023-05-18T19:14:35.160743300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586664,715366,0,641,0,TO_TIMESTAMP('2023-05-18 20:14:34.887','YYYY-MM-DD HH24:MI:SS.US'),100,'Wie die Rechnung bezahlt wird',0,'D','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsweise',0,10050,0,1,1,TO_TIMESTAMP('2023-05-18 20:14:34.887','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:14:35.162875500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T19:14:35.164429900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143) 
;

-- 2023-05-18T19:14:35.175279500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715366
;

-- 2023-05-18T19:14:35.176881200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715366)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> pricing.Summe Zeilen
-- Column: M_Requisition.TotalLines
-- 2023-05-18T19:15:06.044896600Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-05-18 20:15:06.044','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617443
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> pricing.Zahlungsweise
-- Column: M_Requisition.PaymentRule
-- 2023-05-18T19:15:17.497836300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715366,0,641,550696,617458,'F',TO_TIMESTAMP('2023-05-18 20:15:17.215','YYYY-MM-DD HH24:MI:SS.US'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','N','Y','N','N','N',0,'Zahlungsweise',30,0,0,TO_TIMESTAMP('2023-05-18 20:15:17.215','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Requisition.Warehouse_Location_ID
-- 2023-05-18T19:30:36.137064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586665,1875,0,30,541610,702,131,'Warehouse_Location_ID',TO_TIMESTAMP('2023-05-18 20:30:35.855','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Warehouse Location/Address','D',0,10,'Address of Warehouse','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Warehouse Address',0,0,TO_TIMESTAMP('2023-05-18 20:30:35.855','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T19:30:36.138666800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T19:30:36.632422Z
/* DDL */  select update_Column_Translation_From_AD_Element(1875) 
;

-- 2023-05-18T19:31:37.506320900Z
INSERT INTO t_alter_column values('m_warehouse','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Warehouse Address
-- Column: M_Requisition.Warehouse_Location_ID
-- 2023-05-18T19:32:42.316094400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586665,715367,0,641,0,TO_TIMESTAMP('2023-05-18 20:32:42.039','YYYY-MM-DD HH24:MI:SS.US'),100,'Warehouse Location/Address',0,'D','Address of Warehouse',0,'Y','Y','Y','N','N','N','N','N','Warehouse Address',0,10060,0,1,1,TO_TIMESTAMP('2023-05-18 20:32:42.039','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:32:42.317704500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T19:32:42.319280800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1875) 
;

-- 2023-05-18T19:32:42.321993800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715367
;

-- 2023-05-18T19:32:42.323047800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715367)
;

-- 2023-05-18T19:33:51.069298900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582338,0,TO_TIMESTAMP('2023-05-18 20:33:50.779','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Lieferadresse','Lieferadresse',TO_TIMESTAMP('2023-05-18 20:33:50.779','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:33:51.070866300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582338 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T19:34:06.490168600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery address', PrintName='Delivery address',Updated=TO_TIMESTAMP('2023-05-18 20:34:06.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582338 AND AD_Language='en_US'
;

-- 2023-05-18T19:34:06.492852700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582338,'en_US') 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Lieferadresse
-- Column: M_Requisition.Warehouse_Location_ID
-- 2023-05-18T19:34:20.494507Z
UPDATE AD_Field SET AD_Name_ID=582338, Description=NULL, Help=NULL, Name='Lieferadresse',Updated=TO_TIMESTAMP('2023-05-18 20:34:20.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715367
;

-- 2023-05-18T19:34:20.495575800Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Lieferadresse' WHERE AD_Field_ID=715367 AND AD_Language='de_DE'
;

-- 2023-05-18T19:34:20.497185500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582338) 
;

-- 2023-05-18T19:34:20.499936400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715367
;

-- 2023-05-18T19:34:20.500960800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715367)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Lieferadresse
-- Column: M_Requisition.Warehouse_Location_ID
-- 2023-05-18T19:34:50.216810200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715367,0,641,550695,617459,'F',TO_TIMESTAMP('2023-05-18 20:34:49.948','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferadresse',25,0,0,TO_TIMESTAMP('2023-05-18 20:34:49.948','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:35:21.654558500Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN Warehouse_Location_ID NUMERIC(10)')
;

-- 2023-05-18T19:35:21.745852100Z
ALTER TABLE M_Requisition ADD CONSTRAINT WarehouseLocation_MRequisition FOREIGN KEY (Warehouse_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Requisition.Warehouse_Location_ID
-- 2023-05-18T19:37:03.428922800Z
UPDATE AD_Column SET AD_Val_Rule_ID=540133,Updated=TO_TIMESTAMP('2023-05-18 20:37:03.428','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586665
;

-- 2023-05-18T19:39:00.343954100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582339,0,'Receiver_ID',TO_TIMESTAMP('2023-05-18 20:39:00.084','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Empfänger','Empfänger',TO_TIMESTAMP('2023-05-18 20:39:00.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:39:00.344989700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582339 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Receiver_ID
-- 2023-05-18T19:39:17.750443500Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receiver', PrintName='Receiver',Updated=TO_TIMESTAMP('2023-05-18 20:39:17.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582339 AND AD_Language='en_US'
;

-- 2023-05-18T19:39:17.753090100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582339,'en_US') 
;

-- Column: M_Requisition.Receiver_ID
-- 2023-05-18T19:39:42.450185600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586666,582339,0,30,110,702,164,'Receiver_ID',TO_TIMESTAMP('2023-05-18 20:39:42.171','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Empfänger',0,0,TO_TIMESTAMP('2023-05-18 20:39:42.171','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T19:39:42.452111900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586666 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T19:39:42.923600200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582339) 
;

-- 2023-05-18T19:39:45.062512700Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN Receiver_ID NUMERIC(10)')
;

-- 2023-05-18T19:39:45.136917Z
ALTER TABLE M_Requisition ADD CONSTRAINT Receiver_MRequisition FOREIGN KEY (Receiver_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Empfänger
-- Column: M_Requisition.Receiver_ID
-- 2023-05-18T19:40:20.956801500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586666,715368,0,641,0,TO_TIMESTAMP('2023-05-18 20:40:20.694','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Empfänger',0,10070,0,1,1,TO_TIMESTAMP('2023-05-18 20:40:20.694','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T19:40:20.957849200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T19:40:20.959989600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582339) 
;

-- 2023-05-18T19:40:20.962628700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715368
;

-- 2023-05-18T19:40:20.963682800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715368)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> main.Empfänger
-- Column: M_Requisition.Receiver_ID
-- 2023-05-18T19:40:55.096199400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715368,0,641,550695,617460,'F',TO_TIMESTAMP('2023-05-18 20:40:54.839','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Empfänger',18,0,0,TO_TIMESTAMP('2023-05-18 20:40:54.839','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:20:40.323784200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582340,0,'IsQuotesExist',TO_TIMESTAMP('2023-05-18 21:20:39.857','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Lieferantenangebote vorhanden?','Lieferantenangebote vorhanden?',TO_TIMESTAMP('2023-05-18 21:20:39.857','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:20:40.325379700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582340 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsQuotesExist
-- 2023-05-18T20:20:53.075922Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Supplier quotes existing?', PrintName='Supplier quotes existing?',Updated=TO_TIMESTAMP('2023-05-18 21:20:53.075','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582340 AND AD_Language='en_US'
;

-- 2023-05-18T20:20:53.078659300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582340,'en_US') 
;

-- Column: M_Requisition.IsQuotesExist
-- 2023-05-18T20:21:06.743936200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586667,582340,0,20,702,'IsQuotesExist',TO_TIMESTAMP('2023-05-18 21:21:06.36','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferantenangebote vorhanden?',0,0,TO_TIMESTAMP('2023-05-18 21:21:06.36','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:21:06.746026900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586667 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:21:07.204316800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582340) 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Lieferantenangebote vorhanden?
-- Column: M_Requisition.IsQuotesExist
-- 2023-05-18T20:21:38.825185300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586667,715369,0,641,0,TO_TIMESTAMP('2023-05-18 21:21:38.559','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lieferantenangebote vorhanden?',0,10080,0,1,1,TO_TIMESTAMP('2023-05-18 21:21:38.559','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:21:38.827356900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:21:38.828381200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582340) 
;

-- 2023-05-18T20:21:38.831131400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715369
;

-- 2023-05-18T20:21:38.832191600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715369)
;

-- 2023-05-18T20:21:41.986394600Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN IsQuotesExist CHAR(1) DEFAULT ''N'' CHECK (IsQuotesExist IN (''Y'',''N'')) NOT NULL')
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: note
-- 2023-05-18T20:22:04.849042100Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2023-05-18 21:22:04.848','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550701
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: pricing
-- 2023-05-18T20:22:10.979752700Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-05-18 21:22:10.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550696
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: quotes
-- 2023-05-18T20:22:31.149686900Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546824,550702,TO_TIMESTAMP('2023-05-18 21:22:30.895','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','quotes',30,TO_TIMESTAMP('2023-05-18 21:22:30.895','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: quotes
-- 2023-05-18T20:22:43.963679800Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2023-05-18 21:22:43.963','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550702
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Lieferantenangebote vorhanden?
-- Column: M_Requisition.IsQuotesExist
-- 2023-05-18T20:23:02.125059500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715369,0,641,550702,617461,'F',TO_TIMESTAMP('2023-05-18 21:23:01.831','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferantenangebote vorhanden?',10,0,0,TO_TIMESTAMP('2023-05-18 21:23:01.831','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:26:13.756741500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582341,0,'MissingQuoteNote',TO_TIMESTAMP('2023-05-18 21:26:13.427','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Fehlende Angebotsnotiz','Fehlende Angebotsnotiz',TO_TIMESTAMP('2023-05-18 21:26:13.427','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:26:14.082662700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582341 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MissingQuoteNote
-- 2023-05-18T20:26:36.985612100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Missing quote note', PrintName='Missing quote note',Updated=TO_TIMESTAMP('2023-05-18 21:26:36.985','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582341 AND AD_Language='en_US'
;

-- 2023-05-18T20:26:36.988301900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582341,'en_US') 
;

-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:26:54.924203700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586668,582341,0,14,702,'MissingQuoteNote',TO_TIMESTAMP('2023-05-18 21:26:54.664','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fehlende Angebotsnotiz',0,0,TO_TIMESTAMP('2023-05-18 21:26:54.664','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:26:54.925785Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:26:55.374721200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582341) 
;

-- 2023-05-18T20:26:57.071745700Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN MissingQuoteNote VARCHAR(2000)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:27:11.927742100Z
UPDATE AD_Field SET AD_Column_ID=586668, Description=NULL, Help=NULL, Name='Fehlende Angebotsnotiz',Updated=TO_TIMESTAMP('2023-05-18 21:27:11.927','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715369
;

-- 2023-05-18T20:27:11.929369500Z
UPDATE AD_Field_Trl trl SET Name='Fehlende Angebotsnotiz' WHERE AD_Field_ID=715369 AND AD_Language='de_DE'
;

-- 2023-05-18T20:27:11.930428400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582341) 
;

-- 2023-05-18T20:27:11.933634400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715369
;

-- 2023-05-18T20:27:11.934688500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715369)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:27:23.302331700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715369,0,641,550702,617462,'F',TO_TIMESTAMP('2023-05-18 21:27:23.063','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Fehlende Angebotsnotiz',20,0,0,TO_TIMESTAMP('2023-05-18 21:27:23.063','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Lieferantenangebote vorhanden?
-- Column: M_Requisition.IsQuotesExist
-- 2023-05-18T20:30:47.179434700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586667,715370,0,641,0,TO_TIMESTAMP('2023-05-18 21:30:46.621','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lieferantenangebote vorhanden?',0,10090,0,1,1,TO_TIMESTAMP('2023-05-18 21:30:46.621','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:30:47.183105500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:30:47.184697800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582340) 
;

-- 2023-05-18T20:30:47.187358400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715370
;

-- 2023-05-18T20:30:47.187879400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715370)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:31:23.617210400Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617462
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Lieferantenangebote vorhanden?
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:31:57.347815900Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617461
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Lieferantenangebote vorhanden?
-- Column: M_Requisition.IsQuotesExist
-- 2023-05-18T20:32:44.151126Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715370,0,641,550702,617463,'F',TO_TIMESTAMP('2023-05-18 21:32:43.804','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferantenangebote vorhanden?',10,0,0,TO_TIMESTAMP('2023-05-18 21:32:43.804','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:33:15.582767400Z
INSERT INTO t_alter_column values('m_requisition','MissingQuoteNote','VARCHAR(2000)',null,null)
;

-- 2023-05-18T20:33:51.857497300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715369
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:33:51.862239Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=715369
;

-- 2023-05-18T20:33:51.867490400Z
DELETE FROM AD_Field WHERE AD_Field_ID=715369
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:34:32.717693200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586668,715371,0,641,0,TO_TIMESTAMP('2023-05-18 21:34:32.437','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fehlende Angebotsnotiz',0,10100,0,1,1,TO_TIMESTAMP('2023-05-18 21:34:32.437','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:34:32.719366400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:34:32.720973900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582341) 
;

-- 2023-05-18T20:34:32.723663900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715371
;

-- 2023-05-18T20:34:32.724205600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715371)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Fehlende Angebotsnotiz
-- Column: M_Requisition.MissingQuoteNote
-- 2023-05-18T20:34:49.702107500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715371,0,641,550702,617464,'F',TO_TIMESTAMP('2023-05-18 21:34:49.28','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Fehlende Angebotsnotiz',20,0,0,TO_TIMESTAMP('2023-05-18 21:34:49.28','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:36:33.850414900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582342,0,'QuoteNumber',TO_TIMESTAMP('2023-05-18 21:36:33.581','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Angebotsnummer','Angebotsnummer',TO_TIMESTAMP('2023-05-18 21:36:33.581','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:36:34.149662500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582342 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QuoteNumber
-- 2023-05-18T20:36:45.741418100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Quote number', PrintName='Quote number',Updated=TO_TIMESTAMP('2023-05-18 21:36:45.741','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582342 AND AD_Language='en_US'
;

-- 2023-05-18T20:36:45.744029100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582342,'en_US') 
;

-- Column: M_Requisition.QuoteNumber
-- 2023-05-18T20:37:46.152641100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586669,582342,0,10,702,'QuoteNumber',TO_TIMESTAMP('2023-05-18 21:37:45.654','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Angebotsnummer',0,0,TO_TIMESTAMP('2023-05-18 21:37:45.654','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:37:46.154711100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586669 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:37:46.631613500Z
/* DDL */  select update_Column_Translation_From_AD_Element(582342) 
;

-- 2023-05-18T20:37:48.356441500Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN QuoteNumber VARCHAR(20)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Angebotsnummer
-- Column: M_Requisition.QuoteNumber
-- 2023-05-18T20:38:17.534248300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586669,715372,0,641,0,TO_TIMESTAMP('2023-05-18 21:38:17.227','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Angebotsnummer',0,10110,0,1,1,TO_TIMESTAMP('2023-05-18 21:38:17.227','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:38:17.535288100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715372 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:38:17.536893700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582342) 
;

-- 2023-05-18T20:38:17.539644600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715372
;

-- 2023-05-18T20:38:17.540678800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715372)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Angebotsnummer
-- Column: M_Requisition.QuoteNumber
-- 2023-05-18T20:38:46.529885900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715372,0,641,550702,617465,'F',TO_TIMESTAMP('2023-05-18 21:38:46.226','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Angebotsnummer',30,0,0,TO_TIMESTAMP('2023-05-18 21:38:46.226','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:39:49.679594800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582343,0,'QuoteDate',TO_TIMESTAMP('2023-05-18 21:39:49.38','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Datum des Angebots','Datum des Angebots',TO_TIMESTAMP('2023-05-18 21:39:49.38','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:39:49.681187400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582343 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QuoteDate
-- 2023-05-18T20:40:03.042527800Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Quote date', PrintName='Quote date',Updated=TO_TIMESTAMP('2023-05-18 21:40:03.042','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582343 AND AD_Language='en_US'
;

-- 2023-05-18T20:40:03.044688Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582343,'en_US') 
;

-- Column: M_Requisition.QuoteDate
-- 2023-05-18T20:40:13.364552900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586670,582343,0,15,702,'QuoteDate',TO_TIMESTAMP('2023-05-18 21:40:13.12','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Datum des Angebots',0,0,TO_TIMESTAMP('2023-05-18 21:40:13.12','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:40:13.366098600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586670 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:40:13.816991300Z
/* DDL */  select update_Column_Translation_From_AD_Element(582343) 
;

-- 2023-05-18T20:40:15.091901300Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN QuoteDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Datum des Angebots
-- Column: M_Requisition.QuoteDate
-- 2023-05-18T20:40:31.902170700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586670,715373,0,641,0,TO_TIMESTAMP('2023-05-18 21:40:31.634','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Datum des Angebots',0,10120,0,1,1,TO_TIMESTAMP('2023-05-18 21:40:31.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:40:31.903747500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:40:31.905313800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582343) 
;

-- 2023-05-18T20:40:31.910139200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715373
;

-- 2023-05-18T20:40:31.911341100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715373)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> quotes.Datum des Angebots
-- Column: M_Requisition.QuoteDate
-- 2023-05-18T20:40:42.650471Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715373,0,641,550702,617466,'F',TO_TIMESTAMP('2023-05-18 21:40:42.385','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Datum des Angebots',40,0,0,TO_TIMESTAMP('2023-05-18 21:40:42.385','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: pricing
-- 2023-05-18T20:42:20.914767300Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=546825, SeqNo=50,Updated=TO_TIMESTAMP('2023-05-18 21:42:20.914','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550696
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: note
-- 2023-05-18T20:42:32.822950400Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=546825, SeqNo=60,Updated=TO_TIMESTAMP('2023-05-18 21:42:32.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550701
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> processed.Aktiv
-- Column: M_Requisition.IsActive
-- 2023-05-18T20:43:26.353252800Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-05-18 21:43:26.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617449
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20
-- UI Element Group: org
-- 2023-05-18T20:43:49.314052200Z
UPDATE AD_UI_ElementGroup SET SeqNo=70,Updated=TO_TIMESTAMP('2023-05-18 21:43:49.314','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550700
;

-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:44:50.785660500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586671,208,0,30,702,'C_Project_ID',TO_TIMESTAMP('2023-05-18 21:44:50.546','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2023-05-18 21:44:50.546','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:44:50.787220600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586671 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:44:51.258162300Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- 2023-05-18T20:44:53.731120Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2023-05-18T20:44:53.804431400Z
ALTER TABLE M_Requisition ADD CONSTRAINT CProject_MRequisition FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- 2023-05-18T20:45:19.068759500Z
INSERT INTO t_alter_column values('m_requisition','C_Project_ID','NUMERIC(10)',null,null)
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Projekt
-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:45:35.135419400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586671,715374,0,641,0,TO_TIMESTAMP('2023-05-18 21:45:34.757','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project',0,'D','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Projekt',0,10130,0,1,1,TO_TIMESTAMP('2023-05-18 21:45:34.757','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:45:35.136999500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:45:35.138599400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2023-05-18T20:45:35.169547500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715374
;

-- 2023-05-18T20:45:35.171203400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715374)
;

-- 2023-05-18T20:45:51.276360500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582344,0,TO_TIMESTAMP('2023-05-18 21:45:50.709','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Kostenträger - Projekt','Kostenträger - Projekt',TO_TIMESTAMP('2023-05-18 21:45:50.709','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:45:51.277391400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582344 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T20:46:38.322737Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cost Unit - Project', PrintName='Cost Unit - Project',Updated=TO_TIMESTAMP('2023-05-18 21:46:38.322','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582344 AND AD_Language='en_US'
;

-- 2023-05-18T20:46:38.324830400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582344,'en_US') 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Kostenträger - Projekt
-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:46:50.327953500Z
UPDATE AD_Field SET AD_Name_ID=582344, Description=NULL, Help=NULL, Name='Kostenträger - Projekt',Updated=TO_TIMESTAMP('2023-05-18 21:46:50.327','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715374
;

-- 2023-05-18T20:46:50.329014400Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Kostenträger - Projekt' WHERE AD_Field_ID=715374 AND AD_Language='de_DE'
;

-- 2023-05-18T20:46:50.330627600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582344) 
;

-- 2023-05-18T20:46:50.333287500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715374
;

-- 2023-05-18T20:46:50.334334600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715374)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10
-- UI Element Group: cost
-- 2023-05-18T20:48:40.099504300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546824,550703,TO_TIMESTAMP('2023-05-18 21:48:39.829','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','cost',30,TO_TIMESTAMP('2023-05-18 21:48:39.829','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> cost.Kostenträger - Projekt
-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:48:55.834100400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715374,0,641,550703,617467,'F',TO_TIMESTAMP('2023-05-18 21:48:55.56','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Kostenträger - Projekt',10,0,0,TO_TIMESTAMP('2023-05-18 21:48:55.56','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Requisition.C_ProjectType_ID
-- 2023-05-18T20:49:36.928594700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586672,2033,0,19,702,'C_ProjectType_ID',TO_TIMESTAMP('2023-05-18 21:49:36.636','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Type of the project','D',0,10,'Type of the project with optional phases of the project with standard performance information','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projektart',0,0,TO_TIMESTAMP('2023-05-18 21:49:36.636','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:49:36.930155Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586672 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:49:37.374580500Z
/* DDL */  select update_Column_Translation_From_AD_Element(2033) 
;

-- 2023-05-18T20:49:41.494074800Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN C_ProjectType_ID NUMERIC(10)')
;

-- 2023-05-18T20:49:41.571810600Z
ALTER TABLE M_Requisition ADD CONSTRAINT CProjectType_MRequisition FOREIGN KEY (C_ProjectType_ID) REFERENCES public.C_ProjectType DEFERRABLE INITIALLY DEFERRED
;

-- 2023-05-18T20:50:16.929543300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582345,0,TO_TIMESTAMP('2023-05-18 21:50:16.637','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Kostenträger - Projekttyp','Kostenträger - Projekttyp',TO_TIMESTAMP('2023-05-18 21:50:16.637','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:50:16.930586400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582345 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T20:50:28.238993600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cost Unit - Project type', PrintName='Cost Unit - Project type',Updated=TO_TIMESTAMP('2023-05-18 21:50:28.238','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582345 AND AD_Language='en_US'
;

-- 2023-05-18T20:50:28.241135Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582345,'en_US') 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Projektart
-- Column: M_Requisition.C_ProjectType_ID
-- 2023-05-18T20:50:45.651818300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586672,715375,0,641,0,TO_TIMESTAMP('2023-05-18 21:50:45.393','YYYY-MM-DD HH24:MI:SS.US'),100,'Type of the project',0,'D','Type of the project with optional phases of the project with standard performance information',0,'Y','Y','Y','N','N','N','N','N','Projektart',0,10140,0,1,1,TO_TIMESTAMP('2023-05-18 21:50:45.393','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:50:45.654022900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715375 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:50:45.655625400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2033) 
;

-- 2023-05-18T20:50:45.659942600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715375
;

-- 2023-05-18T20:50:45.660462200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715375)
;

-- 2023-05-18T20:50:54.974348900Z
INSERT INTO t_alter_column values('m_requisition','C_ProjectType_ID','NUMERIC(10)',null,null)
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Kostenträger - Projekttyp
-- Column: M_Requisition.C_ProjectType_ID
-- 2023-05-18T20:51:04.002828600Z
UPDATE AD_Field SET AD_Name_ID=582345, Description=NULL, Help=NULL, Name='Kostenträger - Projekttyp',Updated=TO_TIMESTAMP('2023-05-18 21:51:04.002','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715375
;

-- 2023-05-18T20:51:04.003897700Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Kostenträger - Projekttyp' WHERE AD_Field_ID=715375 AND AD_Language='de_DE'
;

-- 2023-05-18T20:51:04.005463400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582345) 
;

-- 2023-05-18T20:51:04.008081400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715375
;

-- 2023-05-18T20:51:04.008600700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715375)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> cost.Kostenträger - Projekttyp
-- Column: M_Requisition.C_ProjectType_ID
-- 2023-05-18T20:51:15.691877500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715375,0,641,550703,617468,'F',TO_TIMESTAMP('2023-05-18 21:51:15.428','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Kostenträger - Projekttyp',20,0,0,TO_TIMESTAMP('2023-05-18 21:51:15.428','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:53:14.040678700Z
UPDATE AD_Column SET AD_Reference_Value_ID=541136, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-05-18 21:53:14.04','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586671
;

-- 2023-05-18T20:53:43.324202300Z
INSERT INTO t_alter_column values('m_requisition','C_Project_ID','NUMERIC(10)',null,null)
;

-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:53:50.403537900Z
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-05-18 21:53:50.403','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586671
;

-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:55:37.157604700Z
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-05-18 21:55:37.157','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586671
;

-- Column: M_Requisition.C_Project_ID
-- 2023-05-18T20:55:45.700295100Z
UPDATE AD_Column SET AD_Reference_Value_ID=541652,Updated=TO_TIMESTAMP('2023-05-18 21:55:45.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586671
;

-- 2023-05-18T20:57:55.302970600Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582346,0,'IsBudgetPlanned',TO_TIMESTAMP('2023-05-18 21:57:55.025','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Geplant in Kostenstelle Budget','Geplant in Kostenstelle Budget',TO_TIMESTAMP('2023-05-18 21:57:55.025','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:57:55.305144500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582346 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Requisition.IsBudgetPlanned
-- 2023-05-18T20:58:04.534032Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586673,582346,0,20,702,'IsBudgetPlanned',TO_TIMESTAMP('2023-05-18 21:58:04.261','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Geplant in Kostenstelle Budget',0,0,TO_TIMESTAMP('2023-05-18 21:58:04.261','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T20:58:04.536143700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586673 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T20:58:05.000325100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582346) 
;

-- 2023-05-18T20:58:06.354350400Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN IsBudgetPlanned CHAR(1) DEFAULT ''N'' CHECK (IsBudgetPlanned IN (''Y'',''N'')) NOT NULL')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Geplant in Kostenstelle Budget
-- Column: M_Requisition.IsBudgetPlanned
-- 2023-05-18T20:58:27.283493300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586673,715376,0,641,0,TO_TIMESTAMP('2023-05-18 21:58:27.009','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Geplant in Kostenstelle Budget',0,10150,0,1,1,TO_TIMESTAMP('2023-05-18 21:58:27.009','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:58:27.285152100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T20:58:27.286829200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582346) 
;

-- 2023-05-18T20:58:27.289664500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715376
;

-- 2023-05-18T20:58:27.291262900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715376)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> cost.Geplant in Kostenstelle Budget
-- Column: M_Requisition.IsBudgetPlanned
-- 2023-05-18T20:58:41.289237100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715376,0,641,550703,617469,'F',TO_TIMESTAMP('2023-05-18 21:58:41.025','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Geplant in Kostenstelle Budget',30,0,0,TO_TIMESTAMP('2023-05-18 21:58:41.025','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:59:39.838560100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582347,0,'MissingBudgetNote',TO_TIMESTAMP('2023-05-18 21:59:39.585','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Fehlender Budgetvermerk','Fehlender Budgetvermerk',TO_TIMESTAMP('2023-05-18 21:59:39.585','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T20:59:39.840752500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582347 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MissingBudgetNote
-- 2023-05-18T20:59:52.678233300Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Missing Budget Note', PrintName='Missing Budget Note',Updated=TO_TIMESTAMP('2023-05-18 21:59:52.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582347 AND AD_Language='en_US'
;

-- 2023-05-18T20:59:52.680306200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582347,'en_US') 
;

-- Column: M_Requisition.MissingBudgetNote
-- 2023-05-18T21:00:09.793525800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586674,582347,0,14,702,'MissingBudgetNote',TO_TIMESTAMP('2023-05-18 22:00:09.118','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fehlender Budgetvermerk',0,0,TO_TIMESTAMP('2023-05-18 22:00:09.118','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T21:00:09.795620600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586674 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T21:00:10.243993900Z
/* DDL */  select update_Column_Translation_From_AD_Element(582347) 
;

-- 2023-05-18T21:00:11.882649700Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN MissingBudgetNote VARCHAR(2000)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Fehlender Budgetvermerk
-- Column: M_Requisition.MissingBudgetNote
-- 2023-05-18T21:00:31.148938400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586674,715377,0,641,0,TO_TIMESTAMP('2023-05-18 22:00:30.512','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fehlender Budgetvermerk',0,10160,0,1,1,TO_TIMESTAMP('2023-05-18 22:00:30.512','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T21:00:31.150519400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715377 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T21:00:31.152144600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582347) 
;

-- 2023-05-18T21:00:31.154813500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715377
;

-- 2023-05-18T21:00:31.156070400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715377)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 10 -> cost.Fehlender Budgetvermerk
-- Column: M_Requisition.MissingBudgetNote
-- 2023-05-18T21:00:49.538852400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715377,0,641,550703,617470,'F',TO_TIMESTAMP('2023-05-18 22:00:49.155','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Fehlender Budgetvermerk',40,0,0,TO_TIMESTAMP('2023-05-18 22:00:49.155','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T21:02:34.425958200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582348,0,TO_TIMESTAMP('2023-05-18 22:02:34.042','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Bestellt','Bestellt',TO_TIMESTAMP('2023-05-18 22:02:34.042','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T21:02:34.427525400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582348 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-05-18T21:02:46.265096200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ordered', PrintName='Ordered',Updated=TO_TIMESTAMP('2023-05-18 22:02:46.265','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582348 AND AD_Language='en_US'
;

-- 2023-05-18T21:02:46.267225700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582348,'en_US') 
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Bestellt
-- Column: M_Requisition.Processed
-- 2023-05-18T21:03:11.663023500Z
UPDATE AD_Field SET AD_Name_ID=582348, Description=NULL, Help=NULL, Name='Bestellt',Updated=TO_TIMESTAMP('2023-05-18 22:03:11.663','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=10022
;

-- 2023-05-18T21:03:11.664114700Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Bestellt' WHERE AD_Field_ID=10022 AND AD_Language='de_DE'
;

-- 2023-05-18T21:03:11.665704700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582348) 
;

-- 2023-05-18T21:03:11.668936900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10022
;

-- 2023-05-18T21:03:11.669979900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(10022)
;

-- 2023-05-18T21:03:51.534192100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582349,0,'PurchaseOrderNo',TO_TIMESTAMP('2023-05-18 22:03:51.293','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Bestellungsnummer','Bestellungsnummer',TO_TIMESTAMP('2023-05-18 22:03:51.293','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T21:03:51.535248700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582349 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PurchaseOrderNo
-- 2023-05-18T21:04:04.604770100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Purchase Order No', PrintName='Purchase Order No',Updated=TO_TIMESTAMP('2023-05-18 22:04:04.604','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582349 AND AD_Language='en_US'
;

-- 2023-05-18T21:04:04.606870400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582349,'en_US') 
;

-- Column: M_Requisition.PurchaseOrderNo
-- 2023-05-18T21:04:23.061116600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586675,582349,0,10,702,'PurchaseOrderNo',TO_TIMESTAMP('2023-05-18 22:04:22.247','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bestellungsnummer',0,0,TO_TIMESTAMP('2023-05-18 22:04:22.247','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-18T21:04:23.062706200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586675 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-18T21:04:23.504999500Z
/* DDL */  select update_Column_Translation_From_AD_Element(582349) 
;

-- 2023-05-18T21:04:28.150006400Z
/* DDL */ SELECT public.db_alter_table('M_Requisition','ALTER TABLE public.M_Requisition ADD COLUMN PurchaseOrderNo VARCHAR(20)')
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Bestellungsnummer
-- Column: M_Requisition.PurchaseOrderNo
-- 2023-05-18T21:04:56.738134800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586675,715378,0,641,0,TO_TIMESTAMP('2023-05-18 22:04:56.414','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Bestellungsnummer',0,10170,0,1,1,TO_TIMESTAMP('2023-05-18 22:04:56.414','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-18T21:04:56.739745100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-18T21:04:56.741367500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582349) 
;

-- 2023-05-18T21:04:56.743624900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715378
;

-- 2023-05-18T21:04:56.744689400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715378)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> processed.Bestellungsnummer
-- Column: M_Requisition.PurchaseOrderNo
-- 2023-05-18T21:05:25.623019100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715378,0,641,550699,617471,'F',TO_TIMESTAMP('2023-05-18 22:05:25.355','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Bestellungsnummer',40,0,0,TO_TIMESTAMP('2023-05-18 22:05:25.355','YYYY-MM-DD HH24:MI:SS.US'),100)
;

