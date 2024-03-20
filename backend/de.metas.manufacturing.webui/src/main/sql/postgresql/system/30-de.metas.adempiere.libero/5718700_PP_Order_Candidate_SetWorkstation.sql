-- Value: PP_Order_Candidate_SetWorkstation
-- Classname: de.metas.manufacturing.webui.process.PP_Order_Candidate_SetWorkstation
-- 2024-03-07T08:53:02.245Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585359,'Y','de.metas.manufacturing.webui.process.PP_Order_Candidate_SetWorkstation','N',TO_TIMESTAMP('2024-03-07 10:53:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Set Workstation','json','N','N','xls','Java',TO_TIMESTAMP('2024-03-07 10:53:02','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_Candidate_SetWorkstation')
;

-- 2024-03-07T08:53:02.248Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585359 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_Candidate_SetWorkstation(de.metas.manufacturing.webui.process.PP_Order_Candidate_SetWorkstation)
-- ParameterName: WorkStation_ID
-- 2024-03-07T08:53:33.174Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583018,0,585359,542786,30,541855,540669,'WorkStation_ID',TO_TIMESTAMP('2024-03-07 10:53:33','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Arbeitsstation',10,TO_TIMESTAMP('2024-03-07 10:53:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T08:53:33.176Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542786 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_SetWorkstation(de.metas.manufacturing.webui.process.PP_Order_Candidate_SetWorkstation)
-- Table: PP_Order_Candidate
-- EntityType: D
-- 2024-03-07T08:53:56.268Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585359,541913,541463,TO_TIMESTAMP('2024-03-07 10:53:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-03-07 10:53:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N')
;

-- Process: PP_Order_Candidate_SetWorkstation(de.metas.manufacturing.webui.process.PP_Order_Candidate_SetWorkstation)
-- Table: PP_Order_Candidate
-- EntityType: D
-- 2024-03-07T08:54:02.412Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2024-03-07 10:54:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541463
;

