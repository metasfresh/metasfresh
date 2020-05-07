-- 2019-02-20T11:39:57.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Do you really want to delete?',Updated=TO_TIMESTAMP('2019-02-20 11:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541061
;

-- 2019-02-20T11:49:16.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Möchten sie den Datensatz wirklich löschen?',Updated=TO_TIMESTAMP('2019-02-20 11:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541061
;

-- 2019-02-20T11:49:48.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-20 11:49:48','YYYY-MM-DD HH24:MI:SS'),Description='Möchten sie den Datensatz wirklich löschen?' WHERE AD_Process_ID=541061 AND AD_Language='de_CH'
;

-- 2019-02-20T11:50:11.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-20 11:50:11','YYYY-MM-DD HH24:MI:SS'),Description='Do you really want to delete?' WHERE AD_Process_ID=541061 AND AD_Language='en_US'
;

-- 2019-02-20T11:51:07.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-20 11:51:07','YYYY-MM-DD HH24:MI:SS'),Description='Möchten sie den Datensatz wirklich löschen?' WHERE AD_Process_ID=541061 AND AD_Language='nl_NL'
;

-- 2019-02-20T15:52:50.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2019-02-20 15:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541061
;

