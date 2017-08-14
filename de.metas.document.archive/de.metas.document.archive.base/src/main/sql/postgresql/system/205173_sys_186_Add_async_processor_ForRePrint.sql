-- 19.01.2017 19:07:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540752,'de.metas.mo73.printing.process.ReprintInvoiceProcess','N',TO_TIMESTAMP('2017-01-19 19:07:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.archive','Y','N','N','N','N','N',0,'Reprint Invoices','S',53,618,'Java',TO_TIMESTAMP('2017-01-19 19:07:30','YYYY-MM-DD HH24:MI:SS'),100,'ReprintInvoice')
--;

-- 19.01.2017 19:07:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540752 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
--;

-- 19.01.2017 19:09:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540752,540453,TO_TIMESTAMP('2017-01-19 19:09:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.archive','Y',TO_TIMESTAMP('2017-01-19 19:09:21','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- 19.01.2017 19:10:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) 
SELECT 0,0,540052,'de.metas.document.archive.async.spi.impl.RecreateArchiveWorkpackageProcessor',TO_TIMESTAMP('2017-01-19 19:10:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.archive','Y',TO_TIMESTAMP('2017-01-19 19:10:10','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from C_Queue_PackageProcessor where C_Queue_PackageProcessor_ID=540052);
;

-- 19.01.2017 19:10:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) 
SELECT 0,0,540045,TO_TIMESTAMP('2017-01-19 19:10:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'RecreateArchiveWorkpackageProcessor',1,TO_TIMESTAMP('2017-01-19 19:10:27','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from C_Queue_PackageProcessor where C_Queue_PackageProcessor_ID=540052);
;

-- 19.01.2017 19:10:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) 
SELECT 0,0,540052,540075,540045,TO_TIMESTAMP('2017-01-19 19:10:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2017-01-19 19:10:44','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from C_Queue_PackageProcessor where C_Queue_PackageProcessor_ID=540052);
;

-- 20.01.2017 10:17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Async_Batch_Type (AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,IsSendMail,IsSendNotification,KeepAliveTimeHours,NotificationType,SkipTimeoutMillis,Updated,UpdatedBy)
SELECT 0,0,540007,TO_TIMESTAMP('2017-01-20 10:17:02','YYYY-MM-DD HH24:MI:SS'),100,'ReCreatePDF','Y','N','N','24','ABP',0,TO_TIMESTAMP('2017-01-20 10:17:02','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from C_Async_Batch_Type where C_Async_Batch_Type_ID=540007);
;

update C_Async_Batch_Type set AD_Client_Id=1000000 where C_Async_Batch_Type_ID=540007
;

