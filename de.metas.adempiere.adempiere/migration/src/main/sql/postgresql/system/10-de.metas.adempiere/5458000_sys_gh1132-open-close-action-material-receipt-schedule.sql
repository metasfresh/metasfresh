-- 09.03.2017 21:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2017-03-09 21:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540546 AND AD_Table_ID=540524
;

-- 09.03.2017 21:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2017-03-09 21:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540547 AND AD_Table_ID=540524
;

-- 09.03.2017 21:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeile reaktivieren',Updated=TO_TIMESTAMP('2017-03-09 21:29:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540546
;

-- 09.03.2017 21:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540546
;

-- 09.03.2017 21:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-09 21:29:19','YYYY-MM-DD HH24:MI:SS'),Name='Reactivate Row' WHERE AD_Process_ID=540546 AND AD_Language='en_US'
;

-- 09.03.2017 21:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zeile schließen',Updated=TO_TIMESTAMP('2017-03-09 21:29:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540547
;

-- 09.03.2017 21:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540547
;

-- 09.03.2017 21:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-09 21:30:03','YYYY-MM-DD HH24:MI:SS'),Name='Close Row' WHERE AD_Process_ID=540547 AND AD_Language='en_US'
;

