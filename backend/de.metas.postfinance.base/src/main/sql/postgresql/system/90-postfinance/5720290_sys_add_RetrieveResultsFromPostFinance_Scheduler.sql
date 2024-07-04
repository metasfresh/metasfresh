
-- 2024-03-26T17:48:10.654Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,585366,540024,550114,TO_TIMESTAMP('2024-03-26 19:48:10.646','YYYY-MM-DD HH24:MI:SS.US'),100,'30 7 * * *','de.metas.postfinance',0,'Y','N',7,'C_Doc_Outbound_Log_RetrieveResultsFromPostFinance','N','P','C','NEW',100,TO_TIMESTAMP('2024-03-26 19:48:10.646','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-26T17:48:58.300Z
UPDATE AD_Scheduler SET Description='Retrieve results from PostFinance every morning at 07:30',Updated=TO_TIMESTAMP('2024-03-26 19:48:58.3','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Scheduler_ID=550114
;

-- 2024-03-26T17:49:02.936Z
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,542794,550114,540051,TO_TIMESTAMP('2024-03-26 19:49:02.934','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',TO_TIMESTAMP('2024-03-26 19:49:02.934','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-26T17:49:10.724Z
UPDATE AD_Scheduler_Para SET ParameterDefault='1000000',Updated=TO_TIMESTAMP('2024-03-26 19:49:10.724','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540051
;


-- 2024-03-26T17:51:42.420Z
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2024-03-26 19:51:42.42','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Scheduler_ID=550114
;