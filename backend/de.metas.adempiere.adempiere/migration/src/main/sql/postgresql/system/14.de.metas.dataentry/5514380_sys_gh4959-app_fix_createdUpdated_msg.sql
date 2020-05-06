-- 2019-03-01T14:17:20.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Erstellt von {0} am {1,date} um {2,time}; Aktualisiert von {3} am {4,date} um {5,time}',Updated=TO_TIMESTAMP('2019-03-01 14:17:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544840
;

-- 2019-03-01T14:17:29.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Erstellt von {0} am {1,date} um {2,time}; Aktualisiert von {3} am {4,date} um {5,time}.',Updated=TO_TIMESTAMP('2019-03-01 14:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544840
;

-- 2019-03-01T14:17:40.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Erstellt von {0} am {1,date} um {2,time}; Aktualisiert von {3} am {4,date} um {5,time}.',Updated=TO_TIMESTAMP('2019-03-01 14:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544840
;

-- 2019-03-01T14:19:08.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Created by {0} on {1,date} at {2,time}; Updated by {3} on {4,date} at {5,time}',Updated=TO_TIMESTAMP('2019-03-01 14:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544840
;

