-- Value: Delete OLCands
-- Classname: de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords
-- 2024-11-18T14:16:49.127Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585435,'Y','de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords','N',TO_TIMESTAMP('2024-11-18 14:16:48.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Löschen','json','N','N','xls','Java',TO_TIMESTAMP('2024-11-18 14:16:48.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Delete OLCands')
;

-- 2024-11-18T14:16:49.159Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585435 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- 2024-11-18T14:16:59.664Z
UPDATE AD_Process_Trl SET Name='Delete',Updated=TO_TIMESTAMP('2024-11-18 14:16:59.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585435
;

-- Value: Delete OLCands
-- Classname: de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords
-- 2024-11-18T14:17:23.596Z
UPDATE AD_Process SET Description='Bist du sicher?',Updated=TO_TIMESTAMP('2024-11-18 14:17:23.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585435
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- 2024-11-18T14:17:29.213Z
UPDATE AD_Process_Trl SET Description='Bist du sicher?',Updated=TO_TIMESTAMP('2024-11-18 14:17:29.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585435
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- 2024-11-18T14:17:30.678Z
UPDATE AD_Process_Trl SET Description='Bist du sicher?',Updated=TO_TIMESTAMP('2024-11-18 14:17:30.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585435
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- 2024-11-18T14:17:32.354Z
UPDATE AD_Process_Trl SET Description='Bist du sicher?',Updated=TO_TIMESTAMP('2024-11-18 14:17:32.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585435
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- 2024-11-18T14:17:34.014Z
UPDATE AD_Process_Trl SET Description='Bist du sicher?',Updated=TO_TIMESTAMP('2024-11-18 14:17:34.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585435
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- 2024-11-18T14:17:58.032Z
UPDATE AD_Process_Trl SET Description='Are you sure?',Updated=TO_TIMESTAMP('2024-11-18 14:17:58.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585435
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- Table: C_OLCand
-- EntityType: D
-- 2024-11-18T14:18:33.102Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585435,540244,541536,TO_TIMESTAMP('2024-11-18 14:18:32.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2024-11-18 14:18:32.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Process: Delete OLCands(de.metas.ui.web.ordercandidate.process.OLCandDeleteSelectedRecords)
-- ParameterName: Description
-- 2024-11-18T14:57:55.143Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,275,0,585435,542904,10,'Description',TO_TIMESTAMP('2024-11-18 14:57:54.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1=2','D',255,'Y','N','Y','N','N','N','Beschreibung','',10,TO_TIMESTAMP('2024-11-18 14:57:54.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-11-18T14:57:55.151Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542904 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: de.metas.ui.web.ordercandidate.process.PROCESSED_RECORDS_CANNOT_BE_DELETED
-- 2024-11-18T16:33:01.376Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545481,0,TO_TIMESTAMP('2024-11-18 16:33:00.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bereits bearbeitete Datensätze können nicht gelöscht werden!','E',TO_TIMESTAMP('2024-11-18 16:33:00.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ui.web.ordercandidate.process.PROCESSED_RECORDS_CANNOT_BE_DELETED')
;

-- 2024-11-18T16:33:01.403Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545481 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.ordercandidate.process.PROCESSED_RECORDS_CANNOT_BE_DELETED
-- 2024-11-18T16:33:29.760Z
UPDATE AD_Message_Trl SET MsgText='Already processed records cannot be deleted!',Updated=TO_TIMESTAMP('2024-11-18 16:33:29.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545481
;

-- Value: de.metas.ui.web.ordercandidate.process.DELETE_ALL_RECORDS
-- 2024-11-18T16:50:52.584Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545482,0,TO_TIMESTAMP('2024-11-18 16:50:52.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Das Löschen aller Datensätze wird nicht unterstützt','E',TO_TIMESTAMP('2024-11-18 16:50:52.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ui.web.ordercandidate.process.DELETE_ALL_RECORDS')
;

-- 2024-11-18T16:50:52.590Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545482 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.ordercandidate.process.DELETE_ALL_RECORDS
-- 2024-11-18T16:51:00.591Z
UPDATE AD_Message_Trl SET MsgText='Deleting all records is not supported',Updated=TO_TIMESTAMP('2024-11-18 16:51:00.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545482
;
