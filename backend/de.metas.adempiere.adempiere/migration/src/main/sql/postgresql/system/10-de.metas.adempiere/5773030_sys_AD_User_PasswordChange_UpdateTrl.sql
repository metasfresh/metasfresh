


-- Element: OldPassword
-- 2025-10-08T12:22:57.295Z
UPDATE AD_Element_Trl SET Description='The current password must be known unless the logged-in role has the "Allow Password Change For Others" permission, or the logged-in user is a System Administrator with access to all organizations.',Updated=TO_TIMESTAMP('2025-10-08 12:22:57.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='en_US'
;

-- 2025-10-08T12:22:57.299Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-08T12:22:57.761Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'en_US')
;

-- Element: OldPassword
-- 2025-10-08T12:23:49.849Z
UPDATE AD_Element_Trl SET Description='Das alte Passwort muss bekannt sein, es sei denn, die angemeldete Rolle verfügt über die Berechtigung „Passwortänderung für andere erlauben“ oder der angemeldete Benutzer ist ein Systemadministrator mit Zugriff auf alle Organisationen.',Updated=TO_TIMESTAMP('2025-10-08 12:23:49.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_CH'
;

-- 2025-10-08T12:23:49.850Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-08T12:23:50.065Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_CH')
;

-- Element: OldPassword
-- 2025-10-08T12:23:57.875Z
UPDATE AD_Element_Trl SET Description='Das alte Passwort muss bekannt sein, es sei denn, die angemeldete Rolle verfügt über die Berechtigung „Passwortänderung für andere erlauben“ oder der angemeldete Benutzer ist ein Systemadministrator mit Zugriff auf alle Organisationen.',Updated=TO_TIMESTAMP('2025-10-08 12:23:57.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2571 AND AD_Language='de_DE'
;

-- 2025-10-08T12:23:57.875Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-08T12:23:59.252Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2571,'de_DE')
;

-- 2025-10-08T12:23:59.253Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2571,'de_DE')
;


