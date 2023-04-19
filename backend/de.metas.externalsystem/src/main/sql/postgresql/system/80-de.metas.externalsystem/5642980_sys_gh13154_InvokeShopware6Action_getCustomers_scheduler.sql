-- 2022-06-10T12:57:12.278Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,584804,540024,550095,TO_TIMESTAMP('2022-06-10 15:57:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',7,'D','Y','N',7,'Call_External_System_Shopware6 - getCustomers','N','P','F','NEW',540091,TO_TIMESTAMP('2022-06-10 15:57:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-10T12:57:29.329Z
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541940,550095,540042,TO_TIMESTAMP('2022-06-10 15:57:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-06-10 15:57:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-10T12:57:33.723Z
UPDATE AD_Scheduler_Para SET ParameterDefault='540000',Updated=TO_TIMESTAMP('2022-06-10 15:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540042
;

-- 2022-06-10T12:57:44.255Z
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,541939,550095,540043,TO_TIMESTAMP('2022-06-10 15:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-06-10 15:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-10T12:57:51.514Z
UPDATE AD_Scheduler_Para SET ParameterDefault='getCustomers',Updated=TO_TIMESTAMP('2022-06-10 15:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540043
;

-- 2022-06-10T12:58:02.494Z
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2022-06-10 15:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550095
;


update externalsystem_config_shopware6
set jsonpathmetasfreshid=replace(jsonpathmetasfreshid, '/orderCustomer', ''),
    jsonpathshopwareid=replace(jsonpathshopwareid, '/orderCustomer', ''),
    Updated=now(),
    UpdatedBy=99
;
