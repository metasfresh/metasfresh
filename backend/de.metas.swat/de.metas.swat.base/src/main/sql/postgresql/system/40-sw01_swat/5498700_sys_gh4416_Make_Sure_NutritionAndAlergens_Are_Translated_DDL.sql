
-- 2018-08-02T14:17:10.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET IsTranslated='Y',Name='Produkt Allergene' WHERE AD_Tab_ID=541164 AND AD_Language='de_CH'
;

-- 2018-08-02T14:17:21.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET Name='Product Allergen' WHERE AD_Tab_ID=541164 AND AD_Language='nl_NL'
;

-- 2018-08-02T14:18:05.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET IsTranslated='Y',Name='Produkt Nutrition' WHERE AD_Tab_ID=541159 AND AD_Language='en_US'
;

-- 2018-08-02T14:18:10.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET IsTranslated='Y' WHERE AD_Tab_ID=541159 AND AD_Language='de_CH'
;

-- 2018-08-02T14:20:16.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET Name='Nutrition' WHERE AD_Field_ID=564973 AND AD_Language='en_US'
;

-- 2018-08-02T14:20:30.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET Name='Nutrition Fact' WHERE AD_Field_ID=564973 AND AD_Language='en_US'
;

-- 2018-08-02T14:22:06.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Allergen',Updated=TO_TIMESTAMP('2018-08-02 14:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565116
;

-- 2018-08-02T14:22:13.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Allergen' WHERE AD_Field_ID=565116 AND AD_Language='en_US'
;

-- 2018-08-02T14:22:18.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET Name='Allergen' WHERE AD_Field_ID=565116 AND AD_Language='nl_NL'
;

-- 2018-08-02T14:22:35.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Allergen' WHERE AD_Field_ID=565116 AND AD_Language='de_CH'
;

-- 2018-08-02T14:59:36.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2018-08-02 14:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552457
;

-- 2018-08-02T14:59:39.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-08-02 14:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552456
;

-- 2018-08-02T14:59:46.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-08-02 14:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552455
;





-- 2018-08-02T15:54:28.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=552454
;

-- 2018-08-02T15:55:05.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-02 15:55:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552457
;

-- 2018-08-02T15:55:05.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-02 15:55:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552456
;

-- 2018-08-02T15:55:07.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-02 15:55:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552455
;

-- 2018-08-02T15:59:11.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2018-08-02 15:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541688
;

-- 2018-08-02T15:59:14.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2018-08-02 15:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541688
;
















-- 2018-08-02T16:01:44.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=18,Updated=TO_TIMESTAMP('2018-08-02 16:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541159
;

-- 2018-08-02T16:01:48.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=19,Updated=TO_TIMESTAMP('2018-08-02 16:01:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541164
;

-- 2018-08-02T16:02:26.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=170,Updated=TO_TIMESTAMP('2018-08-02 16:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541159
;

-- 2018-08-02T16:02:30.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=180,Updated=TO_TIMESTAMP('2018-08-02 16:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541164
;

-- 2018-08-02T16:04:41.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2018-08-02 16:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552452
;

-- 2018-08-02T16:04:54.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-08-02 16:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552453
;

-- 2018-08-02T16:04:59.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2018-08-02 16:04:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552450
;

-- 2018-08-02T16:05:04.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2018-08-02 16:05:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552449
;

-- 2018-08-02T16:05:35.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-08-02 16:05:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552449
;





