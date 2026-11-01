-- Run mode: SWING_CLIENT

-- Element: IsAllowIssuingAnyHU
-- 2026-05-19T07:53:40.586Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rohwarenprüfung deaktivieren', PrintName='Rohwarenprüfung deaktivieren',Updated=TO_TIMESTAMP('2026-05-19 07:53:40.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583889 AND AD_Language='de_DE'
;

-- 2026-05-19T07:53:40.647Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-19T07:53:48.307Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583889,'de_DE')
;

-- Element: IsAllowIssuingAnyHU
-- 2026-05-19T07:54:07.630Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rohwarenprüfung deaktivieren', PrintName='Rohwarenprüfung deaktivieren',Updated=TO_TIMESTAMP('2026-05-19 07:54:07.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583889 AND AD_Language='de_CH'
;

-- 2026-05-19T07:54:07.687Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-19T07:54:13.735Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583889,'de_CH')
;

-- 2026-05-19T07:54:13.795Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583889,'de_CH')
;

-- Element: IsAllowIssuingAnyHU
-- 2026-05-19T07:56:00.419Z
UPDATE AD_Element_Trl SET Name='No Raw Material Check', PrintName='No Raw Material Check',Updated=TO_TIMESTAMP('2026-05-19 07:56:00.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583889 AND AD_Language='en_US'
;

-- 2026-05-19T07:56:00.476Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-19T07:56:07.415Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583889,'en_US')
;

