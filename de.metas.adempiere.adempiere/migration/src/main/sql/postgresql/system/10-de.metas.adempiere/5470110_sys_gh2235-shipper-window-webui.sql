-- 2017-08-28T16:05:28.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540909,0,142,TO_TIMESTAMP('2017-08-28 16:05:28','YYYY-MM-DD HH24:MI:SS'),100,'Spediteure verwalten','de.metas.inoutcandidate','_Lieferweg','Y','N','N','N','N','Lieferweg',TO_TIMESTAMP('2017-08-28 16:05:28','YYYY-MM-DD HH24:MI:SS'),100,'Lieferweg')
;

-- 2017-08-28T16:05:28.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540909 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-08-28T16:05:28.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540909, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540909)
;

-- 2017-08-28T16:05:28.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540730 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:28.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540731 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:28.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540747 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:28.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540733 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:28.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540732 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:28.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540768 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:28.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540909 AND AD_Tree_ID=10
;

-- 2017-08-28T16:05:57.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-28 16:05:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipper',Description='Maintain Shipper Businesspartners',WEBUI_NameBrowse='Shipper' WHERE AD_Menu_ID=540909 AND AD_Language='en_US'
;

-- 2017-08-28T16:06:16.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,185,540433,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-28T16:06:16.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540433 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-28T16:06:16.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540578,540433,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:16.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540579,540433,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:16.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540578,541017,TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-28 16:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1073,0,185,541017,547669,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2051,0,185,541017,547670,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1075,0,185,541017,547671,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1076,0,185,541017,547672,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,40,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1074,0,185,541017,547673,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2588,0,185,541017,547674,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',60,60,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6937,0,185,541017,547675,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'URL des Spediteurs um Sendungen zu verfolgen','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','Y','Y','N','Nachverfolgungs-URL',70,70,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542626,0,185,541017,547676,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','Y','N','Standard',80,80,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,513,540434,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-28T16:06:17.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540434 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-28T16:06:17.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540580,540434,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540580,541018,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7052,0,513,541018,547677,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7063,0,513,541018,547678,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7055,0,513,541018,547679,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','Y','N','Lieferweg',0,30,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7058,0,513,541018,547680,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7053,0,513,541018,547681,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Fracht-Kategorie','Fracht-Kategorien werden verwendet um die Fracht für den gewählten Spediteur zu berechnen','Y','N','N','Y','N','Fracht-Kategorie',0,50,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7059,0,513,541018,547682,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig ab',0,60,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7056,0,513,541018,547683,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','Währung',0,70,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7057,0,513,541018,547684,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Frachtbetrag','"Frachtbetrag" gibt den Betrag für Fracht in diesem Beleg an.','Y','N','N','Y','N','Frachtbetrag',0,80,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7061,0,513,541018,547685,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','Land',0,90,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7062,0,513,541018,547686,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Receiving Country','The To Country indicates the receiving country on a document','Y','N','N','Y','N','An',0,100,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7054,0,513,541018,547687,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert eine geographische Region','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','N','Y','N','Region',0,110,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-28T16:06:17.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7060,0,513,541018,547688,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Receiving Region','The To Region indicates the receiving region on a document','Y','N','N','Y','N','An',0,120,0,TO_TIMESTAMP('2017-08-28 16:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

