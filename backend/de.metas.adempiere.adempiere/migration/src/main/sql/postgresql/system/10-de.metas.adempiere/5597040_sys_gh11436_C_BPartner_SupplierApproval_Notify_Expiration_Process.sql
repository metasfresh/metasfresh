-- 2021-07-06T11:10:08.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584856,'Y','N',TO_TIMESTAMP('2021-07-06 14:10:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'AD_UserGroup_NotifySupplierApproval','json','N','N','Java',TO_TIMESTAMP('2021-07-06 14:10:08','YYYY-MM-DD HH24:MI:SS'),100,'AD_UserGroup_NotifySupplierApproval')
;

-- 2021-07-06T11:10:08.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584856 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-07-06T13:30:31.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541382,'O',TO_TIMESTAMP('2021-07-06 16:30:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID',TO_TIMESTAMP('2021-07-06 16:30:31','YYYY-MM-DD HH24:MI:SS'),100,'540000')
;

-- 2021-07-06T16:11:18.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.C_BP_SupplierApproval_Expiration_Notify',Updated=TO_TIMESTAMP('2021-07-06 19:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584856
;

-- 2021-07-07T09:06:02.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Process used for updating the supplier approval duration and the supplier approval date for a business partner''s supplier approval.',Updated=TO_TIMESTAMP('2021-07-07 12:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584855
;

-- 2021-07-07T09:58:58.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545045,0,TO_TIMESTAMP('2021-07-07 12:58:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The supplier approval of the business partner {} for the norm {} expires on {}.','I',TO_TIMESTAMP('2021-07-07 12:58:58','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Supplier_Approval_WillExpire')
;

-- 2021-07-07T09:58:58.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545045 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-07-07T10:01:41.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The supplier approval of the business partner {0} for the norm {1} expires on {2}.',Updated=TO_TIMESTAMP('2021-07-07 13:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545045
;

-- 2021-07-07T10:03:46.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='C_BP_SupplierApproval_Expiration_Notify', Value='C_BP_SupplierApproval_Expiration_Notify',Updated=TO_TIMESTAMP('2021-07-07 13:03:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584856
;

-- 2021-07-07T10:10:48.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584856,0,550077,TO_TIMESTAMP('2021-07-07 13:10:47','YYYY-MM-DD HH24:MI:SS'),100,'00 23 * * *','de.metas.swat',0,'D','Y','N',7,'N','Supplier Approval Expiration Notify','N','P','C','NEW',100,TO_TIMESTAMP('2021-07-07 13:10:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-07T10:12:08.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET Description='This process is scheduled to be run each night to check the dupplier approval dates of all the vendor supplier approvals and notify the ure group in charge if they are about to expire.',Updated=TO_TIMESTAMP('2021-07-07 13:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550077
;

-- 2021-07-07T10:12:25.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET Description='This process is scheduled to be run each night to check the supplier approval dates of all the vendor supplier approvals and notify the user group in charge if they are about to expire.',Updated=TO_TIMESTAMP('2021-07-07 13:12:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550077
;


-- -- 2021-07-07T10:15:33.608Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584856,291,540954,TO_TIMESTAMP('2021-07-07 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-07-07 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
-- ;

-- 2021-07-07T10:21:25.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545046,0,TO_TIMESTAMP('2021-07-07 13:21:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The partner {0} doesn''t have a supplier approval for the norm {1}','I',TO_TIMESTAMP('2021-07-07 13:21:25','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Lacks_Supplier_Approval')
;

-- 2021-07-07T10:21:25.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545046 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;




