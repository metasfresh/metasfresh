-- Run mode: SWING_CLIENT

-- Element: M_LocatorFrom_ID
-- 2025-12-10T15:56:31.714Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-10 15:56:31.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583180 AND AD_Language='de_CH'
;

-- 2025-12-10T15:56:32.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583180,'de_CH')
;

-- Element: M_LocatorFrom_ID
-- 2025-12-10T15:57:43.809Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='From Locator', PrintName='From Locator',Updated=TO_TIMESTAMP('2025-12-10 15:57:43.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583180 AND AD_Language='en_US'
;

-- 2025-12-10T15:57:43.810Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-10T15:57:44.168Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583180,'en_US')
;

-- Element: M_LocatorFrom_ID
-- 2025-12-10T15:57:47.461Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-10 15:57:47.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583180 AND AD_Language='de_DE'
;

-- 2025-12-10T15:57:47.464Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583180,'de_DE')
;

-- 2025-12-10T15:57:47.471Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583180,'de_DE')
;

