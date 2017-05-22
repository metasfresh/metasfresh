-- 2017-05-22T17:43:02.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,143,540200,TO_TIMESTAMP('2017-05-22 17:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 17:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540281,540200,TO_TIMESTAMP('2017-05-22 17:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 17:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540282,540200,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540281,540462,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,439,0,143,540462,544499,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,939,0,143,540462,544500,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',20,20,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,441,0,143,540462,544501,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,442,0,143,540462,544502,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,40,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,443,0,143,540462,544503,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1553,0,143,540462,544504,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','N','Y','Y','N','Zusammenfassungseintrag',60,60,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56981,0,143,540462,544505,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Data Replication Strategy','The Data Replication Strategy determines what and how tables are replicated ','Y','N','Y','Y','N','Replizierung - Strategie',70,70,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,170,540201,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540283,540201,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540283,540463,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,913,0,170,540463,544506,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,912,0,170,540463,544507,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,914,0,170,540463,544508,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9246,0,170,540463,544509,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Organization Type','Organization Type allows you to categorize your organizations for reporting purposes','Y','N','N','Y','N','Organization Type',0,40,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9245,0,170,540463,544510,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','Lager',0,50,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,55416,0,170,540463,544511,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'The (logical) warehouse to use for recording drop ship receipts and shipments.','The drop ship warehouse will be used for recording material transactions relating to drop shipments to and from this organization.','Y','N','N','Y','N','Lager für Streckengeschäft',0,60,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1314,0,170,540463,544512,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Dun & Bradstreet - Nummer','Für EDI verwendet - Details unter www.dnb.com/dunsno/list.htm','Y','N','N','Y','N','D-U-N-S',0,70,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1315,0,170,540463,544513,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Steuernummer','"Steuer-ID" gibt die offizielle Steuernummer für diese Einheit an.','Y','N','N','Y','N','Steuer-ID',0,80,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8729,0,170,540463,544514,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Parent (superior) Organization ','Übergeordnete Organisation - die nächst höhere Ebene in der organisatorischen Hierarchie.','Y','N','N','Y','N','Übergeordnete Organisation',0,90,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8728,0,170,540463,544515,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Vorgesetzter oder Übergeordneter für diesen Nutzer oder diese Organisation - verwendet für Eskalation und Freigabe','"Vorgesetzter" zeigt an, wer für Weiterleitung und Eskalation von Vorfällen dieses Nutzers verwendet wird - oder für Freigaben.','Y','N','N','Y','N','Vorgesetzter',0,100,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551613,0,170,540463,544516,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Verantwortlicher für die Ausführung des Workflow','Die Verantwortung für die Ausführung eines Workflow liegt bei einem Nutzer. "Workflow - Verantwortlicher" ermöglicht anzugeben, auf welche Weise dieser Nutzer gefunden wird.','Y','N','N','Y','N','Workflow - Verantwortlicher',0,110,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,502220,0,170,540463,544517,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden','Beispiel: http://host:8080/reports','Y','N','N','Y','N','Berichts-Präfix',0,120,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56622,0,170,540463,544518,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Buchführungs-Kalenders','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','Y','N','Kalender',0,130,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550282,0,170,540463,544519,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','Preissystem',0,140,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547035,0,170,540463,544520,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Speichung Kreditkartendaten',0,150,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551040,0,170,540463,544521,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Default BP Location linked to the org.','Y','N','N','Y','N','OrgBP_Location_ID',0,160,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:03.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57532,0,170,540463,544522,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Logo',0,170,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545335,0,170,540463,544523,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Portal domain name (DNS)',0,180,0,TO_TIMESTAMP('2017-05-22 17:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545333,0,170,540463,544524,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Portal Logo (large)',0,190,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545334,0,170,540463,544525,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Portal Logo (small)',0,200,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,637,540202,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540284,540202,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540284,540464,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9988,0,637,540464,544526,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9993,0,637,540464,544527,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9990,0,637,540464,544528,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9989,0,637,540464,544529,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9991,0,637,540464,544530,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig ab',0,50,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T17:43:04.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9992,0,637,540464,544531,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig bis',0,60,0,TO_TIMESTAMP('2017-05-22 17:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;