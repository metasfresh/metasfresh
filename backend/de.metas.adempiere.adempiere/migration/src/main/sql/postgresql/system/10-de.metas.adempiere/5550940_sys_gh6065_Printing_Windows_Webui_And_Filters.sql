-- 2020-02-03T09:11:26.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2020-02-03 11:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548306
;

-- 2020-02-03T10:29:08.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2020-02-03 12:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548069
;

-- 2020-02-03T10:29:18.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsRangeFilter='Y', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2020-02-03 12:29:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548063
;

-- 2020-02-03T10:29:31.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2020-02-03 12:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548221
;

-- 2020-02-03T10:31:16.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2020-02-03 12:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548787
;

-- 2020-02-03T10:31:30.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsRangeFilter='Y',Updated=TO_TIMESTAMP('2020-02-03 12:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548250
;










-- 2020-02-03T10:38:06.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540461,541766,TO_TIMESTAMP('2020-02-03 12:38:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:38:06','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-02-03T10:38:06.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541766 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-03T10:38:07.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542248,541766,TO_TIMESTAMP('2020-02-03 12:38:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:38:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542249,541766,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542248,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551207,0,540461,565880,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Druck-Job',10,0,10,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551203,0,540461,565881,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','Y','Ansprechpartner',20,0,20,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551206,0,540461,565882,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Mandant fĂĽr diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie kĂ¶nnen keine Daten ĂĽber Mandanten hinweg verwenden. .','Y','N','Y','N','Y','Mandant',30,0,30,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551208,0,540461,565883,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kĂ¶nnen Daten ĂĽber Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','Sektion',40,0,40,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551527,0,540461,565884,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','Y','Verarbeitet',50,0,50,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551204,0,540461,565885,543432,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','Y','Erstellt',60,0,60,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:07.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540462,541767,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-02-03T10:38:07.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541767 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-03T10:38:08.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542250,541767,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542250,543433,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551216,0,540462,565886,543433,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Druck-Job Position',0,0,10,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551662,0,540462,565887,543433,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','Y','Reihenfolge',0,0,20,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551217,0,540462,565888,543433,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Druck-Warteschlangendatensatz',0,0,30,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551506,0,540462,565889,543433,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Druckpaket',0,0,40,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540486,541768,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-02-03T10:38:08.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541768 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-03T10:38:08.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542251,541768,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542251,543434,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:08.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,556528,0,540486,565890,543434,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Hardware-Drucker',0,0,10,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551659,0,540486,565891,543434,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','Y','Sektion',0,0,20,TO_TIMESTAMP('2020-02-03 12:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551661,0,540486,565892,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Ausdruck für',0,0,30,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551676,0,540486,565893,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Unique identifier of a host','Y','N','N','N','Y','Host key',0,0,40,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,556219,0,540486,565894,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu erstellenden/zu druckenden Exemplare','Y','N','N','N','Y','Kopien',0,0,50,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551657,0,540486,565895,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Print job line from',0,0,60,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551658,0,540486,565896,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Print job line to',0,0,70,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551660,0,540486,565897,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','N','Y','Status',0,0,80,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:09.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551653,0,540486,565898,543434,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Fehlermeldung',0,0,90,TO_TIMESTAMP('2020-02-03 12:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:26.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542249,543435,TO_TIMESTAMP('2020-02-03 12:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2020-02-03 12:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:30.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542249,543436,TO_TIMESTAMP('2020-02-03 12:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2020-02-03 12:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:38:55.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-02-03 12:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551200
;

-- 2020-02-03T10:39:34.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543436, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-03 12:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565883
;

-- 2020-02-03T10:39:42.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543436, SeqNo=20,Updated=TO_TIMESTAMP('2020-02-03 12:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565882
;

-- 2020-02-03T10:39:56.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2020-02-03 12:39:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565880
;

-- 2020-02-03T10:40:06.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543435, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-03 12:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565884
;

-- 2020-02-03T10:40:23.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-02-03 12:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565880
;

-- 2020-02-03T10:40:23.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-02-03 12:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565882
;

-- 2020-02-03T10:40:23.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-02-03 12:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565881
;

-- 2020-02-03T10:40:23.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-02-03 12:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565884
;

-- 2020-02-03T10:40:23.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-02-03 12:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565885
;









-- 2020-02-03T10:52:53.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540479,541769,TO_TIMESTAMP('2020-02-03 12:52:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:52:53','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-02-03T10:52:53.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541769 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-03T10:52:53.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542252,541769,TO_TIMESTAMP('2020-02-03 12:52:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:52:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542253,541769,TO_TIMESTAMP('2020-02-03 12:52:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-02-03 12:52:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542252,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551447,0,540479,565899,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','Y','Aktiv',10,0,10,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,556217,0,540479,565900,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu erstellenden/zu druckenden Exemplare','Y','N','Y','N','Y','Kopien',20,0,20,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551458,0,540479,565901,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'User Session Online or Web','Online or Web Session Information','Y','N','Y','N','Y','Nutzersitzung',30,0,30,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551459,0,540479,565902,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Transaktions-ID',40,0,40,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551455,0,540479,565903,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Seitenzahl',50,0,50,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551454,0,540479,565904,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Number of different package infos for a given print package.','Y','N','Y','N','Y','Anz. Druckpaket-Infos',60,0,60,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551769,0,540479,565905,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Druck-Job Anweisung',70,0,70,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551450,0,540479,565906,543437,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Binary format of the print package (e.g. postscript vs pdf)','Y','N','Y','N','Y','Binärformat',80,0,80,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:54.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540480,541770,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-02-03T10:52:54.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541770 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-03T10:52:55.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542254,541770,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-03 12:52:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542254,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551511,0,540480,565907,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Kalibrierung-X',0,0,10,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551512,0,540480,565908,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Kalibrierung-Y',0,0,20,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551471,0,540480,565909,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Druckpaket',0,0,30,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551461,0,540480,565910,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','Y','Aktiv',0,0,40,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551469,0,540480,565911,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Von Seite',0,0,50,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551470,0,540480,565912,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Bis Seite',0,0,60,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551466,0,540480,565913,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Hardware-Drucker',0,0,70,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:55.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551473,0,540480,565914,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Print service name',0,0,80,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:56.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551474,0,540480,565915,543438,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Hardware-Schacht',0,0,90,TO_TIMESTAMP('2020-02-03 12:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:52:56.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551468,0,540480,565916,543438,TO_TIMESTAMP('2020-02-03 12:52:56','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','N','Y','Name',0,0,100,TO_TIMESTAMP('2020-02-03 12:52:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:55:14.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542253,543439,TO_TIMESTAMP('2020-02-03 12:55:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2020-02-03 12:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:55:18.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542253,543440,TO_TIMESTAMP('2020-02-03 12:55:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2020-02-03 12:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:55:31.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543439, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-03 12:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=565899
;

-- 2020-02-03T10:55:56.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-02-03 12:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551453
;

-- 2020-02-03T10:55:58.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-02-03 12:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551457
;

-- 2020-02-03T10:56:18.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551453,0,540479,565917,543440,'F',TO_TIMESTAMP('2020-02-03 12:56:18','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2020-02-03 12:56:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:56:24.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551457,0,540479,565918,543440,'F',TO_TIMESTAMP('2020-02-03 12:56:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2020-02-03 12:56:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-03T10:57:26.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2020-02-03 12:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548259
;

-- 2020-02-03T10:57:50.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2020-02-03 12:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548259
;






