--
-- create inactive AD_Scheduler
--

-- 2021-04-20T13:29:04.163Z
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,584802,540024,550071,TO_TIMESTAMP('2021-04-20 15:29:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',1,'M','Y','N',7,'Call_External_System_Alberta - getSalesOrders','N','P','F','NEW',540092,TO_TIMESTAMP('2021-04-20 15:29:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-20T13:29:33.076Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541931,550071,540027,TO_TIMESTAMP('2021-04-20 15:29:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','getSalesOrders',TO_TIMESTAMP('2021-04-20 15:29:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-20T13:29:44.217Z
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541932,550071,540028,TO_TIMESTAMP('2021-04-20 15:29:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','540000',TO_TIMESTAMP('2021-04-20 15:29:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-20T13:29:49.122Z
-- URL zum Konzept
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2021-04-20 15:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550071
;
