-- 2021-11-05T12:52:20.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540089,'de.metas.salesorder.candidate.ProcessOLCandsWorkpackageProcessor',TO_TIMESTAMP('2021-11-05 14:52:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.salesorder','Y',TO_TIMESTAMP('2021-11-05 14:52:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T12:52:23.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET InternalName='ProcessOLCandsWorkpackageProcessor',Updated=TO_TIMESTAMP('2021-11-05 14:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540089
;

-- 2021-11-05T12:52:29.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='A',Updated=TO_TIMESTAMP('2021-11-05 14:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540089
;

-- 2021-11-05T12:53:18.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Automatically process OLCands to order, ship and invoice',Updated=TO_TIMESTAMP('2021-11-05 14:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540089
;

-- 2021-11-05T12:53:43.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540060,TO_TIMESTAMP('2021-11-05 14:53:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'ProcessOLCandsWorkpackageProcessor',10,TO_TIMESTAMP('2021-11-05 14:53:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T12:53:46.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET PoolSize=1,Updated=TO_TIMESTAMP('2021-11-05 14:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540060
;

-- 2021-11-05T12:53:47.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000,Updated=TO_TIMESTAMP('2021-11-05 14:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540060
;

-- 2021-11-05T12:53:53.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540089,540095,540060,TO_TIMESTAMP('2021-11-05 14:53:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-11-05 14:53:53','YYYY-MM-DD HH24:MI:SS'),100)
;

