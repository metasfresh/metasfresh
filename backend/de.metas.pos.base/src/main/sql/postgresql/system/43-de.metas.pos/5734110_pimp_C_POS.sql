-- Name: POS-Terminal
-- Action Type: W
-- Window: POS-Terminal(338,D)
-- 2024-09-20T20:14:28.292Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,573996,542173,0,338,TO_TIMESTAMP('2024-09-20 23:14:27','YYYY-MM-DD HH24:MI:SS'),100,'Verwalten des Verkaufsstellen-Terminalfensters','D','posTerminal','Y','N','N','N','N','POS-Terminal',TO_TIMESTAMP('2024-09-20 23:14:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T20:14:28.295Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542173 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-09-20T20:14:28.298Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542173, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542173)
;

-- 2024-09-20T20:14:28.327Z
/* DDL */  select update_menu_translation_from_ad_element(573996) 
;

-- Reordering children of `Sales Orders`
-- Node name: `Web POS`
-- 2024-09-20T20:14:36.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=52001 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (C_OLCand)`
-- 2024-09-20T20:14:36.681Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540241 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition - Fehler (C_OLCand)`
-- 2024-09-20T20:14:36.682Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540311 AND AD_Tree_ID=10
;

-- Node name: `Auftragskand. Prozessor (C_OLCandProcessor)`
-- 2024-09-20T20:14:36.683Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540205 AND AD_Tree_ID=10
;

-- Node name: `Market Place`
-- 2024-09-20T20:14:36.684Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=460 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2024-09-20T20:14:36.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=301 AND AD_Tree_ID=10
;

-- Node name: `Sales Order (C_Order)`
-- 2024-09-20T20:14:36.686Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=129 AND AD_Tree_ID=10
;

-- Node name: `Order Detail`
-- 2024-09-20T20:14:36.687Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=543 AND AD_Tree_ID=10
;

-- Node name: `Open Orders`
-- 2024-09-20T20:14:36.688Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=195 AND AD_Tree_ID=10
;

-- Node name: `Order Transactions`
-- 2024-09-20T20:14:36.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53223 AND AD_Tree_ID=10
;

-- Node name: `Generate PO from Sales Order (de.metas.order.process.C_Order_CreatePOFromSOs)`
-- 2024-09-20T20:14:36.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=335 AND AD_Tree_ID=10
;

-- Node name: `Reopen Order (org.compiere.process.OrderOpen)`
-- 2024-09-20T20:14:36.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=436 AND AD_Tree_ID=10
;

-- Node name: `Order Batch Process (org.compiere.process.OrderBatchProcess)`
-- 2024-09-20T20:14:36.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=507 AND AD_Tree_ID=10
;

-- Node name: `POS Key Layout (C_POSKeyLayout)`
-- 2024-09-20T20:14:36.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=492 AND AD_Tree_ID=10
;

-- Node name: `POS Key Generate (org.compiere.process.PosKeyGenerate)`
-- 2024-09-20T20:14:36.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53269 AND AD_Tree_ID=10
;

-- Node name: `POS Terminal (C_POS)`
-- 2024-09-20T20:14:36.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=491 AND AD_Tree_ID=10
;

-- Node name: `Bestellkontrolle zum Kunden Drucken (@PREFIX@de/metas/docs/sales/ordercheckup/legacy/report.jasper)`
-- 2024-09-20T20:14:36.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540583 AND AD_Tree_ID=10
;

-- Node name: `POS-Terminal`
-- 2024-09-20T20:14:36.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542173 AND AD_Tree_ID=10
;

-- Reordering children of `Point of Sale (POS)`
-- Node name: `POS Order (C_POS_Order)`
-- 2024-09-20T20:14:52.227Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542172 AND AD_Tree_ID=10
;

-- Node name: `POS-Terminal`
-- 2024-09-20T20:14:52.229Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542173 AND AD_Tree_ID=10
;

-- Reordering children of `Point of Sale (POS)`
-- Node name: `POS-Terminal`
-- 2024-09-20T20:14:55.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542173 AND AD_Tree_ID=10
;

-- Node name: `POS Order (C_POS_Order)`
-- 2024-09-20T20:14:55.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542172 AND AD_Tree_ID=10
;

-- Column: C_POS.SalesRep_ID
-- Column: C_POS.SalesRep_ID
-- 2024-09-20T20:17:31.117Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-09-20 23:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12748
;

-- 2024-09-20T20:17:31.871Z
INSERT INTO t_alter_column values('c_pos','SalesRep_ID','NUMERIC(10)',null,null)
;

-- 2024-09-20T20:17:31.876Z
INSERT INTO t_alter_column values('c_pos','SalesRep_ID',null,'NULL',null)
;

-- Tab: POS-Terminal(338,D) -> POS-Terminal(676,D)
-- UI Section: main
-- 2024-09-20T20:18:33.938Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,676,546180,TO_TIMESTAMP('2024-09-20 23:18:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 23:18:33','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-09-20T20:18:33.941Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546180 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main
-- UI Column: 10
-- 2024-09-20T20:18:34.101Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547555,546180,TO_TIMESTAMP('2024-09-20 23:18:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 23:18:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main
-- UI Column: 20
-- 2024-09-20T20:18:34.215Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547556,546180,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10
-- UI Element Group: default
-- 2024-09-20T20:18:34.356Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547555,551965,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Mandant
-- Column: C_POS.AD_Client_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Mandant
-- Column: C_POS.AD_Client_ID
-- 2024-09-20T20:18:34.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10799,0,676,551965,625770,'F',TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Organisation
-- Column: C_POS.AD_Org_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Organisation
-- Column: C_POS.AD_Org_ID
-- 2024-09-20T20:18:34.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10796,0,676,551965,625771,'F',TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Organisation',20,20,0,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Name
-- Column: C_POS.Name
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Name
-- Column: C_POS.Name
-- 2024-09-20T20:18:34.793Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10801,0,676,551965,625772,'F',TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Beschreibung
-- Column: C_POS.Description
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Beschreibung
-- Column: C_POS.Description
-- 2024-09-20T20:18:34.906Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10795,0,676,551965,625773,'F',TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,40,0,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Kommentar/Hilfe
-- Column: C_POS.Help
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Kommentar/Hilfe
-- Column: C_POS.Help
-- 2024-09-20T20:18:35.025Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10805,0,676,551965,625774,'F',TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','Y','N','Kommentar/Hilfe',50,50,0,TO_TIMESTAMP('2024-09-20 23:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Aktiv
-- Column: C_POS.IsActive
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Aktiv
-- Column: C_POS.IsActive
-- 2024-09-20T20:18:35.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10807,0,676,551965,625775,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Kundenbetreuer
-- Column: C_POS.SalesRep_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Kundenbetreuer
-- Column: C_POS.SalesRep_ID
-- 2024-09-20T20:18:35.253Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10800,0,676,551965,625776,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Kundenbetreuer',70,70,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Kassenbuch
-- Column: C_POS.C_CashBook_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Kassenbuch
-- Column: C_POS.C_CashBook_ID
-- 2024-09-20T20:18:35.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10802,0,676,551965,625777,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Kassenbuch für Bargeldtransaktionen','The Cash Book identifies a unique cash book.  The cash book is used to record cash transactions.','Y','N','Y','Y','N','Kassenbuch',80,80,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Preisliste
-- Column: C_POS.M_PriceList_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Preisliste
-- Column: C_POS.M_PriceList_ID
-- 2024-09-20T20:18:35.465Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10806,0,676,551965,625778,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','Y','N','Preisliste',90,90,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Modify Price
-- Column: C_POS.IsModifyPrice
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Modify Price
-- Column: C_POS.IsModifyPrice
-- 2024-09-20T20:18:35.577Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10809,0,676,551965,625779,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Allow modifying the price','Allow modifying the price for products with a non zero price','Y','N','Y','Y','N','Modify Price',100,100,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Lager
-- Column: C_POS.M_Warehouse_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Lager
-- Column: C_POS.M_Warehouse_ID
-- 2024-09-20T20:18:35.692Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10804,0,676,551965,625780,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager',110,110,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Bankverbindung
-- Column: C_POS.C_BP_BankAccount_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Bankverbindung
-- Column: C_POS.C_BP_BankAccount_ID
-- 2024-09-20T20:18:35.796Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,54330,0,676,551965,625781,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','Y','Y','N','Bankverbindung',120,120,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Belegart
-- Column: C_POS.C_DocType_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Belegart
-- Column: C_POS.C_DocType_ID
-- 2024-09-20T20:18:35.909Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10811,0,676,551965,625782,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','Y','N','Belegart',130,130,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Vorlage Geschäftspartner
-- Column: C_POS.C_BPartnerCashTrx_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Vorlage Geschäftspartner
-- Column: C_POS.C_BPartnerCashTrx_ID
-- 2024-09-20T20:18:36.018Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10810,0,676,551965,625783,'F',TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnervorlage, die zum schnellen Anlegen neuer Geschäftspartner verwendet wird','Wenn Sie einen weiteren Geschäftspartner aus dem Geschäftspartner-Auswahlfeld anlegen (Rechts-Klick: Neuer Datensatz), wird dieser als Vorlage verwendet für z.B. Preisliste, Zahlungsbedingungen usw.','Y','N','Y','Y','N','Vorlage Geschäftspartner',140,140,0,TO_TIMESTAMP('2024-09-20 23:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.POS - Tastenanordnung
-- Column: C_POS.C_POSKeyLayout_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.POS - Tastenanordnung
-- Column: C_POS.C_POSKeyLayout_ID
-- 2024-09-20T20:18:36.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10798,0,676,551965,625784,'F',TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Anordnung der Funktionstasten für POS','Anordnung der Funktionstasten für POS','Y','N','Y','Y','N','POS - Tastenanordnung',150,150,0,TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Auto Logout Delay
-- Column: C_POS.AutoLogoutDelay
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Auto Logout Delay
-- Column: C_POS.AutoLogoutDelay
-- 2024-09-20T20:18:36.246Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58808,0,676,551965,625785,'F',TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Automatically logout if terminal inactive for this period','Measured in seconds, zero for no automatic logout','Y','N','Y','Y','N','Auto Logout Delay',160,160,0,TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.On Screen Keyboard layout
-- Column: C_POS.OSK_KeyLayout_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.On Screen Keyboard layout
-- Column: C_POS.OSK_KeyLayout_ID
-- 2024-09-20T20:18:36.353Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58809,0,676,551965,625786,'F',TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'The key layout to use for on screen keyboard for text fields.','If empty, the on screen keyboard will not be used.','Y','N','Y','Y','N','On Screen Keyboard layout',170,170,0,TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.On Screen Number Pad layout
-- Column: C_POS.OSNP_KeyLayout_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.On Screen Number Pad layout
-- Column: C_POS.OSNP_KeyLayout_ID
-- 2024-09-20T20:18:36.459Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58810,0,676,551965,625787,'F',TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'The key layout to use for on screen number pad for numeric fields.','If empty, the on screen numberpad will not be used.','Y','N','Y','Y','N','On Screen Number Pad layout',180,180,0,TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Drucker
-- Column: C_POS.PrinterName
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Drucker
-- Column: C_POS.PrinterName
-- 2024-09-20T20:18:36.567Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10803,0,676,551965,625788,'F',TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Name of the Printer','Internal (Opereating System) Name of the Printer; Please mote that the printer name may be different on different clients. Enter a printer name, which applies to ALL clients (e.g. printer on a server). <p>
If none is entered, the default printer is used. You specify your default printer when you log in. You can also change the default printer in Preferences.','Y','N','Y','Y','N','Drucker',190,190,0,TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.CashDrawer
-- Column: C_POS.CashDrawer
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.CashDrawer
-- Column: C_POS.CashDrawer
-- 2024-09-20T20:18:36.684Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,52017,0,676,551965,625789,'F',TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','CashDrawer',200,200,0,TO_TIMESTAMP('2024-09-20 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;



































-- UI Element: POS-Terminal -> POS-Terminal.Kassenbuch
-- Column: C_POS.C_CashBook_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Kassenbuch
-- Column: C_POS.C_CashBook_ID
-- 2024-09-20T20:20:22.962Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625777
;

-- 2024-09-20T20:20:22.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10802
;

-- Field: POS-Terminal -> POS-Terminal -> Kassenbuch
-- Column: C_POS.C_CashBook_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Kassenbuch
-- Column: C_POS.C_CashBook_ID
-- 2024-09-20T20:20:22.981Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10802
;

-- 2024-09-20T20:20:22.986Z
DELETE FROM AD_Field WHERE AD_Field_ID=10802
;

-- 2024-09-20T20:20:22.990Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS C_CashBook_ID')
;

-- Column: C_POS.C_CashBook_ID
-- Column: C_POS.C_CashBook_ID
-- 2024-09-20T20:20:23.020Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=12750
;

-- 2024-09-20T20:20:23.024Z
DELETE FROM AD_Column WHERE AD_Column_ID=12750
;























-- UI Element: POS-Terminal -> POS-Terminal.POS - Tastenanordnung
-- Column: C_POS.C_POSKeyLayout_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.POS - Tastenanordnung
-- Column: C_POS.C_POSKeyLayout_ID
-- 2024-09-20T20:21:06.156Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625784
;

-- 2024-09-20T20:21:06.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10798
;

-- Field: POS-Terminal -> POS-Terminal -> POS - Tastenanordnung
-- Column: C_POS.C_POSKeyLayout_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> POS - Tastenanordnung
-- Column: C_POS.C_POSKeyLayout_ID
-- 2024-09-20T20:21:06.163Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10798
;

-- 2024-09-20T20:21:06.167Z
DELETE FROM AD_Field WHERE AD_Field_ID=10798
;

-- 2024-09-20T20:21:06.169Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS C_POSKeyLayout_ID')
;

-- Column: C_POS.C_POSKeyLayout_ID
-- Column: C_POS.C_POSKeyLayout_ID
-- 2024-09-20T20:21:06.183Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=12746
;

-- 2024-09-20T20:21:06.187Z
DELETE FROM AD_Column WHERE AD_Column_ID=12746
;

-- UI Element: POS-Terminal -> POS-Terminal.On Screen Keyboard layout
-- Column: C_POS.OSK_KeyLayout_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.On Screen Keyboard layout
-- Column: C_POS.OSK_KeyLayout_ID
-- 2024-09-20T20:21:21.218Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625786
;

-- 2024-09-20T20:21:21.220Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=58809
;

-- Field: POS-Terminal -> POS-Terminal -> On Screen Keyboard layout
-- Column: C_POS.OSK_KeyLayout_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> On Screen Keyboard layout
-- Column: C_POS.OSK_KeyLayout_ID
-- 2024-09-20T20:21:21.227Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=58809
;

-- 2024-09-20T20:21:21.231Z
DELETE FROM AD_Field WHERE AD_Field_ID=58809
;

-- 2024-09-20T20:21:21.233Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS OSK_KeyLayout_ID')
;

-- Column: C_POS.OSK_KeyLayout_ID
-- Column: C_POS.OSK_KeyLayout_ID
-- 2024-09-20T20:21:21.246Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=59091
;

-- 2024-09-20T20:21:21.250Z
DELETE FROM AD_Column WHERE AD_Column_ID=59091
;

-- UI Element: POS-Terminal -> POS-Terminal.On Screen Number Pad layout
-- Column: C_POS.OSNP_KeyLayout_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.On Screen Number Pad layout
-- Column: C_POS.OSNP_KeyLayout_ID
-- 2024-09-20T20:21:31.172Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625787
;

-- 2024-09-20T20:21:31.174Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=58810
;

-- Field: POS-Terminal -> POS-Terminal -> On Screen Number Pad layout
-- Column: C_POS.OSNP_KeyLayout_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> On Screen Number Pad layout
-- Column: C_POS.OSNP_KeyLayout_ID
-- 2024-09-20T20:21:31.179Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=58810
;

-- 2024-09-20T20:21:31.183Z
DELETE FROM AD_Field WHERE AD_Field_ID=58810
;

-- 2024-09-20T20:21:31.185Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS OSNP_KeyLayout_ID')
;

-- Column: C_POS.OSNP_KeyLayout_ID
-- Column: C_POS.OSNP_KeyLayout_ID
-- 2024-09-20T20:21:31.199Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=59092
;

-- 2024-09-20T20:21:31.204Z
DELETE FROM AD_Column WHERE AD_Column_ID=59092
;

-- UI Element: POS-Terminal -> POS-Terminal.Kundenbetreuer
-- Column: C_POS.SalesRep_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Kundenbetreuer
-- Column: C_POS.SalesRep_ID
-- 2024-09-20T20:21:41.300Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625776
;

-- 2024-09-20T20:21:41.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10800
;

-- Field: POS-Terminal -> POS-Terminal -> Kundenbetreuer
-- Column: C_POS.SalesRep_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Kundenbetreuer
-- Column: C_POS.SalesRep_ID
-- 2024-09-20T20:21:41.307Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10800
;

-- 2024-09-20T20:21:41.311Z
DELETE FROM AD_Field WHERE AD_Field_ID=10800
;

-- 2024-09-20T20:21:41.313Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS SalesRep_ID')
;

-- Column: C_POS.SalesRep_ID
-- Column: C_POS.SalesRep_ID
-- 2024-09-20T20:21:41.325Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=12748
;

-- 2024-09-20T20:21:41.329Z
DELETE FROM AD_Column WHERE AD_Column_ID=12748
;

-- UI Element: POS-Terminal -> POS-Terminal.CashDrawer
-- Column: C_POS.CashDrawer
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.CashDrawer
-- Column: C_POS.CashDrawer
-- 2024-09-20T20:22:12.661Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625789
;

-- 2024-09-20T20:22:12.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=52017
;

-- Field: POS-Terminal -> POS-Terminal -> CashDrawer
-- Column: C_POS.CashDrawer
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> CashDrawer
-- Column: C_POS.CashDrawer
-- 2024-09-20T20:22:12.668Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=52017
;

-- 2024-09-20T20:22:12.672Z
DELETE FROM AD_Field WHERE AD_Field_ID=52017
;

-- 2024-09-20T20:22:12.673Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS CashDrawer')
;

-- Column: C_POS.CashDrawer
-- Column: C_POS.CashDrawer
-- 2024-09-20T20:22:12.683Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=52058
;

-- 2024-09-20T20:22:12.686Z
DELETE FROM AD_Column WHERE AD_Column_ID=52058
;

-- UI Element: POS-Terminal -> POS-Terminal.Kommentar/Hilfe
-- Column: C_POS.Help
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Kommentar/Hilfe
-- Column: C_POS.Help
-- 2024-09-20T20:22:24.105Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625774
;

-- 2024-09-20T20:22:24.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10805
;

-- Field: POS-Terminal -> POS-Terminal -> Kommentar/Hilfe
-- Column: C_POS.Help
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Kommentar/Hilfe
-- Column: C_POS.Help
-- 2024-09-20T20:22:24.115Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10805
;

-- 2024-09-20T20:22:24.119Z
DELETE FROM AD_Field WHERE AD_Field_ID=10805
;

-- 2024-09-20T20:22:24.123Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE C_POS DROP COLUMN IF EXISTS Help')
;

-- Column: C_POS.Help
-- Column: C_POS.Help
-- 2024-09-20T20:22:24.136Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=12753
;

-- 2024-09-20T20:22:24.140Z
DELETE FROM AD_Column WHERE AD_Column_ID=12753
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: org & client
-- 2024-09-20T20:23:00.592Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547556,551966,TO_TIMESTAMP('2024-09-20 23:23:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','org & client',10,TO_TIMESTAMP('2024-09-20 23:23:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Organisation
-- Column: C_POS.AD_Org_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> org & client.Organisation
-- Column: C_POS.AD_Org_ID
-- 2024-09-20T20:23:08.877Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551966, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625771
;

-- UI Element: POS-Terminal -> POS-Terminal.Mandant
-- Column: C_POS.AD_Client_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> org & client.Mandant
-- Column: C_POS.AD_Client_ID
-- 2024-09-20T20:23:30.875Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551966, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-09-20 23:23:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625770
;

-- UI Element: POS-Terminal -> POS-Terminal.Modify Price
-- Column: C_POS.IsModifyPrice
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Modify Price
-- Column: C_POS.IsModifyPrice
-- 2024-09-20T20:24:14.948Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625779
;

-- UI Element: POS-Terminal -> POS-Terminal.Auto Logout Delay
-- Column: C_POS.AutoLogoutDelay
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Auto Logout Delay
-- Column: C_POS.AutoLogoutDelay
-- 2024-09-20T20:24:21.780Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625785
;

-- UI Element: POS-Terminal -> POS-Terminal.Drucker
-- Column: C_POS.PrinterName
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Drucker
-- Column: C_POS.PrinterName
-- 2024-09-20T20:24:25.529Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=625788
;

-- Column: C_POS.C_DocTypeOrder_ID
-- Column: C_POS.C_DocTypeOrder_ID
-- 2024-09-20T20:25:14.895Z
UPDATE AD_Column SET AD_Element_ID=577366, AD_Reference_ID=30, AD_Reference_Value_ID=170, ColumnName='C_DocTypeOrder_ID', Description='Document type used for the orders generated from this order candidate', FieldLength=10, Help='The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.', IsExcludeFromZoomTargets='Y', IsMandatory='Y', Name='Auftrags-Belegart',Updated=TO_TIMESTAMP('2024-09-20 23:25:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12795
;

-- 2024-09-20T20:25:14.906Z
UPDATE AD_Field SET Name='Auftrags-Belegart', Description='Document type used for the orders generated from this order candidate', Help='The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.' WHERE AD_Column_ID=12795
;

-- 2024-09-20T20:25:14.908Z
/* DDL */  select update_Column_Translation_From_AD_Element(577366) 
;

alter table C_POS rename column c_doctype_id to C_DocTypeOrder_ID;































-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: flags
-- 2024-09-20T20:27:20.089Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547556,551967,TO_TIMESTAMP('2024-09-20 23:27:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2024-09-20 23:27:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Aktiv
-- Column: C_POS.IsActive
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> flags.Aktiv
-- Column: C_POS.IsActive
-- 2024-09-20T20:27:30.377Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551967, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625775
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10
-- UI Element Group: ship from
-- 2024-09-20T20:28:24.325Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547555,551968,TO_TIMESTAMP('2024-09-20 23:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','ship from',20,TO_TIMESTAMP('2024-09-20 23:28:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Lager
-- Column: C_POS.M_Warehouse_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> ship from.Lager
-- Column: C_POS.M_Warehouse_ID
-- 2024-09-20T20:28:37.328Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551968, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625780
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10
-- UI Element Group: cashbook
-- 2024-09-20T20:29:18.453Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547555,551969,TO_TIMESTAMP('2024-09-20 23:29:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','cashbook',30,TO_TIMESTAMP('2024-09-20 23:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Bankverbindung
-- Column: C_POS.C_BP_BankAccount_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> cashbook.Bankverbindung
-- Column: C_POS.C_BP_BankAccount_ID
-- 2024-09-20T20:29:33.517Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551969, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:29:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625781
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10
-- UI Element Group: pricing
-- 2024-09-20T20:29:50.643Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547555,551970,TO_TIMESTAMP('2024-09-20 23:29:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','pricing',40,TO_TIMESTAMP('2024-09-20 23:29:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Preisliste
-- Column: C_POS.M_PriceList_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> pricing.Preisliste
-- Column: C_POS.M_PriceList_ID
-- 2024-09-20T20:30:06.408Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551970, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:30:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625778
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10
-- UI Element Group: follow-up docs
-- 2024-09-20T20:30:40.881Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547555,551971,TO_TIMESTAMP('2024-09-20 23:30:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','follow-up docs',50,TO_TIMESTAMP('2024-09-20 23:30:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Belegart
-- Column: C_POS.C_DocTypeOrder_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> follow-up docs.Belegart
-- Column: C_POS.C_DocTypeOrder_ID
-- 2024-09-20T20:31:18.260Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551971, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:31:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625782
;

-- UI Element: POS-Terminal -> POS-Terminal.Mandant
-- Column: C_POS.AD_Client_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> org & client.Mandant
-- Column: C_POS.AD_Client_ID
-- 2024-09-20T20:31:43.319Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625770
;

-- UI Element: POS-Terminal -> POS-Terminal.Organisation
-- Column: C_POS.AD_Org_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> org & client.Organisation
-- Column: C_POS.AD_Org_ID
-- 2024-09-20T20:31:43.327Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625771
;

-- UI Element: POS-Terminal -> POS-Terminal.Vorlage Geschäftspartner
-- Column: C_POS.C_BPartnerCashTrx_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Vorlage Geschäftspartner
-- Column: C_POS.C_BPartnerCashTrx_ID
-- 2024-09-20T20:31:43.332Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625783
;

-- UI Element: POS-Terminal -> POS-Terminal.Belegart
-- Column: C_POS.C_DocTypeOrder_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> follow-up docs.Belegart
-- Column: C_POS.C_DocTypeOrder_ID
-- 2024-09-20T20:31:43.338Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625782
;

-- UI Element: POS-Terminal -> POS-Terminal.Beschreibung
-- Column: C_POS.Description
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Beschreibung
-- Column: C_POS.Description
-- 2024-09-20T20:31:43.343Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625773
;

-- UI Element: POS-Terminal -> POS-Terminal.Preisliste
-- Column: C_POS.M_PriceList_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> pricing.Preisliste
-- Column: C_POS.M_PriceList_ID
-- 2024-09-20T20:31:43.349Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625778
;

-- UI Element: POS-Terminal -> POS-Terminal.Name
-- Column: C_POS.Name
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Name
-- Column: C_POS.Name
-- 2024-09-20T20:31:43.354Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625772
;

-- UI Element: POS-Terminal -> POS-Terminal.Aktiv
-- Column: C_POS.IsActive
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> flags.Aktiv
-- Column: C_POS.IsActive
-- 2024-09-20T20:31:43.358Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625775
;

-- UI Element: POS-Terminal -> POS-Terminal.Lager
-- Column: C_POS.M_Warehouse_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> ship from.Lager
-- Column: C_POS.M_Warehouse_ID
-- 2024-09-20T20:31:43.363Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625780
;

-- UI Element: POS-Terminal -> POS-Terminal.Bankverbindung
-- Column: C_POS.C_BP_BankAccount_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> cashbook.Bankverbindung
-- Column: C_POS.C_BP_BankAccount_ID
-- 2024-09-20T20:31:43.368Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-20 23:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625781
;

-- Column: C_POS.M_Warehouse_ID
-- Column: C_POS.M_Warehouse_ID
-- 2024-09-20T20:32:02.297Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 23:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12752
;

-- Column: C_POS.M_PriceList_ID
-- Column: C_POS.M_PriceList_ID
-- 2024-09-20T20:32:08.010Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 23:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12754
;

-- Column: C_POS.C_BP_BankAccount_ID
-- Column: C_POS.C_BP_BankAccount_ID
-- 2024-09-20T20:32:16.910Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 23:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54236
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: flags
-- 2024-09-20T20:53:49.905Z
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 23:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551967
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: org & client
-- 2024-09-20T20:53:51.412Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2024-09-20 23:53:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551966
;

