
;

-- 21.07.2016 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540709,'Y','de.metas.acct.process.Documents_FactAcct_Creation_For_Posted','N',TO_TIMESTAMP('2016-07-21 18:35:59','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','N','N','N','Y',0,'.Documents_FactAcct_Creation_For_Posted','N','Y',0,0,'Java',TO_TIMESTAMP('2016-07-21 18:35:59','YYYY-MM-DD HH24:MI:SS'),100,'10000001')
;

-- 21.07.2016 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540709 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 21.07.2016 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Documents_FactAcct_Creation_For_Posted', Value='Documents_FactAcct_Creation_For_Posted',Updated=TO_TIMESTAMP('2016-07-21 18:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540709
;

-- 21.07.2016 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540709
;

-- 21.07.2016 18:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,540709,0,550039,TO_TIMESTAMP('2016-07-21 18:42:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.s',1,'D','Y','N',7,'N','Documents_FactAcct_Creation_For_Posted','N','P','F','NEW',0,TO_TIMESTAMP('2016-07-21 18:42:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 21.07.2016 18:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='30 1 * * *', ScheduleType='C',Updated=TO_TIMESTAMP('2016-07-21 18:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550039
;

