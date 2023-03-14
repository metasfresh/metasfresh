

-- 2021-11-15T13:16:39.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Send to GRS', Value='C_BPartner_SyncTo_GRS_HTTP',Updated=TO_TIMESTAMP('2021-11-15 15:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584944
;

-- 2021-11-15T13:16:57.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte GRS REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Name='An GRS senden',Updated=TO_TIMESTAMP('2021-11-15 15:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584944
;

-- 2021-11-15T13:17:04.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte GRS REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Help=NULL, Name='An GRS senden',Updated=TO_TIMESTAMP('2021-11-15 15:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584944
;

-- 2021-11-15T13:17:04.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte GRS REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Name='An GRS senden',Updated=TO_TIMESTAMP('2021-11-15 15:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584944
;

-- 2021-11-15T13:17:16.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte GRS REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Name='An GRS senden',Updated=TO_TIMESTAMP('2021-11-15 15:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584944
;

-- 2021-11-15T13:17:35.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sends the selected business partners to the corresponding GRS REST API config. Once a business partner was send to an external system, it will be automatically resend when changed, as long as the corresponding checkbox is ticked in the external system config.', Name='Send to GRS',Updated=TO_TIMESTAMP('2021-11-15 15:17:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584944
;

