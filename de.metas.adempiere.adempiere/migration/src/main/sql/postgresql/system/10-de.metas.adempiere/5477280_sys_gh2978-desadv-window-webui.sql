-- 2017-11-15T16:47:11.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540976,0,540256,TO_TIMESTAMP('2017-11-15 16:47:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','_EDI_Desadv','Y','N','N','Y','N','EDI Lieferavis (DESADV)',TO_TIMESTAMP('2017-11-15 16:47:11','YYYY-MM-DD HH24:MI:SS'),100,'EDI Lieferavis (DESADV)')
;

-- 2017-11-15T16:47:11.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540976 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-11-15T16:47:11.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540976, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540976)
;

-- 2017-11-15T16:47:12.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=500221 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=346 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53132 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=180 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=494 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=444 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540032 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540582 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540596 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540382 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:12.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=459, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:18.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2017-11-15T16:47:54.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-15 16:47:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='EDI DESADV',WEBUI_NameBrowse='EDI DESADV' WHERE AD_Menu_ID=540976 AND AD_Language='en_US'
;

-- 2017-11-15T16:52:09.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540662,540533,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-15T16:52:09.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540533 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-15T16:52:09.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540709,540533,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:09.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540710,540533,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:09.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540709,541226,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:09.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555440,0,540662,541226,549207,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','DESADV',0,10,0,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:09.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555439,0,540662,541226,549208,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',10,20,0,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:09.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555438,0,540662,541226,549209,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,30,0,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:09.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555444,0,540662,541226,549210,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Nr.',30,40,0,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555446,0,540662,541226,549211,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden. Zur Zeit schreibgeschätzt, um versehentliche Fehleingaben zu vermeiden.	','Y','N','Y','Y','N','Referenz',40,50,0,TO_TIMESTAMP('2017-11-15 16:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555441,0,540662,541226,549212,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',50,60,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555442,0,540222,549212,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555765,0,540662,541226,549213,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Übergabeadresse',60,70,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555766,0,540662,541226,549214,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung','Y','N','Y','Y','N','Rechnungsstandort',70,80,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555767,0,540662,541226,549215,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','Y','N','Währung',80,90,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556362,0,540662,541226,549216,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Geliefert %',90,100,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555443,0,540662,541226,549217,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','Y','Y','N','Auftragsdatum',100,110,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555445,0,540662,541226,549218,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.','Y','N','Y','Y','N','Bewegungsdatum',110,120,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556363,0,540662,541226,549219,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Geliefert % Minimum',120,130,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555448,0,540662,541226,549220,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','EDI Status Exportieren',130,140,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555763,0,540662,541226,549221,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','in Verarbeitung',140,150,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555449,0,540662,541226,549222,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',150,160,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555657,0,540662,541226,549223,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','EDI Fehlermeldung',160,170,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556936,0,540662,541226,549224,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Can be used to flag records and thus make them selectable from the UI via advanced search.','Y','N','Y','Y','N','UserFlag',170,180,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540664,540534,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-15T16:52:10.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540534 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-15T16:52:10.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540711,540534,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540711,541227,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555467,0,540664,541227,549225,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Nr.',0,10,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:10.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555787,0,540664,541227,549226,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','Auftrag',0,20,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555468,0,540664,541227,549227,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,30,0,TO_TIMESTAMP('2017-11-15 16:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555469,0,540223,549227,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555470,0,540664,541227,549228,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','Y','N','Auftragsdatum',0,40,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555471,0,540664,541227,549229,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.','Y','N','N','Y','N','Bewegungsdatum',0,50,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555472,0,540664,541227,549230,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','Referenz',0,60,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555475,0,540664,541227,549231,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','N','Belegstatus',0,70,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540663,540535,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-11-15T16:52:11.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540535 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-15T16:52:11.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540712,540535,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540712,541228,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555452,0,540663,541228,549232,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,10,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555457,0,540663,541228,549233,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','N','Zeile Nr.',0,20,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555458,0,540663,541228,549234,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,30,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555463,0,540663,541228,549235,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produktnummer',0,40,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555464,0,540663,541228,549236,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)','Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.','Y','N','N','Y','N','CU-EAN',0,50,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555460,0,540663,541228,549237,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','Y','N','N','Y','N','Produktbeschreibung',0,60,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555461,0,540663,541228,549238,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','N','N','Y','N','Auftragsmenge (CU)',0,70,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555773,0,540663,541228,549239,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)','Y','N','N','Y','N','Liefermenge (CU)',0,80,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555456,0,540663,541228,549240,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,90,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555772,0,540663,541228,549241,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.','Y','N','N','Y','N','Nachlieferung folgt',0,100,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555771,0,540663,541228,549242,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Verpackungskapazität',0,110,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555465,0,540663,541228,549243,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Loading Unit',0,120,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555462,0,540663,541228,549244,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','SSCC18',0,130,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555656,0,540663,541228,549245,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt das System dieses Feld auf "Ja" und der Nutzer kann dann eine SSCC18 Nummer eintragen.','Y','N','N','Y','N','manuelle SSCC18',0,140,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555454,0,540663,541228,549246,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','DESADV',0,150,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555451,0,540663,541228,549247,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,160,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555450,0,540663,541228,549248,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,170,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-15T16:52:11.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555453,0,540663,541228,549249,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','EDI_DesadvLine',0,180,0,TO_TIMESTAMP('2017-11-15 16:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

