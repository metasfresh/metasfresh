-- 2017-04-08T16:42:47.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540114,0,TO_TIMESTAMP('2017-04-08 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'EE02','Y','org.eevolution.PayrollModuleInterceptor','Model Validator to Libero HR & Payroll',1,TO_TIMESTAMP('2017-04-08 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2017-04-08T16:47:20.445
-- disabling it because it's not currently on our classpath.
UPDATE AD_ModelValidator SET IsActive='N',Updated=TO_TIMESTAMP('2017-04-08 16:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540114
;


-- 2017-04-08T16:44:07.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50139
;


