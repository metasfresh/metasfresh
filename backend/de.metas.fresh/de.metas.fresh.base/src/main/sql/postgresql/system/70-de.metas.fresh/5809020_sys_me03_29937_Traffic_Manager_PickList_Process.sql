-- Run mode: SWING_CLIENT

-- IDs from idserver.metas.de on 2026-06-19:
--   AD_Process_ID 585640  (picking list report process for Traffic Manager)

-- Process: Print Picking List from Traffic Manager
-- Jasper report: de/metas/docs/sales/picking_traffic_mgr/report.jasper
-- Attached to: Window 541929 (Traffic Management) → Tab 548377 (Kommissionierplan)
--              via AD_Table 542514 (M_Picking_Job_Schedule_view)
-- 2026-06-19T09:00:10.000Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585640 /*From ID Server*/,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2026-06-19 09:00:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','N','N','Y','N','N','N','N','Y','N','N','N','@PREFIX@de/metas/docs/sales/picking_traffic_mgr/report.jasper',0,'Pickliste drucken (Traffic Manager)','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2026-06-19 09:00:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'TrafficMgr_PickingList')
;

-- 2026-06-19T09:00:10.100Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585640 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: TrafficMgr_PickingList(585640)
-- 2026-06-19T09:00:11.000Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Pickliste drucken (Traffic Manager)',Updated=TO_TIMESTAMP('2026-06-19 09:00:11.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585640
;

-- 2026-06-19T09:00:11.100Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-19T09:00:11.200Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Pickliste drucken (Traffic Manager)',Updated=TO_TIMESTAMP('2026-06-19 09:00:11.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585640
;

-- 2026-06-19T09:00:11.300Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Print Picking List (Traffic Manager)',Updated=TO_TIMESTAMP('2026-06-19 09:00:11.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585640
;

-- 2026-06-19T09:00:11.400Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Pickliste drucken (Traffic Manager)',Updated=TO_TIMESTAMP('2026-06-19 09:00:11.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585640
;

-- 2026-06-19T09:00:12.000Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,585640,542514,TO_TIMESTAMP('2026-06-19 09:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2026-06-19 09:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Scope the process to Window 541929 so it only appears in the Traffic Management window
-- 2026-06-19T09:00:12.100Z
UPDATE AD_Table_Process SET AD_Window_ID=541929,Updated=TO_TIMESTAMP('2026-06-19 09:00:12.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585640 AND AD_Table_ID=542514
;
