-- 20.07.2016 09:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541003,'S',TO_TIMESTAMP('2016-07-20 09:45:46','YYYY-MM-DD HH24:MI:SS'),100,'Configuration for the default payment rule to be used in invoice.','de.metas.invoice','Y','C_Invoice_PaymentRule',TO_TIMESTAMP('2016-07-20 09:45:46','YYYY-MM-DD HH24:MI:SS'),100,'P')
;

-- 20.07.2016 09:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.invoice.C_Invoice_PaymentRule',Updated=TO_TIMESTAMP('2016-07-20 09:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541003
;

