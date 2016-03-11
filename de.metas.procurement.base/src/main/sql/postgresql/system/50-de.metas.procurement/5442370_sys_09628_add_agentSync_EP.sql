-- 11.03.2016 17:47
-- URL zum Konzept
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540081,540044,540039,0,'de.metas.procurement.base.IAgentSyncBL',TO_TIMESTAMP('2016-03-11 17:47:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','de.metas.procurement.base.IAgentSyncBL',TO_TIMESTAMP('2016-03-11 17:47:43','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 11.03.2016 17:48
-- URL zum Konzept
UPDATE AD_JavaClass SET AD_EntityType_ID=5380, IsInterface='Y',Updated=TO_TIMESTAMP('2016-03-11 17:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_ID=540044
;


-- 11.03.2016 17:49
-- URL zum Konzept
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,EndpointType,IsActive,Updated,UpdatedBy) VALUES (0,540044,540009,0,TO_TIMESTAMP('2016-03-11 17:49:51','YYYY-MM-DD HH24:MI:SS'),100,'C','Y',TO_TIMESTAMP('2016-03-11 17:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.03.2016 17:50
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET IsEndpointActive='Y',Updated=TO_TIMESTAMP('2016-03-11 17:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540009
;

-- 11.03.2016 17:50
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET Description=NULL, IsEndpointActive='N',Updated=TO_TIMESTAMP('2016-03-11 17:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540005
;

-- 11.03.2016 17:50
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET Description='This endpoint invokes methods of the procurement webUI',Updated=TO_TIMESTAMP('2016-03-11 17:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540009
;

-- 11.03.2016 17:51
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET Description='This interface is invoked by the procurement webUI. Not active because it doesn''t extend ISingletonService. Instead, we use IAgentSyncBL-',Updated=TO_TIMESTAMP('2016-03-11 17:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540005
;

