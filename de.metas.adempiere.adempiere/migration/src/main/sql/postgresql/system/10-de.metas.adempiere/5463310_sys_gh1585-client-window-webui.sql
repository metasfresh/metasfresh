-- 2017-05-22T18:46:30.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,145,540204,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540286,540204,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540287,540204,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540286,540470,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,54680,0,145,540470,544540,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Data Replication Strategy','The Data Replication Strategy determines what and how tables are replicated ','Y','N','Y','Y','N','Replizierung - Strategie',10,10,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,59691,0,145,540470,544541,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Password Reset Mail',20,20,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3817,0,145,540470,544542,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,317,0,145,540470,544543,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,318,0,145,540470,544544,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,319,0,145,540470,544545,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10318,0,145,540470,544546,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Verwendung von Beta-Funktionalität freigeben','The exact scope of Beta Functionality is listed in the release note.  It is usually not recommended to enable Beta functionality in production environments.','Y','N','Y','Y','N','Beta-Funktionalität verwenden',70,70,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5160,0,145,540470,544547,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','Y','N','Sprache',80,80,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5759,0,145,540470,544548,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Dokumente sind mehrsprachig verwendet','If selected, you enable multi lingual documents and need to maintain translations for entities used in documents (examples: Products, Payment Terms, ...).<br>
Please note, that the base language is always English.','Y','N','Y','Y','N','Multisprachliche Dokumente',90,90,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11025,0,145,540470,544549,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Ermöglichen und einstellen des Grades der automatischen Archivierung','Adempiere allows to automatically create archives of Documents (e.g. Invoices) or Reports. You view the archived material with the Archive Viewer','Y','N','Y','Y','N','Automatische Archivierung',100,100,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11205,0,145,540470,544550,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Methode für den Materialfluß','The Material Movement Policy determines how the stock is flowing (FiFo or LiFo) if a specific Product Instance was not selected.  The policy can not contradict the costing method (e.g. FiFo movement policy and LiFo costing method).','Y','N','Y','Y','N','Materialfluß',110,110,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3813,0,145,540470,544551,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Hostname oder IP-Adresse des Servers für SMTP und IMAP','Der Name oder die IP-Adresse des Servers für diesen Mandanten mit SMTP-Service, um EMail zu versenden und  IMAP, um einkommende EMail zu verarbeiten.','Y','N','Y','Y','N','EMail-Server',120,120,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5887,0,145,540470,544552,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Ihr EMail-Server verlangt eine Anmeldung','Some email servers require authentication before sending emails.  If yes, users are required to define their email user name and password.  If authentication is required and no user name and password is required, delivery will fail.','Y','N','Y','Y','N','SMTP-Anmeldung',130,130,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5161,0,145,540470,544553,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse für den Versand oder Empfang bei automatischer Verarbeitung (voll qualifiziert)','EMails for requests, alerts and escalation are sent from this email address as well as delivery information if the sales rep does not have an email account. The address must be filly qualified (e.g. joe.smith@company.com) and should be a valid address.','Y','N','Y','Y','N','Anfrage-EMail',140,140,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:30.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5162,0,145,540470,544554,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Verzeichnis für die Verarbeitung eigehender EMails; wenn leer wird INBOX verwendet','Email folder used to read emails to process as requests, If left empty the default mailbox (INBOX) will be used. Requires IMAP services.','Y','N','Y','Y','N','Anfrage-Verzeichnis',150,150,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5163,0,145,540470,544555,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name (ID) des EMail-Kontos','EMail user name for requests, alerts and escalation are sent from this email address as well as delivery information if the sales rep does not have an email account. Required, if your mail server requires authentification as well as for processing incoming mails.','Y','N','Y','Y','N','Anfrage-Nutzer',160,160,0,TO_TIMESTAMP('2017-05-22 18:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5164,0,145,540470,544556,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Passwort des Nutzer-Namens (ID) für den EMail-Zugriff','Y','N','Y','Y','N','Passwort Anfrage-Nutzer',170,170,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12099,0,145,540470,544557,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Sende EMail vom Server','When selected, mail is sent from the server rather then the client.  This decreases availability.  You would select this when you do not want to enable email relay for the client addresses in your mail server.','Y','N','Y','Y','N','Server EMail',180,180,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12098,0,145,540470,544558,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Teste EMail-Verbindung','Test EMail Connection based on info defined. An EMail is sent from the request user to the request user.  Also, the web store mail settings are tested.','Y','N','Y','Y','N','Teste EMail',190,190,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11024,0,145,540470,544559,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Liste von Modell-Validierungs-Klassen, separiert durch  ;','List of classes implementing the interface org.compiere.model.ModelValidator, separaed by semicolon.
The class is called for the client and alows to validate documents in the prepare stage and monitor model changes.','Y','N','Y','Y','N','Modell-Validierungs-Klassen',200,200,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12327,0,145,540470,544560,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Verbuchung zu Testzwecken sofort durchführen','If selected, the accouning consequences are immediately generated when completing a doecument.  Otherwise the document is posted by a batch process.  You should select this only if you are testing,','Y','N','Y','Y','N','Verbuchung sofort',210,210,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12326,0,145,540470,544561,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Kostenrechnung zu Testzwecken sofort aktualisieren','If selected, costs are updated immediately when a Cost Detail record is created (by matching or shipping).  Otherwise the costs are updated by batch or when the costs are needed for posting. You should select this only if you are testing,','Y','N','Y','Y','N','Kostenrechnung sofort',220,220,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50158,0,145,540470,544562,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Store Attachments On File System',230,230,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50159,0,145,540470,544563,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Windows Attachment Path',240,240,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50160,0,145,540470,544564,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Unix Attachment Path',250,250,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50184,0,145,540470,544565,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Store Archive On File System',260,260,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50185,0,145,540470,544566,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Windows Archive Path',270,270,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50186,0,145,540470,544567,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Unix Archive Path',280,280,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,54238,0,145,540470,544568,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsUseASP',290,290,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,169,540205,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540288,540205,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540288,540471,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,904,0,169,540471,544569,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5346,0,169,540471,544570,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Berechnung von Zahlungsrabatten beinhaltet nicht Steuern und Gebühren','If the payment discount is calculated from line amounts only, the tax and charge amounts are not included. This is e.g. business practice in the US.  If not selected the total invoice amount is used to calculate the payment discount.','Y','N','N','Y','N','Rabatt aus Zeilenbeträgen berechnet',0,20,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4727,0,169,540471,544571,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Buchführungs-Kalenders','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','Y','N','Kalender',0,30,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,905,0,169,540471,544572,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Primäre Vorgaben für die Buchhaltung','Ein Buchführungsschema definiert die Vorgaben für die Buchführung, wie Kostenrechnungsmethode, Währung und Kalender.','Y','N','N','Y','N','Primäres Buchführungsschema',0,40,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1309,0,169,540471,544573,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßneinheit für Volumen','"Maßeinheit für Volumen" bezeichnet die Standardmaßeinheit, die bei Produkten mit Volumenangabe auf Belegen verwendet wird.','Y','N','N','Y','N','Maßeinheit für Volumen',0,50,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1310,0,169,540471,544574,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Gewicht','"Maßeinheit für Gewicht" bezeichnet die Standardmaßeinheit, die bei Produkten mit Gewichtsangabe auf Belegen verwendet wird.','Y','N','N','Y','N','Maßeinheit für Gewicht',0,60,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1311,0,169,540471,544575,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Länge','"Maßeinheit für Länge" bezeichnet die Standardmaßeinheit, die bei Produkten mit Längenangabe auf Belegen verwendet wird.','Y','N','N','Y','N','Maßeinheit für Länge',0,70,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1312,0,169,540471,544576,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Standardmaßeinheit für Zeit','"Maßeinheit für Zeit" bezeichnet die Standardmaßeinheit, die bei Produkten mit Zeitangabe auf Belegen verwendet wird.','Y','N','N','Y','N','Maßeinheit für Zeit',0,80,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3052,0,169,540471,544577,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnervorlage, die zum schnellen Anlegen neuer Geschäftspartner verwendet wird','Wenn Sie einen weiteren Geschäftspartner aus dem Geschäftspartner-Auswahlfeld anlegen (Rechts-Klick: Neuer Datensatz), wird dieser als Vorlage verwendet für z.B. Preisliste, Zahlungsbedingungen usw.','Y','N','N','Y','N','Vorlage Geschäftspartner',0,90,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3054,0,169,540471,544578,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produkt für Fracht',0,100,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9201,0,169,540471,544579,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl von Tagen, für die das Protokoll aufbewahrt wird','Ältere Protokolleinträge können gelöscht werden.','Y','N','N','Y','N','Tage Protokoll aufheben',0,110,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1564,0,169,540471,544580,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Baum zur Festlegung der Organisations-Hierarchie','Bäume werden für das Erstellen von (Finanz-) Berichten und die Zugriffssteuerung (über die Rolle) verwendet.','Y','N','N','Y','N','Primärbaum Organisation',0,120,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1563,0,169,540471,544581,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Menübaum','Baum für den Menüzugriff','Y','N','N','Y','N','Primärbaum Menü',0,130,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1562,0,169,540471,544582,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Baum zur Festlegung der Geschäftspartner-Hierarchie','Bäume werden für das Erstellen von (Finanz-) Berichten verwendet','Y','N','N','Y','N','Primärbaum Geschäftspartner',0,140,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1565,0,169,540471,544583,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Baum zur Festlegung der Produkt-Hierarchie','Bäume werden für das Erstellen von (Finanz-) Berichten verwendet','Y','N','N','Y','N','Primärbaum Produkt',0,150,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1566,0,169,540471,544584,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Baum zur Festlegung der Projekt-Hierarchie','Bäume werden für das Erstellen von (Finanz-) Berichten verwendet','Y','N','N','Y','N','Primärbaum Projekt',0,160,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1567,0,169,540471,544585,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Baum zur Festlegung der Hierarchie regionaler Vertriebsgebiete','Bäume werden für das Erstellen von (Finanz-) Berichten verwendet','Y','N','N','Y','N','Primärbaum Vertriebsgebiet',0,170,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10319,0,169,540471,544586,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Tree to determine marketing campaign hierarchy','Bäume werden für das Erstellen von (Finanz-) Berichten verwendet','Y','N','N','Y','N','Baum Kampagne',0,180,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10320,0,169,540471,544587,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Tree to determine activity hierarchy','Bäume werden für das Erstellen von (Finanz-) Berichten verwendet','Y','N','N','Y','N','Baum Aktivität',0,190,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:31.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57531,0,169,540471,544588,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Logo',0,200,0,TO_TIMESTAMP('2017-05-22 18:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:32.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57534,0,169,540471,544589,TO_TIMESTAMP('2017-05-22 18:46:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Logo Report',0,210,0,TO_TIMESTAMP('2017-05-22 18:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T18:46:32.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57535,0,169,540471,544590,TO_TIMESTAMP('2017-05-22 18:46:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Logo Web',0,220,0,TO_TIMESTAMP('2017-05-22 18:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

