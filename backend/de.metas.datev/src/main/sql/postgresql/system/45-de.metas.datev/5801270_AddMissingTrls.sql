-- 2026-05-07T14:03:35.209Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 14:03:35.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543882 AND AD_Language='de_CH'
;

-- 2026-05-07T14:04:03.514Z
UPDATE AD_Element_Trl SET Name='CSV-Codierung', PrintName='CSV-Codierung',Updated=TO_TIMESTAMP('2026-05-07 14:04:03.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543882 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-07T14:04:03.515Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:06:31.672Z
UPDATE AD_Element_Trl SET Name='CSV-Feldtrenner', PrintName='CSV-Feldtrenner',Updated=TO_TIMESTAMP('2026-05-07 14:06:31.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543883 AND AD_Language='de_CH'
;

-- 2026-05-07T14:06:31.672Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:06:36.612Z
UPDATE AD_Element_Trl SET Name='CSV-Feldtrenner', PrintName='CSV-Feldtrenner',Updated=TO_TIMESTAMP('2026-05-07 14:06:36.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543883 AND AD_Language='de_DE'
;

-- 2026-05-07T14:06:36.613Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:07:19.300Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dezimaltrennzeichen', PrintName='Dezimaltrennzeichen',Updated=TO_TIMESTAMP('2026-05-07 14:07:19.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543888 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-07T14:07:19.301Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:07:22.594Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 14:07:22.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543888 AND AD_Language='en_US'
;

-- 2026-05-07T14:08:35.155Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tausendertrennzeichen', PrintName='Tausendertrennzeichen',Updated=TO_TIMESTAMP('2026-05-07 14:08:35.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543887 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-07T14:08:35.155Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:08:38.861Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 14:08:38.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543887 AND AD_Language='en_US'
;

