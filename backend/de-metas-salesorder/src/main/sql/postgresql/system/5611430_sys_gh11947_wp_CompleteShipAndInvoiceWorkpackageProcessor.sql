
-- 2021-11-02T13:34:25.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540074,'de.metas.salesorder.async.CompleteShipAndInvoiceWorkpackageProcessor',TO_TIMESTAMP('2021-11-02 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.salesorder','Y',TO_TIMESTAMP('2021-11-02 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-02T13:35:02.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET InternalName='CompleteShipAndInvoiceWorkpackageProcessor',Updated=TO_TIMESTAMP('2021-11-02 15:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540074
;

-- 2021-11-02T13:35:29.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Complete shipment and invoice',Updated=TO_TIMESTAMP('2021-11-02 15:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540074
;

-- 2021-11-02T13:36:50.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540057,TO_TIMESTAMP('2021-11-02 15:36:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'CompleteShipAndInvoiceWorkpackageProcessor',10,TO_TIMESTAMP('2021-11-02 15:36:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-02T13:36:56.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET PoolSize=1,Updated=TO_TIMESTAMP('2021-11-02 15:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540057
;

-- 2021-11-02T13:36:58.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000,Updated=TO_TIMESTAMP('2021-11-02 15:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540057
;

-- 2021-11-02T13:37:10.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540074,540093,540057,TO_TIMESTAMP('2021-11-02 15:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-11-02 15:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

