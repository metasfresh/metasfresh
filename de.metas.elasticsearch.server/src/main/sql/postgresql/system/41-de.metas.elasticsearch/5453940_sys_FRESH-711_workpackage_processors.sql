-- Nov 25, 2016 1:18 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540044,TO_TIMESTAMP('2016-11-25 13:18:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'Elasticsearch model indexer',1,TO_TIMESTAMP('2016-11-25 13:18:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2016 1:23 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540050,'de.metas.elasticsearch.scheduler.async.AsyncAddToIndexProcessor',TO_TIMESTAMP('2016-11-25 13:23:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.elasticsearch','de.metas.elasticsearch.scheduler.async.AsyncAddToIndexProcessor','Y',TO_TIMESTAMP('2016-11-25 13:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2016 1:23 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540051,'de.metas.elasticsearch.scheduler.async.AsyncRemoveFromIndexProcessor',TO_TIMESTAMP('2016-11-25 13:23:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.elasticsearch','de.metas.elasticsearch.scheduler.async.AsyncRemoveFromIndexProcessor','Y',TO_TIMESTAMP('2016-11-25 13:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2016 1:24 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540050,540073,540044,TO_TIMESTAMP('2016-11-25 13:24:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-11-25 13:24:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2016 1:24 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540051,540074,540044,TO_TIMESTAMP('2016-11-25 13:24:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-11-25 13:24:37','YYYY-MM-DD HH24:MI:SS'),100)
;

