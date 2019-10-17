-- 2018-12-19T17:59:15.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='* Number of months that have to pass until contract renewal so the customer turns from "regular" to "new" AND
* Number of months without a contract renewal until a partner is considered not a customer any more
(issue https://github.com/metasfresh/metasfresh/issues/4820)',Updated=TO_TIMESTAMP('2018-12-19 17:59:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541251
;

