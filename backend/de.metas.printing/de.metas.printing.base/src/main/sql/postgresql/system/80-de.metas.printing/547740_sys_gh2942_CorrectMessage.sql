-- 2017-11-16T19:41:06.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Pdf {0} von {1} wurde fertiggestellt.
Die Datei enthält {2} Belege. {3}',Updated=TO_TIMESTAMP('2017-11-16 19:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543766
;
