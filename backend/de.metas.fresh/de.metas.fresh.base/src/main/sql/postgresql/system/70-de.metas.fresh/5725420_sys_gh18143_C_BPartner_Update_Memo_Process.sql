-- Value: C_BPartner_Update_Memo
-- Classname: de.metas.bpartner.process.C_BPartner_Update_Memo
-- 2024-06-04T10:33:40.980Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585398,'Y','de.metas.bpartner.process.C_BPartner_Update_Memo','N',TO_TIMESTAMP('2024-06-04 11:33:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Geschäftspartner Memo aktualisieren','json','N','Y','xls','Java',TO_TIMESTAMP('2024-06-04 11:33:40','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Update_Memo')
;

-- 2024-06-04T10:33:40.982Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585398 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_BPartner_Update_Memo(de.metas.bpartner.process.C_BPartner_Update_Memo)
-- ParameterName: Memo
-- 2024-06-04T12:33:45.140Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2112,0,585398,542843,14,'Memo',TO_TIMESTAMP('2024-06-04 13:33:44','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text','D',0,'Y','N','Y','N','N','N','Memo',10,TO_TIMESTAMP('2024-06-04 13:33:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-04T12:33:45.143Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542843 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_BPartner_Update_Memo(de.metas.bpartner.process.C_BPartner_Update_Memo)
-- Table: C_BPartner
-- EntityType: D
-- 2024-06-04T12:36:52.803Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585398,291,541495,TO_TIMESTAMP('2024-06-04 13:36:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-06-04 13:36:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: C_BPartner_Update_Memo(de.metas.bpartner.process.C_BPartner_Update_Memo)
-- 2024-06-04T12:43:44.938Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Update Partner Memo',Updated=TO_TIMESTAMP('2024-06-04 13:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585398
;

