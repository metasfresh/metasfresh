-- 2017-11-30T16:32:38.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540987,0,540347,TO_TIMESTAMP('2017-11-30 16:32:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Board_Einstellung','Y','N','N','N','N','Board Einstellung',TO_TIMESTAMP('2017-11-30 16:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Board Einstellung')
;

-- 2017-11-30T16:32:38.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540987 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-11-30T16:32:38.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540987, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540987)
;

-- 2017-11-30T16:32:57.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 16:32:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Board Configuration',WEBUI_NameBrowse='Board Configuration' WHERE AD_Menu_ID=540987 AND AD_Language='en_US'
;

-- 2017-11-30T16:35:35.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540832,540548,TO_TIMESTAMP('2017-11-30 16:35:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 16:35:35','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-30T16:35:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540548 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-30T16:35:36.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540731,540548,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540732,540548,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540731,541268,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558712,0,540832,541268,549522,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558713,0,540832,541268,549523,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558716,0,540832,541268,549524,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558714,0,540832,541268,549525,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',40,40,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558717,0,540832,541268,549526,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','Y','N','DB-Tabelle',50,50,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558718,0,540832,541268,549527,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Regel für die  dynamische Validierung','Diese Regeln bestimmen, wie ein Eintrag als gültig bewertet wird. Sie können Variablen für eine dynamische (kontextbezogene) Validierung verwenden.','Y','N','Y','Y','N','Dynamische Validierung',60,60,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540833,540549,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-30T16:35:36.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540549 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-30T16:35:36.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540733,540549,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540733,541269,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558719,0,540833,541269,549528,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558720,0,540833,541269,549529,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558721,0,540833,541269,549530,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558723,0,540833,541269,549531,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558724,0,540833,541269,549532,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,50,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558725,0,540833,541269,549533,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Board',0,60,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540834,540550,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-30T16:35:36.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540550 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-30T16:35:36.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540734,540550,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540734,541270,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558733,0,540834,541270,549534,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,10,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558732,0,540834,541270,549535,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Spalte in der Tabelle','Verbindung zur Spalte der Tabelle','Y','N','N','Y','N','Spalte',0,20,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:35:36.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558729,0,540834,541270,549536,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2017-11-30 16:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:45:53.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540988,0,540347,TO_TIMESTAMP('2017-11-30 16:45:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','_Board_Einstellung','Y','N','N','N','N','Board Einstellung',TO_TIMESTAMP('2017-11-30 16:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Board Einstellung')
;

-- 2017-11-30T16:45:53.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540988 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-11-30T16:45:53.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540988, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540988)
;

-- 2017-11-30T16:45:53.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- 2017-11-30T16:45:53.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540960 AND AD_Tree_ID=10
;

-- 2017-11-30T16:45:53.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540988 AND AD_Tree_ID=10
;

-- 2017-11-30T16:48:09.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540731,541271,TO_TIMESTAMP('2017-11-30 16:48:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-30 16:48:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:48:21.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90,Updated=TO_TIMESTAMP('2017-11-30 16:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541268
;

-- 2017-11-30T16:48:46.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558716,0,540832,541271,549537,'F',TO_TIMESTAMP('2017-11-30 16:48:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-11-30 16:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:49:15.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540731,541272,TO_TIMESTAMP('2017-11-30 16:49:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','validation',20,TO_TIMESTAMP('2017-11-30 16:49:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:49:26.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558717,0,540832,541272,549538,'F',TO_TIMESTAMP('2017-11-30 16:49:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','DB Tabelle',10,0,0,TO_TIMESTAMP('2017-11-30 16:49:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:49:45.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558718,0,540832,541272,549539,'F',TO_TIMESTAMP('2017-11-30 16:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Validierung',20,0,0,TO_TIMESTAMP('2017-11-30 16:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:49:54.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540732,541273,TO_TIMESTAMP('2017-11-30 16:49:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-11-30 16:49:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:50:05.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558714,0,540832,541273,549540,'F',TO_TIMESTAMP('2017-11-30 16:50:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-11-30 16:50:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:50:10.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540732,541274,TO_TIMESTAMP('2017-11-30 16:50:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-11-30 16:50:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:50:23.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558713,0,540832,541274,549541,'F',TO_TIMESTAMP('2017-11-30 16:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-11-30 16:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:50:32.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558712,0,540832,541274,549542,'F',TO_TIMESTAMP('2017-11-30 16:50:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-11-30 16:50:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:52:17.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549522
;

-- 2017-11-30T16:52:17.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549523
;

-- 2017-11-30T16:52:17.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549524
;

-- 2017-11-30T16:52:17.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549525
;

-- 2017-11-30T16:52:17.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549526
;

-- 2017-11-30T16:52:17.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549527
;

-- 2017-11-30T16:52:21.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541268
;

-- 2017-11-30T16:54:26.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-11-30 16:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549537
;

-- 2017-11-30T16:54:26.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-11-30 16:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549538
;

-- 2017-11-30T16:54:26.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-11-30 16:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549539
;

-- 2017-11-30T16:54:26.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-11-30 16:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549540
;

-- 2017-11-30T16:54:26.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-11-30 16:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549541
;

-- 2017-11-30T16:54:43.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-11-30 16:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549537
;

-- 2017-11-30T16:54:43.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-11-30 16:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549538
;

-- 2017-11-30T16:54:43.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-11-30 16:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549539
;

-- 2017-11-30T16:54:43.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-11-30 16:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549540
;

-- 2017-11-30T16:54:43.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-11-30 16:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549541
;

-- 2017-11-30T16:55:22.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-11-30 16:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549533
;

-- 2017-11-30T16:55:22.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-11-30 16:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549528
;

-- 2017-11-30T16:55:22.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-11-30 16:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549532
;

-- 2017-11-30T16:55:22.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-11-30 16:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549531
;

-- 2017-11-30T16:55:22.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-11-30 16:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549529
;

-- 2017-11-30T16:55:37.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-11-30 16:55:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549532
;

-- 2017-11-30T16:55:39.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-11-30 16:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549531
;

-- 2017-11-30T16:55:42.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-11-30 16:55:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549530
;

-- 2017-11-30T16:55:45.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-30 16:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549529
;

-- 2017-11-30T16:55:49.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-11-30 16:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549528
;

-- 2017-11-30T16:55:49.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-11-30 16:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549532
;

-- 2017-11-30T16:55:50.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-11-30 16:55:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549531
;

-- 2017-11-30T16:55:50.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-11-30 16:55:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549530
;

-- 2017-11-30T16:55:52.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-11-30 16:55:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549529
;

-- 2017-11-30T16:56:12.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-11-30 16:56:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549532
;

-- 2017-11-30T16:56:16.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-11-30 16:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549529
;

-- 2017-11-30T16:56:19.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-11-30 16:56:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549528
;

-- 2017-11-30T16:56:23.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-11-30 16:56:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549531
;

-- 2017-11-30T16:57:58.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558728,0,540834,541270,549543,'F',TO_TIMESTAMP('2017-11-30 16:57:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-11-30 16:57:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:58:08.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558727,0,540834,541270,549544,'F',TO_TIMESTAMP('2017-11-30 16:58:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-11-30 16:58:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T16:58:14.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-11-30 16:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549543
;

-- 2017-11-30T16:58:41.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-11-30 16:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549534
;

-- 2017-11-30T16:58:47.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-11-30 16:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549543
;

-- 2017-11-30T16:58:52.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-11-30 16:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549544
;

-- 2017-11-30T16:58:56.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-11-30 16:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549535
;

-- 2017-11-30T17:01:51.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Board Karte',Updated=TO_TIMESTAMP('2017-11-30 17:01:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558730
;

-- 2017-11-30T17:02:06.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 17:02:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Board Card Field' WHERE AD_Field_ID=558730 AND AD_Language='en_US'
;

-- 2017-11-30T17:02:21.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Board Spalte',Updated=TO_TIMESTAMP('2017-11-30 17:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558722
;

-- 2017-11-30T17:02:41.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Board Bahn',Updated=TO_TIMESTAMP('2017-11-30 17:02:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558722
;

-- 2017-11-30T17:02:56.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DB Tabelle',Updated=TO_TIMESTAMP('2017-11-30 17:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558717
;

-- 2017-11-30T17:03:00.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Validierung',Updated=TO_TIMESTAMP('2017-11-30 17:03:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558718
;

-- 2017-11-30T17:03:38.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 17:03:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='DB Table' WHERE AD_Field_ID=558717 AND AD_Language='en_US'
;

-- 2017-11-30T17:03:59.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 17:03:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Validation Rule',Description='',Help='' WHERE AD_Field_ID=558718 AND AD_Language='en_US'
;

-- 2017-11-30T17:04:14.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 17:04:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=558714 AND AD_Language='en_US'
;

-- 2017-11-30T17:04:22.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 17:04:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=558716 AND AD_Language='en_US'
;

-- 2017-11-30T17:04:37.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-30 17:04:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=558712 AND AD_Language='en_US'
;

