
-- Value: C_Payment_MarkForRefund
-- 2025-05-06T12:09:56.853Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585465,'Y','N',TO_TIMESTAMP('2025-05-06 15:09:56.486','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Mark for Refund','json','N','N','xls','Java',TO_TIMESTAMP('2025-05-06 15:09:56.486','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Payment_MarkForRefund')
;

-- 2025-05-06T12:09:56.863Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585465 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Payment_MarkForRefund
-- 2025-05-06T12:10:22.036Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Markieren von Doppelzahlungen',Updated=TO_TIMESTAMP('2025-05-06 15:10:22.036','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585465
;

-- Process: C_Payment_MarkForRefund
-- 2025-05-06T12:10:26.310Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Markieren von Doppelzahlungen',Updated=TO_TIMESTAMP('2025-05-06 15:10:26.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585465
;

-- 2025-05-06T12:10:26.312Z
UPDATE AD_Process SET Name='Markieren von Doppelzahlungen' WHERE AD_Process_ID=585465
;

-- Process: C_Payment_MarkForRefund
-- Table: C_Payment
-- EntityType: D
-- 2025-05-06T12:13:56.931Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585465,335,541551,TO_TIMESTAMP('2025-05-06 15:13:56.796','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2025-05-06 15:13:56.796','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;


-- Value: C_Payment_MarkForRefund
-- Classname: de.metas.payment.process.C_Payment_MarkForRefund
-- 2025-05-06T16:00:49.140Z
UPDATE AD_Process SET Classname='de.metas.payment.process.C_Payment_MarkForRefund',Updated=TO_TIMESTAMP('2025-05-06 19:00:49.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585465
;

