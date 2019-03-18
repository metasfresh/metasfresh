-- 2019-03-05T14:27:34.696
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564327,575998,0,540879,TO_TIMESTAMP('2019-03-05 14:27:34','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Newsletter',TO_TIMESTAMP('2019-03-05 14:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:27:34.696
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 2019-03-05T14:27:34.966
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564330,576001,0,540879,TO_TIMESTAMP('2019-03-05 14:27:34','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','Handynummer',TO_TIMESTAMP('2019-03-05 14:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:27:34.966
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=576001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


-- 2019-03-05T14:27:35.210
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564333,576004,0,540879,TO_TIMESTAMP('2019-03-05 14:27:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data import',TO_TIMESTAMP('2019-03-05 14:27:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:27:35.210
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=576004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-05T14:27:35.300
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564341,576005,0,540879,TO_TIMESTAMP('2019-03-05 14:27:35','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',250,'D','Beschreibt eine Telefon Nummer','Y','N','N','N','N','N','N','N','Telefon',TO_TIMESTAMP('2019-03-05 14:27:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:27:35.300
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=576005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-05T14:27:35.380
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564342,576006,0,540879,TO_TIMESTAMP('2019-03-05 14:27:35','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer',250,'D','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','N','N','N','N','N','Fax',TO_TIMESTAMP('2019-03-05 14:27:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:27:35.380
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=576006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;







-- 2019-03-05T14:28:19.752
-- #298 changing anz. stellen
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540879,541145,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-05T14:28:19.772
-- #298 changing anz. stellen
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541145 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-05T14:28:19.872
-- #298 changing anz. stellen
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541462,541145,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:19.952
-- #298 changing anz. stellen
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541463,541145,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.042
-- #298 changing anz. stellen
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541462,542251,TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-05 14:28:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.172
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,
Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) 
VALUES (0,560330,0,540879,556918,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',
'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','Y','Aktiv',0,0,10,
TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.272
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,
IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560339,0,540879,556919,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),
100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','Y','Suchschlüssel',10,0,20,
TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.362
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,
IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560326,0,540879,556920,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),
100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','Y','Mandant',
20,0,30,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.442
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,
IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560327,0,540879,556921,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),
100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',
'Y','N','Y','N','Y','Sektion',30,0,40,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.532
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,
IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560337,0,540879,556922,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),
100,'BP Value','Suchschlüssel für den Geschäftspartner','Y','N','Y','N','Y','BPValue',40,0,50,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.602
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,
IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560338,0,540879,556923,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,
'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','Y','Geschäftspartner',50,0,
60,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.684
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,
Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560333,0,540879,556924,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','Y','N','Y','Vorname',60,0,70,
TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.764
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,
SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560334,0,540879,556925,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Nachname',70,0,80,
TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.844
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,
Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560336,0,540879,556926,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,
'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','Y','eMail',80,0,90,
TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:20.934
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,
IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560335,0,540879,556927,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,
'Used for login. See Help.','','Y','N','Y','N','Y','Login',90,0,100,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.014
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,
SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560341,0,540879,556928,542251,TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Systembenutzer',100,0,110,
TO_TIMESTAMP('2019-03-05 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.097
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,
SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560340,0,540879,556929,542251,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Role name',110,0,120,
TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.179
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,
Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560343,0,540879,556930,542251,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',
'The Role determines security and access a user who has this Role will have in the System.','Y','N','Y','N','Y','Rolle',120,0,130,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.259
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,
Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560328,0,540879,556931,542251,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100,
'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','Y','N','Y',
'Import-Fehlermeldung',130,0,140,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.349
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,
Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560329,0,540879,556932,542251,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',
'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','Y','N','Y','Importiert',140,0,150,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.429
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,
Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560331,0,540879,556933,542251,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',
'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','Y','Verarbeitet',150,0,160,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:28:21.559
-- #298 changing anz. stellen
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_Element_ID,AD_UI_ElementField_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560342,0,556923,540315,
TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-05 14:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;





-- 2019-03-05T14:44:26.464
-- #298 changing anz. stellen
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541463,542252,TO_TIMESTAMP('2019-03-05 14:44:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-03-05 14:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:44:39.379
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542252, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-05 14:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556918
;

-- 2019-03-05T14:45:02.452
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542252, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-05 14:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556928
;

-- 2019-03-05T14:45:30.610
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542252, SeqNo=30,Updated=TO_TIMESTAMP('2019-03-05 14:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556932
;

-- 2019-03-05T14:45:48.140
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542252, SeqNo=40,Updated=TO_TIMESTAMP('2019-03-05 14:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556933
;

-- 2019-03-05T14:46:19.246
-- #298 changing anz. stellen
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541463,542253,TO_TIMESTAMP('2019-03-05 14:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-03-05 14:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T14:46:50.724
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542253, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-05 14:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556921
;

-- 2019-03-05T14:46:57.860
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542253, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-05 14:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556920
;

-- 2019-03-05T15:02:38.889
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-03-05 15:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556924
;

-- 2019-03-05T15:02:42.682
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-03-05 15:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556925
;

-- 2019-03-05T15:03:17.393
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-03-05 15:03:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556930
;

-- 2019-03-05T15:03:21.981
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-03-05 15:03:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556930
;

-- 2019-03-05T15:03:27.116
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-03-05 15:03:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556929
;

-- 2019-03-05T15:03:57.146
-- #298 changing anz. stellen
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541462,542254,TO_TIMESTAMP('2019-03-05 15:03:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',20,TO_TIMESTAMP('2019-03-05 15:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:04:18.160
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-05 15:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556927
;

-- 2019-03-05T15:04:20.330
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542254, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-05 15:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556927
;

-- 2019-03-05T15:04:24.391
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-05 15:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556926
;

-- 2019-03-05T15:04:26.604
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542254, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-05 15:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556926
;

-- 2019-03-05T15:05:16.762
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542254, SeqNo=30,Updated=TO_TIMESTAMP('2019-03-05 15:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556931
;

-- 2019-03-05T15:05:38.851
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,576005,0,540879,556934,542254,'F',TO_TIMESTAMP('2019-03-05 15:05:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Telefon',40,0,0,TO_TIMESTAMP('2019-03-05 15:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:05:45.880
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,576006,0,540879,556935,542254,'F',TO_TIMESTAMP('2019-03-05 15:05:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fax',50,0,0,TO_TIMESTAMP('2019-03-05 15:05:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:06:01.015
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,576001,0,540879,556936,542254,'F',TO_TIMESTAMP('2019-03-05 15:06:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mobile Phone',60,0,0,TO_TIMESTAMP('2019-03-05 15:06:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:06:47.233
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,576004,0,540879,556937,542254,'F',TO_TIMESTAMP('2019-03-05 15:06:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data Import',70,0,0,TO_TIMESTAMP('2019-03-05 15:06:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:06:52.284
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542251, SeqNo=80,Updated=TO_TIMESTAMP('2019-03-05 15:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556937
;

-- 2019-03-05T15:07:13.548
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,575998,0,540879,556938,542252,'F',TO_TIMESTAMP('2019-03-05 15:07:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Newsletter',50,0,0,TO_TIMESTAMP('2019-03-05 15:07:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:07:22.302
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-03-05 15:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556938
;

-- 2019-03-05T15:07:24.462
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-03-05 15:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556932
;

-- 2019-03-05T15:07:28.370
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-03-05 15:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=556933
;













-- 2019-03-05T15:18:21.006
-- #298 changing anz. stellen
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,543431,541210,0,TO_TIMESTAMP('2019-03-05 15:18:20','YYYY-MM-DD HH24:MI:SS'),100,'D','I_User','Y','N','N','N','N','Import User',TO_TIMESTAMP('2019-03-05 15:18:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-05T15:18:21.016
-- #298 changing anz. stellen
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541210 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-05T15:18:21.026
-- #298 changing anz. stellen
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541210, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541210)
;

-- 2019-03-05T15:18:21.036
-- #298 changing anz. stellen
/* DDL */  select update_menu_translation_from_ad_element(543431) 
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.117
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:21.127
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:36.267
-- #298 changing anz. stellen
UPDATE AD_Menu SET Action='W', AD_Window_ID=540368, Name='Import Users',Updated=TO_TIMESTAMP('2019-03-05 15:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541210
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541187 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541045 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541141 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541157 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541186 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541193 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541197 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.001
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541198 AND AD_Tree_ID=10
;

-- 2019-03-05T15:18:43.011
-- #298 changing anz. stellen
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541199 AND AD_Tree_ID=10
;


