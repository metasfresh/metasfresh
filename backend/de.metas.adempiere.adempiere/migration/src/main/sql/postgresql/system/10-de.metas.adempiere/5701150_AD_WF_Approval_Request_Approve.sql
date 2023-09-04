-- Value: AD_WF_Approval_Request_Approve
-- Classname: de.metas.workflow.process.AD_WF_Approval_Request_Approve
-- 2023-09-01T06:36:17.406324400Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585310,'Y','de.metas.workflow.process.AD_WF_Approval_Request_Approve','N',TO_TIMESTAMP('2023-09-01 09:36:16.994','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Approve','json','N','N','xls','Java',TO_TIMESTAMP('2023-09-01 09:36:16.994','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_WF_Approval_Request_Approve')
;

-- 2023-09-01T06:36:17.419872900Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585310 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_WF_Approval_Request_Approve(de.metas.workflow.process.AD_WF_Approval_Request_Approve)
-- Table: AD_WF_Approval_Request
-- EntityType: D
-- 2023-09-01T06:40:56.434863300Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585310,542364,541411,TO_TIMESTAMP('2023-09-01 09:40:56.238','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-09-01 09:40:56.238','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','N')
;

