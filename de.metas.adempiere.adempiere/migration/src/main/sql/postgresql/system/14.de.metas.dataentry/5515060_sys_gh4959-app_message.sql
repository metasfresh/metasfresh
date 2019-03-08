
-- 2019-03-07T07:25:28.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Erstellt von {0} am {1,date} um {2,time}; Aktualisiert von {3} am {4,date} um {5,time}',Updated=TO_TIMESTAMP('2019-03-07 07:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544840
;
