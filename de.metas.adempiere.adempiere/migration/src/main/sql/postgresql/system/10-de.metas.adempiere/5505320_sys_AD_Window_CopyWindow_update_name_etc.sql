-- 2018-11-01T12:12:21.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-01 12:12:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Copy from other window' WHERE AD_Process_ID=540998 AND AD_Language='en_US'
;

-- 2018-11-01T12:12:36.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Von anderem Fenster kopieren',Updated=TO_TIMESTAMP('2018-11-01 12:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540998
;

-- 2018-11-01T12:12:44.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-01 12:12:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Von anderem Fenster kopieren' WHERE AD_Process_ID=540998 AND AD_Language='de_CH'
;

-- 2018-11-01T12:13:55.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Kopiert alle Tabs, Felder usw. von einem anderen Fenster in das aktuelle Fenster.',Updated=TO_TIMESTAMP('2018-11-01 12:13:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540998
;

-- 2018-11-01T12:14:11.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Description='', Help='', Name='Quell-Fenster',Updated=TO_TIMESTAMP('2018-11-01 12:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541335
;

-- 2018-11-01T12:14:25.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-01 12:14:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quell-Fenster',Description='',Help='' WHERE AD_Process_Para_ID=541335 AND AD_Language='de_CH'
;

-- 2018-11-01T12:14:35.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-01 12:14:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Source window',Description='',Help='' WHERE AD_Process_Para_ID=541335 AND AD_Language='en_US'
;

