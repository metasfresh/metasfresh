-- Value: DD_Order_Candidate_EnqueToProcess
-- Classname: de.metas.distribution.ddordercandidate.process.DD_Order_Candidate_EnqueToProcess
-- 2024-08-06T11:13:53.412Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585419,'Y','de.metas.distribution.ddordercandidate.process.DD_Order_Candidate_EnqueToProcess','N',TO_TIMESTAMP('2024-08-06 14:13:53','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Generate Distribution Orders','json','N','N','xls','Java',TO_TIMESTAMP('2024-08-06 14:13:53','YYYY-MM-DD HH24:MI:SS'),100,'DD_Order_Candidate_EnqueToProcess')
;

-- 2024-08-06T11:13:53.415Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585419 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: DD_Order_Candidate_EnqueToProcess(de.metas.distribution.ddordercandidate.process.DD_Order_Candidate_EnqueToProcess)
-- Table: DD_Order_Candidate
-- EntityType: EE01
-- 2024-08-06T11:14:19.250Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585419,542424,541514,TO_TIMESTAMP('2024-08-06 14:14:19','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2024-08-06 14:14:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N')
;

-- Value: DD_Order_Candidate_EnqueueToProcess
-- Classname: de.metas.distribution.ddordercandidate.process.DD_Order_Candidate_EnqueueToProcess
-- 2024-08-06T11:15:44.463Z
UPDATE AD_Process SET Classname='de.metas.distribution.ddordercandidate.process.DD_Order_Candidate_EnqueueToProcess', Value='DD_Order_Candidate_EnqueueToProcess',Updated=TO_TIMESTAMP('2024-08-06 14:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585419
;

-- Value: DD_Order_Candidate_EnqueueToProcess
-- Classname: de.metas.distribution.webui.process.DD_Order_Candidate_EnqueueToProcess
-- 2024-08-06T11:17:03.052Z
UPDATE AD_Process SET Classname='de.metas.distribution.webui.process.DD_Order_Candidate_EnqueueToProcess',Updated=TO_TIMESTAMP('2024-08-06 14:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585419
;

