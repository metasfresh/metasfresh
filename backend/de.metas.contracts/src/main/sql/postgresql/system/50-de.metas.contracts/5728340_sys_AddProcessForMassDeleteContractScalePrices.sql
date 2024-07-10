-- Value: ModCntr_Specific_Delete_Price_Selection
-- Classname: de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection
-- 2024-07-10T13:52:59.371Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585406,'Y','de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection','N',TO_TIMESTAMP('2024-07-10 16:52:59.107','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Preise für Selektion löschen','json','N','N','xls','Deletes prices for selected contracts','Java',TO_TIMESTAMP('2024-07-10 16:52:59.107','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Specific_Delete_Price_Selection')
;

-- 2024-07-10T13:52:59.393Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585406 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ModCntr_Specific_Delete_Price_Selection(de.metas.contracts.modular.process.ModCntr_Specific_Delete_Price_Selection)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2024-07-10T13:53:27.332Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585406,540320,541502,TO_TIMESTAMP('2024-07-10 16:53:27.175','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-07-10 16:53:27.175','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

