-- 2018-03-21T15:22:06.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Produkte umlagern',Updated=TO_TIMESTAMP('2018-03-21 15:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540912
;

-- 2018-03-21T15:22:47.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-21 15:22:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Relocate Products' WHERE AD_Process_ID=540912 AND AD_Language='en_US'
;

