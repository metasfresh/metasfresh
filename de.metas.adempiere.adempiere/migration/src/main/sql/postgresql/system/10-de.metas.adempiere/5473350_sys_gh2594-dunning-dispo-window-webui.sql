-- 2017-10-02T12:41:09.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540424,540512,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-10-02T12:41:09.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540512 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-10-02T12:41:09.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540684,540512,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540685,540512,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540684,541191,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550585,0,540424,541191,548963,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,10,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550582,0,540424,541191,548964,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Mahndatensatz-ID',20,20,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550574,0,540424,541191,548965,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','Y','N','Datensatz-ID',30,30,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550575,0,540424,541191,548966,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','Y','N','DB-Tabelle',40,40,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550591,0,540424,541191,548967,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Mahnstufe',50,50,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557045,0,540424,541191,548968,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Nr.',60,60,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550655,0,540424,541191,548969,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Date of Dunning','Y','N','Y','Y','N','Dunning Date',70,70,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551735,0,540424,541191,548970,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Effective Date of Dunning','Y','N','Y','Y','N','Dunning Date Effective',80,80,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550579,0,540424,541191,548971,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',90,90,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,550806,0,540214,548971,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550581,0,540424,541191,548972,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Mahnkontakt',100,100,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550656,0,540424,541191,548973,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem Zahlung fällig wird','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','N','Y','Y','N','Datum Fälligkeit',110,110,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:09.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550587,0,540424,541191,548974,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)','Y','N','Y','Y','N','Tage fällig',120,120,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550698,0,540424,541191,548975,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','Y','N','Währung',130,130,0,TO_TIMESTAMP('2017-10-02 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550580,0,540424,541191,548976,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.','Y','N','Y','Y','N','Mahnzins',140,140,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550578,0,540424,541191,548977,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Pauschale Mahngebühr','The Fee Amount indicates the charge amount on a dunning letter for overdue invoices.  This field will only display if the charge fee checkbox has been selected.','Y','N','Y','Y','N','Mahnpauschale',150,150,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550584,0,540424,541191,548978,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Offener Betrag',160,160,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550588,0,540424,541191,548979,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gesamtbetrag',170,170,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550805,0,540424,541191,548980,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Mahnkarenz',180,180,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550589,0,540424,541191,548981,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',190,190,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:41:10.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550810,0,540424,541191,548982,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsDunningDocProcessed',200,200,0,TO_TIMESTAMP('2017-10-02 12:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:44:15.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540684,541192,TO_TIMESTAMP('2017-10-02 12:44:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-10-02 12:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:44:36.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90,Updated=TO_TIMESTAMP('2017-10-02 12:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541191
;

-- 2017-10-02T12:46:18.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550579,0,540424,541192,548983,'F',TO_TIMESTAMP('2017-10-02 12:46:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner',10,0,0,TO_TIMESTAMP('2017-10-02 12:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:46:25.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,550806,0,540215,548983,TO_TIMESTAMP('2017-10-02 12:46:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-10-02 12:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:46:35.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,550581,0,540216,548983,TO_TIMESTAMP('2017-10-02 12:46:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-10-02 12:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:48:33.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540685,541193,TO_TIMESTAMP('2017-10-02 12:48:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-10-02 12:48:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:48:46.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550589,0,540424,541193,548984,'F',TO_TIMESTAMP('2017-10-02 12:48:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2017-10-02 12:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:48:56.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550810,0,540424,541193,548985,'F',TO_TIMESTAMP('2017-10-02 12:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnung erstellt',20,0,0,TO_TIMESTAMP('2017-10-02 12:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:51:34.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557045,0,540424,541192,548986,'F',TO_TIMESTAMP('2017-10-02 12:51:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Nr.',20,0,0,TO_TIMESTAMP('2017-10-02 12:51:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:52:02.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550575,0,540424,541192,548987,'F',TO_TIMESTAMP('2017-10-02 12:52:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tabelle',30,0,0,TO_TIMESTAMP('2017-10-02 12:52:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:52:11.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550574,0,540424,541192,548988,'F',TO_TIMESTAMP('2017-10-02 12:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Datensatz',40,0,0,TO_TIMESTAMP('2017-10-02 12:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:52:47.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540684,541194,TO_TIMESTAMP('2017-10-02 12:52:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','dunning',20,TO_TIMESTAMP('2017-10-02 12:52:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:53:03.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550584,0,540424,541194,548989,'F',TO_TIMESTAMP('2017-10-02 12:53:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Offener Betrag',10,0,0,TO_TIMESTAMP('2017-10-02 12:53:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:53:16.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550580,0,540424,541194,548990,'F',TO_TIMESTAMP('2017-10-02 12:53:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnzins',20,0,0,TO_TIMESTAMP('2017-10-02 12:53:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:53:28.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550805,0,540424,541194,548991,'F',TO_TIMESTAMP('2017-10-02 12:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnkarenz',30,0,0,TO_TIMESTAMP('2017-10-02 12:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:53:43.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550588,0,540424,541194,548992,'F',TO_TIMESTAMP('2017-10-02 12:53:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesamtbetrag',40,0,0,TO_TIMESTAMP('2017-10-02 12:53:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:53:55.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550698,0,540424,541194,548993,'F',TO_TIMESTAMP('2017-10-02 12:53:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währung',50,0,0,TO_TIMESTAMP('2017-10-02 12:53:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:54:27.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550591,0,540424,541192,548994,'F',TO_TIMESTAMP('2017-10-02 12:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnstufe',50,0,0,TO_TIMESTAMP('2017-10-02 12:54:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:54:50.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540685,541195,TO_TIMESTAMP('2017-10-02 12:54:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-10-02 12:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:55:03.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550585,0,540424,541195,548995,'F',TO_TIMESTAMP('2017-10-02 12:55:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-10-02 12:55:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:55:15.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550583,0,540424,541195,548996,'F',TO_TIMESTAMP('2017-10-02 12:55:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-10-02 12:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:56:54.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548963
;

-- 2017-10-02T12:56:54.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548964
;

-- 2017-10-02T12:56:54.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548965
;

-- 2017-10-02T12:56:54.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548966
;

-- 2017-10-02T12:56:54.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548967
;

-- 2017-10-02T12:56:54.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548968
;

-- 2017-10-02T12:57:27.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548975
;

-- 2017-10-02T12:57:27.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548976
;

-- 2017-10-02T12:57:44.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548972
;

-- 2017-10-02T12:57:50.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548978
;

-- 2017-10-02T12:57:50.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548979
;

-- 2017-10-02T12:58:06.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548977
;

-- 2017-10-02T12:58:06.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548980
;

-- 2017-10-02T12:58:06.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548981
;

-- 2017-10-02T12:58:06.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548982
;

-- 2017-10-02T12:58:12.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540214
;

-- 2017-10-02T12:58:16.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548971
;

-- 2017-10-02T12:58:44.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540685,541196,TO_TIMESTAMP('2017-10-02 12:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2017-10-02 12:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-02T12:59:05.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541196, SeqNo=10,Updated=TO_TIMESTAMP('2017-10-02 12:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548973
;

-- 2017-10-02T12:59:23.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541194, SeqNo=60,Updated=TO_TIMESTAMP('2017-10-02 12:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548974
;

-- 2017-10-02T12:59:34.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541196, SeqNo=20,Updated=TO_TIMESTAMP('2017-10-02 12:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548970
;

-- 2017-10-02T12:59:42.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541196, SeqNo=30,Updated=TO_TIMESTAMP('2017-10-02 12:59:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548969
;

-- 2017-10-02T12:59:46.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541191
;

-- 2017-10-02T13:01:20.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541196, SeqNo=40,Updated=TO_TIMESTAMP('2017-10-02 13:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548991
;

-- 2017-10-02T13:45:07.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2017-10-02 13:45:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547399
;

-- 2017-10-02T13:45:09.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2017-10-02 13:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547399
;

-- 2017-10-02T13:48:32.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:48:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Business Partner',Description='',Help='' WHERE AD_Field_ID=550579 AND AD_Language='en_US'
;

-- 2017-10-02T13:48:51.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:48:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dunning Level' WHERE AD_Field_ID=550591 AND AD_Language='en_US'
;

-- 2017-10-02T13:49:08.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:49:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Open Amount' WHERE AD_Field_ID=550584 AND AD_Language='en_US'
;

-- 2017-10-02T13:49:20.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:49:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Total Amount' WHERE AD_Field_ID=550588 AND AD_Language='en_US'
;

-- 2017-10-02T13:49:40.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:49:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Days Due',Description='' WHERE AD_Field_ID=550587 AND AD_Language='en_US'
;

-- 2017-10-02T13:50:00.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:50:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Currency',Description='',Help='' WHERE AD_Field_ID=550698 AND AD_Language='en_US'
;

-- 2017-10-02T13:50:26.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:50:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dunning Interest Amount',Description='' WHERE AD_Field_ID=550580 AND AD_Language='en_US'
;

-- 2017-10-02T13:50:49.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:50:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Processed',Description='',Help='' WHERE AD_Field_ID=550589 AND AD_Language='en_US'
;

-- 2017-10-02T13:51:03.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahnung erstellt',Updated=TO_TIMESTAMP('2017-10-02 13:51:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550810
;

-- 2017-10-02T13:51:14.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:51:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dunning processed' WHERE AD_Field_ID=550810 AND AD_Language='en_US'
;

-- 2017-10-02T13:51:32.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:51:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Due Date',Description='',Help='' WHERE AD_Field_ID=550656 AND AD_Language='en_US'
;

-- 2017-10-02T13:52:19.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-02 13:52:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dunning Grace' WHERE AD_Field_ID=550805 AND AD_Language='en_US'
;

-- 2017-10-02T13:53:40.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahn Datum Effektiv',Updated=TO_TIMESTAMP('2017-10-02 13:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551735
;

-- 2017-10-02T13:53:50.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahn Datum',Updated=TO_TIMESTAMP('2017-10-02 13:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550655
;

-- 2017-10-02T13:54:12.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahn Datum effektiv',Updated=TO_TIMESTAMP('2017-10-02 13:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551735
;

-- 2017-10-02T13:54:22.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahndatum effektiv',Updated=TO_TIMESTAMP('2017-10-02 13:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551735
;

-- 2017-10-02T13:54:26.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahndatum',Updated=TO_TIMESTAMP('2017-10-02 13:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550655
;

-- 2017-10-02T13:56:50.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548983
;

-- 2017-10-02T13:56:50.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548986
;

-- 2017-10-02T13:56:50.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548987
;

-- 2017-10-02T13:56:50.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548988
;

-- 2017-10-02T13:56:50.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548994
;

-- 2017-10-02T13:56:50.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548989
;

-- 2017-10-02T13:56:50.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548990
;

-- 2017-10-02T13:56:50.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548992
;

-- 2017-10-02T13:56:50.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548993
;

-- 2017-10-02T13:56:50.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548974
;

-- 2017-10-02T13:56:50.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548984
;

-- 2017-10-02T13:56:50.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548985
;

-- 2017-10-02T13:56:50.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548973
;

-- 2017-10-02T13:56:50.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548970
;

-- 2017-10-02T13:56:50.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548969
;

-- 2017-10-02T13:56:50.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548991
;

-- 2017-10-02T13:56:50.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-10-02 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548995
;

-- 2017-10-02T13:57:05.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-10-02 13:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548983
;

-- 2017-10-02T13:57:05.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-10-02 13:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548986
;

-- 2017-10-02T13:57:05.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-10-02 13:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548987
;

-- 2017-10-02T13:57:05.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-10-02 13:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548995
;

-- 2017-10-02T14:03:32.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2017-10-02 14:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547345
;

-- 2017-10-02T14:03:32.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2017-10-02 14:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554811
;

-- 2017-10-02T14:03:32.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2017-10-02 14:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547608
;

-- 2017-10-02T14:03:32.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2017-10-02 14:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548501
;

-- 2017-10-02T14:03:32.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2017-10-02 14:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547341
;

-- 2017-10-02T14:03:32.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2017-10-02 14:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547325
;

-- 2017-10-02T14:03:56.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2017-10-02 14:03:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548764
;

