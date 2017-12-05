-- 2017-11-29T12:28:03.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540983,0,172,TO_TIMESTAMP('2017-11-29 12:28:02','YYYY-MM-DD HH24:MI:SS'),100,'Import Geschäftspartner','de.metas.contracts','_Import_Geschäftspartner','Y','N','N','N','N','Import Geschäftspartner',TO_TIMESTAMP('2017-11-29 12:28:02','YYYY-MM-DD HH24:MI:SS'),100,'Import Geschäftspartner')
;

-- 2017-11-29T12:28:03.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540983 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-11-29T12:28:03.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540983, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540983)
;

-- 2017-11-29T12:28:03.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53246 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=495 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50007 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=362 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=475 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=366 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=483 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=368 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=508 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540589 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:03.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=367, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:15.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Description='Import Geschäftspartner', Name='Import Geschäftspartner',Updated=TO_TIMESTAMP('2017-11-29 12:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=172
;

-- 2017-11-29T12:28:15.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Import Geschäftspartner', IsActive='Y', Name='Import Geschäftspartner',Updated=TO_TIMESTAMP('2017-11-29 12:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=185
;

-- 2017-11-29T12:28:15.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Description='Import Geschäftspartner', Help='Das Fenster "Import Geschäftspartner" zeigt den Inhalt einer Zwischentabelle, die beim Import von Geschäftspartnern verwendet wird. Der Start des Prozesses "Verarbeiten" fügt Einträge hinzu oder modifiziert die relevanten Einträge.', Name='Import Geschäftspartner',Updated=TO_TIMESTAMP('2017-11-29 12:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=138
;

-- 2017-11-29T12:28:56.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2017-11-29T12:28:56.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2017-11-29T12:29:33.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,441,540546,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-29T12:29:33.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540546 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-29T12:29:33.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540728,540546,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540729,540546,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540728,541262,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5926,0,441,541262,549428,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Import - Geschäftspartner',10,10,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5937,0,441,541262,549429,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','Y','Y','N','Importiert',20,20,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5955,0,441,541262,549430,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',30,30,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5938,0,441,541262,549431,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','Y','Y','N','Import-Fehlermeldung',40,40,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5939,0,441,541262,549432,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',50,50,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560543,0,441,541262,549433,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel der Organisation','Y','N','Y','Y','N','Organisations-Schlüssel',60,60,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5942,0,441,541262,549434,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',70,70,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5936,0,441,541262,549435,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',80,80,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556301,0,441,541262,549436,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firmenname',90,90,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5950,0,441,541262,549437,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',100,100,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5935,0,441,541262,549438,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',110,110,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:33.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560604,0,441,541262,549439,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Short Description',120,120,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5953,0,441,541262,549440,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Steuernummer','"Steuer-ID" gibt die offizielle Steuernummer für diese Einheit an.','Y','N','Y','Y','N','Steuer-ID',130,130,0,TO_TIMESTAMP('2017-11-29 12:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6050,0,441,541262,549441,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppen-Schlüssel','Y','N','Y','Y','N','Gruppen-Schlüssel',140,140,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6051,0,441,541262,549442,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','Y','N','Geschäftspartnergruppe',150,150,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5943,0,540225,549430,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5948,0,441,541262,549443,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort','"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','N','Y','Y','N','Straße und Nr.',160,160,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5947,0,441,541262,549444,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 2 für diesen Standort','"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','Y','Y','N','Adresszusatz',170,170,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560399,0,441,541262,549445,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeilee 3 für diesen Standort','"Adresszeile 2" bietet weitere Adressangaben für diesen Standort. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','Y','Y','N','Adresszeile 3',180,180,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560400,0,441,541262,549446,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 4 für diesen Standort','The Address 4 provides additional address information for an entity. Z.B. Gebäudenummer, Stockwerk, Raumnummer o.ä.','Y','N','Y','Y','N','Adresszusatz',190,190,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5927,0,441,541262,549447,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','N','Y','Y','N','PLZ',200,200,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5933,0,441,541262,549448,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Additional ZIP or Postal code','The Additional ZIP or Postal Code identifies, if appropriate, any additional Postal Code information.','Y','N','Y','Y','N','-',210,210,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5945,0,441,541262,549449,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','N','Y','Y','N','Ort',220,220,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5951,0,441,541262,549450,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Name der Region','"Region" definiert die Bezeichnung, die bei der Verwendung in einem Dokument gedruckt wird.','Y','N','Y','Y','N','Region',230,230,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5954,0,441,541262,549451,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert eine geographische Region','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','Y','Y','N','Region',240,240,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5918,0,441,541262,549452,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html','Für Details - http://www.din.de/gremien/nas/nabd/iso3166ma/codlstp1.html oder - http://www.unece.org/trade/rec/rec03en.htm','Y','N','Y','Y','N','ISO Ländercode',250,250,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5949,0,441,541262,549453,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','Y','N','Land',260,260,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5922,0,540226,549430,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5929,0,441,541262,549454,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag','"Titel" gibt die Bezeichnung für diesen Eintrag an.','Y','N','Y','Y','N','Titel',270,270,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556302,0,441,541262,549455,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','Y','Y','N','Vorname',280,280,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556303,0,441,541262,549456,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Nachname',290,290,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5946,0,441,541262,549457,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibung des Kontaktes','Y','N','Y','Y','N','Kontakt-Beschreibung',300,300,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559884,0,441,541262,549458,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Standard-Ansprechpartner',310,310,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:34.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5920,0,441,541262,549459,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','N','Y','Y','N','Bemerkungen',320,320,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5921,0,441,541262,549460,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Beschreibt eine Telefon Nummer','Y','N','Y','Y','N','Telefon',330,330,0,TO_TIMESTAMP('2017-11-29 12:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5923,0,441,541262,549461,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.','Y','N','Y','Y','N','Mobil',340,340,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5952,0,441,541262,549462,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','Y','Y','N','Fax',350,350,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5930,0,441,541262,549463,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','Geburtstag oder Jahrestag','Y','N','Y','Y','N','Geburtstag',360,360,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5944,0,441,541262,549464,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','Y','N','eMail',370,370,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5919,0,441,541262,549465,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Anrede für den Geschäftspartner-Kontakt','Y','N','Y','Y','N','Kontakt-Anrede',380,380,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5934,0,441,541262,549466,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','Y','Y','N','Anrede',390,390,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58038,0,441,541262,549467,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this Business Partner is a Customer','The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.','Y','N','Y','Y','N','Customer',400,400,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58039,0,441,541262,549468,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if  this Business Partner is an employee','The Employee checkbox indicates if this Business Partner is an Employee.  If it is selected, additional fields will display which further identify this employee.','Y','N','Y','Y','N','Employee',410,410,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58040,0,441,541262,549469,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this Business Partner is a Vendor','The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.','Y','N','Y','Y','N','Vendor',420,420,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557474,0,441,541262,549470,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','Y','Y','N','Lieferstandard',430,430,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559883,0,441,541262,549471,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Liefer Standard Adresse',440,440,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557475,0,441,541262,549472,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner','Wenn "Rechnungs-Adresse" selektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','Y','N','Y','Y','N','Vorbelegung Rechnung',450,450,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559882,0,441,541262,549473,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Rechnung Standard Adresse',460,460,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559903,0,441,541262,549474,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Rechnungskontakt',470,470,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559904,0,441,541262,549475,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferkontakt',480,480,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559947,0,441,541262,549476,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','Y','N','Sprache',490,490,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5928,0,441,541262,549477,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','Y','N','N','Name Zusatz',500,0,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560563,0,441,541262,549478,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','Y','Y','N','Name3',510,500,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560565,0,441,541262,549479,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','SEPA Signed',520,510,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560564,0,441,541262,549480,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Pharmaproductpermlaw52',530,520,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2017-11-29T12:29:35.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560566,0,441,541262,549481,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Pharmacie Permission',540,530,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2017-11-29T12:29:35.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560578,0,441,541262,549482,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm','Y','N','Y','Y','N','Swift code',550,540,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560579,0,441,541262,549483,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.','Y','N','Y','Y','N','IBAN',560,550,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560581,0,441,541262,549484,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','Y','Y','N','Bankverbindung',570,560,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560597,0,441,541262,549485,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Ersten Verkaufs','"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.','Y','N','Y','Y','N','Erster Verkauf',580,570,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560580,0,441,541262,549486,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Status Terminplan',590,580,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560598,0,441,541262,549487,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.','Y','N','Y','Y','N','Terminplan Rechnung',600,590,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:36.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560577,0,441,541262,549488,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','Y','N','Zahlungsweise',610,600,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:36.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560600,0,441,541262,549489,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100,'Möglichkeiten der Bezahlung einer Bestellung','"Zahlungsweise" zeigt die Arten der Zahlungen für Einkäufe an.','Y','N','Y','Y','N','Zahlungsweise',620,610,0,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:36.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560601,0,441,541262,549490,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100,'Zahlungskondition','Y','N','Y','Y','N','Zahlungskondition',630,620,0,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:36.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560602,0,441,541262,549491,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100,'Zahlungskondition für die Bestellung','Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.','Y','N','Y','Y','N','Zahlungskondition',640,630,0,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:36.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5941,0,441,541262,549492,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner importieren','Die Standardwerte gelten für Felder mit Inhalt "NULL", sie überschreiben keine Werte.','Y','N','Y','Y','N','Geschäftspartner importieren',650,640,0,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:36.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5924,0,441,541262,549493,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',660,650,0,TO_TIMESTAMP('2017-11-29 12:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T12:50:43.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540728,541263,TO_TIMESTAMP('2017-11-30 12:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-30 12:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T12:51:00.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90,Updated=TO_TIMESTAMP('2017-11-30 12:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541262
;

-- 2017-11-30T12:52:18.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5955,0,441,541263,549496,'F',TO_TIMESTAMP('2017-11-30 12:52:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner',10,0,0,TO_TIMESTAMP('2017-11-30 12:52:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T12:52:33.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556301,0,441,541263,549497,'F',TO_TIMESTAMP('2017-11-30 12:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Firmenname',20,0,0,TO_TIMESTAMP('2017-11-30 12:52:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T12:52:59.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556302,0,441,541263,549498,'F',TO_TIMESTAMP('2017-11-30 12:52:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorname',30,0,0,TO_TIMESTAMP('2017-11-30 12:52:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T12:53:07.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556303,0,441,541263,549499,'F',TO_TIMESTAMP('2017-11-30 12:53:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Nachname',40,0,0,TO_TIMESTAMP('2017-11-30 12:53:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:03:12.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5950,0,441,541263,549500,'F',TO_TIMESTAMP('2017-11-30 13:03:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',50,0,0,TO_TIMESTAMP('2017-11-30 13:03:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:03:17.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-11-30 13:03:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549500
;

-- 2017-11-30T13:03:19.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-11-30 13:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549497
;

-- 2017-11-30T13:03:22.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-30 13:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549498
;

-- 2017-11-30T13:03:24.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-11-30 13:03:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549499
;

-- 2017-11-30T13:03:43.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540728,541264,TO_TIMESTAMP('2017-11-30 13:03:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','address',20,TO_TIMESTAMP('2017-11-30 13:03:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:04:40.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5948,0,441,541264,549501,'F',TO_TIMESTAMP('2017-11-30 13:04:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Strasse und Nr.',10,0,0,TO_TIMESTAMP('2017-11-30 13:04:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:04:54.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5947,0,441,541264,549502,'F',TO_TIMESTAMP('2017-11-30 13:04:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Adresszusatz',20,0,0,TO_TIMESTAMP('2017-11-30 13:04:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:05:07.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560399,0,441,541264,549503,'F',TO_TIMESTAMP('2017-11-30 13:05:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Adresszeile 3',30,0,0,TO_TIMESTAMP('2017-11-30 13:05:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:05:19.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560400,0,441,541264,549504,'F',TO_TIMESTAMP('2017-11-30 13:05:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Adresszeile 4',40,0,0,TO_TIMESTAMP('2017-11-30 13:05:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:05:28.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5927,0,441,541264,549505,'F',TO_TIMESTAMP('2017-11-30 13:05:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','PLZ',50,0,0,TO_TIMESTAMP('2017-11-30 13:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:05:38.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5945,0,441,541264,549506,'F',TO_TIMESTAMP('2017-11-30 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ort',60,0,0,TO_TIMESTAMP('2017-11-30 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:06:19.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5949,0,441,541264,549507,'F',TO_TIMESTAMP('2017-11-30 13:06:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Land',70,0,0,TO_TIMESTAMP('2017-11-30 13:06:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:06:32.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540729,541265,TO_TIMESTAMP('2017-11-30 13:06:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-11-30 13:06:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:06:49.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5937,0,441,541265,549508,'F',TO_TIMESTAMP('2017-11-30 13:06:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Importiert',10,0,0,TO_TIMESTAMP('2017-11-30 13:06:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:07:07.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58038,0,441,541265,549509,'F',TO_TIMESTAMP('2017-11-30 13:07:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kunde',20,0,0,TO_TIMESTAMP('2017-11-30 13:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:07:18.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58040,0,441,541265,549510,'F',TO_TIMESTAMP('2017-11-30 13:07:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferant',30,0,0,TO_TIMESTAMP('2017-11-30 13:07:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T13:07:33.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58039,0,441,541265,549511,'F',TO_TIMESTAMP('2017-11-30 13:07:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mitarbeiter',40,0,0,TO_TIMESTAMP('2017-11-30 13:07:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:10:28.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540729,541266,TO_TIMESTAMP('2017-11-30 14:10:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-11-30 14:10:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:10:40.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5942,0,441,541266,549512,'F',TO_TIMESTAMP('2017-11-30 14:10:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-11-30 14:10:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:10:49.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5939,0,441,541266,549513,'F',TO_TIMESTAMP('2017-11-30 14:10:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-11-30 14:10:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:11:45.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540729,541267,TO_TIMESTAMP('2017-11-30 14:11:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','div',20,TO_TIMESTAMP('2017-11-30 14:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:11:48.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-11-30 14:11:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541266
;

-- 2017-11-30T14:12:07.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5938,0,441,541267,549514,'F',TO_TIMESTAMP('2017-11-30 14:12:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Fehlermeldung',10,0,0,TO_TIMESTAMP('2017-11-30 14:12:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:13:22.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5924,0,441,541265,549515,'F',TO_TIMESTAMP('2017-11-30 14:13:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',50,0,0,TO_TIMESTAMP('2017-11-30 14:13:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:20:04.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549435
;

-- 2017-11-30T14:20:27.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5936,0,441,541263,549516,'F',TO_TIMESTAMP('2017-11-30 14:20:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',5,0,0,TO_TIMESTAMP('2017-11-30 14:20:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:20:33.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-11-30 14:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549516
;

-- 2017-11-30T14:20:36.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-11-30 14:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549496
;

-- 2017-11-30T14:20:38.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-11-30 14:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549500
;

-- 2017-11-30T14:20:40.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-30 14:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549497
;

-- 2017-11-30T14:20:41.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-11-30 14:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549498
;

-- 2017-11-30T14:20:46.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-11-30 14:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549499
;

-- 2017-11-30T14:23:02.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560581,0,441,541267,549517,'F',TO_TIMESTAMP('2017-11-30 14:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bankverbindung',20,0,0,TO_TIMESTAMP('2017-11-30 14:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:23:10.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560579,0,441,541267,549518,'F',TO_TIMESTAMP('2017-11-30 14:23:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','IBAN',30,0,0,TO_TIMESTAMP('2017-11-30 14:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:23:22.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560578,0,441,541267,549519,'F',TO_TIMESTAMP('2017-11-30 14:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Swift code',40,0,0,TO_TIMESTAMP('2017-11-30 14:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:24:04.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5953,0,441,541267,549520,'F',TO_TIMESTAMP('2017-11-30 14:24:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Steuer ID',50,0,0,TO_TIMESTAMP('2017-11-30 14:24:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:24:43.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549440
;

-- 2017-11-30T14:24:43.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549429
;

-- 2017-11-30T14:24:47.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549431
;

-- 2017-11-30T14:25:21.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5943,0,540227,549496,TO_TIMESTAMP('2017-11-30 14:25:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 14:25:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:25:26.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5922,0,540228,549496,TO_TIMESTAMP('2017-11-30 14:25:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-30 14:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:25:43.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540225
;

-- 2017-11-30T14:25:43.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540226
;

-- 2017-11-30T14:25:53.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549430
;

-- 2017-11-30T14:25:53.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549432
;

-- 2017-11-30T14:25:53.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549434
;

-- 2017-11-30T14:25:53.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549436
;

-- 2017-11-30T14:26:04.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549437
;

-- 2017-11-30T14:26:04.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549447
;

-- 2017-11-30T14:26:04.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549449
;

-- 2017-11-30T14:26:53.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549467
;

-- 2017-11-30T14:26:53.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549468
;

-- 2017-11-30T14:26:53.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549469
;

-- 2017-11-30T14:26:53.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549482
;

-- 2017-11-30T14:26:53.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549483
;

-- 2017-11-30T14:26:53.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549484
;

-- 2017-11-30T14:26:53.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549493
;

-- 2017-11-30T14:27:14.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559947,0,441,541267,549521,'F',TO_TIMESTAMP('2017-11-30 14:27:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sprache',60,0,0,TO_TIMESTAMP('2017-11-30 14:27:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-30T14:29:05.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549443
;

-- 2017-11-30T14:29:09.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549444
;

-- 2017-11-30T14:29:11.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549445
;

-- 2017-11-30T14:29:19.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549446
;

-- 2017-11-30T14:29:27.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549453
;

-- 2017-11-30T14:29:55.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,441,540547,TO_TIMESTAMP('2017-11-30 14:29:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-30 14:29:55','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-11-30T14:29:55.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540547 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-30T14:29:58.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540730,540547,TO_TIMESTAMP('2017-11-30 14:29:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-30 14:29:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-01T12:23:01.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540730, SeqNo=10,Updated=TO_TIMESTAMP('2017-12-01 12:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541262
;

-- 2017-12-01T12:23:01.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540730, SeqNo=10,Updated=TO_TIMESTAMP('2017-12-01 12:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541262
;

-- 2017-12-01T12:42:16.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Field SET Name='Apotheken Zulassung',Updated=TO_TIMESTAMP('2017-12-01 12:42:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560566
-- ;

-- 2017-12-01T12:52:11.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Field SET Name='Arzneimittel Gesetz §52a',Updated=TO_TIMESTAMP('2017-12-01 12:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560564
-- ;

-- 2017-12-01T12:52:21.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontakt Name',Updated=TO_TIMESTAMP('2017-12-01 12:52:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5956
;

-- 2017-12-01T12:52:40.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SEPA Unterzeichnung',Updated=TO_TIMESTAMP('2017-12-01 12:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560565
;

-- 2017-12-01T12:52:47.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunde',Updated=TO_TIMESTAMP('2017-12-01 12:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58038
;

-- 2017-12-01T12:52:52.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mitarbeiter',Updated=TO_TIMESTAMP('2017-12-01 12:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58039
;

-- 2017-12-01T12:53:00.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant',Updated=TO_TIMESTAMP('2017-12-01 12:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58040
;

-- 2017-12-01T12:58:43.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549448
;

-- 2017-12-01T12:58:43.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549466
;

-- 2017-12-01T12:58:43.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549459
;

-- 2017-12-01T12:58:43.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549438
;

-- 2017-12-01T12:58:43.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549485
;

-- 2017-12-01T12:58:43.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549462
;

-- 2017-12-01T12:58:43.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549463
;

-- 2017-12-01T12:58:43.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549492
;

-- 2017-12-01T12:58:43.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549442
;

-- 2017-12-01T12:58:43.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549441
;

-- 2017-12-01T12:58:43.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549452
;

-- 2017-12-01T12:58:43.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549428
;

-- 2017-12-01T12:58:43.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549465
;

-- 2017-12-01T12:58:43.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549457
;

-- 2017-12-01T12:58:43.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549471
;

-- 2017-12-01T12:58:43.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549475
;

-- 2017-12-01T12:58:43.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549470
;

-- 2017-12-01T12:58:43.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549461
;

-- 2017-12-01T12:58:43.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549456
;

-- 2017-12-01T12:58:43.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549478
;

-- 2017-12-01T12:58:43.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549433
;

-- 2017-12-01T12:58:43.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549481
-- ;

-- 2017-12-01T12:58:43.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549480
-- ;

-- 2017-12-01T12:58:43.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549473
;

-- 2017-12-01T12:58:43.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549474
;

-- 2017-12-01T12:58:43.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549451
;

-- 2017-12-01T12:58:43.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549450
;

-- 2017-12-01T12:58:43.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549479
;

-- 2017-12-01T12:58:43.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549439
;

-- 2017-12-01T12:58:43.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549476
;

-- 2017-12-01T12:58:43.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549458
;

-- 2017-12-01T12:58:43.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549486
;

-- 2017-12-01T12:58:43.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549460
;

-- 2017-12-01T12:58:43.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549487
;

-- 2017-12-01T12:58:43.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549454
;

-- 2017-12-01T12:58:43.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549472
;

-- 2017-12-01T12:58:43.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549455
;

-- 2017-12-01T12:58:43.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549491
;

-- 2017-12-01T12:58:43.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549490
;

-- 2017-12-01T12:58:43.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549489
;

-- 2017-12-01T12:58:43.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549488
;

-- 2017-12-01T12:58:43.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549464
;

-- 2017-12-01T12:58:43.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549516
;

-- 2017-12-01T12:58:43.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549496
;

-- 2017-12-01T12:58:43.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549500
;

-- 2017-12-01T12:58:43.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549497
;

-- 2017-12-01T12:59:04.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549455
;

-- 2017-12-01T12:59:07.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549456
;

-- 2017-12-01T13:01:11.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549498
;

-- 2017-12-01T13:01:11.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549499
;

-- 2017-12-01T13:01:11.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549501
;

-- 2017-12-01T13:01:11.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549505
;

-- 2017-12-01T13:01:11.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549506
;

-- 2017-12-01T13:01:11.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549507
;

-- 2017-12-01T13:01:11.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-12-01 13:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549512
;

-- 2017-12-01T13:01:27.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-12-01 13:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549515
;

-- 2017-12-01T13:01:27.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-12-01 13:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549508
;

-- 2017-12-01T13:01:27.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-12-01 13:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549512
;

-- 2017-12-01T13:04:10.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-12-01 13:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549516
;

-- 2017-12-01T13:04:10.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-12-01 13:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549496
;

-- 2017-12-01T13:04:10.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-12-01 13:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549508
;

-- 2017-12-01T13:04:10.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-12-01 13:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549512
;

