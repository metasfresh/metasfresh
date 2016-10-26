
-- 25.10.2016 17:27
-- URL zum Konzept
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.dlm.partitioner.async.DLMPartitionerWorkpackageProcessor',540049,TO_TIMESTAMP('2016-10-25 17:27:50','YYYY-MM-DD HH24:MI:SS'),100,'See GitHub issue metasfresh/metasfresh#489','de.metas.dlm','DLMPartitionerWorkpackageProcessor','Y',TO_TIMESTAMP('2016-10-25 17:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.10.2016 17:28
-- URL zum Konzept
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540043,TO_TIMESTAMP('2016-10-25 17:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'DLMPartitionerWorkpackageProcessor',1,TO_TIMESTAMP('2016-10-25 17:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.10.2016 17:28
-- URL zum Konzept
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540049,540072,540043,TO_TIMESTAMP('2016-10-25 17:28:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-10-25 17:28:16','YYYY-MM-DD HH24:MI:SS'),100)
;

