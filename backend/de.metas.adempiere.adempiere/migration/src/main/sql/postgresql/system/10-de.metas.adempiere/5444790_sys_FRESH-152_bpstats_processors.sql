-- 18.04.2016 17:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Recommended to run in a 1-sized thread pool. Updates C_BPartner_Stats.TotalOpenBalance and SO_CreditUsed.',Updated=TO_TIMESTAMP('2016-04-18 17:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540024
;

-- 18.04.2016 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'org.adempiere.bpartner.service.async.spi.impl.C_BPartner_UpdateSOCreditStatus',540043,TO_TIMESTAMP('2016-04-18 17:02:52','YYYY-MM-DD HH24:MI:SS'),100,'Recommended to run in a 1-sized thread pool. Updates C_BPartner_Stats.SOCreditStatus','de.metas.swat','C_BPartner_UpdateSOCreditStatus','Y',TO_TIMESTAMP('2016-04-18 17:02:52','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 18.04.2016 17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'org.adempiere.bpartner.service.async.spi.impl.C_BPartner_UpdateActualLifeTime',540044,TO_TIMESTAMP('2016-04-18 17:03:56','YYYY-MM-DD HH24:MI:SS'),100,'Recommended to run in a 1-sized thread pool. Updates C_BPartner_Stats.ActualLifeTime','de.metas.swat','C_BPartner_UpdateActualLifeTime','Y',TO_TIMESTAMP('2016-04-18 17:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.04.2016 17:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540043,540066,540024,TO_TIMESTAMP('2016-04-18 17:29:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-04-18 17:29:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.04.2016 17:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540044,540067,540024,TO_TIMESTAMP('2016-04-18 17:29:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-04-18 17:29:56','YYYY-MM-DD HH24:MI:SS'),100)
;

