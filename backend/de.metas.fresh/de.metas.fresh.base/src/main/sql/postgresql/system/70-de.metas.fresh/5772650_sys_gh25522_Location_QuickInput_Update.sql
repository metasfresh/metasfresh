-- Run mode: SWING_CLIENT

-- Element: null
-- 2025-10-07T09:26:16.411Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Neue Anschrift', PrintName='Neue Anschrift',Updated=TO_TIMESTAMP('2025-10-07 09:26:16.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583962 AND AD_Language='de_DE'
;

-- 2025-10-07T09:26:16.491Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T09:26:19.338Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583962,'de_DE')
;

-- 2025-10-07T09:26:19.415Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583962,'de_DE')
;

-- Element: null
-- 2025-10-07T09:26:37.999Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Neue Anschrift', PrintName='Neue Anschrift',Updated=TO_TIMESTAMP('2025-10-07 09:26:37.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583962 AND AD_Language='de_CH'
;

-- 2025-10-07T09:26:38.074Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T09:26:41.297Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583962,'de_CH')
;

-- Element: null
-- 2025-10-07T09:26:51.176Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Neue Anschrift', PrintName='Neue Anschrift',Updated=TO_TIMESTAMP('2025-10-07 09:26:51.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583962 AND AD_Language='fr_CH'
;

-- 2025-10-07T09:26:51.251Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T09:26:53.376Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583962,'fr_CH')
;

-- Field: Neue Anschrift(541941,D) -> Neue Anschrift(548413,D) -> Lieferadresse
-- Column: C_BPartner_Location_QuickInput.IsShipTo
-- 2025-10-07T09:37:31.319Z
UPDATE AD_Field SET AD_Name_ID=580755, Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners', Name='Lieferadresse',Updated=TO_TIMESTAMP('2025-10-07 09:37:31.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753607
;

-- 2025-10-07T09:37:31.395Z
UPDATE AD_Field_Trl trl SET Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners',Help='Identifiziert die Adresse des Geschäftspartners',Name='Lieferadresse' WHERE AD_Field_ID=753607 AND AD_Language='de_DE'
;

-- 2025-10-07T09:37:31.473Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580755)
;

-- 2025-10-07T09:37:31.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753607
;

-- 2025-10-07T09:37:31.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753607)
;

-- UI Element: Neue Anschrift(541941,D) -> Neue Anschrift(548413,D) -> main -> 10 -> default.Name
-- Column: C_BPartner_Location_QuickInput.Name
-- 2025-10-07T13:55:02.057Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637030
;
