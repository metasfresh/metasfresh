-- 2018-07-05T12:03:45.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Kein ausgewählter Datensatz hat eine Mailaddresse.', MsgType='I',Updated=TO_TIMESTAMP('2018-07-05 12:03:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=542865
;

-- 2018-07-05T12:04:06.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-05 12:04:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='No selected record has a mail address.' WHERE AD_Message_ID=542865 AND AD_Language='en_GB'
;

-- 2018-07-05T12:04:13.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-05 12:04:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='No selected record has a mail address.' WHERE AD_Message_ID=542865 AND AD_Language='en_US'
;

-- 2018-07-05T12:04:23.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-05 12:04:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Kein ausgewählter Datensatz hat eine Mailaddresse.' WHERE AD_Message_ID=542865 AND AD_Language='de_CH'
;

-- 2018-07-05T14:30:25.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=542864
;

-- 2018-07-05T14:30:25.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=542864
;

-- 2018-07-05T14:30:25.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=542863
;

-- 2018-07-05T14:30:25.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=542863
;

-- 2018-07-05T14:30:25.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=542861
;

-- 2018-07-05T14:30:25.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=542861
;

