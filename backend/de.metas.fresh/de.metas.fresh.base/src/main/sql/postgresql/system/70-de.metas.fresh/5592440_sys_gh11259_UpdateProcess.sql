-- 2021-06-11T14:54:20.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Import Payment Request for Purchase Invoice QR',Updated=TO_TIMESTAMP('2021-06-11 17:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584841
;

-- 2021-06-11T14:56:51.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2021-06-11 17:56:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540942
;

-- 2021-06-11T15:00:14.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zahlung einlesen QR',Updated=TO_TIMESTAMP('2021-06-11 18:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584841
;

-- 2021-06-11T15:00:21.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Zahlung einlesen QR',Updated=TO_TIMESTAMP('2021-06-11 18:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584841
;

-- 2021-06-11T15:00:21.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zahlung einlesen QR',Updated=TO_TIMESTAMP('2021-06-11 18:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584841
;

-- 2021-06-11T15:00:54.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Import Payment Request for Purchase Invoice QR',Updated=TO_TIMESTAMP('2021-06-11 18:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584841
;

-- 2021-06-11T15:03:11.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2021-06-11 18:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540942
;

-- 2021-06-11T15:05:00.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2021-06-11 18:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584841
;

-- 2021-06-11T15:05:10.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2021-06-11 18:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584841
;

-- 2021-06-11T15:05:28.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET EntityType='de.metas.ui.web', WEBUI_ViewQuickAction='N',Updated=TO_TIMESTAMP('2021-06-11 18:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540942
;

-- 2021-06-11T15:37:29.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=543328, ColumnName='Barcode', Description=NULL, EntityType='de.metas.ui.web', Name='Barcode',Updated=TO_TIMESTAMP('2021-06-11 18:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542026
;

-- 2021-06-11T15:39:45.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-11 18:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542026
;

-- 2021-06-11T15:40:26.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-06-11 18:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542026
;

-- 2021-06-11T15:40:45.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@C_BPartner_ID@',Updated=TO_TIMESTAMP('2021-06-11 18:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542027
;



-- 2021-06-14T06:52:21.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-14 09:52:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542026
;

-- 2021-06-14T06:52:24.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2021-06-14 09:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542027
;

-- 2021-06-14T06:52:25.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2021-06-14 09:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542028
;

-- 2021-06-14T06:52:27.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2021-06-14 09:52:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542029
;

-- 2021-06-14T06:52:29.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2021-06-14 09:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542030
;

-- 2021-06-14T06:52:31.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2021-06-14 09:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542031
;

-- 2021-06-14T09:59:22.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=543079, ColumnName='FullPaymentString', Description='Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String', EntityType='de.metas.payment', Name='Eingelesene Zeichenkette',Updated=TO_TIMESTAMP('2021-06-14 12:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542026
;

-- 2021-06-14T10:06:16.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.banking.payment.process.WEBUI_Import_Payment_Request_For_Purchase_Invoice_QRCode',Updated=TO_TIMESTAMP('2021-06-14 13:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584841
;

-- 2021-06-14T10:23:53.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2021-06-14 13:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542027
;

-- 2021-06-14T10:23:54.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2021-06-14 13:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542028
;

-- 2021-06-14T10:23:56.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2021-06-14 13:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542029
;

-- 2021-06-14T10:23:58.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2021-06-14 13:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542030
;

-- 2021-06-14T10:24:00.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2021-06-14 13:24:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542031
;

-- 2021-06-14T11:00:52.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=36,Updated=TO_TIMESTAMP('2021-06-14 14:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542026
;




-- 2021-06-15T15:02:04.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='',Updated=TO_TIMESTAMP('2021-06-15 18:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542027
;

