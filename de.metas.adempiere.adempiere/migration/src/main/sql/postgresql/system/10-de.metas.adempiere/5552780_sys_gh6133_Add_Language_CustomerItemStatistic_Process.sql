-- 2020-02-20T15:16:56.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from customerItemStatistics(@AD_Client_ID@, @AD_Org_ID@, ''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @C_BPartner_ID/-1@, @M_Product_ID/-1@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2020-02-20 17:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584649
;

