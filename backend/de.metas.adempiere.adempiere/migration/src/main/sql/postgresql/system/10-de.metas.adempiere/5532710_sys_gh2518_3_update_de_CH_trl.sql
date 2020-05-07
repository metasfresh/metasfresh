-- 2019-10-02T05:42:43.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zahlung einlesen',Updated=TO_TIMESTAMP('2019-10-02 08:42:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541196
;

-- 2019-10-02T05:43:22.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543079 AND AD_Language='de_DE'
;

-- 2019-10-02T05:43:22.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543079,'de_DE') 
;

-- 2019-10-02T05:43:22.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543079,'de_DE') 
;

-- 2019-10-02T05:43:53.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Neues Bankkonto wird erstellt', PrintName='Neues Bankkonto wird erstellt',Updated=TO_TIMESTAMP('2019-10-02 08:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577105 AND AD_Language='de_CH'
;

-- 2019-10-02T05:43:53.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577105,'de_CH') 
;

-- 2019-10-02T05:46:01.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.  Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:06.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.    Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:07.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.    Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:09.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.    Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:10.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.    Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:12.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}.    Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:17.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {0}.  Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:29.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {0}. Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:40.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {0}. Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:46.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}. Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=542877
;

-- 2019-10-02T05:46:55.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Could not find the scanned bank account: {0}. Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-10-02 08:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542877
;

-- 2019-10-02T05:48:05.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544936
;

-- 2019-10-02T05:48:08.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544936
;

-- 2019-10-02T05:48:24.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544937
;

-- 2019-10-02T05:48:28.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544937
;

-- 2019-10-02T05:49:27.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=542985
;

-- 2019-10-02T05:49:33.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Payment Request for invoice already exists.',Updated=TO_TIMESTAMP('2019-10-02 08:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=542985
;

-- 2019-10-02T05:49:37.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Payment Request for invoice already exists.',Updated=TO_TIMESTAMP('2019-10-02 08:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=542985
;

-- 2019-10-02T05:49:53.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:49:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544938
;

-- 2019-10-02T05:49:57.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-02 08:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544938
;

