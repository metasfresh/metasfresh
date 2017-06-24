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

