-- 2017-08-30T14:45:13.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540632,540446,TO_TIMESTAMP('2017-08-30 14:45:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-30 14:45:12','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-30T14:45:13.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540446 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-30T14:45:13.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540596,540446,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540597,540446,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540596,541050,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554954,0,540632,541050,547852,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,10,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554955,0,540632,541050,547853,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,20,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554958,0,540632,541050,547854,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,30,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554960,0,540632,541050,547855,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,40,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554963,0,540632,541050,547856,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,50,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554961,0,540632,541050,547857,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,60,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554957,0,540632,541050,547858,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,70,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554956,0,540632,541050,547859,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Async Batch',50,80,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554962,0,540632,541050,547860,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses','Y','N','Y','Y','N','Prozess-Instanz',60,90,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554953,0,540632,541050,547861,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',70,100,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556645,0,540632,541050,547862,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Expected',80,110,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556696,0,540632,541050,547863,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','LastProcessed WorkPackage',90,120,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555013,0,540632,541050,547864,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Enqueued',100,130,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555017,0,540632,541050,547865,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Verarbeitet',110,140,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555014,0,540632,541050,547866,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','First Enqueued',120,150,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555015,0,540632,541050,547867,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Last Enqueued',130,160,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555016,0,540632,541050,547868,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Last Processed',140,170,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555018,0,540632,541050,547869,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',150,180,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554959,0,540632,541050,547870,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','Y','Y','N','Erstellt durch',160,190,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555043,0,540632,541050,547871,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Async Batch Type',170,200,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556938,0,540632,541050,547872,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Parent_Async_Batch_ID',180,210,0,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:13.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540728,540447,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-30T14:45:13.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540447 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-30T14:45:13.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540598,540447,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540598,541051,TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-30 14:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556703,0,540728,541051,547873,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,10,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556704,0,540728,541051,547874,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,20,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556705,0,540728,541051,547875,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,30,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556706,0,540728,541051,547876,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Async Batch',0,40,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556707,0,540728,541051,547877,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,50,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556708,0,540728,541051,547878,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,60,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556709,0,540728,541051,547879,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,70,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556710,0,540728,541051,547880,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Notified',0,80,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556711,0,540728,541051,547881,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,90,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556713,0,540728,541051,547882,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Notified',0,100,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556714,0,540728,541051,547883,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Queue',0,110,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556715,0,540728,541051,547884,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Bach Workpackage SeqNo',0,120,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540636,540448,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-30T14:45:14.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540448 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-30T14:45:14.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540599,540448,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540599,541052,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555002,0,540636,541052,547885,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,10,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555003,0,540636,541052,547886,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,20,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555004,0,540636,541052,547887,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Async Batch',0,30,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555005,0,540636,541052,547888,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,40,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555006,0,540636,541052,547889,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Enqueued',0,50,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555007,0,540636,541052,547890,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,60,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555008,0,540636,541052,547891,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,70,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555009,0,540636,541052,547892,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,80,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555010,0,540636,541052,547893,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Verarbeitet',0,90,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:45:14.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555011,0,540636,541052,547894,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','WorkPackage Processor',0,100,0,TO_TIMESTAMP('2017-08-30 14:45:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:51:41.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540596,541053,TO_TIMESTAMP('2017-08-30 14:51:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-30 14:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T14:51:48.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit',Updated=TO_TIMESTAMP('2017-08-30 14:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541050
;

-- 2017-08-30T14:54:11.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2017-08-30 14:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541050
;

-- 2017-08-30T15:23:25.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554961,0,540632,541053,547895,TO_TIMESTAMP('2017-08-30 15:23:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-08-30 15:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:24:00.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555043,0,540632,541053,547896,TO_TIMESTAMP('2017-08-30 15:24:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Async Stapel Art',20,0,0,TO_TIMESTAMP('2017-08-30 15:24:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:24:17.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554962,0,540632,541053,547897,TO_TIMESTAMP('2017-08-30 15:24:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Prozess Instanz',30,0,0,TO_TIMESTAMP('2017-08-30 15:24:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:25:07.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556938,0,540632,541053,547898,TO_TIMESTAMP('2017-08-30 15:25:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Eltern Prozess',40,0,0,TO_TIMESTAMP('2017-08-30 15:25:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:25:23.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540596,541054,TO_TIMESTAMP('2017-08-30 15:25:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','stats',20,TO_TIMESTAMP('2017-08-30 15:25:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:25:46.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556696,0,540632,541054,547899,TO_TIMESTAMP('2017-08-30 15:25:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Letztes Arbeitspaket',10,0,0,TO_TIMESTAMP('2017-08-30 15:25:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:26:25.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555014,0,540632,541054,547900,TO_TIMESTAMP('2017-08-30 15:26:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Erstmalig eingereiht',20,0,0,TO_TIMESTAMP('2017-08-30 15:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:26:49.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555015,0,540632,541054,547901,TO_TIMESTAMP('2017-08-30 15:26:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zuletzt eingereiht',30,0,0,TO_TIMESTAMP('2017-08-30 15:26:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:27:10.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556696,0,540632,541054,547902,TO_TIMESTAMP('2017-08-30 15:27:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zuletzt verarbeitet',40,0,0,TO_TIMESTAMP('2017-08-30 15:27:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:27:14.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-08-30 15:27:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547902
;

-- 2017-08-30T15:27:17.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-30 15:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547900
;

-- 2017-08-30T15:27:20.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-08-30 15:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547901
;

-- 2017-08-30T15:27:25.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-30 15:27:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547900
;

-- 2017-08-30T15:27:28.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-30 15:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547901
;

-- 2017-08-30T15:27:32.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-08-30 15:27:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547899
;

-- 2017-08-30T15:27:35.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-30 15:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547902
;

-- 2017-08-30T15:27:46.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540597,541055,TO_TIMESTAMP('2017-08-30 15:27:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-08-30 15:27:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:27:55.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554953,0,540632,541055,547903,TO_TIMESTAMP('2017-08-30 15:27:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-08-30 15:27:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:28:12.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555018,0,540632,541055,547904,TO_TIMESTAMP('2017-08-30 15:28:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',20,0,0,TO_TIMESTAMP('2017-08-30 15:28:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:28:24.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554956,0,540632,541055,547905,TO_TIMESTAMP('2017-08-30 15:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Nr.',30,0,0,TO_TIMESTAMP('2017-08-30 15:28:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:28:55.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554959,0,540632,541055,547906,TO_TIMESTAMP('2017-08-30 15:28:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Erstellt durch',40,0,0,TO_TIMESTAMP('2017-08-30 15:28:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:29:06.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554958,0,540632,541055,547907,TO_TIMESTAMP('2017-08-30 15:29:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Erstellt am',50,0,0,TO_TIMESTAMP('2017-08-30 15:29:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:29:18.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540597,541056,TO_TIMESTAMP('2017-08-30 15:29:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-08-30 15:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:29:26.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554963,0,540632,541056,547908,TO_TIMESTAMP('2017-08-30 15:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-08-30 15:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:29:35.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554960,0,540632,541056,547909,TO_TIMESTAMP('2017-08-30 15:29:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-08-30 15:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:30:55.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=555016,Updated=TO_TIMESTAMP('2017-08-30 15:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547902
;

-- 2017-08-30T15:31:44.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547854
;

-- 2017-08-30T15:31:44.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547855
;

-- 2017-08-30T15:31:44.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547856
;

-- 2017-08-30T15:31:44.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547857
;

-- 2017-08-30T15:31:44.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547859
;

-- 2017-08-30T15:31:59.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547860
;

-- 2017-08-30T15:31:59.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547861
;

-- 2017-08-30T15:31:59.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547863
;

-- 2017-08-30T15:32:11.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547865
;

-- 2017-08-30T15:32:11.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547866
;

-- 2017-08-30T15:32:11.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547867
;

-- 2017-08-30T15:32:31.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547868
;

-- 2017-08-30T15:32:31.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547870
;

-- 2017-08-30T15:32:46.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547869
;

-- 2017-08-30T15:33:43.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555017,0,540632,541050,547910,TO_TIMESTAMP('2017-08-30 15:33:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',190,0,0,TO_TIMESTAMP('2017-08-30 15:33:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:34:05.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anzahl Verarbeitet',Updated=TO_TIMESTAMP('2017-08-30 15:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547910
;

-- 2017-08-30T15:34:25.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anzahl eingereiht',Updated=TO_TIMESTAMP('2017-08-30 15:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547864
;

-- 2017-08-30T15:34:29.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anzahl verarbeitet',Updated=TO_TIMESTAMP('2017-08-30 15:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547910
;

-- 2017-08-30T15:34:36.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anzahl erwartet',Updated=TO_TIMESTAMP('2017-08-30 15:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547862
;

-- 2017-08-30T15:35:02.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547872
;

-- 2017-08-30T15:35:31.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556938,0,540632,541055,547911,TO_TIMESTAMP('2017-08-30 15:35:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Eltern Nr.',60,0,0,TO_TIMESTAMP('2017-08-30 15:35:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:35:40.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-08-30 15:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547911
;

-- 2017-08-30T15:35:43.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-08-30 15:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547906
;

-- 2017-08-30T15:35:46.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-08-30 15:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547907
;

-- 2017-08-30T15:37:35.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547871
;

-- 2017-08-30T15:38:49.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547911
;

-- 2017-08-30T15:40:28.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540597,541057,TO_TIMESTAMP('2017-08-30 15:40:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','counter',20,TO_TIMESTAMP('2017-08-30 15:40:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:40:31.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-30 15:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541056
;

-- 2017-08-30T15:40:46.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556645,0,540632,541057,547912,TO_TIMESTAMP('2017-08-30 15:40:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl erwartet',10,0,0,TO_TIMESTAMP('2017-08-30 15:40:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:40:56.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555013,0,540632,541057,547913,TO_TIMESTAMP('2017-08-30 15:40:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl eingereiht',20,0,0,TO_TIMESTAMP('2017-08-30 15:40:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:41:11.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555017,0,540632,541057,547914,TO_TIMESTAMP('2017-08-30 15:41:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl verarbeitet',30,0,0,TO_TIMESTAMP('2017-08-30 15:41:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:41:30.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554957,0,540632,541053,547915,TO_TIMESTAMP('2017-08-30 15:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',50,0,0,TO_TIMESTAMP('2017-08-30 15:41:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-30T15:41:44.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547858
;

-- 2017-08-30T15:41:44.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547862
;

-- 2017-08-30T15:41:44.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547864
;

-- 2017-08-30T15:41:44.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547910
;

-- 2017-08-30T15:41:50.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547852
;

-- 2017-08-30T15:41:50.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547853
;

-- 2017-08-30T15:41:54.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541050
;

-- 2017-08-30T15:42:54.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541055, SeqNo=70,Updated=TO_TIMESTAMP('2017-08-30 15:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547912
;

-- 2017-08-30T15:43:08.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541055, SeqNo=80,Updated=TO_TIMESTAMP('2017-08-30 15:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547913
;

-- 2017-08-30T15:43:16.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541055, SeqNo=90,Updated=TO_TIMESTAMP('2017-08-30 15:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547914
;

-- 2017-08-30T15:43:21.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541057
;

-- 2017-08-30T15:43:33.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-08-30 15:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541056
;

-- 2017-08-30T15:45:27.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547895
;

-- 2017-08-30T15:45:27.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547905
;

-- 2017-08-30T15:45:27.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547897
;

-- 2017-08-30T15:45:27.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547898
;

-- 2017-08-30T15:45:27.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547896
;

-- 2017-08-30T15:45:27.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547915
;

-- 2017-08-30T15:45:27.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547907
;

-- 2017-08-30T15:45:27.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547906
;

-- 2017-08-30T15:45:27.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547903
;

-- 2017-08-30T15:45:27.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547904
;

-- 2017-08-30T15:45:27.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547913
;

-- 2017-08-30T15:45:27.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547912
;

-- 2017-08-30T15:45:27.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547914
;

-- 2017-08-30T15:45:27.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547900
;

-- 2017-08-30T15:45:27.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547901
;

-- 2017-08-30T15:45:27.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547899
;

-- 2017-08-30T15:45:27.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547902
;

-- 2017-08-30T15:45:27.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2017-08-30 15:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547908
;

-- 2017-08-30T15:45:41.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-08-30 15:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547895
;

-- 2017-08-30T15:45:41.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-08-30 15:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547905
;

-- 2017-08-30T15:45:41.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-08-30 15:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547897
;

-- 2017-08-30T15:45:41.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-08-30 15:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547903
;

-- 2017-08-30T15:46:27.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-30 15:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556645
;

-- 2017-08-30T15:46:29.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-30 15:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556696
;

-- 2017-08-30T15:46:30.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-30 15:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555043
;

-- 2017-08-30T15:46:46.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-08-30 15:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556938
;

-- 2017-08-30T15:46:58.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nr.',Updated=TO_TIMESTAMP('2017-08-30 15:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554956
;

-- 2017-08-30T15:47:05.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl erwartet',Updated=TO_TIMESTAMP('2017-08-30 15:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556645
;

-- 2017-08-30T15:47:18.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl eingereiht',Updated=TO_TIMESTAMP('2017-08-30 15:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555013
;

-- 2017-08-30T15:47:25.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl verarbeitet',Updated=TO_TIMESTAMP('2017-08-30 15:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555017
;

-- 2017-08-30T15:47:33.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuerst eingereiht',Updated=TO_TIMESTAMP('2017-08-30 15:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555014
;

-- 2017-08-30T15:47:42.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuletzt eingereicht',Updated=TO_TIMESTAMP('2017-08-30 15:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555015
;

-- 2017-08-30T15:47:52.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuletzt verarbeitet',Updated=TO_TIMESTAMP('2017-08-30 15:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555016
;

-- 2017-08-30T15:47:55.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuletzt eingereiht',Updated=TO_TIMESTAMP('2017-08-30 15:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555015
;

-- 2017-08-30T15:48:08.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Async Stapel Art',Updated=TO_TIMESTAMP('2017-08-30 15:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555043
;

-- 2017-08-30T15:48:19.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Eltern Stapel Nr.',Updated=TO_TIMESTAMP('2017-08-30 15:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556938
;

-- 2017-08-30T15:48:35.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuletzt verabeitetes Arbeitspaket',Updated=TO_TIMESTAMP('2017-08-30 15:48:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556696
;

-- 2017-08-30T15:48:44.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prozess Instanz',Updated=TO_TIMESTAMP('2017-08-30 15:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554962
;

-- 2017-08-30T15:49:23.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2017-08-30 15:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554956
;

-- 2017-08-30T16:14:04.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547876
;

-- 2017-08-30T16:14:04.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547879
;

-- 2017-08-30T16:14:04.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547884
;

-- 2017-08-30T16:14:04.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547883
;

-- 2017-08-30T16:14:04.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547882
;

-- 2017-08-30T16:14:04.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547873
;

-- 2017-08-30T16:14:04.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547874
;

-- 2017-08-30T16:14:04.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547875
;

-- 2017-08-30T16:14:04.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547880
;

-- 2017-08-30T16:14:04.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-08-30 16:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547881
;

-- 2017-08-30T16:14:38.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sequenz',Updated=TO_TIMESTAMP('2017-08-30 16:14:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556715
;

-- 2017-08-30T16:15:05.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Async Stapel',Updated=TO_TIMESTAMP('2017-08-30 16:15:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556706
;

-- 2017-08-30T16:15:28.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitpaket informiert',Updated=TO_TIMESTAMP('2017-08-30 16:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556713
;

-- 2017-08-30T16:15:39.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Informiert',Updated=TO_TIMESTAMP('2017-08-30 16:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556710
;

-- 2017-08-30T16:16:22.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-08-30 16:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547884
;

-- 2017-08-30T16:16:24.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-08-30 16:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547883
;

-- 2017-08-30T16:16:26.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-30 16:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547882
;

-- 2017-08-30T16:16:28.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-08-30 16:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547873
;

-- 2017-08-30T16:16:29.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-08-30 16:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547877
;

-- 2017-08-30T16:16:31.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-08-30 16:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547878
;

-- 2017-08-30T16:16:33.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-08-30 16:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547874
;

-- 2017-08-30T16:16:34.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-08-30 16:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547875
;

-- 2017-08-30T16:16:37.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-08-30 16:16:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547880
;

-- 2017-08-30T16:16:39.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-08-30 16:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547881
;

-- 2017-08-30T16:16:43.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-08-30 16:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547879
;

-- 2017-08-30T16:16:44.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547884
;

-- 2017-08-30T16:16:44.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547883
;

-- 2017-08-30T16:16:44.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547882
;

-- 2017-08-30T16:16:45.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547873
;

-- 2017-08-30T16:16:45.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547877
;

-- 2017-08-30T16:16:46.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547878
;

-- 2017-08-30T16:16:46.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547874
;

-- 2017-08-30T16:16:46.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547875
;

-- 2017-08-30T16:16:47.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547880
;

-- 2017-08-30T16:16:49.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547881
;

-- 2017-08-30T16:17:05.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Eingereiht',Updated=TO_TIMESTAMP('2017-08-30 16:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555006
;

-- 2017-08-30T16:17:14.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Async Stapel',Updated=TO_TIMESTAMP('2017-08-30 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555004
;

-- 2017-08-30T16:17:21.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DB Tabelle',Updated=TO_TIMESTAMP('2017-08-30 16:17:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555005
;

-- 2017-08-30T16:18:20.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547892
;

-- 2017-08-30T16:18:20.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547887
;

-- 2017-08-30T16:18:20.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547894
;

-- 2017-08-30T16:18:20.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547888
;

-- 2017-08-30T16:18:20.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547889
;

-- 2017-08-30T16:18:20.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547893
;

-- 2017-08-30T16:18:20.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547885
;

-- 2017-08-30T16:18:20.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-08-30 16:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547886
;

-- 2017-08-30T16:19:31.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Async Stapel',Updated=TO_TIMESTAMP('2017-08-30 16:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547887
;

-- 2017-08-30T16:19:35.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='DB Tabelle',Updated=TO_TIMESTAMP('2017-08-30 16:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547888
;

-- 2017-08-30T16:19:43.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Eingereiht',Updated=TO_TIMESTAMP('2017-08-30 16:19:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547889
;

-- 2017-08-30T16:19:54.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Arbeitspaket Prozessor',Updated=TO_TIMESTAMP('2017-08-30 16:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547894
;

-- 2017-08-30T16:20:08.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-08-30 16:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547887
;

-- 2017-08-30T16:20:11.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-08-30 16:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547894
;

-- 2017-08-30T16:20:13.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-30 16:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547888
;

-- 2017-08-30T16:20:15.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-08-30 16:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547889
;

-- 2017-08-30T16:20:17.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-08-30 16:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547893
;

-- 2017-08-30T16:20:18.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-08-30 16:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547890
;

-- 2017-08-30T16:20:20.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-08-30 16:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547891
;

-- 2017-08-30T16:20:22.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-08-30 16:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547885
;

-- 2017-08-30T16:20:27.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-08-30 16:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547886
;

-- 2017-08-30T16:20:33.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-08-30 16:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547887
;

-- 2017-08-30T16:35:37.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Statistiken',Updated=TO_TIMESTAMP('2017-08-30 16:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540636
;

-- 2017-08-30T16:35:43.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Parameter',Updated=TO_TIMESTAMP('2017-08-30 16:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540633
;

-- 2017-08-30T16:35:58.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Arbeitspaket informiert',Updated=TO_TIMESTAMP('2017-08-30 16:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540728
;

-- 2017-08-30T16:36:28.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitspaket Prozessor',Updated=TO_TIMESTAMP('2017-08-30 16:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555011
;

-- 2017-08-30T16:37:15.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitspaket Verarbeitungswarteschlange',Updated=TO_TIMESTAMP('2017-08-30 16:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556714
;

-- 2017-08-30T16:40:45.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-30 16:40:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=554957 AND AD_Language='en_US'
;

-- 2017-08-30T16:41:06.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-30 16:41:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Process Instance',Description='Process Instance' WHERE AD_Field_ID=554962 AND AD_Language='en_US'
;

-- 2017-08-30T16:41:42.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-30 16:41:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Parent Async Batch No.' WHERE AD_Field_ID=556938 AND AD_Language='en_US'
;

-- 2017-08-30T16:42:15.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-30 16:42:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Processed',Description='',Help='' WHERE AD_Field_ID=555018 AND AD_Language='en_US'
;

-- 2017-08-30T16:43:08.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-30 16:43:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Amount Processed' WHERE AD_Field_ID=555017 AND AD_Language='en_US'
;

-- 2017-08-30T16:43:42.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-30 16:43:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='SeqNo' WHERE AD_Field_ID=556715 AND AD_Language='en_US'
;

