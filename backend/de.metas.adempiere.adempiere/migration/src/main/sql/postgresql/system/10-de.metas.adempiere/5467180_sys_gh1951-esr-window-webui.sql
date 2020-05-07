-- 2017-07-05T17:33:45.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540891,0,540159,TO_TIMESTAMP('2017-07-05 17:33:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','_ESR Zahlungsimport','Y','N','N','N','N','ESR Zahlungsimport',TO_TIMESTAMP('2017-07-05 17:33:45','YYYY-MM-DD HH24:MI:SS'),100,'ESR Zahlungsimport')
;

-- 2017-07-05T17:33:45.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540891 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-07-05T17:33:45.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540891, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540891)
;

-- 2017-07-05T17:33:46.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540385 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:46.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540398 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:46.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540399 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:46.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540386 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:46.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540387 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:46.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540447 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:46.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000082 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2017-07-05T17:33:51.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2017-07-05T18:14:55.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540442,540332,TO_TIMESTAMP('2017-07-05 18:14:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:14:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-05T18:14:55.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540332 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-05T18:14:55.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540454,540332,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540455,540332,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540454,540786,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550772,0,540442,540786,546239,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie kA¶nnen keine Daten ALber Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550773,0,540442,540786,546240,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550768,0,540442,540786,546241,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Belegs','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','Y','Y','N','Belegdatum',30,10,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551431,0,540442,540786,546242,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','Y','Y','N','Bankverbindung',40,20,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551042,0,540442,540786,546243,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufs-Transaktion (Zahlungseingang)','Y','N','Y','N','N','Zahlungseingang',50,0,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550796,0,540442,540786,546244,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',60,30,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551052,0,540442,540786,546245,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Element ist gültig','Das Element hat die Überprüfung bestanden','Y','N','Y','N','N','Gültig',70,0,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551030,0,540442,540786,546246,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',80,40,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551083,0,540442,540786,546247,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Anzahl Transaktionen',90,50,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551051,0,540442,540786,546248,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gesamtbetrag',100,60,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550771,0,540442,540786,546249,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ESR Zahlungsimport',110,0,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558526,0,540442,540786,546250,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Art der Daten','Y','N','Y','Y','N','Daten-Typ',120,70,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540443,540333,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-05T18:14:55.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540333 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-05T18:14:55.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540456,540333,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540456,540787,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550783,0,540443,540787,546251,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Zahlungsimport',0,10,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550790,0,540443,540787,546252,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Zeile Nr.','Y','N','N','Y','N','Position',0,20,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550787,0,540443,540787,546253,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,30,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551033,0,540443,540787,546254,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','Verarbeitet',0,40,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550793,0,540443,540787,546255,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,50,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:55.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551043,0,540443,540787,546256,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Importstatus',0,60,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551778,0,540443,540787,546257,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Gesamtbetrag der zugeordneten Rechnung','Y','N','N','Y','N','Rechnungsbetrag',0,70,0,TO_TIMESTAMP('2017-07-05 18:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551779,0,540443,540787,546258,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.','Y','N','N','Y','N','Offener Rechnungsbetrag',0,80,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550778,0,540443,540787,546259,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','Y','N','Betrag',0,90,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551681,0,540443,540787,546260,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Aktion',0,100,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550855,0,540443,540787,546261,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums','Y','N','N','Y','N','Import-Fehler',0,110,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550794,0,540443,540787,546262,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Transaktionsart',0,120,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550792,0,540443,540787,546263,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Referenznummer der jeweiligen Rechnung','Y','N','N','Y','N','ESR Referenznummer (Rechnung)',0,130,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551870,0,540443,540787,546264,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Extern erstellte ESR-Referenz',0,140,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550791,0,540443,540787,546265,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Post-Teilnehmernummer','Y','N','N','Y','N','Post-Teilnehmernummer',0,150,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551172,0,540443,540787,546266,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','N','Y','N','Bankverbindung',0,160,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550857,0,540443,540787,546267,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung.','Y','N','N','Y','N','Organisation',0,170,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550789,0,540443,540787,546268,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Sektion',0,180,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550856,0,540443,540787,546269,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,190,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550788,0,540443,540787,546270,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Geschäftspartner','Y','N','N','Y','N','Geschäftspartner-Schlüssel',0,200,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550858,0,540443,540787,546271,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','Rechnung',0,210,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551900,0,540443,540787,546272,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Belegnummer der zugeordneten Rechnung','Y','N','N','Y','N','ESR Rechnungsnummer',0,220,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551883,0,540443,540787,546273,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnung manuell zugeordnet',0,230,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551848,0,540443,540787,546274,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Bereits zugeordnet',0,240,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550779,0,540443,540787,546275,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Buchungsdatum',0,250,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550777,0,540443,540787,546276,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zahldatum',0,260,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550795,0,540443,540787,546277,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,270,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551032,0,540443,540787,546278,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Payment identifier','The Payment is a unique identifier of this payment.','Y','N','N','Y','N','Zahlung',0,280,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551031,0,540443,540787,546279,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Element ist gültig','Das Element hat die Überprüfung bestanden','Y','N','N','Y','N','Gültig',0,290,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550785,0,540443,540787,546280,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'ESR complete line text','Y','N','N','Y','N','Importierte ESR-Zeile',0,300,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558657,0,540443,540787,546281,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Fehler beim Zuordnen der Daten, z.B. fehlende Rechnung für eine eingelesene Referenznummer','Y','N','N','Y','N','Zuordnungsfehler',0,310,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540642,540334,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-05T18:14:56.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540334 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-05T18:14:56.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540457,540334,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540457,540788,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555055,0,540642,540788,546282,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,10,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:56.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555056,0,540642,540788,546283,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,20,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555057,0,540642,540788,546284,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,30,0,TO_TIMESTAMP('2017-07-05 18:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555059,0,540642,540788,546285,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','N','Y','N','Bankverbindung',0,40,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555060,0,540642,540788,546286,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Bereits zugeordnet',0,50,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555061,0,540642,540788,546287,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555062,0,540642,540788,546288,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','Y','N','Betrag',0,70,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555063,0,540642,540788,546289,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Buchungsdatum',0,80,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555064,0,540642,540788,546290,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,90,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555065,0,540642,540788,546291,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,100,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555066,0,540642,540788,546292,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Belegnummer der zugeordneten Rechnung','Y','N','N','Y','N','ESR Rechnungsnummer',0,110,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555067,0,540642,540788,546293,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Referenznummer der jeweiligen Rechnung','Y','N','N','Y','N','ESR Referenznummer (Rechnung)',0,120,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555068,0,540642,540788,546294,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Sektion',0,130,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555069,0,540642,540788,546295,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Zahlungsimport',0,140,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555070,0,540642,540788,546296,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR_ImportLine',0,150,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555071,0,540642,540788,546297,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Aktion',0,160,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555072,0,540642,540788,546298,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Extern erstellte ESR-Referenz',0,170,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555073,0,540642,540788,546299,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums','Y','N','N','Y','N','Import-Fehler',0,180,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555074,0,540642,540788,546300,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,190,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555075,0,540642,540788,546301,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Geschäftspartner','Y','N','N','Y','N','Geschäftspartner-Schlüssel',0,200,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555076,0,540642,540788,546302,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Element ist gültig','Das Element hat die Überprüfung bestanden','Y','N','N','Y','N','Gültig',0,210,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555077,0,540642,540788,546303,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'ESR complete line text','Y','N','N','Y','N','Importierte ESR-Zeile',0,220,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555078,0,540642,540788,546304,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Importstatus',0,230,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555079,0,540642,540788,546305,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Kontonummer','The Account Number indicates the Number assigned to this bank account. ','Y','N','N','Y','N','Konto-Nr.',0,240,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555080,0,540642,540788,546306,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,250,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555081,0,540642,540788,546307,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein manueller Vorgang','Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorang manuell durchgeführt wird.','Y','N','N','Y','N','Manuell',0,260,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555082,0,540642,540788,546308,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.','Y','N','N','Y','N','Offener Rechnungsbetrag',0,270,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555083,0,540642,540788,546309,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung.','Y','N','N','Y','N','Organisation',0,280,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555084,0,540642,540788,546310,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Zeile Nr.','Y','N','N','Y','N','Position',0,290,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555085,0,540642,540788,546311,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Post-Teilnehmernummer','Y','N','N','Y','N','Post-Teilnehmernummer',0,300,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:57.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555086,0,540642,540788,546312,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','Rechnung',0,310,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555087,0,540642,540788,546313,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100,'Gesamtbetrag der zugeordneten Rechnung','Y','N','N','Y','N','Rechnungsbetrag',0,320,0,TO_TIMESTAMP('2017-07-05 18:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555088,0,540642,540788,546314,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Reference No',0,330,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555089,0,540642,540788,546315,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,340,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555090,0,540642,540788,546316,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Transaktionsart',0,350,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555091,0,540642,540788,546317,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','Verarbeitet',0,360,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555092,0,540642,540788,546318,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zahldatum',0,370,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555093,0,540642,540788,546319,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Payment identifier','The Payment is a unique identifier of this payment.','Y','N','N','Y','N','Zahlung',0,380,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:14:58.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558658,0,540642,540788,546320,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Fehler beim Zuordnen der Daten, z.B. fehlende Rechnung für eine eingelesene Referenznummer','Y','N','N','Y','N','Zuordnungsfehler',0,390,0,TO_TIMESTAMP('2017-07-05 18:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:16:31.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540454,540789,TO_TIMESTAMP('2017-07-05 18:16:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-05 18:16:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:16:44.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=20, UIStyle='',Updated=TO_TIMESTAMP('2017-07-05 18:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540786
;

-- 2017-07-05T18:17:17.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551431,0,540442,540789,546321,TO_TIMESTAMP('2017-07-05 18:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bankverbindung',10,0,0,TO_TIMESTAMP('2017-07-05 18:17:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:18:37.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540455,540790,TO_TIMESTAMP('2017-07-05 18:18:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-07-05 18:18:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:18:55.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551042,0,540442,540790,546322,TO_TIMESTAMP('2017-07-05 18:18:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungseingang',10,0,0,TO_TIMESTAMP('2017-07-05 18:18:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:19:11.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551052,0,540442,540790,546323,TO_TIMESTAMP('2017-07-05 18:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gültig',20,0,0,TO_TIMESTAMP('2017-07-05 18:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:19:24.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551030,0,540442,540790,546324,TO_TIMESTAMP('2017-07-05 18:19:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',30,0,0,TO_TIMESTAMP('2017-07-05 18:19:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:20:28.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550768,0,540442,540789,546325,TO_TIMESTAMP('2017-07-05 18:20:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Belegdatum',20,0,0,TO_TIMESTAMP('2017-07-05 18:20:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:20:43.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540455,540791,TO_TIMESTAMP('2017-07-05 18:20:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-07-05 18:20:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:21:00.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550773,0,540442,540791,546326,TO_TIMESTAMP('2017-07-05 18:21:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-05 18:21:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:21:11.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550772,0,540442,540791,546327,TO_TIMESTAMP('2017-07-05 18:21:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-05 18:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:21:51.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540454,540792,TO_TIMESTAMP('2017-07-05 18:21:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-07-05 18:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:21:54.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2017-07-05 18:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540786
;

-- 2017-07-05T18:22:16.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550796,0,540442,540792,546328,TO_TIMESTAMP('2017-07-05 18:22:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-07-05 18:22:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:23:48.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551083,0,540442,540792,546329,TO_TIMESTAMP('2017-07-05 18:23:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl Transaktionen',20,0,0,TO_TIMESTAMP('2017-07-05 18:23:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:23:53.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2017-07-05 18:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546329
;

-- 2017-07-05T18:24:12.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551051,0,540442,540792,546330,TO_TIMESTAMP('2017-07-05 18:24:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesamtbetrag',5,0,0,TO_TIMESTAMP('2017-07-05 18:24:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:24:30.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-05 18:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546329
;

-- 2017-07-05T18:24:33.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-05 18:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546330
;

-- 2017-07-05T18:24:39.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-05 18:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546328
;

-- 2017-07-05T18:26:02.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558526,0,540442,540789,546331,TO_TIMESTAMP('2017-07-05 18:26:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Datentyp',30,0,0,TO_TIMESTAMP('2017-07-05 18:26:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:26:39.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546239
;

-- 2017-07-05T18:26:39.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546240
;

-- 2017-07-05T18:26:39.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546241
;

-- 2017-07-05T18:26:39.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546242
;

-- 2017-07-05T18:26:39.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546243
;

-- 2017-07-05T18:26:39.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546244
;

-- 2017-07-05T18:26:39.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546245
;

-- 2017-07-05T18:26:39.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546246
;

-- 2017-07-05T18:26:43.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546247
;

-- 2017-07-05T18:26:43.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546248
;

-- 2017-07-05T18:26:43.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546249
;

-- 2017-07-05T18:26:43.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546250
;

-- 2017-07-05T18:26:47.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540786
;

-- 2017-07-05T18:33:15.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546321
;

-- 2017-07-05T18:33:15.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546325
;

-- 2017-07-05T18:33:15.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546331
;

-- 2017-07-05T18:33:15.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546329
;

-- 2017-07-05T18:33:15.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546330
;

-- 2017-07-05T18:33:15.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546328
;

-- 2017-07-05T18:33:15.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546322
;

-- 2017-07-05T18:33:15.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546323
;

-- 2017-07-05T18:33:15.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546324
;

-- 2017-07-05T18:33:15.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546326
;

-- 2017-07-05T18:33:15.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-07-05 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546327
;

-- 2017-07-05T18:33:25.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-05 18:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546321
;

-- 2017-07-05T18:33:25.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-05 18:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546325
;

-- 2017-07-05T18:33:25.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-05 18:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546331
;

-- 2017-07-05T18:47:36.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-05 18:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546252
;

-- 2017-07-05T18:47:54.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-05 18:47:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546262
;

-- 2017-07-05T18:47:57.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-05 18:47:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546269
;

-- 2017-07-05T18:48:04.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-05 18:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546271
;

-- 2017-07-05T18:48:27.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-05 18:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546257
;

-- 2017-07-05T18:48:31.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-07-05 18:48:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546258
;

-- 2017-07-05T18:48:35.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-07-05 18:48:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546259
;

-- 2017-07-05T18:49:09.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-07-05 18:49:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546260
;

-- 2017-07-05T18:49:22.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-07-05 18:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546276
;

-- 2017-07-05T18:49:30.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-07-05 18:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546275
;

-- 2017-07-05T18:51:05.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540443,540335,TO_TIMESTAMP('2017-07-05 18:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-05 18:51:05','YYYY-MM-DD HH24:MI:SS'),100,'org')
;

-- 2017-07-05T18:51:05.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540335 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-05T18:51:08.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540458,540335,TO_TIMESTAMP('2017-07-05 18:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 18:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:51:25.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540458,540793,TO_TIMESTAMP('2017-07-05 18:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2017-07-05 18:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:51:39.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550793,0,540443,540793,546332,TO_TIMESTAMP('2017-07-05 18:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-05 18:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:51:52.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550787,0,540443,540793,546333,TO_TIMESTAMP('2017-07-05 18:51:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-05 18:51:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T18:52:10.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Organisation',Updated=TO_TIMESTAMP('2017-07-05 18:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540335
;

-- 2017-07-05T18:53:24.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540443,540336,TO_TIMESTAMP('2017-07-05 18:53:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','Fehler',30,TO_TIMESTAMP('2017-07-05 18:53:24','YYYY-MM-DD HH24:MI:SS'),100,'error')
;

-- 2017-07-05T18:53:24.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540336 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-05T19:16:24.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546255
;

-- 2017-07-05T19:16:28.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546253
;

-- 2017-07-05T19:17:29.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546251
;

-- 2017-07-05T19:17:29.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546254
;

-- 2017-07-05T19:17:29.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546256
;

-- 2017-07-05T19:17:29.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546261
;

-- 2017-07-05T19:17:29.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546263
;

-- 2017-07-05T19:17:29.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546264
;

-- 2017-07-05T19:17:29.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546265
;

-- 2017-07-05T19:17:29.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546266
;

-- 2017-07-05T19:17:29.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546267
;

-- 2017-07-05T19:17:29.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546268
;

-- 2017-07-05T19:17:29.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546270
;

-- 2017-07-05T19:17:29.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546272
;

-- 2017-07-05T19:17:29.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546273
;

-- 2017-07-05T19:17:29.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546274
;

-- 2017-07-05T19:17:29.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546277
;

-- 2017-07-05T19:17:29.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546278
;

-- 2017-07-05T19:17:29.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546279
;

-- 2017-07-05T19:17:29.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546280
;

-- 2017-07-05T19:17:29.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546281
;

-- 2017-07-05T19:17:52.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Sonstiges', Value='Rest',Updated=TO_TIMESTAMP('2017-07-05 19:17:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540336
;

-- 2017-07-05T19:17:56.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540459,540336,TO_TIMESTAMP('2017-07-05 19:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-05 19:17:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:18:19.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540459,540794,TO_TIMESTAMP('2017-07-05 19:18:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','rest',10,TO_TIMESTAMP('2017-07-05 19:18:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:18:43.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551033,0,540443,540794,546334,TO_TIMESTAMP('2017-07-05 19:18:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2017-07-05 19:18:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:18:56.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551043,0,540443,540794,546335,TO_TIMESTAMP('2017-07-05 19:18:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Importstatus',20,0,0,TO_TIMESTAMP('2017-07-05 19:18:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:19:11.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550855,0,540443,540794,546336,TO_TIMESTAMP('2017-07-05 19:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Import-Fehler',30,0,0,TO_TIMESTAMP('2017-07-05 19:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:19:29.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550792,0,540443,540794,546337,TO_TIMESTAMP('2017-07-05 19:19:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ESR Referenznummer',40,0,0,TO_TIMESTAMP('2017-07-05 19:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:19:56.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551870,0,540443,540794,546338,TO_TIMESTAMP('2017-07-05 19:19:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Extern erstellte ESR Referenz',50,0,0,TO_TIMESTAMP('2017-07-05 19:19:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:20:10.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550791,0,540443,540794,546339,TO_TIMESTAMP('2017-07-05 19:20:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Post-Teilnehmernummer',60,0,0,TO_TIMESTAMP('2017-07-05 19:20:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:20:27.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551172,0,540443,540794,546340,TO_TIMESTAMP('2017-07-05 19:20:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bankverbindung',70,0,0,TO_TIMESTAMP('2017-07-05 19:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:20:39.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550857,0,540443,540794,546341,TO_TIMESTAMP('2017-07-05 19:20:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Organisation',80,0,0,TO_TIMESTAMP('2017-07-05 19:20:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:20:51.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550789,0,540443,540794,546342,TO_TIMESTAMP('2017-07-05 19:20:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ESR Sektion',90,0,0,TO_TIMESTAMP('2017-07-05 19:20:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:21:16.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550788,0,540443,540794,546343,TO_TIMESTAMP('2017-07-05 19:21:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner-Schlüssel',100,0,0,TO_TIMESTAMP('2017-07-05 19:21:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:21:34.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551900,0,540443,540794,546344,TO_TIMESTAMP('2017-07-05 19:21:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ESR Rechnungsnummer',110,0,0,TO_TIMESTAMP('2017-07-05 19:21:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:21:50.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551883,0,540443,540794,546345,TO_TIMESTAMP('2017-07-05 19:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rechnung manuell zugeordnet',120,0,0,TO_TIMESTAMP('2017-07-05 19:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:22:09.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551848,0,540443,540794,546346,TO_TIMESTAMP('2017-07-05 19:22:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bereits zugeordnet',130,0,0,TO_TIMESTAMP('2017-07-05 19:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:22:22.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550795,0,540443,540794,546347,TO_TIMESTAMP('2017-07-05 19:22:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',140,0,0,TO_TIMESTAMP('2017-07-05 19:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:22:32.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551032,0,540443,540794,546348,TO_TIMESTAMP('2017-07-05 19:22:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlung',150,0,0,TO_TIMESTAMP('2017-07-05 19:22:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:22:48.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551031,0,540443,540794,546349,TO_TIMESTAMP('2017-07-05 19:22:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gültig',160,0,0,TO_TIMESTAMP('2017-07-05 19:22:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:23:08.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550785,0,540443,540794,546350,TO_TIMESTAMP('2017-07-05 19:23:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Importierte ESR Zeile',170,0,0,TO_TIMESTAMP('2017-07-05 19:23:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-05T19:23:23.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558657,0,540443,540794,546351,TO_TIMESTAMP('2017-07-05 19:23:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zuordnungsfehler',180,0,0,TO_TIMESTAMP('2017-07-05 19:23:23','YYYY-MM-DD HH24:MI:SS'),100)
;

