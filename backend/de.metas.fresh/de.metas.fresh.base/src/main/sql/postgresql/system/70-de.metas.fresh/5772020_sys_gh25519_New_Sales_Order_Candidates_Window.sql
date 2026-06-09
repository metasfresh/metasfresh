-- Run mode: SWING_CLIENT

-- 2025-10-05T15:06:25.056Z
UPDATE AD_Window_Trl SET Name='Auftragsdisposition',Updated=TO_TIMESTAMP('2025-10-05 15:06:24.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Window_ID=541952
;

-- 2025-10-05T15:06:25.130Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-05T15:06:34.556Z
UPDATE AD_Window_Trl SET Name='Auftragsdisposition',Updated=TO_TIMESTAMP('2025-10-05 15:06:34.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Window_ID=541952
;

-- 2025-10-05T15:06:34.619Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-05T15:06:42.355Z
UPDATE AD_Window_Trl SET Name='Auftragsdisposition',Updated=TO_TIMESTAMP('2025-10-05 15:06:42.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Window_ID=541952
;

-- 2025-10-05T15:06:42.419Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

