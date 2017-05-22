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

-- 2017-05-22T19:38:03.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540286,540472,TO_TIMESTAMP('2017-05-22 19:38:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 19:38:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:38:13.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=99, UIStyle='',Updated=TO_TIMESTAMP('2017-05-22 19:38:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540470
;

-- 2017-05-22T19:41:23.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,317,0,145,540472,544591,TO_TIMESTAMP('2017-05-22 19:41:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-05-22 19:41:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:42:35.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3817,0,145,540472,544592,TO_TIMESTAMP('2017-05-22 19:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',20,0,0,TO_TIMESTAMP('2017-05-22 19:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:42:50.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5160,0,145,540472,544593,TO_TIMESTAMP('2017-05-22 19:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sprache',30,0,0,TO_TIMESTAMP('2017-05-22 19:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:44:33.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540286,540473,TO_TIMESTAMP('2017-05-22 19:44:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-05-22 19:44:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:44:44.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,318,0,145,540473,544594,TO_TIMESTAMP('2017-05-22 19:44:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-05-22 19:44:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:45:08.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11025,0,145,540473,544595,TO_TIMESTAMP('2017-05-22 19:45:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Automatische Archivierung',20,0,0,TO_TIMESTAMP('2017-05-22 19:45:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:45:23.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11205,0,145,540473,544596,TO_TIMESTAMP('2017-05-22 19:45:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Materialfluß',30,0,0,TO_TIMESTAMP('2017-05-22 19:45:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:45:39.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540287,540474,TO_TIMESTAMP('2017-05-22 19:45:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-22 19:45:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:45:52.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,319,0,145,540474,544597,TO_TIMESTAMP('2017-05-22 19:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-05-22 19:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:46:18.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5759,0,145,540474,544598,TO_TIMESTAMP('2017-05-22 19:46:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Multisprachliche Dokumente',20,0,0,TO_TIMESTAMP('2017-05-22 19:46:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:46:42.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10318,0,145,540474,544599,TO_TIMESTAMP('2017-05-22 19:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beta Funktionalität',30,0,0,TO_TIMESTAMP('2017-05-22 19:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:46:50.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Beta Funktionalität verwenden',Updated=TO_TIMESTAMP('2017-05-22 19:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544599
;

-- 2017-05-22T19:47:04.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12327,0,145,540474,544600,TO_TIMESTAMP('2017-05-22 19:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verbuchung sofort',40,0,0,TO_TIMESTAMP('2017-05-22 19:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:47:19.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12326,0,145,540474,544601,TO_TIMESTAMP('2017-05-22 19:47:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kostenrechnung sofort',50,0,0,TO_TIMESTAMP('2017-05-22 19:47:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:47:48.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540287,540475,TO_TIMESTAMP('2017-05-22 19:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','email',20,TO_TIMESTAMP('2017-05-22 19:47:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:48:13.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5887,0,145,540475,544602,TO_TIMESTAMP('2017-05-22 19:48:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','SMTP Anmeldung',10,0,0,TO_TIMESTAMP('2017-05-22 19:48:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:48:27.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3813,0,145,540475,544603,TO_TIMESTAMP('2017-05-22 19:48:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail Server',20,0,0,TO_TIMESTAMP('2017-05-22 19:48:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:49:45.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544544
;

-- 2017-05-22T19:49:45.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544542
;

-- 2017-05-22T19:49:45.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544543
;

-- 2017-05-22T19:49:45.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544545
;

-- 2017-05-22T19:49:45.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544546
;

-- 2017-05-22T19:49:45.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544547
;

-- 2017-05-22T19:49:57.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544548
;

-- 2017-05-22T19:49:57.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544549
;

-- 2017-05-22T19:49:57.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544550
;

-- 2017-05-22T19:49:57.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544551
;

-- 2017-05-22T19:49:57.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544552
;

-- 2017-05-22T19:50:09.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544560
;

-- 2017-05-22T19:50:09.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544561
;

-- 2017-05-22T19:50:25.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544540
;

-- 2017-05-22T19:50:25.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544541
;

-- 2017-05-22T19:50:25.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544553
;

-- 2017-05-22T19:50:25.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544554
;

-- 2017-05-22T19:50:25.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544555
;

-- 2017-05-22T19:50:25.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544556
;

-- 2017-05-22T19:50:25.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544557
;

-- 2017-05-22T19:50:25.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544558
;

-- 2017-05-22T19:50:25.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544559
;

-- 2017-05-22T19:50:25.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544562
;

-- 2017-05-22T19:50:25.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544563
;

-- 2017-05-22T19:50:25.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544564
;

-- 2017-05-22T19:50:25.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544565
;

-- 2017-05-22T19:50:25.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544566
;

-- 2017-05-22T19:50:25.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544567
;

-- 2017-05-22T19:50:25.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544568
;

-- 2017-05-22T19:50:30.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540470
;

-- 2017-05-22T19:50:43.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,145,540206,TO_TIMESTAMP('2017-05-22 19:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',20,TO_TIMESTAMP('2017-05-22 19:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:50:47.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540289,540206,TO_TIMESTAMP('2017-05-22 19:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 19:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:50:54.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540289,540476,TO_TIMESTAMP('2017-05-22 19:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-05-22 19:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:51:12.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,54680,0,145,540476,544604,TO_TIMESTAMP('2017-05-22 19:51:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Replizierung Strategie',10,0,0,TO_TIMESTAMP('2017-05-22 19:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:51:31.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,59691,0,145,540476,544605,TO_TIMESTAMP('2017-05-22 19:51:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Passwort Reset eMail',20,0,0,TO_TIMESTAMP('2017-05-22 19:51:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:51:44.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5161,0,145,540476,544606,TO_TIMESTAMP('2017-05-22 19:51:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anfrage eMail',30,0,0,TO_TIMESTAMP('2017-05-22 19:51:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:51:57.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5162,0,145,540476,544607,TO_TIMESTAMP('2017-05-22 19:51:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anfrage Verzeichnis',40,0,0,TO_TIMESTAMP('2017-05-22 19:51:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:52:08.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5163,0,145,540476,544608,TO_TIMESTAMP('2017-05-22 19:52:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anfrage Nutzer',50,0,0,TO_TIMESTAMP('2017-05-22 19:52:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:52:23.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12099,0,145,540476,544609,TO_TIMESTAMP('2017-05-22 19:52:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Server eMail',60,0,0,TO_TIMESTAMP('2017-05-22 19:52:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:52:34.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12098,0,145,540476,544610,TO_TIMESTAMP('2017-05-22 19:52:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Teste eMail',70,0,0,TO_TIMESTAMP('2017-05-22 19:52:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:52:51.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11024,0,145,540476,544611,TO_TIMESTAMP('2017-05-22 19:52:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Modell Validierungs Klassen',80,0,0,TO_TIMESTAMP('2017-05-22 19:52:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:53:23.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50158,0,145,540476,544612,TO_TIMESTAMP('2017-05-22 19:53:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anhang speichern Dateisystem',90,0,0,TO_TIMESTAMP('2017-05-22 19:53:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:53:52.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50159,0,145,540476,544613,TO_TIMESTAMP('2017-05-22 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Windows Anhang Verzeichnis',100,0,0,TO_TIMESTAMP('2017-05-22 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:54:16.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50160,0,145,540476,544614,TO_TIMESTAMP('2017-05-22 19:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Unix Anhang Verzeichnis',110,0,0,TO_TIMESTAMP('2017-05-22 19:54:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:54:39.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50184,0,145,540476,544615,TO_TIMESTAMP('2017-05-22 19:54:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Archiv speichern Dateisystem',120,0,0,TO_TIMESTAMP('2017-05-22 19:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:54:58.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50185,0,145,540476,544616,TO_TIMESTAMP('2017-05-22 19:54:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Windows Archiv Verzeichnis',130,0,0,TO_TIMESTAMP('2017-05-22 19:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:55:19.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50186,0,145,540476,544617,TO_TIMESTAMP('2017-05-22 19:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Unix Archiv Verzeichnis',140,0,0,TO_TIMESTAMP('2017-05-22 19:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T19:55:31.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544604
;

-- 2017-05-22T19:55:32.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544605
;

-- 2017-05-22T19:55:32.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544606
;

-- 2017-05-22T19:55:33.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544607
;

-- 2017-05-22T19:55:33.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544608
;

-- 2017-05-22T19:55:33.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544609
;

-- 2017-05-22T19:55:34.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544610
;

-- 2017-05-22T19:55:34.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544611
;

-- 2017-05-22T19:55:35.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544612
;

-- 2017-05-22T19:55:35.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544613
;

-- 2017-05-22T19:55:36.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544614
;

-- 2017-05-22T19:55:36.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544615
;

-- 2017-05-22T19:55:37.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544616
;

-- 2017-05-22T19:55:38.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 19:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544617
;

-- 2017-05-22T19:59:11.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544591
;

-- 2017-05-22T19:59:11.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544592
;

-- 2017-05-22T19:59:11.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544594
;

-- 2017-05-22T19:59:11.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544593
;

-- 2017-05-22T19:59:11.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544597
;

-- 2017-05-22T19:59:11.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544595
;

-- 2017-05-22T19:59:11.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544596
;

-- 2017-05-22T19:59:11.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544600
;

-- 2017-05-22T19:59:11.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544601
;

-- 2017-05-22T19:59:11.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544612
;

-- 2017-05-22T19:59:11.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-22 19:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544615
;

-- 2017-05-22T19:59:35.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-22 19:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544595
;

-- 2017-05-22T19:59:35.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-22 19:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544596
;

-- 2017-05-22T19:59:35.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-22 19:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544597
;

-- 2017-05-22T20:00:02.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-22 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544598
;

-- 2017-05-22T20:00:02.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-22 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544599
;

-- 2017-05-22T20:00:02.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-22 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544600
;

-- 2017-05-22T20:00:02.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-22 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544601
;

-- 2017-05-22T20:00:02.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-05-22 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544612
;

-- 2017-05-22T20:00:02.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-05-22 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544615
;

-- 2017-05-22T20:00:40.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-22 20:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544591
;

-- 2017-05-22T20:00:40.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-22 20:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544594
;

-- 2017-05-22T20:00:40.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-22 20:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544593
;

-- 2017-05-22T20:00:40.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-22 20:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544597
;

