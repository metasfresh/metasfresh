-- 2017-05-08T13:22:39.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53027,540168,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540235,540168,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540236,540168,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540235,540368,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53425,0,53027,540368,543797,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53426,0,53027,540368,543798,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53427,0,53027,540368,543799,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53428,0,53027,540368,543800,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53429,0,53027,540368,543801,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53430,0,53027,540368,543802,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','Y','N','Kommentar/Hilfe',60,60,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53431,0,53027,540368,543803,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',70,70,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53432,0,53027,540368,543804,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','Y','N','Standard',80,80,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53433,0,53027,540368,543805,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Type of Worflow','The type of workflow determines how the workflow is started.','Y','N','Y','Y','N','Arbeitsablauf Typ',90,90,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53434,0,53027,540368,543806,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Prozess Typ',100,100,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53442,0,53027,540368,543807,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob diese Anfrage von hoher, mittlerer oder niedriger Priorität ist.','Die Priorität gibt die Wichtigkeit dieser Anfrage wieder.','Y','N','Y','Y','N','Priorität',110,110,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53443,0,53027,540368,543808,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','Y','N','Gültig ab',120,120,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53444,0,53027,540368,543809,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','Y','Y','N','Gültig bis',130,130,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:39.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53445,0,53027,540368,543810,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Status of Publication','Used for internal documentation','Y','N','Y','Y','N','Status Arbeitsablauf',140,140,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53446,0,53027,540368,543811,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Beleg Nr.',150,150,0,TO_TIMESTAMP('2017-05-08 13:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53447,0,53027,540368,543812,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Version of the table definition','The Version indicates the version of this table definition.','Y','N','Y','Y','N','Version',160,160,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53448,0,53027,540368,543813,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Author/Creator of the Entity','Y','N','Y','Y','N','Bearbeiter',170,170,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53449,0,53027,540368,543814,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Workflow Node step or process','The Workflow Node indicates a unique step or process in a Workflow.','Y','N','Y','Y','N','Anfangsknoten',180,180,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53456,0,53027,540368,543815,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration','Unit to define the length of time for the execution','Y','N','Y','Y','N','Dauer Einheit',190,190,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56685,0,53027,540368,543816,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent','ADempiere Calculate the total yield for a product from the yield for each activity when the process Workflow Cost Roll-Up is executed.

The expected yield for an Activity can be expressed as:

Yield = Acceptable Units at Activity End x 100

The Total manufacturing yield for a product is determined by multiplying the yied percentage for each activity.

Manufacturing Yield = Yield % for Activity 10 x Yied % for Activity 20 , etc 

Take care when setting yield to anything but 100% particularly when yied is used for multiples activities

','Y','N','Y','Y','N','Ausbringung %',200,200,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53460,0,53027,540368,543817,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Validieren, dass der Workflow korrekt ist','(eingeschränkte Prüfung)','Y','N','Y','Y','N','Arbeitsablauf verifizieren',210,210,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53019,540169,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540237,540169,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540237,540369,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56256,0,53019,540369,543818,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56261,0,53019,540369,543819,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56263,0,53019,540369,543820,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Workflow oder Kombination von Aufgaben','"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','N','Y','N','Workflow',0,30,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56259,0,53019,540369,543821,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','Sprache',0,40,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56255,0,53019,540369,543822,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56262,0,53019,540369,543823,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','Y','N','Übersetzt',0,60,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56260,0,53019,540369,543824,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,70,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56258,0,53019,540369,543825,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,80,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56257,0,53019,540369,543826,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','Kommentar/Hilfe',0,90,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53025,540170,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540238,540170,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540238,540370,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53367,0,53025,540370,543827,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53368,0,53025,540370,543828,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53369,0,53025,540370,543829,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Workflow oder Kombination von Aufgaben','"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','N','Y','N','Arbeitsablauf',0,30,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53370,0,53025,540370,543830,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,40,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53371,0,53025,540370,543831,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,50,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53372,0,53025,540370,543832,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53373,0,53025,540370,543833,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','Kommentar/Hilfe',0,70,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53374,0,53025,540370,543834,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,80,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53375,0,53025,540370,543835,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Resource','Select the manufacturing resource (previously defined) where you want to execute the operation. For the product costing, the Resource rate is taken from the cost element introduced in the window Product Costing.','Y','N','N','Y','N','Produktions Ressource',0,90,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53383,0,53025,540370,543836,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob diese Anfrage von hoher, mittlerer oder niedriger Priorität ist.','Die Priorität gibt die Wichtigkeit dieser Anfrage wieder.','Y','N','N','Y','N','Priorität',0,100,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:40.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53386,0,53025,540370,543837,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Semantics for multiple incoming Transitions','Semantics for multiple incoming Transitions for a Node/Activity. AND joins all concurrent threads - XOR requires one thread (no synchronization).','Y','N','N','Y','N','Join Element',0,110,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:41.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53387,0,53025,540370,543838,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100,'Semantics for multiple outgoing Transitions','Semantics for multiple outgoing Transitions for a Node/Activity.  AND represents multiple concurrent threads - XOR represents the first transition with a true Transaition condition.','Y','N','N','Y','N','Split Element',0,120,0,TO_TIMESTAMP('2017-05-08 13:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:22:41.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56690,0,53025,540370,543839,TO_TIMESTAMP('2017-05-08 13:22:41','YYYY-MM-DD HH24:MI:SS'),100,'The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent','ADempiere Calculate the total yield for a product from the yield for each activity when the process Workflow Cost Roll-Up is executed.

The expected yield for an Activity can be expressed as:

Yield = Acceptable Units at Activity End x 100

The Total manufacturing yield for a product is determined by multiplying the yied percentage for each activity.

Manufacturing Yield = Yield % for Activity 10 x Yied % for Activity 20 , etc 

Take care when setting yield to anything but 100% particularly when yied is used for multiples activities

','Y','N','N','Y','N','Ausbringung %',0,130,0,TO_TIMESTAMP('2017-05-08 13:22:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:41:46.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540235,540371,TO_TIMESTAMP('2017-05-08 13:41:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 13:41:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:42:06.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=99, UIStyle='',Updated=TO_TIMESTAMP('2017-05-08 13:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540368
;

-- 2017-05-08T13:45:26.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53427,0,53027,540371,543840,TO_TIMESTAMP('2017-05-08 13:45:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',10,0,0,TO_TIMESTAMP('2017-05-08 13:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:45:34.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53428,0,53027,540371,543841,TO_TIMESTAMP('2017-05-08 13:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',20,0,0,TO_TIMESTAMP('2017-05-08 13:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:45:47.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53446,0,53027,540371,543842,TO_TIMESTAMP('2017-05-08 13:45:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beleg Nr.',30,0,0,TO_TIMESTAMP('2017-05-08 13:45:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:45:59.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53448,0,53027,540371,543843,TO_TIMESTAMP('2017-05-08 13:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bearbeiter',40,0,0,TO_TIMESTAMP('2017-05-08 13:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:46:26.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540235,540372,TO_TIMESTAMP('2017-05-08 13:46:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','secondary',20,TO_TIMESTAMP('2017-05-08 13:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:46:34.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='durations',Updated=TO_TIMESTAMP('2017-05-08 13:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540372
;

-- 2017-05-08T13:46:51.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53433,0,53027,540372,543844,TO_TIMESTAMP('2017-05-08 13:46:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsablauf Typ',10,0,0,TO_TIMESTAMP('2017-05-08 13:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:47:04.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53434,0,53027,540372,543845,TO_TIMESTAMP('2017-05-08 13:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Prozess Typ',20,0,0,TO_TIMESTAMP('2017-05-08 13:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:47:19.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53445,0,53027,540372,543846,TO_TIMESTAMP('2017-05-08 13:47:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Status Arbeitsablauf',30,0,0,TO_TIMESTAMP('2017-05-08 13:47:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:48:12.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53456,0,53027,540372,543847,TO_TIMESTAMP('2017-05-08 13:48:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Dauer Einheit',40,0,0,TO_TIMESTAMP('2017-05-08 13:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:48:56.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56685,0,53027,540372,543848,TO_TIMESTAMP('2017-05-08 13:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ausbringung %',50,0,0,TO_TIMESTAMP('2017-05-08 13:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:51:34.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540236,540373,TO_TIMESTAMP('2017-05-08 13:51:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-08 13:51:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:51:44.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53431,0,53027,540373,543849,TO_TIMESTAMP('2017-05-08 13:51:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-05-08 13:51:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:51:55.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53432,0,53027,540373,543850,TO_TIMESTAMP('2017-05-08 13:51:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Standard',20,0,0,TO_TIMESTAMP('2017-05-08 13:51:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:52:09.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540236,540374,TO_TIMESTAMP('2017-05-08 13:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','priority',20,TO_TIMESTAMP('2017-05-08 13:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:52:23.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53442,0,53027,540374,543851,TO_TIMESTAMP('2017-05-08 13:52:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Priorität',10,0,0,TO_TIMESTAMP('2017-05-08 13:52:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:52:48.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53443,0,53027,540374,543852,TO_TIMESTAMP('2017-05-08 13:52:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gültig ab',20,0,0,TO_TIMESTAMP('2017-05-08 13:52:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:53:02.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53444,0,53027,540374,543853,TO_TIMESTAMP('2017-05-08 13:53:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gültig bis',30,0,0,TO_TIMESTAMP('2017-05-08 13:53:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:53:24.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53460,0,53027,540374,543854,TO_TIMESTAMP('2017-05-08 13:53:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsablauf verifizieren',40,0,0,TO_TIMESTAMP('2017-05-08 13:53:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:53:34.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540236,540375,TO_TIMESTAMP('2017-05-08 13:53:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-05-08 13:53:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:53:43.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53426,0,53027,540375,543855,TO_TIMESTAMP('2017-05-08 13:53:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-05-08 13:53:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:53:50.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53425,0,53027,540375,543856,TO_TIMESTAMP('2017-05-08 13:53:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-08 13:53:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:54:22.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56684,0,53027,540373,543857,TO_TIMESTAMP('2017-05-08 13:54:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gültig',30,0,0,TO_TIMESTAMP('2017-05-08 13:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:54:53.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543797
;

-- 2017-05-08T13:54:53.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543798
;

-- 2017-05-08T13:54:53.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543799
;

-- 2017-05-08T13:54:53.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543800
;

-- 2017-05-08T13:54:53.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543803
;

-- 2017-05-08T13:54:53.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543804
;

-- 2017-05-08T13:55:08.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543805
;

-- 2017-05-08T13:55:08.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543806
;

-- 2017-05-08T13:55:08.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543807
;

-- 2017-05-08T13:55:08.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543808
;

-- 2017-05-08T13:55:08.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543809
;

-- 2017-05-08T13:55:08.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543810
;

-- 2017-05-08T13:56:29.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543811
;

-- 2017-05-08T13:56:29.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543812
;

-- 2017-05-08T13:56:29.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543813
;

-- 2017-05-08T13:56:29.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543814
;

-- 2017-05-08T13:56:29.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543815
;

-- 2017-05-08T13:56:29.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543816
;

-- 2017-05-08T13:56:34.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543817
;

-- 2017-05-08T13:56:35.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-08 13:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543801
;

-- 2017-05-08T13:56:36.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-08 13:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543802
;

-- 2017-05-08T13:56:57.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53447,0,53027,540372,543858,TO_TIMESTAMP('2017-05-08 13:56:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Version',15,0,0,TO_TIMESTAMP('2017-05-08 13:56:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:57:21.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53449,0,53027,540374,543859,TO_TIMESTAMP('2017-05-08 13:57:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anfangsknoten',50,0,0,TO_TIMESTAMP('2017-05-08 13:57:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:57:58.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53447,0,53027,540371,543860,TO_TIMESTAMP('2017-05-08 13:57:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Version',50,0,0,TO_TIMESTAMP('2017-05-08 13:57:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T13:58:05.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543858
;

-- 2017-05-08T14:05:40.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543802
;

-- 2017-05-08T14:05:40.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543840
;

-- 2017-05-08T14:05:40.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543841
;

-- 2017-05-08T14:05:40.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543842
;

-- 2017-05-08T14:05:40.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543801
;

-- 2017-05-08T14:05:40.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543843
;

-- 2017-05-08T14:05:40.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543860
;

-- 2017-05-08T14:05:40.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543849
;

-- 2017-05-08T14:05:40.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543850
;

-- 2017-05-08T14:05:40.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543857
;

-- 2017-05-08T14:05:40.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543844
;

-- 2017-05-08T14:05:40.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543845
;

-- 2017-05-08T14:05:40.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543846
;

-- 2017-05-08T14:05:40.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543847
;

-- 2017-05-08T14:05:40.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543848
;

-- 2017-05-08T14:05:40.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543851
;

-- 2017-05-08T14:05:40.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543852
;

-- 2017-05-08T14:05:40.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543853
;

-- 2017-05-08T14:05:40.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543854
;

-- 2017-05-08T14:05:40.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543855
;

-- 2017-05-08T14:05:40.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2017-05-08 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543856
;

-- 2017-05-08T14:08:50.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-08 14:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543840
;

-- 2017-05-08T14:08:50.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-08 14:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543841
;

-- 2017-05-08T14:08:50.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-08 14:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543842
;

-- 2017-05-08T14:08:50.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-08 14:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543843
;

-- 2017-05-08T14:08:50.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-05-08 14:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543849
;

-- 2017-05-08T14:09:08.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-08 14:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543844
;

-- 2017-05-08T14:09:08.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-08 14:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543842
;

-- 2017-05-08T14:09:08.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-05-08 14:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543843
;

-- 2017-05-08T14:09:08.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=60,Updated=TO_TIMESTAMP('2017-05-08 14:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543849
;

-- 2017-05-08T14:09:49.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='N', SeqNo_SideList=0,Updated=TO_TIMESTAMP('2017-05-08 14:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543840
;

-- 2017-05-08T14:09:49.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-08 14:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543841
;

-- 2017-05-08T14:09:49.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-08 14:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543844
;

-- 2017-05-08T14:09:49.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-08 14:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543842
;

-- 2017-05-08T14:09:49.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-08 14:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543843
;

-- 2017-05-08T14:09:49.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-05-08 14:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543849
;

-- 2017-05-08T14:09:52.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543840
;

-- 2017-05-08T14:09:52.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543841
;

-- 2017-05-08T14:09:52.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543842
;

-- 2017-05-08T14:09:52.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543801
;

-- 2017-05-08T14:09:52.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543843
;

-- 2017-05-08T14:09:52.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543860
;

-- 2017-05-08T14:09:52.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543849
;

-- 2017-05-08T14:09:52.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543850
;

-- 2017-05-08T14:09:52.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543857
;

-- 2017-05-08T14:09:52.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543844
;

-- 2017-05-08T14:09:52.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543845
;

-- 2017-05-08T14:09:52.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543846
;

-- 2017-05-08T14:09:52.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543847
;

-- 2017-05-08T14:09:52.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543848
;

-- 2017-05-08T14:09:52.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543851
;

-- 2017-05-08T14:09:52.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543852
;

-- 2017-05-08T14:09:52.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543853
;

-- 2017-05-08T14:09:52.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543854
;

-- 2017-05-08T14:09:52.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543855
;

-- 2017-05-08T14:09:52.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2017-05-08 14:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543856
;

