-- Run mode: SWING_CLIENT

-- gh#28223: Ensure all Mobile_Application records have proper en_US translations and IsTranslated='Y'

--
-- 540008 (scanAnything / Scan)
--

-- en_US: set Name='Scan' and mark as translated
-- 2026-02-25T12:00:00Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Scan',Updated=now()::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540008
;

-- 2026-02-25T12:00:00Z
/* DDL */  select update_Mobile_Application_TRLs(540008,'en_US')
;

-- de_DE: mark as translated
-- 2026-02-25T12:00:00Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=now()::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540008
;

-- 2026-02-25T12:00:00Z
/* DDL */  select update_Mobile_Application_TRLs(540008,'de_DE')
;

-- de_CH: mark as translated
-- 2026-02-25T12:00:00Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=now()::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540008
;

-- 2026-02-25T12:00:00Z
/* DDL */  select update_Mobile_Application_TRLs(540008,'de_CH')
;

--
-- 540009 (pos / POS)
--

-- en_US: set Name='POS' and mark as translated
-- 2026-02-25T12:00:00Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='POS',Updated=now()::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540009
;

-- 2026-02-25T12:00:00Z
/* DDL */  select update_Mobile_Application_TRLs(540009,'en_US')
;

-- de_DE: mark as translated
-- 2026-02-25T12:00:00Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=now()::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540009
;

-- 2026-02-25T12:00:00Z
/* DDL */  select update_Mobile_Application_TRLs(540009,'de_DE')
;

-- de_CH: mark as translated
-- 2026-02-25T12:00:00Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=now()::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540009
;

-- 2026-02-25T12:00:00Z
/* DDL */  select update_Mobile_Application_TRLs(540009,'de_CH')
;
