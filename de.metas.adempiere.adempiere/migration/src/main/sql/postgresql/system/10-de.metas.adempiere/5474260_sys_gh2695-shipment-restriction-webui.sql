-- 2017-10-12T16:48:50.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Liefer Einschränkungen',Updated=TO_TIMESTAMP('2017-10-12 16:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540370
;

-- 2017-10-12T16:48:50.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Liefer Einschränkungen',Updated=TO_TIMESTAMP('2017-10-12 16:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540967
;

-- 2017-10-12T16:51:47.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Liefer Einschränkungen',Updated=TO_TIMESTAMP('2017-10-12 16:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540882
;

-- 2017-10-12T16:54:48.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540970,0,540370,TO_TIMESTAMP('2017-10-12 16:54:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','_Liefer Einschränkung','Y','N','N','N','N','Liefer Einschränkung',TO_TIMESTAMP('2017-10-12 16:54:48','YYYY-MM-DD HH24:MI:SS'),100,'Liefer Einschränkung')
;

-- 2017-10-12T16:54:48.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540970 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-10-12T16:54:48.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540970, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540970)
;

-- 2017-10-12T16:54:48.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=500221 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=346 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53132 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=180 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=494 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=444 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540032 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540582 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540596 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540382 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:48.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:52.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2017-10-12 16:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540970
;

-- 2017-10-12T16:54:57.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2017-10-12T16:54:57.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2017-10-12T16:55:06.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Lieferung Einschränkung',Updated=TO_TIMESTAMP('2017-10-12 16:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540970
;

-- 2017-10-12T16:55:16.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Lieferung Einschränkung',Updated=TO_TIMESTAMP('2017-10-12 16:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540370
;

-- 2017-10-12T16:55:16.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Lieferung Einschränkung',Updated=TO_TIMESTAMP('2017-10-12 16:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540967
;

-- 2017-10-12T16:55:16.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Lieferung Einschränkung',Updated=TO_TIMESTAMP('2017-10-12 16:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540970
;

-- 2017-10-12T16:55:26.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferung Einschränkung',Updated=TO_TIMESTAMP('2017-10-12 16:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540882
;

-- 2017-10-12T16:55:38.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2017-10-12 16:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000019
;

-- 2017-10-12T16:59:37.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540882,540519,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-10-12T16:59:37.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540519 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-10-12T16:59:37.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540693,540519,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540694,540519,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540693,541207,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560380,0,540882,541207,549075,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartners für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','Y','N','Rechnungspartner',10,10,0,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560376,0,540882,541207,549076,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',20,20,0,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560381,0,540882,541207,549077,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Delivery stop',30,30,0,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560378,0,540882,541207,549078,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Source document (table)',40,40,0,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T16:59:37.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560379,0,540882,541207,549079,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Source document',50,50,0,TO_TIMESTAMP('2017-10-12 16:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T17:16:56.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560374,0,540882,541207,549080,'F',TO_TIMESTAMP('2017-10-12 17:16:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',60,0,0,TO_TIMESTAMP('2017-10-12 17:16:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T17:17:04.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560375,0,540882,541207,549081,'F',TO_TIMESTAMP('2017-10-12 17:17:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',70,0,0,TO_TIMESTAMP('2017-10-12 17:17:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T17:19:04.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-10-12 17:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549078
;

-- 2017-10-12T17:19:04.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-10-12 17:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549079
;

-- 2017-10-12T17:19:04.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-12 17:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549077
;

-- 2017-10-12T17:19:04.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-12 17:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549076
;

-- 2017-10-12T17:19:04.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-10-12 17:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549081
;

-- 2017-10-12T17:19:32.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-10-12 17:19:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549078
;

-- 2017-10-12T17:19:34.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-10-12 17:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549079
;

-- 2017-10-12T17:19:36.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-10-12 17:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549077
;

-- 2017-10-12T17:19:37.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-12 17:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549076
;

-- 2017-10-12T17:19:39.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-10-12 17:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549081
;

-- 2017-10-12T17:19:43.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-10-12 17:19:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549080
;

-- 2017-10-12T20:37:03.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540694,541208,TO_TIMESTAMP('2017-10-12 20:37:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-10-12 20:37:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:37:07.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540694,541209,TO_TIMESTAMP('2017-10-12 20:37:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-10-12 20:37:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:37:22.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560376,0,540882,541208,549082,'F',TO_TIMESTAMP('2017-10-12 20:37:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-10-12 20:37:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:37:34.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560381,0,540882,541208,549083,'F',TO_TIMESTAMP('2017-10-12 20:37:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferstopp',20,0,0,TO_TIMESTAMP('2017-10-12 20:37:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:38:02.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560375,0,540882,541209,549084,'F',TO_TIMESTAMP('2017-10-12 20:38:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-10-12 20:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:38:10.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560374,0,540882,541209,549085,'F',TO_TIMESTAMP('2017-10-12 20:38:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-10-12 20:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:38:49.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549077
;

-- 2017-10-12T20:38:49.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549076
;

-- 2017-10-12T20:38:49.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549080
;

-- 2017-10-12T20:38:49.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549081
;

-- 2017-10-12T20:39:02.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-12 20:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549083
;

-- 2017-10-12T20:39:02.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-12 20:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549082
;

-- 2017-10-12T20:39:02.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-10-12 20:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549084
;

-- 2017-10-12T20:39:31.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-10-12 20:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549075
;

-- 2017-10-12T20:39:31.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-10-12 20:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549078
;

-- 2017-10-12T20:39:31.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-10-12 20:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549079
;

-- 2017-10-12T20:39:31.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-10-12 20:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549082
;

-- 2017-10-12T20:39:31.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-10-12 20:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549084
;

-- 2017-10-12T20:40:04.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540882,540520,TO_TIMESTAMP('2017-10-12 20:40:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-10-12 20:40:04','YYYY-MM-DD HH24:MI:SS'),100,'table')
;

-- 2017-10-12T20:40:04.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540520 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-10-12T20:40:10.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540520
;

-- 2017-10-12T20:40:10.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540520
;

-- 2017-10-12T20:40:21.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540693,541210,TO_TIMESTAMP('2017-10-12 20:40:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','data',20,TO_TIMESTAMP('2017-10-12 20:40:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-12T20:40:40.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541210, SeqNo=10,Updated=TO_TIMESTAMP('2017-10-12 20:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549078
;

-- 2017-10-12T20:40:46.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541210, SeqNo=20,Updated=TO_TIMESTAMP('2017-10-12 20:40:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549079
;

-- 2017-10-12T20:41:39.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-10-12 20:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549084
;

-- 2017-10-12T20:41:42.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-10-12 20:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549085
;

-- 2017-10-12T20:42:05.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferstopp',Updated=TO_TIMESTAMP('2017-10-12 20:42:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560381
;

-- 2017-10-12T20:42:29.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Quelle',Updated=TO_TIMESTAMP('2017-10-12 20:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560378
;

-- 2017-10-12T20:42:35.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Datensatz',Updated=TO_TIMESTAMP('2017-10-12 20:42:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560379
;

-- 2017-10-12T20:42:39.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tabelle',Updated=TO_TIMESTAMP('2017-10-12 20:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560378
;

-- 2017-10-12T20:42:51.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferung Einschränkung',Updated=TO_TIMESTAMP('2017-10-12 20:42:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560377
;

-- 2017-10-12T20:43:01.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:43:01','YYYY-MM-DD HH24:MI:SS') WHERE AD_Field_ID=560380 AND AD_Language='de_CH'
;

-- 2017-10-12T20:43:14.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:43:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Business Partner',Description='',Help='' WHERE AD_Field_ID=560380 AND AD_Language='en_US'
;

-- 2017-10-12T20:43:27.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:43:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=560376 AND AD_Language='en_US'
;

-- 2017-10-12T20:43:33.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:43:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=560381 AND AD_Language='en_US'
;

-- 2017-10-12T20:43:42.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:43:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Table' WHERE AD_Field_ID=560378 AND AD_Language='en_US'
;

-- 2017-10-12T20:43:52.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:43:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Record' WHERE AD_Field_ID=560379 AND AD_Language='en_US'
;

-- 2017-10-12T20:44:03.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:44:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=560374 AND AD_Language='en_US'
;

-- 2017-10-12T20:44:15.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:44:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=560375 AND AD_Language='en_US'
;

-- 2017-10-12T20:44:26.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 20:44:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment Constraint' WHERE AD_Field_ID=560377 AND AD_Language='en_US'
;

