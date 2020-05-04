-- 2018-05-26T10:48:57.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sammelbestellungen',Updated=TO_TIMESTAMP('2018-05-26 10:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564128
;

-- 2018-05-26T10:49:35.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-26 10:49:35','YYYY-MM-DD HH24:MI:SS'),Name='Reminder Time (min)' WHERE AD_Field_ID=564123 AND AD_Language='en_US'
;

-- 2018-05-26T10:53:24.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@FrequencyType@!=''M''',Updated=TO_TIMESTAMP('2018-05-26 10:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564103
;

-- 2018-05-26T10:54:25.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@FrequencyType@=''M''',Updated=TO_TIMESTAMP('2018-05-26 10:54:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564103
;

