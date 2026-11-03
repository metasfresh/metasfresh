-- 2018-05-27T09:19:15.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_PurchaseCandidates_PurchaseView_Launcher','3','de.metas.purchasecandidate','N','Y','N','N',540972,'N','Y','N','Java','N','N',0,0,'Bestellungen disponieren','de.metas.ui.web.order.sales.purchasePlanning.process.WEBUI_PurchaseCandidates_PurchaseView_Launcher',100,TO_TIMESTAMP('2018-05-27 09:19:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-27 09:19:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-27T09:19:15.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540972 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-05-27T09:19:37.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-27 09:19:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Create purchase orders' WHERE AD_Process_ID=540972 AND AD_Language='en_US'
;

-- 2018-05-27T09:35:06.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.purchasecandidate',100,'Y',540861,0,'Y','N',540972,0,100,TO_TIMESTAMP('2018-05-27 09:35:06','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-27 09:35:06','YYYY-MM-DD HH24:MI:SS'))
;

