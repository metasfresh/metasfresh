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

