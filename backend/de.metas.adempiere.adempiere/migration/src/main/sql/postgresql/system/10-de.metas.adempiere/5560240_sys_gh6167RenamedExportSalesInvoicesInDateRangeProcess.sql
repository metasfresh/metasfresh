-- 2020-05-29T11:10:05.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Anzahl Rechnungen pro Organisation',Updated=TO_TIMESTAMP('2020-05-29 14:10:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541243
;

-- 2020-05-29T11:10:13.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Anzahl Rechnungen pro Organisation',Updated=TO_TIMESTAMP('2020-05-29 14:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541243
;

-- 2020-05-29T11:10:16.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Anzahl Rechnungen pro Organisation',Updated=TO_TIMESTAMP('2020-05-29 14:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541243
;

-- 2020-05-29T11:15:03.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Export Number of Invoices per Organisation',Updated=TO_TIMESTAMP('2020-05-29 14:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541243
;

-- 2020-05-29T11:15:11.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Export Number of Invoices per Organisation',Updated=TO_TIMESTAMP('2020-05-29 14:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541243
;

-- 2020-05-29T11:19:24.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Export Number of Invoices per Organisation', PrintName='Export Number of Invoices per Organisation',Updated=TO_TIMESTAMP('2020-05-29 14:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577459 AND AD_Language='en_US'
;

-- 2020-05-29T11:19:24.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577459,'en_US') 
;

