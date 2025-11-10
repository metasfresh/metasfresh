-- Run mode: SWING_CLIENT

-- Element: avv_no
-- 2025-11-10T18:25:55.353Z
UPDATE AD_Element_Trl SET Description='Abfallschlüsselnummer - dient der eindeutigen Identifikation von Abfallarten nach der Abfallverzeichnisverordnung (AVV)', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-10 18:25:55.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583997 AND AD_Language='de_DE'
;

-- 2025-11-10T18:25:55.419Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T18:26:04.439Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583997,'de_DE')
;

-- 2025-11-10T18:26:04.504Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583997,'de_DE')
;

-- Element: avv_no
-- 2025-11-10T18:26:13.159Z
UPDATE AD_Element_Trl SET Description='Abfallschlüsselnummer - dient der eindeutigen Identifikation von Abfallarten nach der Abfallverzeichnisverordnung (AVV)', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-10 18:26:13.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583997 AND AD_Language='de_CH'
;

-- 2025-11-10T18:26:13.230Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T18:26:24.840Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583997,'de_CH')
;

-- Element: avv_no
-- 2025-11-10T18:26:34.660Z
UPDATE AD_Element_Trl SET Description='Waste code number - serves to uniquely identify waste types according to the Waste Catalogue Ordinance (AVV)', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-10 18:26:34.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583997 AND AD_Language='en_US'
;

-- 2025-11-10T18:26:34.724Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T18:26:44.545Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583997,'en_US')
;

