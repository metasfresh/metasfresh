
-- Element: PP_Product_Planning_ID
-- 2026-06-12T12:29:12.365Z
UPDATE AD_Element_Trl SET Name='Produkt Plandaten',Updated=TO_TIMESTAMP('2026-06-12 12:29:12.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53268 AND AD_Language='de_CH'
;

-- 2026-06-12T12:29:12.366Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-12T12:29:12.647Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53268,'de_CH')
;

-- 2026-06-12T12:29:12.648Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53268,'de_CH')
;

-- Element: PP_Product_Planning_ID
-- 2026-06-12T12:29:17.088Z
UPDATE AD_Element_Trl SET Name='Produkt Plandaten',Updated=TO_TIMESTAMP('2026-06-12 12:29:17.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53268 AND AD_Language='de_DE'
;

-- 2026-06-12T12:29:17.088Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-12T12:29:17.245Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53268,'de_DE')
;
