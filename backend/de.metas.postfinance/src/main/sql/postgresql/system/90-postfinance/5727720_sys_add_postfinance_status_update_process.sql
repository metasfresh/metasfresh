-- Run mode: SWING_CLIENT

-- Value: C_Doc_Outbound_Log_Update_PostFinance_Status
-- Classname: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status
-- 2024-07-02T14:38:29.581Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585402,'Y','de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status','N',TO_TIMESTAMP('2024-07-02 16:38:29.395','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'PostFinance Status aktualisieren','json','N','N','xls','Java',TO_TIMESTAMP('2024-07-02 16:38:29.395','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Doc_Outbound_Log_Update_PostFinance_Status')
;

-- 2024-07-02T14:38:29.584Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585402 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Doc_Outbound_Log_Update_PostFinance_Status(de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status)
-- 2024-07-02T14:38:37.104Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 16:38:37.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585402
;

-- Process: C_Doc_Outbound_Log_Update_PostFinance_Status(de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status)
-- 2024-07-02T14:38:37.933Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 16:38:37.933','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585402
;

-- Process: C_Doc_Outbound_Log_Update_PostFinance_Status(de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status)
-- 2024-07-02T14:39:01.093Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Update PostFinance Status',Updated=TO_TIMESTAMP('2024-07-02 16:39:01.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585402
;

-- Process: C_Doc_Outbound_Log_Update_PostFinance_Status(de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status)
-- ParameterName: PostFinance_Export_Status
-- 2024-07-02T14:40:27.761Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583052,0,585402,542855,17,541860,'PostFinance_Export_Status',TO_TIMESTAMP('2024-07-02 16:40:27.618','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance',0,'Y','N','Y','N','Y','N','PostFinance Status',10,TO_TIMESTAMP('2024-07-02 16:40:27.618','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-02T14:40:27.763Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542855 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-07-02T14:40:27.766Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583052)
;

-- Process: C_Doc_Outbound_Log_Update_PostFinance_Status(de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status)
-- Table: C_Doc_Outbound_Log
-- Window: Ausgehende Belege(540170,de.metas.document.archive)
-- EntityType: de.metas.postfinance
-- 2024-07-02T14:45:49.662Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585402,540453,541498,540170,TO_TIMESTAMP('2024-07-02 16:45:49.531','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y',TO_TIMESTAMP('2024-07-02 16:45:49.531','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.OnlyOnePostFinanceStatusSelection
-- 2024-07-03T12:44:57.387Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545429,0,TO_TIMESTAMP('2024-07-03 14:44:57.267','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Selektion muss den gleichen PostFinance Status haben','I',TO_TIMESTAMP('2024-07-03 14:44:57.267','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.OnlyOnePostFinanceStatusSelection')
;

-- 2024-07-03T12:44:57.391Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545429 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.OnlyOnePostFinanceStatusSelection
-- 2024-07-03T12:45:02.064Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-03 14:45:02.064','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545429
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.OnlyOnePostFinanceStatusSelection
-- 2024-07-03T12:45:03.569Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-03 14:45:03.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545429
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.OnlyOnePostFinanceStatusSelection
-- 2024-07-03T12:45:29.533Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Selekcion needs to have the same PostFinance Status',Updated=TO_TIMESTAMP('2024-07-03 14:45:29.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545429
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.NoTargetPostFinanceStatusForSelection
-- 2024-07-03T12:46:50.490Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545430,0,TO_TIMESTAMP('2024-07-03 14:46:50.375','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Kein Ziel PostFinance Status für Selektion','I',TO_TIMESTAMP('2024-07-03 14:46:50.375','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.NoTargetPostFinanceStatusForSelection')
;

-- 2024-07-03T12:46:50.493Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545430 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.NoTargetPostFinanceStatusForSelection
-- 2024-07-03T12:46:56.892Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-03 14:46:56.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545430
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.NoTargetPostFinanceStatusForSelection
-- 2024-07-03T12:46:58.078Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-03 14:46:58.077','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545430
;

-- Value: de.metas.postfinance.webui.process.C_Doc_Outbound_Log_Update_PostFinance_Status.NoTargetPostFinanceStatusForSelection
-- 2024-07-03T12:47:21.729Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No target PostFinance Status for selection',Updated=TO_TIMESTAMP('2024-07-03 14:47:21.729','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545430
;

