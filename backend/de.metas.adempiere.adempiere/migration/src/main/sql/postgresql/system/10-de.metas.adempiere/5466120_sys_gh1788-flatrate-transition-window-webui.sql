-- 2017-06-24T18:36:38.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540353,540323,TO_TIMESTAMP('2017-06-24 18:36:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 18:36:38','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-24T18:36:38.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540323 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-24T18:36:38.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540442,540323,TO_TIMESTAMP('2017-06-24 18:36:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 18:36:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540443,540323,TO_TIMESTAMP('2017-06-24 18:36:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-24 18:36:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540442,540765,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548483,0,540353,540765,546131,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,10,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548481,0,540353,540765,546132,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',20,20,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548471,0,540353,540765,546133,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',30,30,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548486,0,540353,540765,546134,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Vertragslaufzeit',40,40,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548474,0,540353,540765,546135,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Einheit',50,50,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550451,0,540353,540765,546136,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','Y','Y','N','Abrechnungs-/Lieferkalender',60,60,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551442,0,540353,540765,546137,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Endet mit Kalenderjahr',70,70,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550521,0,540353,540765,546138,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferintervall',80,80,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550522,0,540353,540765,546139,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Einheit',90,90,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548477,0,540353,540765,546140,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.','Y','N','Y','Y','N','Ablauffrist',100,100,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548473,0,540353,540765,546141,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Einheit',110,110,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548472,0,540353,540765,546142,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Betreuer Informieren',120,120,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548478,0,540353,540765,546143,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert','Y','N','Y','Y','N','Vertrag autom. verlängern',130,130,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550450,0,540353,540765,546144,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind','Angabe ist optional.','Y','N','Y','Y','N','Nächste Vertragsbedingungen',140,140,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548482,0,540353,540765,546145,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.','Wenn die neue Vertragperiode nicht automatisch fertig gestellt wird, hat der Betreuer noch die Möglichkeit, z.B. die geplante Mege pro Maßeinheit anzupassen.','Y','N','Y','Y','N','Neue Vertragslaufzeit autom. Fertigstellen',150,150,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548469,0,540353,540765,546146,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','Y','N','Belegstatus',160,160,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548470,0,540353,540765,546147,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs','You find the current status in the Document Status field. The options are listed in a popup','Y','N','Y','Y','N','Belegverarbeitung',170,170,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548485,0,540353,540765,546148,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',180,180,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540422,540324,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-24T18:36:39.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540324 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-24T18:36:39.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540444,540324,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540444,540766,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550535,0,540422,540766,546149,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,10,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550546,0,540422,540766,546150,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertragsbedingungen',0,20,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550536,0,540422,540766,546151,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Zeitpunk vor dem Vertragsende, bis zu dem die Änderungskondition gültig ist','Y','N','N','Y','N','Gültigkeitsfrist',0,30,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:39.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550537,0,540422,540766,546152,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Einheit der Frist',0,40,0,TO_TIMESTAMP('2017-06-24 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:40.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550531,0,540422,540766,546153,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt die durchzuführende Aktion an','"Aktion" ist ein Auswahlfeld, das die für diesen Vorgang durchzuführende Aktion anzeigt.','Y','N','N','Y','N','Aktion',0,50,0,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:40.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550547,0,540422,540766,546154,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertrags-Status',0,60,0,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:40.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550542,0,540422,540766,546155,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind','Angabe ist optional.','Y','N','N','Y','N','Nächste Vertragsbedingungen',0,70,0,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:40.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550544,0,540422,540766,546156,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,80,0,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:36:40.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550543,0,540422,540766,546157,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','Preissystem',0,90,0,TO_TIMESTAMP('2017-06-24 18:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:40:40.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540442,540767,TO_TIMESTAMP('2017-06-24 18:40:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-24 18:40:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:40:56.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90,Updated=TO_TIMESTAMP('2017-06-24 18:40:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540765
;

-- 2017-06-24T18:42:40.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,548481,0,540353,540767,546158,TO_TIMESTAMP('2017-06-24 18:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-06-24 18:42:40','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2017-06-24T18:43:03.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550451,0,540353,540767,546159,TO_TIMESTAMP('2017-06-24 18:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abrechnungs-Kalender',20,0,0,TO_TIMESTAMP('2017-06-24 18:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:43:20.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548486,0,540353,540767,546160,TO_TIMESTAMP('2017-06-24 18:43:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vertragslaufzeit',30,0,0,TO_TIMESTAMP('2017-06-24 18:43:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:43:55.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548474,0,540353,540767,546161,TO_TIMESTAMP('2017-06-24 18:43:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vertrag Einheit',40,0,0,TO_TIMESTAMP('2017-06-24 18:43:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:44:23.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548477,0,540353,540767,546162,TO_TIMESTAMP('2017-06-24 18:44:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ablauffrist',50,0,0,TO_TIMESTAMP('2017-06-24 18:44:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:44:53.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548473,0,540353,540767,546163,TO_TIMESTAMP('2017-06-24 18:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ablauf Einheit',60,0,0,TO_TIMESTAMP('2017-06-24 18:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:45:05.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540442,540768,TO_TIMESTAMP('2017-06-24 18:45:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','lieferung',20,TO_TIMESTAMP('2017-06-24 18:45:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:45:21.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550521,0,540353,540768,546164,TO_TIMESTAMP('2017-06-24 18:45:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferinterval',10,0,0,TO_TIMESTAMP('2017-06-24 18:45:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:45:40.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550522,0,540353,540768,546165,TO_TIMESTAMP('2017-06-24 18:45:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferinterval Einheit',20,0,0,TO_TIMESTAMP('2017-06-24 18:45:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:45:51.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548471,0,540353,540768,546166,TO_TIMESTAMP('2017-06-24 18:45:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',30,0,0,TO_TIMESTAMP('2017-06-24 18:45:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:47:02.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540443,540769,TO_TIMESTAMP('2017-06-24 18:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-06-24 18:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:47:24.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551442,0,540353,540769,546167,TO_TIMESTAMP('2017-06-24 18:47:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Endet mit Kalenderjahr',10,0,0,TO_TIMESTAMP('2017-06-24 18:47:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:47:49.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548472,0,540353,540769,546168,TO_TIMESTAMP('2017-06-24 18:47:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Betreuer informieren',20,0,0,TO_TIMESTAMP('2017-06-24 18:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:48:26.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548478,0,540353,540769,546169,TO_TIMESTAMP('2017-06-24 18:48:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vertrag autom. verlängern',30,0,0,TO_TIMESTAMP('2017-06-24 18:48:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:48:57.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548482,0,540353,540769,546170,TO_TIMESTAMP('2017-06-24 18:48:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vertragsverlängerung fertigstellen',40,0,0,TO_TIMESTAMP('2017-06-24 18:48:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:49:55.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540443,540770,TO_TIMESTAMP('2017-06-24 18:49:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-06-24 18:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:50:03.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548483,0,540353,540770,546171,TO_TIMESTAMP('2017-06-24 18:50:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-06-24 18:50:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:50:12.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548479,0,540353,540770,546172,TO_TIMESTAMP('2017-06-24 18:50:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-06-24 18:50:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:51:23.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546164
;

-- 2017-06-24T18:51:23.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546165
;

-- 2017-06-24T18:51:23.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546166
;

-- 2017-06-24T18:51:27.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540768
;

-- 2017-06-24T18:52:05.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546131
;

-- 2017-06-24T18:52:05.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546132
;

-- 2017-06-24T18:52:05.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546134
;

-- 2017-06-24T18:52:05.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546136
;

-- 2017-06-24T18:52:05.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546137
;

-- 2017-06-24T18:52:05.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546142
;

-- 2017-06-24T18:52:37.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546146
;

-- 2017-06-24T18:52:37.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546147
;

-- 2017-06-24T18:52:37.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546148
;

-- 2017-06-24T18:52:43.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546143
;

-- 2017-06-24T18:53:02.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546135
;

-- 2017-06-24T18:53:09.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546140
;

-- 2017-06-24T18:53:12.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546141
;

-- 2017-06-24T18:53:36.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-24 18:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546133
;

-- 2017-06-24T18:53:37.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-24 18:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546138
;

-- 2017-06-24T18:53:37.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-24 18:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546139
;

-- 2017-06-24T18:53:37.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-24 18:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546144
;

-- 2017-06-24T18:53:39.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-24 18:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546145
;

-- 2017-06-24T18:53:58.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540353,540325,TO_TIMESTAMP('2017-06-24 18:53:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-24 18:53:58','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-06-24T18:53:58.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540325 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-24T18:54:01.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540445,540325,TO_TIMESTAMP('2017-06-24 18:54:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-24 18:54:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:54:24.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540445,540771,TO_TIMESTAMP('2017-06-24 18:54:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-06-24 18:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-24T18:54:40.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540445, SeqNo=20,Updated=TO_TIMESTAMP('2017-06-24 18:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540765
;

-- 2017-06-24T19:00:18.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546133
;

-- 2017-06-24T19:00:18.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546138
;

-- 2017-06-24T19:00:18.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546145
;

-- 2017-06-24T19:00:18.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546144
;

-- 2017-06-24T19:00:18.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546158
;

-- 2017-06-24T19:00:18.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546159
;

-- 2017-06-24T19:00:18.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546160
;

-- 2017-06-24T19:00:18.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546161
;

-- 2017-06-24T19:00:18.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546162
;

-- 2017-06-24T19:00:18.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546163
;

-- 2017-06-24T19:00:18.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546139
;

-- 2017-06-24T19:00:18.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546167
;

-- 2017-06-24T19:00:18.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546168
;

-- 2017-06-24T19:00:18.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546169
;

-- 2017-06-24T19:00:18.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546171
;

-- 2017-06-24T19:00:18.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-06-24 19:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546172
;

-- 2017-06-24T19:00:38.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-06-24 19:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546158
;

-- 2017-06-24T19:00:38.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-06-24 19:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546159
;

-- 2017-06-24T19:00:38.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-06-24 19:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546160
;

-- 2017-06-24T19:00:38.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-06-24 19:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546161
;

-- 2017-06-24T19:06:59.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-24 19:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546149
;

-- 2017-06-24T19:06:59.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-06-24 19:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546155
;

-- 2017-06-24T19:06:59.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-24 19:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546156
;

-- 2017-06-24T19:06:59.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-06-24 19:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546157
;

-- 2017-06-24T19:07:06.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-06-24 19:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546155
;

-- 2017-06-24T19:07:21.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-06-24 19:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546150
;

-- 2017-06-24T19:07:25.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-06-24 19:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546151
;

-- 2017-06-24T19:07:30.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-06-24 19:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546152
;

