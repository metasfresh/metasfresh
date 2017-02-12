-- 12.02.2017 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-02-12 15:42:02','YYYY-MM-DD HH24:MI:SS'),Name='Organisation',Description='Organisational entity within client',Help='An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.' WHERE AD_Field_ID=3155 AND AD_Language='en_US'
;

-- 12.02.2017 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-02-12 15:42:46','YYYY-MM-DD HH24:MI:SS'),Name='Letter Salutation' WHERE AD_Field_ID=551741 AND AD_Language='en_US'
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,282,540052,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540074,540052,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540075,540052,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540074,540102,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3154,0,282,540102,541473,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3155,0,282,540102,541474,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3162,0,282,540102,541475,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3159,0,282,540102,541476,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',40,40,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3158,0,282,540102,541477,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt','Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','N','Y','Y','N','Anrede',50,50,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3161,0,282,540102,541478,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Nur den ersten Namen bei Anreden drucken','Das Selektionsfeld "Nur erster Name" zeigt an, dass nur der erste Name dieses Kontaktes bei der Anrede gedruckt weren soll.','Y','N','Y','Y','N','Nur erster Name',60,60,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3160,0,282,540102,541479,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','Y','N','Standard',70,70,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551741,0,282,540102,541480,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Briefanrede',80,80,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,283,540053,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540076,540053,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540076,540103,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3163,0,283,540103,541481,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3165,0,283,540103,541482,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3166,0,283,540103,541483,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','Y','N','Anrede',0,30,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3164,0,283,540103,541484,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','Sprache',0,40,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3168,0,283,540103,541485,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3169,0,283,540103,541486,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','Y','N','Übersetzt',0,60,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3170,0,283,540103,541487,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,70,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3167,0,283,540103,541488,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Für Briefe - z.B. "Sehr geehrter {0}" oder "Sehr geehrter Herr {0}" - Zur Laufzeit wird  "{0}" durch den Namen ersetzt','Anrede definiert, was auf einem Brief an einen Geschäftspartner gedruckt wird.','Y','N','N','Y','N','Anrede',0,80,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551757,0,283,540103,541489,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Briefanrede',0,90,0,TO_TIMESTAMP('2017-02-12 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2017 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-02-12 15:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541475
;

-- 12.02.2017 15:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-02-12 15:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541473
;

-- 12.02.2017 15:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=85,Updated=TO_TIMESTAMP('2017-02-12 15:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541474
;

-- 12.02.2017 15:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2017-02-12 15:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541476
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541473
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541475
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541477
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541480
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541478
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541479
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541476
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-02-12 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541474
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-02-12 15:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541477
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-02-12 15:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541480
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-02-12 15:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541478
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-02-12 15:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541479
;

-- 12.02.2017 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-02-12 15:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541474
;

