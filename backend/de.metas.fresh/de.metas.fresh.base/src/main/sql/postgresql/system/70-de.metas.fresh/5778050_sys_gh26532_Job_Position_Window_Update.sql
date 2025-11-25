-- Run mode: SWING_CLIENT

-- Element: null
-- 2025-11-24T14:30:54.771Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stellenbezeichnung', PrintName='Stellenbezeichnung',Updated=TO_TIMESTAMP('2025-11-24 14:30:54.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574214 AND AD_Language='de_CH'
;

-- 2025-11-24T14:30:54.837Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:31:05.708Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574214,'de_CH')
;

-- Element: null
-- 2025-11-24T14:31:36.253Z
UPDATE AD_Element_Trl SET Name='Job Position', PrintName='Job Position',Updated=TO_TIMESTAMP('2025-11-24 14:31:36.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574214 AND AD_Language='en_US'
;

-- 2025-11-24T14:31:36.318Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:31:46.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574214,'en_US')
;

-- Element: null
-- 2025-11-24T14:31:58.304Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stellenbezeichnung', PrintName='Stellenbezeichnung',Updated=TO_TIMESTAMP('2025-11-24 14:31:58.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574214 AND AD_Language='de_DE'
;

-- 2025-11-24T14:31:58.367Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:32:07.497Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(574214,'de_DE')
;

-- 2025-11-24T14:32:07.566Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574214,'de_DE')
;

-- Element: null
-- 2025-11-24T14:41:11.884Z
UPDATE AD_Element_Trl SET Name='Stellenbezeichnung', PrintName='Stellenbezeichnung',Updated=TO_TIMESTAMP('2025-11-24 14:41:11.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574214 AND AD_Language='fr_CH'
;

-- 2025-11-24T14:41:11.948Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:41:23.467Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574214,'fr_CH')
;

-- Element: null
-- 2025-11-24T14:46:48.163Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stellenkategorie', PrintName='Stellenkategorie',Updated=TO_TIMESTAMP('2025-11-24 14:46:48.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574215 AND AD_Language='de_CH'
;

-- 2025-11-24T14:46:48.226Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:46:59.407Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574215,'de_CH')
;

-- Element: null
-- 2025-11-24T14:47:16.783Z
UPDATE AD_Element_Trl SET Name='Job category', PrintName='Job category',Updated=TO_TIMESTAMP('2025-11-24 14:47:16.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574215 AND AD_Language='en_US'
;

-- 2025-11-24T14:47:16.849Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:47:30.341Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574215,'en_US')
;

-- Element: null
-- 2025-11-24T14:47:41.756Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stellenkategorie', PrintName='Stellenkategorie',Updated=TO_TIMESTAMP('2025-11-24 14:47:41.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574215 AND AD_Language='de_DE'
;

-- 2025-11-24T14:47:41.860Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:48:07.276Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(574215,'de_DE')
;

-- 2025-11-24T14:48:07.462Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574215,'de_DE')
;

-- Element: null
-- 2025-11-24T14:48:43.796Z
UPDATE AD_Element_Trl SET Name='Stellenkategorie', PrintName='Stellenkategorie',Updated=TO_TIMESTAMP('2025-11-24 14:48:43.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574215 AND AD_Language='fr_CH'
;

-- 2025-11-24T14:48:43.902Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-24T14:48:57.379Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574215,'fr_CH')
;

