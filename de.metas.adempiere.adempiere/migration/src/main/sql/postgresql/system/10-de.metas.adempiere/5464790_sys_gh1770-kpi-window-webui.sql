-- 2017-06-10T11:31:12.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540788,540279,TO_TIMESTAMP('2017-06-10 11:31:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-10 11:31:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-10T11:31:12.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540279 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-10T11:31:12.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540380,540279,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540381,540279,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540380,540650,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557844,0,540788,540650,545616,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',10,10,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557846,0,540788,540650,545617,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',20,20,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557845,0,540788,540650,545618,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Type fo chart to render','Y','N','Y','Y','N','Chart Type',30,30,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557867,0,540788,540650,545619,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Compare',40,40,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557868,0,540788,540650,545620,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Offset',50,50,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557859,0,540788,540650,545621,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Time range using format ''PnDTnHnMn.nS''','See https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-','Y','N','Y','Y','N','Time range',60,60,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557970,0,540788,540650,545622,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Time range''s ending offset (relative to now)','Time range using format ''PnDTnHnMn.nS''
See https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-','Y','N','Y','Y','N','Time range end',70,70,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557863,0,540788,540650,545623,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Polling interval (sec)',80,80,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557858,0,540788,540650,545624,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Elasticsearch Index',90,90,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557857,0,540788,540650,545625,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Elasticsearch Type',100,100,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557847,0,540788,540650,545626,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Elasticsearch query',110,110,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540789,540280,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-10T11:31:12.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540280 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-10T11:31:12.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540382,540280,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540382,540651,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557850,0,540789,540651,545627,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,10,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557860,0,540789,540651,545628,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,20,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557852,0,540789,540651,545629,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.','Das "System-Element" ermöglicht die zentrale  Verwaltung von Hilfe, Beschreibung und Terminologie für eine Tabellenspalte.','Y','N','N','Y','N','System-Element',0,30,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557856,0,540789,540651,545630,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Systemreferenz und Validierung','Die Referenz kann über den Datentyp, eine Liste oder eine Tabelle erfolgen.','Y','N','N','Y','N','Referenz',0,40,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:12.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557869,0,540789,540651,545631,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'After a group change, totals, etc. are printed','Grouping allows to print sub-totals. If a group changes, the totals are printed.  Group by columns need to be included in the sort order.','Y','N','N','Y','N','Gruppieren',0,50,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:13.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557855,0,540789,540651,545632,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Elasticsearch field path',0,60,0,TO_TIMESTAMP('2017-06-10 11:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:13.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557865,0,540789,540651,545633,TO_TIMESTAMP('2017-06-10 11:31:13','YYYY-MM-DD HH24:MI:SS'),100,'Symbol für die Maßeinheit','Symbol" bezeichnet das Symbol, das bei Verwendung dieser Maßeinheit angezeigt und gedruckt wird.','Y','N','N','Y','N','Symbol',0,70,0,TO_TIMESTAMP('2017-06-10 11:31:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:13.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557866,0,540789,540651,545634,TO_TIMESTAMP('2017-06-10 11:31:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Color',0,80,0,TO_TIMESTAMP('2017-06-10 11:31:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-10T11:31:13.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557870,0,540789,540651,545635,TO_TIMESTAMP('2017-06-10 11:31:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Offset name',0,90,0,TO_TIMESTAMP('2017-06-10 11:31:13','YYYY-MM-DD HH24:MI:SS'),100)
;

