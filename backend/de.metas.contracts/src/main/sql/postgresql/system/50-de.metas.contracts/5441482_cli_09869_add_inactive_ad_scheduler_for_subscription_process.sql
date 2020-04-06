
-- 03.03.2016 10:55
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,0,540003,1000000,550035,TO_TIMESTAMP('2016-03-03 10:55:36','YYYY-MM-DD HH24:MI:SS'),100,'0 3 * * *',0,'H','Y','N',30,'N','Abolieferplan und Lieferdispo aktualisieren','N','P','C','NEW',0,TO_TIMESTAMP('2016-03-03 10:55:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- this shall allow us to switch it on and of as we whish
UPDATE AD_Scheduler SET EntityType='de.metas.contracts.subscription' WHERE AD_Scheduler_ID=550035;
