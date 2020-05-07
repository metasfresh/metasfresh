-- 29.02.2016 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540039,'de.metas.procurement.base.async.PMM_GenerateOrders',TO_TIMESTAMP('2016-02-29 16:01:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','de.metas.procurement.base.async.PMM_GenerateOrders','Y',TO_TIMESTAMP('2016-02-29 16:01:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.02.2016 16:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540039,TO_TIMESTAMP('2016-02-29 16:02:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'Procurement - generate orders',1,TO_TIMESTAMP('2016-02-29 16:02:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.02.2016 16:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540039,540063,540039,TO_TIMESTAMP('2016-02-29 16:02:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-02-29 16:02:26','YYYY-MM-DD HH24:MI:SS'),100)
;

