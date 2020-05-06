-- 12.08.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,'org.adempiere.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner',540024,TO_TIMESTAMP('2015-08-12 15:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Recommended to run in a 1-sized thread pool. Updates C_BPartner.TotalOpenBalance and SO_CreditUsed.','de.metas.swat','Y',TO_TIMESTAMP('2015-08-12 15:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Name='C_BPartner_UpdateStats',Updated=TO_TIMESTAMP('2015-08-12 15:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540024
;

-- 12.08.2015 15:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540024,540048,540024,TO_TIMESTAMP('2015-08-12 15:46:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-08-12 15:46:09','YYYY-MM-DD HH24:MI:SS'),100)
;

