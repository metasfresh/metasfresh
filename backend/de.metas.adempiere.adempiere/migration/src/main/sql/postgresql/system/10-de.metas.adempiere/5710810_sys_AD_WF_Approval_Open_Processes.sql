-- Value: AD_WF_Approval_Request_Download_File
-- Classname: de.metas.workflow.process.AD_WF_Approval_Request_Download_File
-- 2023-11-16T09:47:43.843075Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585336,'Y','de.metas.workflow.process.AD_WF_Approval_Request_Download_File','N',TO_TIMESTAMP('2023-11-16 11:47:43.651','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Open','json','N','N','xls','Java',TO_TIMESTAMP('2023-11-16 11:47:43.651','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_WF_Approval_Request_Download_File')
;

-- 2023-11-16T09:47:43.848573100Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585336 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_WF_Approval_Request_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- Window: Approval Requests(541730,D)
-- EntityType: D
-- 2023-11-16T09:48:43.975365100Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585336,547277,542364,541445,541730,TO_TIMESTAMP('2023-11-16 11:48:43.81','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-11-16 11:48:43.81','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','Y','N','N')
;

-- Value: AD_WF_My_Approvals_Download_File
-- Classname: de.metas.workflow.process.AD_WF_Approval_Request_Download_File
-- 2023-11-16T17:37:34.458876100Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585337,'Y','de.metas.workflow.process.AD_WF_Approval_Request_Download_File','N',TO_TIMESTAMP('2023-11-16 19:37:34.268','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Open','json','N','N','xls','Java',TO_TIMESTAMP('2023-11-16 19:37:34.268','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_WF_My_Approvals_Download_File')
;

-- 2023-11-16T17:37:34.465374600Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585337 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_WF_My_Approvals_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Meine Zulassungen(541736,D) -> Dokumente(547282,D)
-- Window: Meine Zulassungen(541736,D)
-- EntityType: D
-- 2023-11-16T17:38:23.187359400Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585337,547282,542364,541446,541736,TO_TIMESTAMP('2023-11-16 19:38:23.077','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-11-16 19:38:23.077','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','Y','N','N')
;

-- Process: AD_WF_My_Approvals_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Meine Zulassungen(541736,D) -> Dokumente(547282,D)
-- Window: Meine Zulassungen(541736,D)
-- EntityType: D
-- 2023-11-16T18:18:03.268072500Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2023-11-16 20:18:03.268','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541446
;

-- Process: AD_WF_Approval_Request_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- Window: Approval Requests(541730,D)
-- EntityType: D
-- 2023-11-16T18:36:51.029284600Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2023-11-16 20:36:51.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541445
;

-- Process: AD_WF_My_Approvals_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Meine Zulassungen(541736,D) -> Dokumente(547282,D)
-- Window: Meine Zulassungen(541736,D)
-- EntityType: D
-- 2023-11-16T18:37:03.599283100Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='Y',Updated=TO_TIMESTAMP('2023-11-16 20:37:03.598','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541446
;

-- Process: AD_WF_Approval_Request_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- Window: Approval Requests(541730,D)
-- EntityType: D
-- 2023-11-16T18:50:10.025618400Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N',Updated=TO_TIMESTAMP('2023-11-16 20:50:10.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541445
;



-- Process: AD_WF_My_Approvals_Download_File(de.metas.workflow.process.AD_WF_Approval_Request_Download_File)
-- Table: AD_WF_Approval_Request
-- Tab: Meine Freigaben(541736,D) -> Dokumente(547282,D)
-- Window: Meine Freigaben(541736,D)
-- EntityType: D
-- 2023-11-20T14:49:03.374098Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N',Updated=TO_TIMESTAMP('2023-11-20 16:49:03.373','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541446
;

