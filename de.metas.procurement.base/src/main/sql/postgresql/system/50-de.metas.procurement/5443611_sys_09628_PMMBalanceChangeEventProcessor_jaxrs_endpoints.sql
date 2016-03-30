-- 29.03.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,5380,540045,540039,0,'de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor',TO_TIMESTAMP('2016-03-29 14:35:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor',TO_TIMESTAMP('2016-03-29 14:35:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.03.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,5380,540046,540039,0,'de.metas.procurement.base.balance.impl.PMMBalanceChangeEventProcessor',TO_TIMESTAMP('2016-03-29 14:36:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','de.metas.procurement.base.balance.impl.PMMBalanceChangeEventProcessor',TO_TIMESTAMP('2016-03-29 14:36:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.03.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,Description,EndpointType,EntityType,IsActive,IsEndpointActive,Updated,UpdatedBy) VALUES (0,540045,540010,0,TO_TIMESTAMP('2016-03-29 14:42:49','YYYY-MM-DD HH24:MI:SS'),100,'procurement: PMM_Balance event processor','C','de.metas.jax.rs','Y','Y',TO_TIMESTAMP('2016-03-29 14:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.03.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,Description,EndpointType,EntityType,IsActive,IsEndpointActive,Updated,UpdatedBy) VALUES (0,540046,540011,0,TO_TIMESTAMP('2016-03-29 14:43:14','YYYY-MM-DD HH24:MI:SS'),100,'procurement: PMM_Balance event processor (server)','S','de.metas.jax.rs','Y','Y',TO_TIMESTAMP('2016-03-29 14:43:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.03.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_JAXRS_Endpoint SET Description='procurement: PMM_Balance event processor (client)',Updated=TO_TIMESTAMP('2016-03-29 14:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540010
;

