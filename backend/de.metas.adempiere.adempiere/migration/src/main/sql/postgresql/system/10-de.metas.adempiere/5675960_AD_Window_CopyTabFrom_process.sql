-- Delete AD_Window_CopyTabFrom process introduced by 5641120_sys_AD_Window_CopyTabFrom_process.sql
delete from ad_table_process where ad_process_id=585062;
delete from ad_process where ad_process_id=585062;


-- Value: AD_Window_CopyTabFrom
-- Classname: org.compiere.process.AD_Window_CopyTabFrom
-- 2023-02-07T11:40:44.602Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585216,'N','org.compiere.process.AD_Window_CopyTabFrom','N',TO_TIMESTAMP('2023-02-07 13:40:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','N','N',0,'Copy Tab from...','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-07 13:40:44','YYYY-MM-DD HH24:MI:SS'),100,'AD_Window_CopyTabFrom')
;

-- 2023-02-07T11:40:44.604Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585216 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_Window_CopyTabFrom(org.compiere.process.AD_Window_CopyTabFrom)
-- ParameterName: Template_Tab_ID
-- 2023-02-07T11:41:12.537Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544090,0,585216,542537,30,278,'Template_Tab_ID',TO_TIMESTAMP('2023-02-07 13:41:12','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Template Tab',10,TO_TIMESTAMP('2023-02-07 13:41:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:41:12.539Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542537 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: AD_Window_CopyTabFrom(org.compiere.process.AD_Window_CopyTabFrom)
-- Table: AD_Window
-- EntityType: D
-- 2023-02-07T11:41:32.726Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585216,105,541357,TO_TIMESTAMP('2023-02-07 13:41:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-07 13:41:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Value: AD_Window_CopyTabFrom
-- Classname: org.compiere.process.AD_Window_CopyTabFrom
-- 2023-03-08T09:03:44.386Z
UPDATE AD_Process SET AccessLevel='4',Updated=TO_TIMESTAMP('2023-03-08 11:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585216
;

