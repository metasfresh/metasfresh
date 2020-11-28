-- 2018-08-20T17:55:52.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zutaten',Updated=TO_TIMESTAMP('2018-08-20 17:55:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565323
;

-- 2018-08-20T17:56:00.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y' WHERE AD_Field_ID=565323 AND AD_Language='en_US'
;

-- 2018-08-20T17:56:07.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='Y',Name='Zutaten' WHERE AD_Field_ID=565323 AND AD_Language='de_CH'
;

