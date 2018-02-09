-- 2018-02-06T14:23:45.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-06 14:23:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Business Partner Pharma',Description='',Help='' WHERE AD_Window_ID=540409 AND AD_Language='en_US'
;

-- 2018-02-06T14:24:58.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-06 14:24:58','YYYY-MM-DD HH24:MI:SS'),Name='Business Partner Pharma',WEBUI_NameBrowse='Business Partner Pharma' WHERE AD_Menu_ID=541033 AND AD_Language='en_US'
;

-- 2018-02-06T14:31:15.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541012,540631,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:15.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540631 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:15.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540847,540631,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540848,540631,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540847,541431,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562058,0,541012,541431,550569,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562059,0,541012,541431,550570,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562060,0,541012,541431,550571,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562061,0,541012,541431,550572,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562062,0,541012,541431,550573,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','Y','Y','N','Name Zusatz',50,50,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562063,0,541012,541431,550574,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','Y','Y','N','Name3',60,60,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562064,0,541012,541431,550575,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',70,70,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562065,0,541012,541431,550576,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Short Description',80,80,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:15.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562066,0,541012,541431,550577,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',90,90,0,TO_TIMESTAMP('2018-02-06 14:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562067,0,541012,541431,550578,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Anbauplanung',100,100,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562068,0,541012,541431,550579,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firma',110,110,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562069,0,541012,541431,550580,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firmenname',120,120,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562070,0,541012,541431,550581,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Umsatzsteuer-ID',130,130,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562071,0,541012,541431,550582,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','Y','N','Geschäftspartnergruppe',140,140,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562072,0,541012,541431,550583,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','Y','N','Sprache',150,150,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562073,0,541012,541431,550584,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege','Y','N','Y','Y','N','Erhält EDI-Belege',160,160,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562074,0,541012,541431,550585,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'EDI-ID des Dateiempfängers','Y','N','Y','Y','N','EDI-ID des Dateiempfängers',170,170,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562075,0,541012,541431,550586,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.','Y','N','Y','Y','N','"CU pro TU" bei unbestimmter Verpackungskapazität',180,180,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562076,0,541012,541431,550587,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org','The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','Y','Y','N','URL',190,190,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562077,0,541012,541431,550588,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Rechnung per eMail',200,200,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562078,0,541012,541431,550589,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.','Y','N','Y','Y','N','Autom. Referenz',210,210,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562079,0,541012,541431,550590,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen','Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>','Y','N','Y','Y','N','Referenz Vorgabe',220,220,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562080,0,541012,541431,550591,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Mitarbeiter','Indicates the number of employees for this Business Partner.  This field displays only for Prospects.','Y','N','Y','Y','N','Anzahl Beschäftigte',230,230,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562081,0,541012,541431,550592,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','Y','Y','N','Replikations-Standardwert',240,240,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562082,0,541012,541431,550593,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsSourceSupplyCert',250,250,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562083,0,541012,541431,550594,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Logo',260,260,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562084,0,541012,541431,550595,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','MRP ausschliessen',270,270,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562085,0,541012,541431,550596,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Memo Gebinde','Y','N','Y','Y','N','Memo_HU',280,280,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562086,0,541012,541431,550597,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Memo Lieferung','Y','N','Y','Y','N','Memo_Delivery',290,290,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562087,0,541012,541431,550598,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Memo Abrechnung','Y','N','Y','Y','N','Memo_Invoicing',300,300,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562088,0,541012,541431,550599,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text','Y','N','Y','Y','N','Memo',310,310,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562089,0,541012,541431,550600,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','N','Y','N','N','Lieferart',320,0,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562090,0,541012,541431,550601,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Pharmacie Permission',330,320,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562091,0,541012,541431,550602,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','SEPA Signed',340,330,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562092,0,541012,541431,550603,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Pharmaproductpermlaw52',350,340,0,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541013,540632,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:16.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540632 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:16.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540849,540632,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:16.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540849,541432,TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562116,0,541013,541432,550604,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562117,0,541013,541432,550605,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562118,0,541013,541432,550606,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Type of request (e.g. Inquiry, Complaint, ..)','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.','Y','N','N','Y','N','Request Type',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562119,0,541013,541432,550607,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562120,0,541013,541432,550608,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Nr.',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,562121,0,540239,550607,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562122,0,541013,541432,550609,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zulieferant',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562123,0,541013,541432,550610,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','Project',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562124,0,541013,541432,550611,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','Auftrag',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562125,0,541013,541432,550612,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde','Y','N','N','Y','N','Lieferdatum',0,90,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562126,0,541013,541432,550613,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','Rechnung',0,100,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562127,0,541013,541432,550614,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,110,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562128,0,541013,541432,550615,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).','Y','N','N','Y','N','Zahlung',0,120,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562129,0,541013,541432,550616,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','N','N','Y','N','Werbemassnahme',0,130,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562130,0,541013,541432,550617,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','Y','N','Lieferung/Wareneingang',0,140,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562131,0,541013,541432,550618,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization','A Return Material Authorization may be required to accept returns and to create Credit Memos','Y','N','N','Y','N','RMA',0,150,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562132,0,541013,541432,550619,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rücklieferung',0,160,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562133,0,541013,541432,550620,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','Datensatz-ID',0,170,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562134,0,541013,541432,550621,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','Kostenstelle',0,180,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562135,0,541013,541432,550622,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on','The Date Next Action indicates the next scheduled date for an action to occur for this request.','Y','N','N','Y','N','Date next action',0,190,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562136,0,541013,541432,550623,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request','The Due Type indicates if this request is Due, Overdue or Scheduled.','Y','N','N','Y','N','Due type',0,200,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562137,0,541013,541432,550624,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Request Group','Group of requests (e.g. version numbers, responsibility, ...)','Y','N','N','Y','N','Group',0,210,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562138,0,541013,541432,550625,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution','Resolution status (e.g. Fixed, Rejected, ..)','Y','N','N','Y','N','Resolution',0,220,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562139,0,541013,541432,550626,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.','The Priority indicates the importance of this request.','Y','N','N','Y','N','Priority',0,230,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562140,0,541013,541432,550627,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','PerformanceType',0,240,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562141,0,541013,541432,550628,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User','Y','N','N','Y','N','User Importance',0,250,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562142,0,541013,541432,550629,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Qualität-Notiz',0,260,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562143,0,541013,541432,550630,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request','The Summary allows free form text entry of a recap of this request.','Y','N','N','Y','N','Summary',0,270,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:17.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562144,0,541013,541432,550631,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken','The Next Action indicates the next action to be taken on this request.','Y','N','N','Y','N','Next action',0,280,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562145,0,541013,541432,550632,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Aussendienst',0,290,0,TO_TIMESTAMP('2018-02-06 14:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562146,0,541013,541432,550633,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ','Text blocks to be copied into request response text','Y','N','N','Y','N','Standard Response',0,300,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562147,0,541013,541432,550634,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken','The Result indicates the result of any action taken on this request.','Y','N','N','Y','N','Ergebnis',0,310,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562148,0,541013,541432,550635,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Request Status','Status if the request (open, closed, investigating, ..)','Y','N','N','Y','N','Status',0,320,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562149,0,541013,541432,550636,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on','The Date Last Action indicates that last time that the request was acted on.','Y','N','N','Y','N','Date last action',0,330,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562151,0,541013,541432,550637,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact','The Last Result identifies the result of the last contact made.','Y','N','N','Y','N','Last Result',0,340,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562152,0,541013,541432,550638,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,350,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562153,0,541013,541432,550639,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,360,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562154,0,541013,541432,550640,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,370,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562155,0,541013,541432,550641,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,380,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541014,540633,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:18.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540633 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:18.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540850,540633,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540850,541433,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562201,0,541014,541433,550642,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562202,0,541014,541433,550643,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562203,0,541014,541433,550644,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562204,0,541014,541433,550645,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562205,0,541014,541433,550646,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschäftspartner ein Kunde ist','The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.','Y','N','N','Y','N','Kunde',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562206,0,541014,541433,550647,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu druckenden Kopien','"Kopien" gibt die Anzahl der Kopien an, die von jedem Dokument generiert werden sollen.','Y','N','N','Y','N','Kopien',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562207,0,541014,541433,550648,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Kundengruppe',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562208,0,541014,541433,550649,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','N','N','Y','N','Rechnungsstellung',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562209,0,541014,541433,550650,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.','Y','N','N','Y','N','Terminplan Rechnung',0,90,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562210,0,541014,541433,550651,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','N','Y','N','Lieferung',0,100,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562211,0,541014,541433,550652,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','Preissystem',0,110,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562212,0,541014,541433,550653,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','N','N','Y','N','Rabatt Schema',0,120,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562213,0,541014,541433,550654,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','N','Y','N','Zahlungsweise',0,130,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562214,0,541014,541433,550655,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','N','Y','N','Zahlungsbedingung',0,140,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:18.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562215,0,541014,541433,550656,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Rules for overdue invoices','The Dunning indicates the rules and method of dunning for past due payments.','Y','N','N','Y','N','Mahnung',0,150,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562216,0,541014,541433,550657,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','Referenz',0,160,0,TO_TIMESTAMP('2018-02-06 14:31:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562217,0,541014,541433,550658,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Rabatt auf Rechnung und Bestellung drucken','Zeigt an, ob der Rabtt auf dem Dokument gedruckt werden soll','Y','N','N','Y','N','Rabatte drucken',0,170,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562218,0,541014,541433,550659,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibung zur Verwendung auf Aufträgen','"Beschreibung Auftrag" git die Standardbeschreibung an, die auf Afträgen für diesen Kunden verwendet werden soll.','Y','N','N','Y','N','Beschreibung Auftrag',0,180,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562219,0,541014,541433,550660,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden','Y','N','N','Y','N','keine Bestellkontrolle',0,190,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562220,0,541014,541433,550661,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Höchstsumme für Aussenstände','The Credit Limit indicates the total amount allowed ''on account'' in primary accounting currency.  If the Credit Limit is 0, no ckeck is performed.  Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','N','N','Y','N','Kreditlimit',0,200,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562221,0,541014,541433,550662,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Dunning Grace Date',0,210,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562222,0,541014,541433,550663,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Sammel-Lieferscheine erlaubt',0,220,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562223,0,541014,541433,550664,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ADR Kunde',0,230,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562225,0,541014,541433,550665,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','AdRRegion',0,240,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562224,0,541014,541433,550666,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Statistik Gruppe',0,250,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562226,0,541014,541433,550667,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Ersten Verkaufs','"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.','Y','N','N','Y','N','Erster Verkauf',0,260,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562227,0,541014,541433,550668,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz steuerbefreit','If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','N','Y','N','Steuerbefreit',0,270,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562228,0,541014,541433,550669,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','Lager',0,280,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562229,0,541014,541433,550670,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Steuert ob Gebindezeilen beim Lieferscheindruck verborgen werden','Wenn gesetzt, werden Gebindezeilen im ausgedruckten Lieferschein ausgeblendet','Y','N','N','Y','N','Gebinde auf Lsch. ausblenden',0,290,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562230,0,541014,541433,550671,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Optionale Konfigurationsregel für das Kopf-Aggregationsmerkmal bei Rechnungskandidaten mit "Verkaufsrechnung = Ja". Wenn nicht gesetzt wird ein Standardwert benutzt.','Y','N','N','Y','N','Aggregationsregel für Ausgangsrechungen',0,300,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562231,0,541014,541433,550672,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Aggregationsregel für Ausgangsrechungen (Rechnungsposition)',0,310,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541015,540634,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:19.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540634 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:19.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540851,540634,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540851,541434,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562282,0,541015,541434,550673,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','N','Y','N','Lieferung',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562283,0,541015,541434,550674,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562284,0,541015,541434,550675,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562285,0,541015,541434,550676,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562286,0,541015,541434,550677,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562287,0,541015,541434,550678,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist','The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.','Y','N','N','Y','N','Lieferant',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562288,0,541015,541434,550679,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Möglichkeiten der Bezahlung einer Bestellung','"Zahlungsweise" zeigt die Arten der Zahlungen für Einkäufe an.','Y','N','N','Y','N','Zahlungsweise',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562289,0,541015,541434,550680,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Zahlungskondition für die Bestellung','Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.','Y','N','N','Y','N','Zahlungskondition',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:19.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562290,0,541015,541434,550681,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Schema to calculate the purchase trade discount percentage','Y','N','N','Y','N','Einkauf Rabatt Schema',0,90,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562291,0,541015,541434,550682,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Anbauplanung',0,100,0,TO_TIMESTAMP('2018-02-06 14:31:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562294,0,541015,541434,550683,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Einkaufspreissystem',0,110,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562295,0,541015,541434,550684,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Flag Urproduzent',0,120,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562296,0,541015,541434,550685,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Flag Produzentenabrechnung',0,130,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562297,0,541015,541434,550686,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Business partner is exempt from tax on purchases','If a business partner is exempt from tax on purchases, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','N','Y','N','PO Tax exempt',0,140,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562292,0,541015,541434,550687,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ADR Lieferant',0,150,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562299,0,541015,541434,550688,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Optionale Konfigurationsregel für das Kopf-Aggregationsmerkmal bei Rechnungskandidaten mit "Verkaufsrechnung = Nein". Wenn nicht gesetzt wird ein Standardwert benutzt.','Y','N','N','Y','N','Aggregationsregel für Eingangsrechungen',0,160,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562300,0,541015,541434,550689,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Aggregationsregel für Eingangsrechungen (Rechnungsposition)',0,170,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541016,540635,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:20.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540635 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:20.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540852,540635,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540852,541435,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562317,0,541016,541435,550690,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562318,0,541016,541435,550691,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562319,0,541016,541435,550692,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562320,0,541016,541435,550693,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','GLN',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562321,0,541016,541435,550694,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562322,0,541016,541435,550695,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','N','Y','N','Anschrift',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562323,0,541016,541435,550696,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Anschrift','Y','N','N','Y','N','Adresse',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562324,0,541016,541435,550697,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','N','Y','N','Lieferstandard',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562325,0,541016,541435,550698,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Liefer Standard Adresse',0,90,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562327,0,541016,541435,550699,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','Y','N','N','Y','N','Vorbelegung Rechnung',0,100,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562328,0,541016,541435,550700,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnung Standard Adresse',0,110,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562329,0,541016,541435,550701,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Abladeort',0,120,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562330,0,541016,541435,550702,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Erstattungs-Adresse für den Lieferanten','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.','Y','N','N','Y','N','Erstattungs-Adresse',0,130,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562331,0,541016,541435,550703,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','N','Y','N','Replikations-Standardwert',0,140,0,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541017,540636,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:20.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540636 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:20.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540853,540636,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:20.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540853,541436,TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562363,0,541017,541436,550704,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562364,0,541017,541436,550705,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562365,0,541017,541436,550706,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','Y','N','Anrede',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562366,0,541017,541436,550707,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','N','Y','N','Vorname',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562367,0,541017,541436,550708,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Nachname',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562368,0,541017,541436,550709,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562369,0,541017,541436,550710,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562370,0,541017,541436,550711,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','N','N','Y','N','Bemerkungen',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562371,0,541017,541436,550712,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,90,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562372,0,541017,541436,550713,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Standard-Ansprechpartner',0,100,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562374,0,541017,541436,550714,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Weihnachtsgeschenk',0,110,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562375,0,541017,541436,550715,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','eMail',0,120,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562376,0,541017,541436,550716,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','Y','N','Kennwort',0,130,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562377,0,541017,541436,550717,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann','Y','N','N','Y','N','Mengenmeldung App',0,140,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562378,0,541017,541436,550718,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag','"Titel" gibt die Bezeichnung für diesen Eintrag an.','Y','N','N','Y','N','Titel',0,150,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562379,0,541017,541436,550719,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','Geburtstag oder Jahrestag','Y','N','N','Y','N','Geburtstag',0,160,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562380,0,541017,541436,550720,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Beschreibt eine Telefon Nummer','Y','N','N','Y','N','Telefon',0,170,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562381,0,541017,541436,550721,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.','Y','N','N','Y','N','Mobil',0,180,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562382,0,541017,541436,550722,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','Y','N','Fax',0,190,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562383,0,541017,541436,550723,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Position in der Firma','Y','N','N','Y','N','Position',0,200,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,562385,0,540240,550705,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562386,0,541017,541436,550724,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Einkaufskontakt',0,210,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562387,0,541017,541436,550725,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsPurchaseContact_Default',0,220,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562390,0,541017,541436,550726,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnung per eMail',0,230,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562388,0,541017,541436,550727,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsSalesContact',0,240,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:21.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562389,0,541017,541436,550728,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsSalesContact_Default',0,250,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562391,0,541017,541436,550729,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.','Y','N','N','Y','N','Druck-Empfänger',0,260,0,TO_TIMESTAMP('2018-02-06 14:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562392,0,541017,541436,550730,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnungskontakt',0,270,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562393,0,541017,541436,550731,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Lieferkontakt',0,280,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541018,540637,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:22.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540637 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:22.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540854,540637,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540854,541437,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562400,0,541018,541437,550732,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562401,0,541018,541437,550733,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562402,0,541018,541437,550734,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562403,0,541018,541437,550735,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','N','Y','N','Standard',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,562404,0,540241,550732,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562405,0,541018,541437,550736,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Automated Clearing House / Clearing-Zentrale','Das Auswahlfeld "ACH" zeigt an, ob dieses Konto ACH-Transaktionen akzeptiert.','Y','N','N','Y','N','ACH',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562406,0,541018,541437,550737,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Bank Account usage','Determines how the bank account is used.','Y','N','N','Y','N','Kontonutzung',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562407,0,541018,541437,550738,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Bank','"Bank" ist die eindeutige Identifizierung einer Bank für diese Organisation oder für eien Geschäftspartner mit dem die Organisation interagiert.','Y','N','N','Y','N','Bank',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562408,0,541018,541437,550739,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','Währung',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562410,0,541018,541437,550740,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Kontonummer','The Account Number indicates the Number assigned to this bank account. ','Y','N','N','Y','N','Konto-Nr.',0,90,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562411,0,541018,541437,550741,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.','Y','N','N','Y','N','IBAN',0,100,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562414,0,541018,541437,550742,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Verifizierungs-Code der Kreditkarte','Der "Verifizierungs-Code" gibt den Verifizierungs-Code uf der Kreditkarte an (AMEX 4 Stellen auf der Vorderseite; MC / Visa 3 Stellen auf der Rückseite)','Y','N','N','Y','N','Verifizierungs-Code',0,110,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562415,0,541018,541437,550743,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis Monat','Gibt den letzten Monat der Gültigkeit dieser Kreditkarte an.','Y','N','N','Y','N','Exp. Monat',0,120,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562416,0,541018,541437,550744,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis Jahr','Gibt das letzte Jahr der Gültigkeit dieser Kreditkarte an.','Y','N','N','Y','N','Exp. Jahr',0,130,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562417,0,541018,541437,550745,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Konto',0,140,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562418,0,541018,541437,550746,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Standard ESR Konto',0,150,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562419,0,541018,541437,550747,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Teilnehmernummer',0,160,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562421,0,541018,541437,550748,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Name auf Kreditkarte oder des Kontoeigners','"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','Name',0,170,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562422,0,541018,541437,550749,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Straße des Eigentümers von Kreditkarte oder Konto','"Straße" bezeichnet die Straße des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','Straße',0,180,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562423,0,541018,541437,550750,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Ort des Eigentümers der Kreditkarte oder des Bankkontos','"Ort" bezeichnet den Ort des Eigentümers der Kreditkarte oder des Bankkontos','Y','N','N','Y','N','Ort',0,190,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:22.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562424,0,541018,541437,550751,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl des Eigentümers von Kreditkarte oder Konto','"Postleitzahl" bezeichnet die Postleitzahl des Eigentümers von Kreditkarte oder Konto','Y','N','N','Y','N','Postleitzahl',0,200,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562425,0,541018,541437,550752,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Bundesstaat / -land des Eigentümers von Kreditkarte oder Konto','Bundesstaat / -land bezeichnet den Bundesstaat oder das Bundesland des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','Bundesstaat / -land',0,210,0,TO_TIMESTAMP('2018-02-06 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562426,0,541018,541437,550753,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Land','Name des Landes','Y','N','N','Y','N','Land',0,220,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562427,0,541018,541437,550754,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Identifikation - Führerschein-Nr.','Die "Führerschein-Nr." zur Identifikation bei der Zahlung.','Y','N','N','Y','N','Führerschein-Nr.',0,230,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562428,0,541018,541437,550755,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Identifikation - Ausweis-Nr.','Ausweis-Nr. als Identifikation.','Y','N','N','Y','N','Ausweis-Nr.',0,240,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562429,0,541018,541437,550756,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Email-Adresse','"EMail" bezeichnet die EMail-Adresse des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','EMail',0,250,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562430,0,541018,541437,550757,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Diese Adresse wurde verifiziert','"Addresse verifiziert" zeigt an, dass diese Adresse durch die Kreditkartenfirma bestätigt wurde.','Y','N','N','Y','N','Addresse verifiziert',0,260,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562431,0,541018,541437,550758,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Die Postleitzahl wurde verifiziert','"Postleitzahl verifiziert" zeigt an, dass diese Postleitzahl durch die Kreditkartenfirma bestätigt wurde.','Y','N','N','Y','N','Postleitzahl verifiziert',0,270,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541019,540638,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:23.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540638 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:23.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540855,540638,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540855,541438,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562436,0,541019,541438,550759,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','Belegart',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562437,0,541019,541438,550760,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562438,0,541019,541438,550761,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Data Print Format','Das Druckformat legt fest, wie die Daten für den Druck aufbereitet werden.','Y','N','N','Y','N','Druck - Format',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562439,0,541019,541438,550762,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541020,540639,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:23.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540639 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:23.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540856,540639,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540856,541439,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562441,0,541020,541439,550763,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562442,0,541020,541439,550764,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562443,0,541020,541439,550765,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562444,0,541020,541439,550766,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562445,0,541020,541439,550767,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Gegenwärtiger Aussenstand','The Credit Used indicates the total amount of open or unpaid invoices in primary accounting currency for the Business Partner. Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','N','N','Y','N','Kredit gewährt',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562446,0,541020,541439,550768,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Kreditstatus des Geschäftspartners','Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.','Y','N','N','Y','N','Kreditstatus',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562447,0,541020,541439,550769,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Aktueller Gesamtertrag','"Aktueller Gesamtertrag" ist der verzeichnetet Ertrag, der durch diesen Geschäftspartner generiert wurde.','Y','N','N','Y','N','Aktueller Gesamtertrag',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562448,0,541020,541439,550770,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Gesamt der offenen Beträge in primärer Buchführungswährung','The Total Open Balance Amount is the calculated open item amount for Customer and Vendor activity.  If the Balance is below zero, we owe the Business Partner.  The amout is used for Credit Management.
Invoices and Payment Allocations determine the Open Balance (i.e. not Orders or Payments).','Y','N','N','Y','N','Offener Saldo',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541021,540640,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-06T14:31:23.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540640 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:31:23.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540857,540640,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:23.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540857,541440,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562454,0,541021,541440,550771,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,10,0,TO_TIMESTAMP('2018-02-06 14:31:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562455,0,541021,541440,550772,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Typ',0,20,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562456,0,541021,541440,550773,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Nr.',0,30,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562457,0,541021,541440,550774,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,40,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562458,0,541021,541440,550775,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,50,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562459,0,541021,541440,550776,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,60,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562452,0,541021,541440,550777,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','Datensatz-ID',0,70,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:31:24.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562460,0,541021,541440,550778,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,80,0,TO_TIMESTAMP('2018-02-06 14:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:33:47.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540847,541441,TO_TIMESTAMP('2018-02-06 14:33:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-06 14:33:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:34:15.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90,Updated=TO_TIMESTAMP('2018-02-06 14:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541431
;

-- 2018-02-06T14:34:50.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541441, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 14:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550571
;

-- 2018-02-06T14:35:01.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541441, SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 14:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550579
;

-- 2018-02-06T14:35:24.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541441, SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 14:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550572
;

-- 2018-02-06T14:35:50.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541441, SeqNo=40,Updated=TO_TIMESTAMP('2018-02-06 14:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550580
;

-- 2018-02-06T14:35:58.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541441, SeqNo=50,Updated=TO_TIMESTAMP('2018-02-06 14:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550573
;

-- 2018-02-06T14:36:11.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541441, SeqNo=60,Updated=TO_TIMESTAMP('2018-02-06 14:36:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550575
;

-- 2018-02-06T14:36:33.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540848,541442,TO_TIMESTAMP('2018-02-06 14:36:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-02-06 14:36:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:36:38.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540848,541443,TO_TIMESTAMP('2018-02-06 14:36:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-02-06 14:36:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:36:58.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 14:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550577
;

-- 2018-02-06T14:37:11.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541443, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 14:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550570
;

-- 2018-02-06T14:37:21.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541443, SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 14:37:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550569
;

-- 2018-02-06T14:39:44.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 14:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550601
;

-- 2018-02-06T14:39:57.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 14:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550603
;

-- 2018-02-06T14:41:03.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=40,Updated=TO_TIMESTAMP('2018-02-06 14:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550593
;

-- 2018-02-06T14:41:59.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540847,541444,TO_TIMESTAMP('2018-02-06 14:41:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','group',20,TO_TIMESTAMP('2018-02-06 14:41:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:42:26.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541444, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 14:42:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550582
;

-- 2018-02-06T14:42:38.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541444, SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 14:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550583
;

-- 2018-02-06T14:42:47.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541444, SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 14:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550581
;

-- 2018-02-06T14:45:00.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=50,Updated=TO_TIMESTAMP('2018-02-06 14:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550602
;

-- 2018-02-06T14:47:00.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-02-06 14:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550603
;

-- 2018-02-06T14:47:07.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 14:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550593
;

-- 2018-02-06T14:47:09.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-02-06 14:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550602
;

-- 2018-02-06T14:47:13.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-02-06 14:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550603
;

-- 2018-02-06T14:47:37.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541012,540641,TO_TIMESTAMP('2018-02-06 14:47:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-02-06 14:47:37','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2018-02-06T14:47:37.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540641 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-06T14:47:43.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540858,540641,TO_TIMESTAMP('2018-02-06 14:47:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-06 14:47:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T14:48:25.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540858, SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 14:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541431
;

-- 2018-02-06T14:50:45.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=60,Updated=TO_TIMESTAMP('2018-02-06 14:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550591
;

-- 2018-02-06T14:51:50.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541442, SeqNo=70,Updated=TO_TIMESTAMP('2018-02-06 14:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550587
;

-- 2018-02-06T14:53:46.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-02-06 14:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550588
;

-- 2018-02-06T14:53:52.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=95,Updated=TO_TIMESTAMP('2018-02-06 14:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550595
;

-- 2018-02-06T14:54:00.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 14:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550574
;

-- 2018-02-06T14:54:02.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 14:54:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550576
;

-- 2018-02-06T14:54:08.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 14:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550588
;

-- 2018-02-06T14:54:40.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-02-06 14:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550595
;

-- 2018-02-06T14:55:16.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-02-06 14:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550594
;

-- 2018-02-06T14:57:45.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-06 14:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550578
;

-- 2018-02-06T14:58:11.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-06 14:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550592
;

-- 2018-02-06T14:58:56.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-02-06 14:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550600
;

-- 2018-02-06T14:58:58.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-02-06 14:58:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550584
;

-- 2018-02-06T14:59:00.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-02-06 14:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550585
;

-- 2018-02-06T14:59:01.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-02-06 14:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550586
;

-- 2018-02-06T14:59:03.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2018-02-06 14:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550589
;

-- 2018-02-06T14:59:05.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2018-02-06 14:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550590
;

-- 2018-02-06T14:59:07.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2018-02-06 14:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550596
;

-- 2018-02-06T14:59:09.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2018-02-06 14:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550597
;

-- 2018-02-06T14:59:13.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2018-02-06 14:59:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550598
;

-- 2018-02-06T14:59:19.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2018-02-06 14:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550599
;

-- 2018-02-06T14:59:37.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-02-06 14:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550600
;

-- 2018-02-06T14:59:45.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-02-06 14:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550594
;

-- 2018-02-06T14:59:55.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550574
;

-- 2018-02-06T14:59:56.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550576
;

-- 2018-02-06T14:59:56.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550588
;

-- 2018-02-06T14:59:56.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550595
;

-- 2018-02-06T14:59:57.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550594
;

-- 2018-02-06T14:59:57.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550600
;

-- 2018-02-06T14:59:58.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550584
;

-- 2018-02-06T14:59:58.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550585
;

-- 2018-02-06T14:59:58.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550586
;

-- 2018-02-06T14:59:59.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 14:59:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550589
;

-- 2018-02-06T15:00:00.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 15:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550590
;

-- 2018-02-06T15:00:00.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 15:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550596
;

-- 2018-02-06T15:00:01.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 15:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550597
;

-- 2018-02-06T15:00:01.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 15:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550598
;

-- 2018-02-06T15:00:03.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-02-06 15:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550599
;

-- 2018-02-06T15:03:41.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SEPA Mandat erteilt',Updated=TO_TIMESTAMP('2018-02-06 15:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562091
;

-- 2018-02-06T15:04:14.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pharma Zulassung §52',Updated=TO_TIMESTAMP('2018-02-06 15:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562092
;

-- 2018-02-06T15:04:34.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-06 15:04:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pharma Permission §52' WHERE AD_Field_ID=562092 AND AD_Language='en_US'
;

-- 2018-02-06T15:05:06.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Apotheken Erlaubnis',Updated=TO_TIMESTAMP('2018-02-06 15:05:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562090
;

-- 2018-02-06T15:05:22.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-06 15:05:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=562090 AND AD_Language='en_US'
;

-- 2018-02-06T15:05:33.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-06 15:05:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=562091 AND AD_Language='en_US'
;

-- 2018-02-06T15:11:43.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-06 15:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550593
;

-- 2018-02-06T15:12:15.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 15:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550602
;

-- 2018-02-06T15:12:19.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 15:12:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550601
;

-- 2018-02-06T15:57:53.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550640
;

-- 2018-02-06T15:57:53.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550641
;

-- 2018-02-06T15:57:53.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550611
;

-- 2018-02-06T15:57:53.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550632
;

-- 2018-02-06T15:57:53.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550636
;

-- 2018-02-06T15:57:53.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550622
;

-- 2018-02-06T15:57:53.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550620
;

-- 2018-02-06T15:57:53.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550623
;

-- 2018-02-06T15:57:53.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550607
;

-- 2018-02-06T15:57:53.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550624
;

-- 2018-02-06T15:57:53.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550621
;

-- 2018-02-06T15:57:53.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550637
;

-- 2018-02-06T15:57:53.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550612
;

-- 2018-02-06T15:57:53.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550617
;

-- 2018-02-06T15:57:53.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550604
;

-- 2018-02-06T15:57:53.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550631
;

-- 2018-02-06T15:57:53.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550627
;

-- 2018-02-06T15:57:53.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550626
;

-- 2018-02-06T15:57:53.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550614
;

-- 2018-02-06T15:57:53.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550610
;

-- 2018-02-06T15:57:53.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550629
;

-- 2018-02-06T15:57:53.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550618
;

-- 2018-02-06T15:57:53.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550613
;

-- 2018-02-06T15:57:53.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550625
;

-- 2018-02-06T15:57:53.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550619
;

-- 2018-02-06T15:57:53.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550633
;

-- 2018-02-06T15:57:53.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550635
;

-- 2018-02-06T15:57:53.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550630
;

-- 2018-02-06T15:57:53.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550628
;

-- 2018-02-06T15:57:53.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550616
;

-- 2018-02-06T15:57:53.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550615
;

-- 2018-02-06T15:57:53.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550609
;

-- 2018-02-06T15:57:53.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550608
;

-- 2018-02-06T15:57:53.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550606
;

-- 2018-02-06T15:57:53.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550638
;

-- 2018-02-06T15:57:53.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550639
;

-- 2018-02-06T15:57:53.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550634
;

-- 2018-02-06T15:57:53.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-02-06 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550605
;

-- 2018-02-06T15:59:38.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-02-06 15:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550604
;

-- 2018-02-06T16:00:11.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550611
;

-- 2018-02-06T16:00:11.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550612
;

-- 2018-02-06T16:00:11.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550630
;

-- 2018-02-06T16:00:11.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550628
;

-- 2018-02-06T16:00:11.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550616
;

-- 2018-02-06T16:00:11.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550615
;

-- 2018-02-06T16:00:11.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550609
;

-- 2018-02-06T16:00:18.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550632
;

-- 2018-02-06T16:00:18.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550622
;

-- 2018-02-06T16:00:18.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550620
;

-- 2018-02-06T16:00:18.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550623
;

-- 2018-02-06T16:00:18.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550624
;

-- 2018-02-06T16:00:18.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550621
;

-- 2018-02-06T16:00:18.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550617
;

-- 2018-02-06T16:00:18.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550631
;

-- 2018-02-06T16:00:18.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550627
;

-- 2018-02-06T16:00:18.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550626
;

-- 2018-02-06T16:00:18.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550614
;

-- 2018-02-06T16:00:18.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550610
;

-- 2018-02-06T16:00:18.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550629
;

-- 2018-02-06T16:00:18.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550618
;

-- 2018-02-06T16:00:18.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550613
;

-- 2018-02-06T16:00:18.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550625
;

-- 2018-02-06T16:00:18.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550619
;

-- 2018-02-06T16:00:18.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550641
;

-- 2018-02-06T16:00:18.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550637
;

-- 2018-02-06T16:00:18.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550636
;

-- 2018-02-06T16:00:18.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550640
;

-- 2018-02-06T16:00:18.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550633
;

-- 2018-02-06T16:00:27.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540239
;

-- 2018-02-06T16:00:32.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550607
;

-- 2018-02-06T16:00:35.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550635
;

-- 2018-02-06T16:00:41.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550604
;

-- 2018-02-06T16:01:19.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-02-06 16:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550605
;

-- 2018-02-06T16:01:27.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-02-06 16:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550604
;

-- 2018-02-06T16:01:32.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-02-06 16:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550638
;

-- 2018-02-06T16:01:37.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2018-02-06 16:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550634
;

-- 2018-02-06T16:01:41.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2018-02-06 16:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550608
;

-- 2018-02-06T16:02:20.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550606
;

-- 2018-02-06T16:02:21.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550605
;

-- 2018-02-06T16:02:21.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550604
;

-- 2018-02-06T16:02:21.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550608
;

-- 2018-02-06T16:02:22.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550639
;

-- 2018-02-06T16:02:22.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550638
;

-- 2018-02-06T16:02:23.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550634
;

-- 2018-02-06T16:03:09.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorgang Nr.',Updated=TO_TIMESTAMP('2018-02-06 16:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562120
;

-- 2018-02-06T16:03:29.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Typ',Updated=TO_TIMESTAMP('2018-02-06 16:03:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562118
;

-- 2018-02-06T16:03:55.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Notiz',Updated=TO_TIMESTAMP('2018-02-06 16:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562143
;

-- 2018-02-06T16:06:04.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-06 16:06:04','YYYY-MM-DD HH24:MI:SS'),Name='Request' WHERE AD_Field_ID=562120 AND AD_Language='en_US'
;

-- 2018-02-06T16:18:40.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550664
;

-- 2018-02-06T16:18:40.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550665
;

-- 2018-02-06T16:18:40.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550671
;

-- 2018-02-06T16:18:40.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550672
;

-- 2018-02-06T16:18:40.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550645
;

-- 2018-02-06T16:18:40.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550659
;

-- 2018-02-06T16:18:40.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550662
;

-- 2018-02-06T16:18:40.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550670
;

-- 2018-02-06T16:18:40.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550647
;

-- 2018-02-06T16:18:40.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550648
;

-- 2018-02-06T16:18:40.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550669
;

-- 2018-02-06T16:18:40.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550644
;

-- 2018-02-06T16:18:40.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550658
;

-- 2018-02-06T16:18:40.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550649
;

-- 2018-02-06T16:18:40.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550657
;

-- 2018-02-06T16:18:40.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550663
;

-- 2018-02-06T16:18:40.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550666
;

-- 2018-02-06T16:18:40.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550668
;

-- 2018-02-06T16:18:40.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550643
;

-- 2018-02-06T16:18:40.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550660
;

-- 2018-02-06T16:18:40.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550646
;

-- 2018-02-06T16:18:40.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550654
;

-- 2018-02-06T16:18:40.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550655
;

-- 2018-02-06T16:18:40.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550661
;

-- 2018-02-06T16:18:40.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550650
;

-- 2018-02-06T16:18:40.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550651
;

-- 2018-02-06T16:18:40.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550652
;

-- 2018-02-06T16:18:40.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550653
;

-- 2018-02-06T16:18:40.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550656
;

-- 2018-02-06T16:18:40.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550667
;

-- 2018-02-06T16:18:40.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-02-06 16:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550642
;

-- 2018-02-06T16:19:02.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,562200,0,541014,541433,550779,'F',TO_TIMESTAMP('2018-02-06 16:19:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2018-02-06 16:19:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-06T16:20:29.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-06 16:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550779
;

-- 2018-02-06T16:20:31.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-02-06 16:20:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550646
;

-- 2018-02-06T16:20:33.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-02-06 16:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550654
;

-- 2018-02-06T16:20:35.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-02-06 16:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550655
;

-- 2018-02-06T16:20:36.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-02-06 16:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550661
;

-- 2018-02-06T16:20:38.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-02-06 16:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550650
;

-- 2018-02-06T16:20:39.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-02-06 16:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550651
;

-- 2018-02-06T16:20:43.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-02-06 16:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550652
;

-- 2018-02-06T16:20:45.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-02-06 16:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550653
;

-- 2018-02-06T16:20:48.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-02-06 16:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550656
;

-- 2018-02-06T16:20:52.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2018-02-06 16:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550667
;

-- 2018-02-06T16:20:55.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2018-02-06 16:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550649
;

-- 2018-02-06T16:20:57.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=1200,Updated=TO_TIMESTAMP('2018-02-06 16:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550657
;

-- 2018-02-06T16:20:59.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2018-02-06 16:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550657
;

-- 2018-02-06T16:21:01.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2018-02-06 16:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550643
;

-- 2018-02-06T16:21:03.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2018-02-06 16:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550644
;

-- 2018-02-06T16:21:05.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2018-02-06 16:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550647
;

-- 2018-02-06T16:21:06.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2018-02-06 16:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550648
;

-- 2018-02-06T16:21:08.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2018-02-06 16:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550645
;

-- 2018-02-06T16:21:10.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2018-02-06 16:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550664
;

-- 2018-02-06T16:21:15.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2018-02-06 16:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550665
;

-- 2018-02-06T16:21:17.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2018-02-06 16:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550671
;

-- 2018-02-06T16:21:20.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2018-02-06 16:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550672
;

-- 2018-02-06T16:21:22.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2018-02-06 16:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550659
;

-- 2018-02-06T16:21:24.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=230,Updated=TO_TIMESTAMP('2018-02-06 16:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550662
;

-- 2018-02-06T16:21:27.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2018-02-06 16:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550670
;

-- 2018-02-06T16:21:38.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2018-02-06 16:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550669
;

-- 2018-02-06T16:21:41.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=260,Updated=TO_TIMESTAMP('2018-02-06 16:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550658
;

-- 2018-02-06T16:21:43.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=270,Updated=TO_TIMESTAMP('2018-02-06 16:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550663
;

-- 2018-02-06T16:21:45.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=280,Updated=TO_TIMESTAMP('2018-02-06 16:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550666
;

-- 2018-02-06T16:21:47.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=290,Updated=TO_TIMESTAMP('2018-02-06 16:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550668
;

-- 2018-02-06T16:21:49.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=300,Updated=TO_TIMESTAMP('2018-02-06 16:21:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550660
;

-- 2018-02-06T16:21:54.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=310,Updated=TO_TIMESTAMP('2018-02-06 16:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550779
;

-- 2018-02-06T16:21:58.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=310,Updated=TO_TIMESTAMP('2018-02-06 16:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550642
;

-- 2018-02-06T16:22:03.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=320,Updated=TO_TIMESTAMP('2018-02-06 16:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550779
;

-- 2018-02-06T16:25:17.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550649
;

-- 2018-02-06T16:25:18.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550657
;

-- 2018-02-06T16:25:18.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550643
;

-- 2018-02-06T16:25:19.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550644
;

-- 2018-02-06T16:25:19.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550647
;

-- 2018-02-06T16:25:19.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550648
;

-- 2018-02-06T16:25:20.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550665
;

-- 2018-02-06T16:25:21.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550664
;

-- 2018-02-06T16:25:21.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550645
;

-- 2018-02-06T16:25:23.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550671
;

-- 2018-02-06T16:25:24.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550672
;

-- 2018-02-06T16:25:24.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550659
;

-- 2018-02-06T16:25:25.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550662
;

-- 2018-02-06T16:25:25.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550670
;

-- 2018-02-06T16:25:26.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550669
;

-- 2018-02-06T16:25:26.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550658
;

-- 2018-02-06T16:25:27.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550663
;

-- 2018-02-06T16:25:27.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550666
;

-- 2018-02-06T16:25:28.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550668
;

-- 2018-02-06T16:25:29.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550660
;

-- 2018-02-06T16:25:30.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550779
;

-- 2018-02-06T16:25:30.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550646
;

-- 2018-02-06T16:25:31.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550654
;

-- 2018-02-06T16:25:31.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550655
;

-- 2018-02-06T16:25:32.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550661
;

-- 2018-02-06T16:25:32.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550650
;

-- 2018-02-06T16:25:33.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550651
;

-- 2018-02-06T16:25:33.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550652
;

-- 2018-02-06T16:25:34.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550653
;

-- 2018-02-06T16:25:34.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550656
;

-- 2018-02-06T16:25:35.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550667
;

-- 2018-02-06T16:25:36.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-02-06 16:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550642
;

