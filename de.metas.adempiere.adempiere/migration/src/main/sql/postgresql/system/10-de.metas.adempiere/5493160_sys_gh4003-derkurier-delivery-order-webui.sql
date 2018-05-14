-- 2018-05-10T14:39:50.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Der Kurier Versandauftrag',Updated=TO_TIMESTAMP('2018-05-10 14:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541083
;

-- 2018-05-10T14:40:11.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 14:40:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Der Kurier Delivery Orders',WEBUI_NameBrowse='Der Kurier Delivery Orders' WHERE AD_Menu_ID=541083 AND AD_Language='en_US'
;

-- 2018-05-10T14:40:37.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 14:40:37','YYYY-MM-DD HH24:MI:SS'),Name='Der Kurier Delivery Order' WHERE AD_Window_ID=540432 AND AD_Language='en_US'
;

-- 2018-05-10T14:41:19.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2018-05-10 14:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541085
;

-- 2018-05-10T14:42:08.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2018-05-10 14:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541085
;

-- 2018-05-10T14:42:15.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541085,540736,TO_TIMESTAMP('2018-05-10 14:42:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-10 14:42:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-05-10T14:42:15.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540736 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-05-10T14:42:15.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540960,540736,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540961,540736,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540960,541579,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563974,0,541085,541579,551874,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gewünschtes Abholdatum',10,10,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563975,0,541085,541579,551875,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gewünschte Abholuhrzeit von',20,20,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563976,0,541085,541579,551876,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gewünschte Abholuhrzeit bis',30,30,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563882,0,541085,541579,551877,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',40,40,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563883,0,541085,541579,551878,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',50,50,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563981,0,541085,541579,551879,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Transport Auftrag',60,60,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563980,0,541085,541579,551880,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','Y','N','Lieferweg',70,70,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563885,0,541085,541579,551881,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','DerKurier_DeliveryOrder',80,80,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563884,0,541085,541579,551882,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',90,90,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563972,0,541085,541579,551883,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Absender Name',100,100,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563973,0,541085,541579,551884,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Absender Name2',110,110,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563977,0,541085,541579,551885,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Absender Name3',120,120,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563968,0,541085,541579,551886,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Absender Strasse',130,130,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563967,0,541085,541579,551887,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Absender Hausnummer',140,140,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563970,0,541085,541579,551888,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl','Y','N','Y','Y','N','Absender PLZ',150,150,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563965,0,541085,541579,551889,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Absender Ort',160,160,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563966,0,541085,541579,551890,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Zweistelliger ISO-3166 Ländercode','Y','N','Y','Y','N','Absender Land',170,170,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541086,540737,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-05-10T14:42:15.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540737 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-05-10T14:42:15.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540962,540737,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540962,541580,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563887,0,541086,541580,551891,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563888,0,541086,541580,551892,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:15.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563889,0,541086,541580,551893,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563891,0,541086,541580,551894,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','DerKurier_DeliveryOrder',0,40,0,TO_TIMESTAMP('2018-05-10 14:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563895,0,541086,541580,551895,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Kundennummer',0,50,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563903,0,541086,541580,551896,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','N','Zeile Nr.',0,60,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563908,0,541086,541580,551897,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Sendungsnummer',0,70,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563892,0,541086,541580,551898,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Name',0,80,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563893,0,541086,541580,551899,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Name2',0,90,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563894,0,541086,541580,551900,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Name3',0,100,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563899,0,541086,541580,551901,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Strasse',0,110,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563954,0,541086,541580,551902,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','N','Empfänger Hausnummer',0,120,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563897,0,541086,541580,551903,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl','Y','N','N','Y','N','Empfänger PLZ',0,130,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563898,0,541086,541580,551904,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Ort',0,140,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563896,0,541086,541580,551905,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Zweistelliger ISO-3166 Ländercode','Y','N','N','Y','N','Empfänger Land',0,150,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563912,0,541086,541580,551906,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Email',0,160,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563910,0,541086,541580,551907,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Empfänger Telefon',0,170,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563913,0,541086,541580,551908,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Gewünschtes Lieferdepot',0,180,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563900,0,541086,541580,551909,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Gewünschtes Lieferdatum',0,190,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563902,0,541086,541580,551910,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Gewünschte Lieferuhrzeit bis',0,200,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563901,0,541086,541580,551911,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Gewünschte Lieferuhrzeit von',0,210,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563911,0,541086,541580,551912,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Gesamtpaketanzahl',0,220,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563909,0,541086,541580,551913,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Referenz',0,230,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563906,0,541086,541580,551914,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Paketbreite',0,240,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563907,0,541086,541580,551915,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Pakethöhe',0,250,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563905,0,541086,541580,551916,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Paketlänge',0,260,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T14:42:16.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563904,0,541086,541580,551917,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Paketgewicht',0,270,0,TO_TIMESTAMP('2018-05-10 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T15:29:18.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540961,541581,TO_TIMESTAMP('2018-05-10 15:29:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-05-10 15:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T15:29:40.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540961,541582,TO_TIMESTAMP('2018-05-10 15:29:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','datetime',20,TO_TIMESTAMP('2018-05-10 15:29:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T15:29:46.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540961,541583,TO_TIMESTAMP('2018-05-10 15:29:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2018-05-10 15:29:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T15:32:52.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541581, SeqNo=10,Updated=TO_TIMESTAMP('2018-05-10 15:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551882
;

-- 2018-05-10T15:33:05.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541582, SeqNo=10,Updated=TO_TIMESTAMP('2018-05-10 15:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551874
;

-- 2018-05-10T15:33:14.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541582, SeqNo=20,Updated=TO_TIMESTAMP('2018-05-10 15:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551875
;

-- 2018-05-10T15:33:22.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541582, SeqNo=30,Updated=TO_TIMESTAMP('2018-05-10 15:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551876
;

-- 2018-05-10T15:33:34.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541583, SeqNo=10,Updated=TO_TIMESTAMP('2018-05-10 15:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551878
;

-- 2018-05-10T15:33:43.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541583, SeqNo=20,Updated=TO_TIMESTAMP('2018-05-10 15:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551877
;

-- 2018-05-10T15:34:06.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540960,541584,TO_TIMESTAMP('2018-05-10 15:34:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','sender',20,TO_TIMESTAMP('2018-05-10 15:34:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-10T15:34:38.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=10,Updated=TO_TIMESTAMP('2018-05-10 15:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551883
;

-- 2018-05-10T15:34:43.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=20,Updated=TO_TIMESTAMP('2018-05-10 15:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551884
;

-- 2018-05-10T15:34:48.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=30,Updated=TO_TIMESTAMP('2018-05-10 15:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551885
;

-- 2018-05-10T15:34:54.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=40,Updated=TO_TIMESTAMP('2018-05-10 15:34:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551886
;

-- 2018-05-10T15:35:00.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=50,Updated=TO_TIMESTAMP('2018-05-10 15:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551887
;

-- 2018-05-10T15:35:07.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=60,Updated=TO_TIMESTAMP('2018-05-10 15:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551888
;

-- 2018-05-10T15:35:26.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=70,Updated=TO_TIMESTAMP('2018-05-10 15:35:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551889
;

-- 2018-05-10T15:35:31.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541584, SeqNo=80,Updated=TO_TIMESTAMP('2018-05-10 15:35:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551890
;

-- 2018-05-10T15:36:08.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2018-05-10 15:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541085
;

-- 2018-05-10T15:38:24.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=551881
;

-- 2018-05-10T15:38:39.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2018-05-10 15:38:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541085
;

-- 2018-05-10T15:40:42.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551877
;

-- 2018-05-10T15:40:42.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551879
;

-- 2018-05-10T15:40:42.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551880
;

-- 2018-05-10T15:40:42.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551874
;

-- 2018-05-10T15:40:42.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551875
;

-- 2018-05-10T15:40:42.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551876
;

-- 2018-05-10T15:40:42.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551882
;

-- 2018-05-10T15:40:42.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551883
;

-- 2018-05-10T15:40:42.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551884
;

-- 2018-05-10T15:40:42.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551885
;

-- 2018-05-10T15:40:42.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551886
;

-- 2018-05-10T15:40:42.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551887
;

-- 2018-05-10T15:40:42.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551888
;

-- 2018-05-10T15:40:42.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551889
;

-- 2018-05-10T15:40:42.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551890
;

-- 2018-05-10T15:40:42.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2018-05-10 15:40:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551878
;

-- 2018-05-10T15:41:02.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2018-05-10 15:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551879
;

-- 2018-05-10T15:41:02.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2018-05-10 15:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551880
;

-- 2018-05-10T15:41:02.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2018-05-10 15:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551874
;

-- 2018-05-10T15:41:02.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2018-05-10 15:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551878
;

-- 2018-05-10T15:47:35.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551894
;

-- 2018-05-10T15:47:35.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551910
;

-- 2018-05-10T15:47:35.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551911
;

-- 2018-05-10T15:47:35.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551909
;

-- 2018-05-10T15:47:35.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551908
;

-- 2018-05-10T15:47:35.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551891
;

-- 2018-05-10T15:47:35.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551896
;

-- 2018-05-10T15:47:35.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551895
;

-- 2018-05-10T15:47:35.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551897
;

-- 2018-05-10T15:47:35.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551898
;

-- 2018-05-10T15:47:35.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551899
;

-- 2018-05-10T15:47:35.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551900
;

-- 2018-05-10T15:47:35.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551901
;

-- 2018-05-10T15:47:35.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551902
;

-- 2018-05-10T15:47:35.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551903
;

-- 2018-05-10T15:47:35.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551904
;

-- 2018-05-10T15:47:35.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551905
;

-- 2018-05-10T15:47:35.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551906
;

-- 2018-05-10T15:47:35.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551907
;

-- 2018-05-10T15:47:35.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551912
;

-- 2018-05-10T15:47:35.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551913
;

-- 2018-05-10T15:47:35.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551914
;

-- 2018-05-10T15:47:35.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551915
;

-- 2018-05-10T15:47:35.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551916
;

-- 2018-05-10T15:47:35.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551917
;

-- 2018-05-10T15:47:35.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551893
;

-- 2018-05-10T15:47:35.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2018-05-10 15:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551892
;

-- 2018-05-10T15:51:08.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-05-10 15:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551896
;

-- 2018-05-10T15:51:10.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-05-10 15:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551895
;

-- 2018-05-10T15:51:12.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-05-10 15:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551897
;

-- 2018-05-10T15:51:13.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-05-10 15:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551898
;

-- 2018-05-10T15:51:16.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-05-10 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551899
;

-- 2018-05-10T15:51:17.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-05-10 15:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551900
;

-- 2018-05-10T15:51:19.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-05-10 15:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551901
;

-- 2018-05-10T15:51:20.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-05-10 15:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551902
;

-- 2018-05-10T15:51:22.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-05-10 15:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551903
;

-- 2018-05-10T15:51:24.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2018-05-10 15:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551904
;

-- 2018-05-10T15:51:26.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2018-05-10 15:51:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551905
;

-- 2018-05-10T15:51:29.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2018-05-10 15:51:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551906
;

-- 2018-05-10T15:51:31.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2018-05-10 15:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551907
;

-- 2018-05-10T15:51:33.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2018-05-10 15:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551912
;

-- 2018-05-10T15:51:35.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2018-05-10 15:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551913
;

-- 2018-05-10T15:51:37.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2018-05-10 15:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551914
;

-- 2018-05-10T15:51:40.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2018-05-10 15:51:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551915
;

-- 2018-05-10T15:51:42.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2018-05-10 15:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551916
;

-- 2018-05-10T15:51:45.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2018-05-10 15:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551917
;

-- 2018-05-10T15:51:47.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2018-05-10 15:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551893
;

-- 2018-05-10T15:51:50.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2018-05-10 15:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551892
;

-- 2018-05-10T15:51:55.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2018-05-10 15:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551892
;

-- 2018-05-10T15:51:59.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2018-05-10 15:51:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551911
;

-- 2018-05-10T15:52:09.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2018-05-10 15:52:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551910
;

-- 2018-05-10T15:52:13.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=2330,Updated=TO_TIMESTAMP('2018-05-10 15:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551909
;

-- 2018-05-10T15:52:16.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2018-05-10 15:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551908
;

-- 2018-05-10T15:52:20.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=230,Updated=TO_TIMESTAMP('2018-05-10 15:52:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551909
;

-- 2018-05-10T15:52:23.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2018-05-10 15:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551892
;

-- 2018-05-10T15:52:28.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2018-05-10 15:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551891
;

-- 2018-05-10T15:52:31.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551891
;

-- 2018-05-10T15:52:32.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551911
;

-- 2018-05-10T15:52:32.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551910
;

-- 2018-05-10T15:52:33.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551909
;

-- 2018-05-10T15:52:34.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551908
;

-- 2018-05-10T15:52:34.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551896
;

-- 2018-05-10T15:52:35.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551895
;

-- 2018-05-10T15:52:35.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551897
;

-- 2018-05-10T15:52:35.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551898
;

-- 2018-05-10T15:52:36.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551899
;

-- 2018-05-10T15:52:36.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551900
;

-- 2018-05-10T15:52:37.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551901
;

-- 2018-05-10T15:52:38.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551902
;

-- 2018-05-10T15:52:38.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551903
;

-- 2018-05-10T15:52:39.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551904
;

-- 2018-05-10T15:52:39.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551905
;

-- 2018-05-10T15:52:40.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551906
;

-- 2018-05-10T15:52:40.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551907
;

-- 2018-05-10T15:52:41.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551912
;

-- 2018-05-10T15:52:41.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551913
;

-- 2018-05-10T15:52:42.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551914
;

-- 2018-05-10T15:52:43.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551915
;

-- 2018-05-10T15:52:43.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551916
;

-- 2018-05-10T15:52:44.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551917
;

-- 2018-05-10T15:52:44.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551893
;

-- 2018-05-10T15:52:46.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-10 15:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551892
;

-- 2018-05-10T15:56:26.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:56:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=563882 AND AD_Language='en_US'
;

-- 2018-05-10T15:56:37.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:56:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=563883 AND AD_Language='en_US'
;

-- 2018-05-10T15:56:52.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:56:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Transportation Order' WHERE AD_Field_ID=563981 AND AD_Language='en_US'
;

-- 2018-05-10T15:57:04.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:57:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipper',Description='',Help='' WHERE AD_Field_ID=563980 AND AD_Language='en_US'
;

-- 2018-05-10T15:57:18.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:57:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=563884 AND AD_Language='en_US'
;

-- 2018-05-10T15:57:31.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:57:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender Name' WHERE AD_Field_ID=563972 AND AD_Language='en_US'
;

-- 2018-05-10T15:57:48.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:57:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender Name2' WHERE AD_Field_ID=563973 AND AD_Language='en_US'
;

-- 2018-05-10T15:57:56.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:57:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender Name3' WHERE AD_Field_ID=563977 AND AD_Language='en_US'
;

-- 2018-05-10T15:58:07.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:58:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender Street' WHERE AD_Field_ID=563968 AND AD_Language='en_US'
;

-- 2018-05-10T15:58:25.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:58:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender House No' WHERE AD_Field_ID=563967 AND AD_Language='en_US'
;

-- 2018-05-10T15:58:43.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:58:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender Postal',Description='' WHERE AD_Field_ID=563970 AND AD_Language='en_US'
;

-- 2018-05-10T15:58:53.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:58:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender City' WHERE AD_Field_ID=563965 AND AD_Language='en_US'
;

-- 2018-05-10T15:59:06.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:59:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender Country',Description='' WHERE AD_Field_ID=563966 AND AD_Language='en_US'
;

-- 2018-05-10T15:59:31.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 15:59:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preferred Pickup Time' WHERE AD_Field_ID=563975 AND AD_Language='en_US'
;

-- 2018-05-10T16:00:17.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:00:17','YYYY-MM-DD HH24:MI:SS'),Name='Preferred Pickup Time From' WHERE AD_Field_ID=563975 AND AD_Language='en_US'
;

-- 2018-05-10T16:00:37.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:00:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preferred Pickup Time To' WHERE AD_Field_ID=563976 AND AD_Language='en_US'
;

-- 2018-05-10T16:00:58.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:00:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preferred Pickup Date' WHERE AD_Field_ID=563974 AND AD_Language='en_US'
;

-- 2018-05-10T16:01:12.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:01:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Country',Description='',Help='' WHERE AD_Field_ID=563971 AND AD_Language='en_US'
;

-- 2018-05-10T16:06:13.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:06:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=563889 AND AD_Language='en_US'
;

-- 2018-05-10T16:06:33.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:06:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Customer No' WHERE AD_Field_ID=563895 AND AD_Language='en_US'
;

-- 2018-05-10T16:06:45.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:06:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Seq No',Description='',Help='' WHERE AD_Field_ID=563903 AND AD_Language='en_US'
;

-- 2018-05-10T16:06:53.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:06:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Parcel No' WHERE AD_Field_ID=563908 AND AD_Language='en_US'
;

-- 2018-05-10T16:07:05.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:07:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Recepient Name' WHERE AD_Field_ID=563892 AND AD_Language='en_US'
;

-- 2018-05-10T16:07:20.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:07:20','YYYY-MM-DD HH24:MI:SS'),Name='Consignee Name' WHERE AD_Field_ID=563892 AND AD_Language='en_US'
;

-- 2018-05-10T16:07:27.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:07:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Name2' WHERE AD_Field_ID=563893 AND AD_Language='en_US'
;

-- 2018-05-10T16:07:34.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:07:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Name3' WHERE AD_Field_ID=563894 AND AD_Language='en_US'
;

-- 2018-05-10T16:07:44.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:07:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Street' WHERE AD_Field_ID=563899 AND AD_Language='en_US'
;

-- 2018-05-10T16:07:54.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:07:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee House No' WHERE AD_Field_ID=563954 AND AD_Language='en_US'
;

-- 2018-05-10T16:08:07.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:08:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Postal',Description='' WHERE AD_Field_ID=563897 AND AD_Language='en_US'
;

-- 2018-05-10T16:08:19.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:08:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee City' WHERE AD_Field_ID=563898 AND AD_Language='en_US'
;

-- 2018-05-10T16:08:31.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:08:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Country',Description='' WHERE AD_Field_ID=563896 AND AD_Language='en_US'
;

-- 2018-05-10T16:08:40.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:08:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee EMail' WHERE AD_Field_ID=563912 AND AD_Language='en_US'
;

-- 2018-05-10T16:08:50.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:08:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Phone' WHERE AD_Field_ID=563910 AND AD_Language='en_US'
;

-- 2018-05-10T16:09:09.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:09:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Depot' WHERE AD_Field_ID=563913 AND AD_Language='en_US'
;

-- 2018-05-10T16:09:23.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:09:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Delivery Date' WHERE AD_Field_ID=563900 AND AD_Language='en_US'
;

-- 2018-05-10T16:09:36.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:09:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Delivery Time To' WHERE AD_Field_ID=563902 AND AD_Language='en_US'
;

-- 2018-05-10T16:09:50.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:09:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Consignee Delivery Time From' WHERE AD_Field_ID=563901 AND AD_Language='en_US'
;

-- 2018-05-10T16:10:07.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:10:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Package Amount' WHERE AD_Field_ID=563911 AND AD_Language='en_US'
;

-- 2018-05-10T16:10:16.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:10:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reference' WHERE AD_Field_ID=563909 AND AD_Language='en_US'
;

-- 2018-05-10T16:10:27.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:10:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Parcel Width' WHERE AD_Field_ID=563906 AND AD_Language='en_US'
;

-- 2018-05-10T16:10:37.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:10:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Parcel Height' WHERE AD_Field_ID=563907 AND AD_Language='en_US'
;

-- 2018-05-10T16:10:46.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:10:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Parcel Length' WHERE AD_Field_ID=563905 AND AD_Language='en_US'
;

-- 2018-05-10T16:10:56.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:10:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Parcel Weight' WHERE AD_Field_ID=563904 AND AD_Language='en_US'
;

-- 2018-05-10T16:11:06.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:11:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=563887 AND AD_Language='en_US'
;

-- 2018-05-10T16:11:17.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 16:11:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=563888 AND AD_Language='en_US'
;

-- 2018-05-10T16:11:51.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2018-05-10 16:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541085
;

-- 2018-05-10T16:20:46.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2018-05-10 16:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541085
;

