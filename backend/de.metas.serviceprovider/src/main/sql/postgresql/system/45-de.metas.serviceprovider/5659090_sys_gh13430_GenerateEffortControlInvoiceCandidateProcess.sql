
-- Value: GenerateEffortControlInvoiceCandidateProcess
-- Classname: de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess
-- 2022-10-06T08:29:18.555Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585118,'Y','de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess','N',TO_TIMESTAMP('2022-10-06 11:29:18.291','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.serviceprovider','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Generate/Update invoice candidate','json','N','N','xls','Java',TO_TIMESTAMP('2022-10-06 11:29:18.291','YYYY-MM-DD HH24:MI:SS.US'),100,'Generate/Update invoice candidate')
;

-- 2022-10-06T08:29:18.568Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585118 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: GenerateEffortControlInvoiceCandidateProcess(de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess)
-- Table: S_EffortControl
-- EntityType: de.metas.serviceprovider
-- 2022-10-06T08:29:45.050Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585118,542215,541288,TO_TIMESTAMP('2022-10-06 11:29:44.871','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.serviceprovider','Y',TO_TIMESTAMP('2022-10-06 11:29:44.871','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;


-- Process: Generate/Update invoice candidate(de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess)
-- 2022-10-14T14:21:43.719Z
UPDATE AD_Process_Trl SET Name='Rechnungskandidaten generieren/aktualisieren',Updated=TO_TIMESTAMP('2022-10-14 17:21:43.719','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585118
;

-- Value: Generate/Update invoice candidate
-- Classname: de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess
-- 2022-10-14T14:21:47.765Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Rechnungskandidaten generieren/aktualisieren',Updated=TO_TIMESTAMP('2022-10-14 17:21:47.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585118
;

-- Process: Generate/Update invoice candidate(de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess)
-- 2022-10-14T14:21:47.706Z
UPDATE AD_Process_Trl SET Name='Rechnungskandidaten generieren/aktualisieren',Updated=TO_TIMESTAMP('2022-10-14 17:21:47.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585118
;

-- Process: Generate/Update invoice candidate(de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess)
-- 2022-10-14T14:21:57.908Z
UPDATE AD_Process_Trl SET Name='Rechnungskandidaten generieren/aktualisieren',Updated=TO_TIMESTAMP('2022-10-14 17:21:57.907','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585118
;

-- Process: Generate/Update invoice candidate(de.metas.serviceprovider.effortcontrol.process.GenerateEffortControlInvoiceCandidateProcess)
-- 2022-10-14T14:22:01.351Z
UPDATE AD_Process_Trl SET Name='Rechnungskandidaten generieren/aktualisieren',Updated=TO_TIMESTAMP('2022-10-14 17:22:01.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585118
;

