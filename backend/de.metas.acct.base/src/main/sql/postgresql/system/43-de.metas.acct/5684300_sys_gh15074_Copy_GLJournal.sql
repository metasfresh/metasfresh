-- Value: SAP_GLJournal_CopyDocument
-- Classname: de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument
-- 2023-04-04T17:01:50.888Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585254,'Y','de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument','N',TO_TIMESTAMP('2023-04-04 20:01:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Copy GLJournal','json','N','N','xls','Java',TO_TIMESTAMP('2023-04-04 20:01:50','YYYY-MM-DD HH24:MI:SS'),100,'SAP_GLJournal_CopyDocument')
;

-- 2023-04-04T17:01:50.893Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585254 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: DateDoc
-- 2023-04-04T17:09:35.589Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,265,0,585254,542611,15,'DateDoc',TO_TIMESTAMP('2023-04-04 20:09:35','YYYY-MM-DD HH24:MI:SS'),100,'@DateDoc@','de.metas.acct',0,'The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','Y','N','N','N','Document Date',10,TO_TIMESTAMP('2023-04-04 20:09:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-04T17:09:35.593Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542611 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-04-04T17:09:35.646Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(265) 
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: NegateAmounts
-- 2023-04-04T17:11:13.505Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585254,542612,20,'NegateAmounts',TO_TIMESTAMP('2023-04-04 20:11:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,'Y','N','Y','N','Y','N','Negate amounts',20,TO_TIMESTAMP('2023-04-04 20:11:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-04T17:11:13.507Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542612 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: DateDoc
-- 2023-04-04T17:11:16.972Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-04 20:11:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542611
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- Table: SAP_GLJournal
-- EntityType: de.metas.acct
-- 2023-04-04T17:12:10.929Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585254,542275,541374,TO_TIMESTAMP('2023-04-04 20:12:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y',TO_TIMESTAMP('2023-04-04 20:12:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: NegateAmounts
-- 2023-04-05T16:37:16.424Z
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2023-04-05 19:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542612
;

-- Value: gljournal_sap.Document_has_to_be_Completed
-- 2023-04-05T16:41:45.029Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545258,0,TO_TIMESTAMP('2023-04-05 19:41:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Dokument muss fertiggestellt werden.','I',TO_TIMESTAMP('2023-04-05 19:41:44','YYYY-MM-DD HH24:MI:SS'),100,'gljournal_sap.Document_has_to_be_Completed')
;

-- 2023-04-05T16:41:45.034Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545258 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: gljournal_sap.Document_has_to_be_Completed
-- 2023-04-05T16:41:56.046Z
UPDATE AD_Message_Trl SET MsgText='Document must be completed.',Updated=TO_TIMESTAMP('2023-04-05 19:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545258
;

-- 2023-04-05T16:41:56.047Z
UPDATE AD_Message SET MsgText='Document must be completed.' WHERE AD_Message_ID=545258
;
