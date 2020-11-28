-- 2019-07-26T06:40:49.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541169,'Y','N',TO_TIMESTAMP('2019-07-26 09:40:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.paypal','Y','N','N','N','N','N','N','Y','Y',0,'Create PayPal Order','N','N','Java',TO_TIMESTAMP('2019-07-26 09:40:49','YYYY-MM-DD HH24:MI:SS'),100,'C_Payment_Reservation_CreatePayPalOrder')
;

-- 2019-07-26T06:40:49.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541169 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-07-26T06:41:02.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.payment.paypal.ui.process.C_Payment_Reservation_CreatePayPalOrder',Updated=TO_TIMESTAMP('2019-07-26 09:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541169
;

-- 2019-07-26T06:41:42.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541169,541386,540732,TO_TIMESTAMP('2019-07-26 09:41:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.paypal','Y',TO_TIMESTAMP('2019-07-26 09:41:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

