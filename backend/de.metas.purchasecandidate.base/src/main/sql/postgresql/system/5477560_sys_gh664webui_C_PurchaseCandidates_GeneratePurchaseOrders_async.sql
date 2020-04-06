-- 2017-11-20T09:57:21.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (Created,CreatedBy,IsActive,Updated,C_Queue_PackageProcessor_ID,AD_Client_ID,Description,Classname,InternalName,AD_Org_ID,EntityType,UpdatedBy) VALUES (TO_TIMESTAMP('2017-11-20 09:57:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2017-11-20 09:57:21','YYYY-MM-DD HH24:MI:SS'),540053,0,'Generates purchase order from enqueued purchase candidates','de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders','C_PurchaseCandidates_GeneratePurchaseOrders',0,'de.metas.purchasecandidate',100)
;

-- 2017-11-20T09:58:03.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (Created,CreatedBy,IsActive,UpdatedBy,KeepAliveTimeMillis,PoolSize,C_Queue_Processor_ID,AD_Client_ID,AD_Org_ID,Name,Updated) VALUES (TO_TIMESTAMP('2017-11-20 09:58:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,1000,1,540046,0,0,'C_PurchaseCandidates_GeneratePurchaseOrders',TO_TIMESTAMP('2017-11-20 09:58:03','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-11-20T09:58:18.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (Created,CreatedBy,IsActive,Updated,UpdatedBy,C_Queue_Processor_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,AD_Client_ID,AD_Org_ID) VALUES (TO_TIMESTAMP('2017-11-20 09:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2017-11-20 09:58:18','YYYY-MM-DD HH24:MI:SS'),100,540046,540053,540076,0,0)
;

