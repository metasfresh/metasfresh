-- 2022-06-08T07:06:38.239484300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585066,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2022-06-08 10:06:38','YYYY-MM-DD HH24:MI:SS'),100,'Aktualisiert Statisktik nach Mengen Gesamt Woche ','D','','Y','N','N','N','Y','N','N','N','Y','Y',0,'Update fresh statistics kg week report','json','Y','Y','Select report.update_fresh_statistics_kg_week();','SQL',TO_TIMESTAMP('2022-06-08 10:06:38','YYYY-MM-DD HH24:MI:SS'),100,'update_fresh_statistics_kg_week')
;

-- 2022-06-08T07:06:38.245708500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585066 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-06-08T07:07:54.218131600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,585066,0,550090,TO_TIMESTAMP('2022-06-08 10:07:54','YYYY-MM-DD HH24:MI:SS'),100,'50 00 * * *','Runs at 00:50 every night to update the table fresh_statistics_kg_week_MV.','de.metas.swat',0,'D','Y','N',7,'N','update_fresh_statistics_kg_week','N','P','C','NEW',100,TO_TIMESTAMP('2022-06-08 10:07:54','YYYY-MM-DD HH24:MI:SS'),100)
