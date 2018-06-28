-- 2018-06-27T14:56:04.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540986,'Y','de.metas.event.log.process.AD_EventLog_Entry_RepostEvent','N',TO_TIMESTAMP('2018-06-27 14:56:04','YYYY-MM-DD HH24:MI:SS'),100,'The event will be posted with the same UUID, so any event logging will be done to this event log record.
The reposted event will carry the additional information which event handlers already processed it successfully in the past. 
But the currently selected entry''s handler will not be in that list, so it will be processed again!
If the the event was posted to a local topic, it will once again be posted to a local topic, but this time local on the machine this process runs on.','de.metas.event','Y','N','N','N','N','N','N','Y',0,'Repost logged event for entry','N','Y','Java',TO_TIMESTAMP('2018-06-27 14:56:04','YYYY-MM-DD HH24:MI:SS'),100,'AD_EventLog_Entry_RepostEvent')
;

-- 2018-06-27T14:56:04.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540986 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-27T14:56:18.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540986,540889,TO_TIMESTAMP('2018-06-27 14:56:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.event','Y',TO_TIMESTAMP('2018-06-27 14:56:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-06-27T15:10:16.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Repost logged event force reprocess',Updated=TO_TIMESTAMP('2018-06-27 15:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540986
;

-- 2018-06-27T15:10:54.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Repost event force selected handler reprocess',Updated=TO_TIMESTAMP('2018-06-27 15:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540986
;

-- 2018-06-27T15:10:59.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Repost event',Updated=TO_TIMESTAMP('2018-06-27 15:10:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540905
;

