-- 2017-06-24T08:19:54.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540883,0,540113,TO_TIMESTAMP('2017-06-24 08:19:54','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt die Erfassung von Vertragsbedingungen zur pauschalen Abrechnung','de.metas.flatrate','_Vertragsbedingungen','Y','N','N','N','N','Vertragsbedingungen',TO_TIMESTAMP('2017-06-24 08:19:54','YYYY-MM-DD HH24:MI:SS'),100,'Vetragsbedingungen')
;

-- 2017-06-24T08:19:54.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540883 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-24T08:19:54.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540883, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540883)
;

-- 2017-06-24T08:19:54.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540415, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540416 AND AD_Tree_ID=10
;

-- 2017-06-24T08:19:54.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540415, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540480 AND AD_Tree_ID=10
;

-- 2017-06-24T08:19:54.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540415, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:00.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:01.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:01.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:01.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:59.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540884,0,540120,TO_TIMESTAMP('2017-06-24 08:20:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate','_Vertragslaufzeit','Y','N','N','N','N','Vertragslaufzeit',TO_TIMESTAMP('2017-06-24 08:20:59','YYYY-MM-DD HH24:MI:SS'),100,'Vertragslaufzeit')
;

-- 2017-06-24T08:20:59.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540884 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-24T08:20:59.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540884, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540884)
;

-- 2017-06-24T08:20:59.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:59.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:59.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:59.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:59.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- 2017-06-24T08:20:59.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- 2017-06-24T08:21:02.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- 2017-06-24T08:21:02.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2017-06-24T08:21:02.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- 2017-06-24T08:21:02.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- 2017-06-24T08:21:02.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- 2017-06-24T08:21:02.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- 2017-06-24T08:31:58.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540331,540320,TO_TIMESTAMP('2017-06-24 08:31:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 08:31:57','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-24T08:31:58.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540320 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-24T08:31:58.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540438,540320,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540439,540320,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540438,540758,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551476,0,540331,540758,546080,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Manueller Preis',10,10,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547847,0,540331,540758,546081,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548226,0,540331,540758,546082,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Vertragsart',30,30,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548499,0,540331,540758,546083,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548222,0,540331,540758,546084,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548427,0,540331,540758,546085,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Planspiel',60,60,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551045,0,540331,540758,546086,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob das System beim Fertigstellen einer neuen Vertragslaufzeit (z.B. bei automatischer Verlängerung) eine Auftragsbestätigung erzeugt.','Y','N','Y','Y','N','AB bei neuer Vertragslaufzeit',70,70,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548163,0,540331,540758,546087,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Typ der möglichen Einheiten, zu denen Leistungen pauschal erfasst und abgerechnet werden können.','In den Vertragsbedingungen sind nur die Maßeinheiten mit dem hier gewählten Einheiten-Typ auswählbar.
Bei der Pauschalen-Datenerfassung kann nur ein Vertrag ausgewählt werden, dessen Einheiten-Typ mit dem Einheitentyp des jeweiligen Basisdatensatzes übereinstimmt.','Y','N','Y','Y','N','Einheiten-Typ',80,80,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547846,0,540331,540758,546088,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit, zu der pauschal erfasste Leistungen abgerechnet werden sollen','Y','N','Y','Y','N','Maßeinheit',90,90,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548120,0,540331,540758,546089,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Art der Rechnungsstellung bei Rechnungskandidaten, die aufgrund dieser Vertragsbedingungen erszeugt werden.','"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','N','Y','Y','N','Rechnungsstellung',100,100,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547835,0,540331,540758,546090,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, unter dem die pauschal abzurechnenden Leistungen in Rechnung gestellt werden','Y','N','Y','Y','N','Produkt für pauschale Berechnung',110,110,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547848,0,540331,540758,546091,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Preissystem, das bei der Pauschalen-Berechnung benutzt werden soll. ','Das Preissystem enthält neben dem Preis und der Währung noch Informationen zur MwSt.-Kategorie und die Information, ob der jeweilige Preis inklusive MwSt. ist oder nicht. Deshalb muss ein Preissystem auch dann gewählt werden, wenn in den Vertragsbedingungen ein konkreter Preis mit Währung angegeben ist.','Y','N','Y','Y','N','Preissystem',120,120,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551009,0,540331,540758,546092,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Es wird unabhängig vom gewählten Preissystem ein Rabatt von 100% gewährt','Y','N','Y','Y','N','Gratis',130,130,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554789,0,540331,540758,546093,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lagerkonferenz',140,140,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548160,0,540331,540758,546094,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen','Im Fall der Gegegenüberstellung von zu pauschal abgerechenten Beträgen und tatsächlich erbrachten Leistungen kann eine Verrechnung mit eventueller Nachzahlung oder Rückerstattung erfolgen.','Y','N','Y','Y','N','Gegenüberstellung mit erbr. Leist.',150,150,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547849,0,540331,540758,546095,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen','Wenn eine Gegenüberstellung mit tatsächliche erbrachten Leistungen vorgesehen ist, dann definiert dieses Feld, ob eine ggf. eine Abweichung in Rechnung gestellt werden kann.','Y','N','Y','Y','N','Verrechnungsart',160,160,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547851,0,540331,540758,546096,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Differenz zwischen pauschal abgerechneten und tatsächlich erbrachten Leistungen in Rechnung zu stellen ist, dann definiert dieses Feld, ob der Differenzbetrag komplett oder nur teilweise berechnet wird','Y','N','Y','Y','N','Verrechnungsmodus',170,170,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548433,0,540331,540758,546097,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, unter dem ggf. die Differenz zu tatsächlich erbrachten Leistungen in Rechnung gestellt wird.','Y','N','Y','Y','N','Produkt für Verrechnung',180,180,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547843,0,540331,540758,546098,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Korridor - Überschreitung',190,190,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547844,0,540331,540758,546099,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Korridor - Unterschreitung',200,200,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548159,0,540331,540758,546100,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können','Y','N','Y','Y','N','Abschlusskorrektur vorsehen',210,210,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548158,0,540331,540758,546101,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, unter dem ggf. die Differenz zu den in der Abschlusskorrektur gemeldeten Mengen in Rechnung gestellt wird.','Y','N','Y','Y','N','Produkt für Abschlusskorrektur',220,220,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548434,0,540331,540758,546102,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob Nach- bzw. Rückzahlungen erst nach Erfassung der Abschlusskorrektur in Rechnung zu stellen sind','Hinweis: Wenn dieses Häkchen gesetzt ist, werden  Nach- bzw. Rückzahlungen erst nach der Abschlusskorrektur in Rechnung gestellt. In diesem Fall fließt auch die Korrekturmeldung in das Verhältnis aus gemledtenen Einheiten und der tatsächlich erbrachten Leistung mit ein. Außerdem sind die unter "Datenerfassung" => "Pausch-Datensätze" im Feld "Nachzahlung/Erstattung" aufgeführten Werte in diesem Fall rein informativ.','Y','N','Y','Y','N','Verrechung erst nach Abschlusskorrektur',230,230,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548457,0,540331,540758,546103,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.','Y','N','Y','Y','N','Vertragsverlängerung/-übergang',240,240,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548110,0,540331,540758,546104,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Vertragsbedingungen müssen im Status "Fertiggestellt" sein, damit sie in laufenden Verträgen verwendet werden können.','Y','N','Y','Y','N','Belegstatus',250,250,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548111,0,540331,540758,546105,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs','You find the current status in the Document Status field. The options are listed in a popup','Y','N','Y','Y','N','Belegverarbeitung',260,260,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:58.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548113,0,540331,540758,546106,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',270,270,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551910,0,540331,540758,546107,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Keine Rechnungserstellung',280,280,0,TO_TIMESTAMP('2017-06-24 08:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540339,540321,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-24T08:31:59.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540321 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-24T08:31:59.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540440,540321,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540440,540759,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548108,0,540339,540759,546108,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Bestimmt die Reihenfolge, in der die Zuordnungs-Datensätze mit einer gegebenen Auftragszeile verglichen werden','Y','N','N','Y','N','Reihenfolge',0,10,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548098,0,540339,540759,546109,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Kosten','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)','Y','N','N','Y','N','Kosten',0,20,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548104,0,540339,540759,546110,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes, dass dem Vertrag unterliegt','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','Produkt-Kategorie',0,30,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548103,0,540339,540759,546111,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,40,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550426,0,540339,540759,546112,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Liefermenge pro Abolieferung',0,50,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550427,0,540339,540759,546113,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','Preissystem',0,60,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T08:31:59.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550428,0,540339,540759,546114,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.','Y','N','N','Y','N','Vertragsverlängerung/-übergang',0,70,0,TO_TIMESTAMP('2017-06-24 08:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

