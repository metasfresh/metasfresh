-- 2024-03-20T17:36:12.562Z
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540314,0,TO_TIMESTAMP('2024-03-20 18:36:12.402','YYYY-MM-DD HH24:MI:SS.US'),100,'','de.metas.postfinance','Y','Y','de.metas.postfinance','de.metas.postfinance','N',TO_TIMESTAMP('2024-03-20 18:36:12.402','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Value: C_Doc_Outbound_Log_Export_To_Post_Finance
-- Classname: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance
-- 2024-03-20T17:39:19.648Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585364,'Y','de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance','N',TO_TIMESTAMP('2024-03-20 18:39:19.49','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Dokumente zu PostFinance senden','json','N','N','xls','Java',TO_TIMESTAMP('2024-03-20 18:39:19.49','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Doc_Outbound_Log_Export_To_Post_Finance')
;

-- 2024-03-20T17:39:19.652Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585364 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Doc_Outbound_Log_Export_To_Post_Finance(de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance)
-- 2024-03-20T17:39:27.190Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-20 18:39:27.19','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585364
;

-- Process: C_Doc_Outbound_Log_Export_To_Post_Finance(de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance)
-- 2024-03-20T17:39:28.458Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-20 18:39:28.458','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585364
;

-- Process: C_Doc_Outbound_Log_Export_To_Post_Finance(de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance)
-- 2024-03-20T17:39:38.958Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Send documents to PostFinance',Updated=TO_TIMESTAMP('2024-03-20 18:39:38.958','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585364
;

-- Process: C_Doc_Outbound_Log_Export_To_Post_Finance(de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance)
-- Table: C_Doc_Outbound_Log
-- Window: Ausgehende Belege(540170,de.metas.document.archive)
-- EntityType: de.metas.postfinance
-- 2024-03-20T17:40:45.588Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585364,540453,541469,540170,TO_TIMESTAMP('2024-03-20 18:40:45.357','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y',TO_TIMESTAMP('2024-03-20 18:40:45.357','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

