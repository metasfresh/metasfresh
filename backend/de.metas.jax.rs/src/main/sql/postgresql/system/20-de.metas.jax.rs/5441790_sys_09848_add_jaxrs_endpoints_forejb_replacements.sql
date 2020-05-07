
--
-- then endpointws which replace Server.java
--


-- 04.03.2016 11:33
-- URL zum Konzept
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540081,540035,540039,0,'de.metas.session.jaxrs.impl.StatusService',TO_TIMESTAMP('2016-03-04 11:33:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','de.metas.session.jaxrs.impl.StatusService',TO_TIMESTAMP('2016-03-04 11:33:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 11:33
-- URL zum Konzept
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540081,540036,540039,0,'de.metas.session.jaxrs.IStatusService',TO_TIMESTAMP('2016-03-04 11:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','de.metas.session.jaxrs.IStatusService',TO_TIMESTAMP('2016-03-04 11:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 04.03.2016 14:27
-- URL zum Konzept
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,EndpointType,IsActive,Updated,UpdatedBy) VALUES (0,540035,540000,0,TO_TIMESTAMP('2016-03-04 14:27:04','YYYY-MM-DD HH24:MI:SS'),100,'S','Y',TO_TIMESTAMP('2016-03-04 14:27:04','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 04.03.2016 14:27
-- URL zum Konzept
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,EndpointType,IsActive,Updated,UpdatedBy) VALUES (0,540036,540001,0,TO_TIMESTAMP('2016-03-04 14:27:04','YYYY-MM-DD HH24:MI:SS'),100,'C','Y',TO_TIMESTAMP('2016-03-04 14:27:04','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 04.03.2016 18:06
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET IsEndpointActive='Y',Updated=TO_TIMESTAMP('2016-03-04 18:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540001
;
-- 04.03.2016 16:48
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET IsEndpointActive='Y',Updated=TO_TIMESTAMP('2016-03-04 16:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540000
;



--
-- the endpoints which replace Server.java (client&server)
--

-- 04.03.2016 22:33
-- URL zum Konzept
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540081,540037,540039,0,'de.metas.session.jaxrs.impl.ServerService',TO_TIMESTAMP('2016-03-04 22:33:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','de.metas.session.jaxrs.impl.ServerService',TO_TIMESTAMP('2016-03-04 22:33:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 22:33
-- URL zum Konzept
INSERT INTO AD_JavaClass (AD_Client_ID,AD_EntityType_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540081,540038,540039,0,'de.metas.session.jaxrs.IServerService',TO_TIMESTAMP('2016-03-04 22:33:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','de.metas.session.jaxrs.IServerService',TO_TIMESTAMP('2016-03-04 22:33:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 22:35
-- URL zum Konzept
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,EndpointType,IsActive,Updated,UpdatedBy) VALUES (0,540037,540002,0,TO_TIMESTAMP('2016-03-04 22:35:14','YYYY-MM-DD HH24:MI:SS'),100,'S','Y',TO_TIMESTAMP('2016-03-04 22:35:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 22:35
-- URL zum Konzept
INSERT INTO AD_JAXRS_Endpoint (AD_Client_ID,AD_JavaClass_ID,AD_JAXRS_Endpoint_ID,AD_Org_ID,Created,CreatedBy,EndpointType,IsActive,Updated,UpdatedBy) VALUES (0,540038,540003,0,TO_TIMESTAMP('2016-03-04 22:35:14','YYYY-MM-DD HH24:MI:SS'),100,'C','Y',TO_TIMESTAMP('2016-03-04 22:35:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 22:51
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET IsEndpointActive='Y',Updated=TO_TIMESTAMP('2016-03-04 22:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540002
;
-- 04.03.2016 22:51
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET IsEndpointActive='Y',Updated=TO_TIMESTAMP('2016-03-04 22:51:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540003
;

