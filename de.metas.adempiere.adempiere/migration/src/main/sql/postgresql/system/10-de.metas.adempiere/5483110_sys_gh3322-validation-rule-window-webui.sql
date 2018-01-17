-- 2018-01-17T11:22:28.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Validierungs Regeln',Updated=TO_TIMESTAMP('2018-01-17 11:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=103
;

-- 2018-01-17T11:22:28.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Verwalten von dynamischen Validierungs-Regeln für Spalten und Felder', IsActive='Y', Name='Validierungs Regeln',Updated=TO_TIMESTAMP('2018-01-17 11:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=142
;

-- 2018-01-17T11:22:38.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,108,540595,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-17T11:22:38.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540595 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-17T11:22:38.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540798,540595,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540799,540595,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540798,541366,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65359,0,108,541366,550187,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Java Classname','The Classname identifies the Java classname used by this report or process.','Y','N','Y','Y','N','Classname',10,10,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,278,0,108,541366,550188,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',20,20,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1991,0,108,541366,550189,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',30,30,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,194,0,108,541366,550190,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,195,0,108,541366,550191,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,277,0,108,541366,550192,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:38.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5812,0,108,541366,550193,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','Y','Y','N','Entitäts-Art',70,70,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,198,0,108,541366,550194,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Type of Validation (SQL, Java Script, Java Language)','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.','Y','N','Y','Y','N','Art',80,80,0,TO_TIMESTAMP('2018-01-17 11:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,199,0,108,541366,550195,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Validierungscode','Der "Validierungscode" zeigt Datum, Zeit und Fehlerinformation.','Y','N','Y','Y','N','Validierungscode',90,90,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,772,540596,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-17T11:22:39.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540596 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-17T11:22:39.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540800,540596,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540800,541367,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12526,0,772,541367,550196,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12543,0,772,541367,550197,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12553,0,772,541367,550198,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,30,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12530,0,772,541367,550199,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Name der Spalte in der Datenbank','"Spaltenname" bezeichnet den Namen einer Spalte einer Tabelle wie in der Datenbank definiert.','Y','N','N','Y','N','Spaltenname',0,40,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12552,0,772,541367,550200,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.','Das "System-Element" ermöglicht die zentrale  Verwaltung von Hilfe, Beschreibung und Terminologie für eine Tabellenspalte.','Y','N','N','Y','N','System-Element',0,50,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12542,0,772,541367,550201,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,60,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12532,0,772,541367,550202,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,70,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12529,0,772,541367,550203,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','Kommentar/Hilfe',0,80,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12523,0,772,541367,550204,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,90,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12538,0,772,541367,550205,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Length of the column in the database','The Length indicates the length of a column as defined in the database.','Y','N','N','Y','N','Länge',0,100,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12547,0,772,541367,550206,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Systemreferenz und Validierung','Die Referenz kann über den Datentyp, eine Liste oder eine Tabelle erfolgen.','Y','N','N','Y','N','Referenz',0,110,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12548,0,772,541367,550207,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.','"Referenzwert" bezeichnet, wo die Referenzwerte gespeichert werden. Es muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.','Y','N','N','Y','N','Referenzschlüssel',0,120,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12533,0,772,541367,550208,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Regel für die  dynamische Validierung','Diese Regeln bestimmen, wie ein Eintrag als gültig bewertet wird. Sie können Variablen für eine dynamische (kontextbezogene) Validierung verwenden.','Y','N','N','Y','N','Dynamische Validierung',0,130,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53572,540597,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-17T11:22:39.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540597 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-17T11:22:39.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540801,540597,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540801,541368,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65351,0,53572,541368,550209,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Regel für die  dynamische Validierung','Diese Regeln bestimmen, wie ein Eintrag als gültig bewertet wird. Sie können Variablen für eine dynamische (kontextbezogene) Validierung verwenden.','Y','N','N','Y','N','Dynamische Validierung',0,10,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65352,0,53572,541368,550210,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Dictionary Entity Type; Determines ownership and synchronization','The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!','Y','N','N','Y','N','Entitäts-Art',0,20,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65358,0,53572,541368,550211,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,30,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:22:39.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65355,0,53572,541368,550212,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Validation rule to be included ','Y','N','N','Y','N','Included_Val_Rule_ID',0,40,0,TO_TIMESTAMP('2018-01-17 11:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:28:04.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540799,541369,TO_TIMESTAMP('2018-01-17 11:28:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-01-17 11:28:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:28:07.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540799,541370,TO_TIMESTAMP('2018-01-17 11:28:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-01-17 11:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:28:18.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541369, SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550192
;

-- 2018-01-17T11:28:26.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541370, SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:28:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550189
;

-- 2018-01-17T11:28:34.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541370, SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550188
;

-- 2018-01-17T11:29:10.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540798,541371,TO_TIMESTAMP('2018-01-17 11:29:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','code',20,TO_TIMESTAMP('2018-01-17 11:29:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:29:29.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541371, SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:29:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550195
;

-- 2018-01-17T11:30:02.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550190
;

-- 2018-01-17T11:30:14.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550193
;

-- 2018-01-17T11:30:19.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-01-17 11:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550187
;

-- 2018-01-17T11:31:06.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550194
;

-- 2018-01-17T11:31:13.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541371, SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550193
;

-- 2018-01-17T11:31:22.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541371, SeqNo=30,Updated=TO_TIMESTAMP('2018-01-17 11:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550191
;

-- 2018-01-17T11:31:43.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550193
;

-- 2018-01-17T11:31:46.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:31:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550191
;

-- 2018-01-17T11:31:51.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-01-17 11:31:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550195
;

-- 2018-01-17T11:36:34.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541369, SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:36:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550193
;

-- 2018-01-17T11:36:46.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,108,540598,TO_TIMESTAMP('2018-01-17 11:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-01-17 11:36:46','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2018-01-17T11:36:46.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540598 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-17T11:36:48.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540802,540598,TO_TIMESTAMP('2018-01-17 11:36:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-17 11:36:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:36:55.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540802,541372,TO_TIMESTAMP('2018-01-17 11:36:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2018-01-17 11:36:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:37:13.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541372, SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:37:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550191
;

-- 2018-01-17T11:37:35.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-01-17 11:37:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550191
;

-- 2018-01-17T11:40:26.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550188
;

-- 2018-01-17T11:40:26.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550190
;

-- 2018-01-17T11:40:26.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550194
;

-- 2018-01-17T11:40:26.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550187
;

-- 2018-01-17T11:40:26.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550195
;

-- 2018-01-17T11:40:26.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550193
;

-- 2018-01-17T11:40:26.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550191
;

-- 2018-01-17T11:40:26.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550192
;

-- 2018-01-17T11:40:26.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-01-17 11:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550189
;

-- 2018-01-17T11:40:43.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2018-01-17 11:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550190
;

-- 2018-01-17T11:40:43.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2018-01-17 11:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550194
;

-- 2018-01-17T11:40:43.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2018-01-17 11:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550187
;

-- 2018-01-17T11:40:43.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2018-01-17 11:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550193
;

-- 2018-01-17T11:40:43.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2018-01-17 11:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550189
;

-- 2018-01-17T11:41:33.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2018-01-17 11:41:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550190
;

-- 2018-01-17T11:41:36.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2018-01-17 11:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550194
;

-- 2018-01-17T11:41:40.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2018-01-17 11:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550187
;

-- 2018-01-17T11:41:50.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-01-17 11:41:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550188
;

-- 2018-01-17T11:41:53.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-01-17 11:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550189
;

-- 2018-01-17T11:44:47.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550203
;

-- 2018-01-17T11:44:47.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550196
;

-- 2018-01-17T11:44:47.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550201
;

-- 2018-01-17T11:44:47.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550198
;

-- 2018-01-17T11:44:47.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550199
;

-- 2018-01-17T11:44:47.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550200
;

-- 2018-01-17T11:44:47.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550202
;

-- 2018-01-17T11:44:47.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550205
;

-- 2018-01-17T11:44:47.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550206
;

-- 2018-01-17T11:44:47.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550207
;

-- 2018-01-17T11:44:47.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550208
;

-- 2018-01-17T11:44:47.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550204
;

-- 2018-01-17T11:44:47.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-01-17 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550197
;

-- 2018-01-17T11:45:52.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550201
;

-- 2018-01-17T11:45:54.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550198
;

-- 2018-01-17T11:45:55.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-01-17 11:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550199
;

-- 2018-01-17T11:45:57.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-01-17 11:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550200
;

-- 2018-01-17T11:45:59.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-01-17 11:45:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550202
;

-- 2018-01-17T11:46:02.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-01-17 11:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550205
;

-- 2018-01-17T11:46:04.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-01-17 11:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550206
;

-- 2018-01-17T11:46:05.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-01-17 11:46:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550207
;

-- 2018-01-17T11:46:07.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-01-17 11:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550208
;

-- 2018-01-17T11:46:09.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2018-01-17 11:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550204
;

-- 2018-01-17T11:46:15.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2018-01-17 11:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550197
;

-- 2018-01-17T11:46:36.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2018-01-17 11:46:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550196
;

-- 2018-01-17T11:46:40.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2018-01-17 11:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550203
;

-- 2018-01-17T11:46:43.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2018-01-17 11:46:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550196
;

-- 2018-01-17T11:46:48.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2018-01-17 11:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550197
;

-- 2018-01-17T11:46:49.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550201
;

-- 2018-01-17T11:46:50.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550198
;

-- 2018-01-17T11:46:50.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550199
;

-- 2018-01-17T11:46:51.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550200
;

-- 2018-01-17T11:46:51.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550202
;

-- 2018-01-17T11:46:52.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550205
;

-- 2018-01-17T11:46:53.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550206
;

-- 2018-01-17T11:46:53.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550207
;

-- 2018-01-17T11:46:53.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550208
;

-- 2018-01-17T11:46:54.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550204
;

-- 2018-01-17T11:46:55.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550197
;

-- 2018-01-17T11:47:46.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65357,0,53572,541368,550213,'F',TO_TIMESTAMP('2018-01-17 11:47:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2018-01-17 11:47:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:47:58.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,65356,0,53572,541368,550214,'F',TO_TIMESTAMP('2018-01-17 11:47:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2018-01-17 11:47:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T11:48:19.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-01-17 11:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550213
;

-- 2018-01-17T11:48:39.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-01-17 11:48:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550209
;

-- 2018-01-17T11:48:42.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-01-17 11:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550210
;

-- 2018-01-17T11:48:46.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-01-17 11:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550211
;

-- 2018-01-17T11:48:47.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-01-17 11:48:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550212
;

-- 2018-01-17T11:48:49.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-01-17 11:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550213
;

-- 2018-01-17T11:48:54.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-01-17 11:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550214
;

-- 2018-01-17T11:48:54.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550209
;

-- 2018-01-17T11:48:54.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550210
;

-- 2018-01-17T11:48:55.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550211
;

-- 2018-01-17T11:48:56.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-01-17 11:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550212
;

-- 2018-01-17T11:53:56.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Enthaltene Validierungs Regel',Updated=TO_TIMESTAMP('2018-01-17 11:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53572
;

-- 2018-01-17T11:54:03.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Description='Validierungs Regeln',Updated=TO_TIMESTAMP('2018-01-17 11:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=108
;

-- 2018-01-17T11:54:21.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Entitäts Art',Updated=TO_TIMESTAMP('2018-01-17 11:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5812
;

-- 2018-01-17T11:54:30.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Klassenname',Updated=TO_TIMESTAMP('2018-01-17 11:54:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=65359
;

-- 2018-01-17T11:55:04.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='System Element',Updated=TO_TIMESTAMP('2018-01-17 11:55:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12552
;

-- 2018-01-17T11:55:19.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auswahl Spalte',Updated=TO_TIMESTAMP('2018-01-17 11:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12549
;

-- 2018-01-17T11:55:25.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nur Lesen',Updated=TO_TIMESTAMP('2018-01-17 11:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12546
;

-- 2018-01-17T11:55:29.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Entitäts Art',Updated=TO_TIMESTAMP('2018-01-17 11:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12535
;

-- 2018-01-17T11:55:45.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Immer aktualisierbar',Updated=TO_TIMESTAMP('2018-01-17 11:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12524
;

-- 2018-01-17T11:55:53.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SQL Spalte',Updated=TO_TIMESTAMP('2018-01-17 11:55:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12528
;

-- 2018-01-17T11:55:56.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Standardwert Logik',Updated=TO_TIMESTAMP('2018-01-17 11:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12531
;

-- 2018-01-17T11:55:59.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Schlüssel Spalte',Updated=TO_TIMESTAMP('2018-01-17 11:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12537
;

-- 2018-01-17T11:56:06.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Eltern Spalte',Updated=TO_TIMESTAMP('2018-01-17 11:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12544
;

-- 2018-01-17T11:56:15.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Identifikator',Updated=TO_TIMESTAMP('2018-01-17 11:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12536
;

-- 2018-01-17T11:56:27.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verschlüsselung',Updated=TO_TIMESTAMP('2018-01-17 11:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12534
;

-- 2018-01-17T11:56:34.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wert Format',Updated=TO_TIMESTAMP('2018-01-17 11:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12556
;

-- 2018-01-17T11:56:50.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktualisierbar',Updated=TO_TIMESTAMP('2018-01-17 11:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12555
;

-- 2018-01-17T11:57:09.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Entitäts Art',Updated=TO_TIMESTAMP('2018-01-17 11:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=65352
;

-- 2018-01-17T11:57:28.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Enthaltene Validierungs Regel',Updated=TO_TIMESTAMP('2018-01-17 11:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=65355
;

-- 2018-01-17T11:57:38.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommentar/ Hilfe',Updated=TO_TIMESTAMP('2018-01-17 11:57:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=65350
;

-- 2018-01-17T11:57:45.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sektion',Updated=TO_TIMESTAMP('2018-01-17 11:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=65357
;

-- 2018-01-17T11:58:33.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-17 11:58:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Included Validation Rule' WHERE AD_Tab_ID=53572 AND AD_Language='en_US'
;

-- 2018-01-17T11:59:24.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-17 11:59:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='SeqNo',Description='',Help='' WHERE AD_Field_ID=65358 AND AD_Language='en_US'
;

-- 2018-01-17T11:59:43.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-17 11:59:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Entity Type' WHERE AD_Field_ID=65352 AND AD_Language='en_US'
;

-- 2018-01-17T12:00:08.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-17 12:00:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dynamic Validation',Description='',Help='' WHERE AD_Field_ID=65351 AND AD_Language='en_US'
;

-- 2018-01-17T12:00:38.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-17 12:00:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Included Validation Rule' WHERE AD_Field_ID=65355 AND AD_Language='en_US'
;

