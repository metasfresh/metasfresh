-- 2018-08-03T12:48:49.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Customer Label Name' WHERE AD_Field_ID=563050 AND AD_Language='en_US'
;

-- 2018-08-03T12:50:45.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET Name='Product Nutrition' WHERE AD_Tab_ID=541159 AND AD_Language='en_US'
;

-- 2018-08-03T12:51:12.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET Name='Quantity' WHERE AD_Field_ID=564974 AND AD_Language='en_US'
;

-- 2018-08-03T12:52:24.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-08-03 12:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552449
;

-- 2018-08-03T12:52:27.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2018-08-03 12:52:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552448
;

-- 2018-08-03T12:52:28.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-03 12:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552449
;

-- 2018-08-03T12:53:07.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,565112,0,541164,552496,541689,'F',TO_TIMESTAMP('2018-08-03 12:53:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Mandant',40,0,0,TO_TIMESTAMP('2018-08-03 12:53:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-03T12:54:25.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=565113 AND AD_Language='en_US'
;

-- 2018-08-03T12:54:48.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET Name='Client',Description='',Help='' WHERE AD_Field_ID=565112 AND AD_Language='en_US'
;

-- 2018-08-03T12:54:50.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y' WHERE AD_Field_ID=565112 AND AD_Language='en_US'
;

-- 2018-08-03T12:55:35.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=564969 AND AD_Language='en_US'
;

-- 2018-08-03T12:55:45.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=564968 AND AD_Language='en_US'
;

-- 2018-08-03T13:08:32.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=564970 AND AD_Language='en_US'
;

-- 2018-08-03T13:09:04.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=565114 AND AD_Language='en_US'
;

-- 2018-08-03T13:09:46.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-08-03 13:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552457
;

-- 2018-08-03T13:09:46.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-08-03 13:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552456
;

-- 2018-08-03T13:09:46.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-08-03 13:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552455
;

-- 2018-08-03T13:21:02.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Nährwert' WHERE AD_Field_ID=564973 AND AD_Language='de_CH'
;

-- 2018-08-03T13:21:19.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Menge' WHERE AD_Field_ID=564974 AND AD_Language='de_CH'
;

-- 2018-08-03T13:24:22.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2018-08-03 13:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564968
;

-- 2018-08-03T13:24:28.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=20,Updated=TO_TIMESTAMP('2018-08-03 13:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564969
;

-- 2018-08-03T13:24:30.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2018-08-03 13:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564973
;

-- 2018-08-03T13:24:32.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2018-08-03 13:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564974
;

-- 2018-08-03T13:24:35.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2018-08-03 13:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564970
;

-- 2018-08-03T13:24:49.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-08-03 13:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564969
;

-- 2018-08-03T13:25:31.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2018-08-03 13:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565112
;

-- 2018-08-03T13:25:38.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-08-03 13:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565113
;

-- 2018-08-03T13:25:40.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2018-08-03 13:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565116
;

-- 2018-08-03T13:25:44.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=40,Updated=TO_TIMESTAMP('2018-08-03 13:25:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565114
;

