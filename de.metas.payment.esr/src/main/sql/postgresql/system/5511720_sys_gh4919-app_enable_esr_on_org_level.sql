
-- de.metas.order.TemporaryPriceConditionsColorName
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-02-01 15:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541205
;

-- de.metas.order.NoPriceConditionsColorName
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2019-02-01 15:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541204
;
-- 2019-02-01T20:34:59.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If enabled, the system will assume that the respective org(s) have a valid ESR account and it will create ESR reference strings for sales invoices.',Updated=TO_TIMESTAMP('2019-02-01 20:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540875
;

