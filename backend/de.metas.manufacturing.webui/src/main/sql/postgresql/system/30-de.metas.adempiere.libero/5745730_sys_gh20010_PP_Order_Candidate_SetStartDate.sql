-- Run mode: SWING_CLIENT

-- Value: PP_Order_Candidate_SetStartDate
-- Classname: de.metas.manufacturing.webui.process.PP_Order_Candidate_SetStartDate
-- 2025-02-05T11:57:14.579Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585450,'Y','de.metas.manufacturing.webui.process.PP_Order_Candidate_SetStartDate','N',TO_TIMESTAMP('2025-02-05 11:57:14.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Startdatum festlegen','json','N','N','xls','Java',TO_TIMESTAMP('2025-02-05 11:57:14.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PP_Order_Candidate_SetStartDate')
;

-- 2025-02-05T11:57:14.582Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585450 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_Candidate_SetStartDate(de.metas.manufacturing.webui.process.PP_Order_Candidate_SetStartDate)
-- ParameterName: DateStart
-- 2025-02-05T12:00:37.706Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53280,0,585450,542917,16,'DateStart',TO_TIMESTAMP('2025-02-05 12:00:37.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','N','Y','N','Y','N','Startdatum',10,TO_TIMESTAMP('2025-02-05 12:00:37.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-05T12:00:37.708Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542917 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Run mode: SWING_CLIENT

-- Process: PP_Order_Candidate_SetStartDate(de.metas.manufacturing.webui.process.PP_Order_Candidate_SetStartDate)
-- Table: PP_Order_Candidate
-- EntityType: D
-- 2025-02-05T12:14:03.709Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585450,541913,541544,TO_TIMESTAMP('2025-02-05 12:14:03.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-02-05 12:14:03.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Run mode: SWING_CLIENT

-- SysConfig Name: resource.PlanningPrecision
-- SysConfig Value: M
-- 2025-02-05T12:34:56.984Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541750,'S',TO_TIMESTAMP('2025-02-05 12:34:56.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','resource.PlanningPrecision',TO_TIMESTAMP('2025-02-05 12:34:56.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;


-- SysConfig Name: PP_Order_Candidate_SetStartDate.rowsLimit
-- SysConfig Value: 1000
-- 2025-02-05T14:52:11.650Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541751,'S',TO_TIMESTAMP('2025-02-05 14:52:11.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','PP_Order_Candidate_SetStartDate.rowsLimit',TO_TIMESTAMP('2025-02-05 14:52:11.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1000')
;

-- Process: PP_Order_Candidate_SetStartDate(de.metas.manufacturing.webui.process.PP_Order_Candidate_SetStartDate)
-- 2025-02-05T15:30:10.684Z
UPDATE AD_Process_Trl SET Name='Set Start Date',Updated=TO_TIMESTAMP('2025-02-05 15:30:10.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585450
;
