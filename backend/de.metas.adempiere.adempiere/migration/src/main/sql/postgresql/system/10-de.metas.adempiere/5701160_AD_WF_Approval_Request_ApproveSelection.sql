-- Value: AD_WF_Approval_Request_ApproveSelection
-- Classname: de.metas.workflow.process.AD_WF_Approval_Request_ApproveSelection
-- 2023-09-01T06:41:50.598293800Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585311,'Y','de.metas.workflow.process.AD_WF_Approval_Request_ApproveSelection','N',TO_TIMESTAMP('2023-09-01 09:41:50.472','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Approve selected','json','N','N','xls','Java',TO_TIMESTAMP('2023-09-01 09:41:50.472','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_WF_Approval_Request_ApproveSelection')
;

-- 2023-09-01T06:41:50.604037400Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585311 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_WF_Approval_Request_ApproveSelection(de.metas.workflow.process.AD_WF_Approval_Request_ApproveSelection)
-- Table: AD_WF_Approval_Request
-- EntityType: D
-- 2023-09-01T06:42:08.494693100Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585311,542364,541412,TO_TIMESTAMP('2023-09-01 09:42:08.371','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-09-01 09:42:08.371','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Y','Y','N')
;

