-- Run mode: SWING_CLIENT

-- Element: Forwarder_ID
-- 2024-05-23T09:30:05.694Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Spediteur', PrintName='Spediteur',Updated=TO_TIMESTAMP('2024-05-23 09:30:05.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581379 AND AD_Language='de_CH'
;

-- 2024-05-23T09:30:05.721Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581379,'de_CH')
;

-- Element: Forwarder_ID
-- 2024-05-23T09:30:16.856Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Spediteur', PrintName='Spediteur',Updated=TO_TIMESTAMP('2024-05-23 09:30:16.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581379 AND AD_Language='de_DE'
;

-- 2024-05-23T09:30:16.858Z
UPDATE AD_Element SET Name='Spediteur', PrintName='Spediteur', Updated=TO_TIMESTAMP('2024-05-23 09:30:16.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=581379
;

-- 2024-05-23T09:30:17.272Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581379,'de_DE')
;

-- 2024-05-23T09:30:17.275Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581379,'de_DE')
;

