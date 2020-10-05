-- 2020-08-19T07:23:27.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Kalender {0} enthält nicht den zugesagten Termin {1,date}',Updated=TO_TIMESTAMP('2020-08-19 10:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544996
;

-- 2020-08-19T07:23:33.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Kalender {0} enthält nicht den zugesagten Termin {1,date}',Updated=TO_TIMESTAMP('2020-08-19 10:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544996
;

