-- Run mode: SWING_CLIENT

-- Value: M_ShipmentSchedule_QtyPicked_Deactivate
-- 2025-09-02T16:40:01.627Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585495,'Y','N',TO_TIMESTAMP('2025-09-02 16:40:00.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Deactivate  Qty Picked','json','N','N','xls','Java',TO_TIMESTAMP('2025-09-02 16:40:00.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ShipmentSchedule_QtyPicked_Deactivate')
;

-- 2025-09-02T16:40:01.637Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585495 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_ShipmentSchedule_QtyPicked_Deactivate
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-09-02T16:40:49.015Z
UPDATE AD_Process SET Classname='de.metas.process.ExecuteUpdateSQL', SQLStatement='UPDATE M_ShipmentSchedule_QtyPicked set IsActive = ''N'' where M_ShipmentSchedule_QtyPicked_ID = @M_ShipmentSchedule_QtyPicked/-1@', Type='SQL',Updated=TO_TIMESTAMP('2025-09-02 16:40:49.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585495
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- Table: M_ShipmentSchedule
-- EntityType: D
-- 2025-09-02T16:41:38.680Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585495,500221,541567,TO_TIMESTAMP('2025-09-02 16:41:38.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-09-02 16:41:38.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Name: M_ShipmentSchedule_QtyPicked for M_ShipmentSchedule
-- 2025-09-02T16:43:06.315Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541974,TO_TIMESTAMP('2025-09-02 16:43:04.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipmentSchedule_QtyPicked for M_ShipmentSchedule',TO_TIMESTAMP('2025-09-02 16:43:04.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-09-02T16:43:06.318Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541974 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipmentSchedule_QtyPicked for M_ShipmentSchedule
-- Table: M_ShipmentSchedule_QtyPicked
-- Key: M_ShipmentSchedule_QtyPicked.M_ShipmentSchedule_QtyPicked_ID
-- 2025-09-02T16:44:40.131Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549901,0,541974,540542,TO_TIMESTAMP('2025-09-02 16:44:40.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-09-02 16:44:40.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'(Exists (select 1 from M_ShipmentSchedule_QtyPicked where M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID/-1@)')
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_ShipmentSchedule_QtyPicked_ID
-- 2025-09-02T16:46:00.690Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542269,0,585495,542975,30,541974,'M_ShipmentSchedule_QtyPicked_ID',TO_TIMESTAMP('2025-09-02 16:46:00.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate',0,'Y','N','Y','N','Y','N','ShipmentSchedule QtyPicked',10,TO_TIMESTAMP('2025-09-02 16:46:00.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-02T16:46:00.695Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542975 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Column: M_ShipmentSchedule_QtyPicked.M_InOutLine_ID
-- 2025-09-02T17:05:10.333Z
UPDATE AD_Column SET  IsIdentifier='Y',Updated=TO_TIMESTAMP('2025-09-02 17:05:10.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551062
;

-- Column: M_ShipmentSchedule_QtyPicked.QtyPicked
-- 2025-09-02T17:05:33.105Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2025-09-02 17:05:33.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549903
;

-- Name: M_ShipmentSchedule_QtyPicked for M_ShipmentSchedule
-- 2025-09-02T17:08:23.718Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540742,'M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID/-1@',TO_TIMESTAMP('2025-09-02 17:08:23.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_ShipmentSchedule_QtyPicked for M_ShipmentSchedule','S',TO_TIMESTAMP('2025-09-02 17:08:23.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_ShipmentSchedule_QtyPicked_ID
-- 2025-09-02T17:08:45.463Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2025-09-02 17:08:45.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542975
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_ShipmentSchedule_QtyPicked_ID
-- 2025-09-02T17:08:50.838Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540742,Updated=TO_TIMESTAMP('2025-09-02 17:08:50.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542975
;

-- Value: M_ShipmentSchedule_QtyPicked_Deactivate
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-09-02T17:10:24.775Z
UPDATE AD_Process SET SQLStatement='UPDATE M_ShipmentSchedule_QtyPicked set IsActive = ''N'' where M_ShipmentSchedule_QtyPicked_ID = @M_ShipmentSchedule_QtyPicked_ID/-1@',Updated=TO_TIMESTAMP('2025-09-02 17:10:24.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585495
;

-- Name: M_ShipmentSchedule_QtyPicked for M_ShipmentSchedule
-- 2025-09-02T17:11:29.258Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541974
;

-- 2025-09-02T17:11:29.270Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541974
;





-- Run mode: SWING_CLIENT

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- Table: M_ShipmentSchedule
-- EntityType: D
-- 2025-09-03T13:47:36.712Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541567
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- Table: M_ShipmentSchedule_QtyPicked
-- EntityType: D
-- 2025-09-03T13:47:57.330Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585495,540542,541568,TO_TIMESTAMP('2025-09-03 13:47:57.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-09-03 13:47:57.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_ShipmentSchedule_QtyPicked_ID
-- 2025-09-03T13:49:31.197Z
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2025-09-03 13:49:31.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542975
;

-- Value: M_ShipmentSchedule_QtyPicked_Deactivate
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-09-03T13:54:15.621Z
UPDATE AD_Process SET SQLStatement='
UPDATE M_ShipmentSchedule_QtyPicked set IsActive = (CASE WHEN IsActive = ''Y'' THEN ''N'' ELSE ''Y'' END) where M_ShipmentSchedule_QtyPicked_ID = @M_ShipmentSchedule_QtyPicked_ID/-1@',Updated=TO_TIMESTAMP('2025-09-03 13:54:15.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585495
;

-- Value: M_ShipmentSchedule_QtyPicked_Deactivate
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-09-03T13:56:08.524Z
UPDATE AD_Process SET Name='Aktivstatus umschalten',Updated=TO_TIMESTAMP('2025-09-03 13:56:08.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585495
;

-- 2025-09-03T13:56:08.525Z
UPDATE AD_Process_Trl trl SET Name='Aktivstatus umschalten' WHERE AD_Process_ID=585495 AND AD_Language='de_DE'
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- 2025-09-03T13:56:13.493Z
UPDATE AD_Process_Trl SET Name='Aktivstatus umschalten',Updated=TO_TIMESTAMP('2025-09-03 13:56:13.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585495
;

-- 2025-09-03T13:56:13.494Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- 2025-09-03T13:56:26.439Z
UPDATE AD_Process_Trl SET Name='Switch Active Status',Updated=TO_TIMESTAMP('2025-09-03 13:56:26.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585495
;

-- 2025-09-03T13:56:26.440Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_QtyPicked_Deactivate(de.metas.process.ExecuteUpdateSQL)
-- 2025-09-03T13:56:31.704Z
UPDATE AD_Process_Trl SET Name='Aktivstatus umschalten',Updated=TO_TIMESTAMP('2025-09-03 13:56:31.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585495
;

-- 2025-09-03T13:56:31.704Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_ShipmentSchedule_QtyPicked_Deactivate
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-09-03T15:36:39.186Z
UPDATE AD_Process SET SQLStatement='SELECT M_ShipmentSchedule_QtyPicked_SwitchIsActive(@M_ShipmentSchedule_QtyPicked_ID/-1@)',Updated=TO_TIMESTAMP('2025-09-03 15:36:39.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585495
;

-- Value: M_ShipmentSchedule_QtyPicked_SwitchActiveStatus
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2025-09-03T15:55:20.446Z
UPDATE AD_Process SET Value='M_ShipmentSchedule_QtyPicked_SwitchActiveStatus',Updated=TO_TIMESTAMP('2025-09-03 15:55:20.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585495
;

