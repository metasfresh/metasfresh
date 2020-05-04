-- 2020-04-16T13:26:43.744Z
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,540724,550060,TO_TIMESTAMP('2020-04-16 15:26:43','YYYY-MM-DD HH24:MI:SS'),100,'20 00 * * *','Runs at 00:20 every night, to refresh materialized view MV_Fact_Acct_Sum','D',10,'M','Y','N',7,'N','Refresh MV_Fact_Acct_Sum','N','P','C','NEW',100,TO_TIMESTAMP('2020-04-16 15:26:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T13:34:03.217Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,584685,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2020-04-16 15:34:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','Y',0,'Refresh MV_Fact_Acct_Sum','N','N','REFRESH MATERIALIZED VIEW MV_Fact_Acct_Sum;','SQL',TO_TIMESTAMP('2020-04-16 15:34:03','YYYY-MM-DD HH24:MI:SS'),100,'MV_Fact_Acct_Sum')
;

-- 2020-04-16T13:34:03.223Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584685 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-04-16T13:36:50.486Z
-- URL zum Konzept
UPDATE AD_Scheduler SET AD_Process_ID=584685,Updated=TO_TIMESTAMP('2020-04-16 15:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550060
;

