-- 2021-03-20T22:44:07.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584814,'Y','de.metas.contracts.bpartner.process.C_BPartner_DeactivateAfterOrgChange','N',TO_TIMESTAMP('2021-03-21 00:44:07','YYYY-MM-DD HH24:MI:SS'),100,'Deactivate partners,users, locations and bank accounts if they were moved to another org and the date for the org switch is today.','D','Deactivate partners,users, locations and bank accounts if they were moved to another org and the date for the org switch is today.','Y','N','N','N','N','N','N','Y','Y',0,'Deactivate partners after org change','json','N','N','Java',TO_TIMESTAMP('2021-03-21 00:44:07','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_DeactivateAfterOrgChange')
;

-- 2021-03-20T22:44:07.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584814 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-03-20T22:46:14.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584814,0,550070,TO_TIMESTAMP('2021-03-21 00:46:14','YYYY-MM-DD HH24:MI:SS'),100,'00 23 * * *','D',1,'D','Y','N',7,'N','C_BPartner_DeactivateAfterOrgChange','N','P','C','NEW',100,TO_TIMESTAMP('2021-03-21 00:46:14','YYYY-MM-DD HH24:MI:SS'),100)
;

