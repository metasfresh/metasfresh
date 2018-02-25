-- 2018-02-25T12:40:57.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541046,0,540418,TO_TIMESTAMP('2018-02-25 12:40:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','_Kommissionierung_Konfiguration','Y','N','N','N','N','Kommissionierung Konfiguration',TO_TIMESTAMP('2018-02-25 12:40:57','YYYY-MM-DD HH24:MI:SS'),100,'Kommissionierung Konfiguration')
;

-- 2018-02-25T12:40:57.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541046 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-02-25T12:40:57.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541046, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541046)
;

-- 2018-02-25T12:40:57.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=114 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=108 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=115 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53225 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53226 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541046 AND AD_Tree_ID=10
;

-- 2018-02-25T12:41:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541046 AND AD_Tree_ID=10
;

-- 2018-02-25T12:41:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540991 AND AD_Tree_ID=10
;

-- 2018-02-25T12:41:22.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:41:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Picking Config',WEBUI_NameBrowse='Picking Config' WHERE AD_Menu_ID=541046 AND AD_Language='en_US'
;

-- 2018-02-25T12:41:34.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540991
;

-- 2018-02-25T12:41:34.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=540991
;

-- 2018-02-25T12:41:34.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540991 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-02-25T12:43:25.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540382,Updated=TO_TIMESTAMP('2018-02-25 12:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540873
;

-- 2018-02-25T12:44:04.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissionierung Konfiguration',Updated=TO_TIMESTAMP('2018-02-25 12:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541049
;

-- 2018-02-25T12:54:27.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541049,540674,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-25T12:54:27.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540674 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-25T12:54:27.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540900,540674,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540901,540674,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540900,541497,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563026,0,541049,541497,551154,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563027,0,541049,541497,551155,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563028,0,541049,541497,551156,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',30,30,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563030,0,541049,541497,551157,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Picking terminal view profile',40,40,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563031,0,541049,541497,551158,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsAllowOverdelivery',50,50,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541049,540674,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-25T12:54:27.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540674 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-25T12:54:27.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540900,540674,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540901,540674,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540900,541497,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563026,0,541049,541497,551154,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563027,0,541049,541497,551155,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563028,0,541049,541497,551156,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',30,30,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563030,0,541049,541497,551157,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Picking terminal view profile',40,40,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:54:27.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563031,0,541049,541497,551158,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsAllowOverdelivery',50,50,0,TO_TIMESTAMP('2018-02-25 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:56:04.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540901,541498,TO_TIMESTAMP('2018-02-25 12:56:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-02-25 12:56:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:56:07.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540901,541499,TO_TIMESTAMP('2018-02-25 12:56:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-02-25 12:56:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-25T12:56:25.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541498, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-25 12:56:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551158
;

-- 2018-02-25T12:56:38.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541498, SeqNo=20,Updated=TO_TIMESTAMP('2018-02-25 12:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551156
;

-- 2018-02-25T12:56:47.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541499, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-25 12:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551155
;

-- 2018-02-25T12:56:54.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541499, SeqNo=20,Updated=TO_TIMESTAMP('2018-02-25 12:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551154
;

-- 2018-02-25T12:57:08.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-02-25 12:57:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551156
;

-- 2018-02-25T12:57:11.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-02-25 12:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551158
;

-- 2018-02-25T12:57:22.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-25 12:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551154
;

-- 2018-02-25T12:57:22.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-02-25 12:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551157
;

-- 2018-02-25T12:57:22.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-02-25 12:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551156
;

-- 2018-02-25T12:57:22.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-02-25 12:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551158
;

-- 2018-02-25T12:57:22.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-02-25 12:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551155
;

-- 2018-02-25T12:57:41.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Überlieferung zulassen',Updated=TO_TIMESTAMP('2018-02-25 12:57:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563031
;

-- 2018-02-25T12:57:50.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierung Konfiguration',Updated=TO_TIMESTAMP('2018-02-25 12:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563029
;

-- 2018-02-25T12:58:13.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommisisonierung Art',Updated=TO_TIMESTAMP('2018-02-25 12:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563030
;

-- 2018-02-25T12:58:26.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:58:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=563026 AND AD_Language='en_US'
;

-- 2018-02-25T12:58:38.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:58:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=563027 AND AD_Language='en_US'
;

-- 2018-02-25T12:58:49.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:58:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=563028 AND AD_Language='en_US'
;

-- 2018-02-25T12:59:08.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:59:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Picking Group Type' WHERE AD_Field_ID=563030 AND AD_Language='en_US'
;

-- 2018-02-25T12:59:17.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommisisonierung Gruppierung',Updated=TO_TIMESTAMP('2018-02-25 12:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563030
;

-- 2018-02-25T12:59:27.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:59:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Allow Overdelivery' WHERE AD_Field_ID=563031 AND AD_Language='en_US'
;

-- 2018-02-25T12:59:36.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:59:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Picking Configuration' WHERE AD_Field_ID=563029 AND AD_Language='en_US'
;

-- 2018-02-25T12:59:51.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2018-02-25 12:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551157
;

-- 2018-02-25T12:59:51.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2018-02-25 12:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551156
;

-- 2018-02-25T12:59:51.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2018-02-25 12:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551158
;

-- 2018-02-25T12:59:51.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2018-02-25 12:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551155
;

-- 2018-02-25T13:00:32.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-02-25 13:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551154
;

-- 2018-02-25T13:00:35.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-02-25 13:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551155
;

