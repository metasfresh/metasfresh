-- 2018-05-16T14:49:35.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bestell Schema',Updated=TO_TIMESTAMP('2018-05-16 14:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541103
;

-- 2018-05-16T14:49:46.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541103,540746,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-05-16T14:49:46.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540746 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-05-16T14:49:46.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540974,540746,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540974,541604,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564121,0,541103,541604,551998,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','Gültig ab',0,10,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564118,0,541103,541604,551999,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Häufigkeitsart für Ereignisse','Die "Häufigkeitsart" wird zur Berechnung des Termins für das nächste Ereignis verwendet.','Y','N','N','Y','N','Häufigkeitsart',0,20,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564119,0,541103,541604,552000,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Häufigkeit von Ereignissen','The frequency is used in conjunction with the frequency type in determining an event. Example: If the Frequency Type is Week and the Frequency is 2 - it is every two weeks.','Y','N','N','Y','N','Häufigkeit',0,30,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564103,0,541103,541604,552001,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Day of the month 1 to 28/29/30/31','Y','N','N','Y','N','Day of the Month',0,40,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564123,0,541103,541604,552002,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Wiedervorlage (min)',0,50,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564120,0,541103,541604,552003,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564105,0,541103,541604,552004,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Montags verfügbar','Y','N','N','Y','N','Montag',0,70,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564111,0,541103,541604,552005,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for monday','Y','N','N','Y','N','Bereitstellungszeit Mo',0,80,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564109,0,541103,541604,552006,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Dienstags verfügbar','Y','N','N','Y','N','Dienstag',0,90,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564112,0,541103,541604,552007,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for tuesday','Y','N','N','Y','N','Bereitstellungszeit Di',0,100,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564110,0,541103,541604,552008,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Mittwochs verfügbar','Y','N','N','Y','N','Mittwoch',0,110,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564113,0,541103,541604,552009,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for wednesday','Y','N','N','Y','N','Bereitstellungszeit Mi',0,120,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564108,0,541103,541604,552010,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Donnerstags verfügbar','Y','N','N','Y','N','Donnerstag',0,130,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564114,0,541103,541604,552011,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for thursday','Y','N','N','Y','N','Bereitstellungszeit Do',0,140,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564104,0,541103,541604,552012,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Freitags verfügbar','Y','N','N','Y','N','Freitag',0,150,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564115,0,541103,541604,552013,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Friday','Y','N','N','Y','N','Bereitstellungszeit Fr',0,160,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564106,0,541103,541604,552014,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Samstags verfügbar','Y','N','N','Y','N','Samstag',0,170,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564116,0,541103,541604,552015,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Saturday','Y','N','N','Y','N','Bereitstellungszeit Sa',0,180,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564107,0,541103,541604,552016,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Sonntags verfügbar','Y','N','N','Y','N','Sonntag',0,190,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:49:46.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564117,0,541103,541604,552017,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Sunday','Y','N','N','Y','N','Bereitstellungszeit So',0,200,0,TO_TIMESTAMP('2018-05-16 14:49:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:52:16.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564100,0,541103,541604,552018,'F',TO_TIMESTAMP('2018-05-16 14:52:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2018-05-16 14:52:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:52:27.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564099,0,541103,541604,552019,'F',TO_TIMESTAMP('2018-05-16 14:52:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2018-05-16 14:52:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T14:52:30.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2018-05-16 14:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552019
;

-- 2018-05-16T14:52:34.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-05-16 14:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552018
;

-- 2018-05-16T14:52:43.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2018-05-16 14:52:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552019
;

-- 2018-05-16T14:53:12.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552007
;

-- 2018-05-16T14:53:12.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552011
;

-- 2018-05-16T14:53:12.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552013
;

-- 2018-05-16T14:53:12.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552009
;

-- 2018-05-16T14:53:12.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552005
;

-- 2018-05-16T14:53:12.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552015
;

-- 2018-05-16T14:53:12.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552017
;

-- 2018-05-16T14:53:12.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552006
;

-- 2018-05-16T14:53:12.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552008
;

-- 2018-05-16T14:53:12.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552010
;

-- 2018-05-16T14:53:12.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552012
;

-- 2018-05-16T14:53:12.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552014
;

-- 2018-05-16T14:53:12.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552016
;

-- 2018-05-16T14:53:12.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2018-05-16 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552018
;

-- 2018-05-16T15:00:10.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-05-16 15:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551998
;

-- 2018-05-16T15:00:12.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-05-16 15:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551999
;

-- 2018-05-16T15:25:28.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-05-16 15:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552000
;

-- 2018-05-16T15:25:30.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-05-16 15:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552001
;

-- 2018-05-16T15:25:36.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-05-16 15:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552002
;

-- 2018-05-16T15:25:41.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-05-16 15:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552003
;

-- 2018-05-16T15:26:00.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-05-16 15:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552016
;

-- 2018-05-16T15:26:04.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-05-16 15:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552017
;

-- 2018-05-16T15:26:19.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2018-05-16 15:26:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552016
;

-- 2018-05-16T15:26:21.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2018-05-16 15:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552017
;

-- 2018-05-16T15:26:30.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-05-16 15:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552004
;

-- 2018-05-16T15:26:35.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-05-16 15:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552005
;

-- 2018-05-16T15:26:41.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-05-16 15:26:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552006
;

-- 2018-05-16T15:26:46.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2018-05-16 15:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552007
;

-- 2018-05-16T15:26:53.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2018-05-16 15:26:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552008
;

-- 2018-05-16T15:26:58.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2018-05-16 15:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552009
;

-- 2018-05-16T15:27:44.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2018-05-16 15:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552010
;

-- 2018-05-16T15:27:49.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2018-05-16 15:27:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552011
;

-- 2018-05-16T15:27:56.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2018-05-16 15:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552012
;

-- 2018-05-16T15:28:00.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2018-05-16 15:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552013
;

-- 2018-05-16T15:28:11.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2018-05-16 15:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552014
;

-- 2018-05-16T15:28:30.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2018-05-16 15:28:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552015
;

-- 2018-05-16T15:28:37.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2018-05-16 15:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552016
;

-- 2018-05-16T15:28:42.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2018-05-16 15:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552017
;

-- 2018-05-16T15:28:45.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2018-05-16 15:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552018
;

-- 2018-05-16T15:28:48.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2018-05-16 15:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552019
;

-- 2018-05-16T15:29:32.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552017
;

-- 2018-05-16T15:29:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552015
;

-- 2018-05-16T15:29:33.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552013
;

-- 2018-05-16T15:29:34.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552011
;

-- 2018-05-16T15:29:34.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552009
;

-- 2018-05-16T15:29:35.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552007
;

-- 2018-05-16T15:29:35.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552005
;

-- 2018-05-16T15:29:36.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552019
;

-- 2018-05-16T15:29:36.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551998
;

-- 2018-05-16T15:29:37.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551999
;

-- 2018-05-16T15:29:37.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552000
;

-- 2018-05-16T15:29:38.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552001
;

-- 2018-05-16T15:29:38.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552002
;

-- 2018-05-16T15:29:39.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552003
;

-- 2018-05-16T15:29:40.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552004
;

-- 2018-05-16T15:29:41.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552006
;

-- 2018-05-16T15:29:41.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552008
;

-- 2018-05-16T15:29:42.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552010
;

-- 2018-05-16T15:29:42.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552012
;

-- 2018-05-16T15:29:43.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552014
;

-- 2018-05-16T15:29:43.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552016
;

-- 2018-05-16T15:29:45.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-05-16 15:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552018
;

-- 2018-05-16T15:32:01.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:32:01','YYYY-MM-DD HH24:MI:SS') WHERE AD_Tab_ID=541103 AND AD_Language='de_CH'
;

-- 2018-05-16T15:32:09.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:32:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Tab_ID=541103 AND AD_Language='en_US'
;

-- 2018-05-16T15:32:51.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:32:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Valid From',Description='',Help='' WHERE AD_Field_ID=564121 AND AD_Language='en_US'
;

-- 2018-05-16T15:33:06.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:33:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Frequency Type',Description='',Help='' WHERE AD_Field_ID=564118 AND AD_Language='en_US'
;

-- 2018-05-16T15:33:19.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:33:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Frequency',Description='',Help='' WHERE AD_Field_ID=564119 AND AD_Language='en_US'
;

-- 2018-05-16T15:33:27.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:33:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=564103 AND AD_Language='en_US'
;

-- 2018-05-16T15:33:39.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:33:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reminder Time in' WHERE AD_Field_ID=564123 AND AD_Language='en_US'
;

-- 2018-05-16T15:33:47.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:33:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=564120 AND AD_Language='en_US'
;

-- 2018-05-16T15:33:56.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:33:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Monday',Description='' WHERE AD_Field_ID=564105 AND AD_Language='en_US'
;

-- 2018-05-16T15:34:16.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:34:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Monday Preparation Time' WHERE AD_Field_ID=564111 AND AD_Language='en_US'
;

-- 2018-05-16T15:34:25.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:34:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tuesday',Description='' WHERE AD_Field_ID=564109 AND AD_Language='en_US'
;

-- 2018-05-16T15:34:35.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:34:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Tuesday Preparation Time' WHERE AD_Field_ID=564112 AND AD_Language='en_US'
;

-- 2018-05-16T15:34:45.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:34:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Wednesday',Description='' WHERE AD_Field_ID=564110 AND AD_Language='en_US'
;

-- 2018-05-16T15:35:00.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:35:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Wednesday Preparation Time' WHERE AD_Field_ID=564113 AND AD_Language='en_US'
;

-- 2018-05-16T15:35:15.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:35:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Thursday',Description='' WHERE AD_Field_ID=564108 AND AD_Language='en_US'
;

-- 2018-05-16T15:35:27.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:35:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Thursday Preparation Time' WHERE AD_Field_ID=564114 AND AD_Language='en_US'
;

-- 2018-05-16T15:35:36.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:35:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Friday',Description='' WHERE AD_Field_ID=564104 AND AD_Language='en_US'
;

-- 2018-05-16T15:35:47.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:35:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Friday Preparation Time' WHERE AD_Field_ID=564115 AND AD_Language='en_US'
;

-- 2018-05-16T15:35:57.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:35:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Saturday',Description='' WHERE AD_Field_ID=564106 AND AD_Language='en_US'
;

-- 2018-05-16T15:36:13.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:36:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Saturday Preparation Time' WHERE AD_Field_ID=564116 AND AD_Language='en_US'
;

-- 2018-05-16T15:36:22.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:36:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sunday',Description='' WHERE AD_Field_ID=564107 AND AD_Language='en_US'
;

-- 2018-05-16T15:36:34.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:36:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sunday Preparation Time' WHERE AD_Field_ID=564117 AND AD_Language='en_US'
;

-- 2018-05-16T15:36:44.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:36:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=564099 AND AD_Language='en_US'
;

-- 2018-05-16T15:36:54.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:36:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=564101 AND AD_Language='en_US'
;

-- 2018-05-16T15:37:05.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:37:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Business Partner',Description='',Help='' WHERE AD_Field_ID=564122 AND AD_Language='en_US'
;

-- 2018-05-16T15:37:17.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-16 15:37:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=564100 AND AD_Language='en_US'
;

-- 2018-05-16T15:39:54.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tag des Monats',Updated=TO_TIMESTAMP('2018-05-16 15:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564103
;

-- 2018-05-16T15:40:13.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-05-16 15:40:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552018
;

-- 2018-05-16T15:40:17.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-05-16 15:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552019
;

-- 2018-05-16T15:40:23.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2018-05-16 15:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551998
;

-- 2018-05-16T15:40:27.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2018-05-16 15:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552000
;

-- 2018-05-16T15:40:32.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-05-16 15:40:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551999
;

