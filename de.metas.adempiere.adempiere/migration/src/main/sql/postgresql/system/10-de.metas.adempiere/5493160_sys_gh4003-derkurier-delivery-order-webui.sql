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

