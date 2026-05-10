-- Run mode: SWING_CLIENT

-- 2026-05-10T18:29:39.749Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y', Name='Als EXTF-Datei exportieren',Updated=TO_TIMESTAMP('2026-05-10 18:29:39.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language in ('de_CH', 'de_DE') AND AD_Process_Para_ID=543190
;

-- 2026-05-10T18:29:39.761Z
UPDATE AD_Process_Para base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-10T18:30:39.941Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y', Name='Export as EXTF File',Updated=TO_TIMESTAMP('2026-05-10 18:30:39.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543190
;

-- 2026-05-10T18:30:39.944Z
UPDATE AD_Process_Para base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

