-- 2018-01-03T08:00:45.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541008,0,125,TO_TIMESTAMP('2018-01-03 08:00:45','YYYY-MM-DD HH24:MI:SS'),100,'Buchführungs-Schemata verwalten - Bitte neu anmelden damit Änderungen wirksam werden','de.metas.commission','_Buchführungs_Schema','Y','N','N','N','N','Buchführungs Schema',TO_TIMESTAMP('2018-01-03 08:00:45','YYYY-MM-DD HH24:MI:SS'),100,'Buchführungs Schema')
;

-- 2018-01-03T08:00:45.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541008 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-01-03T08:00:45.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541008, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541008)
;

-- 2018-01-03T08:00:45.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541000 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541001 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541003 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540956 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540881 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540882 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:45.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541008 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541000 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541001 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541008 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541003 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540956 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540881 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540882 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- 2018-01-03T08:00:49.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- 2018-01-03T08:01:16.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-03 08:01:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Accounting Schema',Description='',WEBUI_NameBrowse='Accounting Schema' WHERE AD_Menu_ID=541008 AND AD_Language='en_US'
;

-- 2018-01-03T08:01:28.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,199,540577,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-03T08:01:28.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540577 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-03T08:01:28.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540775,540577,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540776,540577,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540775,541331,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12360,0,199,541331,549898,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.','If selcted, you will post service related revenue to a different receiveables account and service related cost to a different payables account.','Y','N','N','Y','N','Leistungen seperat verbuchen',0,10,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50180,0,199,541331,549899,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Erlaube die Buchung von negativen Werten','Y','N','N','Y','N','Negativbuchung erlauben',0,20,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53281,0,199,541331,549900,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Verbuchung bei identischen Konten (Transit) durchführen','Y','N','N','Y','N','Verbuchung bei identischen Konten',0,30,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1351,0,199,541331,549901,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,40,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2037,0,199,541331,549902,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,50,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1353,0,199,541331,549903,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,60,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1354,0,199,541331,549904,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,70,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1352,0,199,541331,549905,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,80,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1355,0,199,541331,549906,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Generally Accepted Accounting Principles','The GAAP identifies the account principles that this accounting schema will adhere to.','Y','N','Y','Y','N','GAAP',60,90,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12374,0,199,541331,549907,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Erstelle Reservierungen für Budgetkontrolle','The Posting Type Commitments is created when posting Purchase Orders; The Posting Type Revervation is created when posting Requisitions.  This is used for bugetary control.','Y','N','Y','Y','N','Art Reservierung',70,100,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1356,0,199,541331,549908,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird','The Accrual checkbox indicates if this accounting schema will use accrual based account or cash based accounting.  The Accrual method recognizes revenue when the product or service is delivered.  Cash based method recognizes income when then payment is received.','Y','N','Y','Y','N','Umsatzrealisierung bei Rechnung',80,110,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1357,0,199,541331,549909,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, wie die Kosten berechnet werden','"Kostenrechnungsmethode" gibt an, wie die Kosten berechnet werden (Standard, Durchschnitt, LiFo, FiFo). Die Standardmethode ist auf Ebene des Kontenrahmens definiert und kann optional in der Produktkategorie überschrieben werden. Die Methode der Kostenrechnung kann nicht zur Art der Materialbewegung (definiert in der Produktkategorie) im Widerspruch stehen.','Y','N','Y','Y','N','Kostenrechnungsmethode',90,120,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11267,0,199,541331,549910,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie der Kosten (z.B. Aktuell, Plan, Zukünftig)','Sie können mehrere Kostenkategorien anlegen. Eine in einem Kontenschema ausgewählte Kostenkategorie wird für die Buchführung verwendet.','Y','N','Y','Y','N','Kostenkategorie',100,130,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12157,0,199,541331,549911,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Die unterste Stufe um Kosteninformation zu kumulieren','Wenn Sie verschiedene Kosten pro Organisation (Lager) oder pro Charge/Los verwalten möchten dann stellen Sie sicher, dass Sie diese für jede Organistaion und jede Charge bzw. jedes Los definieren. Die Kostenrechnungsstufe ist pro Kontenrahmens definiert und kann durch die Produktkategorie oder  den Kontenrahmen überschrieben werden.','Y','N','Y','Y','N','Kostenrechnungsstufe',110,140,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12164,0,199,541331,549912,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Adjust Cost of Good Sold','For Invoice costing methods, you can adjust the cost of goods sold. At the time of shipment, you may not have received the invoice for the receipt or cost adjustments like freight, customs, etc.','Y','N','Y','Y','N','Adjust COGS',120,150,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1358,0,199,541331,549913,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','Y','N','Währung',130,160,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1360,0,199,541331,549914,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Wenn selektiert, werden die Buchungsperioden automatisch geöffnet und geschlossen','Bei der "Automatischen Periodenkontrolle" werden die Buchungsperioden basierend auf dem aktuellen Datum automatisch geöffnet und geschlossen. Wenn die Alternative "Manuell" selektiert ist, müssen Sie die Perioden explizit öffnen und schliessen.','Y','N','Y','Y','N','Automatische Periodenkontrolle',140,170,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1361,0,199,541331,549915,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Periode des Kalenders','"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','Y','N','Periode',150,180,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1362,0,199,541331,549916,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Number of days to be able to post in the past (based on system date)','If Automatic Period Control is enabled, the current period is calculated based on the system date and you can always post to all days in the current period.  History Days enable to post to previous periods.  E.g. today is May 15th and History Days is set to 30, you can post back to April 15th','Y','N','Y','Y','N','Buchungstage - Rückwärts',160,190,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:28.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1363,0,199,541331,549917,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Number of days to be able to post to a future date (based on system date)','If Automatic Period Control is enabled, the current period is calculated based on the system date and you can always post to all days in the current period.  Future Days enable to post to future periods. E.g. today is Apr 15th and Future Days is set to 30, you can post up to May 15th','Y','N','Y','Y','N','Buchungstage - Vorwärts',170,200,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1519,0,199,541331,549918,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Element-Trenner','"Element-Trenner" definiert das Trennzeichen zwischen Elementen der Struktur.','Y','N','Y','Y','N','Element-Trenner',180,210,0,TO_TIMESTAMP('2018-01-03 08:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1520,0,199,541331,549919,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Ability to select (partial) account combinations by an Alias','The Alias checkbox indicates that account combination can be selected using a user defined alias or short key.','Y','N','Y','Y','N','Kontenbezeichner verwenden',190,220,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4858,0,199,541331,549920,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Erzeuge seperate Buchungen für Handelstrabatte','If the invoice is based on an item with a list price, the amount based on the list price and the discount is posted instead of the net amount.
Example: Quantity 10 - List Price: 20 - Actual Price: 17
If selected for a sales invoice 200 is posted to Product Revenue and 30 to Discount Granted - rather than 170 to Product Revenue.
The same applies to vendor invoices.','Y','N','Y','Y','N','Rabatte seperat verbuchen',200,230,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13696,0,199,541331,549921,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Art der MwSt. Korrektur','Determines if/when tax is corrected. Discount could be agreed or granted underpayments; Write-off may be partial or complete write-off.','Y','N','Y','Y','N','MwSt. Korrektur',210,240,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12361,0,199,541331,549922,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.','If selected, landed costs are posted to the account in the line and then this posting is reversed by the postings to the cost adjustment accounts.  If not selected, it is directly posted to the cost adjustment accounts.','Y','N','Y','Y','N','Bezugsnebenkosten direkt verbuchen',220,250,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12180,0,199,541331,549923,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Kontrierung nur für die angegebene Organisation','When you have multiple accounting schema, you may want to restrict the generation of postings entries for the additional accounting schema (i.e. not for the primary).  Example: You have a US and a FR organization. The primary accounting schema is in USD, the second in EUR.  If for the EUR accounting schema, you select the FR organizations, you would not create accounting entries for the transactions of the US organization in EUR.','Y','N','Y','Y','N','Nur für Organisation',230,260,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12522,0,199,541331,549924,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Kopiere Konten von einem anderen Bcuhführungsschema','Create the GL and Default accounts for this accounting schema and copy matching account element values.','Y','N','Y','Y','N','Konten kopieren',240,270,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,217,540578,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-03T08:01:29.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540578 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-03T08:01:29.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540777,540578,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540777,541332,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12602,0,217,541332,549925,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Spalte in der Tabelle','Verbindung zur Spalte der Tabelle','Y','N','N','Y','N','Spalte',0,10,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554684,0,217,541332,549926,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsDisplayInEditor',0,20,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1526,0,217,541332,549927,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,30,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1543,0,217,541332,549928,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,40,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1527,0,217,541332,549929,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','Buchführungs-Schema',0,50,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1529,0,217,541332,549930,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,60,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1524,0,217,541332,549931,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,70,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1528,0,217,541332,549932,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Element Type (account or user defined)','The Element Type indicates if this element is the Account element or is a User Defined element.  ','Y','N','N','Y','N','Art',0,80,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1531,0,217,541332,549933,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Element','The Account Element uniquely identifies an Account Type.  These are commonly known as a Chart of Accounts.','Y','N','N','Y','N','Element',0,90,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1532,0,217,541332,549934,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Ausgeglichen',0,100,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1533,0,217,541332,549935,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Data entry is required in this column','The field must have a value for the record to be saved to the database.','Y','N','N','Y','N','Pflichtangabe',0,110,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1530,0,217,541332,549936,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,120,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1534,0,217,541332,549937,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung.','Y','N','N','Y','N','Organisation',0,130,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1541,0,217,541332,549938,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','Account Elements can be natural accounts or user defined values.','Y','N','N','Y','N','Kontenart',0,140,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1535,0,217,541332,549939,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,150,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1599,0,217,541332,549940,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,160,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1537,0,217,541332,549941,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','N','Y','N','Anschrift',0,170,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1538,0,217,541332,549942,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Vertriebsgebiet','"Vertriebsgebiet" gibt einen bestimmten Verkaufsbereich an.','Y','N','N','Y','N','Vertriebsgebiet',0,180,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:29.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1539,0,217,541332,549943,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Finanzprojekt','Ein Projekt erlaubt, interne oder externe Vorgäng zu verfolgen und zu kontrollieren.','Y','N','N','Y','N','Projekt',0,190,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1540,0,217,541332,549944,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','N','N','Y','N','Werbemassnahme',0,200,0,TO_TIMESTAMP('2018-01-03 08:01:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4575,0,217,541332,549945,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','Kostenstelle',0,210,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,200,540579,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-03T08:01:30.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540579 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-03T08:01:30.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540778,540579,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540778,541333,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1366,0,200,541333,549946,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2060,0,200,541333,549947,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1365,0,200,541333,549948,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','Buchführungs-Schema',0,30,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1367,0,200,541333,549949,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1368,0,200,541333,549950,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.','Y','N','N','Y','N','Doppelte Buchführung',0,50,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1369,0,200,541333,549951,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.','Y','N','N','Y','N','CpD-Konto',0,60,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1370,0,200,541333,549952,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','CpD-Fehlerkonto verwenden',0,70,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1371,0,200,541333,549953,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','CpD-Fehlerkonto',0,80,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1372,0,200,541333,549954,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Sollen Währungsdifferenzen verbucht werden?','Y','N','N','Y','N','Währungsunterschiede verbuchen',0,90,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1364,0,200,541333,549955,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto, das verwendet wird, wenn eine Währung unausgeglichen ist','"Währungsausgleichs-Konto" gibt das Konto an, das verwendet wird, wenn eine Währung unausgeglichen ist (meist aufgrund von Rundungen)','Y','N','N','Y','N','Konto für Währungsdifferenzen',0,100,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1377,0,200,541333,549956,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Saldenvortrag Sachkonten',0,110,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1378,0,200,541333,549957,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Gewinnvortrag vor Verwendung','Y','N','N','Y','N','Gewinnvortrag vor Verwendung',0,120,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1379,0,200,541333,549958,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Verbindlichkeiten gegenüber verbundenen Unternehmen','The Intercompany Due To Account indicates the account that represents money owed to other organizations.','Y','N','N','Y','N','Verbindlichkeiten geg. verbundenen Unternehmen',0,130,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1380,0,200,541333,549959,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungen geg. verbundenen Unternehmen','The Intercompany Due From account indicates the account that represents money owed to this organization from other organizations.','Y','N','N','Y','N','Forderungen geg. verbundenen Unternehmen',0,140,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5332,0,200,541333,549960,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Purchase Price Variance Offset Account','Offset account for standard costing purchase price variances. The counter account is Product PPV.','Y','N','N','Y','N','PPV Offset',0,150,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12375,0,200,541333,549961,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Budgetary Commitment Offset Account','The Commitment Offset Account is used for posting Commitments and Reservations.  It is usually an off-balance sheet and gain-and-loss account.','Y','N','N','Y','N','Commitment Offset',0,160,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53282,0,200,541333,549962,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Budgetary Commitment Offset Account for Sales','The Commitment Offset Account is used for posting Commitments Sales and Reservations.  It is usually an off-balance sheet and gain-and-loss account.','Y','N','N','Y','N','Commitment Offset Sales',0,170,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,252,540580,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-03T08:01:30.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540580 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-03T08:01:30.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540779,540580,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540779,541334,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2646,0,252,541334,549963,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2647,0,252,541334,549964,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2648,0,252,541334,549965,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','Buchführungs-Schema',0,30,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2650,0,252,541334,549966,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungen aus Lieferungen (und Leistungen)','The Customer Receivables Accounts indicates the account to be used for recording transaction for customers receivables.','Y','N','N','Y','N','Forderungen aus Lieferungen',0,40,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12349,0,252,541334,549967,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungen aus Dienstleistungen','Account to post services related Accounts Receivables if you want to differentiate between Services and Product related revenue. This account is only used, if posting to service accounts is enabled in the accounting schema.','Y','N','N','Y','N','Forderungen aus Dienstleistungen',0,50,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2649,0,252,541334,549968,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Erhaltene Anzahlungen','The Customer Prepayment account indicates the account to be used for recording prepayments from a customer.','Y','N','N','Y','N','Erhaltene Anzahlungen',0,60,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:30.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3827,0,252,541334,549969,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für gewährte Skonti','Indicates the account to be charged for payment discount expenses.','Y','N','N','Y','N','Gewährte Skonti',0,70,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3828,0,252,541334,549970,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungsverluste','The Write Off Account identifies the account to book write off transactions to.','Y','N','N','Y','N','Forderungsverluste',0,80,0,TO_TIMESTAMP('2018-01-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3854,0,252,541334,549971,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für unfertige Erzeugnisse','The Not Invoiced Receivables account indicates the account used for recording receivables that have not yet been invoiced.','Y','N','N','Y','N','Unfertige Erzeugnisse',0,90,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3821,0,252,541334,549972,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für nicht abgerechnete Einnahmen','The Not Invoiced Revenue account indicates the account used for recording revenue that has not yet been invoiced.','Y','N','N','Y','N','Nicht abgerechnete Einnahmen',0,100,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3853,0,252,541334,549973,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für vorausberechnete Einnahmen','The Unearned Revenue indicates the account used for recording invoices sent for products or services not yet delivered.  It is used in revenue recognition','Y','N','N','Y','N','Vorausberechnete Einnahmen',0,110,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2659,0,252,541334,549974,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Verbindlichkeiten aus Lieferungen (und Leistungen)','The Vendor Liability account indicates the account used for recording transactions for vendor liabilities','Y','N','N','Y','N','Verbindlichkeiten aus Lieferungen',0,120,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2660,0,252,541334,549975,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Verbindlichkeiten aus Dienstleistungen','The Vendor Service Liability account indicates the account to use for recording service liabilities.  It is used if you need to distinguish between Liability for products and services. This account is only used, if posting to service accounts is enabled in the accounting schema.','Y','N','N','Y','N','Verbindlichkeiten aus Dienstleistungen',0,130,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2661,0,252,541334,549976,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Geleistete Anzahlungen','The Vendor Prepayment Account indicates the account used to record prepayments from a vendor.','Y','N','N','Y','N','Geleistete Anzahlungen',0,140,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3829,0,252,541334,549977,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für erhaltene Skonti','Indicates the account to be charged for payment discount revenues.','Y','N','N','Y','N','Erhaltene Skonti',0,150,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3822,0,252,541334,549978,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Noch nicht in Rechnung gestellte Wareneingänge',0,160,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3834,0,252,541334,549979,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Account for Withholdings','The Withholding Account indicates the account used to record withholdings.','Y','N','N','Y','N','Einbehalt',0,170,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2652,0,252,541334,549980,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für geleistete Anzahlungen an Mitarbeiter','The Employee Prepayment Account identifies the account to use for recording expense advances made to this employee.','Y','N','N','Y','N','Geleistete Anzahlungen Mitarbeiter',0,180,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2651,0,252,541334,549981,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Löhne und Gehälter','The Employee Expense Account identifies the account to use for recording expenses for this employee.','Y','N','N','Y','N','Löhne und Gehälter',0,190,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2655,0,252,541334,549982,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Warenbestand','"Produkt-Asset" bezeichnet das Konto für die Bewertung eines Produktes im Bestand.','Y','N','N','Y','N','Warenbestand',0,200,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2657,0,252,541334,549983,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Produkt Aufwand','The Product Expense Account indicates the account used to record expenses associated with this product.','Y','N','N','Y','N','Produkt Aufwand',0,210,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12347,0,252,541334,549984,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Bezugsnebenkosten','Account used for posting product cost adjustments (e.g. landed costs)','Y','N','N','Y','N','Bezugsnebenkosten',0,220,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12348,0,252,541334,549985,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Product Inventory Clearing Account','Account used for posting matched product (item) expenses (e.g. AP Invoice, Invoice Match).  You would use a different account then Product Expense, if you want to differentate service related costs from item related costs. The balance on the clearing account should be zero and accounts for the timing difference between invoice receipt and matching.
','Y','N','N','Y','N','Inventory Clearing',0,230,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2658,0,252,541334,549986,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Produkt Ertrag','The Product Revenue Account indicates the account used for recording sales revenue for this product.','Y','N','N','Y','N','Produkt Ertrag',0,240,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:31.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2656,0,252,541334,549987,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Produkt Vertriebsausgaben','The Product COGS Account indicates the account used when recording costs associated with this product.','Y','N','N','Y','N','Produkt Vertriebsausgaben',0,250,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3825,0,252,541334,549988,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.','The Purchase Price Variance is used in Standard Costing. It reflects the difference between the Standard Cost and the Purchase Order Price.','Y','N','N','Y','N','Preisdifferenz Bestellung',0,260,0,TO_TIMESTAMP('2018-01-03 08:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4860,0,252,541334,549989,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Preisdifferenz Einkauf Rechnung','The Invoice Price Variance is used reflects the difference between the current Costs and the Invoice Price.','Y','N','N','Y','N','Preisdifferenz Einkauf Rechnung',0,270,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4861,0,252,541334,549990,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für erhaltene Rabatte','The Trade Discount Receivables Account indicates the account for received trade discounts in vendor invoices','Y','N','N','Y','N','Erhaltene Rabatte',0,280,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4862,0,252,541334,549991,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für gewährte Rabatte','The Trade Discount Granted Account indicates the account for granted trade discount in sales invoices','Y','N','N','Y','N','Gewährte Rabatte',0,290,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2663,0,252,541334,549992,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Warehouse Inventory Asset Account - Currently not used','The Warehouse Inventory Asset Account identifies the account used for recording the value of your inventory. This is the counter account for inventory revaluation differences. The Product Asset account maintains the product asset value.','Y','N','N','Y','N','(Not Used)',0,300,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4863,0,252,541334,549993,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)','In actual costing systems, this account is used to post Inventory value adjustments. You could set it to the standard Inventory Asset account.','Y','N','N','Y','N','Lager Wert Korrektur',0,310,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2662,0,252,541334,549994,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Differenzen im Lagerbestand (erfasst durch Inventur)','The Warehouse Differences Account indicates the account used recording differences identified during inventory counts.','Y','N','N','Y','N','Lager Bestand Korrektur',0,320,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3824,0,252,541334,549995,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Lager Wert Korrektur Währungsdifferenz','The Inventory Revaluation Account identifies the account used to records changes in inventory value due to currency revaluation.','Y','N','N','Y','N','Lager Wert Korrektur Währungsdifferenz',0,330,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2654,0,252,541334,549996,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Project Asset Account','The Project Asset account is the account used as the final asset account in capital projects','Y','N','N','Y','N','Project Asset',0,340,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3835,0,252,541334,549997,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Unfertige Leistungen','The Work in Process account is the account used in capital projects until the project is completed','Y','N','N','Y','N','Unfertige Leistungen',0,350,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56529,0,252,541334,549998,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet','Y','N','N','Y','N','Unfertige Leistungen',0,360,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56522,0,252,541334,549999,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Floor Stock account is the account used Manufacturing Order','The Floor Stock is used for accounting the component with Issue method  is set Floor stock  into Bill of Material & Formula Window.

The components with Issue Method  defined as Floor stock is acounting next way:

Debit Floor Stock Account
Credit Work in Process Account ','Y','N','N','Y','N','Floor Stock',0,370,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56524,0,252,541334,550000,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Method Change Variance account is the account used Manufacturing Order','The Method Change Variance is used in Standard Costing. It reflects the difference between the Standard BOM , Standard Manufacturing Workflow and Manufacturing BOM Manufacturing Workflow.

If you change the method the manufacturing defined in BOM or Workflow Manufacturig then this variance is generate.','Y','N','N','Y','N','Method Change Variance',0,380,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56528,0,252,541334,550001,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Usage Variance account is the account used Manufacturing Order','The Usage Variance is used in Standard Costing. It reflects the difference between the  Quantities of Standard BOM  or Time Standard Manufacturing Workflow and Quantities of Manufacturing BOM or Time Manufacturing Workflow of Manufacturing Order.

If you change the Quantities or Time  defined in BOM or Workflow Manufacturig then this variance is generate.','Y','N','N','Y','N','Usage Variance',0,390,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56527,0,252,541334,550002,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Rate Variance account is the account used Manufacturing Order','The Rate Variance is used in Standard Costing. It reflects the difference between the Standard Cost Rates and  The Cost Rates of Manufacturing Order.

If you change the Standard Rates then this variance is generate.','Y','N','N','Y','N','Rate Variance',0,400,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56525,0,252,541334,550003,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Mix Variance account is the account used Manufacturing Order','The Mix Variance is used when a co-product  received in Inventory  is different the quantity  expected
','Y','N','N','Y','N','Mix Variance',0,410,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56523,0,252,541334,550004,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Labor account is the account used Manufacturing Order','The Labor is used for accounting the productive Labor
','Y','N','N','Y','N','Labor',0,420,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56520,0,252,541334,550005,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Burden account is the account used Manufacturing Order','The Burden is used for accounting the Burden','Y','N','N','Y','N','Burden',0,430,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56521,0,252,541334,550006,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Cost Of Production account is the account used Manufacturing Order','The Cost Of Production is used for accounting Non productive Labor
','Y','N','N','Y','N','Cost Of Production',0,440,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56526,0,252,541334,550007,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Outside Processing Account is the account used in Manufacturing Order','The Outside Processing Account is used for accounting the Outside Processing','Y','N','N','Y','N','Outside Processing',0,450,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56550,0,252,541334,550008,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Overhead account is the account used  in Manufacturing Order ','Y','N','N','Y','N','Overhead',0,460,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56551,0,252,541334,550009,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'The Scrap account is the account used  in Manufacturing Order ','Y','N','N','Y','N','Scrap',0,470,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3842,0,252,541334,550010,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Bank Konto','The Bank Asset Account identifies the account to be used for booking changes to the balance in this bank account','Y','N','N','Y','N','Bank',0,480,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3841,0,252,541334,550011,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Bank In Transit','The Bank in Transit Account identifies the account to be used for funds which are in transit.','Y','N','N','Y','N','Bank In Transit',0,490,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3846,0,252,541334,550012,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Bank Unidentified Receipts Account','The Bank Unidentified Receipts Account identifies the account to be used when recording receipts that can not be reconciled at the present time.','Y','N','N','Y','N','Bank Unidentified Receipts',0,500,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:32.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5133,0,252,541334,550013,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für nicht zugeordnete Zahlungen','Receipts not allocated to Invoices','Y','N','N','Y','N','Nicht zugeordnete Zahlungen',0,510,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5132,0,252,541334,550014,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100,'Konto für selektierte Zahlungen','Y','N','N','Y','N','Bezahlung selektiert',0,520,0,TO_TIMESTAMP('2018-01-03 08:01:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3843,0,252,541334,550015,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Nebenkosten des Geldverkehrs','The Bank Expense Account identifies the account to be used for recording charges or fees incurred from this Bank.','Y','N','N','Y','N','Nebenkosten des Geldverkehrs',0,530,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3845,0,252,541334,550016,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Zins Aufwendungen','The Bank Interest Expense Account identifies the account to be used for recording interest expenses.','Y','N','N','Y','N','Zins Aufwendungen',0,540,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3844,0,252,541334,550017,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Zinserträge','The Bank Interest Revenue Account identifies the account to be used for recording interest revenue from this Bank.','Y','N','N','Y','N','Zinserträge',0,550,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3849,0,252,541334,550018,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Erträge aus Kursdifferenzen','The Bank Revaluation Gain Account identifies the account to be used for recording gains that are recognized when converting currencies.','Y','N','N','Y','N','Erträge aus Kursdifferenzen',0,560,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3850,0,252,541334,550019,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Währungsverluste','The Bank Revaluation Loss Account identifies the account to be used for recording losses that are recognized when converting currencies.','Y','N','N','Y','N','Währungsverluste',0,570,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5138,0,252,541334,550020,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Bank Settlement Loss Account','The Bank Settlement loss account identifies the account to be used when recording a currency loss when the settlement and receipt currency are not the same.','Y','N','N','Y','N','Bank Settlement Loss',0,580,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3847,0,252,541334,550021,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Bank Settlement Gain Account','The Bank Settlement Gain account identifies the account to be used when recording a currency gain when the settlement and receipt currency are not the same.','Y','N','N','Y','N','Bank Settlement Gain',0,590,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3839,0,252,541334,550022,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für geschuldete MwSt.','"Fällige Steuer" zeigt das Konto an, auf dem die zu zahlenden Steuern gesammelt werden.','Y','N','N','Y','N','Geschuldete MwSt.',0,600,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3837,0,252,541334,550023,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Verbindlichkeiten aus Steuern','The Tax Liability Account indicates the account used to record your tax liability declaration.','Y','N','N','Y','N','Verbindlichkeiten aus Steuern',0,610,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3840,0,252,541334,550024,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Vorsteuer','The Tax Credit Account indicates the account used to record taxes that can be reclaimed','Y','N','N','Y','N','Vorsteuer',0,620,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3838,0,252,541334,550025,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Steuerüberzahlungen','The Tax Receivables Account indicates the account used to record the tax credit amount after your tax declaration.','Y','N','N','Y','N','Steuerüberzahlungen',0,630,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3836,0,252,541334,550026,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Account for paid tax you cannot reclaim','The Tax Expense Account indicates the account used to record the taxes that have been paid that cannot be reclaimed.','Y','N','N','Y','N','Sonstige Steuern',0,640,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3851,0,252,541334,550027,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Betriebliche Aufwendungen','The Charge Expense Account identifies the account to use when recording charges paid to vendors.','Y','N','N','Y','N','Betriebliche Aufwendungen',0,650,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3852,0,252,541334,550028,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Sonstige Einnahmen','The Charge Revenue Account identifies the account to use when recording charges paid by customers.','Y','N','N','Y','N','Sonstige Einnahmen',0,660,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3830,0,252,541334,550029,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für nicht realisierte Währungsgewinne','The Unrealized Gain Account indicates the account to be used when recording gains achieved from currency revaluation that have yet to be realized.','Y','N','N','Y','N','Nicht realisierte Währungsgewinne',0,670,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3831,0,252,541334,550030,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für nicht realisierte Währungsverluste','The Unrealized Loss Account indicates the account to be used when recording losses incurred from currency revaluation that have yet to be realized.','Y','N','N','Y','N','Nicht realisierte Währungsverluste',0,680,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3832,0,252,541334,550031,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Realisierte Währungsgewinne','The Realized Gain Account indicates the account to be used when recording gains achieved from currency revaluation that have been realized.','Y','N','N','Y','N','Realisierte Währungsgewinne',0,690,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3833,0,252,541334,550032,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für realisierte Währungsverluste','The Realized Loss Account indicates the account to be used when recording losses incurred from currency revaluation that have yet to be realized.','Y','N','N','Y','N','Realisierte Währungsverluste',0,700,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4092,0,252,541334,550033,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Kasse','The Cash Book Asset Account identifies the account to be used for recording payments into and disbursements from this cash book.','Y','N','N','Y','N','Kasse',0,710,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4093,0,252,541334,550034,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Kassendifferenz','The Cash Book Differences Account identifies the account to be used for recording any differences that affect this cash book','Y','N','N','Y','N','Kassendifferenz',0,720,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5134,0,252,541334,550035,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Geldtransit','Account for Invoices paid by cash','Y','N','N','Y','N','Geldtransit',0,730,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:33.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4094,0,252,541334,550036,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Kasse Aufwand','The Cash Book Expense Account identifies the account to be used for general, non itemized expenses.','Y','N','N','Y','N','Kasse Aufwand',0,740,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4095,0,252,541334,550037,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Kasse Ertrag','The Cash Book Receipt Account identifies the account to be used for general, non itemized cash book receipts.','Y','N','N','Y','N','Kasse Ertrag',0,750,0,TO_TIMESTAMP('2018-01-03 08:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3823,0,252,541334,550038,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Kopieren und überschreiben der Konten im System (GEFÄHRLICH !!!)','Sie können entweder fehlende Konten hinzufügen - oder alle Standardkonten kopieren und überschreiben. If you copy and overwrite the current default values, you may have to repeat previous updates (e.g. set the bank account asset accounts, ...). If no Accounting Schema is selected all Accounting Schemas will be updated / inserted.','Y','N','N','Y','N','Konten kopieren',0,760,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540720,540581,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-03T08:01:34.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540581 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-03T08:01:34.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540780,540581,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540780,541335,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556602,0,540720,541335,550039,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Steuerart','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','N','Y','N','Steuer',0,10,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556603,0,540720,541335,550040,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','Sales Transaction',0,20,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556606,0,540720,541335,550041,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig ab',0,30,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556607,0,540720,541335,550042,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig bis',0,40,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556604,0,540720,541335,550043,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','VAT Code',0,50,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556605,0,540720,541335,550044,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-03T08:01:34.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556599,0,540720,541335,550045,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,70,0,TO_TIMESTAMP('2018-01-03 08:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

