-- 2020-03-24T12:00:40.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,584674,'Y','org.compiere.process.AD_Tab_CopyToNewWindow','N',TO_TIMESTAMP('2020-03-24 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','Y',0,'Copy Tab to a new Window','N','N','Java',TO_TIMESTAMP('2020-03-24 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,'AD_Tab_CopyToNewWindow')
;

-- 2020-03-24T12:00:40.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584674 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-03-24T12:01:58.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,631,0,584674,541769,17,108,'WindowType',TO_TIMESTAMP('2020-03-24 14:01:58','YYYY-MM-DD HH24:MI:SS'),100,'Type or classification of a Window','U',0,'The Window Type indicates the type of window being defined (Maintain, Transaction or Query)','Y','N','Y','N','Y','N','WindowType',10,TO_TIMESTAMP('2020-03-24 14:01:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-24T12:01:58.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541769 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-24T12:02:13.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='M',Updated=TO_TIMESTAMP('2020-03-24 14:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541769
;

-- 2020-03-24T12:02:15.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2020-03-24 14:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541769
;

-- 2020-03-24T12:02:41.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584674,106,540808,TO_TIMESTAMP('2020-03-24 14:02:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-03-24 14:02:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

