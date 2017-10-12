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

