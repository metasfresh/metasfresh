-- Run mode: SWING_CLIENT

-- Element: MultiplyRate
-- 2025-09-24T16:35:23.496Z
UPDATE AD_Element_Trl SET Description='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück', Help='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück', IsTranslated='Y', Name='Umrechnungsfaktor', PrintName='Umrechnungsfaktor',Updated=TO_TIMESTAMP('2025-09-24 16:35:23.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=466 AND AD_Language='de_DE'
;

-- 2025-09-24T16:35:23.561Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T16:35:31.884Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(466,'de_DE')
;

-- 2025-09-24T16:35:31.943Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(466,'de_DE')
;

-- Element: MultiplyRate
-- 2025-09-24T16:35:59.552Z
UPDATE AD_Element_Trl SET Description='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück', Help='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück', IsTranslated='Y', Name='Umrechnungsfaktor', PrintName='Umrechnungsfaktor',Updated=TO_TIMESTAMP('2025-09-24 16:35:59.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=466 AND AD_Language='de_CH'
;

-- 2025-09-24T16:35:59.611Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T16:36:07.971Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(466,'de_CH')
;

-- Element: MultiplyRate
-- 2025-09-24T16:36:37.654Z
UPDATE AD_Element_Trl SET Description='An amount is multiplied by this factor to calculate the amount in the target unit. For example, when converting from pieces to kilograms, this factor is the weight per piece', Help='An amount is multiplied by this factor to calculate the amount in the target unit. For example, when converting from pieces to kilograms, this factor is the weight per piece', Name='Conversion factor', PrintName='Conversion factor',Updated=TO_TIMESTAMP('2025-09-24 16:36:37.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=466 AND AD_Language='en_US'
;

-- 2025-09-24T16:36:37.714Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T16:36:48.386Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(466,'en_US')
;

-- Field: Produkt(140,D) -> Maßeinheit Umrechnung(53246,D) -> Umrechnungsfaktor
-- Column: C_UOM_Conversion.MultiplyRate
-- 2025-09-24T16:39:15.203Z
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück', Help='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück', Name='Umrechnungsfaktor',Updated=TO_TIMESTAMP('2025-09-24 16:39:15.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=57412
;

-- 2025-09-24T16:39:15.294Z
UPDATE AD_Field_Trl trl SET Description='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück',Help='Ein Betrag wird mit diesem Faktor multipliziert, um den Betrag in der Ziel Einheit zu errechnen. Z.B. bei Umrechnung von Stück in Kilo ist dieser Faktor das Gewicht pro Stück',Name='Umrechnungsfaktor' WHERE AD_Field_ID=57412 AND AD_Language='de_DE'
;

-- 2025-09-24T16:39:15.417Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(466)
;

-- 2025-09-24T16:39:15.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=57412
;

-- 2025-09-24T16:39:15.608Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(57412)
;

-- Element: DivideRate
-- 2025-09-24T16:40:46.090Z
UPDATE AD_Element_Trl SET Description='Ein Betrag wird durch diesen Divisor dividiert, um den Betrag in der Quell Einheit zu errechnen. Z.B. bei Umrechnung von Kilo in Stück ist dieser Faktor das die Menge pro Kilo', Name='Umrechnungsdivisor', PrintName='Umrechnungsdivisor',Updated=TO_TIMESTAMP('2025-09-24 16:40:46.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='de_DE'
;

-- 2025-09-24T16:40:46.150Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T16:40:53.924Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(286,'de_DE')
;

-- 2025-09-24T16:40:53.983Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'de_DE')
;

-- Element: DivideRate
-- 2025-09-24T16:41:08.469Z
UPDATE AD_Element_Trl SET Description='Ein Betrag wird durch diesen Divisor dividiert, um den Betrag in der Quell Einheit zu errechnen. Z.B. bei Umrechnung von Kilo in Stück ist dieser Faktor das die Menge pro Kilo', Name='Umrechnungsdivisor', PrintName='Umrechnungsdivisor',Updated=TO_TIMESTAMP('2025-09-24 16:41:08.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='de_CH'
;

-- 2025-09-24T16:41:08.531Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T16:41:16.380Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'de_CH')
;

-- Element: DivideRate
-- 2025-09-24T16:41:33.798Z
UPDATE AD_Element_Trl SET Description='An amount is divided by this divisor to calculate the amount in the source unit. For example, when converting kilograms to pieces, this factor is the quantity per kilogram.', Name='Conversion divisor', PrintName='Conversion divisor',Updated=TO_TIMESTAMP('2025-09-24 16:41:33.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='en_US'
;

-- 2025-09-24T16:41:33.855Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T16:41:40.091Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'en_US')
;

-- Field: Produkt(140,D) -> Maßeinheit Umrechnung(53246,D) -> Umrechnungsdivisor
-- Column: C_UOM_Conversion.DivideRate
-- 2025-09-24T16:42:13.383Z
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Ein Betrag wird durch diesen Divisor dividiert, um den Betrag in der Quell Einheit zu errechnen. Z.B. bei Umrechnung von Kilo in Stück ist dieser Faktor das die Menge pro Kilo', Help='', Name='Umrechnungsdivisor',Updated=TO_TIMESTAMP('2025-09-24 16:42:13.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=57411
;

-- 2025-09-24T16:42:13.496Z
UPDATE AD_Field_Trl trl SET Description='Ein Betrag wird durch diesen Divisor dividiert, um den Betrag in der Quell Einheit zu errechnen. Z.B. bei Umrechnung von Kilo in Stück ist dieser Faktor das die Menge pro Kilo',Name='Umrechnungsdivisor' WHERE AD_Field_ID=57411 AND AD_Language='de_DE'
;

-- 2025-09-24T16:42:13.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(286)
;

-- 2025-09-24T16:42:13.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=57411
;

-- 2025-09-24T16:42:13.748Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(57411)
;

-- UI Element: Produkt(140,D) -> Maßeinheit Umrechnung(53246,D) -> main -> 10 -> default.Ziel-Quelle Faktor
-- Column: C_UOM_Conversion.DivideRate
-- 2025-09-24T16:43:22.176Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-09-24 16:43:22.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000206
;

-- UI Element: Produkt(140,D) -> Maßeinheit Umrechnung(53246,D) -> main -> 10 -> default.IsCatchUOMForProduct
-- Column: C_UOM_Conversion.IsCatchUOMForProduct
-- 2025-09-24T16:43:22.528Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-24 16:43:22.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560297
;

-- UI Element: Produkt(140,D) -> Maßeinheit Umrechnung(53246,D) -> main -> 10 -> default.Active
-- Column: C_UOM_Conversion.IsActive
-- 2025-09-24T16:43:22.881Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-24 16:43:22.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000202
;

-- UI Element: Produkt(140,D) -> Maßeinheit Umrechnung(53246,D) -> main -> 10 -> default.Sektion
-- Column: C_UOM_Conversion.AD_Org_ID
-- 2025-09-24T16:43:23.236Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-24 16:43:23.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000200
;

