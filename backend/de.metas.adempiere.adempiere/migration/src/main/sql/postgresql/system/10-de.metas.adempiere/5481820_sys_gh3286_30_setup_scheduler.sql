-- 2018-01-06T16:55:59.915
-- URL zum Konzept
INSERT INTO AD_Scheduler (Created,KeepLogDays,AD_Client_ID,Supervisor_ID,CreatedBy,FrequencyType,IsActive,AD_Org_ID,AD_Process_ID,Processing,IsIgnoreProcessingTime,Frequency,ScheduleType,ManageScheduler,SchedulerProcessType,CronPattern,AD_Scheduler_ID,AD_Role_ID,Status,EntityType,Description,Name,Updated,UpdatedBy) VALUES (TO_TIMESTAMP('2018-01-06 16:55:59','YYYY-MM-DD HH24:MI:SS'),7,1000000,0,100,'M','Y',0,540909,'N','N',1,'C','N','P','00 00 * * *',550041,1000000,'NEW','de.metas.swat','deletes tables t_webui_viewselection and _line every night at 00:00','truncate t_webui_viewselection',TO_TIMESTAMP('2018-01-06 16:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

