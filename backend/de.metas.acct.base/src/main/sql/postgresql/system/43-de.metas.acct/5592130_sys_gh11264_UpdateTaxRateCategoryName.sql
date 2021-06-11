-- Update normal tax rate name
-- 2021-06-10T12:41:56.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Normale MWSt',Updated=TO_TIMESTAMP('2021-06-10 15:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=1000009
;

-- 2021-06-10T12:41:56.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Normale MWSt'  WHERE C_TaxCategory_ID=1000009 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-10T13:18:01.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Normale MWSt',Updated=TO_TIMESTAMP('2021-06-10 16:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=1000009
;


-- Update reduced tax rate name
-- 2021-06-10T12:44:06.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Reduzierte MWSt',Updated=TO_TIMESTAMP('2021-06-10 15:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=1000010
;

-- 2021-06-10T12:44:06.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Reduzierte MWSt'  WHERE C_TaxCategory_ID=1000010 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;