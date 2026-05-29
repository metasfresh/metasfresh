-- Run mode: SWING_CLIENT

-- Process: PickingJobScheduleView_Schedule(de.metas.ui.web.pickingJobSchedule.process.PickingJobScheduleView_Schedule)
-- 2025-09-24T14:44:03.499Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zeitplan',Updated=TO_TIMESTAMP('2025-09-24 14:44:03.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585493
;

-- 2025-09-24T14:44:03.571Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: PickingJobScheduleView_Schedule(de.metas.ui.web.pickingJobSchedule.process.PickingJobScheduleView_Schedule)
-- 2025-09-24T14:44:03.499Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zeitplan',Updated=TO_TIMESTAMP('2025-09-24 14:44:03.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585493
;

-- 2025-09-24T14:44:03.571Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;
