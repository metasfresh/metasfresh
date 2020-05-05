-- 2019-09-20T12:28:05.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.

Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-09-20 15:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542877
;

-- 2019-09-20T12:28:21.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {0}.

Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 15:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=542877
;

