-- 09.03.2017 21:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Leergut Rücknahme',Updated=TO_TIMESTAMP('2017-03-09 21:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540760
;

-- 09.03.2017 21:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540760
;

-- 09.03.2017 21:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-09 21:16:01','YYYY-MM-DD HH24:MI:SS'),Name='Empties Receive' WHERE AD_Process_ID=540760 AND AD_Language='en_US'
;

-- 09.03.2017 21:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-09 21:16:33','YYYY-MM-DD HH24:MI:SS'),Name='Empties Return' WHERE AD_Process_ID=540759 AND AD_Language='en_US'
;

