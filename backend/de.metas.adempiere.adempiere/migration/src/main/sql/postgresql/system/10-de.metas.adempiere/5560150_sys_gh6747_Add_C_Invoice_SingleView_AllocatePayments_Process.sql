-- 2020-05-28T11:02:23.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AD_Org_ID,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,IsNotifyUserAfterExecution,IsServerProcess,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-05-28 14:02:23','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-28 14:02:23','YYYY-MM-DD HH24:MI:SS'),'N',0,'N','3','N','N','N',100,584699,'PaymentView_Launcher_From_C_Invoice_SingleDocument','Y','Y','N','N','N',0,'Java','Y','Zahlung-Zuordnung','N','N','de.metas.ui.web.payment_allocation.process.PaymentView_Launcher_From_C_Invoice_SingleDocument','D')
;

-- 2020-05-28T11:02:23.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584699 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-05-28T11:02:51.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2020-05-28 14:02:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',318,TO_TIMESTAMP('2020-05-28 14:02:51','YYYY-MM-DD HH24:MI:SS'),100,0,'N',584699,'Y','Y','N','N',540825,0,'D')
;

