-- 2018-06-06T01:38:15.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540976,'N','de.metas.letter.process.C_Letter_CreateFrom_MKTG_ContactPerson','N',TO_TIMESTAMP('2018-06-06 01:38:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.serialletter','Y','N','N','N','N','N','N','Y',0,'Create letters','Y','Y','Java',TO_TIMESTAMP('2018-06-06 01:38:15','YYYY-MM-DD HH24:MI:SS'),100,'C_Letter_CreateFrom_MKTG_ContactPerson')
;

-- 2018-06-06T01:38:15.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540976 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-06T01:38:43.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540976,540970,TO_TIMESTAMP('2018-06-06 01:38:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.serialletter','Y',TO_TIMESTAMP('2018-06-06 01:38:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-06-06T01:38:48.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2018-06-06 01:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540976 AND AD_Table_ID=540970
;



