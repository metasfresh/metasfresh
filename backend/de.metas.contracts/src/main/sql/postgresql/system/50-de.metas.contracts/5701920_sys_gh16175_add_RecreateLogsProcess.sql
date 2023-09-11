-- Value: RecomputeLogRecordsForDocument
-- 2023-09-06T08:36:23.500425100Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585314,'Y','N',TO_TIMESTAMP('2023-09-06 11:36:23.263','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Recompute Modular Logs','json','N','N','xls','Java',TO_TIMESTAMP('2023-09-06 11:36:23.263','YYYY-MM-DD HH24:MI:SS.US'),100,'RecomputeLogRecordsForDocument')
;

-- 2023-09-06T08:36:23.509026900Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585314 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: RecomputeLogRecordsForDocument
-- Classname: de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument
-- 2023-09-06T08:36:45.218586500Z
UPDATE AD_Process SET Classname='de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument',Updated=TO_TIMESTAMP('2023-09-06 11:36:45.217','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585314
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: C_Order
-- EntityType: de.metas.contracts
-- 2023-09-06T08:37:07.085893600Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,259,541414,TO_TIMESTAMP('2023-09-06 11:37:06.763','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 11:37:06.763','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: M_Inventory
-- EntityType: de.metas.contracts
-- 2023-09-06T08:38:39.348806600Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,321,541415,TO_TIMESTAMP('2023-09-06 11:38:39.222','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 11:38:39.222','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: C_Invoice
-- EntityType: de.metas.contracts
-- 2023-09-06T08:39:17.545779Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,318,541416,TO_TIMESTAMP('2023-09-06 11:39:17.249','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 11:39:17.249','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2023-09-06T08:40:58.191416700Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,540320,541417,TO_TIMESTAMP('2023-09-06 11:40:58.024','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 11:40:58.024','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: PP_Cost_Collector
-- EntityType: de.metas.contracts
-- 2023-09-06T08:41:52.370903400Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,53035,541418,TO_TIMESTAMP('2023-09-06 11:41:52.212','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 11:41:52.212','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: M_InOut
-- EntityType: de.metas.contracts
-- 2023-09-06T08:43:32.963857200Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,319,541419,TO_TIMESTAMP('2023-09-06 11:43:32.818','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 11:43:32.818','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- 2023-09-06T08:53:03.751351400Z
UPDATE AD_Process_Trl SET Name='Die Vertragsbausteinlogs neu erstellen',Updated=TO_TIMESTAMP('2023-09-06 11:53:03.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585314
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- 2023-09-06T08:53:06.692786Z
UPDATE AD_Process_Trl SET Name='Die Vertragsbausteinlogs neu erstellen',Updated=TO_TIMESTAMP('2023-09-06 11:53:06.692','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585314
;

-- 2023-09-06T08:53:06.695204500Z
UPDATE AD_Process SET Name='Die Vertragsbausteinlogs neu erstellen' WHERE AD_Process_ID=585314
;

-- Process: RecomputeLogRecordsForDocument(de.metas.contracts.modular.log.process.RecomputeLogRecordsForDocument)
-- Table: PP_Order
-- EntityType: de.metas.contracts
-- 2023-09-06T09:11:22.689535200Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585314,53027,541420,TO_TIMESTAMP('2023-09-06 12:11:22.519','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 12:11:22.519','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Value: RecomputeModularContractLogs
-- 2023-09-06T09:12:22.096079800Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585315,'Y','N',TO_TIMESTAMP('2023-09-06 12:12:21.956','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Die Vertragsbausteinlogs neu erstellen','json','N','N','xls','Java',TO_TIMESTAMP('2023-09-06 12:12:21.956','YYYY-MM-DD HH24:MI:SS.US'),100,'RecomputeModularContractLogs')
;

-- 2023-09-06T09:12:22.105601300Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585315 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: RecomputeModularContractLogs
-- 2023-09-06T09:12:45.949161400Z
UPDATE AD_Process_Trl SET Name='Recompute Modular Logs',Updated=TO_TIMESTAMP('2023-09-06 12:12:45.949','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585315
;

-- Process: RecomputeModularContractLogs
-- Table: ModCntr_Log
-- EntityType: de.metas.contracts
-- 2023-09-06T09:13:03.744004200Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585315,542338,541421,TO_TIMESTAMP('2023-09-06 12:13:03.563','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-09-06 12:13:03.563','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Value: RecomputeModularContractLogs
-- Classname: de.metas.contracts.modular.log.process.RecomputeLogRecords
-- 2023-09-08T10:12:17.678300Z
UPDATE AD_Process SET Classname='de.metas.contracts.modular.log.process.RecomputeLogRecords',Updated=TO_TIMESTAMP('2023-09-08 10:12:17.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585315
;

