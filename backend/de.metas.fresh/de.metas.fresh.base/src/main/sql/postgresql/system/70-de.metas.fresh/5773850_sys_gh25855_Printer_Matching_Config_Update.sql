-- Run mode: SWING_CLIENT

-- Element: C_Workplace_ID
-- 2025-10-21T09:10:21.479Z
UPDATE AD_Element_Trl SET Description='Die Zuordnung gilt für alle Nutzer, die diesem Arbeitsplatz zugewiesen sind', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-21 09:10:21.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582772 AND AD_Language='de_DE'
;

-- 2025-10-21T09:10:21.555Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:10:31.983Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582772,'de_DE')
;

-- 2025-10-21T09:10:32.057Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582772,'de_DE')
;

-- Element: C_Workplace_ID
-- 2025-10-21T09:10:40.445Z
UPDATE AD_Element_Trl SET Description='The assignment applies to all users assigned to this workstation',Updated=TO_TIMESTAMP('2025-10-21 09:10:40.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582772 AND AD_Language='en_US'
;

-- 2025-10-21T09:10:40.519Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:10:50.008Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582772,'en_US')
;

-- Element: AD_User_PrinterMatchingConfig_ID
-- 2025-10-21T09:16:34.278Z
UPDATE AD_Element_Trl SET Description='Nutzer, für den die Zuordnung gilt. Falls der Nutzer einem Arbeitsplatz zugeordnet ist, wird die Drucker-Zuordnung des Arbeitsplatzes präferiert, sofern vorhanden.',Updated=TO_TIMESTAMP('2025-10-21 09:16:34.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577447 AND AD_Language='de_DE'
;

-- 2025-10-21T09:16:34.355Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:16:41.325Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577447,'de_DE')
;

-- 2025-10-21T09:16:41.403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577447,'de_DE')
;

-- Element: AD_User_PrinterMatchingConfig_ID
-- 2025-10-21T09:16:52.900Z
UPDATE AD_Element_Trl SET Description='Nutzer, für den die Zuordnung gilt. Falls der Nutzer einem Arbeitsplatz zugeordnet ist, wird die Drucker-Zuordnung des Arbeitsplatzes präferiert, sofern vorhanden.',Updated=TO_TIMESTAMP('2025-10-21 09:16:52.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577447 AND AD_Language='de_CH'
;

-- 2025-10-21T09:16:52.972Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:17:01.421Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577447,'de_CH')
;

-- Element: AD_User_PrinterMatchingConfig_ID
-- 2025-10-21T09:17:14.639Z
UPDATE AD_Element_Trl SET Description='User to whom the assignment applies. If the user is assigned to a workstation, the workstation''s printer assignment is given priority, if available.',Updated=TO_TIMESTAMP('2025-10-21 09:17:14.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577447 AND AD_Language='en_US'
;

-- 2025-10-21T09:17:14.714Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:17:24.457Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577447,'en_US')
;

-- Element: ConfigHostKey
-- 2025-10-21T09:19:24.164Z
UPDATE AD_Element_Trl SET Description='Optionaler Schlüssel, mit dem eine Drucker-Zuordnung einem Benutzer zugeordnet werden kann. Wenn es eine Zuordnung mit passendem Nutzer und Hostkey gibt, wird sie einer Zuordnung mit nur passendem Nutzer aber ohne Hostkey vorgezogen.',Updated=TO_TIMESTAMP('2025-10-21 09:19:24.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_DE'
;

-- 2025-10-21T09:19:24.239Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:19:33.349Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543929,'de_DE')
;

-- 2025-10-21T09:19:33.422Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_DE')
;

-- Element: ConfigHostKey
-- 2025-10-21T09:19:43.654Z
UPDATE AD_Element_Trl SET Description='Optionaler Schlüssel, mit dem eine Drucker-Zuordnung einem Benutzer zugeordnet werden kann. Wenn es eine Zuordnung mit passendem Nutzer und Hostkey gibt, wird sie einer Zuordnung mit nur passendem Nutzer aber ohne Hostkey vorgezogen.',Updated=TO_TIMESTAMP('2025-10-21 09:19:43.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_CH'
;

-- 2025-10-21T09:19:43.729Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:19:54.031Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_CH')
;

-- Element: ConfigHostKey
-- 2025-10-21T09:20:04.094Z
UPDATE AD_Element_Trl SET Description='Optional key used to assign a printer mapping to a user. If a mapping exists with a matching user and host key, it is preferred over a mapping with only a matching user but no host key.',Updated=TO_TIMESTAMP('2025-10-21 09:20:04.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='en_US'
;

-- 2025-10-21T09:20:04.167Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-21T09:20:15.577Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'en_US')
;

-- Field: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> Client-Zuordnungsschlüssel
-- Column: AD_Printer_Config.ConfigHostKey
-- 2025-10-21T09:22:56.346Z
UPDATE AD_Field SET DisplayLogic='@C_Workplace_ID@=0',Updated=TO_TIMESTAMP('2025-10-21 09:22:56.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555239
;

