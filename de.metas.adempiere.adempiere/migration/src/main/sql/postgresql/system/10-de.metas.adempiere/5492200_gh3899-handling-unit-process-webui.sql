-- 2018-04-28T13:05:00.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541081,0,540224,TO_TIMESTAMP('2018-04-28 13:05:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','540224 (Todo: Set Internal Name for UI testing)','Y','N','N','N','N','Handling Unit Process',TO_TIMESTAMP('2018-04-28 13:05:00','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit Prozesse')
;

-- 2018-04-28T13:05:00.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541081 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-04-28T13:05:00.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541081, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541081)
;

-- 2018-04-28T13:05:01.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540989 AND AD_Tree_ID=10
;

-- 2018-04-28T13:05:01.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- 2018-04-28T13:05:01.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540960 AND AD_Tree_ID=10
;

-- 2018-04-28T13:05:01.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540988 AND AD_Tree_ID=10
;

-- 2018-04-28T13:05:01.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541081 AND AD_Tree_ID=10
;

-- 2018-04-28T13:05:12.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Handling Unit Prozesse',Updated=TO_TIMESTAMP('2018-04-28 13:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541081
;

-- 2018-04-28T13:05:18.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 13:05:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=541081 AND AD_Language='en_US'
;

-- 2018-04-28T13:08:28.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Handling Unit Prozess',Updated=TO_TIMESTAMP('2018-04-28 13:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540224
;

-- 2018-04-28T13:08:28.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Handling Unit Prozess',Updated=TO_TIMESTAMP('2018-04-28 13:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540555
;

-- 2018-04-28T13:08:28.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Handling Unit Prozess',Updated=TO_TIMESTAMP('2018-04-28 13:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541081
;

-- 2018-04-28T13:08:37.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 13:08:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Window_ID=540224 AND AD_Language='en_US'
;

-- 2018-04-28T13:08:53.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540605,540723,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-04-28T13:08:53.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540723 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-04-28T13:08:53.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540944,540723,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540945,540723,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540944,541558,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554554,0,540605,541558,551693,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',10,10,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554555,0,540605,541558,551694,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Handling Unit Process',0,20,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554552,0,540605,541558,551695,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',20,30,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554553,0,540605,541558,551696,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',30,40,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554557,0,540605,541558,551697,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','Y','N','Prozess',40,50,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554556,0,540605,541558,551698,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packvorschrift',50,60,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563612,0,540605,541558,551699,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.','Y','N','Y','Y','N','Als Benutzeraktion verfügbar machen',60,70,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563613,0,540605,541558,551700,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Nur auf Top-Level HU anwenden',70,80,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554558,0,540605,541558,551701,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Auf LUs anwenden',80,90,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554559,0,540605,541558,551702,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Auf TUs anwenden',90,100,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:08:53.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554560,0,540605,541558,551703,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Auf CUs anwenden',100,110,0,TO_TIMESTAMP('2018-04-28 13:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:13:04.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540555
;

-- 2018-04-28T13:13:04.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=540555
;

-- 2018-04-28T13:13:04.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540555 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-04-28T13:13:45.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540479 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540481 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540893 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540482 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540489 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540490 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540520 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540545 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540546 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540600 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540555 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540659 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:45.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540597 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540479 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540481 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540893 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540482 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540489 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540490 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540520 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540545 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540546 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540555 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540600 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540659 AND AD_Tree_ID=10
;

-- 2018-04-28T13:13:47.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540597 AND AD_Tree_ID=10
;
-- 2018-04-28T13:22:33.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540945,541559,TO_TIMESTAMP('2018-04-28 13:22:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-04-28 13:22:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:22:37.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540945,541560,TO_TIMESTAMP('2018-04-28 13:22:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-04-28 13:22:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:22:54.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541560, SeqNo=10,Updated=TO_TIMESTAMP('2018-04-28 13:22:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551696
;

-- 2018-04-28T13:23:02.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541560, SeqNo=20,Updated=TO_TIMESTAMP('2018-04-28 13:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551695
;

-- 2018-04-28T13:23:11.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541559, SeqNo=10,Updated=TO_TIMESTAMP('2018-04-28 13:23:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551693
;

-- 2018-04-28T13:23:31.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541559, SeqNo=20,Updated=TO_TIMESTAMP('2018-04-28 13:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551699
;

-- 2018-04-28T13:23:40.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541559, SeqNo=30,Updated=TO_TIMESTAMP('2018-04-28 13:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551703
;

-- 2018-04-28T13:23:47.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541559, SeqNo=40,Updated=TO_TIMESTAMP('2018-04-28 13:23:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551702
;

-- 2018-04-28T13:23:56.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541559, SeqNo=50,Updated=TO_TIMESTAMP('2018-04-28 13:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551701
;

-- 2018-04-28T13:24:04.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541559, SeqNo=60,Updated=TO_TIMESTAMP('2018-04-28 13:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551700
;

-- 2018-04-28T13:24:26.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540944,541561,TO_TIMESTAMP('2018-04-28 13:24:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','packing instruction',20,TO_TIMESTAMP('2018-04-28 13:24:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-28T13:25:01.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541561, SeqNo=10,Updated=TO_TIMESTAMP('2018-04-28 13:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551698
;

-- 2018-04-28T13:25:23.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551694
;

-- 2018-04-28T13:27:40.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 13:27:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Process',Description='',Help='' WHERE AD_Field_ID=554557 AND AD_Language='en_US'
;

-- 2018-04-28T13:27:57.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 13:27:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Instruction' WHERE AD_Field_ID=554556 AND AD_Language='en_US'
;

-- 2018-04-28T13:28:33.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 13:28:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Provide as User Action',Description='' WHERE AD_Field_ID=563612 AND AD_Language='en_US'
;

-- 2018-04-28T13:28:54.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 13:28:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Apply only to Top Level HU' WHERE AD_Field_ID=563613 AND AD_Language='en_US'
;

-- 2018-04-28T13:34:17.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551695
;

-- 2018-04-28T13:34:17.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551697
;

-- 2018-04-28T13:34:17.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551698
;

-- 2018-04-28T13:34:17.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551699
;

-- 2018-04-28T13:34:17.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551700
;

-- 2018-04-28T13:34:17.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551701
;

-- 2018-04-28T13:34:17.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551702
;

-- 2018-04-28T13:34:17.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551703
;

-- 2018-04-28T13:34:17.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551693
;

-- 2018-04-28T13:34:17.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-04-28 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551696
;

-- 2018-04-28T13:34:26.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2018-04-28 13:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551697
;

-- 2018-04-28T13:34:26.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2018-04-28 13:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551698
;

-- 2018-04-28T13:34:26.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2018-04-28 13:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551693
;

-- 2018-04-28T13:34:26.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2018-04-28 13:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551696
;

