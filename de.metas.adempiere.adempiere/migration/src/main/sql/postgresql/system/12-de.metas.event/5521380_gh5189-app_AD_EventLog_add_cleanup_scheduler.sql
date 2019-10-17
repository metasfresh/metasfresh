-- 2019-05-10T23:23:10.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (Created,KeepLogDays,AD_Client_ID,CreatedBy,FrequencyType,Updated,UpdatedBy,IsActive,AD_Org_ID,AD_Process_ID,Processing,IsIgnoreProcessingTime,Frequency,ScheduleType,SchedulerProcessType,ManageScheduler,CronPattern,AD_Scheduler_ID,AD_Role_ID,Status,EntityType,Name,Supervisor_ID,Description) VALUES (TO_TIMESTAMP('2019-05-10 23:23:09','YYYY-MM-DD HH24:MI:SS'),7,0,100,'D',TO_TIMESTAMP('2019-05-10 23:23:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,540906,'N','N',0,'C','P','N','05 00 * * *',550052,0,'NEW','de.metas.swat','AD_EventLog_DeleteOldRecords_Delete old event log records',100,'Deletes AD_EventLog records that had no (unaknowledged) error and are older than 30 days')
;

-- 2019-05-10T23:23:20.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Process_Para_ID,IsActive,CreatedBy,Created,AD_Client_ID,AD_Scheduler_ID,ParameterDefault,AD_Scheduler_Para_ID,UpdatedBy,Updated,AD_Org_ID) VALUES (541257,'Y',100,TO_TIMESTAMP('2019-05-10 23:23:20','YYYY-MM-DD HH24:MI:SS'),0,550052,'30',540005,100,TO_TIMESTAMP('2019-05-10 23:23:20','YYYY-MM-DD HH24:MI:SS'),0)
;

