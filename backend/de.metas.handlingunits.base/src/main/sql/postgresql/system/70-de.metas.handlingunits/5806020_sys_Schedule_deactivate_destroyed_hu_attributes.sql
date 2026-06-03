-- Weekly AD_Scheduler to run dlm.deactivate_destroyed_hu_attributes (function in 5806010). Mirrors
-- the DLM archive_c_queue_data scheduler pattern (ExecuteUpdateSQL + AD_Scheduler).
-- Cron '0 2 * * 0' = every Sunday 02:00. SQLStatement processes up to 100000 destroyed HUs per run
-- (weekly delta; the one-time backlog is handled by a manual operational runbook).

-- --- AD_Process (runs the function via de.metas.process.ExecuteUpdateSQL) ---
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585630 /*From ID Server*/,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2026-06-03 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','N','N','N','N','N','N','N','Y','Y',0,'HU-Attribute zerstörter HUs deaktivieren','json','Y','S','select dlm.deactivate_destroyed_hu_attributes(100000);','SQL',TO_TIMESTAMP('2026-06-03 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Deactivate_Destroyed_HU_Attributes')
;

-- seed AD_Process_Trl for all system languages (copies the German base text)
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585630
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- English override
UPDATE AD_Process_Trl SET Name='Deactivate attributes of destroyed HUs', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-03 14:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585630
;

-- --- AD_Scheduler (weekly: Sunday 02:00) ---
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy)
VALUES (0,0,585630 /*From ID Server*/,0,550125 /*From ID Server*/,TO_TIMESTAMP('2026-06-03 14:00:02','YYYY-MM-DD HH24:MI:SS'),100,'0 2 * * 0','D',0,'D','Y','N',7,'N','Deactivate_Destroyed_HU_Attributes','N','N','C','NEW',100,TO_TIMESTAMP('2026-06-03 14:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
