-- 2021-07-22T18:38:03.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584880,'Y','de.metas.payment.revolut.process.C_PaySelection_RevolutPayment_CSVExport','N',TO_TIMESTAMP('2021-07-22 21:38:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','N','N','N','Y','N','N','N','Y','Y',0,'Revolut Export','json','N','N','Java',TO_TIMESTAMP('2021-07-22 21:38:03','YYYY-MM-DD HH24:MI:SS'),100,'C_PaySelection_RevolutPayment_CSVExport')
;

-- 2021-07-22T18:38:03.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584880 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-07-22T18:38:35.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584880,426,540973,TO_TIMESTAMP('2021-07-22 21:38:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y',TO_TIMESTAMP('2021-07-22 21:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

