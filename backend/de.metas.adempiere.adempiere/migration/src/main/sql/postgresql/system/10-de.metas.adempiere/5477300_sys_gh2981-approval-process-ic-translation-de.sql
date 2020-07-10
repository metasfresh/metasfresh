-- 2017-11-16T09:21:26.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Freigabe zur Fakturierung',Updated=TO_TIMESTAMP('2017-11-16 09:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540830
;

-- 2017-11-16T09:21:32.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-16 09:21:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540830 AND AD_Language='en_US'
;

