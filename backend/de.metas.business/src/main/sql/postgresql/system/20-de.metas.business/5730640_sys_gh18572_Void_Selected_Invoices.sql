-- 2024-08-01T11:02:21.518Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585417,'Y','org.adempiere.ad.process.ProcessDocumentsBySelection','N',TO_TIMESTAMP('2024-08-01 14:02:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Ausgewählte Belege stornieren','json','N','N','xls','Java',TO_TIMESTAMP('2024-08-01 14:02:20','YYYY-MM-DD HH24:MI:SS'),100,'VoidSelectedInvoices')
;

-- 2024-08-01T11:02:21.565Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585417 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2024-08-01T11:14:48.290Z
UPDATE AD_Process SET Description='Ausgewählte Belege stornieren',Updated=TO_TIMESTAMP('2024-08-01 14:14:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585417
;

-- 2024-08-01T11:14:52.904Z
UPDATE AD_Process_Trl SET Description='Ausgewählte Belege stornieren',Updated=TO_TIMESTAMP('2024-08-01 14:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585417
;

-- 2024-08-01T11:14:54.638Z
UPDATE AD_Process_Trl SET Description='Ausgewählte Belege stornieren',Updated=TO_TIMESTAMP('2024-08-01 14:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585417
;

-- 2024-08-01T11:15:29.333Z
UPDATE AD_Process_Trl SET Description='Void selected invoices', Name='Void selected invoices',Updated=TO_TIMESTAMP('2024-08-01 14:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585417
;

-- 2024-08-01T11:15:31.982Z
UPDATE AD_Process_Trl SET Description='Ausgewählte Belege stornieren',Updated=TO_TIMESTAMP('2024-08-01 14:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585417
;

-- 2024-08-01T11:15:33.609Z
UPDATE AD_Process_Trl SET Description='Ausgewählte Belege stornieren',Updated=TO_TIMESTAMP('2024-08-01 14:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585417
;

-- 2024-08-01T11:16:55.040Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,287,0,585417,542882,17,135,'DocAction',TO_TIMESTAMP('2024-08-01 14:16:54','YYYY-MM-DD HH24:MI:SS'),100,'VO','Der zukünftige Status des Belegs','D',0,'You find the current status in the Document Status field. The options are listed in a popup','Y','N','Y','N','Y','N','Belegverarbeitung',10,TO_TIMESTAMP('2024-08-01 14:16:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-01T11:16:55.047Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542882 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-08-01T11:29:31.391Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585417,318,541513,TO_TIMESTAMP('2024-08-01 14:29:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-08-01 14:29:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2024-08-01T13:30:39.252Z
UPDATE AD_Process_Para SET DisplayLogic='1=0',Updated=TO_TIMESTAMP('2024-08-01 16:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542882
;

