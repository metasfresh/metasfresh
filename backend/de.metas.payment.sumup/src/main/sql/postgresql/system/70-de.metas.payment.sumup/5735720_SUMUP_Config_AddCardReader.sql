-- Value: SUMUP_Config_AddCardReader
-- Classname: de.metas.payment.sumup.process.SUMUP_Config_AddCardReader
-- 2024-10-03T20:08:34.330Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585424,'Y','de.metas.payment.sumup.process.SUMUP_Config_AddCardReader','N',TO_TIMESTAMP('2024-10-03 23:08:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Pair New Card Reader','json','N','N','xls','Java',TO_TIMESTAMP('2024-10-03 23:08:34','YYYY-MM-DD HH24:MI:SS'),100,'SUMUP_Config_AddCardReader')
;

-- 2024-10-03T20:08:34.337Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585424 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SUMUP_Config_AddCardReader(de.metas.payment.sumup.process.SUMUP_Config_AddCardReader)
-- ParameterName: Name
-- 2024-10-03T20:08:59.044Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,469,0,585424,542891,10,'Name',TO_TIMESTAMP('2024-10-03 23:08:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup',0,'Y','N','Y','N','Y','N','Name',10,TO_TIMESTAMP('2024-10-03 23:08:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T20:08:59.049Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542891 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-10-03T20:09:36.038Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583301,0,'SUMUP_PairingCode',TO_TIMESTAMP('2024-10-03 23:09:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Pairing Code','Pairing Code',TO_TIMESTAMP('2024-10-03 23:09:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T20:09:36.043Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583301 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: SUMUP_Config_AddCardReader(de.metas.payment.sumup.process.SUMUP_Config_AddCardReader)
-- ParameterName: SUMUP_PairingCode
-- 2024-10-03T20:09:58.665Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583301,0,585424,542892,10,'SUMUP_PairingCode',TO_TIMESTAMP('2024-10-03 23:09:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup',0,'Y','N','Y','N','Y','N','Pairing Code',20,TO_TIMESTAMP('2024-10-03 23:09:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T20:09:58.667Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542892 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: SUMUP_Config_AddCardReader(de.metas.payment.sumup.process.SUMUP_Config_AddCardReader)
-- Table: SUMUP_Config
-- EntityType: de.metas.payment.sumup
-- 2024-10-03T20:10:14.271Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585424,542440,541522,TO_TIMESTAMP('2024-10-03 23:10:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y',TO_TIMESTAMP('2024-10-03 23:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Value: SUMUP_Config_AddCardReader
-- Classname: de.metas.payment.sumup.webui.process.SUMUP_Config_AddCardReader
-- 2024-10-03T20:27:15.583Z
UPDATE AD_Process SET Classname='de.metas.payment.sumup.webui.process.SUMUP_Config_AddCardReader',Updated=TO_TIMESTAMP('2024-10-03 23:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585424
;

