-- Run mode: SWING_CLIENT

-- Element: M_Picking_Job_Schedule_ID
-- 2025-09-24T13:58:55.722Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kommissionierplan', PrintName='Kommissionierplan',Updated=TO_TIMESTAMP('2025-09-24 13:58:55.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583882 AND AD_Language='de_DE'
;

-- 2025-09-24T13:58:55.792Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T13:59:06.872Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583882,'de_DE')
;

-- 2025-09-24T13:59:06.944Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583882,'de_DE')
;

-- Element: M_Picking_Job_Schedule_ID
-- 2025-09-24T13:59:35.130Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kommissionierplan', PrintName='Kommissionierplan',Updated=TO_TIMESTAMP('2025-09-24 13:59:35.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583882 AND AD_Language='de_CH'
;

-- 2025-09-24T13:59:35.202Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T13:59:45.645Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583882,'de_CH')
;

-- Element: QtyToPick
-- 2025-09-24T14:03:13.626Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zu kommissionierende Menge', PrintName='Zu kommissionierende Menge',Updated=TO_TIMESTAMP('2025-09-24 14:03:13.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580046 AND AD_Language='de_CH'
;

-- 2025-09-24T14:03:13.698Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T14:03:25.249Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580046,'de_CH')
;

-- Element: QtyToPick
-- 2025-09-24T14:03:39.005Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zu kommissionierende Menge', PrintName='Zu kommissionierende Menge',Updated=TO_TIMESTAMP('2025-09-24 14:03:39.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580046 AND AD_Language='de_DE'
;

-- 2025-09-24T14:03:39.079Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T14:03:48.337Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580046,'de_DE')
;

-- 2025-09-24T14:03:48.408Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580046,'de_DE')
;

