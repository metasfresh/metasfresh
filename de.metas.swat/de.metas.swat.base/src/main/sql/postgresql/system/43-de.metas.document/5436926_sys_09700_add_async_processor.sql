
-- 19.01.2016 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540036,'de.metas.document.async.spi.impl.CreateCounterDocWorkpackageProcessor',TO_TIMESTAMP('2016-01-19 15:43:25','YYYY-MM-DD HH24:MI:SS'),100,'Task 09700','de.metas.document','CreateCounterDocWorkpackageProcessor','Y',TO_TIMESTAMP('2016-01-19 15:43:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.01.2016 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540036,TO_TIMESTAMP('2016-01-19 15:43:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'CreateCounterDocWorkpackageProcessor',1,TO_TIMESTAMP('2016-01-19 15:43:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.01.2016 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540036,540060,540036,TO_TIMESTAMP('2016-01-19 15:44:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-01-19 15:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 19.01.2016 17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Classname='de.metas.document.async.spi.impl.CreateCounterDocPP', InternalName='CreateCounterDocPP',Updated=TO_TIMESTAMP('2016-01-19 17:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540036
;

