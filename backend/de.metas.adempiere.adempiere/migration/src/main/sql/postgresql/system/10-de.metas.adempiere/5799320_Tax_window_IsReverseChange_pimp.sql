-- Run mode: SWING_CLIENT

-- Column: C_Tax.IsReverseCharge
-- 2026-04-23T08:46:45.782Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-04-23 08:46:45.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=586704
;

-- UI Element: Steuersatz(137,D) -> Steuer(174,D) -> main -> 20 -> property.Reverse Charge
-- Column: C_Tax.IsReverseCharge
-- 2026-04-23T08:48:04.908Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-04-23 08:48:04.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=617553
;

-- UI Element: Steuersatz(137,D) -> Steuer(174,D) -> main -> 10 -> default.Fiskalvertretung
-- Column: C_Tax.IsFiscalRepresentation
-- 2026-04-23T08:48:04.916Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-04-23 08:48:04.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=585274
;

-- UI Element: Steuersatz(137,D) -> Steuer(174,D) -> main -> 10 -> default.Kleinunernehmen
-- Column: C_Tax.IsSmallbusiness
-- 2026-04-23T08:48:04.923Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-04-23 08:48:04.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=585275
;

-- UI Element: Steuersatz(137,D) -> Steuer(174,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Tax.IsActive
-- 2026-04-23T08:48:04.937Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-04-23 08:48:04.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- UI Element: Steuersatz(137,D) -> Steuer(174,D) -> main -> 20 -> org.Sektion
-- Column: C_Tax.AD_Org_ID
-- 2026-04-23T08:48:04.946Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-04-23 08:48:04.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- Element: IsReverseCharge
-- 2026-04-23T08:58:24.669Z
UPDATE AD_Element_Trl SET Description='Steuerschuldnerschaft des Leistungsempfängers nach §13b UStG; der Lieferant weist keine Umsatzsteuer aus.', Help='Die Umsatzsteuer wird vom Leistungsempfänger geschuldet (§13b UStG). In der Buchung werden Umsatzsteuer und Vorsteuer gleichzeitig erfasst.', IsTranslated='Y', Name='13b UStG', PrintName='13b UStG',Updated=TO_TIMESTAMP('2026-04-23 08:58:24.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582372 AND AD_Language='de_CH'
;

-- 2026-04-23T08:58:24.671Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-23T08:58:25.377Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582372,'de_CH')
;

-- Element: IsReverseCharge
-- 2026-04-23T08:58:39.843Z
UPDATE AD_Element_Trl SET Description='Steuerschuldnerschaft des Leistungsempfängers nach §13b UStG; der Lieferant weist keine Umsatzsteuer aus.', Help='Die Umsatzsteuer wird vom Leistungsempfänger geschuldet (§13b UStG). In der Buchung werden Umsatzsteuer und Vorsteuer gleichzeitig erfasst.', Name='13b UStG', PrintName='13b UStG',Updated=TO_TIMESTAMP('2026-04-23 08:58:39.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582372 AND AD_Language='de_DE'
;

-- 2026-04-23T08:58:39.844Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-23T08:58:40.877Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582372,'de_DE')
;

-- 2026-04-23T08:58:40.878Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582372,'de_DE')
;

-- Element: IsReverseCharge
-- 2026-04-23T08:58:42.668Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-23 08:58:42.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582372 AND AD_Language='de_DE'
;

-- 2026-04-23T08:58:42.690Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582372,'de_DE')
;

-- 2026-04-23T08:58:42.693Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582372,'de_DE')
;

-- Element: IsReverseCharge
-- 2026-04-23T08:59:18.992Z
UPDATE AD_Element_Trl SET Description='Tax liability shifts to the recipient under reverse charge (§13b UStG); no VAT is charged by the supplier.', Help='The recipient accounts for VAT under reverse charge (§13b UStG). Output and input VAT are recorded simultaneously.',Updated=TO_TIMESTAMP('2026-04-23 08:59:18.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=582372 AND AD_Language='en_US'
;

-- 2026-04-23T08:59:18.995Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-23T08:59:19.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582372,'en_US')
;

