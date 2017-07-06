-- 2017-07-06T16:22:53.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540892,0,540163,TO_TIMESTAMP('2017-07-06 16:22:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','_Asynchrone Verarbeitungswarteschlange','Y','N','N','N','N','Asynchrone Verarbeitungswarteschlange',TO_TIMESTAMP('2017-07-06 16:22:53','YYYY-MM-DD HH24:MI:SS'),100,'Asynchrone Verarbeitungswarteschlage')
;

-- 2017-07-06T16:22:53.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540892 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-07-06T16:22:53.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540892, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540892)
;

-- 2017-07-06T16:22:54.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2017-07-06T16:22:54.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-07-06T16:23:03.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2017-07-06T16:37:13.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540456,540339,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-06T16:37:13.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540339 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T16:37:13.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540463,540339,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540464,540339,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540463,540801,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554265,0,540456,540801,546405,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Processor',0,10,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551111,0,540456,540801,546406,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551114,0,540456,540801,546407,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551115,0,540456,540801,546408,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitspaket-ID',30,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551105,0,540456,540801,546409,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Queue Block',40,30,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551112,0,540456,540801,546410,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.','The Priority indicates the importance of this request.','Y','N','Y','Y','N','Priority',50,40,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551130,0,540456,540801,546411,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.','Y','N','Y','Y','N','Bereit zur Verarbeitung',60,50,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551509,0,540456,540801,546412,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zuletzt Übersprungen um',70,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551894,0,540456,540801,546413,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Skip Timeout (millis)',80,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551892,0,540456,540801,546414,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Skipped Last Reason',90,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551891,0,540456,540801,546415,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Skipped First Time',100,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551890,0,540456,540801,546416,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Skipped Count',110,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553927,0,540456,540801,546417,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','In Verarbeitung',120,60,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551110,0,540456,540801,546418,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',130,70,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551108,0,540456,540801,546419,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','Y','Y','N','Fehler',140,80,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:13.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556202,0,540456,540801,546420,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Fehler zur Kentnis genommen',150,0,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555027,0,540456,540801,546421,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Async Batch',160,90,0,TO_TIMESTAMP('2017-07-06 16:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556697,0,540456,540801,546422,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Batch Enqueued Count',170,100,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551109,0,540456,540801,546423,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Fehlermeldung',180,110,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551893,0,540456,540801,546424,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Automatically created or manually entered System Issue','System Issues are created to speed up the resolution of any system related issues (potential bugs).  If enabled, they are automatically reported to Adempiere.  No data or confidential information is transferred.','Y','N','Y','N','N','System-Problem',190,0,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554266,0,540456,540801,546425,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Last StartTime',200,120,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554267,0,540456,540801,546426,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Last End Time',210,130,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554268,0,540456,540801,546427,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Last Duration (ms)',220,140,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556189,0,540456,540801,546428,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','Y','N','Ansprechpartner',230,150,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556188,0,540456,540801,546429,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role','The Role determines security and access a user who has this Role will have in the System.','Y','N','Y','Y','N','Rolle',240,160,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556526,0,540456,540801,546430,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.','Y','N','Y','Y','N','Betreuer',250,170,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540455,540340,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-06T16:37:14.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540340 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T16:37:14.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540465,540340,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540465,540802,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551098,0,540455,540802,546431,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551101,0,540455,540802,546432,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551094,0,540455,540802,546433,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Queue Block',0,30,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551797,0,540455,540802,546434,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Processor',0,40,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551099,0,540455,540802,546435,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Erstellt durch Prozess-Instanz',0,50,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540457,540341,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-06T16:37:14.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540341 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T16:37:14.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540466,540341,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540466,540803,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551125,0,540457,540803,546436,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551126,0,540457,540803,546437,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551127,0,540457,540803,546438,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Queue',0,30,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551121,0,540457,540803,546439,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,40,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551120,0,540457,540803,546440,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','Datensatz-ID',0,50,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555431,0,540457,540803,546441,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Arbeitspaket mit einem Element, das den selben Datensatz referenziert, noch nicht verarbeitet wurde und somit die Verarbeitung dieses Elements verhindert','Y','N','N','Y','N','Ältestes nicht verarb. Vorgänger-Paket',0,60,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540661,540342,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-06T16:37:14.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540342 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T16:37:14.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540467,540342,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:14.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540467,540804,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555429,0,540661,540804,546442,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Queue',0,10,0,TO_TIMESTAMP('2017-07-06 16:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555427,0,540661,540804,546443,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Workpackage audit/log table',0,20,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555430,0,540661,540804,546444,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,30,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555428,0,540661,540804,546445,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Textual Informational, Menu or Error Message','The Message Text indicates the message that will display ','Y','N','N','Y','N','Message Text',0,40,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540673,540343,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-06T16:37:15.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540343 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T16:37:15.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540468,540343,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540468,540805,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555740,0,540673,540805,546446,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Parameter Name',0,10,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555736,0,540673,540805,546447,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Systemreferenz und Validierung','Die Referenz kann über den Datentyp, eine Liste oder eine Tabelle erfolgen.','Y','N','N','Y','N','Referenz',0,20,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555737,0,540673,540805,546448,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process String',0,30,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555738,0,540673,540805,546449,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process Number',0,40,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555739,0,540673,540805,546450,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process Date',0,50,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540672,540344,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-06T16:37:15.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540344 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T16:37:15.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540469,540344,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540469,540806,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555701,0,540672,540806,546451,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,10,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555704,0,540672,540806,546452,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Parameter Name',0,20,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555697,0,540672,540806,546453,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process String',0,30,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555698,0,540672,540806,546454,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process String To',0,40,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555702,0,540672,540806,546455,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process Number',0,50,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555703,0,540672,540806,546456,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process Number To',0,60,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555699,0,540672,540806,546457,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process Date',0,70,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555700,0,540672,540806,546458,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess-Parameter','Y','N','N','Y','N','Process Date To',0,80,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555705,0,540672,540806,546459,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Information','The Information displays data from the source document line.','Y','N','N','Y','N','Info',0,90,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555706,0,540672,540806,546460,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Info To',0,100,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T16:37:15.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555696,0,540672,540806,546461,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses','Y','N','N','Y','N','Prozess-Instanz',0,110,0,TO_TIMESTAMP('2017-07-06 16:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:46:39.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540463,540807,TO_TIMESTAMP('2017-07-06 18:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','deafult',10,'primary',TO_TIMESTAMP('2017-07-06 18:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:46:52.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90, UIStyle='',Updated=TO_TIMESTAMP('2017-07-06 18:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540801
;

-- 2017-07-06T18:48:56.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551115,0,540456,540807,546462,TO_TIMESTAMP('2017-07-06 18:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitspaket ID',10,0,0,TO_TIMESTAMP('2017-07-06 18:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:49:09.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551105,0,540456,540807,546463,TO_TIMESTAMP('2017-07-06 18:49:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Queue Block',20,0,0,TO_TIMESTAMP('2017-07-06 18:49:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:49:27.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554265,0,540456,540807,546464,TO_TIMESTAMP('2017-07-06 18:49:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Work Package Processor',30,0,0,TO_TIMESTAMP('2017-07-06 18:49:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:49:49.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551112,0,540456,540807,546465,TO_TIMESTAMP('2017-07-06 18:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Priorität',40,0,0,TO_TIMESTAMP('2017-07-06 18:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:50:22.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540463,540808,TO_TIMESTAMP('2017-07-06 18:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','error',20,TO_TIMESTAMP('2017-07-06 18:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:50:28.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='default',Updated=TO_TIMESTAMP('2017-07-06 18:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540807
;

-- 2017-07-06T18:54:19.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551891,0,540456,540808,546466,TO_TIMESTAMP('2017-07-06 18:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Skipped First Time',10,0,0,TO_TIMESTAMP('2017-07-06 18:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:54:55.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551509,0,540456,540808,546467,TO_TIMESTAMP('2017-07-06 18:54:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zuletzt übersprungen',20,0,0,TO_TIMESTAMP('2017-07-06 18:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:55:04.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Zuerst übersprungen',Updated=TO_TIMESTAMP('2017-07-06 18:55:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546466
;

-- 2017-07-06T18:55:33.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551892,0,540456,540808,546468,TO_TIMESTAMP('2017-07-06 18:55:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Übersprungen Grund',30,0,0,TO_TIMESTAMP('2017-07-06 18:55:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:55:43.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540464,540809,TO_TIMESTAMP('2017-07-06 18:55:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-07-06 18:55:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:55:59.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551110,0,540456,540809,546469,TO_TIMESTAMP('2017-07-06 18:55:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2017-07-06 18:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:56:20.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551130,0,540456,540809,546470,TO_TIMESTAMP('2017-07-06 18:56:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bereit zur Verarbeitung',20,0,0,TO_TIMESTAMP('2017-07-06 18:56:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:56:35.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553927,0,540456,540809,546471,TO_TIMESTAMP('2017-07-06 18:56:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','In Verarbeitung',30,0,0,TO_TIMESTAMP('2017-07-06 18:56:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:56:44.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551108,0,540456,540809,546472,TO_TIMESTAMP('2017-07-06 18:56:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Fehler',40,0,0,TO_TIMESTAMP('2017-07-06 18:56:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:57:00.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540464,540810,TO_TIMESTAMP('2017-07-06 18:57:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','error',20,TO_TIMESTAMP('2017-07-06 18:57:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:57:12.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551109,0,540456,540810,546473,TO_TIMESTAMP('2017-07-06 18:57:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Fehlermeldung',10,0,0,TO_TIMESTAMP('2017-07-06 18:57:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:57:18.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540464,540811,TO_TIMESTAMP('2017-07-06 18:57:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-07-06 18:57:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:57:29.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551114,0,540456,540811,546474,TO_TIMESTAMP('2017-07-06 18:57:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-06 18:57:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:57:38.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551111,0,540456,540811,546475,TO_TIMESTAMP('2017-07-06 18:57:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-06 18:57:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:58:28.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540456,540345,TO_TIMESTAMP('2017-07-06 18:58:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-06 18:58:28','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-07-06T18:58:28.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540345 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-06T18:58:31.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540470,540345,TO_TIMESTAMP('2017-07-06 18:58:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-06 18:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-06T18:58:58.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546405
;

-- 2017-07-06T18:58:58.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546406
;

-- 2017-07-06T18:58:59.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546407
;

-- 2017-07-06T18:58:59.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546408
;

-- 2017-07-06T18:58:59.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546409
;

-- 2017-07-06T18:58:59.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546410
;

-- 2017-07-06T18:58:59.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546411
;

-- 2017-07-06T18:58:59.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546412
;

-- 2017-07-06T18:59:10.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546414
;

-- 2017-07-06T18:59:10.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546415
;

-- 2017-07-06T18:59:10.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546417
;

-- 2017-07-06T18:59:10.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546418
;

-- 2017-07-06T18:59:10.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546419
;

-- 2017-07-06T18:59:20.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546423
;

-- 2017-07-06T18:59:34.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540470, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-06 18:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540801
;

-- 2017-07-06T18:59:46.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546413
;

-- 2017-07-06T18:59:46.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546416
;

-- 2017-07-06T18:59:47.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546420
;

-- 2017-07-06T18:59:47.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546421
;

-- 2017-07-06T18:59:48.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546422
;

-- 2017-07-06T18:59:48.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546424
;

-- 2017-07-06T18:59:49.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546425
;

-- 2017-07-06T18:59:49.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546426
;

-- 2017-07-06T18:59:49.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546427
;

-- 2017-07-06T18:59:50.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546428
;

-- 2017-07-06T18:59:51.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546429
;

-- 2017-07-06T18:59:53.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-07-06 18:59:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546430
;

-- 2017-07-06T19:00:01.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-07-06 19:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540801
;

-- 2017-07-06T19:04:06.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546428
;

-- 2017-07-06T19:04:06.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546421
;

-- 2017-07-06T19:04:06.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546422
;

-- 2017-07-06T19:04:06.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546430
;

-- 2017-07-06T19:04:06.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546427
;

-- 2017-07-06T19:04:06.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546426
;

-- 2017-07-06T19:04:06.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546425
;

-- 2017-07-06T19:04:06.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546429
;

-- 2017-07-06T19:04:06.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546462
;

-- 2017-07-06T19:04:06.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546464
;

-- 2017-07-06T19:04:06.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546463
;

-- 2017-07-06T19:04:06.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546465
;

-- 2017-07-06T19:04:06.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546469
;

-- 2017-07-06T19:04:06.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546470
;

-- 2017-07-06T19:04:06.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546471
;

-- 2017-07-06T19:04:06.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546472
;

-- 2017-07-06T19:04:06.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546473
;

-- 2017-07-06T19:04:06.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546466
;

-- 2017-07-06T19:04:06.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546467
;

-- 2017-07-06T19:04:06.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546468
;

-- 2017-07-06T19:04:06.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546474
;

-- 2017-07-06T19:04:06.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-07-06 19:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546475
;

-- 2017-07-06T19:04:18.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-06 19:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546462
;

-- 2017-07-06T19:04:18.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-06 19:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546464
;

-- 2017-07-06T19:04:28.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-06 19:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546465
;

-- 2017-07-06T19:05:07.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-07-06 19:05:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546474
;

-- 2017-07-06T19:05:12.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-07-06 19:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546475
;
