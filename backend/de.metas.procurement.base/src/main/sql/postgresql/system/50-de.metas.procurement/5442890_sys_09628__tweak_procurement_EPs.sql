
-- 17.03.2016 16:16
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540983,'C',TO_TIMESTAMP('2016-03-17 16:16:33','YYYY-MM-DD HH24:MI:SS'),100,'The JMS queue which metasfresh uses to send JAX-RS requests to the procurement webui. Needs to be defined with AD_Client_ID=0 and AD_Org_ID=0!','de.metas.procurement','Y','de.metas.procurement.webui.jms.queue.request',TO_TIMESTAMP('2016-03-17 16:16:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs.procurement.webui.request')
;

-- 17.03.2016 16:17
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540985,'C',TO_TIMESTAMP('2016-03-17 16:17:29','YYYY-MM-DD HH24:MI:SS'),100,'The JMS queue which metasfresh uses to send JAX-RS requests to the procurement webui. Needs to be defined with AD_Client_ID=0 and AD_Org_ID=0!','de.metas.procurement','Y','de.metas.procurement.webui.jms.queue.response',TO_TIMESTAMP('2016-03-17 16:17:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs.procurement.webui.response')
;


-- 17.03.2016 22:16
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET Description='This endpoint invokes methods of the procurement webUI. We do *not* automatically create the endpoint here, because the ModuleInterceptor of de.metas.procurement.base creates it and makes sure it connects to those queues that are dedicated to communicating with the procurement webUI.', IsEndpointActive='N',Updated=TO_TIMESTAMP('2016-03-17 22:16:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540009
;

-- 17.03.2016 22:17
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET Description='Server endpoint, to be invoked by the procurement-webUI.
This endpoint invokes methods of the procurement webUI. We do *not* automatically create the endpoint here, because the ModuleInterceptor of de.metas.procurement.base creates it and makes sure it connects to those queues that are dedicated to communicating with the procurement webUI.', IsEndpointActive='N',Updated=TO_TIMESTAMP('2016-03-17 22:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540008
;
