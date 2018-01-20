-- 2018-01-20T11:10:14.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,245,540608,TO_TIMESTAMP('2018-01-20 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:13','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:14.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540608 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:14.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540814,540608,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540815,540608,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540814,541388,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2527,0,245,541388,550290,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2528,0,245,541388,550291,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3116,0,245,541388,550292,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2529,0,245,541388,550293,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','Y','N','Prozess',40,40,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2533,0,245,541388,550294,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',50,50,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2530,0,245,541388,550295,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',60,60,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2531,0,245,541388,550296,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','Y','N','Kommentar/Hilfe',70,70,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2532,0,245,541388,550297,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',80,80,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10562,0,245,541388,550298,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Diese Funktionalität wird als Beta eingestuft','Beta-Funktionalität ist nicht komplett getestet oder fertiggestellt','Y','N','Y','Y','N','Beta-Funktionalität',90,90,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5128,0,245,541388,550299,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','Y','Y','N','Entitäts-Art',100,100,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4540,0,245,541388,550300,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Notwendige Berechtigungsstufe','Bezeichnet die notwendige Berechtigung für den Zugriff auf diesen Eintrag oder Prozess.','Y','N','Y','Y','N','Berechtigungsstufe',110,110,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2571,0,245,541388,550301,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Indicates a Report record','The Report checkbox indicates that this record is a report as opposed to a process','Y','N','Y','Y','N','Bericht',120,120,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12100,0,245,541388,550302,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Run this Process on Server only','Enabling this flag disables to run the process on the client.  This potentially decreases the availability.','Y','N','Y','Y','N','Server Process',130,130,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,61140,0,245,541388,550303,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'At the same time allow to run only one instance of this process','Y','N','Y','Y','N','Allow one instance only',140,140,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,61141,0,245,541388,550304,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'If only one instance is allowed to run at a time, how many seconds to wait for it. Zero or negative number means forever.','Y','N','Y','Y','N','Wait Timeout',150,150,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3703,0,245,541388,550305,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Java-Klasse',160,160,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2534,0,245,541388,550306,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Name of the Database Procedure','The Procedure indicates the name of the database procedure called by this report or process.','Y','N','Y','Y','N','Procedure',170,170,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10235,0,245,541388,550307,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Workflow oder Kombination von Aufgaben','"Workflow" bezeichnet einen eindeutigen Workflow im System.','Y','N','Y','Y','N','Workflow',180,180,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56497,0,245,541388,550308,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Special Form','The Special Form field identifies a unique Special Form in the system.','Y','N','Y','Y','N','Special Form',190,190,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3278,0,245,541388,550309,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'View used to generate this report','The Report View indicates the view used to generate this report.','Y','N','Y','Y','N','Berichts-View',200,200,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3219,0,245,541388,550310,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Print without dialog','The Direct Print checkbox indicates that this report will print without a print dialog box being displayed.','Y','N','Y','Y','N','Direct print',210,210,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5849,0,245,541388,550311,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Data Print Format','Das Druckformat legt fest, wie die Daten für den Druck aufbereitet werden.','Y','N','Y','Y','N','Druck - Format',220,220,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:14.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50155,0,245,541388,550312,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Show Help',230,230,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556932,0,245,541388,550313,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsApplySecuritySettings',240,240,0,TO_TIMESTAMP('2018-01-20 11:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50156,0,245,541388,550314,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Jasper Report',250,250,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556165,0,245,541388,550315,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.','Y','N','Y','Y','N','Jasper Report (tabular)',260,260,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57342,0,245,541388,550316,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Copy settings from one report and process to another.','Copy the settings from the selected report and process to the current one.  This overwrites existing settings and translations.','Y','N','Y','Y','N','Copy From Report and Process',270,270,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543153,0,245,541388,550317,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Type of Validation (SQL, Java Script, Java Language)','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.','Y','N','Y','Y','N','Art',280,280,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543154,0,245,541388,550318,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','SQLStatement',290,290,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554874,0,245,541388,550319,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'If true, then refresh all entries after a process''s execution; otherwise, refresh only current selection','Y','N','Y','Y','N','Refresh All After Execution',300,300,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556273,0,245,541388,550320,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.','Y','N','Y','Y','N','Mehrfachausführung erlaubt',310,310,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556918,0,245,541388,550321,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsUseBPartnerLanguage',320,320,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,248,540609,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:15.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540609 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:15.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540816,540609,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540816,541389,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2556,0,248,541389,550322,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2558,0,248,541389,550323,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2559,0,248,541389,550324,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','Y','N','Prozess',0,30,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2557,0,248,541389,550325,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','Sprache',0,40,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2562,0,248,541389,550326,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2563,0,248,541389,550327,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','Y','N','Übersetzt',0,60,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2564,0,248,541389,550328,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,70,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2560,0,248,541389,550329,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,80,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2561,0,248,541389,550330,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','Kommentar/Hilfe',0,90,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,308,540610,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:15.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540610 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:15.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540817,540610,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540817,541390,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3654,0,308,541390,550331,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:15.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3655,0,308,541390,550332,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3658,0,308,541390,550333,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','Y','N','Prozess',0,30,0,TO_TIMESTAMP('2018-01-20 11:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3659,0,308,541390,550334,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role','The Role determines security and access a user who has this Role will have in the System.','Y','N','N','Y','N','Rolle',0,40,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3656,0,308,541390,550335,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3657,0,308,541390,550336,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Feld / Eintrag / Bereich kann gelesen und verändert werden','"Lesen und Schreiben" zeigt an, dass hier gelesen und aktualisiert werden kann.','Y','N','N','Y','N','Lesen und Schreiben',0,60,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,246,540611,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:16.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540611 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:16.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540818,540611,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540818,541391,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2535,0,246,541391,550337,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2536,0,246,541391,550338,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2537,0,246,541391,550339,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','Y','N','Prozess',0,30,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2546,0,246,541391,550340,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2541,0,246,541391,550341,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2542,0,246,541391,550342,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','Kommentar/Hilfe',0,60,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2543,0,246,541391,550343,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,70,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5817,0,246,541391,550344,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','Y','N','Entitäts-Art',0,80,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4576,0,246,541391,550345,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Information wird in der System-Element-Tabelle verwaltet','The Centrally Maintained checkbox indicates if the Name, Description and Help maintained in ''System Element'' table  or ''Window'' table.','Y','N','N','Y','N','Zentral verwaltet',0,90,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2547,0,246,541391,550346,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,100,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3110,0,246,541391,550347,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Name der Spalte in der Datenbank','"Spaltenname" bezeichnet den Namen einer Spalte einer Tabelle wie in der Datenbank definiert.','Y','N','N','Y','N','Spaltenname',0,110,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5818,0,246,541391,550348,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.','Das "System-Element" ermöglicht die zentrale  Verwaltung von Hilfe, Beschreibung und Terminologie für eine Tabellenspalte.','Y','N','N','Y','N','System-Element',0,120,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2539,0,246,541391,550349,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Systemreferenz und Validierung','Die Referenz kann über den Datentyp, eine Liste oder eine Tabelle erfolgen.','Y','N','N','Y','N','Referenz',0,130,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553878,0,246,541391,550350,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Automatic completion for textfields','The autocompletion uses all existing values (from the same client and organization) of the field.','Y','N','N','Y','N','Autocomplete',0,140,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2540,0,246,541391,550351,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.','"Referenzwert" bezeichnet, wo die Referenzwerte gespeichert werden. Es muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.','Y','N','N','Y','N','Referenzschlüssel',0,150,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2907,0,246,541391,550352,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"','<B>Validation elements:</B>
 	(Space) any character
_	Space (fixed character)
l	any Letter a..Z NO space
L	any Letter a..Z NO space converted to upper case
o	any Letter a..Z or space
O	any Letter a..Z or space converted to upper case
a	any Letters & Digits NO space
A	any Letters & Digits NO space converted to upper case
c	any Letters & Digits or space
C	any Letters & Digits or space converted to upper case
0	Digits 0..9 NO space
9	Digits 0..9 or space

Example of format "(000)_000-0000"','Y','N','N','Y','N','Value Format',0,160,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2901,0,246,541391,550353,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Regel für die  dynamische Validierung','Diese Regeln bestimmen, wie ein Eintrag als gültig bewertet wird. Sie können Variablen für eine dynamische (kontextbezogene) Validierung verwenden.','Y','N','N','Y','N','Dynamische Validierung',0,170,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2903,0,246,541391,550354,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Length of the column in the database','The Length indicates the length of a column as defined in the database.','Y','N','N','Y','N','Länge',0,180,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2904,0,246,541391,550355,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Data entry is required in this column','The field must have a value for the record to be saved to the database.','Y','N','N','Y','N','Pflichtangabe',0,190,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2544,0,246,541391,550356,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'The parameter is a range of values','The Range checkbox indicates that this parameter is a range of values.','Y','N','N','Y','N','Range',0,200,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2902,0,246,541391,550357,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Alternativen zur Bestimmung eines Standardwertes, separiert durch Semikolon','Die Alternativen werden in Reihenfolge der Definition ausgewertet; der erste ''not null'' Wert wird der Standardwert für diese Spalte. Die Alternativen sind durch Komma oder Semikolon separiert. a) Literale:. ''Text'' oder 123 b) Variablen - im Format @Variable@ - Logindvariablen: z.B. #Date, #AD_Org_ID, #AD_Client_ID - Buchführungsschemavariablen: z.B. $C_AcctSchema_ID, $C_Calendar_ID - Variablen globaler Standardwerte: z.B. DateFormat - Fensterwerte (alle Auswahlen aus Listen, Selektionsfelder, Auswahlknöpfe und DateDoc/DateAcct) c) SQL-Code mit dem Marker: @SQL=SELECT irgendwas AS DefaultValue FROM ... Das SQL-Statement kann Variablen enthalten. Es kann neben dem SQL-Statement keine andere Alternative geben. Die Standardwert-Logik wird nur ausgewertet, wenn der Nutzer keinen Standardwert vorgegeben hat. Standardwertdefinitionen werden für Spalten wie Schlüssel, Übergeordnet, Mandant sowie bei Schaltflächen ignoriert.','Y','N','N','Y','N','Standardwert-Logik',0,210,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:16.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4398,0,246,541391,550358,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Alternativen zur Bestimmung eines Standardwertes, separiert durch Semikolon','Die Alternativen werden in Reihenfolge der Definition ausgewertet; der erste ''not null'' Wert wird der Standardwert für diese Spalte. Die Alternativen sind durch Komma oder Semikolon separiert. a) Literale:. ''Text'' oder 123 b) Variablen - im Format @Variable@ - Logindvariablen: z.B. #Date, #AD_Org_ID, #AD_Client_ID - Buchführungsschemavariablen: z.B. $C_AcctSchema_ID, $C_Calendar_ID - Variablen globaler Standardwerte: z.B. DateFormat - Fensterwerte (alle Auswahlen aus Listen, Selektionsfelder, Auswahlknöpfe und DateDoc/DateAcct) c) SQL-Code mit dem Marker: @SQL=SELECT irgendwas AS DefaultValue FROM ... Das SQL-Statement kann Variablen enthalten. Es kann neben dem SQL-Statement keine andere Alternative geben. Die Standardwert-Logik wird nur ausgewertet, wenn der Nutzer keinen Standardwert vorgegeben hat. Standardwertdefinitionen werden für Spalten wie Schlüssel, Übergeordnet, Mandant sowie bei Schaltflächen ignoriert.','Y','N','N','Y','N','Default Logic 2',0,220,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2906,0,246,541391,550359,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Minimum Value for a field','The Minimum Value indicates the lowest  allowable value for a field.','Y','N','N','Y','N','Min. Wert',0,230,0,TO_TIMESTAMP('2018-01-20 11:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2905,0,246,541391,550360,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Maximum Value for a field','The Maximum Value indicates the highest allowable value for a field','Y','N','N','Y','N','Max. Wert',0,240,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56333,0,246,541391,550361,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Logic to determine if field is read only (applies only when field is read-write)','Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten können (optional) zwischen einfachen Anführungszeichen stehen.','Y','N','N','Y','N','Read Only Logic',0,250,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56334,0,246,541391,550362,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Wenn es ein anzuzeigendes Feld ist, bestimmt das Ergebnis, ob dieses Feld tatsächlich angezeigt wird.','Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten können (optional) zwischen einfachen Anführungszeichen stehen.','Y','N','N','Y','N','Anzeigelogik',0,260,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,59683,0,246,541391,550363,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Display or Storage is encrypted','Display encryption (in Window/Tab/Field) - all characters are displayed as ''*'' - in the database it is stored in clear text. You will not be able to report on these columns.<br>
Data storage encryption (in Table/Column) - data is stored encrypted in the database (dangerous!) and you will not be able to report on those columns. Independent from Display encryption.','Y','N','N','Y','N','Encrypted',0,270,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540719,540612,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:17.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540612 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:17.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540819,540612,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540819,541392,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53389,540613,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:17.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540613 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:17.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540820,540613,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540820,541393,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,61179,0,53389,541393,550364,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','Y','N','Prozess',0,10,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,61174,0,53389,541393,550365,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,20,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558789,0,53389,541393,550366,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Eingabe- oder Anzeige-Fenster','Das Feld "Fenster" identifiziert ein bestimmtes Fenster im system.','Y','N','N','Y','N','Fenster',0,30,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,61171,0,53389,541393,550367,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547617,0,53389,541393,550368,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','Y','N','Entitäts-Art',0,50,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557494,0,53389,541393,550369,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Quick action',0,60,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557495,0,53389,541393,550370,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Default quick action',0,70,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540771,540614,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-20T11:10:17.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540614 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-20T11:10:17.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540821,540614,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540821,541394,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557426,0,540771,541394,550371,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557432,0,540771,541394,550372,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','N','Y','N','Prozess',0,20,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557430,0,540771,541394,550373,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Internal statistics how often the entity was used','For internal use.','Y','N','N','Y','N','Statistic Count',0,30,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-20T11:10:17.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557431,0,540771,541394,550374,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'Internal statistics how many milliseconds a process took','For internal use','Y','N','N','Y','N','Statistic Milliseconds',0,40,0,TO_TIMESTAMP('2018-01-20 11:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

