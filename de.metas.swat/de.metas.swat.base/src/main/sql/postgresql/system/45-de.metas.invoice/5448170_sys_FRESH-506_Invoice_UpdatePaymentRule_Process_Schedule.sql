
-- 12.07.2016 15:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540701,'Y','de.metas.adempiere.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2016-07-12 15:50:25','YYYY-MM-DD HH24:MI:SS'),100,'Task FRESH-506 : Change all invoices'' PaymentRule values from ''S'' (Check) to ''P'' (OnCredit)','de.metas.invoice','Y','N','N','N','N','N','N','Y',0,'C_Invoice_Update_PaymentRule','N','Y','INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID) 
SELECT C_Invoice_Candidate_ID FROM C_Invoice_Candidate WHERE Processed=''N''',0,0,'SQL',TO_TIMESTAMP('2016-07-12 15:50:25','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Update_PaymentRule')
;

-- 12.07.2016 15:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540701 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 12.07.2016 15:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='7',Updated=TO_TIMESTAMP('2016-07-12 15:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540701
;






-- 12.07.2016 16:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='test', SQLStatement='select C_Invoice_Update_PaymentRule()',Updated=TO_TIMESTAMP('2016-07-12 16:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540701
;

-- 12.07.2016 16:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select C_Invoice_Update_PaymentRule();',Updated=TO_TIMESTAMP('2016-07-12 16:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540701
;

-- 12.07.2016 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,0,550037,TO_TIMESTAMP('2016-07-12 17:04:28','YYYY-MM-DD HH24:MI:SS'),100,'30 23 * * *','Task FRESH-506 : Change all invoices'' PaymentRule values from ''S'' (Check) to ''P'' (OnCredit)','de.metas.s',1,'D','Y','N',7,'N','C_Invoice_Update_PaymentRule','N','P','C','NEW',0,TO_TIMESTAMP('2016-07-12 17:04:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.07.2016 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2016-07-12 17:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550037
;

-- 12.07.2016 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='08 17 * * *',Updated=TO_TIMESTAMP('2016-07-12 17:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550037
;







-- 12.07.2016 17:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport=NULL,Updated=TO_TIMESTAMP('2016-07-12 17:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540701
;

-- 12.07.2016 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,540701,0,550038,TO_TIMESTAMP('2016-07-12 17:14:08','YYYY-MM-DD HH24:MI:SS'),100,'17 17 * * *','Task FRESH-506 : Change all invoices'' PaymentRUle values from ''S'' (Check) to ''P'' (OnCredit)','de.metas.s',1,'D','Y','N',7,'N','C_Invoice_Update_PaymentRule','N','P','C','NEW',0,TO_TIMESTAMP('2016-07-12 17:14:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.07.2016 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Scheduler WHERE AD_Scheduler_ID=550037
;

-- 12.07.2016 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2016-07-12 17:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550038
;


-- 12.07.2016 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='30 23 * * *',Updated=TO_TIMESTAMP('2016-07-12 17:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550038
;


