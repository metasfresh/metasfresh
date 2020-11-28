-- 17.02.2016 13:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (1000000,0,'de.metas.migration.async.ExecuteSQLWorkpackageProcessor',540038,TO_TIMESTAMP('2016-02-17 13:11:25','YYYY-MM-DD HH24:MI:SS'),100,'Processor used to execute a given SQL code and if there were some records updated, re-enqueue a new workpackage to execute it again, until nothing left.','de.metas.async','de.metas.migration.async.ExecuteSQLWorkpackageProcessor','Y',TO_TIMESTAMP('2016-02-17 13:11:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2016 13:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (1000000,0,540038,TO_TIMESTAMP('2016-02-17 13:11:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'ExecuteSQLWorkpackageProcessor',1,TO_TIMESTAMP('2016-02-17 13:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2016 13:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540038,540062,540038,TO_TIMESTAMP('2016-02-17 13:12:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-02-17 13:12:36','YYYY-MM-DD HH24:MI:SS'),100)
;

