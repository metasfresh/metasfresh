-- Value: S_Resource_PrintSingleQRCode
-- Classname: de.metas.resource.process.S_Resource_PrintSingleQRCode
-- 2022-11-21T11:24:37.589Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585150,'Y','de.metas.resource.process.S_Resource_PrintSingleQRCode','N',TO_TIMESTAMP('2022-11-21 13:24:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'QR Code','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-21 13:24:37','YYYY-MM-DD HH24:MI:SS'),100,'S_Resource_PrintSingleQRCode')
;

-- 2022-11-21T11:24:37.591Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585150 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: S_Resource_PrintSingleQRCode(de.metas.resource.process.S_Resource_PrintSingleQRCode)
-- Table: S_Resource
-- EntityType: D
-- 2022-11-21T11:24:51.518Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585150,487,541311,TO_TIMESTAMP('2022-11-21 13:24:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-11-21 13:24:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Value: S_Resource_PrintQRCodes
-- Classname: de.metas.resource.process.S_Resource_PrintQRCodes
-- 2022-11-21T11:25:47.371Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585151,'Y','de.metas.resource.process.S_Resource_PrintQRCodes','N',TO_TIMESTAMP('2022-11-21 13:25:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'QR Code','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-21 13:25:47','YYYY-MM-DD HH24:MI:SS'),100,'S_Resource_PrintQRCodes')
;

-- 2022-11-21T11:25:47.372Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585151 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: S_Resource_PrintQRCodes(de.metas.resource.process.S_Resource_PrintQRCodes)
-- Table: S_Resource
-- EntityType: D
-- 2022-11-21T11:26:12.539Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585151,487,541312,TO_TIMESTAMP('2022-11-21 13:26:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-11-21 13:26:12','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N')
;

