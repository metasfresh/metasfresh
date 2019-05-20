-- 2019-03-26T20:42:59.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,0,541084,1000000,550051,TO_TIMESTAMP('2019-03-26 20:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Invokes the C_Invoice_ImportInvoiceResponse process every 10 minutes','de.metas.vertical.healthcare.forum_datenaustausch_ch',10,'M','Y','N',7,'N','C_Invoice_ImportInvoiceResponse','N','P','F','NEW',1000000,TO_TIMESTAMP('2019-03-26 20:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T20:44:42.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541383,550051,540002,TO_TIMESTAMP('2019-03-26 20:44:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','/var/metasfresh/forum_datenaustausch_ch/invoice_response/',TO_TIMESTAMP('2019-03-26 20:44:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T20:44:51.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541384,550051,540003,TO_TIMESTAMP('2019-03-26 20:44:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','*.xml',TO_TIMESTAMP('2019-03-26 20:44:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T20:45:22.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (1000000,0,541385,550051,540004,TO_TIMESTAMP('2019-03-26 20:45:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','/var/metasfresh/forum_datenaustausch_ch/invoice_response/imported',TO_TIMESTAMP('2019-03-26 20:45:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T20:45:51.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SchedulerRecipient (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Scheduler_ID,AD_SchedulerRecipient_ID,AD_User_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,1000000,550051,540002,100,TO_TIMESTAMP('2019-03-26 20:45:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-26 20:45:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T20:46:10.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2019-03-26 20:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550051
;

-- 2019-03-26T22:39:29.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET Description='Invokes the C_Invoice_ImportInvoiceResponse process every 10 minutes; Set sys config org.compiere.server.Scheduler.notifyOnNotOK=Y to make sure that the supervisor is notified in case of problems', Updated=TO_TIMESTAMP('2019-03-26 22:39:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550051
;
