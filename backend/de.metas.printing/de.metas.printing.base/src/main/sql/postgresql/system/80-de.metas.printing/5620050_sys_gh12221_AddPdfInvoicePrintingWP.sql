-- 2021-12-23T09:10:59.437190500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.printing.async.spi.impl.PrintingQueuePDFConcatenateWorkpackageProcessorcessor',540092,TO_TIMESTAMP('2021-12-23 11:10:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','nvoicePDFConcatenateWorkpackageProcessor','Y',TO_TIMESTAMP('2021-12-23 11:10:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-23T09:11:14.352464900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540062,TO_TIMESTAMP('2021-12-23 11:11:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'PrintingQueuePDFConcatenateWorkpackageProcessor',1,TO_TIMESTAMP('2021-12-23 11:11:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-23T09:11:21.716838500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540092,540098,540062,TO_TIMESTAMP('2021-12-23 11:11:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-12-23 11:11:21','YYYY-MM-DD HH24:MI:SS'),100)
;

