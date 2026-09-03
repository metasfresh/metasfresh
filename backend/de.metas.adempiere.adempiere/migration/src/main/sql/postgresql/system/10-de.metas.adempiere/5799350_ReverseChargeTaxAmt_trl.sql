-- Run mode: SWING_CLIENT

-- Element: ReverseChargeTaxAmt
-- 2026-04-23T09:22:30.658Z
UPDATE AD_Element_Trl SET Description='Steuerbetrag für Umsätze nach §13b UStG. Wird nicht dem Rechnungsbetrag hinzugerechnet.', Help='Der Steuerbetrag wird gemäß §13b UStG vom Leistungsempfänger geschuldet. Er wird für Zwecke der Umsatzsteuer-Voranmeldung als Umsatzsteuer und Vorsteuer erfasst.', Name='Steuerbetrag §13b UStG', PrintName='Steuerbetrag §13b UStG',Updated=TO_TIMESTAMP('2026-04-23 09:22:30.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582373 AND AD_Language='de_CH'
;

-- 2026-04-23T09:22:30.660Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-23T09:22:30.977Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582373,'de_CH')
;

-- Element: ReverseChargeTaxAmt
-- 2026-04-23T09:22:36.132Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-23 09:22:36.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582373 AND AD_Language='de_CH'
;

-- 2026-04-23T09:22:36.138Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582373,'de_CH')
;

-- Element: ReverseChargeTaxAmt
-- 2026-04-23T09:22:52.788Z
UPDATE AD_Element_Trl SET Description='Steuerbetrag für Umsätze nach §13b UStG. Wird nicht dem Rechnungsbetrag hinzugerechnet.', Help='Der Steuerbetrag wird gemäß §13b UStG vom Leistungsempfänger geschuldet. Er wird für Zwecke der Umsatzsteuer-Voranmeldung als Umsatzsteuer und Vorsteuer erfasst.', IsTranslated='Y', Name='Steuerbetrag §13b UStG', PrintName='Steuerbetrag §13b UStG',Updated=TO_TIMESTAMP('2026-04-23 09:22:52.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582373 AND AD_Language='de_DE'
;

-- 2026-04-23T09:22:52.789Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-23T09:22:53.468Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582373,'de_DE')
;

-- 2026-04-23T09:22:53.469Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582373,'de_DE')
;

