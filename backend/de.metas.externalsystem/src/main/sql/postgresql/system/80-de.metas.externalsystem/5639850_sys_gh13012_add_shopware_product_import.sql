-- 2022-05-19T09:02:10.069Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,584804,540024,550088,TO_TIMESTAMP('2022-05-19 11:02:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'M','Y','N',7,'N','Call_External_System_Shopware6 - getProducts','N','P','F','NEW',540091,TO_TIMESTAMP('2022-05-19 11:02:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-19T09:02:29.230Z
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541939,550088,540040,TO_TIMESTAMP('2022-05-19 11:02:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','getProducts',TO_TIMESTAMP('2022-05-19 11:02:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-19T09:02:38.488Z
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541940,550088,540041,TO_TIMESTAMP('2022-05-19 11:02:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','540000',TO_TIMESTAMP('2022-05-19 11:02:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-19T09:03:36.663Z
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2022-05-19 11:03:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550088
;

-- 2022-05-19T09:29:51.722Z
UPDATE AD_Scheduler SET CronPattern=NULL, Frequency=10,Updated=TO_TIMESTAMP('2022-05-19 11:29:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550069
;

