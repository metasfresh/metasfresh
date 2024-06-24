-- Value: C_Workplace_PrintQRCode
-- Classname: de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode
-- 2023-10-26T11:24:57.902Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585332,'Y','de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode','N',TO_TIMESTAMP('2023-10-26 14:24:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'QR-Codes drucken','json','N','N','xls','Java',TO_TIMESTAMP('2023-10-26 14:24:57','YYYY-MM-DD HH24:MI:SS'),100,'C_Workplace_PrintQRCode')
;

-- 2023-10-26T11:24:57.904Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585332 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- Table: C_Workplace
-- EntityType: D
-- 2023-10-26T11:25:19.017Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585332,542375,541442,TO_TIMESTAMP('2023-10-26 14:25:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-10-26 14:25:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- 2023-10-26T11:25:45.644Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-26 14:25:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585332
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- 2023-10-26T11:25:46.867Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-26 14:25:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585332
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- 2023-10-26T11:25:52.039Z
UPDATE AD_Process_Trl SET Name='Print QR Code',Updated=TO_TIMESTAMP('2023-10-26 14:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585332
;

-- Process: C_Workplace_PrintQRCode(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode)
-- 2023-10-26T11:25:53.443Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-26 14:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585332
;

-- Value: C_Workplace_PrintQRCode_ViewSelection
-- Classname: de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode_ViewSelection
-- 2023-10-26T11:26:31.176Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585333,'Y','de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode_ViewSelection','N',TO_TIMESTAMP('2023-10-26 14:26:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'QR-Codes drucken','json','N','N','xls','Java',TO_TIMESTAMP('2023-10-26 14:26:31','YYYY-MM-DD HH24:MI:SS'),100,'C_Workplace_PrintQRCode_ViewSelection')
;

-- 2023-10-26T11:26:31.177Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585333 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Workplace_PrintQRCode_ViewSelection(de.metas.workplace.qrcode.process.C_Workplace_PrintQRCode_ViewSelection)
-- Table: C_Workplace
-- EntityType: D
-- 2023-10-26T11:26:50Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585333,542375,541443,TO_TIMESTAMP('2023-10-26 14:26:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-10-26 14:26:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N')
;

