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

-- Value: Msg_No_Fallback_Price
-- 2024-07-10T14:16:44.497Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545431,0,TO_TIMESTAMP('2024-07-10 17:16:44.338','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','No fallback scale price!','E',TO_TIMESTAMP('2024-07-10 17:16:44.338','YYYY-MM-DD HH24:MI:SS.US'),100,'Msg_No_Fallback_Price')
;

-- 2024-07-10T14:16:44.500Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545431 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Msg_No_Fallback_Price
-- 2024-07-10T14:16:53.679Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Kein Fallback-Staffelpreis!',Updated=TO_TIMESTAMP('2024-07-10 17:16:53.679','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545431
;

-- Value: Msg_No_Fallback_Price
-- 2024-07-10T14:16:55.508Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-10 17:16:55.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545431
;
