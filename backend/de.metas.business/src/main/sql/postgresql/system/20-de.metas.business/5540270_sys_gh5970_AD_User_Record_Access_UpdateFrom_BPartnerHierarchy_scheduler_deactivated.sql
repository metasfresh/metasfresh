-- 2020-01-04T11:29:37.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,0,550057,TO_TIMESTAMP('2020-01-04 13:29:36','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'D','Y','N',7,'N','AD_User_Record_Access_UpdateFrom_BPartnerHierarchy','N','P','F','NEW',100,TO_TIMESTAMP('2020-01-04 13:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-04T11:29:47.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2020-01-04 13:29:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550057
;

update ad_scheduler set ad_process_id=541239 where ad_scheduler_id=550057
;

