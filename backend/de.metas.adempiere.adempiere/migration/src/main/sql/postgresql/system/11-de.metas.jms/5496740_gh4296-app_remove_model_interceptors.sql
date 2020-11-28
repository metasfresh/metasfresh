-- remove de.metas.jms.JmsInterceptor
-- 2018-06-28T11:06:41.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ModelValidator WHERE AD_ModelValidator_ID=540108
;

-- remove de.metas.jax.rs.model.interceptor.JaxRsInterceptor
-- 2018-06-28T12:37:02.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ModelValidator WHERE AD_ModelValidator_ID=540107
;

-- unrelated fix:
-- change the tab for C_Queue_WorkPackage_Log to read-only
-- 2018-06-28T11:21:15.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-28 11:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540661
;

