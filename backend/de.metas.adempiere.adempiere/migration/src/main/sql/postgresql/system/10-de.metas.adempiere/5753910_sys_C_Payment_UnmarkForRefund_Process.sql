-- Run mode: SWING_CLIENT

-- Value: C_Payment_UnmarkForRefund
-- Classname: de.metas.payment.process.C_Payment_UnmarkForRefund
-- 2025-05-08T12:30:45.268Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585466,'Y','de.metas.payment.process.C_Payment_UnmarkForRefund','N',TO_TIMESTAMP('2025-05-08 15:30:44.697','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Entmarkieren von Doppelzahlungen','json','N','N','xls','Java',TO_TIMESTAMP('2025-05-08 15:30:44.697','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Payment_UnmarkForRefund')
;

-- 2025-05-08T12:30:45.315Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585466 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Payment_UnmarkForRefund(de.metas.payment.process.C_Payment_UnmarkForRefund)
-- 2025-05-08T12:31:05.926Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Unmark for refund',Updated=TO_TIMESTAMP('2025-05-08 15:31:05.926','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585466
;

-- Process: C_Payment_UnmarkForRefund(de.metas.payment.process.C_Payment_UnmarkForRefund)
-- Table: C_Payment
-- EntityType: D
-- 2025-05-08T12:58:16.089Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585466,335,541552,TO_TIMESTAMP('2025-05-08 15:58:15.855','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2025-05-08 15:58:15.855','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

