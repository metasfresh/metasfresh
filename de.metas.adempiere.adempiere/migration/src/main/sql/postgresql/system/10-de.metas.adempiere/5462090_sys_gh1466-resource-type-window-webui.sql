-- 2017-05-07T15:12:44.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,418,540166,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:44.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540231,540166,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:44.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540232,540166,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:44.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540231,540361,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:44.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5540,0,418,540361,543752,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-07 15:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5541,0,418,540361,543753,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5543,0,418,540361,543754,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5544,0,418,540361,543755,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5545,0,418,540361,543756,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5542,0,418,540361,543757,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5546,0,418,540361,543758,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','Y','N','Maßeinheit',70,70,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5547,0,418,540361,543759,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Bruchteil der Mengeneinheit zulassen','Wenn zulässig können Sie Bruchteile der Mengeneinheit eingeben','Y','N','Y','Y','N','Bruchteil der Mengeneinheit zulassen',80,80,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5588,0,418,540361,543760,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','Y','N','Produkt-Kategorie',90,90,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5587,0,418,540361,543761,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','Y','N','Steuerkategorie',100,100,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5581,0,418,540361,543762,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Nur eine Zuordnung zur Zeit (keine Doppelbuchung oder überlappende Buchung)','If selected, you can only have one assignment for the resource at a single point in time.   It is also  not possible to have overlapping assignments.','Y','N','Y','Y','N','Nur einmalige Zuordnung',110,110,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5578,0,418,540361,543763,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Ressource hat eingeschränkte zeitliche Verfügbarkeit','Ressource ist nur zu bestimmten Zeiten verfügbar','Y','N','Y','Y','N','Zeitabschnitt',120,120,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5584,0,418,540361,543764,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Start des Zeitabschnittes','Startzeitpunkt für Zeitabschnitt','Y','N','Y','Y','N','Startzeitpunkt',130,130,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5579,0,418,540361,543765,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Ende des Zeitabschnittes','Endzeitpunkt für Zeitabschnitt','Y','N','Y','Y','N','Endzeitpunkt',140,140,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5586,0,418,540361,543766,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Ressource hat eingeschränkte Tageverfügbarkeit','Ressource ist nur an bestimmten Tagen verfügbar','Y','N','Y','Y','N','Tag',150,150,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5576,0,418,540361,543767,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Sonntags verfügbar','Y','N','Y','Y','N','Sonntag',160,160,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5585,0,418,540361,543768,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Montags verfügbar','Y','N','Y','Y','N','Montag',170,170,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5575,0,418,540361,543769,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Dienstags verfügbar','Y','N','Y','Y','N','Dienstag',180,180,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5583,0,418,540361,543770,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Mittwochs verfügbar','Y','N','Y','Y','N','Mittwoch',190,190,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5582,0,418,540361,543771,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Donnerstags verfügbar','Y','N','Y','Y','N','Donnerstag',200,200,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5580,0,418,540361,543772,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Freitags verfügbar','Y','N','Y','Y','N','Freitag',210,210,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-07T15:12:45.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5577,0,418,540361,543773,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Samstags verfügbar','Y','N','Y','Y','N','Samstag',220,220,0,TO_TIMESTAMP('2017-05-07 15:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

