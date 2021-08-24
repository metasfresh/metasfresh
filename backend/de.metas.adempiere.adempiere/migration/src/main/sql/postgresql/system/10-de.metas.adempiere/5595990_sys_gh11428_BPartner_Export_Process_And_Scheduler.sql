
-- 2021-06-30T14:52:54.266Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584851,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2021-06-30 17:52:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'Update Partner Export','json','N','N','Select Update_C_BPartner_Export();','SQL',TO_TIMESTAMP('2021-06-30 17:52:53','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Export_Update')
;

-- 2021-06-30T14:52:54.301Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584851 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-06-30T15:08:25.265Z
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584851,0,550075,TO_TIMESTAMP('2021-06-30 18:08:24','YYYY-MM-DD HH24:MI:SS'),100,'30 00 * * *','Runs at 00:30 every night to update the table C_BPartner_Export.','de.metas.swat',0,'D','Y','N',7,'N','Update_C_BPartner_Export','N','P','C','NEW',100,TO_TIMESTAMP('2021-06-30 18:08:24','YYYY-MM-DD HH24:MI:SS'),100)
;

