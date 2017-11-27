-- 2017-11-25T12:41:04.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53054,540543,TO_TIMESTAMP('2017-11-25 12:41:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-11-25 12:41:04','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-11-25T12:41:04.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540543 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-11-25T12:41:11.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540724,540543,TO_TIMESTAMP('2017-11-25 12:41:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-11-25 12:41:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-25T12:41:27.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540724, SeqNo=10,Updated=TO_TIMESTAMP('2017-11-25 12:41:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540183
;

-- 2017-11-25T12:41:40.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-11-25 12:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540183
;

-- 2017-11-25T12:42:41.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540724, SeqNo=20,Updated=TO_TIMESTAMP('2017-11-25 12:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540949
;

-- 2017-11-25T12:42:52.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540724, SeqNo=30,Updated=TO_TIMESTAMP('2017-11-25 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540950
;

-- 2017-11-25T12:43:09.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-11-25 12:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540949
;

-- 2017-11-25T12:43:11.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-11-25 12:43:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540950
;

-- 2017-11-25T12:43:26.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-11-25 12:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540949
;

-- 2017-11-25T12:43:30.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-11-25 12:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540950
;

-- 2017-11-25T12:43:36.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-11-25 12:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540949
;

-- 2017-11-25T12:43:40.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-11-25 12:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540950
;

-- 2017-11-25T12:44:31.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegart',Updated=TO_TIMESTAMP('2017-11-25 12:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54128
;

-- 2017-11-25T12:45:13.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Startdatum Plan',Updated=TO_TIMESTAMP('2017-11-25 12:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54144
;

-- 2017-11-25T12:45:38.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Enddatum Plan',Updated=TO_TIMESTAMP('2017-11-25 12:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54143
;

-- 2017-11-25T12:45:52.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ist % Menge',Updated=TO_TIMESTAMP('2017-11-25 12:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54113
;

-- 2017-11-25T12:46:04.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Material Vorgang ID',Updated=TO_TIMESTAMP('2017-11-25 12:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556417
;

-- 2017-11-25T12:46:52.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540947, SeqNo=80,Updated=TO_TIMESTAMP('2017-11-25 12:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542351
;

-- 2017-11-25T12:47:09.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='dates',Updated=TO_TIMESTAMP('2017-11-25 12:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540947
;

-- 2017-11-25T13:09:11.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mengeneinheit',Updated=TO_TIMESTAMP('2017-11-25 13:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54150
;

-- 2017-11-25T13:12:58.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Datum',Updated=TO_TIMESTAMP('2017-11-25 13:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542357
;

-- 2017-11-25T13:13:01.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-25 13:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542351
;

-- 2017-11-25T13:13:04.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-11-25 13:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542375
;

-- 2017-11-25T13:13:06.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-11-25 13:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542358
;

-- 2017-11-25T13:13:09.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-11-25 13:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542359
;

-- 2017-11-25T13:13:13.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-11-25 13:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542362
;

-- 2017-11-25T13:14:07.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kosten',Updated=TO_TIMESTAMP('2017-11-25 13:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53038
;

-- 2017-11-25T13:17:27.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt Attribute',Updated=TO_TIMESTAMP('2017-11-25 13:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540699
;

-- 2017-11-25T13:17:37.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-25 13:17:37','YYYY-MM-DD HH24:MI:SS'),Name='Product Attributes' WHERE AD_Tab_ID=540699 AND AD_Language='en_US'
;

-- 2017-11-25T13:17:44.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-25 13:17:44','YYYY-MM-DD HH24:MI:SS'),Name='Costs' WHERE AD_Tab_ID=53038 AND AD_Language='en_US'
;

-- 2017-11-25T13:19:07.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Attribut Wert',Updated=TO_TIMESTAMP('2017-11-25 13:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556229
;

-- 2017-11-25T13:19:16.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Attribut',Updated=TO_TIMESTAMP('2017-11-25 13:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556226
;

-- 2017-11-25T13:19:50.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitsablauf',Updated=TO_TIMESTAMP('2017-11-25 13:19:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53811
;

-- 2017-11-25T13:20:07.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktivität',Updated=TO_TIMESTAMP('2017-11-25 13:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53812
;

-- 2017-11-25T13:20:13.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prozess Typ',Updated=TO_TIMESTAMP('2017-11-25 13:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53820
;

-- 2017-11-25T13:20:24.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge Batch Größe',Updated=TO_TIMESTAMP('2017-11-25 13:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53822
;

-- 2017-11-25T13:20:35.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status Veröffentlichung',Updated=TO_TIMESTAMP('2017-11-25 13:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53827
;

-- 2017-11-25T13:20:46.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ersteller',Updated=TO_TIMESTAMP('2017-11-25 13:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53831
;

-- 2017-11-25T13:20:58.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitszeit',Updated=TO_TIMESTAMP('2017-11-25 13:20:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53834
;

-- 2017-11-25T13:21:08.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Warte Zeit',Updated=TO_TIMESTAMP('2017-11-25 13:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53840
;

-- 2017-11-25T13:21:35.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verantwortlicher Arbeitsablauf',Updated=TO_TIMESTAMP('2017-11-25 13:21:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53806
;

-- 2017-11-25T13:21:46.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitsablauf Art',Updated=TO_TIMESTAMP('2017-11-25 13:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53819
;

-- 2017-11-25T13:21:58.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitsablauf Prozessor',Updated=TO_TIMESTAMP('2017-11-25 13:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53807
;

-- 2017-11-25T13:22:12.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arbeitsablauf Validieren',Updated=TO_TIMESTAMP('2017-11-25 13:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53842
;

-- 2017-11-25T13:23:45.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktuelle Kosten unterhalb',Updated=TO_TIMESTAMP('2017-11-25 13:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53741
;

-- 2017-11-25T13:24:13.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktuelle Kosten Level',Updated=TO_TIMESTAMP('2017-11-25 13:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53741
;

-- 2017-11-25T13:24:43.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mengeneinheit',Updated=TO_TIMESTAMP('2017-11-25 13:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554738
;

-- 2017-11-25T13:24:53.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-11-25 13:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548823
;

-- 2017-11-25T13:28:17.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Stückliste Nutzung',Updated=TO_TIMESTAMP('2017-11-25 13:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53800
;

-- 2017-11-25T13:28:32.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktionsauftrag Stückliste',Updated=TO_TIMESTAMP('2017-11-25 13:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53803
;

-- 2017-11-25T13:28:42.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kopieren Von',Updated=TO_TIMESTAMP('2017-11-25 13:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53802
;

-- 2017-11-25T13:28:47.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Stücklisten Zugehörigkeit',Updated=TO_TIMESTAMP('2017-11-25 13:28:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53799
;

-- 2017-11-25T13:30:46.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kritische Komponente',Updated=TO_TIMESTAMP('2017-11-25 13:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53762
;

-- 2017-11-25T13:30:58.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Komponenten Typ',Updated=TO_TIMESTAMP('2017-11-25 13:30:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53760
;

-- 2017-11-25T13:31:09.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prognose',Updated=TO_TIMESTAMP('2017-11-25 13:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53771
;

-- 2017-11-25T13:31:10.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Qty Scrap',Updated=TO_TIMESTAMP('2017-11-25 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53781
;

-- 2017-11-25T13:31:19.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge abgewiesen',Updated=TO_TIMESTAMP('2017-11-25 13:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53780
;

-- 2017-11-25T13:31:29.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge Ausschuss',Updated=TO_TIMESTAMP('2017-11-25 13:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53781
;

-- 2017-11-25T13:31:45.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge gebucht',Updated=TO_TIMESTAMP('2017-11-25 13:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53782
;

-- 2017-11-25T13:38:15.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540186, SeqNo=60,Updated=TO_TIMESTAMP('2017-11-25 13:38:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542323
;

-- 2017-11-25T13:38:46.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2017-11-25 13:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542323
;

