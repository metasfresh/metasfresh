-- Run mode: SWING_CLIENT

-- Element: AccountConceptualName
-- 2025-04-17T19:01:44.149Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Buchungsschlüssel', PrintName='Buchungsschlüssel',Updated=TO_TIMESTAMP('2025-04-17 19:01:44.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582046 AND AD_Language='de_CH'
;

-- 2025-04-17T19:01:44.154Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-17T19:01:44.574Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582046,'de_CH')
;

-- Element: AccountConceptualName
-- 2025-04-17T19:01:51.283Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Buchungsschlüssel', PrintName='Buchungsschlüssel',Updated=TO_TIMESTAMP('2025-04-17 19:01:51.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582046 AND AD_Language='de_DE'
;

-- 2025-04-17T19:01:51.284Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-17T19:01:52.743Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582046,'de_DE')
;

-- 2025-04-17T19:01:52.744Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582046,'de_DE')
;

