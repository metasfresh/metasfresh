-- Run mode: SWING_CLIENT

-- Value: M_ShipmentSchedule_Traffic_Management_assign
-- Classname: de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign
-- 2025-12-11T14:05:10.958Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585546,'Y','de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign','N',TO_TIMESTAMP('2025-12-11 14:05:10.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Lieferdiposition mit heutigen Bewegungsdatum Traffic','json','N','N','xls','Java',TO_TIMESTAMP('2025-12-11 14:05:10.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ShipmentSchedule_Traffic_Management_assign')
;

-- 2025-12-11T14:05:10.967Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585546 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_ShipmentSchedule_Traffic_Management_assign
-- Classname: de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign
-- 2025-12-11T14:08:17.492Z
UPDATE AD_Process SET Name='Lieferdiposition mit heutigen Bewegungsdatum Traffic Management zuweisen',Updated=TO_TIMESTAMP('2025-12-11 14:08:17.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585546
;

-- 2025-12-11T14:08:17.501Z
UPDATE AD_Process_Trl trl SET Name='Lieferdiposition mit heutigen Bewegungsdatum Traffic Management zuweisen' WHERE AD_Process_ID=585546 AND AD_Language='de_DE'
;

-- Process: M_ShipmentSchedule_Traffic_Management_assign(de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign)
-- 2025-12-11T14:08:37.436Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferdiposition mit heutigen Bewegungsdatum Traffic Management zuweisen',Updated=TO_TIMESTAMP('2025-12-11 14:08:37.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585546
;

-- 2025-12-11T14:08:37.444Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_Traffic_Management_assign(de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign)
-- 2025-12-11T14:08:38.667Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-11 14:08:38.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585546
;

-- Process: M_ShipmentSchedule_Traffic_Management_assign(de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign)
-- 2025-12-11T14:08:41.211Z
UPDATE AD_Process_Trl SET Name='Lieferdiposition mit heutigen Bewegungsdatum Traffic Management zuweisen',Updated=TO_TIMESTAMP('2025-12-11 14:08:41.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585546
;

-- 2025-12-11T14:08:41.226Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: M_ShipmentSchedule_Traffic_Management_assign(de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign)
-- 2025-12-11T14:09:29.521Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Shipment schedules with preperation date today Traffic Managment assignment',Updated=TO_TIMESTAMP('2025-12-11 14:09:29.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585546
;

-- 2025-12-11T14:09:29.523Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-11T14:23:34.675Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,585546,540024,550122,TO_TIMESTAMP('2025-12-11 14:23:34.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',5,'M','Y','N',7,'Automatische Traffic Management Zuweisung','N','P','F','NEW',100,TO_TIMESTAMP('2025-12-11 14:23:34.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
