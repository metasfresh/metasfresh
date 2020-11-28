-- 2017-06-26T15:30:31.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Wähle alle %(size)s Zeilen',Updated=TO_TIMESTAMP('2017-06-26 15:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544399
;

-- 2017-06-26T15:30:44.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),MsgText='Select all %(size)s items' WHERE AD_Message_ID=544399 AND AD_Language='de_CH'
;

-- 2017-06-26T15:30:47.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),MsgText='Select all %(size)s items' WHERE AD_Message_ID=544399 AND AD_Language='en_US'
;

-- 2017-06-26T15:30:52.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-26 15:30:52','YYYY-MM-DD HH24:MI:SS'),MsgText='Select all %(size)s items' WHERE AD_Message_ID=544399 AND AD_Language='nl_NL'
;

