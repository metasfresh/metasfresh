-- 2021-09-16T12:44:39.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540069,'de.metas.invoicecandidate.async.spi.impl.RecreateInvoiceWorkpackageProcessor',TO_TIMESTAMP('2021-09-16 15:44:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y',TO_TIMESTAMP('2021-09-16 15:44:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-16T12:44:45.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET InternalName='RecreateInvoiceWorkpackageProcessor',Updated=TO_TIMESTAMP('2021-09-16 15:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540069
;

-- 2021-09-16T12:45:33.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540055,TO_TIMESTAMP('2021-09-16 15:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'RecreateInvoiceWorkpackageProcessor',10,TO_TIMESTAMP('2021-09-16 15:45:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-16T12:45:38.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET PoolSize=1,Updated=TO_TIMESTAMP('2021-09-16 15:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540055
;

-- 2021-09-16T12:45:44.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000,Updated=TO_TIMESTAMP('2021-09-16 15:45:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540055
;

-- 2021-09-16T12:46:26.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540069,540091,540055,TO_TIMESTAMP('2021-09-16 15:46:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-16 15:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

