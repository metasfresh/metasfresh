-- 2019-05-21T14:23:51.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,0,550053,TO_TIMESTAMP('2019-05-21 14:23:51','YYYY-MM-DD HH24:MI:SS'),100,'At 01:14:02am every day','de.metas.handlingunits',1,'D','Y','N',7,'N','M_HU_UpdateHUAgeAttributeProcess','N','P','F','NEW',100,TO_TIMESTAMP('2019-05-21 14:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T14:29:00.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='14 01 * * *', Description='At 01:14am every day', ScheduleType='C',Updated=TO_TIMESTAMP('2019-05-21 14:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550053
;

-- 2019-05-21T14:39:04.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541135,'Y','de.metas.handlingunits.age.process.M_HU_UpdateHUAgeAttributeProcess','N',TO_TIMESTAMP('2019-05-21 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.handlingunits','Y','N','N','N','N','N','N','Y','Y',0,'Update HUAge Attribute depending on ProductionDate Attribute','N','N','Java',TO_TIMESTAMP('2019-05-21 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_UpdateHUAgeAttributeProcess')
;

-- 2019-05-21T14:39:04.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541135 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-05-21T14:41:46.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET AD_Process_ID=541135,Updated=TO_TIMESTAMP('2019-05-21 14:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550053
;

-- @TheBestPessimist W/O this the scheduled process didn't work with customer data :(
UPDATE ad_scheduler SET ad_client_id=1000000 WHERE ad_scheduler_id = 550053;
