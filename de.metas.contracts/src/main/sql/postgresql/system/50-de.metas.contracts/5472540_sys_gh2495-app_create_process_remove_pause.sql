
-- 2017-09-25T16:19:35.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540852,'N','de.metas.contracts.subscription.process.C_SubscriptionProgress_RemovePauses','N',TO_TIMESTAMP('2017-09-25 16:19:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y','N','Y','N','N','N','N','Y','Abo-Lieferpause(n) entfernen','Y','Y','Java',TO_TIMESTAMP('2017-09-25 16:19:35','YYYY-MM-DD HH24:MI:SS'),100,'C_SubscriptionProgress_RemovePauses')
;

-- 2017-09-25T16:19:35.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540852 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-09-25T16:29:56.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540852,540029,TO_TIMESTAMP('2017-09-25 16:29:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y',TO_TIMESTAMP('2017-09-25 16:29:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

-- 2017-09-25T16:55:54.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Abo-Lieferpause entfernen',Updated=TO_TIMESTAMP('2017-09-25 16:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540852
;

-- 2017-09-25T16:55:58.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.subscription.process.C_SubscriptionProgress_RemovePause',Updated=TO_TIMESTAMP('2017-09-25 16:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540852
;

