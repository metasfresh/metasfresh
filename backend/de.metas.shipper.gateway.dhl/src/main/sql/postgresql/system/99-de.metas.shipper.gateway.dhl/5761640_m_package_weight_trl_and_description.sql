-- Element: PackageWeight
-- 2025-07-30T10:10:55.700Z
UPDATE AD_Element_Trl SET Description='Packstück Gewicht Berechungsmethode kann über System - Konfiguration "de.metas.shipping.WeightSourceTypes" angepasst werden.', Name='Packstück Gewicht', PrintName='Packstück Gewicht',Updated=TO_TIMESTAMP('2025-07-30 10:10:55.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=540096 AND AD_Language='de_CH'
;

-- 2025-07-30T10:10:55.702Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-30T10:10:55.951Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540096,'de_CH')
;

-- Element: PackageWeight
-- 2025-07-30T10:11:13.322Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-30 10:11:13.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=540096 AND AD_Language='de_CH'
;

-- 2025-07-30T10:11:13.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540096,'de_CH')
;

-- Element: PackageWeight
-- 2025-07-30T10:12:45.501Z
UPDATE AD_Element_Trl SET Description='Packstück Gewicht Berechungsmethode kann über System - Konfiguration "de.metas.shipping.WeightSourceTypes" angepasst werden.', IsTranslated='Y', Name='Packstück Gewicht', PrintName='Packstück Gewicht',Updated=TO_TIMESTAMP('2025-07-30 10:12:45.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=540096 AND AD_Language='de_DE'
;

-- 2025-07-30T10:12:45.503Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-30T10:12:46.772Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(540096,'de_DE')
;

-- 2025-07-30T10:12:46.774Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540096,'de_DE')
;

-- Element: PackageWeight
-- 2025-07-30T10:13:48.847Z
UPDATE AD_Element_Trl SET Description='Weight of a package calculation can be changed via system config "de.metas.shipping.WeightSourceTypes"',Updated=TO_TIMESTAMP('2025-07-30 10:13:48.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=540096 AND AD_Language='en_US'
;

-- 2025-07-30T10:13:48.849Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-30T10:13:49.223Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540096,'en_US')
;
