
-- 19.11.2015 13:36:49 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) 
select
	0,0,540032,'de.metas.printing.spi.impl.ProcessPrintingQueueWorkpackageProcessor',TO_TIMESTAMP('2015-11-19 13:36:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.archive','Y',TO_TIMESTAMP('2015-11-19 13:36:49','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from C_Queue_PackageProcessor where C_Queue_PackageProcessor_ID = 540032);
;

-- 19.11.2015 13:37:53 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Name,PoolSize,Priority,Updated,UpdatedBy) 
select
	0,0,540032,TO_TIMESTAMP('2015-11-19 13:37:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Process PrintingQueue Processor',1,'5',TO_TIMESTAMP('2015-11-19 13:37:53','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from C_Queue_PackageProcessor where C_Queue_PackageProcessor_ID = 540032);
;


-- 19.11.2015 13:38:05 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) 
select
	0,0,540032,540056,540032,TO_TIMESTAMP('2015-11-19 13:38:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-11-19 13:38:05','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from C_Queue_PackageProcessor where C_Queue_PackageProcessor_ID = 540032);
;


update C_Queue_PackageProcessor
set Classname = 'de.metas.document.archive.async.spi.impl.ProcessPrintingQueueWorkpackageProcessor', EntityType='de.metas.document.archive'
where C_Queue_PackageProcessor_ID = 540032;