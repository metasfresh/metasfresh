-- 2017-04-24T17:41:17.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540780,'N','de.metas.ui.web.request.process.WEBUI_CreateRequest','N',TO_TIMESTAMP('2017-04-24 17:41:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Create request','N','N','Java',TO_TIMESTAMP('2017-04-24 17:41:16','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_CreateRequest')
;

-- 2017-04-24T17:41:17.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540780 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-04-24T17:41:35.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540780,291,TO_TIMESTAMP('2017-04-24 17:41:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2017-04-24 17:41:35','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

