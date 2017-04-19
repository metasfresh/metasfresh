-- 2017-03-29T08:49:47.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,118,540093,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540131,540093,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540132,540093,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540131,540190,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12640,0,118,540190,542379,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Auf welche Weise der Java-Client mit dem Server verbunden ist','Depending on the connection profile, different protocols are used and tasks are performed on the server rather then the client. Usually the user can select different profiles, unless it is enforced by the User or Role definition. The User level profile overwrites the Role based profile.','Y','N','Y','N','N','Verbindungsart',10,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,309,0,118,540190,542380,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',20,10,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2001,0,118,540190,542381,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',30,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542723,0,118,540190,542382,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','Y','Y','N','Vorname',40,20,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553164,0,118,540190,542383,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Nachname',50,30,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,300,0,118,540190,542384,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','N','N','Name',60,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13755,0,118,540190,542385,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required','7 bit lower case alpha numeric - max length 8 - can be used for operating system names.','Y','N','Y','N','N','Search Key',70,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,301,0,118,540190,542386,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',80,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6522,0,118,540190,542387,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','N','Y','N','N','Bemerkungen',90,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,303,0,118,540190,542388,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',100,40,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4623,0,118,540190,542389,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',110,50,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,6516,0,540098,542389,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551889,0,118,540190,542390,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Systembenutzer',120,60,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4260,0,118,540190,542391,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','Y','N','EMail',130,70,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551886,0,118,540190,542392,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Used for login. See Help.','task 04259','Y','N','Y','Y','N','Login',140,80,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,302,0,118,540190,542393,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Passwort beliebiger Länge (unterscheided Groß- und Kleinschreibung)','The Password for this User.  Passwords are required to identify authorized users.  For Adempiere Users, you can change the password via the Process "Reset Password".','Y','N','Y','N','N','Kennwort',150,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6521,0,118,540190,542394,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag','"Titel" gibt die Bezeichnung für diesen Eintrag an.','Y','N','Y','N','N','Titel',160,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,52010,0,118,540190,542395,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserPIN',170,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6515,0,118,540190,542396,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','Geburtstag oder Jahrestag','Y','N','Y','N','N','Geburtstag',180,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6517,0,118,540190,542397,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a telephone number','The Phone field identifies a telephone number','Y','N','Y','N','N','Phone',190,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6514,0,118,540190,542398,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Telefonnummer','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.','Y','N','Y','N','N','Telefon (alternativ)',200,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6518,0,118,540190,542399,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','Y','N','N','Fax',210,0,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:47.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12324,0,118,540190,542400,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'Position in der Firma','Y','N','Y','Y','N','Position',220,90,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12323,0,118,540190,542401,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100,'The user/contact has full access to Business Partner information and resources','If selected, the user has full access to the Business Partner (BP) information (Business Documents like Orders, Invoices - Requests) or resources (Assets, Downloads). If you deselet it, the user has no access rights unless, you explicitly grant it in tab "BP Access"','Y','N','Y','Y','N','Full BP Access',230,100,0,TO_TIMESTAMP('2017-03-29 08:49:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5883,0,118,540190,542402,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name/Konto (ID) im EMail-System','The user name in the mail system is usually the string before the @ of your email address. Notwendig, wenn der EMail-SErver eine Anmeldung vor dem Versenden von EMails verlangt.','Y','N','Y','N','N','EMail Nutzer-ID',240,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5884,0,118,540190,542403,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Passwort Ihrer EMail Nutzer-ID','Notwendig, wenn der EMail-Server eine Anmeldung vor dem Versenden von EMails verlangt.','Y','N','Y','N','N','Passwort EMail-Nutzer',250,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4261,0,118,540190,542404,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Vorgesetzter oder Übergeordneter für diesen Nutzer oder diese Organisation - verwendet für Eskalation und Freigabe','"Vorgesetzter" zeigt an, wer für Weiterleitung und Eskalation von Vorfällen dieses Nutzers verwendet wird - oder für Freigaben.','Y','N','Y','N','N','Vorgesetzter',260,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10491,0,118,540190,542405,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Berechtigung über LDAP-Service','Optional LDAP system user name for the user. If not defined, the normal Name of the user is used. This allows to use the internal (LDAP) user id (e.g. jjanke) and the normal display name (e.g. Jorg Janke).  The LDAP User Name can also be used without LDAP enables (see system window).  This would allow to sign in as jjanke and use the display name of Jorg Janke.','Y','N','Y','Y','N','Berechtigen über LDAP',270,110,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6511,0,118,540190,542406,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Durchführende oder auslösende Organisation','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.','Y','N','Y','N','N','Buchende Organisation',280,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11679,0,118,540190,542407,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Art der Benachrichtigung','EMail oder Nachricht bei aktualisierter Anfrage usw.','Y','N','Y','Y','N','Benachrichtigungs-Art',290,120,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556434,0,118,540190,542408,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.','Y','N','Y','Y','N','Betreuer',300,130,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557931,0,118,540190,542409,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.','Y','N','Y','Y','N','Druck-Empfänger',310,140,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6513,0,118,540190,542410,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','Y','N','N','Anrede',320,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11525,0,118,540190,542411,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum der EMail-Überprüfung','Y','N','Y','N','N','EMail überprüft',330,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8342,0,118,540190,542412,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Überprüfung der EMail-Adresse','Dieses Feld enthält das Datum, an dem die EMail-Adresse verifiziert wurde.','Y','N','Y','N','N','Überprüfung EMail',340,0,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551895,0,118,540190,542413,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Failed login count ','Y','N','Y','Y','N','LoginFailureCount',350,150,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551896,0,118,540190,542414,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Flag is yes if account is locked','Y','N','Y','Y','N','IsAccountLocked',360,160,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551897,0,118,540190,542415,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Client IP address that was used when this account was locked ','Y','N','Y','Y','N','LockedFromIP',370,170,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551898,0,118,540190,542416,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Date when was last faild','Y','N','Y','Y','N','LoginFailureDate',380,180,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551899,0,118,540190,542417,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Button that will call a process to unlock current selected account','Y','N','Y','Y','N','UnlockAccount',390,190,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553935,0,118,540190,542418,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Einkaufskontakt',400,200,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,121,540094,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540133,540094,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540133,540191,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,355,0,121,540191,542419,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2393,0,121,540191,542420,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,356,0,121,540191,542421,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,357,0,121,540191,542422,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role','The Role determines security and access a user who has this Role will have in the System.','Y','N','N','Y','N','Rolle',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,358,0,121,540191,542423,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,573,540095,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540134,540095,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540134,540192,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8733,0,573,540192,542424,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8739,0,573,540192,542425,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8730,0,573,540192,542426,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8735,0,573,540192,542427,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:48.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8737,0,573,540192,542428,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8736,0,573,540192,542429,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2017-03-29 08:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8732,0,573,540192,542430,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Ersatz oder Vertretung für das Original','"Ersatz" gibt an, wer oder was anstelle des Originales eingesetzt werden kann.','Y','N','N','Y','N','Ersatz',0,70,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8738,0,573,540192,542431,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig ab',0,80,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8731,0,573,540192,542432,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig bis',0,90,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,636,540096,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540135,540096,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540135,540193,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10006,0,636,540193,542433,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10011,0,636,540193,542434,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10008,0,636,540193,542435,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10007,0,636,540193,542436,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10009,0,636,540193,542437,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig ab',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10010,0,636,540193,542438,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig bis',0,60,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11682,0,636,540193,542439,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,70,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,696,540097,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540136,540097,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540136,540194,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11260,0,696,540194,542440,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11261,0,696,540194,542441,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11263,0,696,540194,542442,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11259,0,696,540194,542443,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11262,0,696,540194,542444,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Feld / Eintrag / Berecih ist schreibgeschützt','The Read Only indicates that this field may only be Read.  It may not be updated.','Y','N','N','Y','N','Schreibgeschützt',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,709,540098,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540137,540098,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540137,540195,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11540,0,709,540195,542445,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11546,0,709,540195,542446,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11548,0,709,540195,542447,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11544,0,709,540195,542448,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Textvorlage für EMail','The Mail Template indicates the mail template for return messages. Mail text can include variables. Priorität bei der Analyse haben Nutzer/Kontakt, Geschäftspartner und dann die weiteren Objekte (wie Anfrage, mahnung, Workflow, usw.).<br> Also wird @Name@ primär durch den Namen des Nutzer/Kontakt ersetzt wenn einer definiert ist, dann durch den Namen des Geschäftspartners wenn der definiert ist und dann durch den Namen eines der oben angegebenen Objekte.<br>In mehrsprachigen Systemen wird die Übersetzung der Vorlage basierend auf der Auswahl der Sprache für den Geschäftspartner verwendet.','Y','N','N','Y','N','EMail-Vorlage',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11543,0,709,540195,542449,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Vorlage für EMail-Mitteilung im Webshop','Y','N','N','Y','N','EMail-Mitteilung',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11970,0,709,540195,542450,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld "Erstellt" zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,60,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:49.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11545,0,709,540195,542451,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'EMail Message-ID','SMTP Message-ID für Nachverfolgungszwecke','Y','N','N','Y','N','Message-ID',0,70,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12359,0,709,540195,542452,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Mail Betreff','Subject of the Mail ','Y','N','N','Y','N','Betreff',0,80,0,TO_TIMESTAMP('2017-03-29 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12358,0,709,540195,542453,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'In der EMail-Mitteilung verwendeter Text ','"EMailtext" zeigt den in der EMail-Mitteilung verwendeten Text an.','Y','N','N','Y','N','EMailtext',0,90,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11542,0,709,540195,542454,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'EMail Zustellquittierung','Y','N','N','Y','N','Zustellquittierung',0,100,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11541,0,709,540195,542455,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zugestellt',0,110,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,757,540099,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540138,540099,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540138,540196,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12242,0,757,540196,542456,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12245,0,757,540196,542457,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12248,0,757,540196,542458,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12244,0,757,540196,542459,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12243,0,757,540196,542460,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12241,0,757,540196,542461,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12246,0,757,540196,542462,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,70,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12249,0,757,540196,542463,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Validierungscode','Der "Validierungscode" zeigt Datum, Zeit und Fehlerinformation.','Y','N','N','Y','N','Validierungscode',0,80,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,851,540100,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540139,540100,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540139,540197,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13733,0,851,540197,542464,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13739,0,851,540197,542465,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13741,0,851,540197,542466,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13738,0,851,540197,542467,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'LDAP-Server um externe Systeme basierend auf ADempiere-Daten zu authentifizieren','The LDAP Server allows third party software (e.g. Apache) to use the users defined in the system to authentificate and authorize them.  There is only one server per Adempiere system.  The "o" is the Client key and the optional "ou" is the Interest Area key.','Y','N','N','Y','N','LDAP-Server',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13736,0,851,540197,542468,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Interessengebiete oder Themen','Ein "Interessengebiet" zeigt das Interesse eines Kontaktes an einem bestimmten Thema. Interessengebiete können für Marketingkampagnen verwendet werden.','Y','N','N','Y','N','Interessengebiet',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13742,0,851,540197,542469,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld "Erstellt" zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,60,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13735,0,851,540197,542470,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','N','Y','N','Fehler',0,70,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13740,0,851,540197,542471,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Textuelle Zusammenfassung der Anfrage','"Zusammenfassung" erlaubt die freie Texteingabe einer kurzen Wiederholung dieser Anfrage.','Y','N','N','Y','N','Zusammenfassung',0,80,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13734,0,851,540197,542472,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,90,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540651,540101,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540140,540101,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540140,540198,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:50.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555228,0,540651,540198,542473,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Email of the responsible for the System','Email of the responsible person for the system (registered in WebStore)','Y','N','N','Y','N','Registered EMail',0,10,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:51.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555254,0,540651,540198,542474,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Unique identifier of a host','Y','N','N','Y','N','Host key',0,20,0,TO_TIMESTAMP('2017-03-29 08:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:51.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555224,0,540651,540198,542475,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,30,0,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:51.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555225,0,540651,540198,542476,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,40,0,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:51.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555231,0,540651,540198,542477,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role','The Role determines security and access a user who has this Role will have in the System.','Y','N','N','Y','N','Rolle',0,50,0,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:49:51.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555232,0,540651,540198,542478,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100,'User Session Online or Web','Online or Web Session Information','Y','N','N','Y','N','Nutzersitzung',0,60,0,TO_TIMESTAMP('2017-03-29 08:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:51:55.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540131,540199,TO_TIMESTAMP('2017-03-29 08:51:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-29 08:51:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:52:07.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=999, UIStyle='',Updated=TO_TIMESTAMP('2017-03-29 08:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540190
;

-- 2017-03-29T08:55:11.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,300,0,118,540199,542479,TO_TIMESTAMP('2017-03-29 08:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-03-29 08:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:55:23.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542723,0,118,540199,542480,TO_TIMESTAMP('2017-03-29 08:55:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorname',20,0,0,TO_TIMESTAMP('2017-03-29 08:55:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:55:33.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553164,0,118,540199,542481,TO_TIMESTAMP('2017-03-29 08:55:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Nachname',30,0,0,TO_TIMESTAMP('2017-03-29 08:55:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:56:30.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4260,0,118,540199,542482,TO_TIMESTAMP('2017-03-29 08:56:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail',40,0,0,TO_TIMESTAMP('2017-03-29 08:56:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:56:53.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,52010,0,118,540199,542483,TO_TIMESTAMP('2017-03-29 08:56:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','PIN',50,0,0,TO_TIMESTAMP('2017-03-29 08:56:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:58:26.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6515,0,118,540199,542484,TO_TIMESTAMP('2017-03-29 08:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geburtstag',35,0,0,TO_TIMESTAMP('2017-03-29 08:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T08:58:53.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542382
;

-- 2017-03-29T08:58:57.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542383
;

-- 2017-03-29T08:59:01.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542384
;

-- 2017-03-29T08:59:11.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542391
;

-- 2017-03-29T08:59:26.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542393
;

-- 2017-03-29T08:59:52.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=302, Name='Kennwort',Updated=TO_TIMESTAMP('2017-03-29 08:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542483
;

-- 2017-03-29T09:05:09.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540132,540200,TO_TIMESTAMP('2017-03-29 09:05:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','diverse',10,TO_TIMESTAMP('2017-03-29 09:05:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:05:24.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,303,0,118,540200,542485,TO_TIMESTAMP('2017-03-29 09:05:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-03-29 09:05:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:06:01.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6521,0,118,540200,542486,TO_TIMESTAMP('2017-03-29 09:06:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Titel',20,0,0,TO_TIMESTAMP('2017-03-29 09:06:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:06:13.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4623,0,118,540200,542487,TO_TIMESTAMP('2017-03-29 09:06:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner',30,0,0,TO_TIMESTAMP('2017-03-29 09:06:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:06:28.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6517,0,118,540200,542488,TO_TIMESTAMP('2017-03-29 09:06:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Telefon',40,0,0,TO_TIMESTAMP('2017-03-29 09:06:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:06:45.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12324,0,118,540200,542489,TO_TIMESTAMP('2017-03-29 09:06:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Position',50,0,0,TO_TIMESTAMP('2017-03-29 09:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:06:50.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2017-03-29 09:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542489
;

-- 2017-03-29T09:07:18.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4261,0,118,540200,542490,TO_TIMESTAMP('2017-03-29 09:07:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorgesetzter',35,0,0,TO_TIMESTAMP('2017-03-29 09:07:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:07:39.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6513,0,118,540200,542491,TO_TIMESTAMP('2017-03-29 09:07:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anrede',15,0,0,TO_TIMESTAMP('2017-03-29 09:07:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:07:56.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540132,540201,TO_TIMESTAMP('2017-03-29 09:07:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2017-03-29 09:07:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:08:21.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551889,0,118,540201,542492,TO_TIMESTAMP('2017-03-29 09:08:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Systembenutzer',10,0,0,TO_TIMESTAMP('2017-03-29 09:08:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:08:38.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12323,0,118,540201,542493,TO_TIMESTAMP('2017-03-29 09:08:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner Zugang',20,0,0,TO_TIMESTAMP('2017-03-29 09:08:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:09:48.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553935,0,118,540201,542494,TO_TIMESTAMP('2017-03-29 09:09:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Einkaufskontakt',30,0,0,TO_TIMESTAMP('2017-03-29 09:09:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:10:46.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540132,540202,TO_TIMESTAMP('2017-03-29 09:10:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','login',30,TO_TIMESTAMP('2017-03-29 09:10:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:11:50.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551898,0,118,540202,542495,TO_TIMESTAMP('2017-03-29 09:11:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Login Fehler Datum',10,0,0,TO_TIMESTAMP('2017-03-29 09:11:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:12:34.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551897,0,118,540202,542496,TO_TIMESTAMP('2017-03-29 09:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Letzter Zugriff IP',20,0,0,TO_TIMESTAMP('2017-03-29 09:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:13:24.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551896,0,118,540202,542497,TO_TIMESTAMP('2017-03-29 09:13:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Konto gesperrt',30,0,0,TO_TIMESTAMP('2017-03-29 09:13:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:13:46.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551899,0,118,540202,542498,TO_TIMESTAMP('2017-03-29 09:13:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Konto entsperren',40,0,0,TO_TIMESTAMP('2017-03-29 09:13:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:14:49.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540131,540203,TO_TIMESTAMP('2017-03-29 09:14:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-03-29 09:14:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:20:20.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,301,0,118,540203,542499,TO_TIMESTAMP('2017-03-29 09:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-03-29 09:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:20:38.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6522,0,118,540203,542500,TO_TIMESTAMP('2017-03-29 09:20:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bemerkungen',20,0,0,TO_TIMESTAMP('2017-03-29 09:20:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:21:53.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4623,0,118,540203,542501,TO_TIMESTAMP('2017-03-29 09:21:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner',0,0,0,TO_TIMESTAMP('2017-03-29 09:21:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:22:07.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4261,0,118,540203,542502,TO_TIMESTAMP('2017-03-29 09:22:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorgesetzter',5,0,0,TO_TIMESTAMP('2017-03-29 09:22:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:22:20.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542487
;

-- 2017-03-29T09:22:25.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542490
;

-- 2017-03-29T09:24:03.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542400
;

-- 2017-03-29T09:24:08.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542401
;

-- 2017-03-29T09:24:28.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542381
;

-- 2017-03-29T09:25:29.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13755,0,118,540199,542503,TO_TIMESTAMP('2017-03-29 09:25:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',60,0,0,TO_TIMESTAMP('2017-03-29 09:25:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:26:14.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2001,0,118,540203,542504,TO_TIMESTAMP('2017-03-29 09:26:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',30,0,0,TO_TIMESTAMP('2017-03-29 09:26:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:26:24.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542385
;

-- 2017-03-29T09:26:26.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542386
;

-- 2017-03-29T09:26:30.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542387
;

-- 2017-03-29T09:26:32.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542388
;

-- 2017-03-29T09:26:48.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540098
;

-- 2017-03-29T09:26:52.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542389
;

-- 2017-03-29T09:27:16.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,6516,0,540099,542501,TO_TIMESTAMP('2017-03-29 09:27:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-29 09:27:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T09:28:17.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542390
;

-- 2017-03-29T09:28:17.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542394
;

-- 2017-03-29T09:28:17.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542396
;

-- 2017-03-29T09:28:26.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542397
;

-- 2017-03-29T09:28:32.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542404
;

-- 2017-03-29T09:28:37.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542410
;

-- 2017-03-29T09:29:05.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542413
;

-- 2017-03-29T09:29:05.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542414
;

-- 2017-03-29T09:29:05.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542415
;

-- 2017-03-29T09:29:05.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542416
;

-- 2017-03-29T09:29:05.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542417
;

-- 2017-03-29T09:29:10.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542399
;

-- 2017-03-29T09:29:11.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542379
;

-- 2017-03-29T09:29:11.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542380
;

-- 2017-03-29T09:29:12.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542392
;

-- 2017-03-29T09:29:12.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542395
;

-- 2017-03-29T09:29:13.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542398
;

-- 2017-03-29T09:29:14.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542402
;

-- 2017-03-29T09:29:14.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542403
;

-- 2017-03-29T09:29:15.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542405
;

-- 2017-03-29T09:29:15.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542406
;

-- 2017-03-29T09:29:16.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542407
;

-- 2017-03-29T09:29:16.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542408
;

-- 2017-03-29T09:29:17.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542409
;

-- 2017-03-29T09:29:17.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542411
;

-- 2017-03-29T09:29:18.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542412
;

-- 2017-03-29T09:29:19.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-03-29 09:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542418
;

-- 2017-03-29T09:30:07.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551895,0,118,540202,542505,TO_TIMESTAMP('2017-03-29 09:30:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl Fehlversuche',35,0,0,TO_TIMESTAMP('2017-03-29 09:30:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-03-29T13:52:56.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542407
;

-- 2017-03-29T13:52:56.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542405
;

-- 2017-03-29T13:52:56.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542408
;

-- 2017-03-29T13:52:56.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542409
;

-- 2017-03-29T13:52:56.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542418
;

-- 2017-03-29T13:52:56.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542380
;

-- 2017-03-29T13:52:56.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542479
;

-- 2017-03-29T13:52:56.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542486
;

-- 2017-03-29T13:52:56.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542501
;

-- 2017-03-29T13:52:56.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542392
;

-- 2017-03-29T13:52:56.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542484
;

-- 2017-03-29T13:52:56.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542482
;

-- 2017-03-29T13:52:56.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542488
;

-- 2017-03-29T13:52:56.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542505
;

-- 2017-03-29T13:52:56.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542495
;

-- 2017-03-29T13:52:56.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542496
;

-- 2017-03-29T13:52:56.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-03-29 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542497
;

-- 2017-03-29T13:53:42.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-03-29 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542479
;

-- 2017-03-29T13:53:42.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-03-29 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542501
;

-- 2017-03-29T13:53:42.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-03-29 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542488
;

-- 2017-03-29T13:53:42.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-03-29 13:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542482
;

