-- 06.01.2016 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.acct.async.FactAcctLogWorkpackageProcessor',540035,TO_TIMESTAMP('2016-01-06 17:57:18','YYYY-MM-DD HH24:MI:SS'),100,'Processes Fact_Acct_Log records','de.metas.acct','FactAcctLogWorkpackageProcessor','Y',TO_TIMESTAMP('2016-01-06 17:57:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.01.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540035,TO_TIMESTAMP('2016-01-06 17:58:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'FactAcctLogWorkpackageProcessor',1,TO_TIMESTAMP('2016-01-06 17:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.01.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540035,540059,540035,TO_TIMESTAMP('2016-01-06 17:58:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-01-06 17:58:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.01.2016 18:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Processes Fact_Acct_Log records. See http://dewiki908/mediawiki/index.php/09694_Fact_Acct_Summary_incremental_updates_%28101697304165%29',Updated=TO_TIMESTAMP('2016-01-06 18:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540035
;

