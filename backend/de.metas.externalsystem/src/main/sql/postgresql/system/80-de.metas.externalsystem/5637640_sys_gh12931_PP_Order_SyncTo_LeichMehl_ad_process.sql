
-- 2022-05-01T11:22:09.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585045,'Y','de.metas.externalsystem.leichmehl.export.pporder.PP_Order_SyncTo_LeichMehl','N',TO_TIMESTAMP('2022-05-01 14:22:08','YYYY-MM-DD HH24:MI:SS'),100,'Sendet die ausgewählten Produktionsaufträge an die entsprechende Leich & Mehl FTP-Server Konfiguration.','de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','Y',0,'An Leich & Mehl senden','json','N','N','xls','Java',TO_TIMESTAMP('2022-05-01 14:22:08','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_SyncTo_LeichMehl')
;

-- 2022-05-01T11:22:09.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585045 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-05-01T11:22:36.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sends the selected manufacturing orders to the corresponding Leich & Mehl FTP-Server configuration.', Name='Send to Leich & Mehl',Updated=TO_TIMESTAMP('2022-05-01 14:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585045
;

-- 2022-05-01T11:24:53.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541576,TO_TIMESTAMP('2022-05-01 14:24:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','IsActive_LeichMehl_Config',TO_TIMESTAMP('2022-05-01 14:24:53','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-05-01T11:24:53.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541576 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-05-01T11:25:30.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,582888,0,541576,542129,TO_TIMESTAMP('2022-05-01 14:25:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N',TO_TIMESTAMP('2022-05-01 14:25:30','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2022-05-01T11:26:43.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580811,0,585045,542255,18,541576,'ExternalSystem_Config_LeichMehl_ID',TO_TIMESTAMP('2022-05-01 14:26:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',7,'Y','N','Y','N','Y','N','ExternalSystem_Config_LeichMehl',10,TO_TIMESTAMP('2022-05-01 14:26:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-01T11:26:43.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542255 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-01T11:27:39.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585045,53027,541107,TO_TIMESTAMP('2022-05-01 14:27:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2022-05-01 14:27:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

