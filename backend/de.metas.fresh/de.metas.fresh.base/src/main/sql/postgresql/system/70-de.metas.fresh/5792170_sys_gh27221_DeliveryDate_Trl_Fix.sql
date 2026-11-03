-- Run mode: SWING_CLIENT

-- Element: DeliveryDate
-- 2026-03-05T14:21:30.374Z
UPDATE AD_Element_Trl SET Name='Delivery Date', PrintName='Delivery Date',Updated=TO_TIMESTAMP('2026-03-05 14:21:30.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=541376 AND AD_Language='en_US'
;

-- 2026-03-05T14:21:30.434Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-05T14:21:40.558Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541376,'en_US')
;

-- Element: DeliveryDate
-- 2026-03-05T14:21:52.878Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-05 14:21:52.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=541376 AND AD_Language='de_DE'
;

-- 2026-03-05T14:21:52.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541376,'de_DE')
;

-- Element: DeliveryDate
-- 2026-03-05T14:21:57.850Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum', PrintName='Lieferdatum',Updated=TO_TIMESTAMP('2026-03-05 14:21:57.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=541376 AND AD_Language='de_CH'
;

-- 2026-03-05T14:21:57.907Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-05T14:22:06.534Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(541376,'de_CH')
;

-- 2026-03-05T14:22:06.598Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541376,'de_CH')
;

