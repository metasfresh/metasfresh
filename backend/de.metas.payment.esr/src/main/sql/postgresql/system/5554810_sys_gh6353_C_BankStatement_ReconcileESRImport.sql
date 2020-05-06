-- 2020-03-19T09:53:00.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584670,'Y','de.metas.banking.process.C_BankStatement_ReconcileESRImport','N',TO_TIMESTAMP('2020-03-19 11:53:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','N','N','N','N','N','N','Y','Y',0,'Reconcile with ESR Import','N','N','Java',TO_TIMESTAMP('2020-03-19 11:53:00','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_ReconcileESRImport')
;

-- 2020-03-19T09:53:00.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584670 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-03-19T09:53:31.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541827,0,584670,541763,30,'ESR_Import_ID',TO_TIMESTAMP('2020-03-19 11:53:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr',0,'Y','N','Y','N','Y','N','ESR Zahlungsimport',10,TO_TIMESTAMP('2020-03-19 11:53:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-19T09:53:31.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541763 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-19T09:53:50.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584670,392,540804,TO_TIMESTAMP('2020-03-19 11:53:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y',TO_TIMESTAMP('2020-03-19 11:53:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

