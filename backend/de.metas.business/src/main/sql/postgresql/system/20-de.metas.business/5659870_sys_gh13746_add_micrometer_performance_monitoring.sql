-- 2022-10-24T09:38:30.222Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541525,'S',TO_TIMESTAMP('2022-10-24 11:38:30.207','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.monitoring.annotation.enable',TO_TIMESTAMP('2022-10-24 11:38:30.207','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- 2022-10-24T09:38:52.075Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Switch for Monitoring-Annotation used in RestControllers',Updated=TO_TIMESTAMP('2022-10-24 11:38:52.075','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541525
;

-- 2022-10-24T09:38:56.672Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-10-24 11:38:56.672','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541525
;

-- 2022-10-24T09:39:27.815Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541526,'S',TO_TIMESTAMP('2022-10-24 11:39:27.809','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.monitoring.po.enable',TO_TIMESTAMP('2022-10-24 11:39:27.809','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- 2022-10-24T09:39:35.728Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Switch for Monitoring PO functions',Updated=TO_TIMESTAMP('2022-10-24 11:39:35.728','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541526
;

-- 2022-10-24T09:39:51.815Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-10-24 11:39:51.815','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541526
;

-- 2022-10-24T09:40:17.383Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541527,'S',TO_TIMESTAMP('2022-10-24 11:40:17.379','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.monitoring.modelInterceptor.enable',TO_TIMESTAMP('2022-10-24 11:40:17.379','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- 2022-10-24T09:40:29.897Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-10-24 11:40:29.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541527
;

-- 2022-10-24T09:40:46.870Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Switch for Monitoring MODEL_INTERCEPTOR functions',Updated=TO_TIMESTAMP('2022-10-24 11:40:46.87','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541527
;

-- 2022-10-25T08:18:22.166Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='When an event is received via RabbitMQ we invoke the performance monitor such that tracing infos are added to the event.
Note that currently, the sysconfig "de.metas.event.asyncEventBus" or "de.metas.event.asyncEventBus.topic_<topicName>" needs to be N for the respective event''s processing to be monitored.',Updated=TO_TIMESTAMP('2022-10-25 10:18:22.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2022-10-25T08:18:28.129Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='N',Updated=TO_TIMESTAMP('2022-10-25 10:18:28.129','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2022-10-25T08:19:49.976Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.monitoring.eventBus.enable',Updated=TO_TIMESTAMP('2022-10-25 10:19:49.975','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541322
;

-- 2022-10-25T08:25:09.956Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541528,'S',TO_TIMESTAMP('2022-10-25 10:25:09.951','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.monitoring.docAction.enable',TO_TIMESTAMP('2022-10-25 10:25:09.951','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- 2022-10-25T08:25:34.114Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Switch for Monitoring DOC_ACTION functions',Updated=TO_TIMESTAMP('2022-10-25 10:25:34.114','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541528
;

-- 2022-10-25T08:25:44.327Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-10-25 10:25:44.327','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541528
;

-- 2022-10-25T08:26:12.497Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541529,'S',TO_TIMESTAMP('2022-10-25 10:26:12.493','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.monitoring.scheduler.enable',TO_TIMESTAMP('2022-10-25 10:26:12.493','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- 2022-10-25T08:26:21.205Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Switch for Monitoring SCHEDULER functions',Updated=TO_TIMESTAMP('2022-10-25 10:26:21.205','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541529
;

-- 2022-10-25T08:32:03.708Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-10-25 10:32:03.708','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541529
;

-- 2022-10-25T08:26:49.642Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541530,'S',TO_TIMESTAMP('2022-10-25 10:26:49.636','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.monitoring.asyncWorkpackage.enable',TO_TIMESTAMP('2022-10-25 10:26:49.636','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- 2022-10-25T08:27:12.037Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Switch for Monitoring ASYNC_WORKPACKAGE functions',Updated=TO_TIMESTAMP('2022-10-25 10:27:12.036','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541530
;

-- 2022-10-25T08:29:23.668Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-10-25 10:29:23.668','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541530
;

