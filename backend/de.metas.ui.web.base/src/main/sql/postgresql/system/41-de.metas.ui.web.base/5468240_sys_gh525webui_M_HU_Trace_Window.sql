-- 2017-07-20T14:24:40.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540900,0,540353,TO_TIMESTAMP('2017-07-20 14:24:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','_M_HU_Trace','Y','N','N','N','N','HU-Rückverfolgung',TO_TIMESTAMP('2017-07-20 14:24:40','YYYY-MM-DD HH24:MI:SS'),100,'HU-Rückverfolgung')
;

-- 2017-07-20T14:24:40.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540900 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-07-20T14:24:40.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540900, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540900)
;

-- 2017-07-20T14:24:41.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540479 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540481 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540893 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540482 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540489 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540490 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540520 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540545 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540546 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540555 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540561 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540600 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540659 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540597 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:41.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2017-07-20T14:24:46.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2017-07-20T14:25:18.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 14:25:18','YYYY-MM-DD HH24:MI:SS'),Name='Handling Unit Trace',WEBUI_NameBrowse='Handling Unit Trace' WHERE AD_Menu_ID=540900 AND AD_Language='en_US'
;

-- 2017-07-20T14:25:22.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 14:25:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=540900 AND AD_Language='en_US'
;



-- 2017-07-20T14:29:53.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540842,540380,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-20T14:29:53.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540380 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-20T14:29:53.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540514,540380,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540515,540380,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540514,540884,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558810,0,540842,540884,546751,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','Y','N','Produkt',10,10,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558811,0,540842,540884,546752,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','Y','N','Menge',20,20,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558795,0,540842,540884,546753,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Rückverfolgbarkeit',30,30,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558801,0,540842,540884,546754,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zeitpunkt',40,40,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558805,0,540842,540884,546755,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Typ',50,50,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558796,0,540842,540884,546756,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Handling Units',60,60,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558806,0,540842,540884,546757,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','CU Handling Unit (VHU)',70,70,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558807,0,540842,540884,546758,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','CU (VHU) Gebindestatus',80,80,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558803,0,540842,540884,546759,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','Y','N','Belegart',90,90,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558804,0,540842,540884,546760,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','Y','N','Belegstatus',100,100,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558798,0,540842,540884,546761,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','Y','Y','N','Lieferung/Wareneingang',110,110,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:53.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558799,0,540842,540884,546762,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Bewegung von Warenbestand','"Warenbewegung" bezeichnet eindeutig eine Gruppe von Warenbewegungspositionen.','Y','N','Y','Y','N','Warenbewegung',120,120,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:54.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558808,0,540842,540884,546763,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','HU-Transaktionszeile',130,130,0,TO_TIMESTAMP('2017-07-20 14:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:54.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558797,0,540842,540884,546764,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdisposition',140,140,0,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:54.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558802,0,540842,540884,546765,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Manufacturing Cost Collector',150,150,0,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:54.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558809,0,540842,540884,546766,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produktionsauftrag',160,160,0,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:29:54.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558800,0,540842,540884,546767,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ursprungs-VHU',170,170,0,TO_TIMESTAMP('2017-07-20 14:29:54','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2017-07-20T14:43:53.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 14:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540514
;

-- 2017-07-20T14:43:56.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-20 14:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540515
;

-- 2017-07-20T14:44:18.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540515,540885,TO_TIMESTAMP('2017-07-20 14:44:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-20 14:44:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:45:06.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558810,0,540842,540885,546768,TO_TIMESTAMP('2017-07-20 14:45:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2017-07-20 14:45:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T14:45:43.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558811,0,540842,540885,546769,TO_TIMESTAMP('2017-07-20 14:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Menge',20,0,0,TO_TIMESTAMP('2017-07-20 14:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:05:05.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='product',Updated=TO_TIMESTAMP('2017-07-20 15:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540885
;

-- 2017-07-20T15:05:20.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540515,540886,TO_TIMESTAMP('2017-07-20 15:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','hu',20,'primary',TO_TIMESTAMP('2017-07-20 15:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:06:01.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 15:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546768
;

-- 2017-07-20T15:06:05.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 15:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546769
;

-- 2017-07-20T15:06:27.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558805,0,540842,540885,546770,TO_TIMESTAMP('2017-07-20 15:06:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Typ',10,0,0,TO_TIMESTAMP('2017-07-20 15:06:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:07:14.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558796,0,540842,540886,546771,TO_TIMESTAMP('2017-07-20 15:07:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Handling Unit',10,0,0,TO_TIMESTAMP('2017-07-20 15:07:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:07:33.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558806,0,540842,540886,546772,TO_TIMESTAMP('2017-07-20 15:07:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Virtual Handling Unit ',20,0,0,TO_TIMESTAMP('2017-07-20 15:07:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:07:47.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558807,0,540842,540886,546773,TO_TIMESTAMP('2017-07-20 15:07:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gebindestatus',30,0,0,TO_TIMESTAMP('2017-07-20 15:07:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:08:58.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=90, UIStyle='',Updated=TO_TIMESTAMP('2017-07-20 15:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540884
;

-- 2017-07-20T15:09:13.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540514,540887,TO_TIMESTAMP('2017-07-20 15:09:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',10,TO_TIMESTAMP('2017-07-20 15:09:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:09:27.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558794,0,540842,540887,546774,TO_TIMESTAMP('2017-07-20 15:09:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-07-20 15:09:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:09:48.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558801,0,540842,540887,546775,TO_TIMESTAMP('2017-07-20 15:09:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeitpunkt',20,0,0,TO_TIMESTAMP('2017-07-20 15:09:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:10:29.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540514,540888,TO_TIMESTAMP('2017-07-20 15:10:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-07-20 15:10:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:10:40.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558793,0,540842,540888,546776,TO_TIMESTAMP('2017-07-20 15:10:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-20 15:10:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:10:48.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558792,0,540842,540888,546777,TO_TIMESTAMP('2017-07-20 15:10:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-20 15:10:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:19:06.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546770
;

-- 2017-07-20T15:19:48.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558805,0,540842,540887,546778,TO_TIMESTAMP('2017-07-20 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Typ',30,0,0,TO_TIMESTAMP('2017-07-20 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:19:55.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 15:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546778
;

-- 2017-07-20T15:19:59.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 15:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546775
;

-- 2017-07-20T15:20:55.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540515,540889,TO_TIMESTAMP('2017-07-20 15:20:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',30,TO_TIMESTAMP('2017-07-20 15:20:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:22:01.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558803,0,540842,540887,546779,TO_TIMESTAMP('2017-07-20 15:22:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Belegart',40,0,0,TO_TIMESTAMP('2017-07-20 15:22:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:22:18.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558804,0,540842,540887,546780,TO_TIMESTAMP('2017-07-20 15:22:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Belegstatus',50,0,0,TO_TIMESTAMP('2017-07-20 15:22:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:22:39.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 15:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540888
;

-- 2017-07-20T15:22:48.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540514,540890,TO_TIMESTAMP('2017-07-20 15:22:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','doc',20,TO_TIMESTAMP('2017-07-20 15:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:23:40.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540890, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-20 15:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546779
;

-- 2017-07-20T15:23:54.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540890, SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 15:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546780
;

-- 2017-07-20T15:25:21.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='detailsHU',Updated=TO_TIMESTAMP('2017-07-20 15:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540889
;

-- 2017-07-20T15:25:43.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558808,0,540842,540889,546781,TO_TIMESTAMP('2017-07-20 15:25:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Transaktionszeile',10,0,0,TO_TIMESTAMP('2017-07-20 15:25:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:26:01.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558800,0,540842,540889,546782,TO_TIMESTAMP('2017-07-20 15:26:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Quell-HU',20,0,0,TO_TIMESTAMP('2017-07-20 15:26:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:26:38.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540515,540891,TO_TIMESTAMP('2017-07-20 15:26:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','details document',40,TO_TIMESTAMP('2017-07-20 15:26:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:26:57.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558798,0,540842,540891,546783,TO_TIMESTAMP('2017-07-20 15:26:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung/Wareneingang',10,0,0,TO_TIMESTAMP('2017-07-20 15:26:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:27:09.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558799,0,540842,540891,546784,TO_TIMESTAMP('2017-07-20 15:27:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Warenbewegung',20,0,0,TO_TIMESTAMP('2017-07-20 15:27:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:27:30.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558797,0,540842,540891,546785,TO_TIMESTAMP('2017-07-20 15:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferdisposition',30,0,0,TO_TIMESTAMP('2017-07-20 15:27:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:27:55.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558802,0,540842,540891,546786,TO_TIMESTAMP('2017-07-20 15:27:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Manufacturing Cost Collector',40,0,0,TO_TIMESTAMP('2017-07-20 15:27:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:28:18.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558809,0,540842,540891,546787,TO_TIMESTAMP('2017-07-20 15:28:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsauftrag',50,0,0,TO_TIMESTAMP('2017-07-20 15:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T15:29:25.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546751
;

-- 2017-07-20T15:29:25.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546752
;

-- 2017-07-20T15:29:25.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546753
;

-- 2017-07-20T15:29:25.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546754
;

-- 2017-07-20T15:29:25.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546755
;

-- 2017-07-20T15:29:25.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546756
;

-- 2017-07-20T15:29:25.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546757
;

-- 2017-07-20T15:29:25.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546758
;

-- 2017-07-20T15:29:25.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546759
;

-- 2017-07-20T15:29:25.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546760
;

-- 2017-07-20T15:29:25.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546761
;

-- 2017-07-20T15:29:25.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546762
;

-- 2017-07-20T15:29:25.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546763
;

-- 2017-07-20T15:29:25.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546764
;

-- 2017-07-20T15:29:25.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546765
;

-- 2017-07-20T15:29:25.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546766
;

-- 2017-07-20T15:29:25.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546767
;

-- 2017-07-20T15:30:51.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-20 15:30:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540889
;

-- 2017-07-20T15:30:55.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 15:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540891
;

-- 2017-07-20T15:35:06.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546775
;

-- 2017-07-20T15:35:06.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546778
;

-- 2017-07-20T15:35:06.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546771
;

-- 2017-07-20T15:35:06.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546772
;

-- 2017-07-20T15:35:06.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546773
;

-- 2017-07-20T15:35:06.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546779
;

-- 2017-07-20T15:35:06.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-20 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546780
;

-- 2017-07-20T15:36:47.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546783
;

-- 2017-07-20T15:36:47.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546784
;

-- 2017-07-20T15:36:47.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546781
;

-- 2017-07-20T15:36:47.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546785
;

-- 2017-07-20T15:36:47.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546786
;

-- 2017-07-20T15:36:47.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546787
;

-- 2017-07-20T15:36:47.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546782
;

-- 2017-07-20T15:36:47.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546768
;

-- 2017-07-20T15:36:47.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-07-20 15:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546769
;




-- 2017-07-20T15:41:30.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-20 15:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546771
;

-- 2017-07-20T15:41:30.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-20 15:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546775
;

-- 2017-07-20T15:41:30.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-20 15:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546778
;



-- 2017-07-20T15:56:03.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:56:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Event Time',PrintName='Event Time' WHERE AD_Element_ID=543324 AND AD_Language='en_US'
;

-- 2017-07-20T15:56:03.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543324,'en_US') 
;

-- 2017-07-20T15:56:42.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:56:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='HU Trace Type',PrintName='HU Trace Type' WHERE AD_Element_ID=543378 AND AD_Language='en_US'
;

-- 2017-07-20T15:56:42.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543378,'en_US') 
;

-- 2017-07-20T15:57:52.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:57:52','YYYY-MM-DD HH24:MI:SS'),Name='HU Transaction Line',PrintName='HU Transaction Line' WHERE AD_Element_ID=542139 AND AD_Language='en_US'
;

-- 2017-07-20T15:57:52.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542139,'en_US') 
;

-- 2017-07-20T15:58:34.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:58:34','YYYY-MM-DD HH24:MI:SS'),Name='Material Shipment Document' WHERE AD_Column_ID=556993 AND AD_Language='en_US'
;

-- 2017-07-20T15:58:48.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:58:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Column_ID=556993 AND AD_Language='en_US'
;

-- 2017-07-20T15:59:02.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:59:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=542139 AND AD_Language='en_US'
;

-- 2017-07-20T15:59:02.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542139,'en_US') 
;

-- 2017-07-20T15:59:21.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 15:59:21','YYYY-MM-DD HH24:MI:SS'),Name='Movement Document',IsTranslated='Y' WHERE AD_Column_ID=556994 AND AD_Language='en_US'
;

-- 2017-07-20T16:03:07.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:03:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit Trace' WHERE AD_Field_ID=558795 AND AD_Language='en_US'
;

-- 2017-07-20T16:03:52.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:03:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit' WHERE AD_Field_ID=558796 AND AD_Language='en_US'
;

-- 2017-07-20T16:04:11.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:04:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Virtual Handling Unit' WHERE AD_Field_ID=558806 AND AD_Language='en_US'
;

-- 2017-07-20T16:04:30.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:04:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='VHU Status' WHERE AD_Field_ID=558807 AND AD_Language='en_US'
;

-- 2017-07-20T16:05:00.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:05:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Document Type',Description='',Help='' WHERE AD_Field_ID=558803 AND AD_Language='en_US'
;

-- 2017-07-20T16:05:23.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:05:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Document Status',Description='',Help='' WHERE AD_Field_ID=558804 AND AD_Language='en_US'
;

-- 2017-07-20T16:05:39.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:05:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Material Shipment Document' WHERE AD_Field_ID=558798 AND AD_Language='en_US'
;

-- 2017-07-20T16:05:54.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:05:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Movement Document',Description='',Help='' WHERE AD_Field_ID=558799 AND AD_Language='en_US'
;

-- 2017-07-20T16:06:14.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:06:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment Schedule' WHERE AD_Field_ID=558797 AND AD_Language='en_US'
;

-- 2017-07-20T16:06:27.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:06:27','YYYY-MM-DD HH24:MI:SS') WHERE AD_Field_ID=558802 AND AD_Language='nl_NL'
;

-- 2017-07-20T16:06:33.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:06:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=558802 AND AD_Language='en_US'
;

-- 2017-07-20T16:06:56.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:06:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Manufacturing Order' WHERE AD_Field_ID=558809 AND AD_Language='en_US'
;

-- 2017-07-20T16:07:13.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:07:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Virtual Handling Unit Source' WHERE AD_Field_ID=558800 AND AD_Language='en_US'
;

-- 2017-07-20T16:07:36.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:07:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity',Description='Product quantity',Help='' WHERE AD_Field_ID=558811 AND AD_Language='en_US'
;

-- 2017-07-20T16:07:50.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:07:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product',Description='',Help='' WHERE AD_Field_ID=558810 AND AD_Language='en_US'
;

-- 2017-07-20T16:08:11.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:08:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=558793 AND AD_Language='en_US'
;

-- 2017-07-20T16:08:23.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:08:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=558792 AND AD_Language='en_US'
;

-- 2017-07-20T16:08:47.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 16:08:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=558794 AND AD_Language='en_US'
;

-- 2017-07-20T16:25:35.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 16:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540885
;

-- 2017-07-20T16:25:38.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 16:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540886
;

-- 2017-07-20T16:29:31.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-20 16:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540891
;

-- 2017-07-20T16:29:44.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540515,540892,TO_TIMESTAMP('2017-07-20 16:29:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','type',10,'primary',TO_TIMESTAMP('2017-07-20 16:29:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T16:30:39.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-20 16:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546778
;

-- 2017-07-20T16:30:48.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 16:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546775
;

-- 2017-07-20T16:31:40.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 16:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546768
;

-- 2017-07-20T16:31:46.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=40,Updated=TO_TIMESTAMP('2017-07-20 16:31:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546769
;

-- 2017-07-20T16:31:54.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540885
;

-- 2017-07-20T16:32:07.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=50,Updated=TO_TIMESTAMP('2017-07-20 16:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546771
;

-- 2017-07-20T16:32:13.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=60,Updated=TO_TIMESTAMP('2017-07-20 16:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546772
;

-- 2017-07-20T16:32:18.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540892, SeqNo=70,Updated=TO_TIMESTAMP('2017-07-20 16:32:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546773
;

-- 2017-07-20T16:32:24.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540886
;

-- 2017-07-20T16:32:32.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-20 16:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540889
;

-- 2017-07-20T16:32:38.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-20 16:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540891
;

-- 2017-07-20T16:32:49.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540884
;

-- 2017-07-21T10:02:43.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540890, SeqNo=30,Updated=TO_TIMESTAMP('2017-07-21 10:02:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546781
;

-- 2017-07-21T10:02:51.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540890, SeqNo=40,Updated=TO_TIMESTAMP('2017-07-21 10:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546782
;

