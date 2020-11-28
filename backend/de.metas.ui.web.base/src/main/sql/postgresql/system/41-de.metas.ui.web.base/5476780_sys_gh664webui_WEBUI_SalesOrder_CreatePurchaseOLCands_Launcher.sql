-- 2017-11-09T14:42:03.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,Updated,UpdatedBy) VALUES (0,'Y',TO_TIMESTAMP('2017-11-09 14:42:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','WEBUI_SalesOrder_CreatePurchaseOLCands_Launcher','3','N','N','N','N',540887,'N','Y','N','Java','N','N',0,0,'Create purchase orders','de.metas.ui.web.order.purchase.process.WEBUI_SalesOrder_CreatePurchaseOLCands_Launcher','de.metas.ordercandidate',TO_TIMESTAMP('2017-11-09 14:42:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-09T14:42:03.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540887 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-11-09T14:42:36.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,EntityType,Updated,UpdatedBy) VALUES (TO_TIMESTAMP('2017-11-09 14:42:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',259,0,'N','N',540887,0,'de.metas.ordercandidate',TO_TIMESTAMP('2017-11-09 14:42:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-10T09:02:48.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='Y',Updated=TO_TIMESTAMP('2017-11-10 09:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540887
;

