-- 14.10.2015 21:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Generate invoices from invoice candidates',Updated=TO_TIMESTAMP('2015-10-14 21:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540000
;

-- 14.10.2015 21:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540028,'de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor',TO_TIMESTAMP('2015-10-14 21:37:17','YYYY-MM-DD HH24:MI:SS'),100,'Updates invalid invoice candidates','de.metas.invoicecandidate','Y',TO_TIMESTAMP('2015-10-14 21:37:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540029,'de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor',TO_TIMESTAMP('2015-10-14 21:37:56','YYYY-MM-DD HH24:MI:SS'),100,'Create missing invoice candidates for given models','de.metas.invoicecandidate','Y',TO_TIMESTAMP('2015-10-14 21:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Name='C_Invoice_Candidate - Generate Invoices',Updated=TO_TIMESTAMP('2015-10-14 21:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540011
;

-- 14.10.2015 21:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540028,TO_TIMESTAMP('2015-10-14 21:40:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'C_Invoice_Candidate - Update invalid',1,TO_TIMESTAMP('2015-10-14 21:40:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540028,540052,540028,TO_TIMESTAMP('2015-10-14 21:40:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-10-14 21:40:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540029,TO_TIMESTAMP('2015-10-14 21:41:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'C_Invoice_Candidate - Create missing invoice candidates',1,TO_TIMESTAMP('2015-10-14 21:41:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540029,540053,540029,TO_TIMESTAMP('2015-10-14 21:41:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-10-14 21:41:15','YYYY-MM-DD HH24:MI:SS'),100)
;

