-- 2018-01-05T18:23:41.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (CreatedBy,IsActive,C_Queue_PackageProcessor_ID,AD_Client_ID,Classname,InternalName,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES (100,'Y',540054,0,'de.metas.shipper.gateway.go.async.GODeliveryOrderWorkpackageProcessor','GODeliveryOrderWorkpackageProcessor',0,'de.metas.shipper.gateway.go',100,TO_TIMESTAMP('2018-01-05 18:23:40','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-05 18:23:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T18:24:27.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (CreatedBy,IsActive,UpdatedBy,C_Queue_Processor_ID,AD_Client_ID,AD_Org_ID,Name,Created,KeepAliveTimeMillis,PoolSize,Updated) VALUES (100,'Y',100,540047,0,0,'GODeliveryOrderWorkpackageProcessor',TO_TIMESTAMP('2018-01-05 18:24:27','YYYY-MM-DD HH24:MI:SS'),1000,1,TO_TIMESTAMP('2018-01-05 18:24:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T18:24:47.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (CreatedBy,IsActive,C_Queue_Processor_ID,C_Queue_PackageProcessor_ID,UpdatedBy,C_Queue_Processor_Assign_ID,AD_Client_ID,AD_Org_ID,Created,Updated) VALUES (100,'Y',540047,540054,100,540077,0,0,TO_TIMESTAMP('2018-01-05 18:24:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-05 18:24:47','YYYY-MM-DD HH24:MI:SS'))
;

