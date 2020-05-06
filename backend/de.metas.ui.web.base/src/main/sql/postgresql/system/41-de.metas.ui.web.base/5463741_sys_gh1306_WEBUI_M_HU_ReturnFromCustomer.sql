-- 2017-05-29T12:13:36.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Return from customer',Updated=TO_TIMESTAMP('2017-05-29 12:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540797
;

-- 2017-05-29T12:13:36.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540797
;

-- 2017-05-29T12:13:42.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-05-29 12:13:42','YYYY-MM-DD HH24:MI:SS'),Name='Return from customer' WHERE AD_Process_ID=540797 AND AD_Language='de_CH'
;

-- 2017-05-29T12:13:45.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-05-29 12:13:45','YYYY-MM-DD HH24:MI:SS'),Name='Return from customer' WHERE AD_Process_ID=540797 AND AD_Language='en_US'
;

-- 2017-05-29T12:13:48.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-05-29 12:13:48','YYYY-MM-DD HH24:MI:SS'),Name='Return from customer' WHERE AD_Process_ID=540797 AND AD_Language='nl_NL'
;

