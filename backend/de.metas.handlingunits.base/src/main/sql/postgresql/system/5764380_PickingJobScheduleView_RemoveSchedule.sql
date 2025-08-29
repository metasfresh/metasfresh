-- Run mode: SWING_CLIENT

-- Value: PickingJobScheduleView_RemoveSchedule
-- Classname: de.metas.ui.web.pickingJobSchedule.process.PickingJobScheduleView_RemoveSchedule
-- 2025-08-29T06:06:14.143Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585494,'Y','de.metas.ui.web.pickingJobSchedule.process.PickingJobScheduleView_RemoveSchedule','N',TO_TIMESTAMP('2025-08-29 06:06:13.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Remove Schedule','json','N','N','xls','Java',TO_TIMESTAMP('2025-08-29 06:06:13.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PickingJobScheduleView_RemoveSchedule')
;

-- 2025-08-29T06:06:14.152Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585494 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PickingJobScheduleView_RemoveSchedule(de.metas.ui.web.pickingJobSchedule.process.PickingJobScheduleView_RemoveSchedule)
-- Table: M_Picking_Job_Schedule_view
-- EntityType: de.metas.handlingunits
-- 2025-08-29T06:06:40.374Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585494,542514,541566,TO_TIMESTAMP('2025-08-29 06:06:40.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2025-08-29 06:06:40.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Process: PickingJobScheduleView_Schedule(de.metas.ui.web.pickingJobSchedule.process.PickingJobScheduleView_Schedule)
-- Table: M_Picking_Job_Schedule_view
-- EntityType: de.metas.handlingunits
-- 2025-08-29T06:06:52.986Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2025-08-29 06:06:52.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541565
;

