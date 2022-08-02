-- 2021-12-14T22:29:39.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584951,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2021-12-14 23:29:38','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','N','N','N','N','N','N','N','Y','Y',0,'Asynchrone Warteschlangendatensätze archivieren','json','Y','S','select dlm.archive_c_queue_data(30, 100000);','SQL',TO_TIMESTAMP('2021-12-14 23:29:38','YYYY-MM-DD HH24:MI:SS'),100,'Run_DLM_Archive_C_Queue_Data')
;

-- 2021-12-14T22:29:39.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584951 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-12-14T22:30:05.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Archive async queue records',Updated=TO_TIMESTAMP('2021-12-14 23:30:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584951
;


-- 2021-12-14T22:35:40.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584951,0,550082,TO_TIMESTAMP('2021-12-14 23:35:40','YYYY-MM-DD HH24:MI:SS'),100,'0 0 * * *','D',0,'D','Y','N',7,'N','Run_DLM_Archive_C_Queue_Data','N','P','C','NEW',100,TO_TIMESTAMP('2021-12-14 23:35:40','YYYY-MM-DD HH24:MI:SS'),100)
;

