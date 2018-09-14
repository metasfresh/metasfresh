-- 2018-09-14T17:09:45.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541139,0,540078,TO_TIMESTAMP('2018-09-14 17:09:44','YYYY-MM-DD HH24:MI:SS'),100,'Define which email server should be used in which context','de.metas.swat','_EMail_Server_Routing','Y','N','N','N','N','EMail Server Routing',TO_TIMESTAMP('2018-09-14 17:09:44','YYYY-MM-DD HH24:MI:SS'),100,'EMail Server Routing')
;

-- 2018-09-14T17:09:45.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541139 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-09-14T17:09:45.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541139, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541139)
;

-- 2018-09-14T17:09:45.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540277, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540209 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:45.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540277, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540203 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:45.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540277, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541139 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:57.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541139 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:57.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541132 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:57.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541133 AND AD_Tree_ID=10
;

-- 2018-09-14T17:14:21.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540231,540896,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-09-14T17:14:21.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540896 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-09-14T17:14:21.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541168,540896,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541169,540896,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541168,541873,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545251,0,540231,541873,553801,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545253,0,540231,541873,553802,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545254,0,540231,541873,553803,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','Y','N','Prozess',30,30,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545252,0,540231,541873,553804,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Custom Type',40,40,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556927,0,540231,541873,553805,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Dokument Basis Typ',50,50,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556928,0,540231,541873,553806,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Document Sub Type','The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','Y','Y','N','Doc Sub Type',60,60,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:21.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545249,0,540231,541873,553807,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Mail Box',70,70,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:14:22.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557001,0,540231,541873,553808,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Column User To',80,80,0,TO_TIMESTAMP('2018-09-14 17:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

