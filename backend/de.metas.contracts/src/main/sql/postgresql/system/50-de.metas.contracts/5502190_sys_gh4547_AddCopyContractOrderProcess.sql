-- 2018-09-25T17:53:18.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541010,'Y','de.metas.contracts.flatrate.process.C_Order_Copy','N',TO_TIMESTAMP('2018-09-25 17:53:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','N','N','N','N','Y',0,'Vertrag Verlängern','N','Y','Java',TO_TIMESTAMP('2018-09-25 17:53:18','YYYY-MM-DD HH24:MI:SS'),100,'Extend contract')
;

-- 2018-09-25T17:53:18.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541010 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-09-25T17:53:25.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-25 17:53:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541010 AND AD_Language='de_CH'
;

-- 2018-09-25T17:53:29.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-25 17:53:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Extend contract' WHERE AD_Process_ID=541010 AND AD_Language='en_US'
;

-- 2018-09-25T17:53:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-25 17:53:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541010 AND AD_Language='nl_NL'
;

-- 2018-09-25T17:54:06.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,541010,259,143,TO_TIMESTAMP('2018-09-25 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2018-09-25 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

