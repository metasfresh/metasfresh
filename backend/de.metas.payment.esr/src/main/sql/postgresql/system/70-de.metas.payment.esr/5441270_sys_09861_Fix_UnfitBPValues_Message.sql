-- 01.03.2016 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Beleg zur importierten Referenznummer hat den Geschäftspartner mit der Nummer {0} statt wie erforderlich {1}',Updated=TO_TIMESTAMP('2016-03-01 18:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=541117
;

-- 01.03.2016 18:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=541117
;

