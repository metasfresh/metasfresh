-- 2018-03-10T13:04:59.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541057,0,319,TO_TIMESTAMP('2018-03-10 13:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Pakete verwalten','D','_Packstück','Y','N','N','N','N','Packstück',TO_TIMESTAMP('2018-03-10 13:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Packstück')
;

-- 2018-03-10T13:04:59.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541057 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-03-10T13:04:59.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541057, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541057)
;

-- 2018-03-10T13:04:59.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2018-03-10T13:04:59.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:03.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2018-03-10T13:05:21.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-10 13:05:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Package',Description='',WEBUI_NameBrowse='Package' WHERE AD_Menu_ID=541057 AND AD_Language='en_US'
;

-- 2018-03-10T13:05:36.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,626,540681,TO_TIMESTAMP('2018-03-10 13:05:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:36','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-03-10T13:05:36.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540681 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-03-10T13:05:37.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540912,540681,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540913,540681,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540912,541517,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9261,0,626,541517,551257,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,10,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9263,0,626,541517,551258,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Nr.',20,20,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9253,0,626,541517,551259,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',30,30,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9260,0,626,541517,551260,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',40,40,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9254,0,626,541517,551261,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','Y','Y','N','Lieferung/Wareneingang',50,50,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9257,0,626,541517,551262,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','Y','N','Lieferweg',60,60,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10398,0,626,541517,551263,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Date/Time','Actual Date/Time of Shipment (pick up)','Y','N','Y','Y','N','Lieferdatum',70,70,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9259,0,626,541517,551264,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Tracking Info',80,80,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,541270,0,626,541517,551265,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Verpackung',90,90,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543873,0,626,541517,551266,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package','The Weight indicates the weight of the package','Y','N','Y','Y','N','Gewicht',100,100,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553754,0,626,541517,551267,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Package Net Total',110,110,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,541273,0,626,541517,551268,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',120,120,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553753,0,626,541517,551269,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Geschlossen',130,130,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551597,0,626,541517,551270,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ziel-Lager',140,140,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553752,0,626,541517,551271,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',150,150,0,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553755,0,540243,551271,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:37.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,627,540682,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-03-10T13:05:37.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540682 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-03-10T13:05:37.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540914,540682,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540914,541518,TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-03-10 13:05:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9271,0,627,541518,551272,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9266,0,627,541518,551273,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9267,0,627,541518,551274,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','Y','N','Packstück',0,30,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9264,0,627,541518,551275,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Wareneingangsbeleg','Y','N','N','Y','N','Lieferschein-/ Wareneingangsposition',0,40,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9270,0,627,541518,551276,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','Menge',0,50,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9269,0,627,541518,551277,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545092,0,627,541518,551278,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Sent Mail Notification',0,70,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540141,540683,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-03-10T13:05:38.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540683 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-03-10T13:05:38.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540915,540683,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540915,541519,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542997,0,540141,541519,551279,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,10,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542998,0,540141,541519,551280,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,20,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542999,0,540141,541519,551281,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,30,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543000,0,540141,541519,551282,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Name des Depots laut Routentabelle','Y','N','N','Y','N','Depotname',0,40,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543002,0,540141,541519,551283,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'3-stellig numerischer Ländercode nach ISO 3166 des Empfängers','Y','N','N','Y','N','Empfänger-Ländercode',0,50,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543003,0,540141,541519,551284,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl des Empfängers','Y','N','N','Y','N','Empfänger-PLZ',0,60,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543004,0,540141,541519,551285,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,70,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543005,0,540141,541519,551286,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,80,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543006,0,540141,541519,551287,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Gewicht eines Produktes','The Weight indicates the weight  of the product in the Weight UOM of the Client','Y','N','N','Y','N','Gewicht',0,90,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543007,0,540141,541519,551288,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Infotext für Scanart 18','Y','N','N','Y','N','Infotext',0,100,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543008,0,540141,541519,551289,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Kundenreferenz',0,110,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543009,0,540141,541519,551290,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,120,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543010,0,540141,541519,551291,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,130,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543011,0,540141,541519,551292,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Ort',0,140,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543012,0,540141,541519,551293,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','Y','N','Packstück',0,150,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543013,0,540141,541519,551294,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Paketscheinnummer',0,160,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543014,0,540141,541519,551295,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produkt Code',0,170,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543015,0,540141,541519,551296,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Referenz zur Ausrollistennummer','Y','N','N','Y','N','Ref-Ausrollistennummer',0,180,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543016,0,540141,541519,551297,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Route',0,190,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543017,0,540141,541519,551298,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Scanart',0,200,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543018,0,540141,541519,551299,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Scannendes Depot',0,210,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543019,0,540141,541519,551300,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Scandatum und -zeit','Y','N','N','Y','N','Scanzeitpunkt',0,220,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:38.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543020,0,540141,541519,551301,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Servicecode',0,230,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543021,0,540141,541519,551302,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Tournummer',0,240,0,TO_TIMESTAMP('2018-03-10 13:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543022,0,540141,541519,551303,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Name des Unterzeichners/Empfängers der Lieferung','Y','N','N','Y','N','Unterzeichner-Name',0,250,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543023,0,540141,541519,551304,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Zusatzcode oder Differenzcode','Y','N','N','Y','N','Zusatzcode-1',0,260,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543024,0,540141,541519,551305,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Zusatzcode oder Differenzcode','Y','N','N','Y','N','Zusatzcode-2',0,270,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543025,0,540141,541519,551306,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Zusatzcode oder Differenzcode','Y','N','N','Y','N','Zusatzcode-3',0,280,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540563,540684,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-03-10T13:05:39.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540684 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-03-10T13:05:39.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540916,540684,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540916,541520,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553739,0,540563,541520,551307,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','Y','N','Packstück',0,10,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553736,0,540563,541520,551308,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Handling Units',0,20,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-10T13:05:39.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553817,0,540563,541520,551309,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Picking Slot',0,30,0,TO_TIMESTAMP('2018-03-10 13:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

